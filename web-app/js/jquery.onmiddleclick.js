// onMiddleClick support
(function($) {
	var middleClick = 2;

	$.fn.onMiddleClick = function(selector, data, fn) {
		var lastTarget;

		if (fn == null) {
			if (data == null) {
				// called (fn)
				fn = selector;
				data = selector = undefined;
			} else {
				// called (selector, fn)
				fn = data;
				data = undefined;
			}
		}

		// allow shorthands (false), ('selector', false), ('selector', {}, false)
		 if (fn === false) {
			 fn = function() { return false; };
		} else if (!$.isFunction(fn)) {
			return this;
		}

		this.on('mousedown', selector, data, function(e) {
			if (e.which === middleClick) {
				e.preventDefault();
			}
			lastTarget = e.target;
		});
		this.on('mouseup', selector, data, function(e) {
			if (e.which === middleClick && lastTarget === e.target) {
				fn.apply(this, arguments);
			}
		});
		$(document).on('mousedown mouseup ', function(e) {
			if (e.target !== lastTarget) {
				lastTarget = null;
			}
		});

		return this;
	}
})(jQuery);