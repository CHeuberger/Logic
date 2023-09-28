/*
 * Copyright: Carlos F. Heuberger. All rights reserved.
 *
 */
package cfh.logic.proposition;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.SequencedCollection;
import java.util.SequencedMap;

/**
 * @author Carlos F. Heuberger, 2023-09-28
 *
 */
public class Knowledge {

    private final String name;
    private final SequencedMap<String, SymbolImpl> symbols;
    private final List<Sentence> sentences;
    
    public Knowledge(String name) {
        this.name = Objects.requireNonNull(name, "null name");
        this.symbols = new LinkedHashMap<>();
        this.sentences = new ArrayList<>();
    }

    public Symbol symbol(String id) {
        var symbol = symbols.computeIfAbsent(id, SymbolImpl::new);
        return symbol;
    }
    
    public Symbol symbol(String id, String comment) {
        var symbol = symbols.computeIfAbsent(id, s -> new SymbolImpl(id, comment));
        if (!Objects.equals(symbol.comment(), comment)) {
            throw new IllegalArgumentException(
                "Comment of '%s' does not match '%s'%n".formatted(symbol.comment(), comment));
        }
        return symbol;
    }
    
    public Knowledge add(Sentence sentence) {
        sentences.add(sentence);
        return this;
    }
    
    public static Sentence not(Sentence sentence) {
        return sentence.negated();
    }
    
    public SequencedCollection<Symbol> symbols() {
        return Collections.unmodifiableSequencedCollection(symbols.sequencedValues());
    }
    
    public Model model() {
        return new Model(new LinkedHashSet<>(symbols()));
    }
    
    public boolean evaluate(Model model) {
        return
            sentences
            .stream()
            .allMatch(s -> s.evaluate(model));
    }
    
    @Override
    public String toString() {
        return name
            + symbols.values().stream().map(s -> "%n  %s: %s".formatted(s, s.comment())).collect(joining())
            + sentences.stream().map(s -> "%n  %s".formatted(s)).collect(joining());
    }
}
