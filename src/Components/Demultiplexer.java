package Components;

import Components.LogicGates.NotGate;

public class Demultiplexer extends Component {

    // Ports
    protected WireVector input;
    protected WireVector[] outputs;
    protected WireVector sel; // Select

    // Inner signals
    protected AndEnabler[] andEnablers;
    protected NotGate notSel;

    public Demultiplexer(int width, String name, Component parent) {
        super(name, parent);

        int numOutputs = 2;

        input = new WireVector(width - 1, 0, "input", this);
        sel = new WireVector(0, 0, "sel", this);
        outputs = new WireVector[numOutputs];
        andEnablers = new AndEnabler[numOutputs];
        notSel = new NotGate("notSel", this);

        for(int i = 0; i < numOutputs; i++) {
            outputs[i] = new WireVector(width - 1, 0, "output" + i, this);
            andEnablers[i] = new AndEnabler(width, "andEn" + i, this);

            input.drive(andEnablers[i]);
            andEnablers[i].drive(outputs[i]);
        }

        sel.drive(notSel);
        notSel.drive(andEnablers[0].getEna());
        sel.drive(andEnablers[1].getEna());
    }

    public WireVector getInput() {
        return input;
    }

    public WireVector getOutput(int index) {
        return outputs[index];
    }

    public WireVector getSel() {
        return sel;
    }
}
