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
   private final SClass classWith4StaticFields = sClassLoader.load(ClassWith5StaticFields.class);
   private final SClass classWith4StaticFieldsAnd2DynamicFields = sClassLoader.load(ClassWith4StaticFieldsAnd2DynamicFields.class);

   @Test public void classWithNoSuperClassCountsFieldsFromPreamble() {
      assertThat(classWithFiveFields.allocateSize(), equalTo(6));
      assertThat(classWithFiveFields, hasField("a", withIndex(1)));
      assertThat(classWithFiveFields, hasFieldAtIndex(1, "a"));
      assertThat(classWithFiveFields, hasField("b", withIndex(2)));
      assertThat(classWithFiveFields, hasField("c", withIndex(3)));
      assertThat(classWithFiveFields, hasField("d", withIndex(4)));
      assertThat(classWithFiveFields, hasField("e", withIndex(5)));
   }

   @Test public void classWithSuperClassCountsFieldsFromSuperClassFields() {
      assertThat(subClassWithThreeFields.allocateSize(), equalTo(5+3+SClass.OBJECT_PREAMBLE));
      assertThat(subClassWithThreeFields, hasField(classWithFiveFields, "a", withIndex(1)));
      assertThat(subClassWithThreeFields, hasField(classWithFiveFields, "b", withIndex(2)));
      assertThat(subClassWithThreeFields, hasField(classWithFiveFields, "c", withIndex(3)));
      assertThat(subClassWithThreeFields, hasField(classWithFiveFields, "d", withIndex(4)));
      assertThat(subClassWithThreeFields, hasField(classWithFiveFields, "e", withIndex(5)));
      assertThat(subClassWithThreeFields, hasField("f", withIndex(6)));
      assertThat(subClassWithThreeFields, hasFieldAtIndex(6, "f"));
      assertThat(subClassWithThreeFields, hasField("g", withIndex(7)));
      assertThat(subClassWithThreeFields, hasField("h", withIndex(8)));
   }

   @Test public void classWithOverloadedFieldHasMoreThanOneIndex() {
      assertThat(subClassWithOverloadedField.allocateSize(), equalTo(5+1+SClass.OBJECT_PREAMBLE));
      assertThat(subClassWithOverloadedField, hasField(classWithFiveFields, "c", withIndex(3)));
      assertThat(subClassWithOverloadedField, hasField("c", withIndex(6)));
   }

   @Test public void classCountsStaticFieldsFrom0() {
      assertThat(classWith4StaticFields.allocateSize(), equalTo(1));
      assertThat(classWith4StaticFields.staticFieldCount(), equalTo(4));
      assertThat(classWith4StaticFields, hasStaticField("a", withIndex(1)));
      assertThat(classWith4StaticFields, hasStaticField("b", withIndex(2)));
      assertThat(classWith4StaticFields, hasStaticField("c", withIndex(3)));
      assertThat(classWith4StaticFields, hasStaticField("d", withIndex(4)));
   }

   @Test public void classWithStaticAndDynamicFieldsSeparatesThem() {
      assertThat(classWith4StaticFieldsAnd2DynamicFields.allocateSize(), equalTo(3));
      assertThat(classWith4StaticFieldsAnd2DynamicFields.staticFieldCount(), equalTo(4));
      assertThat(classWith4StaticFieldsAnd2DynamicFields, hasStaticField("a", withIndex(1)));
      assertThat(classWith4StaticFieldsAnd2DynamicFields, hasStaticField("b", withIndex(2)));
      assertThat(classWith4StaticFieldsAnd2DynamicFields, hasStaticField("c", withIndex(3)));
      assertThat(classWith4StaticFieldsAnd2DynamicFields, hasStaticField("d", withIndex(4)));
      assertThat(classWith4StaticFieldsAnd2DynamicFields, hasField("e", withIndex(1)));
      assertThat(classWith4StaticFieldsAnd2DynamicFields, hasField("f", withIndex(2)));
   }
}
