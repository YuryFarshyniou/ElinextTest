package com.elinext.test.dao.impl;

import com.elinext.test.dao.EventDAO;

public class InMemoryEventDaoImpl implements EventDAO {
    @Override
    public void doSomeStuff() {
        System.out.println("Do some DAO's things");
    }
}
