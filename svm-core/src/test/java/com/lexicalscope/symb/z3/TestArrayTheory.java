package com.lexicalscope.symb.z3;

import static com.lexicalscope.symb.vm.symbinstructions.PcBuilder.icmpEq;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.junit.junitautocloseable.AutoCloseRule;
import com.lexicalscope.symb.vm.symbinstructions.Pc;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IArraySymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ITerminalSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.StateBuilder;

public class TestArrayTheory {
   @Rule public AutoCloseRule autoCloseRule = new AutoCloseRule();
   private final FeasibilityChecker feasbilityChecker = new FeasibilityChecker();

   private final StateBuilder state = new StateBuilder();
   private final Pc pc = new Pc();

   @Test public void storeAndSelectAtSymbolicIndex() throws Exception {
      final ITerminalSymbol indexSymbol = state.intSymbol();
      final IConstSymbol valueSymbol = state.intConst(7);
      final IArraySymbol storedArraySymbol = state.arrayStore(state.iarrayZeroed(), indexSymbol, valueSymbol);
      pc.and(icmpEq(valueSymbol, state.arraySelect(storedArraySymbol, indexSymbol)));

      assertThat(pc, feasbilityChecker);
   }

   @Test public void storeAndSelectAtPossiblyDifferentSymbolicIndex() throws Exception {
      final ITerminalSymbol storeIndexSymbol = state.intSymbol();
      final ITerminalSymbol selectIndexSymbol = state.intSymbol();
      final IConstSymbol valueSymbol = state.intConst(7);
      final IArraySymbol storedArraySymbol = state.arrayStore(state.iarrayZeroed(), storeIndexSymbol, valueSymbol);
      pc.and(icmpEq(valueSymbol, state.arraySelect(storedArraySymbol, selectIndexSymbol)));

      assertThat(pc, feasbilityChecker);
   }

   @Test public void storeAndSelectAtDefinitelyDifferentSymbolicIndex() throws Exception {
      final ITerminalSymbol storeIndexSymbol = state.intSymbol();
      final ISymbol selectIndexSymbol = state.iadd(storeIndexSymbol, state.intConst(1));
      final IConstSymbol valueSymbol = state.intConst(7);
      final IArraySymbol storedArraySymbol = state.arrayStore(state.iarrayZeroed(), storeIndexSymbol, valueSymbol);
      pc.and(icmpEq(valueSymbol, state.arraySelect(storedArraySymbol, selectIndexSymbol)));

      assertThat(pc, not(feasbilityChecker));
   }
}
