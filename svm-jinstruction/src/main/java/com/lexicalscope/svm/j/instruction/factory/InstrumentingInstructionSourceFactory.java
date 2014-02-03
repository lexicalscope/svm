package com.lexicalscope.svm.j.instruction.factory;

import static com.lexicalscope.fluentreflection.dynamicproxy.FluentProxy.dynamicProxy;
import static org.hamcrest.Matchers.anything;

import java.util.Map;

import com.lexicalscope.fluentreflection.dynamicproxy.Implementing;
import com.lexicalscope.fluentreflection.dynamicproxy.MethodBody;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource.InstructionSink;

public class InstrumentingInstructionSourceFactory implements InstructionSourceFactory {
   private final InstructionSourceFactory delegate;
   private final Map<String, Instrumentation> map;

   public InstrumentingInstructionSourceFactory(final InstructionSourceFactory delegate, final Map<String, Instrumentation> map) {
      this.delegate = delegate;
      this.map = map;
   }

   @Override public InstructionSource instructionSource(final InstructionFactory instructionFactory) {
      final InstructionSource instructionSource = delegate.instructionSource(instructionFactory);
      return dynamicProxy(new Implementing<InstructionSource>(InstructionSource.class) {{
         whenProxying(anything()).execute(new MethodBody() {

            @Override public void body() throws Throwable {
               final Instrumentation instrumentation = map.get(methodName());
               if(instrumentation != null) {
                  instrumentation.before(arg(InstructionSink.class));
               }
               returnValue(method().rebind(instructionSource).call(args()).value());
               if(instrumentation != null) {
                  instrumentation.after(arg(InstructionSink.class));
               }
            }
         });
      }});
   }
}