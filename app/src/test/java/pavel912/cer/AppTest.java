package pavel912.cer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    @Test
    void testCorrectApi() {
        App app1 = new App("R01235", "14/03/2001");
        assertEquals(app1.callApi(), "US Dollar: 28,6500");

        App app2 = new App("R01235", "13/03/2001");
        assertEquals(app2.callApi(), "US Dollar: 28,6700");

        App app3 = new App("R01010", "13/03/2001");
        assertEquals(app3.callApi(), "Australian Dollar: 14,5900");
    }

    @Test
    void testIncorrectApi() {
        App app1 = new App("fdsfdsfsdfsdf", "14/03/2001");
        assertEquals(app1.callApi(), "Currency not found");

        App app2 = new App("R01010", "dasdsadas");
        assertEquals(app2.callApi(), "Incorrect date");
    }
}
