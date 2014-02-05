package com.lexicalscope.svm.j.instruction.instrumentation;

import java.util.Map.Entry;

import org.hamcrest.Matcher;

import com.google.common.collect.ListMultimap;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class MultimapInstrumentationContext implements Instrumentation {
   private final ListMultimap<Matcher<? super SMethodDescriptor>, Instrumentor> instrumentation;
   private final InstructionSource instructions;

   public MultimapInstrumentationContext(
         final InstructionSource instructions,
         final ListMultimap<Matcher<? super SMethodDescriptor>, Instrumentor> instrumentation) {
      this.instructions = instructions;
      this.instrumentation = instrumentation;
   }

   @Override public Instruction instrument(final SMethodDescriptor method, final Instruction methodEntry) {
      Instruction replacementMethodEntry = methodEntry;
      for (final Entry<Matcher<? super SMethodDescriptor>, Instrumentor> entry : instrumentation.entries()) {
         if(entry.getKey().matches(method)) {
            replacementMethodEntry = entry.getValue().instrument(instructions, replacementMethodEntry);
         }
      }
      return replacementMethodEntry;
   }
}
