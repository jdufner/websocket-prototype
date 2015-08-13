package de.jdufner.doppelt;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class DoppeltBuilderSortedSetTest {

  private DoppeltBuilderSortedSet builder = null;

  @Before
  public void setUp() {
    builder = new DoppeltBuilderSortedSet();
  }

  @Test
  public void whenBuild2Expect3Tupel() {
    Set<Integer> tupel1 = new TreeSet<Integer>();
    tupel1.add(1);
    tupel1.add(2);
    Set<Integer> tupel2 = new TreeSet<Integer>();
    tupel2.add(1);
    tupel2.add(3);
    Set<Integer> tupel3 = new TreeSet<Integer>();
    tupel3.add(2);
    tupel3.add(3);
    Set<Set<Integer>> tupels = new HashSet<Set<Integer>>();
    tupels.add(tupel1);
    tupels.add(tupel2);
    tupels.add(tupel3);
    Set<Set<Integer>> listOfTupel = builder.build(2);
    assertEquals(tupels, listOfTupel);
  }

  @Test
  public void whenBuild3AndReduceExpectLinkedByOne() {
    Set<Set<Integer>> listOfTupel = builder.reduce(3);
    assertEquals(7, listOfTupel.size());
  }

  @Test
  public void build4() {
    Set<Set<Integer>> listOfTupel = builder.build(4);
    System.out.println(listOfTupel);
  }

  @Test
  public void reduce4() {
    Set<Set<Integer>> listOfTupel = builder.reduce(4);
    System.out.println(listOfTupel);
  }

  @Test
  public void build5() {
    Set<Set<Integer>> listOfTupel = builder.build(5);
    System.out.println(listOfTupel);
  }

  @Test
  public void reduce5() {
    Set<Set<Integer>> listOfTupel = builder.reduce(5);
    System.out.println(listOfTupel);
  }

  @Test
  @Ignore
  public void build6() {
    Set<Set<Integer>> listOfTupel = builder.build(6);
    System.out.println(listOfTupel);
  }

  @Test
  @Ignore
  public void reduce6() {
    Set<Set<Integer>> listOfTupel = builder.reduce(6);
    System.out.println(listOfTupel);
  }

}
