package de.jdufner.doppelt;

import java.util.Set;

public interface DoppeltBuilder {

	Set<Set<Integer>> build(final int size);

	Set<Set<Integer>> reduce(final int size);
}
