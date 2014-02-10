package com.lexicalscope.svm.vm.conc.junit;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.annotatedWith;
import static org.objectweb.asm.Type.getMethodDescriptor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.lexicalscope.fluentreflection.FluentAnnotated;
import com.lexicalscope.fluentreflection.FluentMethod;
import com.lexicalscope.fluentreflection.FluentObject;
import com.lexicalscope.fluentreflection.FluentReflection;
import com.lexicalscope.fluentreflection.ReflectionMatcher;
import com.lexicalscope.svm.vm.FlowNode;
import com.lexicalscope.svm.vm.Vm;
import com.lexicalscope.svm.vm.conc.JvmBuilder;
import com.lexicalscope.svm.vm.conc.MethodInfo;
import com.lexicalscope.svm.vm.j.State;

public class VmRule implements MethodRule {
   private final ReflectionMatcher<FluentAnnotated> annotatedWithTestPointEntry = annotatedWith(TestEntryPoint.class);
   private final JvmBuilder jvmBuilder;
   private final Map<String, MethodInfo> entryPoints = new HashMap<>();
   private MethodInfo entryPoint;
   private Vm<State> vm;

   public VmRule() {
      this(new JvmBuilder());
   }

   public VmRule(final JvmBuilder jvmBuilder) {
      this.jvmBuilder = jvmBuilder;
   }

   @Override public final Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
      return new Statement() {

         @Override public void evaluate() throws Throwable {
            final FluentObject<Object> object = object(target);
            findEntryPoints(object);
            findEntryPoint(object);
            configureTarget(object);

            base.evaluate();
            cleanup();
         }

         private void findEntryPoint(final FluentObject<Object> object) {
            final FluentMethod targetMethod = FluentReflection.method(method.getMethod());
            if(targetMethod.annotatedWith(WithEntryPoint.class)) {
               final String requiredEntryPoint = targetMethod.annotation(WithEntryPoint.class).value();
               entryPoint = entryPoints.get(requiredEntryPoint);
            } else if(entryPoints.size() == 1) {
               entryPoint = entryPoints.values().iterator().next();
            }
            if(entryPoint == null) {
               throw new AssertionError("no entry point found");
            }
         }

         public void findEntryPoints(final FluentObject<Object> object) {
            for (final FluentMethod entryPointMethod : object.reflectedClass().methods(annotatedWithTestPointEntry)) {
               entryPoints.put(entryPointMethod.name(), new MethodInfo(
                     object.classUnderReflection(),
                     entryPointMethod.name(),
                     getMethodDescriptor(entryPointMethod.member())));
            }
         }
      };
   }

   protected void cleanup() {
      // can be overridden
   }

   protected void configureTarget(final FluentObject<Object> object) {
      // can be overridden
   }

   public final FlowNode<State> execute(final Object ... args) {
      vm = jvmBuilder.build(entryPoint, args);
      return vm.execute();
   }

   public final FlowNode<State> result() {
      return vm.result();
   }

   public final Collection<FlowNode<State>> results() {
      return vm.results();
   }
}
