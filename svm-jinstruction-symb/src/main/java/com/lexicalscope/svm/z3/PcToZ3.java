package com.lexicalscope.svm.z3;

import com.lexicalscope.svm.j.instruction.symbolic.pc.PcVisitor;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;

public class PcToZ3 implements PcVisitor<BoolExpr, Z3Exception> {
   private final Context ctx;

   public PcToZ3(final Context ctx) {
      this.ctx = ctx;
   }

   @Override
   public BoolExpr symbol(final BoolSymbol conjunction) throws Z3Exception {
      return (BoolExpr) conjunction.accept(new SymbolToExpr(ctx));
   }
}
