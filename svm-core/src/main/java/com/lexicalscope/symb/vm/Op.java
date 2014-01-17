package com.lexicalscope.symb.vm;



public interface Op<T> {
   T eval(State ctx);
}
