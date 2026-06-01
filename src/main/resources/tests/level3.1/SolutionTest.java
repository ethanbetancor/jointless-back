import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void deberiaSumarSoloPares() {
        Solution s = new Solution();

        int[] nums = {1, 2, 3, 4, 5, 6};

        assertEquals(12, s.sumaPares(nums));
    }

    @Test
    void deberiaDevolverCeroSiNoHayPares() {
        Solution s = new Solution();

        int[] nums = {1, 3, 5, 7};

        assertEquals(0, s.sumaPares(nums));
    }

    @Test
    void deberiaFuncionarrConArrayVacio() {
        Solution s = new Solution();

        int[] nums = {};

        assertEquals(0, s.sumaPares(nums));
    }
}