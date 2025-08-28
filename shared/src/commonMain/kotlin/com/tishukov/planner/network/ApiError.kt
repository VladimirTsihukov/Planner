package com.tishukov.planner.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorWrapper(
    val error: ApiError?
)

@Serializable
data class ApiError(
    val status: Int?,
    val name: String?,
    val message: String?,
)
