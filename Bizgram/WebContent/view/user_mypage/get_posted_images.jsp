<%@page pageEncoding="UTF-8" %>
<div class="user-posted-image" id="user-posted-image">
	<div hidden="" id="user-posted-image-user-id">${img.user_id}</div>
	<div hidden="" id="user-posted-image-post-id">${img.post_id}</div>
	<img src="${pageContext.request.contextPath}/showImages/ImageShowsNew.action?post_id=${img.post_id}&user_id=${img.user_id}">
	<p>title: ${img.image_name}</p>
	<p>自分でいいね！
	<a href="${pageContext.request.contextPath}/pushGood/PushGoodButton.action?goodMyself=${img.post_id}"><i class="far fa-thumbs-up"></i></a>
	</p>
	<p>${img.good_count} いいね！されています</p>
</div>