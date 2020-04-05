package com.github.pwittchen.touch.library;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class TouchTest {
  @Test
  public void shouldCreateTouchObject() {
    Touch touch = new Touch();
    assertThat(touch).isNotNull();
  }
}