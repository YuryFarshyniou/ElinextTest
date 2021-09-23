package com.elinext.tests_cases.task_4.service.impl;

import com.elinext.annotation.Inject;
import com.elinext.tests_cases.task_4.dao.EventDAO;
import com.elinext.tests_cases.task_4.service.EventService1;
import com.elinext.tests_cases.task_4.utils.Utils;

public class EventService1Impl implements EventService1 {
    private EventDAO eventDAO;
    private Utils utils;

    @Inject
    public EventService1Impl(EventDAO eventDAO, Utils utils) {
        this.eventDAO = eventDAO;
        this.utils = utils;
    }

    @Inject
    public EventService1Impl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public void someServiceThings() {
        System.out.println("Some service's things!");
        eventDAO.doSomeStuff();
        utils.doSomeThings();
    }

}
