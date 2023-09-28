/*
 * Copyright: Carlos F. Heuberger. All rights reserved.
 *
 */
package cfh.logic;

import java.util.Set;

import cfh.logic.proposition.Knowledge;
import cfh.logic.proposition.Model;

/**
 * @author Carlos F. Heuberger, 2023-09-28
 *
 */
public class PropositionCheck extends Check {

    public static void main(String[] args) {
        symbolCheck();
        modelCheck();
        connectiveCheck();
        
        var knowledge = new Knowledge("Knowledge Check") {
            {
                var P = symbol("P", "it is Tuesday");
                var Q = symbol("Q", "it is raining");
                var R = symbol("R", "go to run");

                add( P.and(not(Q)).implies(R) ); // if it is Tuesday and not raining, go to run
            }            
        };
        System.out.println(knowledge);
    }
    
    static void symbolCheck() {
        var knowledge = new Knowledge("Symbol Check");
        var P = knowledge.symbol("P");
        var Q = knowledge.symbol("Q", "with comment");
        System.out.println(knowledge);
        assertEquals(Set.of(P, Q), Set.copyOf(knowledge.symbols()));
        System.out.println();
    }
    
    static void modelCheck() {
        var knowledge = new Knowledge("Model Check");
        var P = knowledge.symbol("P");
        var Q = knowledge.symbol("Q", "with comment");
        System.out.println(knowledge);
        
        Model model = knowledge.model();
        System.out.println("Model:\n" + model);
        model.set(P);
        System.out.println("Set P\n" + model);
        model.unset(P).set(Q, true);
        System.out.println("Unset P, Q=true\n" + model);
        var was = model.flip(P);
        System.out.println("Flip P, was " + was + "\n" + model);
        System.out.println();
        
        var format = "%-5s  %-5s%n";
        model.reset();
        System.out.printf(format, P, Q);
        System.out.printf(format, "=====", "=====");
        do {
            System.out.printf(format, model.get(P), model.get(Q));
        } while (model.next());
        System.out.printf(format, "-----", "-----");
        System.out.printf(format, model.get(P), model.get(Q));
        System.out.println();
    }
    
    @SuppressWarnings("unused")
    static void connectiveCheck() {
        new Knowledge("Connective Check") {
            {
                var P = symbol("P");
                var Q = symbol("Q");
                
                var model = model().set(Q);
                System.out.println(this);
                System.out.println(model);
                
                var format = " %-5.5s  %-5.5s  |  %-5.5s  %-5.5s  %-5.5s  %-5.5s  %-5.5s %n";
                System.out.printf(format, "  P  ", "  Q  ", " ¬ P ", "P ∧ Q", "P ∨ Q", "P ⟶ Q", "P ⟷ Q");
                System.out.printf(format, "=====", "=====", "=====", "=====", "=====", "=====", "=====");
                var not = not(P);
                var and = P.and(Q);
                var or = P.or(Q);
                var implies = P.implies(Q);
                var iff = P.iff(Q);
                model.reset();
                do {
                    System.out.printf(format, model.get(P), model.get(Q), not.evaluate(model), 
                        and.evaluate(model), or.evaluate(model), implies.evaluate(model), iff.evaluate(model));
                } while (model.next());
                System.out.println();
            }
        };
    }
    
    static void sentenceCheck() {
        
    }
    
    static void baseCheck() {
        // TODO ?
    }
    
    static void entailmentCheck() {
        // TODO (⊨ \u22A8) ?
    }
}
