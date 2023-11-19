package Components.LogicGates;

import Components.Component;

public class XorGate extends LogicGate {
    
    public XorGate(String name, Component parent) {
        super(name, parent);

        defaultGateValue = 0;
    }

    @Override
    protected byte gateFunction(byte a, byte b) {
        return (byte)(1 & (a ^ b));
    }
}
