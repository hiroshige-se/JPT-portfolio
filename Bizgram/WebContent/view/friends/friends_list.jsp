<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../header.html" %>

<a href="../uploader/upload2.jsp">画像をアップロードする<i class="fas fa-upload"></i></a>
<a href="../login/logout-in.jsp">ログアウトする<i class="fas fa-paper-plane"></i></a>
<a href="../objects/image_shows_index_good.jsp">マイページへ戻る</a>
<form action="../friend/FriendSearch.action" method="post">
	<p>
		<input type="text" placeholder="友達を検索" name="friend_search">
		<input type="submit" value="検索">
	</p>
</form>
<hr>

<h2>${user.user_name}の友達一覧</h2>

<div class="friends_list">
	<c:forEach var="friends" items="${friends}">
		<p><img src="../images/user_profile/user${friends.friends_id}.jpg" height="250px" width="250px"></p>
		<p>${friends.friends_name}</p>
		<a href="ShowFriendsPage.action?friends_id=${friends.friends_id}&friends_name=${friends.friends_name}">${friends.friends_name}の投稿を見に行く</a>
	</c:forEach>
</div>
<%@include file="../../footer.html" %>