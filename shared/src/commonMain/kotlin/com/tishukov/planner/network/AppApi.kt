package com.tishukov.planner.network

import com.tishukov.planner.categories.models.CategoryApi
import com.tishukov.planner.events.model.SpendEventApi
import com.tishukov.planner.settings.child.auth.child.reqister.model.RegisterRequest
import com.tishukov.planner.settings.child.auth.child.sing_in.model.SignInRequest
import com.tishukov.planner.storage.SettingsManager
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AppApi(
    private val httpClient: HttpClient,
    private val settings: SettingsManager,
) {

    suspend fun register(req: RegisterRequest) =
        httpClient.post("${settings.serverUrl}/api/auth/local/register") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }

    suspend fun singIn(req: SignInRequest) =
        httpClient.post("${settings.serverUrl}/api/auth/local") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }

    suspend fun syncCategories(categories: List<CategoryApi>) =
        httpClient.post("${settings.serverUrl}/api/categories/sync") {
            contentType(ContentType.Application.Json)
            bearerAuth(settings.token)
            setBody(categories)
        }

    suspend fun syncEvents(events: List<SpendEventApi>) =
        httpClient.post("${settings.serverUrl}/api/spend-events/sync") {
            contentType(ContentType.Application.Json)
            bearerAuth(settings.token)
            setBody(events)
        }
}
