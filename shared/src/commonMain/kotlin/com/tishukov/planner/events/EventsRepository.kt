package com.tishukov.planner.events

import com.tishukov.planner.events.data.SpendEventDao
import com.tishukov.planner.events.model.SpendEvent
import com.tishukov.planner.extensions.appLog
import kotlinx.coroutines.flow.flow

class EventsRepository(
    private val dao: SpendEventDao,
) {

    fun getAllFlow() = dao.getAllFlow()

    suspend fun create(spendEvent: SpendEvent) = dao.insert(spendEvent)
}
