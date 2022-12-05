package com.androidapp.newsclientappcleanarchitecture.core.data

import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleGateway
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository(  private val remoteService: ArticleRemoteService): ArticleGateway {
    override fun fetchNewsArticles(category: String): MutableList<ArticleDetails> {
        lateinit var result :  MutableList<ArticleDetails>
        val call: Call<ArticleListRaw> = if (category == "All") {
            remoteService.getAllNews(Constants.API_KEY, Constants.COUNTRY)
        } else {
            remoteService.getNewsByCategory(Constants.COUNTRY, category, Constants.API_KEY)
        }
        call.enqueue(object : Callback<ArticleListRaw> {
            override fun onResponse(call: Call<ArticleListRaw>, response: Response<ArticleListRaw>) {
                if (response.isSuccessful) {
                      result = response.body()?.listArticles!!

                }
            }
            override fun onFailure(call: Call<ArticleListRaw>, t: Throwable) {
            }
        })
        return result
    }

    override fun fetchCategories(): MutableList<String> {
        return CategoryRaw.values().map { it.name } as MutableList<String>
    }

   override fun fetchMockData(): List<ArticleDetails> {
        return listOf(
            ArticleDetails("title", "author",
                "date", "description",
                "https://www.google.com/search?q=image&rlz=1C5CHFA_enPH1030PH1030&oq=image&aqs=chrome..69i57j0i131i433i512l2j0i433i512j69i61j69i60j69i65j69i60.2131j0j7&sourceid=chrome&ie=UTF-8#imgrc=JoR7JNzGko0S6M",
                "jndsaksjd", "shkjsnjds"),
            ArticleDetails("title", "author",
                "date", "description",
                "https://www.google.com/search?q=image&rlz=1C5CHFA_enPH1030PH1030&oq=image&aqs=chrome..69i57j0i131i433i512l2j0i433i512j69i61j69i60j69i65j69i60.2131j0j7&sourceid=chrome&ie=UTF-8#imgrc=JoR7JNzGko0S6M",
                "jndsaksjd", "shkjsnjds"),
            ArticleDetails("title", "author",
                "date", "description",
                "https://www.google.com/search?q=image&rlz=1C5CHFA_enPH1030PH1030&oq=image&aqs=chrome..69i57j0i131i433i512l2j0i433i512j69i61j69i60j69i65j69i60.2131j0j7&sourceid=chrome&ie=UTF-8#imgrc=JoR7JNzGko0S6M",
                "jndsaksjd", "shkjsnjds"),
            ArticleDetails("title", "author",
                "date", "description",
                "https://www.google.com/search?q=image&rlz=1C5CHFA_enPH1030PH1030&oq=image&aqs=chrome..69i57j0i131i433i512l2j0i433i512j69i61j69i60j69i65j69i60.2131j0j7&sourceid=chrome&ie=UTF-8#imgrc=JoR7JNzGko0S6M",
                "jndsaksjd", "shkjsnjds"),
        )
    }

}