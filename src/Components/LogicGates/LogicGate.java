package Components.LogicGates;

import Common.Debug;
import Components.Component;
import Components.Signal;
import Components.Wire;
import Components.WireVector;

public abstract class LogicGate extends WireVector {

    protected byte defaultGateValue = 0;

    public LogicGate(String name, Component parent) {
        super(0, 0, name, parent);
        
        // This allows for infinite drivers
        maxDrivers = -1;
    }

    @Override
    public void read(WireVector vector) {
        if(this.width != vector.getWidth()) {
            Debug.fatal(this.getFullName() + ".read()", "Width mismatch");
        }

        // In case vector is a subVector
        WireVector superVector = vector.getSuperVector();

        // Add vector as driver so the logic gate uses the correct wires
        this.addDriver(vector);
        // Add this to the superVector's reader list so that when the superVector updates, this updates
        superVector.addReader(this);
    }

    @Override
    public void update() {
        Signal[] drivers = this.getDrivers();
        int initialValue = this.getValue();

        this.wires[0].setValue(this.defaultGateValue);

        for(int d = 0; d < drivers.length; d++) {
            Wire[] dWires = ((WireVector)drivers[d]).getWires();

            byte a = (byte)this.wires[0].getValue();
            byte b = (byte)dWires[0].getValue();

            byte newWireVal = this.gateFunction(a, b);

            this.wires[0].setValue(newWireVal);
        }

        hasChanged = (this.getValue() != initialValue);
    }

    protected abstract byte gateFunction(byte a, byte b);
}
