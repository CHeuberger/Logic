/*
 * Copyright: Carlos F. Heuberger. All rights reserved.
 *
 */
package cfh.logic;

import java.util.Objects;
import java.util.Set;

import cfh.logic.proposition.Symbol;

/**
 * @author Carlos F. Heuberger, 2023-09-28
 *
 */
abstract class Check {

    static <E> void assertEquals(Set<Symbol> expected, Set<Symbol> actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("expected %s, got %s".formatted(expected, actual));
        }
    }
}
