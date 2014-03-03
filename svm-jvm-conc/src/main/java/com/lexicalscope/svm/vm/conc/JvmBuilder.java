package com.lexicalscope.svm.vm.conc;

import com.lexicalscope.svm.classloading.ClassSource;
import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.Vm;
import com.lexicalscope.svm.vm.VmImpl;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public final class JvmBuilder {
   private StateSearchFactory searchFactory = new DepthFirstStateSearchFactory();
   private InitialStateBuilder initialStateBuilder = new InitialStateBuilder();

   public static JvmBuilder jvm() { return new JvmBuilder(); }

   public Vm<JState> build(final ClassSource[] classSources, final SMethodDescriptor entryPointName, final Object... args) {
      final StateSearch<JState> search = searchFactory.search();
      for (final ClassSource classSource : classSources) {
         search.consider(initialStateBuilder.createInitialState(search, classSource, entryPointName, args));
      }
      return new VmImpl<JState>(search);
   }

   public <T> JvmBuilder initialState(final InitialStateBuilder initialStateBuilder) {
      this.initialStateBuilder = initialStateBuilder;
      return this;
   }

   public JvmBuilder searchWith(final StateSearchFactory searchFactory) {
      this.searchFactory = searchFactory;
      return this;
   }

   public InitialStateBuilder initialState() {
      return this.initialStateBuilder;
   }
}