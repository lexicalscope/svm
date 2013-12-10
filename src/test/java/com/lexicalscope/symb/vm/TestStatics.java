package com.lexicalscope.symb.vm;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.objectweb.asm.Type.getInternalName;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lexicalscope.symb.vm.classloader.AsmSClassLoader;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public class TestStatics {
   @Rule public ExpectedException exception = ExpectedException.none();

   private final SClassLoader classLoader = new AsmSClassLoader();
   private final StaticsImpl statics = new StaticsImpl(classLoader);
   private final SClass emptyClass = classLoader.load(EmptyClass.class);

   @Test public void definedClassGivesClassRef() {
      statics.defineClass(getInternalName(EmptyClass.class));

      assertThat(emptyClass, sameInstance(statics.load(getInternalName(EmptyClass.class))));
   }

   @Test public void definedClassCannotBeRedefined() {
      statics.defineClass(getInternalName(EmptyClass.class));

      exception.expect(DuplicateClassDefinitionException.class);
      statics.defineClass(getInternalName(EmptyClass.class));
   }
}
