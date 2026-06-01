import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class SolutionTest {

    @Test
    void deberiaEliminarDuplicados() {
        Solution s = new Solution();

        List<Integer> input = List.of(1, 2, 2, 3, 3, 3, 4);

        List<Integer> resultado = s.eliminarDuplicados(input);

        assertEquals(List.of(1, 2, 3, 4), resultado);
    }

    @Test
    void deberiaMantenerOrdenOriginal() {
        Solution s = new Solution();

        List<Integer> input = List.of(5, 1, 5, 2, 1);

        List<Integer> resultado = s.eliminarDuplicados(input);

        assertEquals(List.of(5, 1, 2), resultado);
    }

    @Test
    void deberiaFuncionarrConListaVacia() {
        Solution s = new Solution();

        List<Integer> input = List.of();

        List<Integer> resultado = s.eliminarDuplicados(input);

        assertTrue(resultado.isEmpty());
    }
}