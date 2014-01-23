package com.lexicalscope.symb.classloading;

import java.net.URL;

public interface ClassSource {
   URL loadFromRepository(String name);
}
