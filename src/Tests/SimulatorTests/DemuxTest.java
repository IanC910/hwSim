package Tests.SimulatorTests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Components.Demultiplexer;
import Components.WireVector;
import Simulation.Simulator;

public class DemuxTest {

    @Test
    public void demuxTest() {
        Simulator sim = new Simulator();

        WireVector in = new WireVector(7, 0, "in", null);
        WireVector sel = new WireVector(0, 0, "sel", null);
        WireVector f0 = new WireVector(7, 0, "f0", null);
        WireVector f1 = new WireVector(7, 0, "f1", null);
        Demultiplexer demux = new Demultiplexer(8, "demux", null);

        demux.getInput().read(in);
        demux.getOutput(0).drive(f0);
        demux.getOutput(1).drive(f1);
        demux.getSel().read(sel);

        in.init();
        sel.init();

        sim.addInputEvent(in, 20, 0);
        assertEquals(0, in.getValue());
        assertEquals(0, sel.getValue());
        assertEquals(0, f0.getValue());
        assertEquals(0, f1.getValue());

        sim.runSimForNs(1);
        assertEquals(20, in.getValue());
        assertEquals(0, sel.getValue());
        assertEquals(20, f0.getValue());
        assertEquals(0, f1.getValue());

        sim.addInputEvent(in, 100, 0);
        sim.runSimForNs(1);
        assertEquals(100, in.getValue());
        assertEquals(0, sel.getValue());
        assertEquals(100, f0.getValue());
        assertEquals(0, f1.getValue());

        sim.addInputEvent(sel, 1, 0);
        sim.runSimForNs(1);
        assertEquals(100, in.getValue());
        assertEquals(1, sel.getValue());
        assertEquals(0, f0.getValue());
        assertEquals(100, f1.getValue());

        sim.addInputEvent(in, 50, 0);
        sim.addInputEvent(sel, 0, 0);
        sim.runSimForNs(1);
        assertEquals(50, in.getValue());
        assertEquals(0, sel.getValue());
        assertEquals(50, f0.getValue());
        assertEquals(0, f1.getValue());
    }
}
