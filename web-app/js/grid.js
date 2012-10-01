(function($) {
	var methods = {
		init: function(passedConfig) {
			var meta = $.extend({
				data: []
				, dataUrl: ''
				, columns: [
					{name: 'Column 1', key: 'column_1', sortable: false}
				]
				, reorderableColumns: false
			}, passedConfig);

			this.data('grid', meta);

			this.empty();
			this.html('<table><thead></thead><tbody></tbody><tfoot></tfoot></table>');
			var table = this.find('table:first');

			if (meta.reorderableColumns) {
				table.addClass('reorderable');
				table.sortable({
					items: 'th'
				});
			}

			table.on('click', 'th', function() {
				var self = $(this);

				if (dataUrl !== undefined) {
					$.ajax({
						url: meta.data.dataUrl
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
				table.find('tr').each(function(idx) {
					if ($(this).find(self).length) {
						rowIdx = idx - 1;
						return false;
					}
				});

				if (self.data('url') !== undefined) {
					url = self.data('url');
				} else if (table.data('url') !== undefined) {
					url = table.data('url');
				}

				url = url.replace(/\{\{(.*?)\}\}/gi, function(v, m) {
					if (tableData !== undefined && typeof tableData[rowIdx] != 'undefined' && typeof tableData[rowIdx][m] != 'undefined') {
						return tableData[rowIdx][m];
					}

					return '';
				});

				if (url != '') {
					window.location = url;
				}
			});

            methods['render'].apply(this);

			return this;
		}
		, render: function() {
			var meta = this.data('grid');

            for (var i = 0; i <= meta.data.length; i++) {

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