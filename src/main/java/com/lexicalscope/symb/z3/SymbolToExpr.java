package com.lexicalscope.symb.z3;

import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.SymbolVisitor;
import com.microsoft.z3.BitVecExpr;
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
   public BitVecExpr add(final ISymbol left, final ISymbol right) throws Z3Exception {
      return ctx.mkBVAdd((BitVecExpr) left.accept(this), (BitVecExpr) right.accept(this));
   }

   @Override
   public BitVecExpr sub(final ISymbol left, final ISymbol right) throws Z3Exception {
      return ctx.mkBVSub((BitVecExpr) left.accept(this), (BitVecExpr) right.accept(this));
   }

   @Override
   public BitVecExpr mul(final ISymbol left, final ISymbol right) throws Z3Exception {
      return ctx.mkBVMul((BitVecExpr) left.accept(this), (BitVecExpr) right.accept(this));
   }

   @Override
   public BitVecExpr constant(final int val) throws Z3Exception {
      return ctx.mkBV(val, 32);
   }

   @Override
   public Expr ge(final ISymbol val) throws Z3Exception {
      return ctx.mkBVSGE((BitVecExpr) val.accept(this), constant(0));
   }


   @Override public Expr ge(final ISymbol value1, final ISymbol value2) throws Z3Exception {
      return ctx.mkBVSGE((BitVecExpr) value1.accept(this), (BitVecExpr) value2.accept(this));
   }

   @Override
   public Expr not(final ISymbol val) throws Z3Exception {
      return ctx.mkNot((BoolExpr) val.accept(this));
   }

   @Override
   public Expr intSymbol(final int name) throws Z3Exception {
      return ctx.mkBVConst("i" + name, 32);
   }

   @Override public Expr tru3() throws Z3Exception {
      return ctx.mkTrue();
   }

   @Override public Expr fals3() throws Z3Exception {
      return ctx.mkFalse();
   }
}
