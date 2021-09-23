package com.elinext.tests_cases.example_test.service.impl;

import com.elinext.annotation.Inject;
import com.elinext.tests_cases.example_test.dao.EventDAO;
import com.elinext.tests_cases.example_test.service.EventService;

public class EventServiceImpl implements EventService {
    private EventDAO eventDAO;

    @Inject
    public EventServiceImpl(EventDAO eventDAO) {
        System.out.println("Created!");
        this.eventDAO = eventDAO;
    }


    @Override
    public void someServiceThings() {
        System.out.println("Some service's things!");
        eventDAO.doSomeStuff();
    }

}
