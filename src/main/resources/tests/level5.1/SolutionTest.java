import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class SolutionTest {

    @Test
    void deberiaFiltrarSoloPares() {
        Solution s = new Solution();

        List<Integer> input = List.of(1, 2, 3, 4, 5, 6);

        List<Integer> resultado = s.filtrarPares(input);

        assertEquals(List.of(2, 4, 6), resultado);
    }

    @Test
    void deberiaDevolverListaVaciaSiNoHayPares() {
        Solution s = new Solution();

        List<Integer> input = List.of(1, 3, 5, 7);

        List<Integer> resultado = s.filtrarPares(input);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deberiaFuncionarrConListaVacia() {
        Solution s = new Solution();

        List<Integer> input = List.of();

        List<Integer> resultado = s.filtrarPares(input);

        assertTrue(resultado.isEmpty());
    }
}