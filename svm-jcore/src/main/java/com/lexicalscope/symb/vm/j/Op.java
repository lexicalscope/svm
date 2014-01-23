package com.lexicalscope.symb.vm.j;



public interface Op<T> {
   T eval(State ctx);
}
