package com.elinext;

import com.elinext.injector.Injector;
import com.elinext.injector.impl.InjectorImpl;
import com.elinext.provider.Provider;
import com.elinext.test.dao.EventDAO;
import com.elinext.test.service.EventService;
import com.elinext.test.service.impl.EventServiceImpl;

public class Runner {
    public static void main(String[] args) {
        Injector i = new InjectorImpl();
        i.bind(EventService.class, EventServiceImpl.class);
        Provider<EventService> serviceProvider = i.getProvider(EventService.class);
        EventService instance = serviceProvider.getInstance();
        instance.someServiceThings();

//        EventService eventService = serviceProvider.getInstance();
//        eventService.someServiceThings();

    }
}
