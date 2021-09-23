package com.elinext.tests_cases.task_6.service.impl;

import com.elinext.annotation.Inject;
import com.elinext.tests_cases.task_6.SomeInterface;
import com.elinext.tests_cases.task_6.dao.EventDao3;
import com.elinext.tests_cases.task_6.service.EventService3;

public class EventService3Impl implements EventService3 {
    private EventDao3 eventDao3;
    private SomeInterface someInterface;

    @Inject
    public EventService3Impl(EventDao3 eventDao3, SomeInterface someInterface) {
        this.eventDao3 = eventDao3;
        this.someInterface = someInterface;
    }

    @Override
    public void doSomeServiceThings() {
        System.out.println("Do some service things!");
    }
}
