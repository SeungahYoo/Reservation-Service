let totalCount = 0;

const replaceBookingTicketTemplate = (productPrice, priceIndex) => {
	return `						<div class="qty">
	<div class="count_control" data-price="${productPrice.price}">
		<div class="clearfix">
			<a 
				class="btn_plus_minus spr_book2 ico_minus3 disabled"
				title="빼기"> </a> <input type="tel"
				class="count_control_input disabled" name="prices[${priceIndex}].count" value="0" readonly
				title="수량"> <a 
				class="btn_plus_minus spr_book2 ico_plus3" title="더하기"> </a>
		</div>
		<div class="individual_price">
			<span class="total_price">0</span><span class="price_type">원</span>
		</div>
	</div>
	<div class="qty_info_icon">
		<strong class="product_amount"> <span>${productPrice.priceTypeName}</span>
		</strong> <strong class="product_price"> <span class="price">${productPrice.price}</span>
			<span class="price_type">원</span>
		</strong> <em class="product_dsc">${productPrice.price}원 (${productPrice.discountRate}% 할인가)</em>
	</div>
</div>
<input type="hidden" name="prices[${priceIndex}].productPriceId" value="${productPrice.productPriceId}"/>
`;
}

const replaceStoreDetailsTemplate = (displayInfo, productPrices) => {
	let priceTemplate = "";

	productPrices.forEach(productPrice => {
		priceTemplate += `${productPrice.priceTypeName} ${productPrice.price}원 /<br>`;
	});

	return `
	<h3 class="in_tit">장소</h3>
	<p class="dsc">
		${displayInfo.placeStreet}
	</p>
	<h3 class="in_tit">관람시간</h3>
	<p class="dsc">
		${displayInfo.openingHours}
	</p>
	<h3 class="in_tit">요금</h3>
	<p class="dsc">
	${priceTemplate}
	</p>
	`;
}

function addAmountButtonEventListener() {
	const minusButtons = document.querySelectorAll('.btn_plus_minus.ico_minus3');
	const plusButtons = document.querySelectorAll('.btn_plus_minus.ico_plus3');

	minusButtons.forEach(button => {
		button.addEventListener("click", function (event) {
			const amountBox = button.parentElement.parentElement;
			let amount = Number(amountBox.querySelector('.count_control_input').value);
			if (amount <= 0) {
				//수량이 0이하면 감소할 수 없음
				return;
			}

			if (amountBox.querySelector('.btn_plus_minus.ico_plus3').classList.contains("disabled")) {
				amountBox.querySelector('.btn_plus_minus.ico_plus3').classList.remove("disabled");
			}

			const price = amountBox.dataset.price;
			amountBox.querySelector('.count_control_input').value = amount - 1;
			amountBox.querySelector('.total_price').innerText = price * (amount - 1);
			if (amount - 1 <= 0) {
				button.classList.add("disabled");
				amountBox.querySelector('.count_control_input').classList.add("disabled");
				amountBox.querySelector('.individual_price').classList.remove("on_color");
			}

			totalCount--;
			document.querySelector('#totalCount').innerText = totalCount;
		})
	})

	plusButtons.forEach(button => {
		button.addEventListener("click", function (event) {
			const amountBox = button.parentElement.parentElement;
			let amount = Number(amountBox.querySelector('.count_control_input').value);
			if (amount >= 99) {
				//수량이 99이상이면 증가시킬수 없음 
				return;
			}

			if (amountBox.querySelector('.btn_plus_minus.ico_minus3').classList.contains("disabled")) {
				amountBox.querySelector('.btn_plus_minus.ico_minus3').classList.remove("disabled");
				amountBox.querySelector('.count_control_input').classList.remove("disabled");
				amountBox.querySelector('.individual_price').classList.add("on_color");
			}

			const price = amountBox.dataset.price;
			amountBox.querySelector('.count_control_input').value = amount + 1;
			amountBox.querySelector('.total_price').innerText = price * (amount + 1);

			if (amount + 1 >= 99) {
				button.classList.add("disabled");
			}

			totalCount++;
			document.querySelector('#totalCount').innerText = totalCount;
		})
	})
}

const createBookingTicketTemplate = (productPrices, displayInfo) => {
	let ticketBody = document.createElement("div");
	ticketBody.classList.add("ticket_body");

	let resultHTML = "";

	productPrices.forEach((productPrice, index, productPrices) => {
		resultHTML += replaceBookingTicketTemplate(productPrice, index);
	});

	resultHTML += `	<input type="hidden" name="displayInfoId" value="${displayInfo.displayInfoId}"/>
	<input type="hidden" name="productId" value="${displayInfo.productId}"/>`
	ticketBody.innerHTML = resultHTML;
	let containerTicketBody = document.querySelector(".section_booking_ticket");
	containerTicketBody.replaceChild(ticketBody, document.querySelector(".ticket_body"));

	addAmountButtonEventListener();
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

			document.querySelector('#productDescription').innerText = productDetail.displayInfo.productDescription;

			const displayImage = document.querySelector('#displayImage');
			displayImage.style.position = "relative";
			displayImage.style.left = "0px";
			displayImage.querySelector('.img_thumb').src = `http://127.0.0.1:8080/reservation/${productDetail.productImages[0].saveFileName}`;
			displayImage.querySelector('.preview_txt_tit').innerText = productDetail.displayInfo.productDescription;
			document.querySelector('.store_details').innerHTML = replaceStoreDetailsTemplate(productDetail.displayInfo, productDetail.productPrices);

			createBookingTicketTemplate(productDetail.productPrices, productDetail.displayInfo);
		}
	}

	let displayInfoId = document.querySelector("#display_info_id").value;
	xmlHttpRequest.open("GET", "/reservation/api/reserve?id=" + displayInfoId);
	xmlHttpRequest.send();
}

document.addEventListener("DOMContentLoaded", function () {
	const productId = loadDisplayInfo();
});