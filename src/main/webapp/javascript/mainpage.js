let promotionImageUrl = [];
let now = 1;
let imagesSize = 0;
let productListMaxIndex=0;
let categoryCount = new Map();

let slide = (now, next) => {
		// 다음 사진을 앞으로 당기고 현재 사진은 제자리로 돌아간다.
//		nowLi.style.left = (414 * (now - 2)) + "px"; // 대기줄로 이동
//		beforeLi.style.left = "-414px";// 사라지기
//		nowLi.style.left = "-414px"; // 보이기
	const nowLi = document.querySelector(`.visual_img li:nth-child(1)`);
	const nextLi = document.querySelector(`.visual_img li:nth-child(2)`);
	nowLi.style.transition = "transform 2s";
	nextLi.style.transition = "transform 2s";
	nowLi.style.transform = "translateX(-414px)";
//	nextLi.style.transform = "translateX(-414px)";
	
	nowLi.remove();
	document.querySelector(".visual_img").appendChild(nowLi);
	nowLi.style.removeProperty("transform");
	nextLi.style.removeProperty("transform");
}

let animatePromotion = (now) => {
//	let next = (now === 12)? 1 : now+1;


	setTimeout(() => {
		slide();
//		now = (now===imagesSize+1)? 1 : now+1;
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
			animatePromotion(now);
		
		}
	}
	xmlHttpRequest.open("GET", "/reservation/api/promotions");
	xmlHttpRequest.send();
}


let clickedCategoryBefore = document.querySelector(".event_tab_lst").firstElementChild;

let replaceProductTemplate = (product) => {
	return `<li class="item">
	            <a href="detail.html?id=${product.id}" class="item_book">
	                <div class="item_preview">
	                    <img alt="${product.description}" class="img_thumb" src="http://127.0.0.1:8080/reservation/${product.fileName}">
	                    <span class="img_border"></span>
	                </div>
	                <div class="event_txt">
	                    <h4 class="event_txt_tit"> <span>${product.description}</span> <small class="sm">${product.placeName}</small> </h4>
	                    <p class="event_txt_dsc">${product.content}</p>
	                </div>
	            </a>
			</li>`;
}

let createProductTemplate = (CategorizedProducts) => {
	let leftColumnHTML = "";
	let rightColumnHTML = "";

	CategorizedProducts.forEach((product, index,products) => {
		if (index%2===0) { // 짝수번째 product는 왼쪽 컬럼
			leftColumnHTML += replaceProductTemplate(product);
		} else { // 홀수번째 product는 오른쪽 컬럼
			rightColumnHTML += replaceProductTemplate(product);
		}
	});
	
	if(productListMaxIndex === 0){// 처음부터 로딩하는 경우
		document.querySelector(".lst_event_box:nth-child(1)").innerHTML = leftColumnHTML;
		document.querySelector(".lst_event_box:nth-child(2)").innerHTML = rightColumnHTML;
	} else {// 더보기 버튼 클릭 후 이어서 로딩하는 경우
		document.querySelector(".lst_event_box:nth-child(1)").innerHTML += leftColumnHTML;
		document.querySelector(".lst_event_box:nth-child(2)").innerHTML += rightColumnHTML;
	}
	
	productListMaxIndex += CategorizedProducts.length;
	let moreButton = document.querySelector(".more>button");
	if(productListMaxIndex == document.querySelector("#category_count").innerText){
		moreButton.style.visibility="hidden";
	} else {
		moreButton.style.visibility="visible";
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
	categoryId=document.querySelector(".active").closest("li").dataset.category;
	xmlHttpRequest.open("GET", "/reservation/api/products?categoryId=" + categoryId + "&startIndex=" + productListMaxIndex);
	xmlHttpRequest.send();
};

let loadCategoryCount = (categoryId) => {
	let xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = () => {
		if (xmlHttpRequest.status >= 400) {
			console.log("오류가 발생했습니다");
			return;
		}
		if (xmlHttpRequest.readyState === 4) {
			let categoryCount = JSON.parse(xmlHttpRequest.responseText);
			document.querySelector("#category_count").innerText=categoryCount;
		}
	}
	xmlHttpRequest.open("GET", "/reservation/api/categories/count?categoryId=" + categoryId);
	xmlHttpRequest.send();
}

let addCategoriesEventListener = () => {
	const categoriesUl = document.querySelector(".event_tab_lst");
	clickedCategoryBefore.firstElementChild.classList.add("active");
	
	categoriesUl.addEventListener("click", function (event) {
		let clickedCategoryNow = event.target.closest("li");
		productListMaxIndex=0;
		
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
		let tmpCode = `<li class='item' data-category=${category.id}><a class='anchor'><span>${category.name }</span></a></li>`;
		categoryCount.set(category.id,category.productCount);
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
});

window.onload = function(){
	document.querySelector(".active").click();
};