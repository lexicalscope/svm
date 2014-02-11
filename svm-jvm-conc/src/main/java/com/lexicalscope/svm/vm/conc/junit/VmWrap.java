package com.lexicalscope.svm.vm.conc.junit;

import com.lexicalscope.svm.metastate.MetaKey;
import com.lexicalscope.svm.vm.FlowNode;
import com.lexicalscope.svm.vm.Vm;
import com.lexicalscope.svm.vm.conc.LoadFrom;
import com.lexicalscope.svm.vm.j.State;

public class VmWrap {
   private final VmRule vmRule;
   private Vm<State> vm;
   private final LoadFrom annotation;

   public VmWrap(final VmRule vmRule, final LoadFrom annotation) {
      this.vmRule = vmRule;
      this.annotation = annotation;
   }

   public final FlowNode<State> execute(final Object ... args) {
      if(vm == null) {
         vmRule.builder().loadFrom(annotation.value());
         vm = vmRule.build(args);
      }
      return vm.execute();
   }

   public FlowNode<State> result() {
      return vm.result();
   }

   public <T> T getMeta(final MetaKey<T> key) {
      return result().state().getMeta(key);
   }
}
