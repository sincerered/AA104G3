$(function() {
	navbar = document.getElementById('navbar-example');
	navbarParent = navbar.parentNode;
	containerElement = document.getElementsByClassName('container')[0];
	window.onscroll = function() {
		navbarParentRect = navbarParent.getBoundingClientRect();

		var tempTop = navbarParentRect.top;
		if (tempTop < 0) {
			navbar.style.position = 'fixed';

			console.log(containerElement);
			navbar.style.width = containerElement.clientWidth + 'px';
			console.log(navbar.clientWidth);

		} else {
			navbar.style.position = 'relative';
			console.log(navbar.clientWidth);

		}
	};
	window.onresize = function() {
		navbar.style.width = containerElement.clientWidth + 'px';
	};
	$('#myModal').on('shown.bs.modal', function() {
		$('#myInput').focus()
	});
	$('[data-target="#myModal"]').on('click', function(e) {
		console.log(e.target.dataset.stono);
		if (stono != e.target.dataset.stono) {

		}
	});
});