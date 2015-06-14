package de.jdufner.doppelt;

public class DoppeltBuilderArray {

  public int[][] build(int size) {
    int counter = 0;
    int[][] feld = initializeFeld(size);
    int[] anzahlElemente = zaehleElemente(feld);
    int[] currentPosition = getFirstPositionContainingNull(feld);
    int[][] filledFeld = addMissingTupels(counter, currentPosition, feld,
        anzahlElemente);
    return filledFeld;
  }

  private int[][] addMissingTupels(int counter, int[] currentPosition,
      int[][] feld, int[] anzahlElemente) {
    int nextElement = getFirstElementOfNewLine(feld.length, anzahlElemente);
    if (nextElement == 0) {
      print(feld);
      return feld;
    }
    return null;
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
    for (int j = 0; j < feld[0].length; j++) {
      for (int i = 0; i < feld.length; i++) {
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
    int[][] feld = new int[size][getAnzahlElements(size)];
    for (int j = 0; j < size; j++) {
      for (int i = 0; i < size; i++) {
        if (i == 0) {
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

  void print(int[][] feld) {
    for (int j = 0; j < feld[0].length; j++) {
      for (int i = 0; i < feld.length; i++) {
        System.out.format("%3d", feld[i][j]);
      }
      System.out.println();
    }
  }

  int[] zaehleElemente(int[][] feld) {
    int[] anzahlElemente = new int[getAnzahlElements(feld.length) + 1];
    for (int j = 0; j < feld[0].length; j++) {
      for (int i = 0; i < feld.length; i++) {
        anzahlElemente[feld[i][j]]++;
      }
    }
    return anzahlElemente;
  }

}
