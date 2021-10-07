
const likeBtn = document.getElementById('like-box')
likeBtn.addEventListener("click", handlingLikeBtn());

function handlingLikeBtn(){
    let postId =
    fetch("/like/{postId}")
}
