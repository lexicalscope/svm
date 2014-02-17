package com.lexicalscope.svm.vm.symb.junit;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.annotatedWith;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.lexicalscope.fluentreflection.FluentField;
import com.lexicalscope.fluentreflection.FluentObject;
import com.lexicalscope.svm.j.instruction.symbolic.SymbInstructionFactory;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.Symbol;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class SolverRule implements MethodRule {
   private final SymbInstructionFactory symbInstructionFactory;
   private final FeasibilityChecker feasibilityChecker;

   public SolverRule() {
      this(new FeasibilityChecker());
   }

   public SolverRule(
         final SymbInstructionFactory symbInstructionFactory,
         final FeasibilityChecker feasibilityChecker) {
      this.symbInstructionFactory = symbInstructionFactory;
      this.feasibilityChecker = feasibilityChecker;
   }

   public SolverRule(final FeasibilityChecker feasibilityChecker) {
      this(new SymbInstructionFactory(feasibilityChecker), feasibilityChecker);
   }

   public void createSymbols(final FluentObject<Object> object) {
      for (final FluentField field : object.fields(annotatedWith(Fresh.class))) {
         field.call(symbInstructionFactory.isymbol());
      }
   }

   public boolean equivalant(final Symbol left, final Symbol right) {
      return feasibilityChecker.equivalent(left, right);
   }

   @Override public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
      return new Statement() {
         @Override public void evaluate() throws Throwable {
            createSymbols(object(target));
            base.evaluate();
         }};
   }

   public FeasibilityChecker checker() {
      return feasibilityChecker;
   }
}
