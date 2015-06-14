package de.jdufner.doppelt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DoppeltBuilderList2 {

  public Collection<Collection<Integer>> build(final int size) {
    Collection<Collection<Integer>> initialTupels = buildInitial(size);
    Collection<Collection<Integer>> buildTupels = addMissingTupels(size, initialTupels, new ArrayList<Collection<Integer>>());
    return buildTupels;
  }

  private Collection<Collection<Integer>> addMissingTupels(int size, Collection<Collection<Integer>> initialTupels,
      List<Collection<Integer>> buildTupels) {
    Collection<Collection<Integer>> testedTupels = new ArrayList<Collection<Integer>>();
    buildTupels.addAll(initialTupels);
    do {
      Collection<Integer> tupel = buildNextTupel(size, buildTupels, testedTupels, new ArrayList<Integer>());
      if (tupel != null) {
        buildTupels.add(tupel);
        testedTupels.add(tupel);
        if (buildTupels.size() == getAnzahlElements(size)) {
          return buildTupels;
        }
      } else {
        buildTupels.remove(buildTupels.size() - 1);
      }

    } while (true);
    // return buildTupels;
  }

  private Collection<Integer> buildNextTupel(int size, Collection<Collection<Integer>> tupels,
      Collection<Collection<Integer>> testedTupels, List<Integer> currentTupel) {
    Collection<Integer> testedElements = new ArrayList<Integer>();

    do {
      List<Integer> remainingElements = getRemainingElements(size, tupels, currentTupel, testedElements);
      Integer nextElement = null;
      if (!remainingElements.isEmpty()) {
        nextElement = remainingElements.get(0);
        currentTupel.add(nextElement);
        testedElements.add(nextElement);

        if (currentTupel.size() < size) {
          Collection<Integer> nextTupel = buildNextTupel(size, tupels, testedTupels, currentTupel);
          if (testedTupels.contains(nextTupel)) {
            continue;
          }
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
