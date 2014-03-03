package com.lexicalscope.svm.vm.conc.junit;

import java.util.Collection;

import com.lexicalscope.svm.metastate.MetaKey;
import com.lexicalscope.svm.vm.Vm;
import com.lexicalscope.svm.vm.conc.LoadFrom;
import com.lexicalscope.svm.vm.j.JState;

public class VmWrap {
   private final VmRule vmRule;
   private Vm<JState> vm;
   private final LoadFrom annotation;

   public VmWrap(final VmRule vmRule, final LoadFrom annotation) {
      this.vmRule = vmRule;
      this.annotation = annotation;
   }

   public final JState execute(final Object ... args) {
      if(vm == null) {
         vmRule.loadFrom(annotation.value());
         vm = vmRule.build(args);
      }
      return vm.execute();
   }

   public JState result() {
      return vm.result();
   }

   public Collection<JState> results() {
      return vm.results();
   }

   public <T> T getMeta(final MetaKey<T> key) {
      return result().getMeta(key);
   }
}
