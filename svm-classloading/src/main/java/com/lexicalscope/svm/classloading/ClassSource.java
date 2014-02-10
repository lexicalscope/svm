package com.lexicalscope.svm.classloading;

import java.net.URL;

public interface ClassSource {
   URL loadFromRepository(String name);
}
