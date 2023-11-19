package Tests.BasicTests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Components.Register;
import Components.WireVector;
import Components.WireVector.VectorType;

public class RegisterTest {
    
    @Test
    public void constructorTest() {
        Register reg = new Register(15, 8, VectorType.UNSIGNED, "reg", null);

        assertEquals("reg", reg.getName());

        assertEquals("reg.reg[8]", reg.getWires()[0].getFullName());
        assertEquals(8, reg.getWidth());
        assertEquals(0, reg.getValue());

        assertEquals(0, reg.getMinValue());
        assertEquals(255, reg.getMaxValue());

        assertEquals(reg, reg.getClk().getParent());
        assertEquals(reg, reg.getClk().getReaders()[0]);
    }

    @Test
    public void updateAndRisingEdgeTest() {
        Register reg = new Register(7, 0, "reg", null);
        WireVector vec = new WireVector(7, 0, "vec", null);

        vec.drive(reg);

        // Begin; values should be 0
        assertEquals(0, vec.getValue());
        assertEquals(0, reg.getValue());

        // Set vec; expect no change to reg without rising edge
        vec.setValue(100);

        assertEquals(100, vec.getValue());
        assertEquals(0, reg.getValue());
        assertEquals(true, vec.getHasChanged());
        assertEquals(true, reg.getHasChanged());

        // Update reg. Expect no change because async reset low and not rising edge
        reg.update();

        assertEquals(100, vec.getValue());
        assertEquals(0, reg.getValue());
        assertEquals(true, vec.getHasChanged());
        assertEquals(false, reg.getHasChanged());

        // Rising Edge; expect no change because not enabled
        reg.getEna().setValue(0);
        reg.onRisingEdge();

        assertEquals(100, vec.getValue());
        assertEquals(0, reg.getValue());
        assertEquals(true, vec.getHasChanged());
        assertEquals(false, reg.getHasChanged());

        // Enable and rising edge; reg should latch value
        reg.getEna().setValue(1);
        reg.onRisingEdge();

        assertEquals(100, vec.getValue());
        assertEquals(100, reg.getValue());
        assertEquals(true, vec.getHasChanged());
        assertEquals(true, reg.getHasChanged());

        // async reset high; expect no change without update
        reg.getClr().setValue(1);

        assertEquals(100, vec.getValue());
        assertEquals(100, reg.getValue());
        assertEquals(true, vec.getHasChanged());
        assertEquals(true, reg.getHasChanged());

        // Update; reg should clear
        reg.update();

        assertEquals(100, vec.getValue());
        assertEquals(0, reg.getValue());
        assertEquals(true, vec.getHasChanged());
        assertEquals(true, reg.getHasChanged());

        // Async reset low; expect no change without update or rising edge
        reg.getClr().setValue(0);

        assertEquals(100, vec.getValue());
        assertEquals(0, reg.getValue());
        assertEquals(true, vec.getHasChanged());
        assertEquals(true, reg.getHasChanged());

        // Rising edge: reg should latch
        reg.onRisingEdge();

        assertEquals(100, vec.getValue());
        assertEquals(100, reg.getValue());
        assertEquals(true, vec.getHasChanged());
        assertEquals(true, reg.getHasChanged());

        // Sync reset high and update; expect no change without rising edge
        reg.getSclr().setValue(1);
        reg.update();

        assertEquals(100, vec.getValue());
        assertEquals(100, reg.getValue());
        assertEquals(true, vec.getHasChanged());
        assertEquals(false, reg.getHasChanged());

        // Rising edge; reg should clear
        reg.onRisingEdge();

        assertEquals(100, vec.getValue());
        assertEquals(0, reg.getValue());
        assertEquals(true, vec.getHasChanged());
        assertEquals(true, reg.getHasChanged());
    }

    @Test
    public void subVectorTest() {
        Register reg = new Register(7, 0, "reg", null);
        WireVector regSub = reg.getSubVector(3, 0);
        WireVector vec = new WireVector(3, 0, "vec", null);

        vec.drive(regSub);

        // make sure vec's reader is reg, not regSub
        assertEquals(reg, regSub.getSuperVector());
        assertEquals(reg, vec.getReaders()[0]);

        assertEquals(0, vec.getValue());
        assertEquals(0, reg.getValue());
        assertEquals(0, regSub.getValue());

        // Set vec; expect no change in reg until rising edge
        vec.setValue(10);

        assertEquals(10, vec.getValue());
        assertEquals(0, reg.getValue());
        assertEquals(0, regSub.getValue());

        // Enable and rising edge; reg should latch
        reg.getEna().setValue(1);
        reg.onRisingEdge();

        assertEquals(10, vec.getValue());
        assertEquals(10, reg.getValue());
        assertEquals(10, regSub.getValue());
    }
}
