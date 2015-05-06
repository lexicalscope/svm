package com.lexicalscope.svm.partition.trace.symb;

import static com.lexicalscope.svm.partition.spec.MatchersSpec.*;
import static org.objectweb.asm.Type.getInternalName;

import java.util.Collection;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import com.lexicalscope.svm.examples.ExamplesOneMarker;
import com.lexicalscope.svm.examples.ExamplesTwoMarker;
import com.lexicalscope.svm.examples.router.broken.ExampleServing;
import com.lexicalscope.svm.examples.router.broken.Router;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ITerminalSymbol;
import com.lexicalscope.svm.partition.spec.CallContext;
import com.lexicalscope.svm.partition.trace.PartitionInstrumentation;
import com.lexicalscope.svm.partition.trace.symb.tree.GuidedStateSearchFactory;
import com.lexicalscope.svm.vm.conc.StateSearchFactory;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.symb.junit.SymbVmRule;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class TestRouterInequivalence {
   //@Rule
   public SymbVmRule vm;

   public ISymbol symbol;

   @Before public void setUp() {
      symbol = new ITerminalSymbol("s");
      final FeasibilityChecker feasibilityChecker = new FeasibilityChecker();
      final StateSearchFactory factory = new GuidedStateSearchFactory(feasibilityChecker);
      vm = SymbVmRule.createSymbVmRule(feasibilityChecker, factory);
      vm.entryPoint(ExampleServing.class, "main", "(I)V");
      vm.loadFrom(new Class[] { ExamplesOneMarker.class, ExamplesTwoMarker.class });
      PartitionInstrumentation.instrumentPartition(changedRouter(), unchangedEntry(), vm);
   }

   public Matcher<? super CallContext> changedRouter() {
      return receiver(klassIn(getInternalName(Router.class)));
   }

   public Matcher<? super CallContext> unchangedEntry() {
      return receiver(klassIn(getInternalName(ExampleServing.class)));
   }

   @Test public void testInequivalence() {
      vm.execute(symbol);
      System.out.printf("Got %d traces.\n", vm.results().size());

      final Collection<JState> results = vm.results();
      for (final JState jState : results) {
         System.out.println(jState);
      }
   }
}
