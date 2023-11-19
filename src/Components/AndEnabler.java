package Components;

public class AndEnabler extends WireVector {

    protected WireVector ena; // Enable

    public AndEnabler(int width, String name, Component parent) {
        super(width - 1, 0, name, parent);
        ena = new WireVector(0, 0, "ena", this);

        ena.addReader(this);
    }

    public WireVector getEna() {
        return ena;
    }

    @Override
    public void update() {
        if(ena.getValue() == 1) {
            // super.update sets hasChanged
            super.update();
        }
        else {
            // If not enabled, this has changed if it is newly not enabled, i.e. ena has changed
            hasChanged = ena.getHasChanged();
            this.setValue(0);
        }
    }
    
}
