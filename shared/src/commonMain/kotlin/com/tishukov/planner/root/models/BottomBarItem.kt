package com.tishukov.planner.root.models

import dev.icerock.moko.resources.ImageResource
import com.tishukov.planner.MR
import dev.icerock.moko.resources.StringResource

data class BottomBarItem(
    val title: StringResource,
    val appTab: AppTab,
    val icon: ImageResource,
) {

    companion object {
        fun getItems() = listOf(
            BottomBarItem(MR.strings.events, AppTab.Events, MR.images.ic_calendar_24dp),
            BottomBarItem(MR.strings.categories, AppTab.Categories, MR.images.ic_folder_24dp),
            BottomBarItem(MR.strings.settings, AppTab.Settings, MR.images.ic_settings_24dp),
        )
    }
}
