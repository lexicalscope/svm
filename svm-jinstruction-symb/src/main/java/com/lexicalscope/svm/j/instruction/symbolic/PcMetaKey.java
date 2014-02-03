package com.lexicalscope.svm.j.instruction.symbolic;

import com.lexicalscope.svm.j.instruction.symbolic.pc.Pc;
import com.lexicalscope.symb.vm.j.metastate.MetaKey;

public final class PcMetaKey implements MetaKey<Pc> {
   private PcMetaKey() {}
   public static final PcMetaKey PC = new PcMetaKey();

   @Override public Class<Pc> valueType() {
      return Pc.class;
   }

   @Override public int hashCode() {
      return Pc.class.hashCode();
   }

   @Override public boolean equals(final Object obj) {
      return obj != null && obj.getClass().equals(this.getClass());
   }
}
