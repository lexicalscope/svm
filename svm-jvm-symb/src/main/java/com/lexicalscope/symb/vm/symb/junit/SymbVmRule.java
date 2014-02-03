package com.lexicalscope.symb.vm.symb.junit;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.annotatedWith;

import com.lexicalscope.fluentreflection.FluentField;
import com.lexicalscope.fluentreflection.FluentObject;
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

   @Override protected void configureTarget(final FluentObject<Object> object) {
      for (final FluentField field : object.fields(annotatedWith(Fresh.class))) {
         field.call(symbInstructionFactory.isymbol());
      }
   }
}
