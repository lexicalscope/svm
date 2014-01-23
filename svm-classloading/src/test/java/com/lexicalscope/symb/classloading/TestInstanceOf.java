package com.lexicalscope.symb.classloading;

import static com.lexicalscope.symb.vm.j.j.klass.SClassMatchers.isInstanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lexicalscope.symb.classloading.AsmSClassLoader;
import com.lexicalscope.symb.classloading.SClassLoader;
import com.lexicalscope.symb.vm.j.j.klass.SClass;

public class TestInstanceOf {
   private final SClassLoader sClassLoader = new AsmSClassLoader();

   private final SClass integer = sClassLoader.load(Integer.class);
   private final SClass number = sClassLoader.load(Number.class);

   private final SClass arrayList = sClassLoader.load(ArrayList.class);
   private final SClass list = sClassLoader.load(List.class);

   @Test public void integerIsANumber(){
      assertThat(integer, isInstanceOf(number));
   }

   @Test public void numberInNotAnInteger(){
      assertThat(number, not(isInstanceOf(integer)));
   }

   @Test public void arrayListIsAList(){
      assertThat(arrayList, isInstanceOf(list));
   }

   @Test public void listIsNotAnArrayList(){
      assertThat(list, not(isInstanceOf(arrayList)));
   }
}
