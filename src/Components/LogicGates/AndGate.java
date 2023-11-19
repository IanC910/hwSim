package Components.LogicGates;

import Components.Component;

public class AndGate extends LogicGate {

    public AndGate(String name, Component parent) {
        super(name, parent);

        defaultGateValue = 1;
    }

    @Override
    protected byte gateFunction(byte a, byte b) {
        return (byte)(1 & (a & b));
    }
}
