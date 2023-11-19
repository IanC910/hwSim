package Components;

public class Wire extends Signal {

    protected byte value = 0;

    public Wire(String name, Component parent) {
        super(name, parent);
    }
    
    @Override
    public int getValue() {
        return value;
    }

    public void setValue(byte value) {
        hasChanged = (value != this.value);
        
        if(value == 0) {
            this.value = 0;
        }
        else {
            this.value = 1;
        }
    }

    @Override
    public void update() {
        if(!drivers.isEmpty()) {
            Wire driver = (Wire)drivers.getFirst();
            byte newValue = (byte)driver.getValue();
            hasChanged = (newValue != this.value);
            this.value = newValue;
        }
    }

    public void read(Wire wire) {
        wire.addReader(this);
        this.addDriver(wire);
    }

    public void drive(Wire wire) {
        wire.read(this);
    }
}
