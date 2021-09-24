package com.elinext.scanner.impl;

import com.elinext.scanner.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ScannerImpl implements Scanner {
    private List<Class> classes;

    public ScannerImpl() {
        this.classes = new ArrayList<>();
    }

    @Override
    public void scan(String packageName, Class classForScan) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try {
            while (reader.ready()) {
                String s = reader.readLine();
                if (!s.endsWith(".class")) {
                    scan(packageName + "." + s, classForScan);
                } else {
                    Class aClass = getClass(s, packageName);
                    Class[] interfaces = aClass.getInterfaces();
                    if (interfaces.length > 0 && interfaces[0] == classForScan) {
                        classes.add(aClass);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Problems with scanning packages." + e);
        }
    }

    @Override
    public List<Class> getClasses() {
        return classes;
    }

    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            System.err.println("Class with name " + className + " wasn't found! " + e);
        }
        return null;
    }
}
