package com.lexicalscope.svm.partition.trace.symb.search2;

import static com.lexicalscope.svm.partition.spec.MatchersSpec.*;
import static com.lexicalscope.svm.search2.Side.*;
import static com.lexicalscope.svm.vm.j.StateMatchers.*;
import static com.lexicalscope.svm.vm.j.code.AsmSMethodName.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.objectweb.asm.Type.getInternalName;

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
import com.lexicalscope.svm.examples.router.broken.ExampleServing;
import com.lexicalscope.svm.examples.router.broken.Router;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ITerminalSymbol;
import com.lexicalscope.svm.partition.spec.CallContext;
import com.lexicalscope.svm.partition.trace.PartitionInstrumentation;
import com.lexicalscope.svm.search.ConstantRandomiser;
import com.lexicalscope.svm.search.GuidedSearchObserver;
import com.lexicalscope.svm.search2.TreeSearchFactory;
import com.lexicalscope.svm.vm.conc.StateSearchFactory;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.symb.junit.SymbVmRule;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class TestTreeSearchToMismatch {
   //@Rule
   public SymbVmRule vm;

   public ISymbol symbol;

   @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
   @Mock public GuidedSearchObserver searchObserver;
   @Auto public Sequence searchSequence;

   @Before public void setUp() {
      symbol = new ITerminalSymbol("s");
      final FeasibilityChecker feasibilityChecker = new FeasibilityChecker();
      final StateSearchFactory factory = new TreeSearchFactory(searchObserver, feasibilityChecker, new ConstantRandomiser(0));
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

   private CombinableMatcher<JState> serveMethod(final int line) {
      return both(serveMethod).and(lineNumber(line));
   }

   final Matcher<JState> routerConstructorEntry = currentMethodIs(defaultConstructor(Router.class));
   final CombinableMatcher<JState> routerConstructorExit = both(routerConstructorEntry).and(returnVoid());
   final Matcher<JState> serveMethod = currentMethodIs(method(ExampleServing.class, "serve", "(I)V"));
   final Matcher<JState> matchRouter = currentMethodIs(method(Router.class, "matchRoute", "(Ljava/lang/String;)Ljava/lang/String;"));
   final CombinableMatcher<JState> matchRouterExit = both(matchRouter).and(returnOne());

   @Test public void mismatchIsFound() {
      context.checking(new Expectations(){{
         oneOf(searchObserver).picked(with(entryPoint()), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(routerConstructorEntry)); inSequence(searchSequence);
         oneOf(searchObserver).picked(with(entryPoint()), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(routerConstructorEntry)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(routerConstructorEntry), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(routerConstructorExit)); inSequence(searchSequence);
         oneOf(searchObserver).picked(with(routerConstructorEntry), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(routerConstructorExit)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(routerConstructorExit), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).forkAt(with(serveMethod(15))); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(routerConstructorExit), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).forkAt(with(serveMethod(15))); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(17)), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).forkAt(with(serveMethod(17))); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(17)), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).forkAt(with(serveMethod(17))); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(16)), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouter)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(16)), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouter)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(19)), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).forkAt(with(serveMethod(19))); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(19)), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).forkAt(with(serveMethod(19))); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(18)), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouter)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(18)), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouter)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(22)), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).leaf(with(terminate())); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(22)), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).leaf(with(terminate())); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(20)), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouter)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(serveMethod(20)), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouter)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouter), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouterExit)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouter), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouterExit)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouter), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouterExit)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouter), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouterExit)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouter), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouterExit)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouter), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).goal(with(matchRouterExit)); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouterExit), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).leaf(with(terminate())); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouterExit), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).leaf(with(terminate())); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouterExit), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).leaf(with(terminate())); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouterExit), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).leaf(with(terminate())); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouterExit), with(PSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).leaf(with(terminate())); inSequence(searchSequence);

         oneOf(searchObserver).picked(with(matchRouterExit), with(QSIDE)); inSequence(searchSequence);
         oneOf(searchObserver).leaf(with(terminate())); inSequence(searchSequence);
      }
      });
      vm.execute(symbol);
      assertThat(vm.results(), hasSize(8));
   }
}
