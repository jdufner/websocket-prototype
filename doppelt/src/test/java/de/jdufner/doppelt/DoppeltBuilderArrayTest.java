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
    int[][] feld = builder.build(4);
    DoppeltBuilderArray.print(feld);
  }

  @Test
  public void build5() {
    int[][] feld = builder.build(5);
    DoppeltBuilderArray.print(feld);
  }

  @Test
  public void build6() {
    int[][] feld = builder.build(6);
    DoppeltBuilderArray.print(feld);
  }

  @Test
  public void build8() {
    int[][] feld = builder.build(8);
    DoppeltBuilderArray.print(feld);
  }

}
