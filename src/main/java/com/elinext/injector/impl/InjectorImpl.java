package com.elinext.injector.impl;

import com.elinext.configurator.Configurator;
import com.elinext.configurator.impl.InjectAnnotationConfigurator;
import com.elinext.exceptions.BindingNotFoundException;
import com.elinext.injector.Injector;
import com.elinext.provider.Provider;
import com.elinext.provider.impl.ProviderImpl;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class InjectorImpl implements Injector {
    private final Map<String, Class> bindingInstance;
    private final Map<String, Class> singletonInstances;
    private final Map<String, Object> cache;
    private final Configurator configurator;

    public InjectorImpl() {
        this.configurator = new InjectAnnotationConfigurator();
        this.bindingInstance = new ConcurrentHashMap<>();
        this.singletonInstances = new ConcurrentHashMap<>();
        this.cache = new ConcurrentHashMap<>();
    }

    @Override
    public <T> Provider<T> getProvider(Class<T> type) {
        Provider<T> classProvider;
        if (singletonInstances.containsKey(type.getName())) {
            if (cache.containsKey(type.getName())) {
                classProvider = new ProviderImpl<>((T) cache.get(type.getName()));
                return classProvider;
            }
            classProvider = setSingletonBinding(type);
            return classProvider;

        } else if (bindingInstance.containsKey(type.getName())) {
            classProvider = setBinding(type);
            return classProvider;
        }
        classProvider = new ProviderImpl<>(null);
        return classProvider;
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        checkIfBindingSuccess(intf, impl);
        bindingInstance.put(intf.getName(), impl);
    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
        checkIfBindingSuccess(intf, impl);
        singletonInstances.put(intf.getName(), impl);
    }

    private <T> Provider setSingletonBinding(Class type) {
        Class aClass = singletonInstances.get(type.getName());
//        Proxy.newProxyInstance()
//        T instance = (T)
        configurator.config(aClass);
//        cache.put(type.getName(), instance);
//        return new ProviderImpl<>(instance);
        return null;
    }


    private <T> Provider setBinding(Class type) {
        Class aClass = bindingInstance.get(type.getName());
        T instance = (T) configurator.config(aClass);
        return new ProviderImpl<>(instance);
    }

    private <T> void checkIfBindingSuccess(Class<T> intf, Class<? extends T> impl) {
        Class<?>[] interfaces = impl.getInterfaces();
        if (interfaces.length == 0 || intf != interfaces[0]) {
            throw new BindingNotFoundException();
        }
    }
}
