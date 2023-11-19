package Components;

import java.util.LinkedList;

import Common.Debug;

// Class Signal
// Signals are Components with a single characteristic value
// They can also have readers and drivers
// E.g., signals, registers, logic gates

public abstract class Signal extends Component {
    
    protected LinkedList<Signal> readers;
    protected LinkedList<Signal> drivers;
    protected int maxDrivers = 1;

    protected boolean hasChanged = true;
    protected boolean initialized = false;

    public Signal(String name, Component parent) {
        super(name, parent);
        readers = new LinkedList<>();
        drivers = new LinkedList<>();
    }



    public Signal[] getReaders() {
        return readers.toArray(new Signal[readers.size()]);
    }
    
    public void addReader(Signal reader) {
        if(!readers.contains(reader)) {
            readers.add(reader);
        }
    }

    public Signal[] getDrivers() {
        return drivers.toArray(new Signal[drivers.size()]);
    }
    
    public void addDriver(Signal driver) {
        if(!drivers.contains(driver)) {
            if(drivers.size() < maxDrivers || maxDrivers < 0) {
                drivers.add(driver);
            }
            else {
                Debug.fatal(getFullName() + ".addDriver()", "Too many drivers");
            }
        }
    }

    abstract public int getValue();
    
    abstract public void update();

    public void init() {
        this.update();

        if(this.getHasChanged() || !initialized) {
            initialized = true;

            Signal[] readers = this.getReaders();

            for(int i = 0; i < readers.length; i++) {
                readers[i].init();
            }
        }
    }

    public boolean getHasChanged() {
        return hasChanged;
    }
}
