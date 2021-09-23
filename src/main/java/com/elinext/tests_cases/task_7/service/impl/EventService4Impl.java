package com.elinext.tests_cases.task_7.service.impl;

import com.elinext.annotation.Inject;
import com.elinext.tests_cases.task_7.dao.EventDao4;
import com.elinext.tests_cases.task_7.service.EventService4;

public class EventService4Impl implements EventService4 {
    private EventDao4 eventDao;

    @Inject
    public EventService4Impl(EventDao4 eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public void doSomeThings() {
        System.out.println("Do some things!");
    }
}
