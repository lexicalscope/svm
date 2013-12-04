package com.lexicalscope.symb.vm.classloader;

import static com.lexicalscope.symb.vm.classloader.SClassMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

/**
 * If the compiler changes the field order then these tests will break.
 *
 * @author tim
 */
public class TestFieldLInking {
   private final SClassLoader sClassLoader = new AsmSClassLoader();
   private final SClass classWithFiveFields = sClassLoader.load(ClassWith5Fields.class);
   private final SClass subClassWithThreeFields = sClassLoader.load(SubClassWithAdditionalFields.class);
   private final SClass subClassWithOverloadedField = sClassLoader.load(SubClassWithOverloadedField.class);

   @Test public void classWithNoSuperClassCountsFieldsFrom0() {
      assertThat(classWithFiveFields.fieldCount(), equalTo(5));
      assertThat(classWithFiveFields, hasField("a", withIndex(0)));
      assertThat(classWithFiveFields, hasField("b", withIndex(1)));
      assertThat(classWithFiveFields, hasField("c", withIndex(2)));
      assertThat(classWithFiveFields, hasField("d", withIndex(3)));
      assertThat(classWithFiveFields, hasField("e", withIndex(4)));
   }

   @Test public void classWithSuperClassCountsFieldsFromSuperClassFields() {
      assertThat(subClassWithThreeFields.fieldCount(), equalTo(5+3));
      assertThat(subClassWithThreeFields, hasField(classWithFiveFields, "a", withIndex(0)));
      assertThat(subClassWithThreeFields, hasField(classWithFiveFields, "b", withIndex(1)));
      assertThat(subClassWithThreeFields, hasField(classWithFiveFields, "c", withIndex(2)));
      assertThat(subClassWithThreeFields, hasField(classWithFiveFields, "d", withIndex(3)));
      assertThat(subClassWithThreeFields, SClassMatchers.hasField(classWithFiveFields, "e", withIndex(4)));
      assertThat(subClassWithThreeFields, hasField("f", withIndex(5)));
      assertThat(subClassWithThreeFields, hasField("g", withIndex(6)));
      assertThat(subClassWithThreeFields, hasField("h", withIndex(7)));
   }

   @Test public void classWithOverloadedFieldHasMoreThanOneIndex() {
      assertThat(subClassWithOverloadedField.fieldCount(), equalTo(5+1));
      assertThat(subClassWithOverloadedField, hasField(classWithFiveFields, "c", withIndex(2)));
      assertThat(subClassWithOverloadedField, hasField("c", withIndex(5)));
   }
}
