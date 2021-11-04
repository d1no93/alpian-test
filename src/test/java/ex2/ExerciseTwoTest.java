package ex2;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static ex2.ExerciseTwo.dotProduct;

public class ExerciseTwoTest {

    @Test
    public void testBothArraysPositiveIntegers() {

        List<Integer> a = Arrays.asList(1, 2, 3);
        List<Integer> b = Arrays.asList(4, 5, 6);

        Assertions.assertThat(dotProduct(a,b)).isEqualTo(32);
    }

    @Test
    public void testBothArraysNegativeIntegers() {
        List<Integer> a = Arrays.asList(-1, -2, -3);
        List<Integer> b = Arrays.asList(-4, -5, -6);

        Assertions.assertThat(dotProduct(a,b)).isEqualTo(32);
    }

    @Test
    public void testBothOneArraysNegativeIntegers() {
        List<Integer> a = Arrays.asList(1, 2, 3);
        List<Integer> b = Arrays.asList(-4, -5, -6);

        Assertions.assertThat(dotProduct(a,b)).isEqualTo(-32);
    }

    @Test
    public void testMixedSignedIntegers() {
        List<Integer> a = Arrays.asList(-1, 2, -3);
        List<Integer> b = Arrays.asList(4, -5, 6);

        Assertions.assertThat(dotProduct(a,b)).isEqualTo(-32);
    }
}
