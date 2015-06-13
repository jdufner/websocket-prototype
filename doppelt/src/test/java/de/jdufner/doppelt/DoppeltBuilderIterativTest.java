package de.jdufner.doppelt;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class DoppeltBuilderIterativTest {

  private DoppeltBuilderIterativ builder = null;

  @Before
  public void setUp() {
    builder = new DoppeltBuilderIterativ();
  }

  @Test
  public void whenBuild3ExpectLinkedByOne() {
    Collection<Collection<Integer>> listOfTupel = builder.build(3);
    assertEquals(7, listOfTupel.size());
  }

  @Test
  public void build4() {
    Collection<Collection<Integer>> listOfTupel = builder.build(4);
    System.out.println(listOfTupel);
  }

  @Test
  public void build5() {
    Collection<Collection<Integer>> listOfTupel = builder.build(5);
    System.out.println(listOfTupel);
  }

  @Test
  public void build6() {
    Collection<Collection<Integer>> listOfTupel = builder.build(6);
    System.out.println(listOfTupel);
  }

  @Test
  public void build7() {
    Collection<Collection<Integer>> listOfTupel = builder.build(7);
    System.out.println(listOfTupel);
  }

  @Test
  public void build8() {
    Collection<Collection<Integer>> listOfTupel = builder.build(8);
    System.out.println(listOfTupel);
  }

}
