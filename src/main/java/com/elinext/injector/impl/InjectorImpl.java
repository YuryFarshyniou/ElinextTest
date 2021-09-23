package com.elinext.injector.impl;

import com.elinext.configurator.Configurator;
import com.elinext.configurator.impl.InjectAnnotationConfigurator;
import com.elinext.exceptions.BindingNotFoundException;
import com.elinext.injector.Injector;
import com.elinext.provider.Provider;
import com.elinext.provider.impl.ProviderImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class InjectorImpl implements Injector {
    private final Map<Class, Class> bindingInstance;
    private final Map<Class, Class> singletonInstances;
    private final Map<Class, Object> cache;
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
        if (singletonInstances.containsKey(type)) {
            if (cache.containsKey(type)) {
                classProvider = new ProviderImpl<>((T) cache.get(type));
                return classProvider;
            }
            T instance = (T) configurator.config(type);
            cache.put(type, instance);
            classProvider = new ProviderImpl<>(instance);
            return classProvider;

        } else if (bindingInstance.containsKey(type)) {
            Class aClass = bindingInstance.get(type);
            T instance = (T) configurator.config(aClass);
            classProvider = new ProviderImpl<>(instance);
            return classProvider;
        }
        classProvider = new ProviderImpl<>(null);
        return classProvider;
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        Class<?>[] interfaces = impl.getInterfaces();
        if (interfaces.length == 0 || intf != interfaces[0]) {
            throw new BindingNotFoundException();
        }
        bindingInstance.put(intf, impl);
    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
        Class<?>[] interfaces = impl.getInterfaces();
        if (interfaces.length == 0 || intf != interfaces[0]) {
            throw new BindingNotFoundException();
        }
        singletonInstances.put(intf, impl);
    }
}
