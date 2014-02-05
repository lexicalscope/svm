package com.lexicalscope.svm.j.instruction.factory;


import static com.lexicalscope.svm.j.instruction.instrumentation.InstrumentationBuilder.instrumentation;
import static com.lexicalscope.symb.vm.j.InstructionCode.invokevirtual;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource.InstructionSink;
import com.lexicalscope.svm.j.instruction.instrumentation.Instrumentation;
import com.lexicalscope.svm.j.instruction.instrumentation.InstrumentationContext;
import com.lexicalscope.svm.j.instruction.instrumentation.InstrumentingInstructionSourceFactory;

public class TestInstrumentingInstructionSource {
   @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
   @Mock private InstructionSourceFactory delegateInstructionSourceFactory;
   @Mock private InstructionSource delegateInstructionSource;
   @Mock private InstructionSink sink;
   @Mock private Instrumentation instrumentation;

   @Test @Ignore public void uninstrumentedInstructionsAreDelegated() throws Exception {
      context.checking(new Expectations(){{
         allowing(delegateInstructionSourceFactory).instructionSource(null); will(returnValue(delegateInstructionSource));
         oneOf(delegateInstructionSource).fmul(sink);
      }});

      final InstructionSource instructionSource =
            new InstrumentingInstructionSourceFactory(delegateInstructionSourceFactory, instrumentation().map()).
               instructionSource(null);

      instructionSource.fmul(sink);
   }

   @Test @Ignore public void instrumentedInstructionsAreDelegated() throws Exception {
      context.checking(new Expectations(){{
         allowing(delegateInstructionSourceFactory).instructionSource(null); will(returnValue(delegateInstructionSource));

         oneOf(instrumentation).before(with(invokevirtual), with(isA(InstrumentationContext.class)), with(sink));
         oneOf(delegateInstructionSource).invokevirtual(null, sink); will(returnValue(delegateInstructionSource));
         oneOf(instrumentation).after(with(invokevirtual), with(isA(InstrumentationContext.class)), with(sink));
      }});

      final InstructionSource instructionSource =
            new InstrumentingInstructionSourceFactory(delegateInstructionSourceFactory, instrumentation().instrument(invokevirtual, instrumentation).map()).
               instructionSource(null);

      assertThat(instructionSource.invokevirtual(null, sink), sameInstance(instructionSource));
   }
}
