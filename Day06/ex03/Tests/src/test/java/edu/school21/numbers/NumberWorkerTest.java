package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {
    NumberWorker worker = new NumberWorker();

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 17, 31, 79, 97, 131})
    public void isPrimeForPrimes(int number) {
        assertTrue(worker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 10, 15, 27, 33, 121, 169})
    public void isPrimeForNotPrimes(int number) {
        assertFalse(worker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {-121, 1, 0, -1, -10, -15})
    public void isPrimeForIncorrectNumbers(int number) {
        Assertions.assertThrows(NumberWorker.IllegalNumberException.class, () -> worker.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    public void checkDigitsSum(int input, int expected) {
        assertEquals(expected, worker.digitsSum(input));
    }
}