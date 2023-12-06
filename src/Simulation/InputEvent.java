package Simulation;

import Components.WireVector;
import Common.Debug;

public class InputEvent implements Comparable<InputEvent> {

    protected int startTimeNs = 0;
    protected WireVector signalToAffect = null;
    protected int valueToSet = 0;

    public InputEvent(int startTimeNs, WireVector signalToAffect, int valueToAssign) {
        if(signalToAffect == null) {
            Debug.fatal("InputEvent", "Null signal");
        }
        
        this.startTimeNs = startTimeNs;
        this.signalToAffect = signalToAffect;
        this.valueToSet = valueToAssign;
    }

    public int getStartTimeNs() {
        return this.startTimeNs;
    }

    public WireVector getSignalToAffect() {
        return this.signalToAffect;
    }

    public int getValueToSet() {
        return this.valueToSet;
    }

    @Override
    public int compareTo(InputEvent event) {
        if(this.startTimeNs == event.getStartTimeNs()) {
            return 0;
        }
        else if(this.startTimeNs < event.getStartTimeNs()) {
            return -1;
        }
        return 1;
    }
}
