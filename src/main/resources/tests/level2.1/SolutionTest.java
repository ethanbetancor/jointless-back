import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void deberiaSerSobresaliente() {
    		Solution solution = new Solution();

        assertEquals("SOBRESALIENTE", solution.clasificar(10));
        assertEquals("SOBRESALIENTE", solution.clasificar(9));
    }

    @Test
    void deberiaSerNotable() {
    		Solution solution = new Solution();

        assertEquals("NOTABLE", solution.clasificar(7));
        assertEquals("NOTABLE", solution.clasificar(8));
    }

    @Test
    void deberiaSerAprobado() {
    		Solution solution = new Solution();

        assertEquals("APROBADO", solution.clasificar(5));
        assertEquals("APROBADO", solution.clasificar(6));
    }

    @Test
    void deberiaSerSuspenso() {
    		Solution solution = new Solution();

        assertEquals("SUSPENSO", solution.clasificar(0));
        assertEquals("SUSPENSO", solution.clasificar(4));
    }

    @Test
    void deberiaSerInvalida() {
    		Solution solution = new Solution();

        assertEquals("INVALIDA", solution.clasificar(-1));
        assertEquals("INVALIDA", solution.clasificar(11));
    }
}