package com.lexicalscope.symb.z3;

import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.SymbolVisitor;
import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Z3Exception;

public class SymbolToExpr implements SymbolVisitor<Expr, Z3Exception> {
   private final Context ctx;

   public SymbolToExpr(final Context ctx) {
      this.ctx = ctx;
   }

   @Override
   public Expr add(final Symbol left, final Symbol right) throws Z3Exception {
      return ctx.mkAdd((ArithExpr) left.accept(this), (ArithExpr) right.accept(this));
   }

   @Override
   public Expr sub(final Symbol left, final Symbol right) throws Z3Exception {
      return ctx.mkSub((ArithExpr) left.accept(this), (ArithExpr) right.accept(this));
   }

   @Override
   public Expr mul(final Symbol left, final Symbol right) throws Z3Exception {
      return ctx.mkMul((ArithExpr) left.accept(this), (ArithExpr) right.accept(this));
   }

   @Override
   public Expr constant(final int val) throws Z3Exception {
      return ctx.mkInt(val);
   }

   @Override
   public Expr ge(final Symbol val) throws Z3Exception {
      return ctx.mkGe((ArithExpr) val.accept(this), ctx.mkInt(0));
   }

   @Override
   public Expr not(final Symbol val) throws Z3Exception {
      return ctx.mkNot((BoolExpr) val.accept(this));
   }

   @Override
   public Expr intSymbol(final int name) throws Z3Exception {
      return ctx.mkIntConst("i" + name);
   }
}
