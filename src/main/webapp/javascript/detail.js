let now;

let setNow = (n) => {
	now = n;
}

let getNow = () => {
	return now;
}

let slideNext = (now) => {
	let next = (now === 2) ? 1 : 2;
	const nowLi = document.querySelector('.visual_img li:nth-child(' + now + ')');
	const nextLi = document.querySelector('.visual_img li:nth-child(' + next + ')');
	nowLi.style.transition = "left 1s";
	nextLi.style.transition = "left 1s";
	nowLi.style.left = "414px";
	nextLi.style.left = "0px";

	return next;
}

let slidePrevious = (now) => {
	let next = (now === 2) ? 1 : 2;
	const nowLi = document.querySelector('.visual_img li:nth-child(' + now + ')');
	const nextLi = document.querySelector('.visual_img li:nth-child(' + next + ')');
	nowLi.style.transition = "left 1s";
	nextLi.style.transition = "left 1s";
	nowLi.style.left = "-414px";
	nextLi.style.left = "0px";

	return next;
}

let replaceProductImageTemplate = (productDescription, productImageUrl) => {
	return (`<li class="item" style="width: 414px;">
	<img class="img_thumb" alt="${productDescription}" src="http://127.0.0.1:8080/reservation/${productImageUrl}"> <span class="img_bg"></span>
	<div class="visual_txt">
		<div class="visual_txt_inn">
			<h2 class="visual_txt_tit">
				<span>${productDescription}</span>
			</h2>
			<p class="visual_txt_dsc"></p>
		</div>
	</div></li>`);
}

let addSlideButtonEventListener = () => {
	let previousButton = document.querySelector('.btn_prev');
	let nextButton = document.querySelector('.btn_nxt');

	previousButton.style.visibility = "visible";
	nextButton.style.visibility = "visible";
	setNow(1);

	previousButton.addEventListener("click", function (event) {
		before = (getNow() === 1) ? 2 : 1;
		let beforeLi = document.querySelector('.visual_img li:nth-child(' + before + ')');
		beforeLi.style.removeProperty("transition");
		beforeLi.style.left = "414px";

		setTimeout(() => { //왼쪽으로 옮겨진 image를 오른쪽으로 이동시킴 
			nowVal = slidePrevious(getNow());
			document.querySelector('#image_num').innerText = nowVal;
			setNow(nowVal);
		}, 300);
	});

	nextButton.addEventListener("click", function (event) {
		before = (getNow() === 1) ? 2 : 1;
		let beforeLi = document.querySelector('.visual_img li:nth-child(' + before + ')');
		beforeLi.style.removeProperty("transition");
		beforeLi.style.left = "-414px";

		setTimeout(() => { //오른쪽으로 옮겨진 image를 왼쪽으로 이동시킴 
			nowVal = slideNext(getNow());
			document.querySelector('#image_num').innerText = nowVal;
			setNow(nowVal);
		}, 300);
	});
}

let addMoreButtonEventListener = () => {
	let openButton = document.querySelector('.bk_more._open');
	let closeButton = document.querySelector('.bk_more._close');

	openButton.addEventListener("click", function (event) {
		openButton.style.display = "none";
		closeButton.style.display = "block";
		document.querySelector('.store_details').classList.remove("close3");
	});

	closeButton.addEventListener("click", function (event) {
		document.querySelector('.store_details').classList.add("close3");
		closeButton.style.display = "none";
		openButton.style.display = "block";
	});
}

let createProductImagesTemplate = (productDescription, productImages) => {
	let visualImage = document.createElement("ul");
	visualImage.classList.add("visual_img");
	visualImage.classList.add("detail_swipe");

	let resultHTML = "";
	let imageCount = (productImages.length == 1) ? 1 : 2;

	for (let imageIndex = 0; imageIndex < imageCount; imageIndex++) {
		let image = productImages[imageIndex];
		resultHTML += replaceProductImageTemplate(productDescription, image.saveFileName);
	}
	document.querySelector('#images_count').innerText = imageCount;
	visualImage.innerHTML = resultHTML;
	let containerVisual = document.querySelector(".container_visual");
	containerVisual.replaceChild(visualImage, document.querySelector(".visual_img"));
	document.querySelector('.visual_img').firstElementChild.style.left = "0px";
	if (imageCount === 2) {
		addSlideButtonEventListener();
	}

}

let replaceShortReviewTemplate = (productDescription,shortReview) => {
	console.log(shortReview);
	
	let template = "";
		
	if(shortReview.commentImages.length > 0){
		template+=`<div>
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
		template+=`<div>
		<div class="review_area no_img">`
	}
	template+=`			<h4 class="resoc_name">${productDescription}</h4>
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


let createCommentsTemplate = (productDescription,comments) => {
	let listShortReview = document.createElement("ul");
	listShortReview.classList.add("list_short_review");

	let resultHTML = "";

	comments.forEach(comment => {
		resultHTML += replaceShortReviewTemplate(productDescription,comment);
	});

	listShortReview.innerHTML = resultHTML;
	let containerShortReview = document.querySelector(".short_review_area");
	containerShortReview.replaceChild(listShortReview, document.querySelector(".list_short_review"));
}

let loadDisplayInfo = () => {
	let xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = () => {
		if (xmlHttpRequest.status >= 400) {
			alert("오류가 발생했습니다. 다시 시도해주세요.");
			return;
		}
		if (xmlHttpRequest.readyState === 4) {
			let productDetail = JSON.parse(xmlHttpRequest.responseText);
			console.log(productDetail);
			createProductImagesTemplate(productDetail.displayInfo.productDescription, productDetail.productImages);
			document.querySelector('.close3').innerText = productDetail.displayInfo.productContent;
			addMoreButtonEventListener();
			createCommentsTemplate(productDetail.displayInfo.productDescription,productDetail.comments);
		}
	}

	let displayInfoId = document.querySelector("#display_info_id").value;
	xmlHttpRequest.open("GET", "/reservation/api/detail?id=" + displayInfoId);
	xmlHttpRequest.send();
}

document.addEventListener("DOMContentLoaded", function () {
	const productId = loadDisplayInfo();
});