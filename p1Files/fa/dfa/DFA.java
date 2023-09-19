package fa.dfa;

import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fa.State;

public class DFA implements DFAInterface{

    private TreeSet<State> States;
    private TreeSet<State> FinalStates;
    private TreeSet<Character> Alphabet;
    private Map<State, Map<Character, State>> Transitions;
    private State stStart;

    //RETURN FOR JAVADOC
    public DFA() {
        this.stStart = null;
        this.States = new TreeSet<State>();
        this.FinalStates = new TreeSet<State>();
        this.Alphabet = new TreeSet<Character>();
        this.Transitions  = new HashMap<State,Map<Character,State>>();
    }

    //RETURN FoR JAVADoC
    public DFA(TreeSet<State> States, TreeSet<State> FinalStates, TreeSet<Character> Alphabet, 
    Map<State, Map<Character, State>> Transitions, State stStart) {
        this.stStart = stStart;
        this.States = States;
        this.FinalStates = FinalStates;
        this.Alphabet = Alphabet;
        this.Transitions  = Transitions;
    }

    public boolean addState(String name) {
        boolean check = false;
        Iterator<State> stCheck = States.iterator();
        while(stCheck.hasNext()) {
            if(!stCheck.next().getName().equals(name)) {
                States.add(new DFAState(name));
                check = true;
            }
        }
        return check;
    }
    
    public boolean setFinal(String name) {
        boolean check = false;
        Iterator<State> stCheck = States.iterator();
        while(stCheck.hasNext()) {
            if(stCheck.next().getName().equals(name)) {
                FinalStates.add(new DFAState(name));
                check = true;
            }
        }
        return check;
    }
    
    public boolean setStart(String name) {
        boolean check = false;
        Iterator<State> stCheck = States.iterator();
        while(stCheck.hasNext()) {
            if(stCheck.next().getName().equals(name)) {
                this.stStart = stCheck.next();
                check = true;
            }
        }
        return check;
    }

    public void addSigma(char symbol) {
        Alphabet.add(symbol);
    }
    //return to//
    public boolean accepts(String s) {
        
    }

    public Set<Character> getSigma() {
        return this.Alphabet;
    }

    public State getState(String name) {
        for(State state : States) {
            if(state.getName().equals(name)){
                return state;
            }
        }
        return null;
    }

    public boolean isFinal(String name) {
        boolean check = false;
        for(State s : FinalStates) {
            if(s.getName().equals(name));
            check = true;
        }
        return check;
    }

    public boolean isStart(String name) {
        boolean check = false;
        if (name == stStart.getName()) {
            check = true;
        }
        return check;
    }

    public boolean addTransition(String fromState, String toState, char onSymb) {
        boolean check = false;
        State from = null;
        State to = null;
        Iterator<State> stCheck = States.iterator();
        while(stCheck.hasNext()) {
            if(stCheck.next().getName().equals(fromState)) {
                from = stCheck.next();
            }
            if(from.getName() == toState) {
                to = from;
            }
            if(stCheck.next().getName().equals(toState)) {
                to = stCheck.next();
            }
        }
        if(from != null && to != null && Alphabet.contains(onSymb)) {
            check = true;
            Map<Character, State> destination = new HashMap<Character, State>();
            destination.put(onSymb, to);
            Transitions.put(from, destination);
        }
        return check;
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'swap'");
    }
    
}
