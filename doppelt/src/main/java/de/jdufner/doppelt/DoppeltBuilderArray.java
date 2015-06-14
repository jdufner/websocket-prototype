package de.jdufner.doppelt;

public class DoppeltBuilderArray {

  public int[][] build(int size) {
    int counter = 0;
    int[][] feld = initializeFeld(size);
    int[] anzahlElemente = zaehleElemente(feld);
    int[] currentPosition = getFirstPositionContainingNull(feld);
    int[][] filledFeld = addMissingTupels(counter, currentPosition, feld, anzahlElemente);
    return filledFeld;
  }

  private int[][] addMissingTupels(int counter, int[] currentPosition, int[][] feld, int[] anzahlElemente) {
    int nextElement = 0;
    nextElement = getNextElement(currentPosition, feld, anzahlElemente);
    if (nextElement == 0) {
      print(feld);
      return feld;
    } else {
      setNextElement(nextElement, currentPosition, feld, anzahlElemente);
    }
    return null;
  }

  private void setNextElement(int nextElement, int[] currentPosition, int[][] feld, int[] anzahlElemente) {
    feld[currentPosition[0]][currentPosition[1]] = nextElement;
    anzahlElemente[nextElement]++;
  }

  private int getNextElement(int[] currentPosition, int[][] feld, int[] anzahlElemente) {
    int nextElement = 0;
    if (currentPosition[1] == 0) {
      nextElement = getFirstElementOfNewLine(feld[0].length, anzahlElemente);
    } else {
      int[] linkedElemente = getLinkedElements(currentPosition, feld);
      for (int i = 1; i <= linkedElemente.length; i++) {
        if (linkedElemente[i] == 0) {
          nextElement = i;
        }
      }
    }
    return nextElement;
  }

  private int[] getLinkedElements(int[] currentPosition, int[][] feld) {
    int[] linkedElements = new int[feld[0].length + 1];
    for (int i = 0; i < feld.length; i++) {
      int element = feld[i][currentPosition[0]];
      for (int k = 0; k < currentPosition[1]; k++) {
        for (int j = 0; j < feld.length; j++) {
          if (feld[j][k] == element) {
            // alle Elemente aus Zeile in linkedElements hinzufügen
            addLinkedElements(k, feld, linkedElements);
          }
        }
      }
    }
    return linkedElements;
  }

  private void addLinkedElements(int k, int[][] feld, int[] linkedElements) {
    for (int i = 0; i < feld.length; i++) {
      linkedElements[feld[i][k]]++;
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
    print(feld);
    return feld;
  }

  private int getAnzahlElements(int size) {
    return size + (size - 1) * (size - 1);
  }

  private void print(int[][] feld) {
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
