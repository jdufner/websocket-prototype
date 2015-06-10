package de.jdufner.doppelt;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;


public class DoppeltBuilderSetTest {

	@Test
	public void whenBuild2Expect3Tupel() {
		final DoppeltBuilderSet builder = new DoppeltBuilderSet();
		Set<Set<Integer>> tupels = buildResultTupels();
		Set<Set<Integer>> listOfTupel = builder.build(2);
		assertEquals(tupels, listOfTupel);
	}

	private Set<Set<Integer>> buildResultTupels() {
		Set<Integer> tupel1 = new HashSet<Integer>();
		tupel1.add(1);
		tupel1.add(2);
		Set<Integer> tupel2 = new HashSet<Integer>();
		tupel2.add(1);
		tupel2.add(3);
		Set<Integer> tupel3 = new HashSet<Integer>();
		tupel3.add(2);
		tupel3.add(3);
		Set<Set<Integer>> tupels = new HashSet<Set<Integer>>();
		tupels.add(tupel1);
		tupels.add(tupel2);
		tupels.add(tupel3);
		return tupels;
	}
	
	
	@Test
	public void whenBuild3AndReduceExpectLinkedByOne() {
		final DoppeltBuilderSet builder = new DoppeltBuilderSet();
		Set<Set<Integer>> listOfTupel = builder.reduce(3);
		assertEquals(7, listOfTupel.size());
	}
	
	@Test
	public void build4() {
		final DoppeltBuilderSet builder = new DoppeltBuilderSet();
		Set<Set<Integer>> listOfTupel = builder.build(4);
		System.out.println(listOfTupel);
	}
	
	@Test
	public void reduce4() {
		final DoppeltBuilderSet builder = new DoppeltBuilderSet();
		Set<Set<Integer>> listOfTupel = builder.reduce(4);
		System.out.println(listOfTupel);
	}
	
	@Test
	public void build5() {
		final DoppeltBuilderSet builder = new DoppeltBuilderSet();
		Set<Set<Integer>> listOfTupel = builder.build(5);
		System.out.println(listOfTupel);
	}
	
	@Test
	public void reduce5() {
		final DoppeltBuilderSet builder = new DoppeltBuilderSet();
		Set<Set<Integer>> listOfTupel = builder.reduce(5);
		System.out.println(listOfTupel);
	}
	
}
