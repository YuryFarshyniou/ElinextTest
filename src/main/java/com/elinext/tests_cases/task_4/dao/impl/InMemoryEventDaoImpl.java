package com.elinext.tests_cases.task_4.dao.impl;

import com.elinext.tests_cases.task_4.dao.EventDAO;

public class InMemoryEventDaoImpl implements EventDAO {
    @Override
    public void doSomeStuff() {
        System.out.println("Do some DAO's things");
    }
}
