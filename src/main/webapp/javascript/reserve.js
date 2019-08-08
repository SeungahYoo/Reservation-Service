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
            
            document.querySelector('#productDescription').innerHTML=productDetail.displyInfo.productDescription;
		}
	}

	let displayInfoId = document.querySelector("#display_info_id").value;
	xmlHttpRequest.open("GET", "/reservation/api/detail?id=" + displayInfoId + "&is-detail=true");
	xmlHttpRequest.send();
}

document.addEventListener("DOMContentLoaded", function () {
	const productId = loadDisplayInfo();
});