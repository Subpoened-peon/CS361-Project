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
        return States.add(new DFAState(name));
    }
    //Incomplete / Erroneous
    public boolean setFinal(String name) {
        boolean check = false;
        if (States.contains(name)) {
            FinalStates.add(new DFAState(name));
            check = true;
        }
        return check;
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
        return Alphabet.contains(s) ? true : false;
    }

    public Set<Character> getSigma() {
        return this.Alphabet;
    }

    //NOTE THE FOLLOWING
    //I'm not sure what to do if the state does not exist
    //The interface does not allow us to throw an error here
    //I returned a new state with the name requested
    @Override
    public State getState(String name) {
        for(State state : States) {
            if(state.getName().equals(name)){
                return state;
            }
        }
        return new DFAState(name);
    }

    @Override
    public boolean isFinal(String name) {
        for(State s : FinalStates) {
            if(s.getName().equals(name)) return true;
        }
        return false;
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
