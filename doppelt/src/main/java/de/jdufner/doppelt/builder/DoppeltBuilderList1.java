package de.jdufner.doppelt.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DoppeltBuilderList1 {

  public Collection<Collection<Integer>> build(final int size) {
    Collection<Collection<Integer>> tupels = buildInitial(size);
    tupels = addMissingTupels(size, tupels);
    return tupels;
  }

  private Collection<Collection<Integer>> addMissingTupels(int size, Collection<Collection<Integer>> tupels) {
    for (int i = 2; i <= size; i++) {
      for (int j = 1; j < size; j++) {
        Collection<Integer> tupel = buildNextTupel(size, tupels, new ArrayList<Integer>());
        if (tupel != null) {
          tupels.add(tupel);
        }
      }
    }
    return tupels;
  }

  private Collection<Integer> buildNextTupel(int size, Collection<Collection<Integer>> tupels, List<Integer> currentTupel) {
    Collection<Integer> testedElements = new ArrayList<Integer>();

    do {
      List<Integer> remainingElements = getRemainingElements(size, tupels, currentTupel, testedElements);
      Integer nextElement = null;
      if (!remainingElements.isEmpty()) {
        nextElement = getNextElement(size, remainingElements);
        currentTupel.add(nextElement);
        testedElements.add(nextElement);

        if (currentTupel.size() < size) {
          Collection<Integer> nextTupel = buildNextTupel(size, tupels, currentTupel);
          if (nextTupel == null) {
            currentTupel.remove(nextElement);
          } else {
            return nextTupel;
          }
        } else {
          return currentTupel;
        }

      } else {
        return null;
      }
    } while (true);
  }

  private Integer getNextElement(int size, List<Integer> remainingElements) {
    return getNextElement2(size, remainingElements);
  }

  private Integer getNextElement1(int size, List<Integer> remainingElements) {
    return remainingElements.get(0);
  }

  private Integer getNextElement2(int size, List<Integer> remainingElements) {
    Integer nextElement;
    if (remainingElements.size() >= size) {
      nextElement = remainingElements.get(size - 1);
    } else {
      nextElement = remainingElements.get(0);
    }
    return nextElement;
  }

  private Integer getNextElement3(int size, List<Integer> remainingElements) {
    int index = (int) (Math.random() * remainingElements.size());
    return remainingElements.get(index);
  }

  private List<Integer> getRemainingElements(int size, Collection<Collection<Integer>> tupels, Collection<Integer> currentTupel,
      Collection<Integer> testedElements) {
    Set<Integer> usedElements = getUsedElements(currentTupel, tupels);
    usedElements.addAll(testedElements);
    List<Integer> allElements = allElements(size);
    allElements.removeAll(usedElements);
    return allElements;
  }

  private Set<Integer> getUsedElements(Collection<Integer> currentTupel, Collection<Collection<Integer>> tupels) {
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

  private List<Integer> allElements(int size) {
    return buildCollection(getAnzahlElements(size));
  }

  private List<Integer> buildCollection(int size) {
    List<Integer> allElements = new ArrayList<Integer>();
    for (int i = 1; i <= size; i++) {
      allElements.add(i);
    }
    return allElements;
  }

  private Collection<Collection<Integer>> buildInitial(int size) {
    Collection<Collection<Integer>> tupels = new ArrayList<Collection<Integer>>();
    Collection<Integer> tupel = new ArrayList<Integer>();
    for (int i = 2; i <= getAnzahlElements(size); i++) {
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

  private int getAnzahlElements(int size) {
    return size + (size - 1) * (size - 1);
  }

}
