<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../../header.html" %>
<div class="container">
	<h1>本当にログアウトしますか？</h1>
	<form action="${pageContext.request.contextPath}/login/Logout.action" method="post" >
		<p><input type="submit" value="ログアウトする"></p>
	</form>
	<a href="${pageContext.request.contextPath}/view/user_mypage/mypage.jsp">
		<input type="submit" value="マイページへ戻る">
	</a>
</div>
<%@include file="../../footer.html" %>
