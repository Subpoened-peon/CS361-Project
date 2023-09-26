package fa.dfa;

import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
        char currentTransition;
        State currentState = stStart;

        while(charIndex < s.length()) {
            currentTransition = s.charAt(charIndex);
            for(int i = 0; i < Transitions.get(currentState).size(); i++) {
                //ugliest condition of all time from Kai
                if(Transitions.get(currentState).get(i).containsKey(currentTransition)) {
                    currentState = Transitions.get(currentState).get(i).get(currentTransition);
                    break;
                } else if(i == Transitions.get(currentState).size() - 1) {
                    return false;
                }
            }

            charIndex++;
        }
 
        return this.isFinal(currentState.getName());
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
        DFA swap;
        for (List<Map<Character, DFAState>> stateTransitions : Transitions.values()) {
            for (Map<Character, DFAState> transition : stateTransitions) {
                DFAState stateForSymb1 = transition.get(symb1);
                DFAState stateForSymb2 = transition.get(symb2);

                // Swap the transitions for the symbols
                if (stateForSymb1 != null && stateForSymb2 != null) {
                    transition.put(symb1, stateForSymb2);
                    transition.put(symb2, stateForSymb1);
                }
            }
        }
        swap = new DFA(this.States, this.FinalStates, this.Alphabet, Transitions, this.stStart);
        return swap;
    }
    
    /**
     * a to string method that prints out the set of DFAStates, the alphabet, the initial state,
     * the set of final states, and the transitions for each state.
     * @return a string of the 5-tuple variables
     */
    public String toString() {
        String variables = "Q={ ";
        String Q = "";
        String sigma = "";
        Stack<String> stStack = new Stack<>();
        Stack<String> fiStack = new Stack<>();

        for(DFAState state : States) {
            stStack.push(state.getName());
        }
        while(!stStack.isEmpty()) {
            Q += stStack.pop() + " ";
        }

        variables += Q + "}\nSigma = { ";

        for(Character syms : Alphabet) {
            sigma += syms + " ";
        }

        variables += sigma + "}\ndelta =\n \t" + sigma + "\n";

        for(DFAState state : States) {
            
        }

        variables += "q0 = " + stStart.getName() + "\n" + "F = { ";

        for(DFAState state : FinalStates) {
            fiStack.push(state.getName());
        }
        while(!fiStack.isEmpty()) {
            variables += fiStack.pop() + " ";
        }
        variables += "}";

        return variables;
    }
}
