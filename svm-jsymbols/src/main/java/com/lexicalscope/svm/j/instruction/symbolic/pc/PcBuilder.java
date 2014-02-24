package com.lexicalscope.svm.j.instruction.symbolic.pc;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.AndSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ICmpEqSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ICmpGeSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ICmpGtSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ICmpLeSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ICmpLtSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IConstSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.NotSymbol;

public class PcBuilder {
   public static ICmpEqSymbol icmpEq(final ISymbol value1, final ISymbol value2) {
      return new ICmpEqSymbol(value1, value2);
   }

   public static BoolSymbol icmplt(final ISymbol value1, final ISymbol value2) {
      return new ICmpLtSymbol(value1, value2);
   }

   public static BoolSymbol invert(final BoolSymbol value1) {
      return new NotSymbol(value1);
   }

   public static BoolSymbol icmpgt(final ISymbol value1, final ISymbol value2) {
      return new ICmpGtSymbol(value1, value2);
   }

   public static BoolSymbol icmpge(final ISymbol value1, final ISymbol value2) {
      return new ICmpGeSymbol(value1, value2);
   }

   public static BoolSymbol icmple(final ISymbol value1, final ISymbol value2) {
      return new ICmpLeSymbol(value1, value2);
   }

   public static ISymbol asISymbol(final Object symbolOrInt) {
      return symbolOrInt instanceof ISymbol ? (ISymbol) symbolOrInt : new IConstSymbol((int) symbolOrInt);
   }

   public static BoolSymbol and(final BoolSymbol left, final BoolSymbol right) {
      return new AndSymbol(left, right);
   }
}
