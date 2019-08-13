const loadReservations = (reservationEmail) => {
	let xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = () => {
		if (xmlHttpRequest.status >= 400) {
			alert("오류가 발생했습니다. 다시 시도해주세요.");
			window.location.replace("/reservation/mainpage");
			return;
		}
		if (xmlHttpRequest.readyState === 4) {
			let reservations = JSON.parse(xmlHttpRequest.responseText);
			console.log(reservations);
		}
	}

	xmlHttpRequest.open("GET", "/reservation/api/my-reservation?email=" + reservationEmail);
	xmlHttpRequest.send();
}

var getCookie = function(name) {
	var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
	return value? value[2] : null;
  };

document.addEventListener("DOMContentLoaded", function () {
	let reservationEmail = getCookie('email');
	console.log(reservationEmail);

	const MyReservations = loadReservations(reservationEmail);
	console.log(MyReservations)
});