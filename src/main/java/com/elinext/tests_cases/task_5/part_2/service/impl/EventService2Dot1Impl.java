package com.elinext.tests_cases.task_5.part_2.service.impl;

import com.elinext.tests_cases.task_5.part_2.dao.EventDao2Dot1;
import com.elinext.tests_cases.task_5.part_2.service.EventService2Dot1;

public class EventService2Dot1Impl implements EventService2Dot1 {
    private EventDao2Dot1 eventDao;

    public EventService2Dot1Impl(String s) {
    }

    @Override
    public void doSomeThings() {
        System.out.println("Do some Service things!");
    }
}
