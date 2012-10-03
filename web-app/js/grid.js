(function($) {
	var methods = {
		init: function(passedConfig) {
			var meta = $.extend({
				data: []
				, dataUrl: ''
				, columns: [
					{label: 'No Columns Configured', name: 'no_columns', sortable: false, order: 'asc'}
				]
				, reorderableColumns: false
                , url: ''
			}, passedConfig);

            this.css('padding-bottom', '1px'); // hack for missing bottom border?

			this.empty();
			this.append('<table><thead><tr></tr></thead><tbody></tbody><tfoot></tfoot></table>');
			meta.table = this.find('table:first');
            var thead = meta.table.find('thead > tr');

            for (var c = 0; c < meta.columns.length; c++) {
                thead.append('<th>' +
                    meta.columns[c].label +
                    (meta.columns[c].sortable ? ' <span class="order ' + meta.columns[c].order  + '"></span>' : '') +
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

                // I'd definitely rather not have the decodeURIComponent in there, but it's necessary at the moment. Pending a better solution or variable qualifier
				url = url.replace(/(\{|%7B){2}(.*?)(\}|%7D){2}/gi, function(v, c1, c2, c3) {
                    var column = c2
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

            methods['render'].apply(this);

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
                    row += '<td' + (lastRow ? ' class="last-row"' : '') + '>'
                    row += meta.data[i][meta.columns[c].name] + '</td>';
                }
                tbody.append('<tr class="' + ((i + 1) % 2 ? 'even' : 'odd') + '">' + row + '</tr>');
            }
		}
		, addRow: function(data, at) {
			// allow us to pass in multiple rows
			if (data instanceof Array) {
				for (var i = 0; i < data.length; i++) {
					addRow(data, at + i);
				}
			} else {
				var meta = this.data('grid');
				// default "at" to the end of the array
				at = at || meta.data.length;

				// add the data
				meta.data.splice(at, 0, data);

				// update the namespace
				this.data('grid', meta);
			}

			return this;
		}
	};


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