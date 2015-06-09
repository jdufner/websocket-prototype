package de.jdufner.doppelt;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;


public class DoppeltBuilderTest {

	@Test
	public void whenBuild2Expect3Tupel() {
		final DoppeltBuilder builder = new DoppeltBuilder();
		Set<SortedSet<Integer>> tupels = buildResultTupels();
		Set<SortedSet<Integer>> listOfTupel = builder.build(2);
		assertEquals(tupels, listOfTupel);
	}

	private Set<SortedSet<Integer>> buildResultTupels() {
		SortedSet<Integer> tupel1 = new TreeSet<Integer>();
		tupel1.add(1);
		tupel1.add(2);
		SortedSet<Integer> tupel2 = new TreeSet<Integer>();
		tupel2.add(1);
		tupel2.add(3);
		SortedSet<Integer> tupel3 = new TreeSet<Integer>();
		tupel3.add(2);
		tupel3.add(3);
		Set<SortedSet<Integer>> tupels = new HashSet<SortedSet<Integer>>();
		tupels.add(tupel1);
		tupels.add(tupel2);
		tupels.add(tupel3);
		return tupels;
	}
	
	
	@Test
	public void whenBuild3AndReduceExpectLinkedByOne() {
		final DoppeltBuilder builder = new DoppeltBuilder();
		Set<SortedSet<Integer>> listOfTupel = builder.reduce(3);
		assertEquals(7, listOfTupel.size());
	}
	
	@Test
	public void build8() {
		final DoppeltBuilder builder = new DoppeltBuilder();
		Set<SortedSet<Integer>> listOfTupel = builder.build(8);
		System.out.println(listOfTupel);
	}
	
	@Test
	public void reduce5() {
		final DoppeltBuilder builder = new DoppeltBuilder();
		Set<SortedSet<Integer>> listOfTupel = builder.reduce(5);
		System.out.println(listOfTupel);
	}
	
}
