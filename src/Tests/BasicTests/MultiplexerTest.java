package Tests.BasicTests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Components.Multiplexer;

public class MultiplexerTest {

    @Test
    public void constructorTest() {
        Multiplexer mux = new Multiplexer(8, "mux", null);

        assertEquals(mux, mux.getInput(0).getParent());
        assertEquals("mux.input0", mux.getInput(0).getFullName());

        assertEquals(mux, mux.getInput(0).getReaders()[0]);
        assertEquals(mux, mux.getInput(1).getReaders()[0]);
    }

    @Test
    public void updateTest() {
        Multiplexer mux = new Multiplexer(8, "mux", null);

        mux.getInput(0).setValue(0);
        mux.getInput(1).setValue(0);
        mux.getSel().setValue(0);
        mux.update();
        assertEquals(0, mux.getValue());

        mux.getInput(0).setValue(17);
        mux.getInput(1).setValue(8);
        mux.update();
        assertEquals(17, mux.getValue());
        
        mux.getSel().setValue(1);
        mux.update();
        assertEquals(8, mux.getValue());
        
        mux.getInput(1).setValue(100);
        mux.update();
        assertEquals(100, mux.getValue());
    }  
}
