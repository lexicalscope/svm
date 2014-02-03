package com.lexicalscope.symb.vm.conc.junit;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.annotatedWith;
import static org.objectweb.asm.Type.getMethodDescriptor;

import java.util.Collection;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.lexicalscope.fluentreflection.FluentAnnotated;
import com.lexicalscope.fluentreflection.FluentMethod;
import com.lexicalscope.fluentreflection.FluentObject;
import com.lexicalscope.fluentreflection.ReflectionMatcher;
import com.lexicalscope.symb.vm.FlowNode;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.conc.JvmBuilder;
import com.lexicalscope.symb.vm.conc.MethodInfo;
import com.lexicalscope.symb.vm.j.State;

public class VmRule implements MethodRule {
   private final ReflectionMatcher<FluentAnnotated> annotatedWithTestPointEntry = annotatedWith(TestEntryPoint.class);
   private final JvmBuilder jvmBuilder;
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
            findEntryPoint(object);
            configureTarget(object);
            base.evaluate();
            cleanup();
         }

         public void findEntryPoint(final FluentObject<Object> object) {
            if(!object.fields(annotatedWithTestPointEntry).isEmpty()) {
               entryPoint = object.field(annotatedWithTestPointEntry).call().as(MethodInfo.class);
            } else {
               final FluentMethod entryPointMethod =
                     object.reflectedClass().method(annotatedWithTestPointEntry);
               entryPoint = new MethodInfo(
                     object.classUnderReflection(),
                     entryPointMethod.name(),
                     getMethodDescriptor(entryPointMethod.member()));
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
