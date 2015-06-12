package de.jdufner.doppelt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DoppeltBuilderIterativ {

	private int size = 0;
	private int anzahlElements = 0;

	public Collection<Collection<Integer>> build(final int size) {
		this.size = size;
		this.anzahlElements = size + (size - 1) * (size - 1);
		Collection<Collection<Integer>> tupels = null;
		tupels = buildInitial();
		tupels = addMissingTupels(tupels);

		return null;
	}

	private Collection<Collection<Integer>> addMissingTupels(
			Collection<Collection<Integer>> tupels) {
		for (int i = 2; i <= size; i++) {
			for (int j = 2; j <= size; j++) {
				Set<Integer> usedElements = befuelleUsedElements(tupels, j);
				System.out.println(usedElements);
			}
		}
		return tupels;
	}

	private Set<Integer> befuelleUsedElements(
			Collection<Collection<Integer>> tupels, int j) {
		// usedElements befüllen
		Set<Integer> usedElements = new HashSet<Integer>();
		for (Collection<Integer> tupel : tupels) {
			if (tupel.contains(j)) {
				usedElements.addAll(tupel);
			}
		}
		return usedElements;
	}
	
	private Collection<Integer> allElements() {
		Collection<Integer> allElements = new ArrayList<Integer>();
		for (int i = 1; i <= anzahlElements; i++) {
			allElements.add(i);
		}
		return allElements;
	}

	private Collection<Collection<Integer>> buildInitial() {
		Collection<Collection<Integer>> tupels = new ArrayList<Collection<Integer>>();
		Collection<Integer> tupel = new ArrayList<Integer>();
		for (int i = 2; i <= anzahlElements; i++) {
			if (tupel.size() == 0) {
				tupel.add(1);
			}

			tupel.add(i);

			if (tupel.size() == size) {
				System.out.println(tupel);
				tupels.add(tupel);
				tupel = new ArrayList<Integer>();
			}
		}
		return tupels;
	}

}
