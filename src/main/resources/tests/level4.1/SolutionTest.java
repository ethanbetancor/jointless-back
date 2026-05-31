import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

class SolutionTest {

    @Test
    void deberiaContarFrecuenciasCorrectamente() {
        Solution s = new Solution();

        String[] input = {"a", "b", "a", "c", "b", "a"};

        Map<String, Integer> resultado = s.contarFrecuencias(input);

        assertEquals(3, resultado.get("a"));
        assertEquals(2, resultado.get("b"));
        assertEquals(1, resultado.get("c"));
    }

    @Test
    void deberiaFuncionarrConArrayVacio() {
        Solution s = new Solution();

        String[] input = {};

        Map<String, Integer> resultado = s.contarFrecuencias(input);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deberiaContarUnaSolaPalabra() {
        Solution s = new Solution();

        String[] input = {"java", "java", "java"};

        Map<String, Integer> resultado = s.contarFrecuencias(input);

        assertEquals(3, resultado.get("java"));
    }
}