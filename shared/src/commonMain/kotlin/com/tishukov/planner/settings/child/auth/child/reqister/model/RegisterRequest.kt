package com.tishukov.planner.settings.child.auth.child.reqister.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
)
