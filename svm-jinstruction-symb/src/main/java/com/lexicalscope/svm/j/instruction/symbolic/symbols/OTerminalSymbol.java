package com.lexicalscope.svm.j.instruction.symbolic.symbols;


public class OTerminalSymbol implements OSymbol {
   private final String klassName;
   private final String name;

   public OTerminalSymbol(final int name, final String klassName) {
      this(("o" + name).intern(), klassName);
   }

   public OTerminalSymbol(final String name, final String klassName) {
      this.name = name;
      this.klassName = klassName;
   }

   public String klass() {
      return klassName;
   }

   @Override
   public String toString() {
      return String.format("#%s", name);
   }

   @Override
   public int hashCode() {
      return name.hashCode();
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj != null && obj.getClass().equals(this.getClass())) {
         final OTerminalSymbol that = (OTerminalSymbol) obj;
         final boolean equal = this.name.equals(that.name);
         assert !equal || this.klassName.equals(that.klassName);
         return equal;
      }
      return false;
   }
}
