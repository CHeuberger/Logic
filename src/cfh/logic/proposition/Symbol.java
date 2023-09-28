/*
 * Copyright: Carlos F. Heuberger. All rights reserved.
 *
 */
package cfh.logic.proposition;

import static java.util.Objects.*;

import java.util.Objects;

/**
 * @author Carlos F. Heuberger, 2023-09-28
 *
 */
public interface Symbol extends Sentence {

}

final class SymbolImpl extends SentenceImpl implements Symbol {

    private final String id;
    private final String comment;
    
    SymbolImpl(String id) {
        this(id, null);
    }
    
    SymbolImpl(String id, String comment) {
        this.id = requireNonNull(id, "null id");
        this.comment = comment;
    }
    
    String comment() {
        return comment;
    }
    
    @Override
    public boolean evaluate(Model model) {
        return model.get(this);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof SymbolImpl other) && Objects.equals(other.id, this.id);
    }
    
    @Override
    public String toString() {
        return id;
    }
}
