package com.lexicalscope.svm.j.instruction.instrumentation;

import java.util.HashMap;
import java.util.Map;

public class InstrumentationBuilder {
   private final Map<InstructionCode, Instrumentation> instrumentationMap = new HashMap<>();

   public static InstrumentationBuilder instrumentation() {
      return new InstrumentationBuilder();
   }

   public InstrumentationBuilder instrument(final InstructionCode instruction, final Instrumentation instrumentation) {
      instrumentationMap.put(instruction, instrumentation);
      return this;
   }

   public Map<InstructionCode, Instrumentation> map() {
      return instrumentationMap;
   }
}
