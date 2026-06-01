import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void deberiaSerSobresaliente() {
        Notas n = new Notas();

        assertEquals("SOBRESALIENTE", n.clasificar(10));
        assertEquals("SOBRESALIENTE", n.clasificar(9));
    }

    @Test
    void deberiaSerNotable() {
        Notas n = new Notas();

        assertEquals("NOTABLE", n.clasificar(7));
        assertEquals("NOTABLE", n.clasificar(8));
    }

    @Test
    void deberiaSerAprobado() {
        Notas n = new Notas();

        assertEquals("APROBADO", n.clasificar(5));
        assertEquals("APROBADO", n.clasificar(6));
    }

    @Test
    void deberiaSerSuspenso() {
        Notas n = new Notas();

        assertEquals("SUSPENSO", n.clasificar(0));
        assertEquals("SUSPENSO", n.clasificar(4));
    }

    @Test
    void deberiaSerInvalida() {
        Notas n = new Notas();

        assertEquals("INVALIDA", n.clasificar(-1));
        assertEquals("INVALIDA", n.clasificar(11));
    }
}