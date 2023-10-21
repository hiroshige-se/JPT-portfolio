//	マイページ表示時に投稿されている画像の数を取得し、指定先へ値を渡す
function numberOfPosted() {
	let postedNum = $('#user-posted-image > img').length;
	$('#number-of-posted > span').append(postedNum);
}

//	ヘッダーのお知らせタグのベルを点滅させる
function bellFlashing() {
	  $('#information-bell').fadeToggle(1000, "linear", function() {
	    bellFlashing();
	  });
	}

$(function() {
	//	ドラッグエンターを無効にする
	$(document).on("dragenter", function(e) {
		e.stopPropagation();
		e.preventDefault();
	})

	//	ドラッグオーバーを無効にする
	$(document).on("dragover", function(e) {
		e.stopPropagation();
		e.preventDefault();
	})

	//	ドロップを無効にする
	$(document).on("drop", function(e) {
		e.stopPropagation();
		e.preventDefault();
	})

	numberOfPosted();

	//	友達申請に関するお知らせがある場合は以下の処理を実行
	if ($('#information-bell-flag').text() != "0") bellFlashing();

	//	ユーザー検索タグを押した場合に、ユーザー検索ポップアップを表示する
	$('#mypage-search-user').on("click", function() {
		$('#mypage-search-user-popup-window').fadeIn();
	});

	//	ユーザー検索ポップアップで入力された値をもとに、SearchFriendsActionへ遷移する
	$('#mypage-search-user-popup-contents-btn').on("click", function() {
		let searchUserName = $('input[name="mypage-search-user-popup-contents-user-name"]').val();
		window.location.href = 'http://localhost:8080/Bizgram/friends/SearchFriends.action?friends_name=' + searchUserName;
	});

	//	ユーザー検索ポップアップを閉じる
	$('#mypage-search-user-popup-window-xbtn').on("click", function() {
		$('#mypage-search-user-popup-window').fadeOut();
	});

	//	お知らせがある場合はお知らせページへ遷移し、ない場合はアラートを表示
	$('#mypage-information').click(function() {
		if ($('#information-bell-flag').text() != "0") {
			window.location.href = 'http://localhost:8080/Bizgram/view/user_mypage/information/applied_friends.jsp';
		} else {
			alert("No notification");
		}
	});

	//	投稿画像をポップアップする
	$('#user-posted-image > img').on("click", function() {
		var userId = $(this).parent().children("#user-posted-image-user-id").html();
		var postId = $(this).parent().children("#user-posted-image-post-id").html();
		$('#user-posted-image-popup-content').append("<img src=" + "../showImages/ImageShowsNew.action?user_id=" + userId + "&post_id=" + postId + " width=620px height=465px>");
		$('#user-posted-image-popup-window').fadeIn();
	});

	//	ポップアップされた画像のポップを閉じる
	$('#user-posted-image-popup-bg, #user-posted-image-popup-content').on("click", function() {
		$('#user-posted-image-popup-content').children().remove();
		$(this).parent().fadeOut();
	});

	//	友達の数をクリックすると、友達の一覧をポップアップ表示
	$('#number-of-friends').on("click", function() {
		$('#user-friends-popup-window').fadeIn();
		/*window.location.href = 'http://localhost:8080/Bizgram/friends/FriendsList.action';*/
	});

	//	友達の一覧ポップを閉じる
	$('#user-friends-popup-bg, #user-friends-popup-content').on("click", function() {
		$(this).parent().fadeOut();
	});

	//	画像をアップするポップを表示
	$('#post-image').on("click", function() {
		$('#mypage-post-image-popup-window').fadeIn();
	});

	//	画像をアップするポップを閉じる
	$('#mypage-post-image-popup-bg').on("click", function() {
		$('#post-image-thumbnail').parent().removeClass("post-image-thumbnail-field");
		$('#post-image-thumbnail').removeAttr('src').hide();
		$('input[name="post-image-file"], input[name="image_name"]').hide();
		$(this).parent().fadeOut();
	});

	//	画像をアップするポップで、サムネイル表示をする
	$('#post-image-file-form').on("change", function() {
		if ($('#post-image-thumbnail').hide()) $('#post-image-thumbnail').show();
		if (this.files.length > 0) {
			let file = this.files[0];
			let reader = new FileReader();
			reader.readAsDataURL(file);
			$('#post-image-thumbnail').parent().addClass("post-image-thumbnail-field");
			reader.onload = function() {
				$('#post-image-thumbnail').attr('src',  reader.result);
			}
			$('input[name="post-image-file"], input[name="image_name"]').show();
		}
	});

	//	ドロップ操作で用いる変数
	let dragdropobj = $('#drag-and-drop-handler');

	//	ドラッグエンターを無効にする
	dragdropobj.on("dragenter", function(e) {
		e.stopPropagation();
		e.preventDefault();
	});

//	ドラッグオーバーを無効にする
	dragdropobj.on("dragover", function(e) {
		e.stopPropagation();
		e.preventDefault();
	});

	//	ドロップした画像をサムネイル表示する際に用いる変数
	let dragdropmethodurl = "";

	//	ドロップされた画像をアップするポップで、サムネイル表示をする
	//	同時に、ファイルフォームに画像データを挿入する
	dragdropobj.on("drop", function(e) {
		e.preventDefault();
		if ($('#post-image-thumbnail').hide()) $('#post-image-thumbnail').show();
		if($('#post-image-thumbnail').attr("src") != "") URL.revokeObjectURL(dragdropmethodurl);
		let files = e.originalEvent.dataTransfer.files;
		dragdropmethodurl = URL.createObjectURL(files[0]);
		$('#post-image-thumbnail').parent().addClass("post-image-thumbnail-field");
		$('#post-image-thumbnail').attr('src',  dragdropmethodurl);
		document.getElementById("post-image-file-form").files = files;
		$('input[name="post-image-file"], input[name="image_name"]').show();
	});
})