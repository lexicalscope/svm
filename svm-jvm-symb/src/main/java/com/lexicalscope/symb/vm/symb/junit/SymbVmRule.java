package com.lexicalscope.symb.vm.symb.junit;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.annotatedWith;

import com.lexicalscope.fluentreflection.FluentField;
import com.lexicalscope.fluentreflection.FluentObject;
import com.lexicalscope.svm.j.instruction.symbolic.SymbInstructionFactory;
import com.lexicalscope.symb.vm.conc.junit.VmRule;
import com.lexicalscope.symb.vm.symb.SymbVmFactory;
import com.lexicalscope.symb.z3.FeasibilityChecker;

public class SymbVmRule extends VmRule {
   private final SymbInstructionFactory symbInstructionFactory;
   private final FeasibilityChecker feasibilityChecker;

   public SymbVmRule() {
      this(new FeasibilityChecker());
   }

   public SymbVmRule(final FeasibilityChecker feasibilityChecker) {
      this(new SymbInstructionFactory(feasibilityChecker), feasibilityChecker);
   }

   public SymbVmRule(final SymbInstructionFactory symbInstructionFactory, final FeasibilityChecker feasibilityChecker) {
      super(SymbVmFactory.symbolicVmBuilder(symbInstructionFactory));
      this.symbInstructionFactory = symbInstructionFactory;
      this.feasibilityChecker = feasibilityChecker;
   }

   @Override protected void configureTarget(final FluentObject<Object> object) {
      for (final FluentField field : object.fields(annotatedWith(Fresh.class))) {
         field.call(symbInstructionFactory.isymbol());
      }
   }

   @Override protected void cleanup() {
      feasibilityChecker.close();
   }
}
