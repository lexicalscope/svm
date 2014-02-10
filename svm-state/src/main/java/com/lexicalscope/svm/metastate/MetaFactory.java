package com.lexicalscope.svm.metastate;


public interface MetaFactory<T> {
   T replacement(T original);
}
