package Components;

public class Multiplexer extends WireVector {

    protected WireVector[] inputs;
    protected WireVector sel; // Select


    public Multiplexer(int width, String name, Component parent) {
        super(width - 1, 0, name, parent);

        // TODO: make more than 2 inputs possible?
        int numInputs = 2;
        inputs = new WireVector[numInputs];

        for(int i = 0; i < numInputs; i++) {
            inputs[i] = new WireVector(width - 1, 0, "input" + i, this);
            inputs[i].addReader(this);
        }
        
        sel = new WireVector(0, 0, "sel", this);
        sel.addReader(this);

        this.maxDrivers = 0;
    }

    public WireVector getInput(int index) {
        return inputs[index];
    }

    public WireVector getSel() {
        return sel;
    }

    @Override
    public void update() {
        int initialValue = this.getValue();
        this.hasChanged = false;

        int select = sel.getValue();
        WireVector selectedInput = this.inputs[select];
        int newValue = selectedInput.getValue();

        if(newValue != initialValue) {
            this.setValue(newValue);
            this.hasChanged = true;
        }
    }    
}
