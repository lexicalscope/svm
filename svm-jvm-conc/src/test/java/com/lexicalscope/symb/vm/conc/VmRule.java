package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.annotatedWith;

import java.util.Collection;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.lexicalscope.fluentreflection.FluentObject;
import com.lexicalscope.symb.vm.FlowNode;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.j.State;

public class VmRule implements MethodRule, Vm<State> {
   private Vm<State> vm;

   @Override public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
      return new Statement() {

         @Override public void evaluate() throws Throwable {
            final FluentObject<Object> object = object(target);

            final MethodInfo entryPoint = object.
                  field(annotatedWith(TestEntryPoint.class)).call().as(MethodInfo.class);
            vm = VmFactory.concreteVm(entryPoint);
         }
      };
   }

   @Override public FlowNode<State> execute() {
      return vm.execute();
   }

   @Override public void initial(final FlowNode<State> state) {
      vm.initial(state);
   }

   @Override public void fork(final FlowNode<State>[] states) {
      vm.fork(states);
   }

   @Override public FlowNode<State> result() {
      return vm.result();
   }

   @Override public Collection<FlowNode<State>> results() {
      return vm.results();
   }

   @Override public FlowNode<State> pending() {
      return vm.pending();
   }
}
