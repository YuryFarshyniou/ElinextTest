package com.elinext.tests_cases.task_5.part_1.service.impl;

import com.elinext.tests_cases.task_5.part_1.dao.EventDao2;
import com.elinext.tests_cases.task_5.part_1.service.EventService2;

public class EventService2Impl implements EventService2 {
    private EventDao2 eventDao;

    public EventService2Impl() {
    }

    @Override
    public void someServiceThings() {
        System.out.println("Do some things!!");
    }
}
