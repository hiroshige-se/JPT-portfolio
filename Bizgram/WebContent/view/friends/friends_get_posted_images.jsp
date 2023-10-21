<%@page pageEncoding="UTF-8" %>
<div class="user-posted-image" id="user-posted-image">
	<div hidden="" id="user-posted-image-user-id">${img.user_id}</div>
	<div hidden="" id="user-posted-image-post-id">${img.post_id}</div>
	<img src="${pageContext.request.contextPath}/showImages/ImageShowsNew.action?user_id=${img.user_id}&post_id=${img.post_id}" height="300" width="300">
	<p>title: ${img.image_name}</p>
	<p>いいね！
	<a href="${pageContext.request.contextPath}/pushGood/PushGoodButton.action?friends_id=${img.user_id}&good_for_friends_post=1&good=${img.post_id}"><i class="far fa-thumbs-up"></i></a>
	</p>
	<p>超いいね！
	<a href="${pageContext.request.contextPath}/pushGood/PushGoodButton.action?friends_id=${img.user_id}&good_for_friends_post=1&veryGood=${img.post_id}"><i class="far fa-thumbs-up"></i></a>
	</p>
	<p> ${img.good_count} いいね！されています</p>
</div>