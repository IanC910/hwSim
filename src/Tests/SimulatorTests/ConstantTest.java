package Tests.SimulatorTests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import Components.Component;
import Components.Constant;
import Components.WireVector;
import Simulation.Simulator;

public class ConstantTest {
    
    @Test
    public void constantTest() {
        Simulator sim = new Simulator();

        Constant constant = new Constant(7, 0, "constant", null, 57);
        WireVector out = new WireVector(7, 0, "out", null);

        constant.drive(out);
        
        LinkedList<Component> netlist = new LinkedList<>();

        netlist.add(constant);
        netlist.add(out);

        assertEquals(57, constant.getValue());
        assertEquals(0, out.getValue());

        sim.initializeNetlist(netlist);
        
        assertEquals(57, constant.getValue());
        assertEquals(57, out.getValue());
    }

}
