package com.lexicalscope.symb.z3;

import java.util.List;

import com.lexicalscope.symb.vm.symbinstructions.PcVisitor;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;

public class PcToZ3 implements PcVisitor<BoolExpr, Z3Exception> {
   private final Context ctx;

   public PcToZ3(final Context ctx) {
      this.ctx = ctx;
   }

   @Override
   public BoolExpr conjunction(final List<ISymbol> conjunction) throws Z3Exception {
      final BoolExpr[] exprs = new BoolExpr[conjunction.size()];
      for (int i = 0; i < conjunction.size(); i++) {
            exprs[i] = (BoolExpr) conjunction.get(i).accept(new SymbolToExpr(ctx));
      }
      return ctx.mkAnd(exprs);
   }
}
