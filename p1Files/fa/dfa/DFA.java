package fa.dfa;

import java.util.Set;
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
    private ArrayList<String> Q;
    private ArrayList<String> Final;
    private ArrayList<Character> Sigma;

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
        this.Q = new ArrayList<String>();
        this.Final = new ArrayList<String>();
        this.Sigma = new ArrayList<Character>();
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
        this.Q.add(name);
        this.States.add(new DFAState(name));
        return true;
    }
    
    public boolean setFinal(String name) {
        boolean check = false;
        Iterator<DFAState> stCheck = States.iterator();
        while(stCheck.hasNext()) {
            if(stCheck.next().getName().equals(name)) {
                Final.add(name);
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
        this.Sigma.add(symbol);
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

    public DFAState getState(String name) {
        for(DFAState state : States) {
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

    private void copyStart(DFAState s) {
        stStart = s;
    }

    public DFA swap(char symb1, char symb2) {
        DFA swap = new DFA();
        swap.copyStart(stStart);
        swap.setStart(stStart.getName());
        for(DFAState state : States) {
            swap.addState(state.getName());
        }
        for(DFAState state : FinalStates) {
            swap.setFinal(state.getName());
        }
        for(Character c : Alphabet) {
            swap.addSigma(c.charValue());
        }
        for(DFAState key : Transitions.keySet()) {
            for(Map<Character, DFAState> element : Transitions.get(key)) {
                for(Character c : element.keySet()) {
                    if(c == symb1){
                        swap.addTransition(key.getName(),  element.get(c).getName(), symb2);
                    } else if (c == symb2){
                        swap.addTransition(key.getName(),  element.get(c).getName(), symb1);
                    } else {
                        swap.addTransition(key.getName(),  element.get(c).getName(), c);
                    }
                }
            }
        }
        return swap;
    }
    
    /**
     * a to string method that prints out the set of DFAStates, the alphabet, the initial state,
     * the set of final states, and the transitions for each state.
     * @return a string of the 5-tuple variables
     */
    public String toString() {
        String variables = "Q={ ";
        String sigma = "";
        for (int i = 0; i < Q.size(); i++) {
            variables += Q.get(i) + " ";
        }
        variables += "}\n Sigma = { ";
        for (int i = 0; i < Sigma.size(); i++) {
            variables += Sigma.get(i) + " ";
        }

        variables += sigma + "}\ndelta =\n \t";
        for (int i = 0; i < Sigma.size(); i++) {
            variables += Sigma.get(i) + " ";
        }
        variables += "\n";

        for (int i = 0; i < Q.size(); i++) {
            variables += Q.get(i) + "\t";
            DFAState state = this.getState(Q.get(i));
            for (int j = 0; j < Sigma.size(); j++) {
                ArrayList<Map<Character, DFAState>> transition = Transitions.get(state);
                char symbol = Sigma.get(j);
                
                boolean transitionFound = false; // Flag to check if a transition was found
                
                for (int k = 0; k < transition.size(); k++) {
                    Map<Character, DFAState> symbolTransition = transition.get(k);
                    
                    if (symbolTransition.containsKey(symbol)) {
                        DFAState nextState = symbolTransition.get(symbol);
                        variables += nextState.getName() + " ";
                        transitionFound = true;
                        break; // Exit the loop once a valid transition is found
                    }
                }
                
                if (!transitionFound) {
                    variables += "e ";
                }
            }
            variables += "\n";
        }

        variables += "q0 = " + stStart.getName() + "\n" + "F = { ";
        for (int i = 0; i < Final.size(); i++) {
            variables += Final.get(i) + " ";
        }
        variables += "}";

        return variables;
    }
}
