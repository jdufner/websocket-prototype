package de.jdufner.doppelt;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DoppeltBuilderSet implements DoppeltBuilder {

	private int size = 0;
	private int anzahlElements = 0;

	private Set<Set<Integer>> tupels = new HashSet<Set<Integer>>();

	public Set<Set<Integer>> build(final int size) {
		this.size = size;
		this.anzahlElements = size + (size - 1) * (size - 1);
		buildTupel(1, new HashSet<Integer>());
		return tupels;
	}

	public Set<Set<Integer>> reduce(final int size) {
		final Set<Set<Integer>> allTupels = build(size);
		final Set<Set<Integer>> oneByOneTupels = new HashSet<Set<Integer>>();
		for (final Set<Integer> tupel : allTupels) {
			if (!hasMoreThenOneLink(tupel, oneByOneTupels)) {
				// System.out.println(tupel);
				oneByOneTupels.add(tupel);
			}
		}
		return oneByOneTupels;
	}

	private boolean hasMoreThenOneLink(final Set<Integer> tupel,
			final Set<Set<Integer>> oneByOneTupels) {
		for (final Set<Integer> oneByOneTupel : oneByOneTupels) {
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

	private void buildTupel(final int stacksize, final Set<Integer> set) {
		for (int i = 1; i <= anzahlElements; i++) {
			final Set<Integer> set2 = new HashSet<Integer>(set);
			if (set2.add(i)) {
				if (stacksize < size) {
					buildTupel(stacksize + 1, set2);
				} else {
					// System.out.println(sortedSet2);
					if (set2.size() == size) {
						tupels.add(set2);
					}
				}
			}
		}
	}

}
