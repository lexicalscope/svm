package com.lexicalscope.symb.z3;

import com.lexicalscope.symb.vm.symbinstructions.Pc;
import com.lexicalscope.symb.vm.symbinstructions.symbols.BoolSymbol;
import com.microsoft.z3.ApplyResult;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Goal;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.Tactic;
import com.microsoft.z3.Z3Exception;

public class Simplifier {
   private final Context ctx;

   public Simplifier(final Context ctx) {
      this.ctx = ctx;
   }

   public SModel powerSimplify(final Pc pc, final BoolSymbol symbol) {
      try {
         return new SModel(ctx, powerSimplify(pc.accept(new PcToZ3(ctx)), (BoolExpr) symbol.accept(new SymbolToExpr(ctx))));
      } catch (final Z3Exception e) {
         throw new RuntimeException("unable to create expressions", e);
      }
   }

   private Model powerSimplify(final BoolExpr ... exprToSolve) {
      try {
         final Solver solver = ctx.mkSolver();
         try {
            final Tactic simplify = ctx.mkTactic("simplify");
            final Tactic solveEquations = ctx.mkTactic("solve-eqs");
            final Tactic bitBlast = ctx.mkTactic("bit-blast");
            final Tactic propositional = ctx.mkTactic("sat");

            final Tactic tactic = ctx.parAndThen(simplify, ctx.parAndThen(solveEquations, ctx.parAndThen(bitBlast, propositional)));

            final Goal goal = ctx.mkGoal(true, false, false);
            goal.add(exprToSolve);

            final ApplyResult resultOfApply = tactic.apply(goal);

            for (final BoolExpr subGoalFormula : resultOfApply.getSubgoals()[0].getFormulas()) {
               solver.add(subGoalFormula);
            }

            final Status status = solver.check();
            if(!status.equals(Status.SATISFIABLE)) {
               throw new RuntimeException("unable to find model for " + exprToSolve);
            }

            return resultOfApply.convertModel(0, solver.getModel());
         } finally {
            solver.dispose();
         }
      } catch (final Z3Exception e) {
         throw new RuntimeException("unable to check satisfiablility", e);
      }
   }
}
