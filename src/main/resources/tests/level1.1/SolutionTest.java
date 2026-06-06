import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void testSum() {
        Solution sol = new Solution();
        assertEquals(5, sol.sum(2, 3));
    }

    @Test
    void testSumNegative() {
        Solution sol = new Solution();
        assertEquals(-1, sol.sum(2, -3));
    }

    @Test
    void testSumZero() {
        Solution sol = new Solution();
        assertEquals(0, sol.sum(0, 0));
    }
}

