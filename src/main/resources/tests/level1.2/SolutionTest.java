import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
	 
    @Test
    void deberiaCalcularArea() {
        Solution solution = new Solution();
 
        int resultado = solution.calcularArea(5, 4);
 
        assertEquals(20, resultado);
    }
 
    @Test
    void deberiaCalcularAreaConBaseCero() {
    		Solution solution = new Solution();
 
        int resultado = solution.calcularArea(0, 10);
 
        assertEquals(0, resultado);
    }
 
    @Test
    void deberiaCalcularAreaConAlturaCero() {
    		Solution solution = new Solution();
    	
        int resultado = solution.calcularArea(8, 0);
 
        assertEquals(0, resultado);
    }
 
    @Test
    void deberiaCalcularAreaConNumerosGrandes() {
    		Solution solution = new Solution();
 
        int resultado = solution.calcularArea(100, 50);
 
        assertEquals(5000, resultado);
    }
}
 