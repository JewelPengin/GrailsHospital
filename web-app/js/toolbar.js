(function(){
	$(function() {
		var menu = $('#main-nav');
		menu.on('mouseenter click', 'li', function(e) {
			var self = $(this);
			var subs = self.children('ul');
			var topLevel = !!self.parent().parent('nav').length;

			if (e.type == 'click') { // && !subs.length - maybe?
				var link = self.children('a:first');
				if (link.length) {
					var href = link.prop('href');
					if (href.match(/^javascript:/i)) {
						alert('JWL - implement javascript handling...');
					} else {
						window.location = href;
					}
				}
			}

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


		/* Notification Dropdown Functions */

		var notificationDropdown = menu.find('ul.notifications');
		notificationDropdown.on('click', 'li', function(e) {
			var self = $(this);
			var link = self.find('a');
			if (link.length) {
				var href = link.prop('href');
				if (href.match(/^javascript:/i)) {
					alert('JWL - implement javascript handling...');
				} else {
					window.location = href;
				}
			}
		});

		var notificationCallback = function(response) {
			if (response.status == 200) {
				var data = $.parseJSON(response.responseBody);
				var html = '<li>' +
					'<div class="message-icon">' +
						'<span class="icon icon-'+ data.type +'"></span>' +
					'</div>' +
					'<div class="message-text">' +
						'<a href="'+ (data.link == '' ? '#' : data.link) +'">'+ data.message +'</a>' +
					'</div>' +
				'</li>';
				notificationDropdown.prepend(html);
			}
		}

		securityInfo = securityInfo || {"loggedIn": false}

		$.atmosphere.subscribe('/atmosphere/notification/all', notificationCallback, $.atmosphere.request = {transport: 'streaming'});

		if (securityInfo.loggedIn) {
			$.atmosphere.subscribe('/atmosphere/notification/' + securityInfo.username, notificationCallback, $.atmosphere.request = {transport: 'streaming'});
		}

	});
})();