package com.tishukov.planner.platform

import java.util.UUID

actual fun randomUUID(): String = UUID.randomUUID().toString()
