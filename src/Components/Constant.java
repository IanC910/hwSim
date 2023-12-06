package Components;

public class Constant extends WireVector {
    
    public Constant(int msbIndex, int lsbIndex, VectorType vectorType, String name, Component parent, int value) {
        super(msbIndex, lsbIndex, vectorType, name, parent);

        this.maxDrivers = 0;
        this.setValue(value);
    }

    @Override
    public void update() {}
}
