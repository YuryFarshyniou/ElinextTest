package com.elinext.tests_cases.example_test.dao.impl;

import com.elinext.tests_cases.example_test.dao.EventDAO;

public class InMemoryEventDaoImpl implements EventDAO {
    @Override
    public void doSomeStuff() {
        System.out.println("Do some DAO's things");
    }
}
