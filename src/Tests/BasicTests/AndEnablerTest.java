package Tests.BasicTests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Components.WireVector;
import Components.AndEnabler;

public class AndEnablerTest {
    
    @Test
    public void constructorTest() {
        AndEnabler andEn = new AndEnabler(8, "andEn", null);

        assertEquals(andEn, andEn.getEna().getParent());
        
        assertEquals("andEn.andEn[0]", andEn.getWires()[0].getFullName());
        assertEquals(8, andEn.getWidth());
        assertEquals(0, andEn.getValue());
    }

    @Test
    public void updateTest() {
        AndEnabler andEn = new AndEnabler(8, "andEn", null);
        WireVector vec0 = new WireVector(7, 0, "vec0", null);

        vec0.drive(andEn);

        andEn.getEna().setValue(1);
        vec0.setValue(100);
        andEn.update();
        assertEquals(100, andEn.getValue());

        vec0.setValue(200);
        andEn.update();
        assertEquals(200, andEn.getValue());

        andEn.getEna().setValue(0);
        andEn.update();
        assertEquals(0, andEn.getValue());

        andEn.getEna().setValue(1);
        andEn.update();
        assertEquals(200, andEn.getValue());
    }
}
