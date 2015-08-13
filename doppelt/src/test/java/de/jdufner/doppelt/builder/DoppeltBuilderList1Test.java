package de.jdufner.doppelt.builder;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import de.jdufner.doppelt.builder.DoppeltBuilderList1;

public class DoppeltBuilderList1Test {

  private DoppeltBuilderList1 builder = null;

  @Before
  public void setUp() {
    builder = new DoppeltBuilderList1();
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
    assertEquals(13, listOfTupel.size());
  }

  @Test
  public void build5() {
    Collection<Collection<Integer>> listOfTupel = builder.build(5);
    System.out.println(listOfTupel);
    assertEquals(21, listOfTupel.size());
  }

  @Test
  public void build6() {
    Collection<Collection<Integer>> listOfTupel = builder.build(6);
    System.out.println(listOfTupel);
    assertEquals(31, listOfTupel.size());
  }

  @Test
  public void build7() {
    Collection<Collection<Integer>> listOfTupel = builder.build(7);
    System.out.println(listOfTupel);
    assertEquals(43, listOfTupel.size());
  }

  @Test
  public void build8() {
    Collection<Collection<Integer>> listOfTupel = builder.build(8);
    System.out.println(listOfTupel);
    assertEquals(56, listOfTupel.size());
  }

}
