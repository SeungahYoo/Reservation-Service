const replaceCardItemTemplate = (key, reservation) => {
	let template1 = `						<article class="card_item" data-id="${reservation.reservationInfoId}">
	<a href="#" class="link_booking_details">
		<div class="card_body">
			<div class="left"></div>
			<div class="middle">
				<div class="card_detail">
					<em class="booking_number">No.${reservation.reservationInfoId}</em>
					<h4 class="tit">${reservation.displayInfo.productDescription}</h4>
					<ul class="detail">
						<li class="item"><span class="item_tit">일정</span> <em
							class="item_dsc"> ${reservation.reservationDate} </em></li>
						<li class="item"><span class="item_tit">내역</span> <em
							class="item_dsc"> 내역이 없습니다. </em></li>
						<li class="item"><span class="item_tit">장소</span> <em
							class="item_dsc"> ${reservation.displayInfo.placeName}</em></li>
						<li class="item"><span class="item_tit">업체</span> <em
							class="item_dsc"> 업체명이 없습니다. </em></li>
					</ul>
					<div class="price_summary">
						<span class="price_tit">결제 예정금액</span> <em
							class="price_amount"> <span>${reservation.totalPrice}</span> <span
							class="unit">원</span>
						</em>
					</div>
					<!-- [D] 예약 신청중, 예약 확정 만 취소가능, 취소 버튼 클릭 시 취소 팝업 활성화 -->
					<div class="booking_cancel">`
	let template2 = `</div>
				</div>
			</div>
			<div class="right"></div>
		</div>
		<div class="card_footer">
			<div class="left"></div>
			<div class="middle"></div>
			<div class="right"></div>
		</div>
	</a> <a href="#" class="fn fn-share1 naver-splugin btn_goto_share"
		title="공유하기"></a>
</article>`;

	let button = "";
	if (key === "confirmed") {
		button = `	<button class="btn cancel">
		<span>취소</span>
		</button>`
	} else if (key === "used") {
		button = `	<button class="btn review">
		<span>예매자 리뷰 남기기</span>
		</button>`
	}

	return template1 + button + template2;
}

const loadReservations = (reservationEmail) => {
	let xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = () => {
		if (xmlHttpRequest.status >= 400) {
			alert("오류가 발생했습니다. 다시 시도해주세요.");
			window.location.replace("/reservation/mainpage");
			return;
		}
		if (xmlHttpRequest.readyState === 4) {
			let Myreservations = JSON.parse(xmlHttpRequest.responseText);
			let canceledHTML = "";
			let confirmedHTML = "";
			let usedHTML = "";
			let canceledCardList = document.createElement("div");
			let confirmedCardList = document.createElement("div");
			let usedCardList = document.createElement("div");
			canceledCardList.classList.add("card_list");
			confirmedCardList.classList.add("card_list");
			usedCardList.classList.add("card_list");

			// console.log(Myreservations);

			let canceledCount = Myreservations.canceledReservations.length;
			let confirmedCount = Myreservations.confirmedReservations.length;
			let usedCount = Myreservations.usedReservations.length;

			document.querySelector('#total_count').innerText = canceledCount + confirmedCount + usedCount;
			document.querySelector('#cancel_count').innerText = canceledCount;
			document.querySelector('#confirmed_count').innerText = confirmedCount;
			document.querySelector('#used_count').innerText = usedCount;

			Myreservations.canceledReservations.forEach(reservation => {
				canceledHTML += replaceCardItemTemplate("canceled", reservation);
			});
			Myreservations.confirmedReservations.forEach(reservation => {
				confirmedHTML += replaceCardItemTemplate("confirmed", reservation);
			});
			Myreservations.usedReservations.forEach(reservation => {
				usedHTML += replaceCardItemTemplate("used", reservation);
			});

			const canceldCardWrapper = document.querySelector('li.card.cancel').querySelector('.card_list');
			const confirmedCardWrapper = document.querySelector('li.card.confirmed').querySelector('.card_list');
			const usedCardWrapper = document.querySelector('li.card.used').querySelector('.card_list');

			canceledCardList.innerHTML = canceledHTML;
			document.querySelector('li.card.cancel').replaceChild(canceledCardList, canceldCardWrapper);
			confirmedCardList.innerHTML = confirmedHTML;
			document.querySelector('li.card.confirmed').replaceChild(confirmedCardList, confirmedCardWrapper);
			usedCardList.innerHTML = usedHTML;
			document.querySelector('li.card.used').replaceChild(usedCardList, usedCardWrapper);

			addButtonEventListener();
		}
	}

	xmlHttpRequest.open("GET", "/reservation/api/my-reservation?email=" + reservationEmail);
	xmlHttpRequest.send();
}

const cancelReservation = (reservationInfoId) => {
	let xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = () => {
		if (xmlHttpRequest.status >= 400) {
			alert("오류가 발생했습니다. 다시 시도해주세요.");
			window.location.replace("/reservation/mainpage");
			return;
		}
		if (xmlHttpRequest.readyState === 4) {
			location.reload();
		}
	}

	xmlHttpRequest.open("GET", "/reservation/api/cancel?id=" + reservationInfoId);
	xmlHttpRequest.send();
}

const addButtonEventListener = () => {
	let cancelButtons = document.querySelectorAll('.btn.cancel');
	cancelButtons.forEach(button => {
		button.addEventListener("click", function (event) {
			cancelReservation(button.closest('article').dataset.id);
		})
	});
}

const getCookie = function (name) {
	const value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
	return value ? value[2] : null;
};

document.addEventListener("DOMContentLoaded", function () {
	let reservationEmail = getCookie('email');
	console.log(reservationEmail);
	loadReservations(reservationEmail);

});