package Tests.SimulatorTests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Simulation.*;
import Components.*;
import Components.LogicGates.*;

public class GateTest {
    
    @Test
    public void gateTest() {
        Simulator sim = new Simulator();

        WireVector a = new WireVector(0, 0, "a", null);
        WireVector b = new WireVector(0, 0, "b", null);
        WireVector f = new WireVector(0, 0, "f", null);
        AndGate and = new AndGate("and", null);

        and.read(a);
        and.read(b);
        and.drive(f);

        assertEquals(0, sim.getCurrentTimeNs());
        assertEquals(0, a.getValue());
        assertEquals(0, b.getValue());
        assertEquals(0, and.getValue());
        assertEquals(0, f.getValue());

        sim.addInputEvent(a, 1, 0);
        sim.runSimForNs(1);

        assertEquals(1, sim.getCurrentTimeNs());
        assertEquals(1, a.getValue());
        assertEquals(0, b.getValue());
        assertEquals(0, and.getValue());
        assertEquals(0, f.getValue());

        sim.addInputEvent(b, 1, 0);
        sim.runSimForNs(2);

        assertEquals(3, sim.getCurrentTimeNs());
        assertEquals(1, a.getValue());
        assertEquals(1, b.getValue());
        assertEquals(1, and.getValue());
        assertEquals(1, f.getValue());

        sim.addInputEvent(b, 0, 0);
        sim.addInputEvent(b, 1, 3);
        sim.runSimForNs(2);

        assertEquals(5, sim.getCurrentTimeNs());
        assertEquals(1, a.getValue());
        assertEquals(0, b.getValue());
        assertEquals(0, and.getValue());
        assertEquals(0, f.getValue());

        sim.runSimForNs(1);

        assertEquals(6, sim.getCurrentTimeNs());
        assertEquals(1, a.getValue());
        assertEquals(0, b.getValue());
        assertEquals(0, and.getValue());
        assertEquals(0, f.getValue());

        sim.runSimForNs(1);

        assertEquals(7, sim.getCurrentTimeNs());
        assertEquals(1, a.getValue());
        assertEquals(1, b.getValue());
        assertEquals(1, and.getValue());
        assertEquals(1, f.getValue());
    }
}
