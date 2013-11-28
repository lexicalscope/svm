package com.lexicalscope.symb.z3;

import java.util.HashMap;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Log;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.Version;
import com.microsoft.z3.Z3Exception;

public class FeasbilityChecker {
   private final Context ctx;

   public FeasbilityChecker() throws Z3Exception {
      Context.ToggleWarningMessages(true);
      Log.open("test.log");

      System.out.print("Z3 Major Version: ");
      System.out.println(Version.getMajor());
      System.out.print("Z3 Full Version: ");
      System.out.println(Version.getString());

      final HashMap<String, String> cfg = new HashMap<String, String>();
      cfg.put("model", "true");
      ctx = new Context(cfg);
   }

   public boolean findModelExample2() throws Z3Exception  {
      final IntExpr x = ctx.mkIntConst("x");
      final IntExpr y = ctx.mkIntConst("y");
      final IntExpr one = ctx.mkInt(1);
      final IntExpr two = ctx.mkInt(2);

      final ArithExpr y_plus_one = ctx.mkAdd(y, one);

      final BoolExpr c1 = ctx.mkLt(x, y_plus_one);
      final BoolExpr c2 = ctx.mkGt(x, two);

      final BoolExpr q = ctx.mkAnd(c1, c2);

      return check(ctx, q);
   }

   private boolean check(final Context ctx, final BoolExpr expr) throws Z3Exception {
      final Solver s = ctx.mkSolver();
      s.add(expr);
      return s.check().equals(Status.SATISFIABLE);
   }
}
