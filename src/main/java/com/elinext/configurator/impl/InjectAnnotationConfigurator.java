package com.elinext.configurator.impl;

import com.elinext.annotation.Inject;
import com.elinext.configurator.Configurator;
import com.elinext.exceptions.BindingNotFoundException;
import com.elinext.exceptions.ConstructorNotFoundException;
import com.elinext.exceptions.TooManyConstructorsException;
import com.elinext.scanner.Scanner;
import com.elinext.scanner.impl.ScannerImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InjectAnnotationConfigurator implements Configurator {
    private Scanner scanner;
    private int countForConstructorsWithInject;
    private Object instance;
    boolean isAnnotationInjectIsPresent;
    boolean isDefaultConstructorIsPresent;

    public InjectAnnotationConfigurator() {
        countForConstructorsWithInject = 0;
    }

    @Override
    public Object config(Class classForInject) {
        countForConstructorsWithInject = 0;
        Constructor[] constructors = classForInject.getConstructors();
        actionIfAnnotationInjectIsPresent(constructors, classForInject);
        if (!isAnnotationInjectIsPresent) {
            actionIfThereIsOnlyDefaultConstructor(classForInject, constructors);
        }
        if (!isAnnotationInjectIsPresent && !isDefaultConstructorIsPresent) {
            throw new ConstructorNotFoundException("There are no any suitable constructor!");
        }
        return instance;
    }

    private void actionIfAnnotationInjectIsPresent(Constructor[] constructors, Class instance) {
        for (Constructor constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                throwExceptionIfConstWithInjectMoreThanOne();
                List<Class> classes = getClassesFromScanner(constructor);
                Object[] values = getArrayForConstructor(classes);
                this.instance = createObject(constructor, values, instance);
                isAnnotationInjectIsPresent = true;
            }
        }
    }

    private void throwExceptionIfConstWithInjectMoreThanOne() {
        if (++countForConstructorsWithInject > 1) {
            throw new TooManyConstructorsException("Too many constructors with Inject annotation.");
        }
    }

    private List<Class> getClassesFromScanner(Constructor constructor) {
        this.scanner = new ScannerImpl();
        Class[] parameterTypes = constructor.getParameterTypes();
        for (Class parameterType : parameterTypes) {
            scanner.scan("com.elinext", parameterType);
        }
        List<Class> classes = scanner.getClasses();
        if (classes.size() != parameterTypes.length) {
            throw new BindingNotFoundException("Problem with binding!");
        }
        return classes;
    }

    private Object[] getArrayForConstructor(List<Class> classes) {
        Object[] values = new Object[classes.size()];
        for (int i = 0; i < values.length; i++) {
            Class aClass = classes.get(i);
            try {
                Object o1 = aClass.getDeclaredConstructor().newInstance();
                values[i] = o1;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                System.err.println("Something wrong in getArrayForConstructor method!" + e);
            }
        }
        return values;
    }

    private Object createObject(Constructor constructor, Object[] values, Class instance) {
        Object object = new Object();
        try {
            Optional<StackTraceElement> setSingletonBindingIsPresent = getOptionalWithMethod();
            if (setSingletonBindingIsPresent.isPresent()) {
                object = Proxy.newProxyInstance(instance.getClassLoader(), instance.getInterfaces(), (proxy, method, args) -> {
                    Object o = constructor.newInstance(values);
                    return method.invoke(o);
                });
            } else {
                object = constructor.newInstance(values);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("Something wrong in createObject method!" + e);
        }
        return object;
    }

    private Optional<StackTraceElement> getOptionalWithMethod() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return Arrays.stream(stackTrace)
                .filter(stackTraceElement -> stackTraceElement.getMethodName().equals("setSingletonBinding"))
                .findFirst();
    }


    private void actionIfThereIsOnlyDefaultConstructor(Class classForInject, Constructor[] constructors) {
        for (Constructor constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                createInstance(classForInject);
                Field[] declaredFields = classForInject.getDeclaredFields();
                List<Class> classes = getListOfFields(declaredFields);
                setFields(declaredFields, classes);
            }
        }
    }

    private void createInstance(Class classForInject) {
        try {
            instance = classForInject.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.err.println("Some problem with creating instance with default constructor!");
        }
    }

    private List<Class> getListOfFields(Field[] fields) {
        this.scanner = new ScannerImpl();
        for (Field declaredField : fields) {
            scanner.scan("com.elinext", declaredField.getType());
        }
        List<Class> classes = scanner.getClasses();
        if (classes.size() != fields.length) {
            throw new BindingNotFoundException("Problem with binding!");
        }
        return classes;
    }

    private void setFields(Field[] declaredFields, List<Class> classes) {
        for (int i = 0; i < declaredFields.length; i++) {
            try {
                declaredFields[i].setAccessible(true);
                declaredFields[i].set(instance, classes.get(i).getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                System.err.println("Something wrong with setting instance to the field in default constructor!" + e);
            }
        }
        isDefaultConstructorIsPresent = true;
    }
}
