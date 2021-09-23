package com.elinext.scanner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Scanner {
    void scan(String packageName, Class classForScan) ;

    List<Class> getClasses();
}
