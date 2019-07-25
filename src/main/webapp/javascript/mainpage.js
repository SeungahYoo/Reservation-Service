let promotionImageUrl = [];
let now = 2;
let imagesSize = 0;
let slide = (nowLi, beforeLi) => {
	// console.log(nowLi);
	// console.log(beforeLi);
	// 다음 사진을 앞으로 당기고 현재 사진은 제자리로 돌아간다.

	nowLi.style.left = (414 * (now - 2)) + "px"; // 대기줄로 이동
	beforeLi.style.left = "-414px";// 사라지기
	nowLi.style.left = "-414px"; // 보이기
}

let animatePromotion = (now) => {
	// 2부터 시작
	if (now === 1) {
		before = imagesSize;
	}
	else {
		before = now - 1;
	}


	const nowLi = document.querySelector(`.visual_img li:nth-child(${now})`);
	const beforeLi = document.querySelector('.visual_img li:nth-child(' + before + ')');
	setTimeout(() => {
		slide(nowLi, beforeLi);
		if (now === (imagesSize + 1)) {
			now = 1;
		}
		else {
			now++;
		}
		animatePromotion(now);

	}, 2000);
}

let replacePromotionTemplate = (imageUrl) => {

	let promotionTemplate = document.querySelector("#promotionItem").innerHTML;
	return promotionTemplate.replace("{productImageUrl}", imageUrl);
};


let createPromotionTemplate = () => {
	let visualImage = document.createElement("ul");
	visualImage.classList.add("visual_img");

	let resultHTML = "";

	promotionImageUrl.forEach((url) => {
		resultHTML += replacePromotionTemplate(url);
	});

	visualImage.innerHTML = resultHTML;
	let containerVisual = document.querySelector(".visual_img").parentElement;
	containerVisual.replaceChild(visualImage, document.querySelector(".visual_img"));
};

let loadPoromotions = () => {
	let xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = () => {
		if (xmlHttpRequest.status >= 400) {
			// console.log("오류");
			alert("오류가 발생했습니다. 다시 시도해주세요.");
			return;
		}

		if (xmlHttpRequest.readyState === 4) {
			let imageList = JSON.parse(xmlHttpRequest.responseText);
			console.log(imageList);
			imageList.forEach((image) => {
				promotionImageUrl.push(image.promotionImage);
			});
			imagesSize = promotionImageUrl.length;
			console.log("imgsize: " + imagesSize); "src/main/webapp/WEB-INF/views/mainpage.jsp"
			createPromotionTemplate();
			animatePromotion(now);
		}
	}
	xmlHttpRequest.open("GET", "/reservation/api/promotions");
	xmlHttpRequest.send();
}


let clickedCategoryBefore = document.querySelector(".event_tab_lst").firstElementChild;

let replaceProductTemplate = (id, description, content, placeName, fileName) => {
	let productTemplate = document.querySelector("#itenList").innerHTML;
	return productTemplate.replace("{id}", id)
		.replace("{description}", description)
		.replace("{content}", content)
		.replace("{placeName}", placeName)
		.replace("{fileName}", fileName);
}

let createProductTemplate = (CategorizedProducts) => {
	let visualImage = document.querySelector(".lst_event_box");

	let resultHTML = "";

	CategorizedProducts.forEach((product) => {
		resultHTML += replacePromotionTemplate(product.id, product.description, product.content, product.placeName, product.fileName);
	});

	visualImage.innerHTML = resultHTML;
}

let loadCategorizedProducts = (categoryId, startIdx) => {
	let xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = () => {
		if (xmlHttpRequest.status >= 400) {
			alert("오류가 발생했습니다");
			return;
		}
		if (xmlHttpRequest.readyState === 4) {
			let CategorizedProducts = JSON.parse(xmlHttpRequest.responseText);
			console.log(CategorizedProducts);
			createProductTemplate(CategorizedProducts, event);

		}
	}
	xmlHttpRequest.open("GET", "/reservation/api/products?categoryId=" + categoryId + "&startIdx=" + startIdx);
	xmlHttpRequest.send();
};

let addCategoriesEventListener = () => {
	const categoriesUl = document.querySelector(".event_tab_lst");
	clickedCategoryBefore.firstElementChild.classList.add("active");
	categoriesUl.addEventListener("click", function (event) {

		let clickedCategoryNow = event.target.closest("li");
		clickedCategoryBefore.firstElementChild.classList.remove("active");
		clickedCategoryNow.firstElementChild.classList.add("active");
		loadCategorizedProducts(clickedCategoryNow.dataset.category, 0);
		clickedCategoryBefore = clickedCategoryNow;
	});
}

let createCategoryTemplate = (categories) => {
	let resultHTML = "";
	categories.forEach((category) => {
		let tmpCode = "<li class='item' data-category=" + category.id + ">" +
				"<a class='anchor'><span>" + category.name + "</span></a></li>";
		document.querySelector(".event_tab_lst").innerHTML += tmpCode;
	});
	addCategoriesEventListener();
}

let loadCategories = () => {
	let xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = () => {
		if (xmlHttpRequest.status >= 400) {
			console.log("오류");
			// alert("오류가 발생했습니다. 다시 시도해주세요.");
			return;
		}
		if (xmlHttpRequest.readyState === 4) {
			let categories = JSON.parse(xmlHttpRequest.responseText);
			createCategoryTemplate(categories);
		}
	}
	xmlHttpRequest.open("GET", "/reservation/api/categories");
	xmlHttpRequest.send();
}

document.addEventListener("DOMContentLoaded", function () {
	loadCategories();
	loadPoromotions();
})