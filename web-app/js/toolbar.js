(function(){
	$(function() {
		var menu = $('#main-nav');

		menu.on('mouseenter click', 'li', function() {
			var self = $(this);
			var subs = self.children('ul');
			var topLevel = !!self.parent().parent('nav').length;
			subs.css('display', 'block');
			subs.position({
				of: self
				, my: "left top"
				, at: topLevel ? "left bottom" : "right top"
				, collision: "flip flip"
			});
		}).on('mouseleave', 'li', function() {
			var self = $(this);
			var subs = self.children('ul');
			subs.css('display', 'none');
		});
	});
})();