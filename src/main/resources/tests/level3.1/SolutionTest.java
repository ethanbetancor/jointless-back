import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void deberiaSumarSoloPares() {
        Solution solution = new Solution();

        int[] nums = {1, 2, 3, 4, 5, 6};

        assertEquals(12, solution.sumaPares(nums));
    }

    @Test
    void deberiaDevolverCeroSiNoHayPares() {
        Solution solution = new Solution();

        int[] nums = {1, 3, 5, 7};

        assertEquals(0, solution.sumaPares(nums));
    }

    @Test
    void deberiaFuncionarrConArrayVacio() {
        Solution solution = new Solution();

        int[] nums = {};

        assertEquals(0, solution.sumaPares(nums));
    }
}