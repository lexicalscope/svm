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
import com.lexicalscope.symb.vm.conc.MethodInfo;
import com.lexicalscope.symb.vm.conc.VmFactory;
import com.lexicalscope.symb.vm.j.State;

public class VmRule implements MethodRule {
   private final ReflectionMatcher<FluentAnnotated> annotatedWithTestPointEntry = annotatedWith(TestEntryPoint.class);
   private MethodInfo entryPoint;
   private Vm<State> vm;

   @Override public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
      return new Statement() {

         @Override public void evaluate() throws Throwable {
            final FluentObject<Object> object = object(target);

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
            base.evaluate();
         }
      };
   }

   public FlowNode<State> execute(final Object ... args) {
      vm = VmFactory.concreteVm(entryPoint, args);
      return vm.execute();
   }

   public FlowNode<State> result() {
      return vm.result();
   }

   public Collection<FlowNode<State>> results() {
      return vm.results();
   }
}
