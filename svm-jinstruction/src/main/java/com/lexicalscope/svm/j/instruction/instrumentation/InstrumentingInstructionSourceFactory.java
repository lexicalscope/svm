package com.lexicalscope.svm.j.instruction.instrumentation;

import static com.lexicalscope.fluentreflection.dynamicproxy.FluentProxy.dynamicProxy;
import static org.hamcrest.Matchers.anything;

import java.util.Map;

import com.lexicalscope.fluentreflection.dynamicproxy.Implementing;
import com.lexicalscope.fluentreflection.dynamicproxy.MethodBody;
import com.lexicalscope.svm.j.instruction.factory.InstructionFactory;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource.InstructionSink;
import com.lexicalscope.svm.j.instruction.factory.InstructionSourceFactory;

public class InstrumentingInstructionSourceFactory implements InstructionSourceFactory {
   private final InstructionSourceFactory delegateFactory;
   private final Map<String, Instrumentation> map;

   public InstrumentingInstructionSourceFactory(final InstructionSourceFactory delegate, final Map<String, Instrumentation> map) {
      this.delegateFactory = delegate;
      this.map = map;
   }

   @Override public InstructionSource instructionSource(final InstructionFactory instructionFactory) {
      final InstructionSource delegate = delegateFactory.instructionSource(instructionFactory);
      return dynamicProxy(new Implementing<InstructionSource>(InstructionSource.class) {{
         whenProxying(anything()).execute(new MethodBody() {

            @Override public void body() throws Throwable {
               final String methodName = methodName();
               final Instrumentation instrumentation = map.get(methodName);
               if(instrumentation != null) {
                  instrumentation.before(arg(InstructionSink.class));
               }
               final Object result = method().rebind(delegate).call(args()).value();
               if(result == delegate) {
                  returnValue(proxy());
               } else {
                  returnValue(result);
               }
               if(instrumentation != null) {
                  instrumentation.after(arg(InstructionSink.class));
               }
            }
         });
      }});
   }
}