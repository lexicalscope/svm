package com.lexicalscope.svm.j.instruction.instrumentation;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class TraceMethodCallOp implements Vop {
   @Override public void eval(final State ctx) {
      System.out.println("FOOOOOO");
   }
}
