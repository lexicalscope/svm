package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.svm.vm.j.code.AsmSMethodName.method;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.lexicalscope.svm.classloading.AsmSClassLoader;
import com.lexicalscope.svm.classloading.SClassLoader;
import com.lexicalscope.svm.j.instruction.instrumentation.InstructionFinder;
import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.klass.SClass;
import com.lexicalscope.svm.vm.j.klass.SMethod;
import com.lexicalscope.svm.vm.j.queries.IsConstructorCall;
import com.lexicalscope.svm.vm.j.queries.IsNewInstruction;


public class TestCanFindConstructorCall {
   private final SClassLoader classLoader = new AsmSClassLoader();

   public static class ClassUnderConstruction {

   }

   public static class ClassCallingConstructor {
      public void method() {
         new ClassUnderConstruction();
      }
   }

   @Test public void constructorCallCanBeFound() {
      final SClass loaded = classLoader.load(ClassCallingConstructor.class);
      final SMethod method = loaded.virtualMethod(method(ClassCallingConstructor.class, "method", "()V"));
      final boolean foundConstructor = new FindConstructorCall().findInstruction(method);
      assertThat("found constructor", foundConstructor);
   }

   public static class FindConstructorCall implements InstructionFinder {
      private interface SearchState {
         void matchInstruction(Instruction instruction);
      }

      private class LookingForNew implements SearchState {
         @Override public void matchInstruction(final Instruction instruction) {
            if(instruction.query(new IsNewInstruction(ClassUnderConstruction.class)))
            {
               state = new LookingForConstructor();
            }
         }
      }

      private class LookingForConstructor implements SearchState {
         @Override public void matchInstruction(final Instruction instruction) {
            assert !instruction.query(new IsNewInstruction(ClassUnderConstruction.class)) :
               "found two new instructions with no constructor inbetween";

            if(instruction.query(new IsConstructorCall(ClassUnderConstruction.class)))
            {
               foundConstructor = true;
               state = new LookingForNew();
            }
            else
            {
               assert !instruction.query(new IsConstructorCall(Matchers.any(String.class))) :
                  "found the wrong constructor after a new instruction";
            }
         }
      }

      private SearchState state = new LookingForNew();
      boolean foundConstructor = false;

      @Override public boolean findInstruction(final SMethod method) {
         for (final Instruction instruction : method.entry()) {
            state.matchInstruction(instruction);
         }
         return foundConstructor;
      }
   }
}
