package io.github.xxfast.nytimes.api

import io.github.xxfast.nytimes.core.models.Article
import io.github.xxfast.nytimes.core.models.ArticleUri
import io.github.xxfast.nytimes.core.models.TopStoryResponse
import io.github.xxfast.nytimes.core.models.TopStorySection
import io.github.xxfast.nytimes.utils.get
import io.ktor.client.HttpClient
import io.ktor.http.path

class NyTimesWebService(private val client: HttpClient) {
  suspend fun topStories(section: TopStorySection): Result<TopStoryResponse> = client
    .get { url { path("svc/topstories/v2/${section.name}.json") } }

  suspend fun story(section: TopStorySection, uri: ArticleUri): Result<Article?> = client
    .get<TopStoryResponse> { url { path("svc/topstories/v2/${section.name}.json") } }
    .map { response -> response.results.find { article -> article.uri == uri } }
}
