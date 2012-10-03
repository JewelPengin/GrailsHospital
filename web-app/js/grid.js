(function($) {
	var methods = {
		init: function(passedConfig) {
			var meta = $.extend({
				data: []
				, dataUrl: ''
				, columns: [
					{label: 'No Columns Configured', name: 'no_columns', renderer: 'string', sortable: false, order: 'asc'}
				]
				, reorderableColumns: false
                , url: ''
			}, passedConfig);

            this.css('padding-bottom', '1px'); // hack for missing bottom border?

			this.empty();
			this.append('<table><thead><tr></tr></thead><tbody></tbody><tfoot></tfoot></table>');
			meta.table = this.find('table:first');
            var thead = meta.table.find('thead > tr');

            // column loop - do as much as you can here to cut back re-looping
            for (var c = 0; c < meta.columns.length; c++) {
                var col = meta.columns[c];

                if (col.name === undefined) {
                    throw('grid failed - cannot have a column without a name');
                }

                col.label = col.label || col.name;
                col.renderer = col.renderer || 'string';
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

			if (meta.reorderableColumns) {
                meta.table.addClass('reorderable');
                meta.table.sortable({
					items: 'th'
				});
			}

            meta.table.on('click', 'th', function() {
				var self = $(this);

				if (meta.dataUrl != '') {
					$.ajax({
						url: meta.dataUrl
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
                meta.table.find('tr').each(function(idx) {
					if ($(this).find(self).length) {
						rowIdx = idx - 1;
						return false;
					}
				});

				if (self.data('url') !== undefined) {
					url = self.data('url');
				} else if (meta.url != '') {
					url = meta.url;
				}

                url = url.replace(/(\{|%7B){2}(.*?)(\}|%7D){2}/gi, function(v, c1, c2, c3) {
                    var column = c2;
					if (meta.data.length - 1 >= rowIdx && typeof meta.data[rowIdx][column] != 'undefined') {
						return meta.data[rowIdx][column];
					}

					return '';
				});

				if (url != '') {
					window.location = url;
				}
			});

            this.data('grid', meta);

            methods.render.apply(this);

			return this;
		}
		, render: function() {
			var meta = this.data('grid');
            var tbody = meta.table.find('tbody');
            tbody.empty();

            for (var i = 0; i < meta.data.length; i++) {
                var row = '';
                var lastRow = ((i + 1) == meta.data.length);
                for (var c = 0; c < meta.columns.length; c++) {
                    row += '<td' + (lastRow ? ' class="last-row"' : '') + '>' +
                        meta.columns[c].renderer(meta.data[i][meta.columns[c].name], {}) +
                    '</td>';
                }
                tbody.append('<tr class="' + ((i + 1) % 2 ? 'even' : 'odd') + '">' + row + '</tr>');
            }
		}
		, addRow: function(data, at, render) {
            at = at || meta.data.length
            render = render || true

            if (typeof at == 'boolean') {
                render = at
                at = meta.data.length
            }

			// allow us to pass in multiple rows
			if (data instanceof Array) {
				for (var i = 0; i < data.length; i++) {
					addRow(data, at + i, false);
				}
			} else {
				var meta = this.data('grid');
				// default "at" to the end of the array

				// add the data
				meta.data.splice(at, 0, data);

				// update the namespace
				this.data('grid', meta);
			}

            if (render) {
                methods.render.apply(this);
            }

			return this;
		}
        , sort: function(column, direction) {
            var meta = this.data('grid');
            var columnData = getColumnByName(meta.columns, column);

            if (columnData != null) {

            }
        }
    }
    , renderers = {
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

    function getColumnByName(columns, column) {
        for (var i = 0; i < columns.length; i++) {
            if (columns[i].name == column) {
                return columns[i]
            }
        }

        return null
    }

	$.fn.grid = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error( 'Method ' +  method + ' does not exist.' );
		}
	}
})(jQuery);