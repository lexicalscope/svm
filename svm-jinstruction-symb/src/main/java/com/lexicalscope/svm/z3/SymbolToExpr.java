package com.lexicalscope.svm.z3;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IArraySymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.SymbolVisitor;
import com.microsoft.z3.ArrayExpr;
import com.microsoft.z3.BitVecExpr;
import com.microsoft.z3.BitVecSort;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Z3Exception;

public class SymbolToExpr implements SymbolVisitor<Expr, Z3Exception> {
   private static final int intWidth = 32;
   private final Context ctx;
   private final BitVecSort intBvSort;
   private final BitVecExpr bv0;
   private final ArrayExpr bva0;

   public SymbolToExpr(final Context ctx) throws Z3Exception {
      this.ctx = ctx;
      this.bv0 = constant(0);
      this.intBvSort = ctx.mkBitVecSort(intWidth);
      this.bva0 = ctx.mkConstArray(intBvSort, bv0);
   }

   @Override
   public BitVecExpr add(final ISymbol left, final ISymbol right) throws Z3Exception {
      return ctx.mkBVAdd((BitVecExpr) left.accept(this), (BitVecExpr) right.accept(this));
   }

   @Override public BitVecExpr and(final ISymbol left, final ISymbol right) throws Z3Exception {
      return ctx.mkBVAND((BitVecExpr) left.accept(this), (BitVecExpr) right.accept(this));
   }

   @Override public BitVecExpr sub(final ISymbol left, final ISymbol right) throws Z3Exception {
      return ctx.mkBVSub((BitVecExpr) left.accept(this), (BitVecExpr) right.accept(this));
   }

   @Override
   public BitVecExpr mul(final ISymbol left, final ISymbol right) throws Z3Exception {
      return ctx.mkBVMul((BitVecExpr) left.accept(this), (BitVecExpr) right.accept(this));
   }

   @Override
   public BitVecExpr constant(final int val) throws Z3Exception {
      return ctx.mkBV(val, intWidth);
   }

   @Override
   public BoolExpr ge(final ISymbol val) throws Z3Exception {
      return ctx.mkBVSGE((BitVecExpr) val.accept(this), constant(0));
   }

   @Override public BoolExpr ge(final ISymbol value1, final ISymbol value2) throws Z3Exception {
      return ctx.mkBVSGE((BitVecExpr) value1.accept(this), (BitVecExpr) value2.accept(this));
   }

   @Override public Expr lt(final ISymbol val) throws Z3Exception {
      return ctx.mkBVSLT((BitVecExpr) val.accept(this), constant(0));
   }

   @Override public BoolExpr lt(final ISymbol value1, final ISymbol value2) throws Z3Exception {
      return ctx.mkBVSLT((BitVecExpr) value1.accept(this), (BitVecExpr) value2.accept(this));
   }

   @Override public Expr gt(final ISymbol val) throws Z3Exception {
      return ctx.mkBVSGT((BitVecExpr) val.accept(this), constant(0));
   }

   @Override public BoolExpr gt(final ISymbol value1, final ISymbol value2) throws Z3Exception {
      return ctx.mkBVSGT((BitVecExpr) value1.accept(this), (BitVecExpr) value2.accept(this));
   }

   @Override public Expr le(final ISymbol val) throws Z3Exception {
      return ctx.mkBVSLE((BitVecExpr) val.accept(this), constant(0));
   }

   @Override public BoolExpr le(final ISymbol value1, final ISymbol value2) throws Z3Exception {
      return ctx.mkBVSLE((BitVecExpr) value1.accept(this), (BitVecExpr) value2.accept(this));
   }

   @Override public Expr ne(final ISymbol val) throws Z3Exception {
      return ctx.mkNot(ctx.mkEq(val.accept(this), constant(0)));
   }

   @Override public BoolExpr ne(final ISymbol value1, final ISymbol value2) throws Z3Exception {
      return ctx.mkNot(eq(value1, value2));
   }

   @Override public Expr eq(final ISymbol val) throws Z3Exception {
      return ctx.mkEq(val.accept(this), constant(0));
   }

   @Override public BoolExpr eq(final ISymbol value1, final ISymbol value2) throws Z3Exception {
      return ctx.mkEq(value1.accept(this), value2.accept(this));
   }

   @Override
   public Expr not(final BoolSymbol val) throws Z3Exception {
      return ctx.mkNot((BoolExpr) val.accept(this));
   }

   @Override
   public Expr intSymbol(final String name) throws Z3Exception {
      return ctx.mkConst(name, intBvSort);
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

   @Override public ArrayExpr intArrayZeroed() throws Z3Exception {
      return bva0;
   }

   @Override public ArrayExpr intArraySymbol(final int name) throws Z3Exception {
      return ctx.mkArrayConst("ia" + name, intBvSort, intBvSort);
   }

   @Override public ArrayExpr iarrayStore(final IArraySymbol arraySymbol, final ISymbol indexSymbol, final ISymbol valueSymbol) throws Z3Exception {
      return ctx.mkStore((ArrayExpr) arraySymbol.accept(this), (BitVecExpr) indexSymbol.accept(this), (BitVecExpr) valueSymbol.accept(this));
   }

   @Override public Expr iarraySelect(final IArraySymbol arraySymbol, final ISymbol indexSymbol) throws Z3Exception {
      return ctx.mkSelect((ArrayExpr) arraySymbol.accept(this), indexSymbol.accept(this));
   }
}
