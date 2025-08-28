package com.tishukov.planner.settings.child.auth.child.sing_in.model

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val identifier: String,
    val password: String,
)
