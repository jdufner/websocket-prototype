package de.jdufner.doppelt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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
    return tupels;
  }

  private Collection<Collection<Integer>> addMissingTupels(
      Collection<Collection<Integer>> tupels) {
    for (int i = 2; i <= anzahlElements; i++) {
      for (int j = 1; j < size; j++) {
        Collection<Integer> currentTupel = new ArrayList<Integer>();
        currentTupel.add(i);
        Set<Integer> usedElements = null;
        Integer nextElement = null;
        do {
          usedElements = befuelleUsedElements(currentTupel, tupels);
          nextElement = getNextElement(usedElements);
          if (nextElement != null) {
            currentTupel.add(nextElement);
          }
        } while (currentTupel.size() < size && nextElement != null);
        if (usedElements.size() < anzahlElements) {
          tupels.add(currentTupel);
        }
      }
    }
    return tupels;
  }

  private Integer getNextElement(Set<Integer> usedElements) {
    Collection<Integer> remainingElements = allElements();
    remainingElements.removeAll(usedElements);
    Iterator<Integer> iterator = remainingElements.iterator();
    if (iterator.hasNext()) {
      return iterator.next();
    }
    return null;
  }

  private Set<Integer> befuelleUsedElements(Collection<Integer> currentTupel,
      Collection<Collection<Integer>> tupels) {
    Set<Integer> usedElements = new HashSet<Integer>();
    for (Integer element : currentTupel) {
      for (Collection<Integer> tupel : tupels) {
        if (tupel.contains(element)) {
          usedElements.addAll(tupel);
        }
      }
    }
    return usedElements;
  }

  private Collection<Integer> allElements() {
    return buildCollection(anzahlElements);
  }

  private Collection<Integer> buildCollection(int size) {
    Collection<Integer> allElements = new ArrayList<Integer>();
    for (int i = 1; i <= size; i++) {
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
        // System.out.println(tupel);
        tupels.add(tupel);
        tupel = new ArrayList<Integer>();
      }
    }
    return tupels;
  }

}
