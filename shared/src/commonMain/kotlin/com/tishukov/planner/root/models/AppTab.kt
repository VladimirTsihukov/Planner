package com.tishukov.planner.root.models

sealed interface AppTab {
    data object Events : AppTab
    data object Categories : AppTab
    data object Settings : AppTab
}
