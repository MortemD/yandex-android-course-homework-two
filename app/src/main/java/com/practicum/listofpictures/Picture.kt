package com.practicum.listofpictures

class Picture (
    val id: Int,
    val author: String,
    val url: String
)

fun getNewImage(): Picture {
    val url = urls[kotlin.random.Random.nextInt(0, urls.size)]
    val id = kotlin.random.Random.nextInt(100, 999)
    val newImage= Picture (
        id,
        "Автор ${id}",
        url)
    return newImage
}

val urls = listOf(
    "https://live.staticflickr.com/2922/14537972755_734c710783_b.jpg",
    "https://i.pinimg.com/originals/a3/a3/9b/a3a39b5f89e4a4879bd048b7592ef5f9.jpg",
    "https://cdn.ebaumsworld.com/mediaFiles/picture/604025/87568196.jpg",
    "https://avatars.mds.yandex.net/i?id=8d72d329adf83ef215246da932ed457d_l-5311593-images-thumbs&n=13",
    "https://i.pinimg.com/originals/85/6a/3b/856a3bf8d2057a8718e2b021515d542a.jpg",
    "https://i.pinimg.com/originals/2b/f5/ec/2bf5ec8dbacb6a2c36d1df99be90dab6.jpg",
    "https://i.pinimg.com/originals/db/31/eb/db31eb787bccbbd5562c65963af01cf4.jpg",
    "https://pastvu.com/_p/a/9/w/n/9wnkxqjmhz8gsq4pol.jpg",
    "https://i.pinimg.com/originals/d1/07/8f/d1078feb4cd61553da898274100b0f45.jpg"
)

fun generateSamplePictures(): List<Picture> {
    return urls.mapIndexed { index, url ->
        Picture(
            id = (index + 1).toInt(),
            author = "Author ${index + 1}",
            url = url
        )
    }
}