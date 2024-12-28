package testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static testing.PrimeInt.isPrime;

public class PrimeIntTest {

    @Test
    public void test_isPrime_Zero() {
        assertFalse(isPrime(0));
    }

    @Test
    public void test_isPrime_One() {
        assertFalse(isPrime(1));
    }

    @Test
    public void test_isPrime_NegativeValue() {
        assertFalse(isPrime(-1));
    }

    @Test
    public void test_isPrime_MaxInteger() {
        assertTrue(isPrime(Integer.MAX_VALUE));
    }

    @Test
    public void test_isPrime_CouldBeDivided_OneAndTestedNumberAndMore() {
        assertFalse(isPrime(4));
    }

    @Test
    public void test_isPrime_CouldBeDivided_OneAndTestedNumberOnly() {
        assertTrue(isPrime(7));
    }
}