let replaceShortReviewTemplate = (productDescription, shortReview) => {
	let template = "";

	if (shortReview.commentImages.length > 0) {
		template += `<div>
		<div class="review_area">
			<div class="thumb_area">
			<a href="#" class="thumb" title="이미지 크게 보기"> <img
				width="90" height="90" class="img_vertical_top"
				src="http://127.0.0.1:8080/reservation/${shortReview.commentImages[0].saveFileName}"
				alt="리뷰이미지">
			</a> <span class="img_count">${shortReview.commentImages.length}</span>
		</div>
		`
	} else {
		template += `<div>
		<div class="review_area no_img">`
	}
	template += `			<h4 class="resoc_name">${productDescription}</h4>
			<p class="review">${shortReview.comment}</p>
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

let createCommentsTemplate = (productDescription, comments) => {
	let listShortReview = document.createElement("ul");
	listShortReview.classList.add("list_short_review");

	let resultHTML = "";

	comments.forEach(comment => {
		resultHTML += replaceShortReviewTemplate(productDescription, comment);
	});

	listShortReview.innerHTML = resultHTML;
	let containerShortReview = document.querySelector(".short_review_area");
	containerShortReview.replaceChild(listShortReview, document.querySelector(".list_short_review"));

	document.querySelector('#score_average').innerHTML=comments[0].productScoreAverage;
	document.querySelector('.graph_value').style.width=comments[0].productScoreAverage*20+"%";
	document.querySelector('#commentsCount').innerHTML=comments[0].commentsCount;
}

