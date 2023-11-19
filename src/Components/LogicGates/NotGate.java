package Components.LogicGates;

import Components.Component;

public class NotGate extends LogicGate {

    public NotGate(String name, Component parent) {
        super(name, parent);

        this.wires[0].setValue((byte)1);

        maxDrivers = 1;
        defaultGateValue = 1;
    }

    @Override
    protected byte gateFunction(byte a, byte b) {
        // Ignore byte a
        return (byte)(1 & ~b);
    }
}
