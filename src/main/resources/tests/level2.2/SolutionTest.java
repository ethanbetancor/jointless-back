import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void deberiaAplicar20Porciento() {
    		Solution solution = new Solution();

        double resultado = solution.aplicarDescuento(200);

        assertEquals(160, resultado, 0.01);
    }

    @Test
    void deberiaAplicar10Porciento() {
    		Solution solution = new Solution();
        
    		double resultado = solution.aplicarDescuento(75);

        assertEquals(67.5, resultado, 0.01);
    }

    @Test
    void noDeberiaAplicarDescuento() {
    		Solution solution = new Solution();
        
    		double resultado = d.aplicarDescuento(30);

        assertEquals(30, resultado, 0.01);
    }

    @Test
    void casoBordeCien() {
    		Solution solution = new Solution();
        
    		double resultado = solution.aplicarDescuento(100);

        assertEquals(80, resultado, 0.01);
    }
}