"use strict";

function loginPageSlideShow() {
	let page = 0;
	let lastPage = parseInt($('#login-slideshow-area img').length -1);

	function changePage() {
		$('#login-slideshow-area img').fadeOut(1500);
		$('#login-slideshow-area img').eq(page).fadeIn(1500);
	};

	let Timer;
	function startTimer() {
		Timer = setInterval(function() {
			if (page == lastPage) {
				page = 0;
				changePage();
			} else {
				page++;
				changePage();
			}
		}, 1500);
	};

	function stopTimer() {
		clearInterval(Timer);
	};

	startTimer();
}

$(function() {
	loginPageSlideShow();

	$('#login-label-bizgram').click(function() {
		window.location.href = 'http://localhost:8080/Bizgram/index.jsp';
	});

	$('#login-btn').click(function() {
		let userName = $('input[name="username"]').val();
		let password = $('input[name="password"]').val();
		if (userName == "" || password == "") {
			alert("ユーザー名とパスワードを入力してください");
			return false;
		}
	});
})