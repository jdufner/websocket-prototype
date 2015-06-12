package de.jdufner.doppelt;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Before;
import org.junit.Ignore;
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
	@Ignore
	public void build5() {
		Collection<Collection<Integer>> listOfTupel = builder.build(5);
		System.out.println(listOfTupel);
	}
	
	@Test
	@Ignore
	public void build6() {
		Collection<Collection<Integer>> listOfTupel = builder.build(6);
		System.out.println(listOfTupel);
	}
	
}
