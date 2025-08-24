package com.tishukov.planner.settings.child.auth.child.reqister.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val jwt: String?,
)
