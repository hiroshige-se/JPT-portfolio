<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../../header.html" %>
<div class="login-container">
	<div class="login-slideshow">
		<div class="login-slideshow-area" id="login-slideshow-area">
			<img src="../../images/login_slideshow_images/bada-bagh-3181803_1280.jpg" width="520px" height="390px">
			<img src="../../images/login_slideshow_images/banff-4331689_1280.jpg" width="520px" height="390px">
			<img src="../../images/login_slideshow_images/camogli-4166255_1280.jpg" width="520px" height="390px">
			<img src="../../images/login_slideshow_images/christmasA7_03360_TP_V.jpg" width="520px" height="390px">
			<img src="../../images/login_slideshow_images/istanbul-4307665_1280.jpg" width="520px" height="390px">
			<img src="../../images/login_slideshow_images/PAKU140720354076_TP_V.jpg" width="520px" height="390px">
			<img src="../../images/login_slideshow_images/city-3630634_1280.jpg" width="520px" height="390px">
		</div>
	</div>
	<div class="login-form">
		<div class="login-label-bizgram" id="login-label-bizgram"><h2>Bizgram</h2></div>
		<h3 class="login-label-sub">友達の写真を見て「いいね！」をしよう！</h3>
		<form action="${pageContext.request.contextPath}/login/Login.action" method="post" >
		<div class="login-form-input-field">
			<input type="text" name="username" placeholder="ユーザーネーム">
			<div></div>
			<input type="password" name="password" placeholder="パスワード">
		</div>
		<div class="login-btn" id="login-btn">
			<input type="submit" value="ログイン">
		</div>
		</form>
	</div>
</div>
<%@include file="../../footer.html" %>