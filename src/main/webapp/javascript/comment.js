const escapeHtml = (string) => {
	const replaceMap = { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;', '/': '&#x2F;', '`': '&#x60;', '=': '&#x3D;' };
	return String(string).replace(/[&<>"'`=\/]/g, function (s) { return replaceMap[s]; });
}

const replaceShortReviewTemplate = (productDescription, shortReview, index) => {
	let template = "";

	if (shortReview.commentImages.length > 0) {
		template += `<li class="list_item"><div>
		<div class="review_area">
			<div class="thumb_area" data-index="${index}">
			<a class="thumb" title="이미지 크게 보기"> 
			
			<img
				width="90" height="90" class="img_vertical_top"
				src="/reservation/file/download?fileId=${shortReview.commentImages[0].fileId}"
				alt="리뷰이미지">

			</a> <span class="img_count">${shortReview.commentImages.length}</span>
		</div>
		`
	} else {
		template += `<li class="list_item"><div>
		<div class="review_area no_img">`
	}
	template += `			<h4 class="resoc_name">${productDescription}</h4>
			<p class="review">${escapeHtml(shortReview.comment)}</p>
		</div>
		<div class="info_area">
			<div class="review_info">
				<span class="grade">${shortReview.score}</span> <span class="name">${shortReview.email}</span>
				<span class="date">${shortReview.createDate} 방문</span>
			</div>
		</div>
	</div>
</li>
	`
	return template;
}

const replaceLargeImage = (fildId) => {
	return `<li class="item" ><img class="comment_img_larger"
	alt="리뷰 이미지"
	src="/reservation/file/download?fileId=${fildId}">
</li>` ;
}

const createCommentsTemplate = (productDescription, comments) => {
	let listShortReview = document.createElement("ul");
	listShortReview.classList.add("list_short_review");

	let resultHTML = "";

	comments.forEach((comment, index) => {
		resultHTML += replaceShortReviewTemplate(productDescription, comment, index);
	});

	listShortReview.innerHTML = resultHTML;
	let containerShortReview = document.querySelector(".short_review_area");
	containerShortReview.replaceChild(listShortReview, document.querySelector(".list_short_review"));

	addCommentImageThumbEventListener(comments);
}

const setProductDetail = (productScoreAverage, commentsCount) => {
	document.querySelector('#score_average').innerHTML = productScoreAverage;
	document.querySelector('.graph_value').style.width = productScoreAverage * 20 + "%";
	document.querySelector('#commentsCount').innerHTML = commentsCount;
}

const addCommentImageThumbEventListener = (comments) => {
	const commentImageThumbs = document.querySelectorAll(".thumb_area");


	commentImageThumbs.forEach(thumb => {
		thumb.addEventListener("click", function () {
			const commentIndex = thumb.dataset.index;

			viewImagesLarger(comments[commentIndex]);
			document.querySelector('#popup_viewing_images_larger').style.display = "block";

			document.querySelector('.popup_btn_close').addEventListener("click", function () {
				document.querySelector('#popup_viewing_images_larger').style.display = "none";
			})
		})
	});

}

const viewImagesLarger = (comment) => {
	let visualImage = document.createElement("ul");
	let commentImagesHTML = "";

	comment.commentImages.forEach(image => {
		commentImagesHTML += replaceLargeImage(image.fileId);
	});

	visualImage.innerHTML = commentImagesHTML;

	const containerImage = document.querySelector('.container_image');

	containerImage.replaceChild(visualImage, containerImage.querySelector("ul"))
}

