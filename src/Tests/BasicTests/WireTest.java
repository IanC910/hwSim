package Tests.BasicTests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Components.Wire;

public class WireTest {

    @Test
    public void setAndGetValueTest() {
        Wire wire = new Wire("w", null);

        assertEquals(0, wire.getValue());

        wire.setValue((byte)1);

        assertEquals(1, wire.getValue());

        wire.setValue((byte)4);
        
        assertEquals(1, wire.getValue());
    }
    @Test
    public void connectTest() {
        Wire a = new Wire("a", null);
        Wire b = new Wire("b", null);
        Wire c = new Wire("c", null);

        c.read(b);

        assertEquals(b, c.getDrivers()[0]);
        assertEquals(c, b.getReaders()[0]);

        a.drive(b);

        assertEquals(a, b.getDrivers()[0]);
        assertEquals(b, a.getReaders()[0]);
    }

    @Test
    public void updateTest() {
        Wire a = new Wire("a", null);
        Wire b = new Wire("b", null);

        a.drive(b);

        // Begin; values should be 0
        assertEquals(0, a.getValue());
        assertEquals(0, b.getValue());

        // Set 1, expect no change to b without update
        a.setValue((byte)1);

        assertEquals(1, a.getValue());
        assertEquals(0, b.getValue());
        assertEquals(true, a.getHasChanged());
        assertEquals(true, b.getHasChanged());

        // update b; value of b should change
        b.update();

        assertEquals(1, a.getValue());
        assertEquals(1, b.getValue());
        assertEquals(true, a.getHasChanged());
        assertEquals(true, b.getHasChanged());

        // update b; expect no change to b
        b.update();

        assertEquals(1, a.getValue());
        assertEquals(1, b.getValue());
        assertEquals(true, a.getHasChanged());
        assertEquals(false, b.getHasChanged());

        // Set a 0; expect no change to b without update
        a.setValue((byte)0);

        assertEquals(0, a.getValue());
        assertEquals(1, b.getValue());
        assertEquals(true, a.getHasChanged());
        assertEquals(false, b.getHasChanged());

        // update b; value of b should change
        b.update();

        assertEquals(0, a.getValue());
        assertEquals(0, b.getValue());
        assertEquals(true, a.getHasChanged());
        assertEquals(true, b.getHasChanged());
    }

    @Test
    public void getFullNameTest() {
        Wire a = new Wire("a", null);
        Wire b = new Wire("b", a);
        Wire c = new Wire("c", b);

        assertEquals("a.b.c", c.getFullName());
    }
}
