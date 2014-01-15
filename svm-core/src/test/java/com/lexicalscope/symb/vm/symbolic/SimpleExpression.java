package com.lexicalscope.symb.vm.symbolic;

public interface SimpleExpression {
   int type();

   int left();
   int right();
}