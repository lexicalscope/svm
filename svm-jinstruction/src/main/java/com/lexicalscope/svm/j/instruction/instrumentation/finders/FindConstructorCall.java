package com.lexicalscope.svm.j.instruction.instrumentation.finders;

import org.hamcrest.Matchers;

import com.lexicalscope.svm.j.instruction.instrumentation.InstructionFinder;
import com.lexicalscope.svm.j.instruction.instrumentation.InstructionInstrumentor;
import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.klass.SMethod;
import com.lexicalscope.svm.vm.j.queries.IsConstructorCall;
import com.lexicalscope.svm.vm.j.queries.IsNewInstruction;

public class FindConstructorCall implements InstructionFinder {
   private interface SearchState {
      void matchInstruction(Instruction instruction);
   }

   private class LookingForNew implements FindConstructorCall.SearchState {
      @Override public void matchInstruction(final Instruction instruction) {
         if(instruction.query(new IsNewInstruction(klass)))
         {
            state = new LookingForConstructor();
         }
      }
   }

   private class LookingForConstructor implements FindConstructorCall.SearchState {
      @Override public void matchInstruction(final Instruction instruction) {
         assert !instruction.query(new IsNewInstruction(klass)) :
            "found two new instructions with no constructor inbetween";

         if(instruction.query(new IsConstructorCall(klass)))
         {
            instrumentor.candidate(instruction);
            state = new LookingForNew();
         }
         else
         {
            assert !instruction.query(new IsConstructorCall(Matchers.any(String.class))) :
               "found the wrong constructor after a new instruction";
         }
      }
   }

   private FindConstructorCall.SearchState state = new LookingForNew();
   private final InstructionInstrumentor instrumentor;
   private final Class<?> klass;

   public FindConstructorCall(final Class<?> klass, final InstructionInstrumentor instrumentor) {
      this.klass = klass;
      this.instrumentor = instrumentor;
   }

   @Override public void findInstruction(final SMethod method) {
      for (final Instruction instruction : method.entry()) {
         state.matchInstruction(instruction);
      }
   }
}