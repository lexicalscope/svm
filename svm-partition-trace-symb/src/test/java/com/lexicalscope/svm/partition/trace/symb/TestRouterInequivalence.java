package com.lexicalscope.svm.partition.trace.symb;

import static com.lexicalscope.svm.partition.spec.MatchersSpec.*;
import static com.lexicalscope.svm.search.SideMatchers.*;
import static com.lexicalscope.svm.vm.j.StateMatchers.*;
import static com.lexicalscope.svm.vm.j.code.AsmSMethodName.*;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.objectweb.asm.Type.getInternalName;

import java.util.Collection;

import org.hamcrest.Matcher;
import org.hamcrest.core.CombinableMatcher;
import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.auto.Auto;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.examples.ExamplesOneMarker;
import com.lexicalscope.svm.examples.ExamplesTwoMarker;
import com.lexicalscope.svm.examples.router.working.ExampleServing;
import com.lexicalscope.svm.examples.router.working.Router;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ITerminalSymbol;
import com.lexicalscope.svm.partition.spec.CallContext;
import com.lexicalscope.svm.partition.trace.PartitionInstrumentation;
import com.lexicalscope.svm.partition.trace.symb.tree.GuidedStateSearchFactory;
import com.lexicalscope.svm.search.ConstantRandomiser;
import com.lexicalscope.svm.search.GuidedSearchObserver;
import com.lexicalscope.svm.vm.conc.StateSearchFactory;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.symb.junit.SymbVmRule;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class TestRouterInequivalence {
   //@Rule
   public SymbVmRule vm;

   public ISymbol symbol;

   @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
   @Mock public GuidedSearchObserver searchObserver;
   @Auto public Sequence searchSequence;

   @Before public void setUp() {
      symbol = new ITerminalSymbol("s");
      final FeasibilityChecker feasibilityChecker = new FeasibilityChecker();
      final StateSearchFactory factory = new GuidedStateSearchFactory(searchObserver, feasibilityChecker, new ConstantRandomiser(0));
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

   final Matcher<JState> routerConstructorEntry = currentMethodIs(defaultConstructor(Router.class));
   final CombinableMatcher<JState> routerConstructorExit = both(routerConstructorEntry).and(returnVoid());
   final Matcher<JState> serveMethod = currentMethodIs(method(ExampleServing.class, "serve", "(I)V"));
   final Matcher<JState> matchRouter = currentMethodIs(method(Router.class, "matchRoute", "(Ljava/lang/String;)Ljava/lang/String;"));
   final CombinableMatcher<JState> matchRouterExit = both(matchRouter).and(returnOne());

   @Test public void testInequivalence() {
      context.checking(new Expectations(){{
         oneOf(searchObserver).picked(with(entryPoint()), with(pSide())); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(routerConstructorEntry)); inSequence(searchSequence);
         oneOf(searchObserver).picked(with(entryPoint()), with(qSide())); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(routerConstructorEntry)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(routerConstructorEntry), with(pSide())); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(routerConstructorExit)); inSequence(searchSequence);
         oneOf(searchObserver).picked(with(routerConstructorEntry), with(qSide())); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(routerConstructorExit)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(routerConstructorExit), with(pSide())); inSequence(searchSequence);
         oneOf(searchObserver).forkAt(with(serveMethod(15))); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(routerConstructorExit), with(qSide())); inSequence(searchSequence);
         oneOf(searchObserver).forkAt(with(serveMethod(15))); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(17)), with(pSide())); inSequence(searchSequence);
         oneOf(searchObserver).forkAt(with(serveMethod(17))); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(17)), with(qSide())); inSequence(searchSequence);
         oneOf(searchObserver).forkAt(with(serveMethod(17))); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(16)), with(pSide())); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouter)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(16)), with(qSide())); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouter)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouter), with(pSide())); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouterExit)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouter), with(qSide())); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouterExit)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(19)), with(pSide())); inSequence(searchSequence);
         oneOf(searchObserver).forkAt(with(serveMethod(19))); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(19)), with(qSide())); inSequence(searchSequence);
         oneOf(searchObserver).forkAt(with(serveMethod(19))); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouterExit), with(pSide())); inSequence(searchSequence);
         oneOf(searchObserver).leaf(with(terminate())); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouterExit), with(qSide())); inSequence(searchSequence);
         oneOf(searchObserver).leaf(with(terminate())); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(18)), with(pSide())); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouter)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(18)), with(qSide())); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouter)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(22)), with(pSide())); inSequence(searchSequence);
         oneOf(searchObserver).leaf(with(terminate())); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(22)), with(qSide())); inSequence(searchSequence);
         oneOf(searchObserver).leaf(with(terminate())); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(20)), with(pSide())); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouter)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(20)), with(qSide())); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouter)); inSequence(searchSequence);
      }

      private CombinableMatcher<JState> serveMethod(final int line) {
         return both(serveMethod).and(lineNumber(line));
      }});
      vm.execute(symbol);
      System.out.printf("Got %d traces.\n", vm.results().size());

      final Collection<JState> results = vm.results();
      for (final JState jState : results) {
         System.out.println(jState);
      }
   }
}
