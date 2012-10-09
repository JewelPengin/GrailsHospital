(function($) {
    var renderers = {
        string: function(str, config) {
            return str;
        }
        , truncate: function(str, config) {
            return str.substr(0, config.maxlength);
        }
        , blob: function(str, config) {
            var val = $.trim(str).replace(/\s{2,}/g, ' ')
                , numChars = config.numchars || 80
            // TODO: Clean HTML if need be
                , cleanHTML = config.cleanhtml === undefined ? true : config.cleanhtml
                , useTooltip = config.usetooltip === undefined ? true : config.usetooltip
                , wasTruncated = false;
            ;

            if (val.length > numChars) {
                val = $.trim(val.substr(0, numChars - 3)) + '...';
                wasTruncated = true;
            }

            return '<div class="table-blob"' +
                    ' data-full="' + $('<div/>').html(str).text() + '"' +
                    ' data-length="' + numChars + '"' +
                    ' data-use-tooltip="' + useTooltip + '"' +
                    ' data-was-truncated="' + wasTruncated + '">' +
                    val +
                    '</div>';
        }
        , email: function(str, col) {
            return '<a href="mailto:' + str + '">' + str + '</a>';
        }
        , yesno: function(str) {
            return str.toString() === "1" ? 'Yes' : 'No';
        }
        , number: function(str, config) {
            return str === '' ? '' : Number(str).toLocaleString();
        }
        // http://code.google.com/p/datejs/wiki/FormatSpecifiers
        , date: getDateTimeRenderer('MMM d, yyyy')
        , time: getDateTimeRenderer('h:mm tt')
        , datetime: getDateTimeRenderer('MMM d, yyyy h:mm tt')
        , shortdate: getDateTimeRenderer('MM/dd/yyyy')
        , money: function(str, config) {
            var currencySign = config.currencysign || '$'
                , decimalSeparator = (1.2).toLocaleString().substr(1, 1)
                , val = Number(str).toFixed(2)
            ;
            val = Number(val).toLocaleString().split(decimalSeparator);
            return currencySign + val[0] + decimalSeparator + padR(val[1] || '');
        }
        , timespan: function(str, config) {
            var val = str
                , unit = config.unit || 'milliseconds'
                , ms
                , minutes
                , seconds
                , hours
            ;
            if (val === '') { return ''; }
            if (unit === 'hours') { ms = val * 60 * 60 * 1000; }
            if (unit === 'minutes') { ms = val * 60 * 1000; }
            if (unit === 'seconds') { ms = val * 1000; }
            if (unit === 'milliseconds') { ms = val; }
            hours = Math.floor(ms / (60 * 60 * 1000));
            minutes = Math.floor(ms / (60 * 1000)) % 60;
            seconds = Math.round(ms / 1000) % 60;
            val = ((minutes || hours) ? padL(seconds) : seconds) + 's';
            if (minutes || hours) { val = (hours ? padL(minutes) : minutes) + 'm ' + val; }
            if (hours) { val = hours + 'h ' + val; }
            return val;
        }
        , filesize: function(str, config) {
            var filesize = parseFloat(str)
                , dp = config.decimalplaces || 2
                , units = [ 'bytes', 'KB', 'MB', 'GB', 'TB', 'PB' ]
                , i = 0
                ;
            for (i = 0; i < units.length; i++) {
                if (filesize >= 1024) {
                    filesize /= 1024;
                } else {
                    break;
                }
            }
            return (filesize.toString().indexOf('.') > -1 ? filesize.toFixed(dp) : filesize) + ' ' + units[i];
        }
    }
    , oppositeSort = {'asc': 'desc', 'desc': 'asc'}

    function getRenderer(col) {
        var r = renderers[col.renderer.toLowerCase()] || window[col.renderer]
            , obj = window
            , key
            , parts
            , i
        ;
        if (!$.isFunction(r)) {
            // eval is evil so recurse through window if we can
            parts = col.renderer.split('.');
            for (i = 0; i < parts.length; i++) {
                key = parts[i];
                if (obj[key] !== undefined) {
                    obj = obj[key];
                    if (i === parts.length - 1 && $.isFunction(obj)) {
                        r = obj;
                    }
                } else {
                    break;
                }
            }
            if (!$.isFunction(r)) {
                r = renderers[col.type];
            }
        }
        return r;
    }

    function parseDate(value) {
        var parts = value.split('.');
        // Assumes the data coming in looks like one of the following:
        // {ts '2011-10-26 15:34:47'}
        // {ts '2011-10-26 15:34:47.123'} (however, decimal seconds are dropped)
        // 15:34:47.1234567 (the date defaults to today; use time renderer)
        value = value.replace(/^\{ts '(\d{4}-\d\d-\d\d \d\d:\d\d:\d\d)(\.\d{1,3})?'\}/, '$1');
        // handle Datejs's shortcomings
        if (/^\d\d:\d\d:\d\d\.\d+$/.test(value)) {
            return Date.parse(parts[0]).add({ seconds: parseFloat('.' + parts[1]) });
        }
        return Date.parse(value);
    }

    function getDateTimeRenderer(defaultMask) {
        return function(str, config) {
            var value = str
                , date
            ;
            if (value.length > 0) {
                date = parseDate(value);
                if (date !== null) {
                    return date.toString(config.mask || defaultMask);
                }
            }
            return '';
        };
    }

    function padR(string) {
        return (string + '00').substr(0, 2);
    }

    function padL(string) {
        var s = '00' + string
            , o = s.length - 2
            ;
        return s.substr(o, 2);
    }

	$.fn.grid = function(config) {
        var container = this
            , identifier = container.prop('id')
            , render
            , getSortFunction
            , cols = {} // name/value pair for easier lookup
        ;

        getSortFunction = function(key, asc) {
            var direction = (asc) ? 1 : -1
                , type = cols[key].type
            ;
            switch(type) {
                case 'string':
                case 'blob':
                    return function(a, b) {
                        a = a[key].toLowerCase();
                        b = b[key].toLowerCase();
                        if (a < b) {
                            return direction * -1;
                        }
                        if (a > b) {
                            return direction;
                        }
                        return 0;
                    };
                case 'number':
                case 'bit':
                    return function(a, b) {
                        a = parseFloat(a[key]) || 0;
                        b = parseFloat(b[key]) || 0;
                        if (a < b) {
                            return direction * -1;
                        }
                        if (a > b) {
                            return direction;
                        }
                        return 0;
                    };
                case 'date':
                case 'time':
                case 'datetime':
                    return function(a, b) {
                        a = parseDate(a[key]);
                        b = parseDate(b[key]);
                        if (a < b) {
                            return direction * -1;
                        }
                        if (a > b) {
                            return direction;
                        }
                        return 0;
                    };
            }
            return function(a, b) {
                a = a[key];
                b = b[key];
                if (!isNaN(a) && !isNaN(b)) {
                    a = parseInt(a, 10);
                    b = parseInt(b, 10);
                } else {
                    a = a.toLowerCase();
                    b = b.toLowerCase();
                }
                if (a < b) {
                    return direction * -1;
                }
                if (a > b) {
                    return direction;
                }
                return 0;
            };
        }

        render = function(fullRender) {
            var tbody
                , numLinks = 2
                , pagingControls
                , usePaging
                , fullRender = fullRender === undefined ? false : fullRender;
            ;

            if (fullRender) {
                container.empty();
                container.append('<table><thead><tr></tr></thead><tbody></tbody><tfoot></tfoot></table>');
                config.table = container.find('table:first');
                var thead = config.table.find('thead > tr');
                tbody = config.table.find('tbody');

                for (var c = 0; c < config.columns.length; c++) {
                    var col = config.columns[c];

                    var header = $('<th data-name="' + col.name + '">' +
                        (config.reorderableColumns ? '<div class="drag"></div>' : '') +
                        col.label +
                        (col.sortable ? ' <span class="order ' + col.order  + '"></span>' : '') +
                        //(config.reorderableColumns ? '<div class="remove"></div>' : '') +
                        '<div class="resize"></div>' +
                    '</th>');

                    thead.append(header);
                }

                config.table.on('click', 'th', function() {
                    var self = $(this)
                        , name = self.data('name')
                        , sortDir = cols[name].order
                        , sortIndicator = self.find('span.order')
                    ;

                    if (cols[name].sortable) {

                        if (config.sortedOn == name) {
                            sortDir = config.sortedDir;
                        }

                        sortDir = oppositeSort[sortDir];

                        sortIndicator.removeClass('asc desc').addClass(sortDir);

                        sort(name, sortDir);

                    }

                }).on('mouseover', 'th', function() {
                    var self = $(this);
                    self.children('.drag, .remove').show();
                }).on('mouseout', 'th', function() {
                    var self = $(this);
                    self.children('.drag, .remove').hide();
                }).on('click', 'th > .remove', function() {
                    var self = $(this);
                    var columnName = self.parent('th').data('name');
                    for (var c = 0; c < config.columns.length; c++) {
                        if (config.columns[c].name == columnName) {
                            config.columns.splice(c, 1);
                        }
                    }
                    render(true);
                }).on('click', 'td', function() {
                    var self = $(this);
                    var rowIdx = self.parents('tr:first').data('idx');
                    var url = '';

                    if (self.data('url') !== undefined) {
                        url = self.data('url');
                    } else if (config.url != '') {
                        url = config.url;
                    }

                    url = url.replace(/(\{|%7B){2}(.*?)(\}|%7D){2}/gi, function(v, c1, c2, c3) {
                        var column = c2;
                        if (config.data.length - 1 >= rowIdx && typeof config.data[rowIdx][column] != 'undefined') {
                            return config.data[rowIdx][column];
                        }

                        return '';
                    });

                    if (url != '') {
                        window.location = url;
                    }
                }).on('mouseover', 'td', function() {
                    var self = $(this);
                    var blobTooltipChild = self.children('div.table-blob[data-use-tooltip="true"]');

                    if (blobTooltipChild.length) {
                        var tooltipContainer = container.children('#' + identifier + 'TooltipContainer');

                        if (!tooltipContainer.length) {
                            container.append('<div id="' + identifier + 'TooltipContainer" class="table-blob-tooltip"></div>');
                            tooltipContainer = container.children('#' + identifier + 'TooltipContainer');
                        }

                        self.on('mousemove', function(e) {
                            var tooltipWidth = tooltipContainer.width();
                            var offsetParent = tooltipContainer.offsetParent();
                            var pOffset = offsetParent.offset();
                            var pWidth = offsetParent.width();
                            var leftOffset = 0;

                            if (pOffset.left + pWidth <= e.pageX + tooltipWidth + 15) {
                                leftOffset = e.pageX - pOffset.left - tooltipWidth - 15;
                            } else {
                                leftOffset = e.pageX - pOffset.left + 15;
                            }

                            tooltipContainer.css({'top': e.pageY - pOffset.top + 10, 'left': leftOffset});
                        });

                        tooltipContainer.html(blobTooltipChild.data('full')).show();

                        self.mousemove();
                    }
                }).on('mouseout', 'td', function() {
                    var self = $(this);
                    var blobTooltipChild = self.children('div.table-blob[data-use-tooltip="true"]');

                    if (blobTooltipChild.length) {
                        self.unbind('mousemove');
                        container.children('#' + identifier + 'TooltipContainer').hide().html("");
                    }
                }).on('sortupdate', function() {
                    var headers = config.table.find('thead > tr:first > th');
                    var newColumnOrder = [];
                    headers.each(function() {
                        var self = $(this);
                        newColumnOrder.push(cols[self.data('name')]);
                    });
                    config.columns = newColumnOrder;
                    render(true);
                });

                if (config.reorderableColumns) {
                    config.table.addClass('reorderable');
                    config.table.sortable({
                        items: 'th'
                        , helper: 'clone'
                        , handle: '.drag'
                    });
                }

                thead.find('th').resizable({
                    handles: 'e'
                    , autoHide: true
                });

            } else {
                tbody = config.table.find('tbody')
            }

            tbody.empty();

            for (var i = 0; i < config.data.length; i++) {
                var row = '';
                var lastRow = ((i + 1) == config.data.length);
                for (var c = 0; c < config.columns.length; c++) {
                    row += '<td' + (lastRow ? ' class="last-row"' : '') + '>' +
                        config.columns[c].renderer(config.data[i][config.columns[c].name], config.columns[c]) +
                        '</td>';
                }
                tbody.append('<tr class="' + ((i + 1) % 2 ? 'even' : 'odd') + '" data-idx="' + i + '">' + row + '</tr>');
            }

            makePageButton = function(page, label, buttonClass) {
                if (label === undefined) {
                    label = page;
                }
                if (buttonClass === undefined) {
                    buttonClass = ' class="page-button" ';
                }
                return '<a href="javascript:void(0);" data-page="' + page + '"' + buttonClass + '><span>' + label + '</span></a>';
            };


            pagingControls = container.find('div.grid-paging');
            if (!pagingControls.length) {
                // TODO: I feel like this could be done more efficiently, but something for later.
                if (config.pagingLocation == 'bottom') {
                    container.append('<div class="grid-paging"></div>');
                } else {
                    container.prepend('<div class="grid-paging"></div>');
                }
                pagingControls = container.find('div.grid-paging');
            }
            pagingControls.empty();

            usePaging = config.rows > 0 && config.dataLength > config.data.length;

            if (usePaging) {
                numPages = Math.ceil(config.dataLength / config.rows);
                useNumbersOnly = numPages <= 1 + numLinks * 2;
                startRow = (config.rows * (config.page - 1)) + 1;
                endRow = config.rows * config.page;
                if (endRow > config.dataLength) {
                    endRow = config.dataLength;
                }

                // don't always need to show this text
                if (config.pagingNumOnly === false) {
                    pagingControls.append('Showing rows ' + startRow + '-' + endRow + ' of ' + config.dataLength + ' - ');
                }

                // add << and < buttons
                if (config.page > 1 && !useNumbersOnly) {
                    if (config.pagingUseWords === false) {
                        pagingControls.append(makePageButton(1, '&lt;&lt;', ' class="page-first" '));
                        pagingControls.append(makePageButton(config.page - 1, '&lt;', ' class="page-previous" '));
                    } else {
                        pagingControls.append(makePageButton(1, 'First', ' class="page-first" '));
                        pagingControls.append(makePageButton(config.page - 1, 'Previous', ' class="page-previous" '));
                    }
                } else if (config.pagingShowAll === true && !useNumbersOnly) {
                    if (config.pagingUseWords === false) {
                        pagingControls.append('<span class="page-first-inactive">&lt;&lt;</span>');
                        pagingControls.append('<span class="page-previous-inactive">&lt;</span>');
                    } else {
                        pagingControls.append('<span class="page-first-inactive">First</span>');
                        pagingControls.append('<span class="page-previous-inactive">Previous</span>');
                    }
                }

                // we want 5 links from 2 pages back to 2 pages forward, or up to 4 pages forward or back if we're on one side or the other
                numLeft = Math.max(0, Math.min(config.page - 1, numLinks));
                numRight = Math.max(0, Math.min(numPages - config.page, numLinks));
                numLeft += Math.max(0, Math.min(numLinks - numRight, config.page - numLinks - 1));
                numRight += Math.max(0, Math.min(numLinks - numLeft, numPages - config.page - numLinks));

                for (i = numLeft; i > 0; i--) {
                    pagingControls.append(makePageButton(config.page - i));
                }

                pagingControls.append('<span class="current-page">' + config.page + '</span>');

                for (i = 1; i <= numRight; i++) {
                    pagingControls.append(makePageButton(config.page + i));
                }

                // add > and >> buttons
                if (config.page < numPages && !useNumbersOnly) {
                    if (config.pagingUseWords === false) {
                        pagingControls.append(makePageButton(config.page + 1, '&gt;', ' class="page-next" '));
                        pagingControls.append(makePageButton(numPages, '&gt;&gt;', ' class="page-last" '));
                    } else {
                        pagingControls.append(makePageButton(config.page + 1, 'Next', ' class="page-next" '));
                        pagingControls.append(makePageButton(numPages, 'Last', ' class="page-last" '));
                    }
                } else if (config.pagingShowAll === true && !useNumbersOnly) {
                    if (config.pagingUseWords === false) {
                        pagingControls.append('<span class="page-next-inactive">&gt;</span>');
                        pagingControls.append('<span class="page-last-inactive">&gt;&gt;</span>');
                    } else {
                        pagingControls.append('<span class="page-next-inactive">Next</span>');
                        pagingControls.append('<span class="page-last-inactive">Last</span>');
                    }
                }

                pagingControls.append(' - ')

                pagingControls.find('a').on('click', function() {
                    var self = $(this)
                        , page = self.data('page')
                    ;
                    getData(page);
                });

            }

            var rowList = [5, 10, 15, 25, 50, 100, 250, 500];
            var rowOptions = '';
            var rowSelected = '';

            for (i = 0; i < rowList.length; i++) {
                if (config.rows == rowList[i]) {
                    rowSelected = ' selected="selected"';
                } else {
                    rowSelected = '';
                }

                rowOptions += '<option value="'+rowList[i]+'"'+ rowSelected +'>'+rowList[i]+'</option>';

                if (rowList[i] >= config.dataLength) {
                    break;
                }

                if ((i + 1) != rowList.length && config.rows > rowList[i] && config.rows < rowList[i+1]) {
                    //add config.rows to the list
                    rowOptions += '<option value="'+config.rows+'" selected="selected">'+config.rows+'</option>';
                }
            }

            pagingControls.append('Rows: <select name="rows">'+rowOptions+'</select>');

            pagingControls.find('select[name="rows"]').on('change', function() {
                config.rows = $(this).val();
                getData();
            });


        }

        getColumnByName = function(col) {
            for (var i = 0; i < config.columns.length; i++) {
                if (config.columns[i].name == col) {
                    return config.columns[i]
                }
            }

            return null
        }

        addRow = function(data, at, render) {
            // default "at" to the end of the array
            at = at || config.data.length;
            render = render || true;

            if (typeof at == 'boolean') {
                render = at;
                at = config.data.length;
            }

            // allow us to pass in multiple rows
            if (data instanceof Array) {
                for (var i = 0; i < data.length; i++) {
                    addRow(data, at + i, false);
                }
            } else {
                // add the data
                config.data.splice(at, 0, data);
            }

            if (render) {
                this.render();
            }
        }

        sort = function(col, dir) {
            config.sortedOn = col;
            config.sortedDir = dir;

            if (config.dataLength <= config.data.length) {
                config.data.sort(getSortFunction(config.sortedOn, !!(config.sortedDir == 'asc')));
                render();
            } else {
                config.page = 1;
                getData();
            }
        }

        getData = function(page, col, dir) {
            page = page || config.page || 1
            col = col || config.sortedOn || ''
            dir = dir || config.sortedDir || 'asc'

            // TODO: implement caching

            if (config.dataUrl != '') {
                $.ajax({
                    url: config.dataUrl
                    , type: 'POST'
                    , data: {
                        offset: config.rows * (page - 1)
                        , sort: (col == '' ? undefined : col)
                        , order: dir
                        , max: config.rows
                    }
                }).done(function(returnData) {
                    config.page = page;
                    config.sortedOn = col;
                    config.sortedDir = dir;
                    config.data = returnData.list.list; // odd formatting I know, but it's what I decided on.
                    render();
                }).fail(function() {
                    alert('Failed loading grid data.');
                })
            }

        }

        /******** init section ********/

        config = $.extend({
            data: []
            , dataLength: 0
            , dataUrl: ''
            , columns: [
                {label: 'No Columns Configured', name: 'no_columns', renderer: 'string', sortable: false, order: 'asc'}
            ]
            , reorderableColumns: false
            , url: ''
            , sortedOn: ''
            , sortedDir: 'asc'
            , page: 1
            , rows: -1
            , pagingLocation: 'top' // top/bottom
            , pagingShowAll: false
            , pagingUseWords: false
            , pagingNumOnly: false
        }, config);

        container.css('padding-bottom', '1px'); // hack for missing bottom border?

        container.empty();

        // column loop - do as much as you can here to cut back re-looping
        for (var c = 0; c < config.columns.length; c++) {
            var col = config.columns[c];

            if (col.name === undefined) {
                throw('grid failed - cannot have a column without a name');
            }

            col.label = col.label || col.name;
            col.type = col.type || 'string';
            col.renderer = col.renderer || col.type;
            col.sortable = col.sortable === undefined ? true : col.sortable;
            col.order = col.order || 'asc';

            if (typeof col.renderer === 'string') {
                col.renderer = getRenderer(col);
            }

            cols[col.name] = config.columns[c] = col;
        }

        render(true);

        this.data('grid', this);

        return this;
	}
})(jQuery);