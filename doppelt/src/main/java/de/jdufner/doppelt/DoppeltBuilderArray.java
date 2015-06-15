package de.jdufner.doppelt;

public class DoppeltBuilderArray {

  int counter = 0;

  public int[][] build(int size) {
    int[][] feld = initializeFeld(size);
    int[] anzahlElemente = zaehleElemente(feld);
    int[] currentPosition = getFirstPositionContainingNull(feld);
    int[][] filledFeld = addMissingTupels(counter, currentPosition, feld, anzahlElemente);
    return filledFeld;
  }

  private int[][] addMissingTupels(int counter, int[] currentPosition, int[][] feld, int[] anzahlElemente) {
    ++counter;
    if (counter % 100 == 0) {
      print(feld);
    }
    int nextElement = 0;
    int[][] filledFeld = null;
    int[] testedElements = new int[feld.length];
    do {
      initCurrentPosition(currentPosition, feld);
      nextElement = getNextElement(currentPosition, feld, anzahlElemente, testedElements);
      addElement(nextElement, testedElements);
      if (nextElement != 0) {
        filledFeld = setNextElementAndCallAddMissingTupels(nextElement, counter, currentPosition.clone(), feld.clone(),
            anzahlElemente.clone());
        if (filledFeld != null && filledFeld[filledFeld.length - 1][filledFeld[0].length - 1] != 0) {
          return filledFeld;
        }
      }
    } while (nextElement != 0);
    return filledFeld;
  }

  private void initCurrentPosition(int[] currentPosition, int[][] feld) {
    feld[currentPosition[0]][currentPosition[1]] = 0;
  }

  private void addElement(int nextElement, int[] testedElements) {
    for (int i = 0; i < testedElements.length; i++) {
      if (testedElements[i] == 0) {
        testedElements[i] = nextElement;
        break;
      }
    }
  }

  private int[][] setNextElementAndCallAddMissingTupels(int nextElement, int counter, int[] currentPosition, int[][] feld,
      int[] anzahlElemente) {
    int[][] filledField = null;
    feld[currentPosition[0]][currentPosition[1]] = nextElement;
    if (nextElement != 0 && currentPosition[0] == feld.length - 1 && currentPosition[1] == feld[0].length - 1) {
      return feld;
    }
    anzahlElemente[nextElement]++;
    incrementCurrentPosition(currentPosition, feld);
    if (currentPosition[0] < feld.length) {
      filledField = addMissingTupels(counter, currentPosition, feld, anzahlElemente);
    }
    return filledField;
  }

  private void incrementCurrentPosition(int[] currentPosition, int[][] feld) {
    if (currentPosition[1] == feld[0].length - 1) {
      currentPosition[0]++;
      currentPosition[1] = 0;
    } else {
      currentPosition[1]++;
    }
  }

  private int getNextElement(int[] currentPosition, int[][] feld, int[] anzahlElemente, int[] testedElements) {
    int nextElement = 0;
    if (currentPosition[1] == 0) {
      nextElement = getFirstElementOfNewLine(feld[0].length, anzahlElemente);
    } else {
      int[] linkedElements = getLinkedElements(currentPosition, feld);
      for (int i = 0; i < testedElements.length; i++) {
        if (testedElements[i] != 0) {
          linkedElements[testedElements[i]]++;
        } else {
          break;
        }
      }
      for (int i = 1; i < linkedElements.length; i++) {
        if (linkedElements[i] == 0) {
          nextElement = i;
          break;
        }
      }
    }
    return nextElement;
  }

  private int[] getLinkedElements(int[] currentPosition, int[][] feld) {
    int[] linkedElements = new int[feld.length + 1];
    for (int i = 0; i < feld[0].length; i++) {
      int element = feld[currentPosition[0]][i];
      if (element > 0) {
        for (int k = 0; k < currentPosition[0]; k++) {
          for (int j = 0; j < feld[0].length; j++) {
            if (feld[k][j] == element) {
              addLinkedElements(k, feld, linkedElements);
              break;
            }
          }
        }
      }
    }
    return linkedElements;
  }

  private void addLinkedElements(int k, int[][] feld, int[] linkedElements) {
    for (int i = 0; i < feld[0].length; i++) {
      linkedElements[feld[k][i]]++;
    }
  }

  private int getFirstElementOfNewLine(int size, int[] anzahlElemente) {
    for (int i = 0; i < anzahlElemente.length; i++) {
      if (anzahlElemente[i + 1] < size) {
        return i + 1;
      }
    }
    return 0;
  }

  private int[] getFirstPositionContainingNull(int[][] feld) {
    int[] position = new int[2];
    for (int i = 0; i < feld.length; i++) {
      for (int j = 0; j < feld[0].length; j++) {
        if (feld[i][j] == 0) {
          position[0] = i;
          position[1] = j;
          return position;
        }
      }
    }
    return position;
  }

  private int[][] initializeFeld(int size) {
    int k = 2;
    int[][] feld = new int[getAnzahlElements(size)][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (j == 0) {
          feld[i][j] = 1;
        } else {
          feld[i][j] = k;
          k++;
        }
      }
    }
    // print(feld);
    return feld;
  }

  private int getAnzahlElements(int size) {
    return size + (size - 1) * (size - 1);
  }

  public static void print(int[][] feld) {
    for (int i = 0; i < feld.length; i++) {
      for (int j = 0; j < feld[0].length; j++) {
        System.out.format("%3d", feld[i][j]);
      }
      System.out.println();
    }
  }

  private int[] zaehleElemente(int[][] feld) {
    int[] anzahlElemente = new int[getAnzahlElements(feld[0].length) + 1];
    for (int i = 0; i < feld.length; i++) {
      for (int j = 0; j < feld[0].length; j++) {
        anzahlElemente[feld[i][j]]++;
      }
    }
    return anzahlElemente;
  }

}
