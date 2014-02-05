package com.lexicalscope.svm.j.instruction.instrumentation;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.lexicalscope.symb.vm.j.InstructionCode;

public class InstrumentationBuilder {
   private final ListMultimap<InstructionCode, Instrumentation> instrumentationMap = LinkedListMultimap.create();

   public static InstrumentationBuilder instrumentation() {
      return new InstrumentationBuilder();
   }

   public InstrumentationBuilder instrument(final InstructionCode instruction, final Instrumentation instrumentation) {
      instrumentationMap.put(instruction, instrumentation);
      return this;
   }

   public MultimapInstrumentationContext map() {
      return new MultimapInstrumentationContext(instrumentationMap);
   }

   public Instrumentation2 instrumentation2() {
      return new NullInstrumentation2();
   }
}
