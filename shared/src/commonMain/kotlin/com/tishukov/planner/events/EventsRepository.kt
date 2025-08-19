package com.tishukov.planner.events

import com.tishukov.planner.events.model.SpendEvent
import com.tishukov.planner.extensions.appLog
import kotlinx.coroutines.flow.flow

class EventsRepository {

    fun getAllFlow() = flow { emit(SpendEvent.getStubs()) }

    fun create(spendEvent: SpendEvent) = appLog("create event $spendEvent")
}
