<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../header.html" %>
<div class="friend_main">
	<a href="${pageContext.request.contextPath}/view/user_mypage/mypage.jsp">マイページへ戻る</a>
	<c:forEach var="searchFriends" items="${searchFriends}">
		<p>
		<img src="${pageContext.request.contextPath}/images/user_profile/user${searchFriends.user_id}.jpg" height="150" width="150">${searchFriends.user_name}	 最終投稿日時 : ${searchFriends.last_update}
		<a href="../friends/ToApplyFriends.action?friends_id=${searchFriends.user_id}&friends_name=${searchFriends.user_name}">友達になる<i class="far fa-handshake"></i></a>
		</p>
	</c:forEach>
</div>
<%@include file="../../footer.html" %>