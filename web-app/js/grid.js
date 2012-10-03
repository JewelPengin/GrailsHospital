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
            ;
            if (val.length > numChars) {
                return $.trim(val.substr(0, numChars - 3)) + '...';
            }
            return val;
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
        var render
            , getSortFunction
        ;

        config = $.extend({
            data: []
            , dataUrl: ''
            , columns: [
                {label: 'No Columns Configured', name: 'no_columns', renderer: 'string', sortable: false, order: 'asc'}
            ]
            , reorderableColumns: false
            , url: ''
        }, config);

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

        render = function() {
            var tbody = config.table.find('tbody');
            tbody.empty();

            for (var i = 0; i < config.data.length; i++) {
                var row = '';
                var lastRow = ((i + 1) == config.data.length);
                for (var c = 0; c < config.columns.length; c++) {
                    row += '<td' + (lastRow ? ' class="last-row"' : '') + '>' +
                        config.columns[c].renderer(config.data[i][config.columns[c].name], {}) +
                        '</td>';
                }
                tbody.append('<tr class="' + ((i + 1) % 2 ? 'even' : 'odd') + '">' + row + '</tr>');
            }
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
            config.sortedAsc = !!(dir == 'asc');

            config.data.sort(getSortFunction(config.sortedOn, config.sortedAsc));
        }

        // TODO: sort function

        this.css('padding-bottom', '1px'); // hack for missing bottom border?

        this.empty();
        this.append('<table><thead><tr></tr></thead><tbody></tbody><tfoot></tfoot></table>');
        config.table = this.find('table:first');
        var thead = config.table.find('thead > tr');

        // column loop - do as much as you can here to cut back re-looping
        for (var c = 0; c < config.columns.length; c++) {
            var col = config.columns[c];

            if (col.name === undefined) {
                throw('grid failed - cannot have a column without a name');
            }

            col.label = col.label || col.name;
            col.type = col.type || 'string';
            col.renderer = col.renderer || col.type;
            col.sortable = col.sortable || true;
            col.order = col.order || 'asc';

            if (typeof col.renderer === 'string') {
                col.renderer = getRenderer(col);
            }

            thead.append('<th>' +
                col.label +
                (col.sortable ? ' <span class="order ' + col.order  + '"></span>' : '') +
                '</th>');
        }

        if (config.reorderableColumns) {
            config.table.addClass('reorderable');
            config.table.sortable({
                items: 'th'
            });
        }

        config.table.on('click', 'th', function() {
            var self = $(this);

            if (config.dataUrl != '') {
                $.ajax({
                    url: config.dataUrl
                    , type: 'POST'
                    , data: {}
                }).done(function() {
                        // render data
                    }).fail(function() {
                        // do something productive.
                    })
            }

        }).on('click', 'td', function() {
            var self = $(this);
            var rowIdx = 0;
            var url = '';

            // figure out my row idx
            config.table.find('tr').each(function(idx) {
                if ($(this).find(self).length) {
                    rowIdx = idx - 1;
                    return false;
                }
            });

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
        });

        render();

        this.data('grid', this);

        return this;
	}
})(jQuery);