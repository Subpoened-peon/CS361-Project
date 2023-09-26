package fa.dfa;

import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;

import fa.State;
/**
 * This class is a software representation to a DFA machine that implements the DFAInterface. 
 * It contains the states (which are stored as DFAState objects), which are final and which is the initial, 
 * the alphabet and the transitions that the states have. It allows allows for checking if strings are accepted
 * in the created machine as well as swapping transitions based on the symbol.
 * @author Nicholas Merritt
 * @author Kai Sorensen
 * @version best one
 * @since forever
 */
public class DFA implements DFAInterface{

    private TreeSet<DFAState> States;
    private TreeSet<DFAState> FinalStates;
    private TreeSet<Character> Alphabet;
    private Map<DFAState, ArrayList<Map<Character, DFAState>>> Transitions;
    private DFAState stStart;

    /**
     * This constructor is used to create a new machine from scratch. Most often used for the testing suite where
     * values are added "manually" after the DFA is instantiated. 
     */
    public DFA() {
        this.stStart = null;
        this.States = new TreeSet<DFAState>();
        this.FinalStates = new TreeSet<DFAState>();
        this.Alphabet = new TreeSet<Character>();
        this.Transitions  = new HashMap<DFAState,ArrayList<Map<Character,DFAState>>>();
    }

    /**
     * A constructor that takes the values of another DFA. This constructor is used for creating the swap DFA copy
     * used for the swap method.
     * 
     * @param States The set of states copied from the original DFA
     * @param FinalStates The set of Final states copied from the original DFA
     * @param Alphabet The set of symbols of the alphabet from the original DFA
     * @param Transitions The Transitions that existed for the states of the original DFA
     * @param stStart The initial state for the original DFA
     */
    public DFA(TreeSet<DFAState> States, TreeSet<DFAState> FinalStates, TreeSet<Character> Alphabet, 
    Map<DFAState, ArrayList<Map<Character, DFAState>>> Transitions, DFAState stStart) {
        this.stStart = stStart;
        this.States = States;
        this.FinalStates = FinalStates;
        this.Alphabet = Alphabet;
        this.Transitions  = Transitions;
    }

    public boolean addState(String name) {
        
        Iterator<DFAState> stCheck = States.iterator();
        while(stCheck.hasNext()) {
            if(stCheck.next().getName().equals(name)) {
                return false;
            }
        }
        States.add(new DFAState(name));
        return true;
    }
    
    public boolean setFinal(String name) {
        boolean check = false;
        Iterator<DFAState> stCheck = States.iterator();
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
        Iterator<DFAState> stCheck = States.iterator();


        while(stCheck.hasNext()) {
            DFAState current = stCheck.next();
            if(current.getName().equals(name)) {
                this.stStart = current;
                check = true;
            }
        }
        return check;
    }

    public void addSigma(char symbol) {
        Alphabet.add(symbol);
    }
    
    public boolean accepts(String s) {
        int charIndex = 0;
        char currentTransition = s.charAt(charIndex);
        State currentState = stStart;

        outerloop:
        while(charIndex <= s.length()) {
            //if there exists a transition from the current state on the current transition
            for(int i = 0; i < Transitions.get(currentState).size(); i++) 
            {
                if(Transitions.get(currentState).get(i).containsKey(currentTransition) && charIndex < s.length()) {
                    currentState = Transitions.get(currentState).get(i).get(currentTransition);
                    currentTransition = s.charAt(charIndex++);
                    continue outerloop;
                }
            }

            //if we're at the final state and we've gone through the whole string
            if (this.isFinal(currentState.getName()) && charIndex == s.length()){
                return true;
            } else {
                return false;
            }
        }
        return false;
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
            if(s.getName().equals(name)) {
                check = true;
            }
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
        DFAState from = null;
        DFAState to = null;
        Iterator<DFAState> stCheck = States.iterator();
        while(stCheck.hasNext()) {
            DFAState current = stCheck.next();
            if(current.getName().equals(fromState)){
                from = current;
            }
            if(current.getName().equals(toState)){
                to = current;
            }
        }
        if(from != null && to != null && Alphabet.contains(onSymb)) {
            check = true;
            //This instance acts as a transition without a starting state.
            Map<Character, DFAState> destination = new HashMap<Character, DFAState>();
            destination.put(onSymb, to);
            if(Transitions.containsKey(from)) {
                Transitions.get(from).add(destination);
            } else {
                ArrayList<Map<Character, DFAState>> newTransition = new ArrayList<Map<Character, DFAState>>();
                newTransition.add(destination);
                Transitions.put(from, newTransition);
            }
        }
        return check;
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'swap'");
    }
    
}
