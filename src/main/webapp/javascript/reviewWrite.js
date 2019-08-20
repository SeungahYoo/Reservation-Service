const replaceThumbnailImage = (image) => {
    return `<li class="item"><a href=""
    class="anchor"> <span class="spr_book ico_del">삭제</span>
</a> <img
    src="${window.URL.createObjectURL(image)}"
    width="130" alt="" class="item_thumb"> <span
    class="img_border"></span></li>`
}

const addScoreStarsEventListener = () => {
    const stars = document.querySelectorAll('.rating_rdo');

    stars.forEach(starElement => {
        starElement.addEventListener("click", function () {
            let clickedValue = starElement.value;

            stars.forEach(star => {
                if (star.value <= clickedValue) {
                    star.classList.add("checked");
                    star.checked = true;
                } else {
                    star.classList.remove("checked");
                    star.checked = false;
                }
            })

            if (clickedValue > 0) {
                document.querySelector('#star_score').classList.remove("gray_star");
            } else {
                document.querySelector('#star_score').classList.add("gray_star");
            }

            document.querySelector('#star_score').innerText = clickedValue;
            document.querySelector('#review_score').value = clickedValue;
        })
    });
}

const addCommentTextareaEventListener = () => {
    document.querySelector(".review_write_info").addEventListener("click", function () {
        document.querySelector(".review_write_info").style.visibility = "hidden";
        document.querySelector(".review_textarea").style.visibility = "visible";
        document.querySelector(".review_textarea").focus();
    });
}

const addfileUploaderEventListener = () => {
    const imageUploader = document.querySelector('#reviewImageFileOpenInput');
    imageUploader.addEventListener("change", function (event) {
        const images = event.target.files;

        if (images.length > 3) {
            alert('이미지는 최대 3개까지 업로드 할 수 있습니다.');
            return;
        }

        Array.from(images).forEach(image => {
            if (!isValidImageType(image)) {
                alert('jpg, png 이미지파일만 가능합니다.');
                return;
            }
        });
        const thumbnailList = document.querySelector('.lst_thumb');
        
        let thumbnailsHTML = "";
        Array.from(images).forEach(image => {
            thumbnailsHTML += replaceThumbnailImage(image);
        })
        
        thumbnailList.innerHTML = thumbnailsHTML
    })
}

const isValidImageType = (image) => {
    return (['image/jpg', 'image/png', 'image/jpeg'].includes(String(image.type).toLocaleLowerCase()));
}

document.addEventListener("DOMContentLoaded", function () {
    addScoreStarsEventListener();
    addCommentTextareaEventListener();
    addfileUploaderEventListener();

});