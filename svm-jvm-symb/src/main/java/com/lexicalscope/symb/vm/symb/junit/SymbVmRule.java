package com.lexicalscope.symb.vm.symb.junit;

import com.lexicalscope.svm.j.instruction.symbolic.SymbInstructionFactory;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.symb.vm.conc.junit.VmRule;
import com.lexicalscope.symb.vm.symb.SymbVmFactory;

public class SymbVmRule extends VmRule {
   private final SymbInstructionFactory symbInstructionFactory;

   public SymbVmRule() {
      this(new SymbInstructionFactory());
   }
   public SymbVmRule(final SymbInstructionFactory symbInstructionFactory) {
      super(SymbVmFactory.symbolicVmBuilder(symbInstructionFactory));
      this.symbInstructionFactory = symbInstructionFactory;
   }

   public ISymbol isymbol() {
      return symbInstructionFactory.isymbol();
   }
}
