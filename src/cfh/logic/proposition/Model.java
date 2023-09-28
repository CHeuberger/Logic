/*
 * Copyright: Carlos F. Heuberger. All rights reserved.
 *
 */
package cfh.logic.proposition;

import static java.util.stream.Collectors.*;

import java.util.LinkedHashMap;
import java.util.SequencedMap;
import java.util.SequencedSet;

/**
 * @author Carlos F. Heuberger, 2023-09-28
 *
 */
public class Model {

    private final SequencedMap<Symbol, Boolean> associations;
    
    Model(SequencedSet<Symbol> symbols) {
        associations = new LinkedHashMap<>();
        symbols.forEach(s -> associations.put(s, false));
    }
    
    public Model(Model copy) {
        associations = new LinkedHashMap<>();
        copy.associations.forEach(associations::put);
    }
    
    public Model copy() {
        return new Model(this);
    }
    
    public Model set(Symbol symbol, boolean state) {
        if (!associations.containsKey(symbol))
            throw new IllegalArgumentException("unknown Symbol: " + symbol);
        associations.put(symbol, state);
        return this;
    }
    
    public Model set(Symbol symbol) {
        return set(symbol, true);
    }
    
    public Model unset(Symbol symbol) {
        return set(symbol, false);
    }
    
    public boolean flip(Symbol symbol) {
        if (!associations.containsKey(symbol))
            throw new IllegalArgumentException("unknown Symbol: " + symbol);
        return associations.put(symbol, !associations.get(symbol));
    }
    
    public Model reset() {
        associations.keySet().forEach(this::unset);
        return this;
    }
    
    public boolean next() {
        for (var symbol : associations.sequencedKeySet().reversed()) {
            if (!flip(symbol))
                return true;
        }
        return false;
    }
    
    public boolean get(Symbol symbol) {
        if (!associations.containsKey(symbol))
            throw new IllegalArgumentException("unknown Symbol: " + symbol);
        return associations.get(symbol);            
    }
    
    @Override
    public String toString() {
        return
            associations
            .entrySet()
            .stream()
            .map(e -> "  %s=%s".formatted(e.getKey(), e.getValue()) )
            .collect(joining("\n"));
    }
}
