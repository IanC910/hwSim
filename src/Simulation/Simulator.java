
package Simulation;

import java.util.LinkedList;
import java.util.PriorityQueue;

import Common.Debug;
import Components.*;

public class Simulator {

    // Event queue sorted by start time
    protected PriorityQueue<InputEvent> eventQ = null;
    protected LinkedList<Signal> signalUpdateQ = null;
    protected LinkedList<Register> risingEdgeQ = null;

    protected int currentTimeNs = 0;
    
    public Simulator() {
        signalUpdateQ = new LinkedList<>();
        risingEdgeQ = new LinkedList<>();
        eventQ = new PriorityQueue<>();
    }

    public void addInputEvent(WireVector signalToAffect, int valueToAssign, int startTimeNsFromNow) {
        InputEvent event = new InputEvent(startTimeNsFromNow + this.getCurrentTimeNs(), signalToAffect, valueToAssign);
        eventQ.add(event);
    }

    public int getCurrentTimeNs() {
        return currentTimeNs;
    }

    public void runSimForNs(int runTimeNs) {
        if(runTimeNs < 0) {
            Debug.fatal("Simulator.runSimForNs()", "Negative run time");
        }

        int endTimeNs = currentTimeNs + runTimeNs;
        boolean stopSim = false;
        boolean allQsEmpty = false;
        boolean nextEventAfterEndTime = false;

        // Main Simulation Loop
        while(!stopSim) {

            if(!eventQ.isEmpty()) {
                InputEvent event = eventQ.peek();
                WireVector signalToAffect = event.getSignalToAffect();
                int valueToSet = event.getValueToSet();

                if(event.getStartTimeNs() < endTimeNs) {
                    eventQ.poll();
                
                    if(valueToSet == signalToAffect.getValue()) {
                        continue;
                    }

                    signalToAffect.setValue(valueToSet);
                    addReadersToUpdateQ(signalToAffect);
                }
                else {
                    nextEventAfterEndTime = true;
                }

                // TODO: use time for waveform creation
            }

            // While at least 1 queue is not empty
            while(!signalUpdateQ.isEmpty() || !risingEdgeQ.isEmpty()) {

                while(!signalUpdateQ.isEmpty()) {
                    Signal signal = signalUpdateQ.removeFirst();

                    // Update Component
                    signal.update();

                    // Only add readers to update q if the component value changed
                    if(signal.getHasChanged()) {
                        addReadersToUpdateQ(signal);
                    }
                }

                while(!risingEdgeQ.isEmpty()) {
                    Register register = risingEdgeQ.removeFirst();

                    register.onRisingEdge();

                    // Only add readers to update q if the component value changed
                    if(register.getHasChanged()) {
                        addReadersToUpdateQ(register);
                    }
                }
            } // while at least 1 queue is not empty 

            allQsEmpty = eventQ.isEmpty() && signalUpdateQ.isEmpty() && risingEdgeQ.isEmpty();
            stopSim = nextEventAfterEndTime || allQsEmpty;
        } // Main Simulation Loop

        this.currentTimeNs = endTimeNs;
    }

    protected void addReadersToUpdateQ(Signal signal) {
        Signal[] readers = signal.getReaders();

        for(int i = 0; i < readers.length; i++) {
            // Check for reader type
            if(readers[i] instanceof Register) {
                Register readerAsReg = (Register)readers[i];

                // Check what triggered the reg update
                // CLK rising edge
                if(signal == readerAsReg.getClk() && signal.getValue() == 1) {
                    risingEdgeQ.add(readerAsReg);
                }
                // Asynchronous reset
                else if(signal == readerAsReg.getClr() && signal.getValue() == 1) {
                    if(!signalUpdateQ.contains(readerAsReg)) {
                        signalUpdateQ.add(readerAsReg);
                    }
                }
            }
            else {
                if(readers[i] instanceof Signal) {
                    if(!signalUpdateQ.contains(readers[i])) {
                        signalUpdateQ.add(readers[i]);
                    }
                }
            }
        }
    }
}
