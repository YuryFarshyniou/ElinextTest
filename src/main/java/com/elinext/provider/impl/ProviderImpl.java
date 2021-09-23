package com.elinext.provider.impl;

import com.elinext.provider.Provider;

import java.lang.reflect.InvocationTargetException;

public class ProviderImpl<T> implements Provider<T> {
    private T instance;

    public ProviderImpl(T entity) {
            instance = entity;
    }

    @Override
    public T getInstance() {
        return instance;
    }

}
