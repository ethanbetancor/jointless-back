import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void deberiaAplicar20Porciento() {
    		Solution solution = new Solution();

        double resultado = solution.aplicarDescuento(200.0);

        assertEquals(160.0, resultado, 0.01);
    }

    @Test
    void deberiaAplicar10Porciento() {
    		Solution solution = new Solution();
        
    		double resultado = solution.aplicarDescuento(75.0);

        assertEquals(67.5, resultado, 0.01);
    }

    @Test
    void noDeberiaAplicarDescuento() {
    		Solution solution = new Solution();
        
    		double resultado = solution.aplicarDescuento(30.0);

        assertEquals(30.0, resultado, 0.01);
    }

    @Test
    void casoBordeCien() {
    		Solution solution = new Solution();
        
    		double resultado = solution.aplicarDescuento(100.0);

        assertEquals(80.0, resultado, 0.01);
    }
}