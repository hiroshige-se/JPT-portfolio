$(function() {
	$('#applied-friends-list > #authorize-friends').on("click", function() {
		let friendsId = $(this).prev("#applied-friends-id").text();
		let friendsName = $(this).prev().prev("#applied-friends-name").text();
		let result = confirm('友達申請を許可しますか？');
		if (result) {
			alert("友達になりました!!");
			window.location.href = 'http://localhost:8080/Bizgram/friends/AuthorizeFriends.action?friends_id=' + friendsId + "&friends_name=" + friendsName;
		} else {
			return false;
		}
	});

	$('#reject-friends').click(function() {
		let friendsId = $(this).prev().prev("#applied-friends-id").text();
		let result = confirm('友達申請を拒否しますか？');
		if (result) {
			alert("申請を取り下げました!!");
			window.location.href = 'http://localhost:8080/Bizgram/friends/RejectFriends.action?friends_id=' + friendsId;
		} else {
			return false;
		}
	});
})