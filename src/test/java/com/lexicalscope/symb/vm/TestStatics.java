package com.lexicalscope.symb.vm;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lexicalscope.symb.vm.classloader.AsmSClassLoader;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public class TestStatics {
   @Rule public ExpectedException exception = ExpectedException.none();

   private final SClassLoader classLoader = new AsmSClassLoader();
   private final StaticsImpl statics = new StaticsImpl();
   private final SClass emptyClass = classLoader.load(EmptyClass.class);

   @Test public void definedClassGivesClassRef() {
      final Object klassRef = statics.defineClass(emptyClass);

      assertThat(emptyClass, equalTo(statics.classDef(klassRef)));
   }

   @Test public void definedClassCannotBeRedefined() {
      statics.defineClass(emptyClass);

      exception.expect(DuplicateClassDefinitionException.class);
      statics.defineClass(emptyClass);
   }
}
