import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void deberiaContarMayoresCorrectamente() {
        Solution solution = new Solution();

        int[] nums = {1, 5, 10, 15};

        assertEquals(2, solution.contarMayores(nums, 5));
    }

    @Test
    void deberiaDevolverCeroSiNingunoEsMayor() {
        Solution solution = new Solution();

        int[] nums = {1, 2, 3};

        assertEquals(0, solution.contarMayores(nums, 10));
    }

    @Test
    void deberiaContarTodosSiXEsNegativo() {
        Solution solution = new Solution();

        int[] nums = {1, 2, 3};

        assertEquals(3, solution.contarMayores(nums, -1));
    }
}