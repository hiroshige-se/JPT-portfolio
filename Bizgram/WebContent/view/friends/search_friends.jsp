<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../header.html" %>
<form action="${pageContext.request.contextPath}/friends/SearchFriends.action" method="post">
	<input type="text" placeholder="友達を検索" name="friend_search">
	<input type="submit" value="検索">
</form>
<hr>
<h2>${user.user_name}の友達一覧</h2>
<div class="friends_list">
	<c:forEach var="friends" items="${friends}">
		<p><img src="${pageContext.request.contextPath}/images/user_profile/user${friends.friend_id}.jpg" height="250px" width="250px"></p>
		<p>${friends.friend_name}</p>
		<a href="ShowFriendPage.action?friend_id=${friends.friend_id}&friend_name=${friends.friend_name}">${friends.friend_name}の投稿を見に行く</a>
	</c:forEach>
</div>
<%@include file="../../footer.html" %>