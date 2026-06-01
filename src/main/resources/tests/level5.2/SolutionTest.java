import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class SolutionTest {

    @Test
    void deberiaSumarCorrectamente() {
        Solution s = new Solution();

        List<Integer> input = List.of(1, 2, 3, 4);

        assertEquals(10, s.sumar(input));
    }

    @Test
    void deberiaDevolverCeroSiListaVacia() {
        Solution s = new Solution();

        List<Integer> input = List.of();

        assertEquals(0, s.sumar(input));
    }

    @Test
    void deberiaSumarConNumerosNegativos() {
        Solution s = new Solution();

        List<Integer> input = List.of(-1, -2, 3);

        assertEquals(0, s.sumar(input));
    }
}