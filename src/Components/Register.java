package Components;

public class Register extends WireVector {

    protected WireVector clk; // Clock
    protected WireVector ena; // Enable
    protected WireVector clr; // Asynchronous clear
    protected WireVector sclr; // Synchronous clear

    public Register(int upperIndex, int lowerIndex, VectorType vectorType, String name, Component parent) {
        super(upperIndex, lowerIndex, vectorType, name, parent);
        clk     = new WireVector(0, 0, "clk", this);
        ena     = new WireVector(0, 0, "ena", this);
        clr     = new WireVector(0, 0, "clr", this);
        sclr    = new WireVector(0, 0, "sclr", this);

        clk.addReader(this);
        clr.addReader(this);

        ena.setValue(1);
    }

    public Register(int upperIndex, int lowerIndex, String name, Component parent) {
        this(upperIndex, lowerIndex, VectorType.UNSIGNED, name, parent);
    }

    public WireVector getClk() {
        return clk;
    }

    public WireVector getEna() {
        return ena;
    }

    public WireVector getClr() {
        return clr;
    }

    public WireVector getSclr() {
        return sclr;
    }



    @Override
    public void update() {
        hasChanged = false;

        if(clr.getValue() == 1) {
            if(this.getValue() != 0) {
                hasChanged = true;
                this.setValue(0);
            }
        }
    }

    public void onRisingEdge() {
        hasChanged = false;

        if(sclr.getValue() == 1 || clr.getValue() == 1) {
            if(this.getValue() != 0) {
                hasChanged = true;
                this.setValue(0);
            }
        }
        else if(ena.getValue() == 1) {
            // super.update sets hasChanged
            super.update();
        }
    }
}
