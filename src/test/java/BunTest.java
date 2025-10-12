import org.junit.jupiter.api.Test;
import praktikum.Bun;
import static org.junit.jupiter.api.Assertions.*;

public class BunTest {
    Bun bun = new Bun("ye", 10);

    @Test
    public void bunGetNameTest() {

        String actualResult = bun.getName();
        assertEquals("ye", actualResult);
    }

    @Test
    public void bunGetPriceTest() {

        float actualResult = bun.getPrice();
        assertEquals(10, actualResult);
    }


}

