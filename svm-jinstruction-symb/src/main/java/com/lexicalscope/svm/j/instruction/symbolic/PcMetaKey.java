package com.lexicalscope.svm.j.instruction.symbolic;

import com.lexicalscope.svm.j.instruction.symbolic.pc.Pc;
import com.lexicalscope.svm.metastate.MetaKey;

public final class PcMetaKey implements MetaKey<Pc> {
   public static final PcMetaKey PC = new PcMetaKey();

   private PcMetaKey() {}

   @Override public Class<Pc> valueType() {
      return Pc.class;
   }
}
