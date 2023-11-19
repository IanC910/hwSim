package Components;

import java.lang.Math;

import Common.*;

public class WireVector extends Signal {

    protected int msbIndex = 0;
    protected int lsbIndex = 0;
    protected int width = 1;
    protected Wire[] wires;

    public enum VectorType {
        UNSIGNED,
        SIGNED
    }

    protected VectorType vectorType = VectorType.UNSIGNED;
    protected int maxValue = 1;
    protected int minValue = 0;

    // TODO: support multiple endiannesses
    public WireVector(int msbIndex, int lsbIndex, VectorType vectorType, String name, Component parent) {
        super(name, parent);

        this.width = Math.abs(msbIndex - lsbIndex) + 1;
        if(width < 1) {
            Debug.fatal(this.getFullName() + "WireVector()", "Vector width < 1");
        }
        this.msbIndex = msbIndex;
        this.lsbIndex = lsbIndex;

        wires = new Wire[width];
        for(int i = 0; i < width; i++) {
            wires[i] = new Wire(name + "[" + (i + lsbIndex) + "]", this);
        }

        if(vectorType == VectorType.SIGNED) {
            this.maxValue = (int)Math.pow(2, width - 1) - 1;
            this.minValue = -this.maxValue - 1;
        }
        else {
            this.maxValue = (int)Math.pow(2, width) - 1;
        }

        this.maxDrivers = this.width;
    }

    public WireVector(int msbIndex, int lsbIndex, String name, Component parent) {
        this(msbIndex, lsbIndex, VectorType.UNSIGNED, name, parent);
    }

    

    public int getWidth() {
        return width;
    }

    public int getMsbIndex() {
        return msbIndex;
    }

    public int getLsbIndex() {
        return lsbIndex;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    @Override
    public int getValue() {
        int value = 0;

        for(int i = this.width - 1; i >= 0; i--) {
            value = 2 * value + (int)wires[i].getValue();
        }

        return value;
    }

    public void setValue(int value) {
        if(value < minValue || value > maxValue) {
            Debug.fatal(this.getFullName() + ".setValue()", "Value outside possible range");
        }

        hasChanged = false;

        if(vectorType == VectorType.SIGNED) {
            // TODO: add Signed vector support
        }
        else {
            for(int i = 0; i < this.width; i++) {
                wires[i].setValue((byte)(value % 2));
                value /= 2;
                
                hasChanged |= wires[i].getHasChanged();
            }
        }
    }

    public Wire getWire(int index) {
        if(index < lsbIndex || index > msbIndex) {
            Debug.fatal(this.getFullName() + ".getWire()", "Invalid index");
        }

        if(lsbIndex < msbIndex) {
            return wires[index - lsbIndex];
        }
        return wires[index - msbIndex];
    }

    public Wire[] getWires() {
        return wires;
    }

    public WireVector getSubVector(int msbIndex, int lsbIndex) {
        if( msbIndex > this.msbIndex ||
            lsbIndex > this.msbIndex ||
            msbIndex < this.lsbIndex ||
            lsbIndex < this.lsbIndex
        ) {
            Debug.fatal(this.getFullName() + ".getSubVector()", "Invalid indices");
        }

        int subWidth = Math.abs(msbIndex - lsbIndex) + 1;

        WireVector subVector = new WireVector(
            msbIndex,
            lsbIndex,
            this.vectorType,
            this.name + "[" + msbIndex + ":" + lsbIndex + "]",
            this.getSuperVector()
        );

        int mainIndex = lsbIndex;
        int mainIncrement = 0;
        if(msbIndex != lsbIndex) {
            mainIncrement = (msbIndex - lsbIndex) / Math.abs(msbIndex - lsbIndex); // +1 if upper > lower, else -1
        }
        for(int i = 0; i < subWidth; i++) {
            // Throw out the wire objects in the subVector object, replace with main vector's wire objects
            subVector.wires[i] = this.getWire(mainIndex);
            mainIndex += mainIncrement;
        }

        return subVector;
    }

    public WireVector getSuperVector() {
        return (WireVector)this.wires[0].getParent();
    }

    public void read(WireVector vector) {
        if(this.width != vector.getWidth()) {
            Debug.fatal(this.getFullName() + ".read()", "Width mismatch");
        }
        for(int i = 0; i < this.width; i++) {
            this.wires[i].read(vector.wires[i]);
        }

        // In case this or vector are subVectors
        WireVector superVector = vector.getSuperVector();
        WireVector superThis = this.getSuperVector();

        superThis.addDriver(superVector);
        superVector.addReader(superThis);
    }

    public void drive(WireVector vector) {
        vector.read(this);
    }
    
    @Override
    public void update() {
        if(this.getSuperVector() != this) {
            Debug.fatal(this.getFullName() + ".update()", "Update called on subVector");
        }

        hasChanged = false;
        for(int i = 0; i < this.width; i++) {
            wires[i].update();
            hasChanged |= wires[i].getHasChanged();
        }
    }
}
