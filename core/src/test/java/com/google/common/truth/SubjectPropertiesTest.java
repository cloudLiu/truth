/*
 * Copyright (c) 2011 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.common.truth;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assert_;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for introspective Subject behaviour.
 *
 * @author Christian Gruber (cgruber@israfil.net)
 */
@RunWith(JUnit4.class)
public class SubjectPropertiesTest {

  @Test public void testHasNoSuchField() {
    try {
      assertThat(new A()).hasField("noField");
      assert_().fail("Should have thrown an assertion error.");
    } catch (AssertionError expected) {
      assertThat(expected.getMessage())
          .is("Not true that <A> has a field named <noField>");
    }
  }

  @Test public void testHasPublicField() {
    assertThat(new A()).hasField("publicField");
  }

  @Test public void testHasFieldWithNullSubject() {
    Object nullObject = null;
    try {
      assertThat(nullObject).hasField("publicField");
      assert_().fail("Should have thrown an assertion error.");
    } catch (AssertionError expected) {
      assertThat(expected.getMessage())
          .is("Cannot determine a field name from a null object.");
    }
  }

  @Test public void testHasPublicFieldWithValue() {
    assertThat(new A("value", null)).hasField("publicField").withValue("value");
  }

  @Test public void testHasPublicFieldWithWrongValue() {
    try {
      assertThat(new A("aValue", null)).hasField("publicField").withValue("wrongValue");
      assert_().fail("Should have thrown an assertion error.");
    } catch (AssertionError expected) {
      assertThat(expected.getMessage())
          .is("Not true that <A>'s field <publicField> contains expected value " +
                "<wrongValue>. It contains value <aValue>");
    }
  }

  @Test public void testHasPublicFieldWithNullValue() {
    assertThat(new A(null, null)).hasField("publicField").withValue(null);
  }

  @Test public void testHasPublicFieldWithWrongNullValue() {
    try {
      assertThat(new A(null, null)).hasField("publicField").withValue("wrongValue");
      assert_().fail("Should have thrown an assertion error.");
    } catch (AssertionError expected) {
      assertThat(expected.getMessage())
          .is("Not true that <A>'s field <publicField> contains expected value " +
                "<wrongValue>. It contains value <null>");
    }
  }

  @Test public void testHasPublicFieldWithValueInBadField() {
    try {
      assertThat(new A("value", null)).hasField("noField").withValue("value");
      assert_().fail("Should have thrown an assertion error.");
    } catch (AssertionError expected) {
      assertThat(expected.getMessage())
          .is("Not true that <A> has a field named <noField>");
    }
  }

  @Test public void testHasPrivateField() {
    assertThat(new A()).hasField("privateField");
  }

  @Test public void testHasProvidedFieldWithValue() {
    assertThat(new A(null, "value")).hasField("privateField").withValue("value");
  }

  public static class A {
    public String publicField = null;
    @SuppressWarnings("unused")
    private String privateField = null;
    public A() {}
    public A(String publicField, String privateField) {
      this.publicField = publicField;
      this.privateField = privateField;
    }
  }

}
