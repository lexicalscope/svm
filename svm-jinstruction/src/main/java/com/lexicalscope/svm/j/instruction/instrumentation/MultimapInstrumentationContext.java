package com.lexicalscope.svm.j.instruction.instrumentation;

import com.google.common.collect.ListMultimap;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource.InstructionSink;

public class MultimapInstrumentationContext implements InstrumentationContext {
   private final ListMultimap<InstructionCode, Instrumentation> instrumentation;

   public MultimapInstrumentationContext(final ListMultimap<InstructionCode, Instrumentation> instrumentationMap) {
      instrumentation = instrumentationMap;
   }

   @Override
   public void before(final InstructionCode code, final InstructionSink sink) {
      for(final Instrumentation instrument : instrumentation.get(code)) {
         instrument.before(code, this, sink);
      }
   }

   @Override
   public void after(final InstructionCode code, final InstructionSink sink) {
      for(final Instrumentation instrument : instrumentation.get(code)) {
         instrument.after(code, this, sink);
      }
   }
}
