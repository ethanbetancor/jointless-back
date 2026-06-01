import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void deberiaCalcularArea() {
        Rectangulo rectangulo = new Rectangulo();

        int resultado = rectangulo.calcularArea(5, 4);

        assertEquals(20, resultado);
    }

    @Test
    void deberiaCalcularAreaConBaseCero() {
        Rectangulo rectangulo = new Rectangulo();

        int resultado = rectangulo.calcularArea(0, 10);

        assertEquals(0, resultado);
    }

    @Test
    void deberiaCalcularAreaConAlturaCero() {
        Rectangulo rectangulo = new Rectangulo();

        int resultado = rectangulo.calcularArea(8, 0);

        assertEquals(0, resultado);
    }

    @Test
    void deberiaCalcularAreaConNumerosGrandes() {
        Rectangulo rectangulo = new Rectangulo();

        int resultado = rectangulo.calcularArea(100, 50);

        assertEquals(5000, resultado);
    }
}