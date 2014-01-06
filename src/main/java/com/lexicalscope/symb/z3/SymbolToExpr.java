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

   @Override public Expr and(final ISymbol left, final ISymbol right) throws Z3Exception {
      return ctx.mkBVAND((BitVecExpr) left.accept(this), (BitVecExpr) right.accept(this));
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
   public BoolExpr ge(final ISymbol val) throws Z3Exception {
      return ctx.mkBVSGE((BitVecExpr) val.accept(this), constant(0));
   }

   @Override public BoolExpr ge(final ISymbol value1, final ISymbol value2) throws Z3Exception {
      return ctx.mkBVSGE((BitVecExpr) value1.accept(this), (BitVecExpr) value2.accept(this));
   }

   @Override public BoolExpr lt(final ISymbol value1, final ISymbol value2) throws Z3Exception {
      return ctx.mkBVSLT((BitVecExpr) value1.accept(this), (BitVecExpr) value2.accept(this));
   }

   @Override public BoolExpr gt(final ISymbol value1, final ISymbol value2) throws Z3Exception {
      return ctx.mkBVSGT((BitVecExpr) value1.accept(this), (BitVecExpr) value2.accept(this));
   }

   @Override public BoolExpr le(final ISymbol value1, final ISymbol value2) throws Z3Exception {
      return ctx.mkBVSLE((BitVecExpr) value1.accept(this), (BitVecExpr) value2.accept(this));
   }

   @Override public BoolExpr ne(final ISymbol value1, final ISymbol value2) throws Z3Exception {
      return ctx.mkNot(eq(value1, value2));
   }

   @Override public BoolExpr eq(final ISymbol value1, final ISymbol value2) throws Z3Exception {
      return ctx.mkEq(value1.accept(this), value2.accept(this));
   }

   @Override
   public Expr not(final ISymbol val) throws Z3Exception {
      return ctx.mkNot((BoolExpr) val.accept(this));
   }

   @Override
   public Expr intSymbol(final int name) throws Z3Exception {
      return ctx.mkBVConst("i" + name, 32);
   }

   @Override public BoolExpr tru3() throws Z3Exception {
      return ctx.mkTrue();
   }

   @Override public BoolExpr fals3() throws Z3Exception {
      return ctx.mkFalse();
   }

   @Override public Expr simplified(final Object simplification) throws Z3Exception {
      return (BitVecExpr) simplification;
   }
}
