package com.lexicalscope.svm.j.instruction;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class NoOp implements Vop {
   @Override public void eval(final State ctx) {

   }

   @Override public String toString() {
      return "NOOP";
   }
}