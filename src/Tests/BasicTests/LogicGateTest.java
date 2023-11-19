package Tests.BasicTests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Components.WireVector;
import Components.LogicGates.*;

public class LogicGateTest {

    @Test
    public void notGateTest() {
        WireVector vec = new WireVector(8, 8, "vec", null);
        NotGate notGate = new NotGate("notGate", null);

        vec.drive(notGate);

        assertEquals("notGate", notGate.getName());
        assertEquals(1, notGate.getWidth());
        assertEquals(1, notGate.getValue());

        vec.setValue(1);
        notGate.update();

        assertEquals(0, notGate.getValue());

        vec.setValue(0);
        notGate.update();

        assertEquals(1, notGate.getValue());
    }

    @Test
    public void andGateTest() {
        WireVector vec0 = new WireVector(0, 0, "vec0", null);
        WireVector vec1 = new WireVector(16, 16, "vec1", null);
        AndGate and = new AndGate("and", null);

        vec0.drive(and);
        vec1.drive(and);

        assertEquals(and, vec0.getReaders()[0]);
        assertEquals(and, vec1.getReaders()[0]);

        assertEquals(vec0, and.getDrivers()[0]);
        assertEquals(vec1, and.getDrivers()[1]);

        vec0.setValue(0);
        vec1.setValue(1);
        and.update();

        assertEquals(0, and.getValue());

        WireVector vec2 = new WireVector(27, 27, "vec2", null);
        vec2.drive(and);

        vec2.setValue(1);

        and.update();

        assertEquals(0, and.getValue());

        vec0.setValue(1);

        and.update();

        assertEquals(1, and.getValue());
    }

    @Test
    public void orGateTest() {
        WireVector vec0 = new WireVector(8, 8, "vec0", null);
        WireVector vec1 = new WireVector(9, 9, "vec1", null);
        WireVector vec2 = new WireVector(107, 107, "vec2", null);
        OrGate or = new OrGate("or", null);

        vec0.drive(or);
        vec1.drive(or);
        or.read(vec2);

        assertEquals(or, vec0.getReaders()[0]);
        assertEquals(or, vec1.getReaders()[0]);
        assertEquals(or, vec2.getReaders()[0]);

        vec0.setValue(0);
        vec1.setValue(1);
        vec2.setValue(0);

        or.update();

        assertEquals(1, or.getValue());

        vec1.setValue(0);

        or.update();

        assertEquals(0, or.getValue());

        vec0.setValue(1);

        assertEquals(0, or.getValue());

        or.update();

        assertEquals(1, or.getValue());
    }

    @Test
    public void xorGateTest() {
        WireVector vec0 = new WireVector(0, 0, "vec0", null);
        WireVector vec1 = new WireVector(1, 1, "vec1", null);
        WireVector vec2 = new WireVector(2, 2, "vec2", null);
        XorGate xor = new XorGate("xor", null);

        xor.read(vec0);
        xor.read(vec1);
        xor.read(vec2);

        assertEquals(xor, vec0.getReaders()[0]);
        assertEquals(xor, vec1.getReaders()[0]);
        assertEquals(xor, vec2.getReaders()[0]);

        xor.update();

        assertEquals(0, xor.getValue());

        vec0.setValue(0);
        vec1.setValue(1);
        vec2.setValue(0);

        xor.update();

        assertEquals(1, xor.getValue());

        vec0.setValue(1);
        vec1.setValue(0);
        vec2.setValue(1);

        xor.update();

        assertEquals(0, xor.getValue());

        vec0.setValue(0);
        vec1.setValue(0);
        vec2.setValue(1);

        xor.update();

        assertEquals(1, xor.getValue());
    }

    @Test
    public void nandGateTest() {
        WireVector vec0 = new WireVector(0, 0, "vec0", null);
        WireVector vec1 = new WireVector(1, 1, "vec1", null);
        WireVector vec2 = new WireVector(2, 2, "vec2", null);
        NandGate nand = new NandGate("nand", null);

        nand.read(vec0);
        nand.read(vec1);
        nand.read(vec2);

        assertEquals(nand, vec0.getReaders()[0]);
        assertEquals(nand, vec1.getReaders()[0]);
        assertEquals(nand, vec2.getReaders()[0]);

        nand.update();

        assertEquals(1, nand.getValue());

        vec0.setValue(0);
        vec1.setValue(1);
        vec2.setValue(0);

        nand.update();

        assertEquals(1, nand.getValue());

        vec0.setValue(1);
        vec1.setValue(1);
        vec2.setValue(1);

        nand.update();

        assertEquals(0, nand.getValue());

        vec0.setValue(0);
        vec1.setValue(0);
        vec2.setValue(1);

        nand.update();

        assertEquals(1, nand.getValue());
    }

    @Test
    public void norGateTest() {
        WireVector vec0 = new WireVector(0, 0, "vec0", null);
        WireVector vec1 = new WireVector(1, 1, "vec1", null);
        WireVector vec2 = new WireVector(2, 2, "vec2", null);
        NorGate nor = new NorGate("nor", null);

        nor.read(vec0);
        nor.read(vec1);
        nor.read(vec2);

        assertEquals(nor, vec0.getReaders()[0]);
        assertEquals(nor, vec1.getReaders()[0]);
        assertEquals(nor, vec2.getReaders()[0]);

        nor.update();

        assertEquals(1, nor.getValue());

        vec0.setValue(0);
        vec1.setValue(1);
        vec2.setValue(0);

        nor.update();

        assertEquals(0, nor.getValue());

        vec0.setValue(1);
        vec1.setValue(1);
        vec2.setValue(1);

        nor.update();

        assertEquals(0, nor.getValue());

        vec0.setValue(0);
        vec1.setValue(0);
        vec2.setValue(0);

        nor.update();

        assertEquals(1, nor.getValue());
    }

    @Test
    public void xnorGateTest() {
        WireVector vec0 = new WireVector(0, 0, "vec0", null);
        WireVector vec1 = new WireVector(1, 1, "vec1", null);
        WireVector vec2 = new WireVector(2, 2, "vec2", null);
        XnorGate xnor = new XnorGate("xnor", null);

        xnor.read(vec0);
        xnor.read(vec1);
        xnor.read(vec2);

        assertEquals(xnor, vec0.getReaders()[0]);
        assertEquals(xnor, vec1.getReaders()[0]);
        assertEquals(xnor, vec2.getReaders()[0]);

        xnor.update();

        assertEquals(1, xnor.getValue());

        vec0.setValue(0);
        vec1.setValue(1);
        vec2.setValue(0);

        xnor.update();

        assertEquals(0, xnor.getValue());

        vec0.setValue(1);
        vec1.setValue(1);
        vec2.setValue(1);

        xnor.update();

        assertEquals(0, xnor.getValue());

        vec0.setValue(1);
        vec1.setValue(1);
        vec2.setValue(0);

        xnor.update();

        assertEquals(1, xnor.getValue());
    }
}
