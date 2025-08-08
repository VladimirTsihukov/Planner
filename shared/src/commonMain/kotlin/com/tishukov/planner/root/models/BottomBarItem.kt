package com.tishukov.planner.root.models

data class BottomBarItem(
    val title: String,
    val appTab: AppTab,
) {

    companion object {
        fun getItems() = listOf(
            BottomBarItem("Events", AppTab.Events),
            BottomBarItem("Categories", AppTab.Categories),
            BottomBarItem("Settings", AppTab.Settings),
        )
    }
}
