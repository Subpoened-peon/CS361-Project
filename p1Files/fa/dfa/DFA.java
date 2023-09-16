package fa.dfa;

import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Map;

import fa.State;

public class DFA implements DFAInterface{

    private TreeSet<State> States = new TreeSet<State>();
    private TreeSet<State> FinalStates = new TreeSet<State>();
    private Set<Character> Alphabet = new TreeSet<Character>();
    private Map<String, Map<Character, String>> Transitions = new HashMap<String,Map<Character,String>>();
    private String stStart;
    
    //Incomplete / Erroneous
    public boolean addState(String name) {
        State stName = State(name); 
        return States.add(name);
    }
    //Incomplete / Erroneous
    public boolean setFinal(String name) {
        return FinalStates.add(name);
    }
    //Incomplete / Erroneous
    public boolean setStart(String name) {
        boolean check = false;
        if (States.contains(name)) {
            this.stStart = name;
            check = true;
        }
        return check;
    }

    public void addSigma(char symbol) {
        Alphabet.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accepts'");
    }

    public Set<Character> getSigma() {
        return this.Alphabet;
    }

    @Override
    public State getState(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getState'");
    }

    @Override
    public boolean isFinal(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isFinal'");
    }
    //Incomplete / Erroneous
    public boolean isStart(String name) {
        boolean check = false;
        if (name == stStart) {
            check = true;
        }
        return check;
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTransition'");
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'swap'");
    }
    
}
