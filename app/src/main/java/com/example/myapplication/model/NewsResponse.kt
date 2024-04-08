package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val articles: List<NewsArticle>,
    val status: String // ok
) {
    data class NewsArticle(
        val author: String?, // Alex Wilhelm
        val content: String?, // Hello and welcome back to our regular morning look at private companies, public markets and the gray space in between.Today we’re exploring some fascinating data from Silicon Valley Bank markets report for Q1 2020. We’re digging into two charts that deal wi… [+648 chars]
        val description: String, // Hello and welcome back to our regular morning look at private companies, public markets and the gray space in between. Today we’re exploring some fascinating data from Silicon Valley Bank markets report for Q1 2020. We’re digging into two charts that deal wit…
        val publishedAt: String, // 2020-02-10T17:06:42Z
        val source: Source,
        val title: String, // Is this what an early-stage slowdown looks like?
        val url: String, // http://techcrunch.com/2020/02/10/is-this-what-an-early-stage-slowdown-looks-like/
        val urlToImage: String // https://techcrunch.com/wp-content/uploads/2020/02/GettyImages-dv1637047.jpg?w=556
    ) {
        data class Source(
            val id: String?, // techcrunch
            val name: String // TechCrunch
        )
    }
}