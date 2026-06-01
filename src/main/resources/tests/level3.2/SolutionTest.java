import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void deberiaContarMayoresCorrectamente() {
        Solution s = new Solution();

        int[] nums = {1, 5, 10, 15};

        assertEquals(2, s.contarMayores(nums, 5));
    }

    @Test
    void deberiaDevolverCeroSiNingunoEsMayor() {
        Solution s = new Solution();

        int[] nums = {1, 2, 3};

        assertEquals(0, s.contarMayores(nums, 10));
    }

    @Test
    void deberiaContarTodosSiXEsNegativo() {
        Solution s = new Solution();

        int[] nums = {1, 2, 3};

        assertEquals(3, s.contarMayores(nums, -1));
    }
}