import org.junit.Test;
import static org.junit.Assert.*;

public class FilkTest {

    @Test
    public void Test1(){
        assertFalse(Flik.isSameNumber(127, 128));
        assertTrue(Flik.isSameNumber(127, 127));
        assertTrue(Flik.isSameNumber(128, 128));
    }
}
