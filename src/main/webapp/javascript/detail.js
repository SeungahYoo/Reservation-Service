let nowImage;

const setNowImage = (n) => {
	nowImage = n;
}

const getNowImage = () => {
	return nowImage;
}

const slideNext = (nowImage) => {
	let nextImage = (nowImage === 2) ? 1 : 2;
	const nowLi = document.querySelector(`.visual_img li:nth-child(${nowImage})`);
	const nextLi = document.querySelector(`.visual_img li:nth-child(${nextImage})`);
	nowLi.style.transition = "left 1s";
	nextLi.style.transition = "left 1s";
	nowLi.style.left = "414px";
	nextLi.style.left = "0px";

	return nextImage;
}

const slidePrevious = (nowImage) => {
	let nextImage = (nowImage === 2) ? 1 : 2;
	const nowLi = document.querySelector(`.visual_img li:nth-child(${nowImage })`);
	const nextLi = document.querySelector(`.visual_img li:nth-child(${nextImage})`);
	nowLi.style.transition = "left 1s";
	nextLi.style.transition = "left 1s";
	nowLi.style.left = "-414px";
	nextLi.style.left = "0px";

	return nextImage;
}

const replaceProductImageTemplate = (productDescription, productImageUrl) => {
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

const addSlideButtonEventListener = () => {
	let previousButton = document.querySelector('.btn_prev');
	let nextButton = document.querySelector('.btn_nxt');

	previousButton.style.visibility = "visible";
	nextButton.style.visibility = "visible";
	setNowImage(1);

	previousButton.addEventListener("click", function (event) {
		before = (getNowImage() === 1) ? 2 : 1;
		let beforeLi = document.querySelector(`.visual_img li:nth-child(${before})`);
		beforeLi.style.removeProperty("transition");
		beforeLi.style.left = "414px";

		setTimeout(() => { //왼쪽으로 옮겨진 image를 오른쪽으로 이동시킴 
			nowVal = slidePrevious(getNowImage());
			document.querySelector('#image_num').innerText = nowVal;
			setNowImage(nowVal);
		}, 300);
	});

	nextButton.addEventListener("click", function (event) {
		before = (getNowImage() === 1) ? 2 : 1;
		let beforeLi = document.querySelector(`.visual_img li:nth-child(${before})`);
		beforeLi.style.removeProperty("transition");
		beforeLi.style.left = "-414px";

		setTimeout(() => { //오른쪽으로 옮겨진 image를 왼쪽으로 이동시킴 
			nowVal = slideNext(getNowImage());
			document.querySelector('#image_num').innerText = nowVal;
			setNowImage(nowVal);
		}, 300);
	});
}

const addButtonEventListener = () => {
	let openButton = document.querySelector('.bk_more._open');
	let closeButton = document.querySelector('.bk_more._close');
	let reviewMoreButton = document.querySelector('.btn_review_more');
	let infoTabButton = document.querySelector('.info_tab_lst');

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

	infoTabButton.addEventListener("click", function (event) {
		let beforeClickedTab = document.querySelector('li.active');
		beforeClickedTab.classList.remove('active');
		beforeClickedTab.firstChild.classList.remove('active');

		let nowClickedTab = event.target.closest("li");
		nowClickedTab.classList.add('active');
		nowClickedTab.firstChild.classList.add('active');

		if (nowClickedTab.classList.contains("_detail")) {
			document.querySelector('.detail_location').classList.add("hide");
			document.querySelector('.detail_area_wrap').classList.remove("hide");
		} else if (nowClickedTab.classList.contains("_path")) {
			document.querySelector('.detail_location').classList.remove("hide");
			document.querySelector('.detail_area_wrap').classList.add("hide");
		}
	})
}

const createProductImagesTemplate = (productDescription, productImages) => {
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

const setInfoTab = (displayInfo, displayInfoImage) => {
	//상세정보 
	document.querySelector('.detail_info_lst .in_dsc').innerHTML = displayInfo.productContent;

	//오시는길
	document.querySelector('.store_map').src = `http://127.0.0.1:8080/reservation/${displayInfoImage.saveFileName}`;
	document.querySelector('.store_name').innerHTML = displayInfo.placeName;
	document.querySelector('.store_addr_bold').innerHTML = displayInfo.placeStreet;
	document.querySelector('.addr_old_detail').innerHTML = displayInfo.placeLot;
	document.querySelector('.addr_detail').innerHTML = displayInfo.placeName;
	document.querySelector('.store_tel').innerHTML = displayInfo.telephone;
	document.querySelector('.store_tel').href = "tel:" + displayInfo.telephone
}

const loadDisplayInfo = () => {
	let xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = () => {
		if (xmlHttpRequest.status >= 400) {
			alert("오류가 발생했습니다. 다시 시도해주세요.");
			window.location.replace("/reservation/mainpage");
			return;
		}
		if (xmlHttpRequest.readyState === 4) {
			let productDetail = JSON.parse(xmlHttpRequest.responseText);
			console.log(productDetail);
			document.querySelector('.close3').innerText = productDetail.displayInfo.productContent;
			createProductImagesTemplate(productDetail.displayInfo.productDescription, productDetail.productImages);
			addButtonEventListener();
			if (productDetail.comments.length != 0) {
				createCommentsTemplate(productDetail.displayInfo.productDescription, productDetail.comments);
			} else {
				document.querySelector('#score_average').innerHTML = "0.0";
				document.querySelector('.graph_value').style.width = "0%";
				document.querySelector('#commentsCount').innerHTML = "0";
				document.querySelector('.btn_review_more').style.display = "none";
			}
			setInfoTab(productDetail.displayInfo, productDetail.displayInfoImage);
		}
	}

	let displayInfoId = document.querySelector("#display_info_id").value;
	xmlHttpRequest.open("GET", `/reservation/api/detail?id=${displayInfoId}&is-detail=true`);
	xmlHttpRequest.send();
}

document.addEventListener("DOMContentLoaded", function () {
	const productId = loadDisplayInfo();
});