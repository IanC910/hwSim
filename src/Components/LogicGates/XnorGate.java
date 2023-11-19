package Components.LogicGates;

import Components.Component;

public class XnorGate extends XorGate {

    public XnorGate(String name, Component parent) {
        super(name, parent);

        defaultGateValue = 1;
    }
}
