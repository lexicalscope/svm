package com.lexicalscope.symb.vm.symbinstructions;

import com.lexicalscope.symb.vm.symbinstructions.symbols.ICmpEqSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class PcBuilder {
   public static ICmpEqSymbol icmpEq(final ISymbol value1, final ISymbol value2) {
      return new ICmpEqSymbol(value1, value2);
   }

   public static ISymbol asISymbol(final Object symbolOrInt) {
      return symbolOrInt instanceof ISymbol ? (ISymbol) symbolOrInt : new IConstSymbol((int) symbolOrInt);
   }
}
