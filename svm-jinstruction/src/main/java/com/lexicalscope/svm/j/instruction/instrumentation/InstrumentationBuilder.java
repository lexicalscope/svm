package com.lexicalscope.svm.j.instruction.instrumentation;

import java.util.HashMap;
import java.util.Map;

public class InstrumentationBuilder {
   private final Map<String, Instrumentation> instrumentationMap = new HashMap<>();

   public static InstrumentationBuilder instrumentation() {
      return new InstrumentationBuilder();
   }

   public InstrumentationBuilder instrument(final String string, final Instrumentation instrumentation) {
      instrumentationMap.put(string, instrumentation);
      return this;
   }

   public Map<String, Instrumentation> map() {
      return instrumentationMap;
   }

   public static Instrumentation traceMethodCalls() {
      return new TraceMethodCalls();
   }
}
