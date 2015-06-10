package de.jdufner.doppelt;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class DoppeltBuilderSortedSet {

	private int size = 0;
	private int anzahlElements = 0;

	private Set<SortedSet<Integer>> tupels = new HashSet<SortedSet<Integer>>();

	public Set<SortedSet<Integer>> build(final int size) {
		this.size = size;
		this.anzahlElements = size + (size - 1) * (size - 1);
		buildTupel(1, new TreeSet<Integer>());
		return tupels;
	}

	public Set<SortedSet<Integer>> reduce(final int size) {
		final Set<SortedSet<Integer>> allTupels = build(size);
		final Set<SortedSet<Integer>> oneByOneTupels = new HashSet<SortedSet<Integer>>();
		for (final SortedSet<Integer> tupel : allTupels) {
			if (!hasMoreThenOneLink(tupel, oneByOneTupels)) {
//				System.out.println(tupel);
				oneByOneTupels.add(tupel);
			}
		}
		return oneByOneTupels;
	}

	private boolean hasMoreThenOneLink(final SortedSet<Integer> tupel,
			final Set<SortedSet<Integer>> oneByOneTupels) {
		for (final SortedSet<Integer> oneByOneTupel : oneByOneTupels) {
			final Collection<Integer> links = new HashSet<Integer>();
			for (final Integer element : tupel) {
				if (oneByOneTupel.contains(element)) {
					links.add(element);
				}
			}
			if (links.size() > 1) {
				return true;
			}
		}
		return false;
	}

	private void buildTupel(final int stacksize,
			final SortedSet<Integer> sortedSet) {
		for (int i = 1; i <= anzahlElements; i++) {
			final SortedSet<Integer> sortedSet2 = new TreeSet<Integer>(
					sortedSet);
			if (sortedSet2.add(i)) {
				if (stacksize < size) {
					buildTupel(stacksize + 1, sortedSet2);
				} else {
//					System.out.println(sortedSet2);
					if (sortedSet2.size() == size) {
						tupels.add(sortedSet2);
					}
				}
			}
		}
	}

}
