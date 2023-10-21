<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../../../header.html" %>
<div class="mypage-container">
	<div class="mypage-header">
		<p class="mypage-header-left"><a href="${pageContext.request.contextPath}/view/login/login-to-mypage.jsp">Bizgram</a></p>
		<div class="mypage-header-right">
			<p id="mypage-search-user">ユーザー検索</p>
			<p><a href="${pageContext.request.contextPath}/view/login/logout-alert.jsp">ログアウト</a></p>
			<div hidden="" id="information-bell-flag">${fn:length(appliedFriends)}</div>
			<p id="mypage-information" class="information-bell">お知らせ<span id="information-bell"><i class="fas fa-bell"></i></span></p>
		</div>
	</div>
	<div class="mypage-main">
		<div class="user-header">
			<div class="user-profile">
				<img src="${pageContext.request.contextPath}/images/user_profile/user${user.user_id}.jpg" onClick="" height="150" width="150">
				<p>${user.user_name}</p>
				<p class="user-info-disp" id="number-of-posted"><span></span>件の投稿</p>
				<p class="user-info-disp" id="number-of-friends">友達の数 <a href="${pageContext.request.contextPath}/friends/FriendsList.action">${user.number_of_friends}人</a></p>
				<p class="user-info-disp post-image" id="post-image">画像を投稿する</p>
			</div>
		</div>
		<div class="user-posted-field">
			<figure class="user-posted-images">
				<c:forEach var="img" items="${list}">
					<%@include file="get_posted_images.jsp" %>
				</c:forEach>
			</figure>
		</div>
		<div class="user-posted-image-popup-window" id="user-posted-image-popup-window">
			<div class="user-posted-image-popup-bg" id="user-posted-image-popup-bg"></div>
			<div class="user-posted-image-popup-content" id="user-posted-image-popup-content"></div>
		</div>
	</div>
	<div class="mypage-footer">
		<div class="user-friends-popup-window" id="user-friends-popup-window">
			<div class="user-friends-popup-bg" id="user-friends-popup-bg"></div>
			<div class="user-friends-popup-content" id="user-friends-popup-content">
				<c:forEach var="friends" items="${friends}">
				<div class="user-friends-popup-images">
					<p><img src="${pageContext.request.contextPath}/images/user_profile/user${friends.friends_id}.jpg" height="150px" width="150px"></p>
					<p>${friends.friends_name} <%-- <a href="ShowFriendPage.action?friend_id=${friends.friends_id}&friend_name=${friends.friends_name}">${friends.friends_name}の投稿を見に行く</a> --%></p>
				</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>
<div class="mypage-popup-container">
	<div class="mypage-search-user-popup-window" id="mypage-search-user-popup-window">
		<div class="mypage-search-user-popup-bg" id="mypage-search-user-popup-bg"></div>
		<div class="mypage-search-user-popup-frame" id="mypage-search-user-popup-frame">
			<input type="text" name="mypage-search-user-popup-contents-user-name" class="mypage-search-user-popup-contents">
			<button type="button" class="mypage-search-user-popup-contents" id="mypage-search-user-popup-contents-btn">ユーザー名で検索する</button>
			<div class="mypage-search-user-popup-window-xbtn" id="mypage-search-user-popup-window-xbtn">×</div>
		</div>
	</div>
	<div class="mypage-post-image-popup-window" id="mypage-post-image-popup-window">
		<div class="mypage-post-image-popup-bg" id="mypage-post-image-popup-bg"></div>
		<div class="mypage-post-image-popup-frame" id="mypage-post-image-popup-frame">
			<form method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/uploader/Uploader.action">
				<input type="file" name="file" id="post-image-file-form" accept="image/*" style="display: none">
				<div class="post-image-drop-zone" id="drag-and-drop-handler"><p>ここにアップロードする画像をドロップ</p></div>
				<div class="post-image-file-select" onClick="$('#post-image-file-form').click()">画像を選択する</div>
				<div><img id="post-image-thumbnail" src="" style="display: none"></div>
				<input type="text" name="image_name" style="display: none"  placeholder="画像タイトルを入力">
				<input type="submit" name="post-image-file" value="アップロード" style="display: none">
			</form>
		</div>
	</div>
</div>
<%@include file="../../../footer.html" %>