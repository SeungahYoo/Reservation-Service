let promotionImageUrl = [];
let now = 1;
let imagesSize = 0;
let productListMaxIndex = 0;
let currentCategoryCount = 0;

let slide = (now) => {
	let next = (now === imagesSize) ? 1 : now + 1;
	const nowLi = document.querySelector(`.visual_img li:nth-child(${now})`);
	const nextLi = document.querySelector(`.visual_img li:nth-child(${next})`);
	nowLi.style.transition = "left 2s";
	nextLi.style.transition = "left 2s";
	nowLi.style.left = "-414px";
	nextLi.style.left = "0px";

	return next;
}

let animatePromotion = (now) => {
	let nextIndex = slide(now);

	setTimeout(() => {
		let before = (now === 1) ? imagesSize : now - 1;
		let nowLi = document.querySelector(`.visual_img li:nth-child(${before})`);
		nowLi.style.removeProperty("transition");
		nowLi.style.left = "414px";

		animatePromotion(nextIndex);
	}, 3000);
}

let replacePromotionTemplate = (productImageUrl) => {
	return `<li class="item" id="promotionImage" style="background-image: url(${productImageUrl});">
        		<a href="#"> 
					<span class="img_btm_border"></span> 
					<span class="img_right_border"></span> 
					<span class="img_bg_gra"></span>
            			<div class="event_txt">
							<h4 class="event_txt_tit"></h4>
                			<p class="event_txt_adr"></p>
                			<p class="event_txt_dsc"></p>
            			</div>
        		</a>
    		</li>`;
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

let loadPromotions = () => {
	let xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = () => {
		if (xmlHttpRequest.status >= 400) {
			alert("오류가 발생했습니다. 다시 시도해주세요.");
			return;
		}

		if (xmlHttpRequest.readyState === 4) {
			let imageList = JSON.parse(xmlHttpRequest.responseText);
			imageList.forEach((image) => {
				promotionImageUrl.push(image.promotionImage);
			});
			imagesSize = promotionImageUrl.length;
			createPromotionTemplate();
			document.querySelector('.visual_img').firstElementChild.style.left = "0px";
			animatePromotion(now);
		}
	}
	xmlHttpRequest.open("GET", "/reservation/api/promotions");
	xmlHttpRequest.send();
}

let replaceProductTemplate = (product) => {
	return `<li class="item">
	            <a href="/reservation/detail?id=${product.displayInfoId}" class="item_book">
	                <div class="item_preview">
	                    <img alt="${product.description}" class="img_thumb" src="${product.productImageUrl}">
	                    <span class="img_border"></span>
	                </div>
	                <div class="event_txt">
	                    <h4 class="event_txt_tit"> <span>${product.productDescription}</span> <small class="sm">${product.placeName}</small> </h4>
	                    <p class="event_txt_dsc">${product.productContent}</p>
	                </div>
	            </a>
			</li>`;
}

let createProductTemplate = (CategorizedProducts) => {
	let leftColumnHTML = "";
	let rightColumnHTML = "";

	CategorizedProducts.forEach((product, index, products) => {
		if (index % 2 === 0) { // 짝수번째 product는 왼쪽 컬럼
			leftColumnHTML += replaceProductTemplate(product);
		} else { // 홀수번째 product는 오른쪽 컬럼
			rightColumnHTML += replaceProductTemplate(product);
		}
	});

	if (productListMaxIndex === 0) {// 처음부터 로딩하는 경우
		document.querySelector(".lst_event_box:nth-child(1)").innerHTML = leftColumnHTML;
		document.querySelector(".lst_event_box:nth-child(2)").innerHTML = rightColumnHTML;
	} else {// 더보기 버튼 클릭 후 이어서 로딩하는 경우
		document.querySelector(".lst_event_box:nth-child(1)").innerHTML += leftColumnHTML;
		document.querySelector(".lst_event_box:nth-child(2)").innerHTML += rightColumnHTML;
	}

	productListMaxIndex += CategorizedProducts.length;
	let moreButton = document.querySelector(".more>button");

	if (productListMaxIndex == currentCategoryCount) {
		moreButton.style.visibility = "hidden";
	} else {
		moreButton.style.visibility = "visible";
	}
}

let loadCategoryProducts = () => {
	let xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = () => {
		if (xmlHttpRequest.status >= 400) {
			alert("오류가 발생했습니다");
			return;
		}
		if (xmlHttpRequest.readyState === 4) {
			let CategorizedProducts = JSON.parse(xmlHttpRequest.responseText);
			createProductTemplate(CategorizedProducts, event);
		}
	}
	categoryId = document.querySelector(".active").closest("li").dataset.category;
	xmlHttpRequest.open("GET", `/reservation/api/products?categoryId=${categoryId}&startIndex=${productListMaxIndex}&imageType=th`);
	xmlHttpRequest.send();
};

let loadCategoryCount = (categoryId) => {
	let xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = () => {
		if (xmlHttpRequest.status >= 400) {
			alert("오류가 발생했습니다");
			return;
		}
		if (xmlHttpRequest.readyState === 4) {
			currentCategoryCount = JSON.parse(xmlHttpRequest.responseText);
			document.querySelector("#category_count").innerText = currentCategoryCount;
		}
	}
	xmlHttpRequest.open("GET", `/reservation/api/categories/count?categoryId=${categoryId}`);
	xmlHttpRequest.send();
}

let addCategoriesEventListener = () => {
	const categoriesUl = document.querySelector(".event_tab_lst");

	categoriesUl.addEventListener("click", function (event) {
		let clickedCategoryBefore = document.querySelector(".active").closest("li");
		let clickedCategoryNow = event.target.closest("li");
		productListMaxIndex = 0;

		clickedCategoryBefore.firstElementChild.classList.remove("active");
		clickedCategoryNow.firstElementChild.classList.add("active");
		loadCategoryCount(clickedCategoryNow.dataset.category);
		loadCategoryProducts(clickedCategoryNow.dataset.category, productListMaxIndex);
		clickedCategoryBefore = clickedCategoryNow;
	});
}

let createCategoryTemplate = (categories) => {
	let resultHTML = "";
	categories.forEach((category) => {
		let tmpCode = `<li class='item' data-category=${category.id}><a class='anchor'><span>${category.name}</span></a></li>`;
		document.querySelector(".event_tab_lst").innerHTML += tmpCode;
	});
	addCategoriesEventListener();
}

let loadCategories = () => {
	let xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = () => {
		if (xmlHttpRequest.status >= 400) {
			alert("오류가 발생했습니다. 다시 시도해주세요.");
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

let addMoreButtonEventListener = () => {
	const moreButton = document.querySelector(".more>button");
	moreButton.addEventListener("click", function (event) {
		loadCategoryProducts();
	});
}

document.addEventListener("DOMContentLoaded", function () {
	loadCategories();
	loadPromotions();
	addMoreButtonEventListener();
	loadCategoryCount(0);
	loadCategoryProducts(0, 0);

	let cookieEmail = getCookie('email') ;
	if(cookieEmail !== null ){
		document.querySelector('.viewReservation').innerText = cookieEmail;
	}
});

