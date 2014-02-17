package com.lexicalscope.svm.j.instruction.symbolic.pc;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ICmpEqSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ICmpLtSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IConstSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;

public class PcBuilder {
   public static ICmpEqSymbol icmpEq(final ISymbol value1, final ISymbol value2) {
      return new ICmpEqSymbol(value1, value2);
   }

   public static BoolSymbol icmplt(final ISymbol value1, final ISymbol value2) {
      return new ICmpLtSymbol(value1, value2);
   }

   public static ISymbol asISymbol(final Object symbolOrInt) {
      return symbolOrInt instanceof ISymbol ? (ISymbol) symbolOrInt : new IConstSymbol((int) symbolOrInt);
   }
}
