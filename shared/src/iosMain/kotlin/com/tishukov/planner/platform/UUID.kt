package com.tishukov.planner.platform

import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID().UUIDString()
