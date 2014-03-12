package com.lexicalscope.svm.classloading;

import static com.lexicalscope.MatchersAdditional.has;
import static com.lexicalscope.svm.vm.j.klass.SClassMatchers.nameIs;
import static org.hamcrest.MatcherAssert.assertThat;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.vm.j.klass.SClass;

public class TestClassLoaderLoadOrder {
   @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
   private final SClassLoader sClassLoader = new AsmSClassLoader();

   @Test public void loadWillLoadSuperClassFirst(){
      context.checking(new Expectations(){{
//         oneOf(classLoaded).loaded(with(nameIs(Object.class))); inSequence(loadSequence);
//         oneOf(classLoaded).loaded(with(nameIs(ClassWith5Fields.class))); inSequence(loadSequence);
//         oneOf(classLoaded).loaded(with(nameIs(SubClassWithAdditionalFields.class))); inSequence(loadSequence);
      }});

      final SClass loaded = sClassLoader.load(SubClassWithAdditionalFields.class);
      assertThat(loaded, nameIs(SubClassWithAdditionalFields.class));
      assertThat(loaded.superTypes(),
            has(
                  nameIs(Object.class),
                  nameIs(ClassWith5Fields.class),
                  nameIs(SubClassWithAdditionalFields.class)).only().inOrder());
   }
}
