package com.elinext.test.service.impl;

import com.elinext.annotation.Inject;
import com.elinext.test.dao.EventDAO;
import com.elinext.test.dao.NAo;
import com.elinext.test.service.EventService;

public class EventServiceImpl implements EventService {
    private EventDAO eventDAO;
    private NAo nAo;
    @Inject
    public EventServiceImpl(EventDAO eventDAO, NAo nao) {
        this.eventDAO = eventDAO;
        this.nAo = nao;
    }
    public EventServiceImpl( ) {

    }

    @Override
    public void someServiceThings() {
        System.out.println("Some service's things!");
        eventDAO.doSomeStuff();
        nAo.Some();
    }

}
