
let loadReviewInfo = () => {
	let xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = () => {
		if (xmlHttpRequest.status >= 400) {
			alert("오류가 발생했습니다. 다시 시도해주세요.");
			return;
		}
		if (xmlHttpRequest.readyState === 4) {
			let productDetail = JSON.parse(xmlHttpRequest.responseText);
			console.log(productDetail);
			
			document.querySelector('#score_average').innerHTML = productDetail.productScoreAverage;
			document.querySelector('.graph_value').style.width = productDetail.productScoreAverage*20+"%";
			document.querySelector('#commentsCount').innerHTML = productDetail.commentsCount;

			createCommentsTemplate(productDetail.displayInfo.productDescription, productDetail.comments);
			document.querySelector('#product_title').innerHTML=productDetail.displayInfo.productDescription;
		}
	}

	let displayInfoId = document.querySelector("#display_info_id").value;
	xmlHttpRequest.open("GET", `/reservation/api/detail?id=${displayInfoId}&is-detail=false`);
	xmlHttpRequest.send();
}

document.addEventListener("DOMContentLoaded", function () {
	const productId = loadReviewInfo();
});