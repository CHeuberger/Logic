/*
 * Copyright: Carlos F. Heuberger. All rights reserved.
 *
 */
package cfh.logic.proposition;

import static java.util.Objects.*;

/**
 * @author Carlos F. Heuberger, 2023-09-28
 *
 */
public interface Sentence {

    public Sentence negated();
    public Sentence and(Sentence sentence);
    public Sentence or(Sentence sentence);
    public Sentence implies(Sentence sentence);
    public Sentence iff(Sentence sentence);
    
    public boolean evaluate(Model model);
}

abstract sealed class SentenceImpl implements Sentence 
permits SymbolImpl, NotSentence, AndSentence, OrSentence, ImpliesSentence, IffSentence {

    @Override
    public Sentence negated() {
        return new NotSentence(this);
    }

    @Override
    public Sentence and(Sentence sentence) {
        return new AndSentence(this, sentence);
    }
    
    @Override
    public Sentence or(Sentence sentence) {
        return new OrSentence(this, sentence);
    }

    @Override
    public Sentence implies(Sentence sentence) {
        return new ImpliesSentence(this, sentence);
    }
    
    @Override
    public Sentence iff(Sentence sentence) {
        return new IffSentence(this, sentence);
    }
}

final class NotSentence extends SentenceImpl {
    
    private final SentenceImpl sentence;
    
    NotSentence(Sentence sentence) {
        this.sentence = (SentenceImpl) requireNonNull(sentence, "null sentence");
    }
    
    @Override
    public boolean evaluate(Model model) {
        return ! sentence.evaluate(model);
    }
    
    @Override
    public String toString() {
        return "¬" + sentence;
    }
}

final class AndSentence extends SentenceImpl {
    
    private final Sentence op1;
    private final Sentence op2;
    
    AndSentence(Sentence op1, Sentence op2) {
        this.op1 = requireNonNull(op1, "null op1");
        this.op2 = requireNonNull(op2, "null op2");
    }
    
    @Override
    public boolean evaluate(Model model) {
        return op1.evaluate(model) && op2.evaluate(model);
    }
    
    @Override
    public String toString() {
        return "(%s ∧ %s)".formatted(op1, op2);
    }
}

final class OrSentence extends SentenceImpl {
    
    private final Sentence op1;
    private final Sentence op2;
    
    OrSentence(Sentence op1, Sentence op2) {
        this.op1 = requireNonNull(op1, "null op1");
        this.op2 = requireNonNull(op2, "null op2");
    }
    
    @Override
    public boolean evaluate(Model model) {
        return op1.evaluate(model) || op2.evaluate(model);
    }
    
    @Override
    public String toString() {
        return "(%s ∨ %s)".formatted(op1, op2);
    }
}

final class ImpliesSentence extends SentenceImpl {
    
    private final Sentence op1;
    private final Sentence op2;
    
    ImpliesSentence(Sentence op1, Sentence op2) {
        this.op1 = requireNonNull(op1, "null op1");
        this.op2 = requireNonNull(op2, "null op2");
    }
    
    @Override
    public boolean evaluate(Model model) {
        return !op1.evaluate(model) || op2.evaluate(model);
    }

    @Override
    public String toString() {
        return "(%s ⟶ %s)".formatted(op1, op2);
    }
}

final class IffSentence extends SentenceImpl {
    
    private final Sentence op1;
    private final Sentence op2;
    
    IffSentence(Sentence op1, Sentence op2) {
        this.op1 = requireNonNull(op1, "null op1");
        this.op2 = requireNonNull(op2, "null op2");
    }
    
    @Override
    public boolean evaluate(Model model) {
        return op1.evaluate(model) == op2.evaluate(model);
    }

    @Override
    public String toString() {
        return "(%s ⟷ %s)".formatted(op1, op2);
    }
}
