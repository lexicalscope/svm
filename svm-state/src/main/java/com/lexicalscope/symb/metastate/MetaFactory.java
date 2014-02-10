package com.lexicalscope.symb.metastate;


public interface MetaFactory<T> {
   T replacement(T original);
}
