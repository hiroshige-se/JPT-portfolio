<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../../../header.html" %>
<div class="mypage-container">
	<h2>${user.user_name} へ友達の申請があります</h2>
	<div class="applied-friends-list" id="applied-friends-list">
		<c:forEach var="appliedFriends" items="${appliedFriends}">
		<img src="${pageContext.request.contextPath}/images/user_profile/user${appliedFriends.user_id}.jpg" height="150" width="150">
		<p id="applied-friends-name">${appliedFriends.user_name}</p>
		<div hidden="" id="applied-friends-id">${appliedFriends.user_id}</div>
		<div class="authorize-friends" id="authorize-friends">友達になる</div>
		<div class="reject-friends" id="reject-friends">友達にならない</div>
		</c:forEach>
	</div>
</div>
<%@include file="../../../../footer.html" %>