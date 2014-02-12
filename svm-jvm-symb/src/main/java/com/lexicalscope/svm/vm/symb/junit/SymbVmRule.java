package com.lexicalscope.svm.vm.symb.junit;

import com.lexicalscope.fluentreflection.FluentObject;
import com.lexicalscope.svm.j.instruction.symbolic.SymbInstructionFactory;
import com.lexicalscope.svm.vm.conc.junit.VmRule;
import com.lexicalscope.svm.vm.symb.SymbVmFactory;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class SymbVmRule extends VmRule {
   private final FeasibilityChecker feasibilityChecker;
   private final SolverRule solverRule;

   public SymbVmRule() {
      this(new FeasibilityChecker());
   }

   public SymbVmRule(final FeasibilityChecker feasibilityChecker) {
      this(new SymbInstructionFactory(feasibilityChecker), feasibilityChecker);
   }

   public SymbVmRule(final SymbInstructionFactory symbInstructionFactory, final FeasibilityChecker feasibilityChecker) {
      super(SymbVmFactory.symbolicVmBuilder(symbInstructionFactory, feasibilityChecker));
      this.feasibilityChecker = feasibilityChecker;
      this.solverRule = new SolverRule(symbInstructionFactory, feasibilityChecker);
   }

   @Override protected void configureTarget(final FluentObject<Object> object) {
      solverRule.createSymbols(object);
   }

   @Override protected void cleanup() {
      feasibilityChecker.close();
   }

   public FeasibilityChecker feasbilityChecker() {
      return feasibilityChecker;
   }

   public SolverRule solver() {
      return solverRule;
   }
}
