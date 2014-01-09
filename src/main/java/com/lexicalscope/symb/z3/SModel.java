package com.lexicalscope.symb.z3;

import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ITerminalSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;
import com.microsoft.z3.BitVecNum;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Model;
import com.microsoft.z3.Z3Exception;

public class SModel {
   private final Model solvedModel;
   private final Context ctx;

   public SModel(final Context ctx, final Model solvedModel) {
      this.ctx = ctx;
      this.solvedModel = solvedModel;
   }

   public Symbol interp(final ITerminalSymbol symbol) {
      try {
         final Expr interpretation = solvedModel.getConstInterp(symbol.accept(new SymbolToExpr(ctx)));
         if(interpretation == null) {
            return null;
         } else if(interpretation instanceof BitVecNum) {
            return new IConstSymbol((int) ((BitVecNum) interpretation).getLong());
         }
         return new SimplifiedSymbol(interpretation);
      } catch (final Z3Exception e) {
         throw new RuntimeException("unable to check satisfiablility", e);
      }
   }
}
