package de.jdufner.doppelt;

import org.junit.Before;
import org.junit.Test;

public class DoppeltBuilderArrayTest {

  private DoppeltBuilderArray builder = null;

  @Before
  public void setUp() {
    builder = new DoppeltBuilderArray();
  }

  @Test
  public void build4() {
    builder.build(4);
  }

}
