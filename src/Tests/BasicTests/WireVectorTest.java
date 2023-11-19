package Tests.BasicTests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Components.WireVector;

public class WireVectorTest {
    
    @Test
    public void constructorTest() {
        WireVector v = new WireVector(15, 8, WireVector.VectorType.UNSIGNED, "v", null);

        assertEquals("v", v.getName());
        
        assertEquals("v.v[8]", v.getWires()[0].getFullName());
        assertEquals(8, v.getWidth());
        assertEquals(0, v.getValue());
        
        assertEquals(0, v.getMinValue());
        assertEquals(255, v.getMaxValue());
    }

    @Test
    public void unsignedValueTest() {
        WireVector v = new WireVector(7, 0, WireVector.VectorType.UNSIGNED, "v", null);

        assertEquals(0, v.getMinValue());
        assertEquals(255, v.getMaxValue());

        assertEquals(0, v.getValue());
        
        v.setValue(100);
        
        assertEquals(100, v.getValue());

        assertEquals(0, v.getWire(0).getValue());
        assertEquals(0, v.getWire(1).getValue());
        assertEquals(1, v.getWire(2).getValue());
        assertEquals(0, v.getWire(3).getValue());
        assertEquals(0, v.getWire(4).getValue());
        assertEquals(1, v.getWire(5).getValue());
        assertEquals(1, v.getWire(6).getValue());
        assertEquals(0, v.getWire(7).getValue());
    }
    
    // TODO: signed value test

    @Test
    public void connectTest() {
        WireVector a = new WireVector(7, 0, WireVector.VectorType.UNSIGNED, "a", null);
        WireVector b = new WireVector(7, 0, WireVector.VectorType.UNSIGNED, "b", null);

        a.drive(b);

        assertEquals(a, b.getDrivers()[0]);
        assertEquals(b, a.getReaders()[0]);
    }

    @Test
    public void updateTest() {
        WireVector a = new WireVector(7, 0, WireVector.VectorType.UNSIGNED, "a", null);
        WireVector b = new WireVector(7, 0, WireVector.VectorType.UNSIGNED, "b", null);

        a.drive(b);

        // Begin; values should be 0
        assertEquals(0, a.getValue());
        assertEquals(0, b.getValue());
        assertEquals(true, a.getHasChanged());
        assertEquals(true, b.getHasChanged());

        // Set a; expect no change to b without update
        a.setValue(100);

        assertEquals(100, a.getValue());
        assertEquals(0, b.getValue());
        assertEquals(true, a.getHasChanged());
        assertEquals(true, b.getHasChanged());

        // Update b, value of b should change
        b.update();

        assertEquals(100, a.getValue());
        assertEquals(100, b.getValue());
        assertEquals(true, a.getHasChanged());
        assertEquals(true, b.getHasChanged());

        // Update b, expect no change
        b.update();

        assertEquals(100, a.getValue());
        assertEquals(100, b.getValue());
        assertEquals(true, a.getHasChanged());
        assertEquals(false, b.getHasChanged());

        // Set a, expect no change to b without update
        a.setValue(50);

        assertEquals(50, a.getValue());
        assertEquals(100, b.getValue());
        assertEquals(true, a.getHasChanged());
        assertEquals(false, b.getHasChanged());
    }

    @Test
    public void subVectorTest() {
        WireVector v = new WireVector(7, 0, "v", null);
        WireVector s = v.getSubVector(3, 1);

        assertEquals("v.v[3:1]", s.getFullName());

        assertEquals(8, v.getWidth());
        assertEquals(3, s.getWidth());

        assertEquals(v, s.getParent());
        assertEquals(v, s.getSuperVector());

        v.setValue(7);
        
        assertEquals(7, v.getValue());
        assertEquals(3, s.getValue());

        s.setValue(0);
        
        assertEquals(1, v.getValue());
        assertEquals(0, s.getValue());
    }

    @Test
    public void subVectorUpdateTest() {
        WireVector v1 = new WireVector(3, 0, "v1", null);
        WireVector v2 = new WireVector(7, 0, "v2", null);
        WireVector sub2 = v2.getSubVector(3, 0);
        WireVector v3 = new WireVector(3, 0, "v3", null);

        v1.drive(sub2);
        sub2.drive(v3);

        assertEquals(0, sub2.getDrivers().length);

        assertEquals(1, v2.getDrivers().length);
        assertEquals(v1, v2.getDrivers()[0]);

        assertEquals(1, v1.getReaders().length);
        assertEquals(v2, v1.getReaders()[0]);

        assertEquals(1, v3.getDrivers().length);
        assertEquals(v2, v3.getDrivers()[0]);


        assertEquals(0, v1.getValue());
        assertEquals(0, v2.getValue());
        assertEquals(0, sub2.getValue());
        assertEquals(0, v3.getValue());

        v1.setValue(10);

        assertEquals(10, v1.getValue());
        assertEquals(0, v2.getValue());
        assertEquals(0, sub2.getValue());
        assertEquals(0, v3.getValue());

        v2.update();

        assertEquals(10, v1.getValue());
        assertEquals(10, v2.getValue());
        assertEquals(10, sub2.getValue());
        assertEquals(0, v3.getValue());

        v3.update();

        assertEquals(10, v1.getValue());
        assertEquals(10, v2.getValue());
        assertEquals(10, sub2.getValue());
        assertEquals(10, v3.getValue());
    }
}
