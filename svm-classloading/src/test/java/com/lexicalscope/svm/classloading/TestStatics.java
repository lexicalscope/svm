package com.lexicalscope.svm.classloading;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.objectweb.asm.Type.getInternalName;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lexicalscope.svm.classloading.AsmSClassLoader;
import com.lexicalscope.svm.classloading.DuplicateClassDefinitionException;
import com.lexicalscope.svm.classloading.SClassLoader;
import com.lexicalscope.svm.classloading.StaticsImpl;

public class TestStatics {
   @Rule public ExpectedException exception = ExpectedException.none();

   private final SClassLoader classLoader = new AsmSClassLoader();
   private final StaticsImpl statics = new StaticsImpl(classLoader);

   @Test public void definedClassGivesClassRef() {
      statics.defineClass(getInternalName(EmptyClass.class));

      assertThat(classLoader.load(EmptyClass.class), sameInstance(statics.load(getInternalName(EmptyClass.class))));
   }

   @Test public void definedClassCannotBeRedefined() {
      statics.defineClass(getInternalName(EmptyClass.class));

      exception.expect(DuplicateClassDefinitionException.class);
      statics.defineClass(getInternalName(EmptyClass.class));
   }
}