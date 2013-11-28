package com.lexicalscope.symb.vm;

public interface Heap {
   ObjectRef newObject();
   Heap snapshot();
}