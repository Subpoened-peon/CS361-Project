package fa.dfa;

import  fa.State;

public class DFAState /*hi this is kai*/ extends State implements Comparable<DFAState>{

    public DFAState(String name) {
        super(name);
    }

    @Override
    public int compareTo(DFAState s) {
        return s.getName().compareTo(this.getName());
    }
    
}
