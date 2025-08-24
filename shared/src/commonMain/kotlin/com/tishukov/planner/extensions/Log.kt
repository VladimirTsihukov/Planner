package com.tishukov.planner.extensions

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun appLog(message: String) {
    Napier.d("Error message: $message")
}

fun initLog() = Napier.base(DebugAntilog())
