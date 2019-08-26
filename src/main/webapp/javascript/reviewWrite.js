const replaceThumbnailImage = (image) => {
    return `<li class="item"><a href="#"
    class="anchor"> <span class="spr_book ico_del" id="remove_file_button" data-name="${image.name}">삭제</span>
</a> <img
    src="${window.URL.createObjectURL(image)}"
    width="130" alt="" class="item_thumb"> <span
    class="img_border"></span></li>`
}

const removeFile = (imageIndex) => {
    let filelist = new DataTransfer();
    let attachedFilelist = Array.from(document.querySelector('#reviewImageFileOpenInput').files);
    attachedFilelist.splice(imageIndex, 1);

    document.querySelector('#reviewImageFileOpenInput').files = filelist.files;
}

const getIndex = (imageName, filelist) => {
    let fileIndex = 0;

    while (fileIndex < filelist.length) {
        if (fileIndex in filelist && filelist[fileIndex].name === imageName) {
            return fileIndex;
        }
        fileIndex++;
    }

    return -1;
}

const addScoreStarsEventListener = () => {

    function ScoreStar(star) {
        this.star = star;
        this.score = star.value;
    }

    ScoreStar.prototype.setScore = function (clickedValue) {
        if (this.score <= clickedValue) {
            this.star.classList.add("checked");
            this.star.checked = true;
        } else {
            this.star.classList.remove("checked");
            this.star.checked = false;
        }
    }

    const scoreStars = new Array();
    const starElements = document.querySelectorAll('.rating_rdo');

    starElements.forEach((starElement, index, starElements) => {
        scoreStars[index] = new ScoreStar(starElement);

        starElement.addEventListener("click", function () {
            let clickedValue = starElement.value;

            scoreStars.forEach(scoreStar => {
                scoreStar.setScore(clickedValue);
            });

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

    const commentTextarea = document.querySelector(".review_textarea");

    commentTextarea.addEventListener("input", function (event) {
        document.querySelector('#comment_length').innerText = commentTextarea.textLength;
        if (commentTextarea.textLength >= 400) {
            alert('최소 5자에서 최대 400자까지 등록할 수 있습니다.');
        }
    })
}

// const isSubmitValid = () => {
//     const commentLength = document.querySelector(".review_textarea").length;
//     const score = document.querySelector('#review_score').value;

//     if (commentLength < 5 || commentLength > 400) {
//         alert('최소 5자에서 최대 400자 이상 작성하셔야 게시물이 등록됩니다.');
//         return false;
//     } else if (score <= 0 || score > 5) {
//         alert('별점을 선택해 주십시요. (1~5점 내에 선택 가능합니다.)');
//         return false;
//     }

//     return true;
// }

const addCheckValidSubmitEventListener = () => {
    document.querySelector('#review_form').addEventListener("submit", function (event) {
        const commentLength = document.querySelector(".review_textarea").length;
        const score = document.querySelector('#review_score').value;

        if (commentLength < 5 || commentLength > 400) {
            alert('최소 5자에서 최대 400자 이상 작성하셔야 게시물이 등록됩니다.');
            event.preventDefault();
        } else if (score <= 0 || score > 5) {
            alert('별점을 선택해 주십시요. (1~5점 내에 선택 가능합니다.)');
            event.preventDefault();
        }

        return true;
    })
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

        addRemoveFileButtonEventListener();
    })
}

const addRemoveFileButtonEventListener = () => {
    const removeFileButtons = document.querySelectorAll('#remove_file_button');

    removeFileButtons.forEach(button => {
        button.addEventListener("click", function (event) {
            let currentAttachedFiles = Array.from(document.querySelector('.lst_thumb').children);
            let li = event.target.closest('li')

            removeFile(currentAttachedFiles.indexOf(li));
            button.closest("li").remove();
        })
    });
}

const isValidImageType = (image) => {
    return (['image/jpg', 'image/png', 'image/jpeg'].includes(String(image.type).toLocaleLowerCase()));
}

document.addEventListener("DOMContentLoaded", function () {
    addScoreStarsEventListener();
    addCommentTextareaEventListener();
    addfileUploaderEventListener();
    addCheckValidSubmitEventListener();
});