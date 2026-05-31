import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void deberiaAplicar20Porciento() {
        Descuento d = new Descuento();

        double resultado = d.aplicarDescuento(200);

        assertEquals(160, resultado, 0.01);
    }

    @Test
    void deberiaAplicar10Porciento() {
        Descuento d = new Descuento();

        double resultado = d.aplicarDescuento(75);

        assertEquals(67.5, resultado, 0.01);
    }

    @Test
    void noDeberiaAplicarDescuento() {
        Descuento d = new Descuento();

        double resultado = d.aplicarDescuento(30);

        assertEquals(30, resultado, 0.01);
    }

    @Test
    void casoBordeCien() {
        Descuento d = new Descuento();

        double resultado = d.aplicarDescuento(100);

        assertEquals(80, resultado, 0.01);
    }
}