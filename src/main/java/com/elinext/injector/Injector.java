package com.elinext.injector;

import com.elinext.provider.Provider;

public interface Injector {
    <T> Provider<T> getProvider(Class<T> type);  // Получение инстанса класса со всеми инъекциями по классу интерфейса.

    <T> void bind(Class<T> intf, Class<? extends T> impl); //регистрация байдинга по классу интерфейса и его реализации.

    <T> void bindSingleton(Class<T> intf, Class<? extends T> impl); // Регистрация синглетон класса.
}
