package ex1;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ex1.ExerciseOne.collatz;
import static ex1.ExerciseOne.tailRecursiveCollatz;

public class ExerciseOneTest {

    private ByteArrayOutputStream byteArrayOutputStream;

    private void initialiseByteOutputStream() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    private List<Integer> getCollatzOutput() {

        if (byteArrayOutputStream.toString().equals("")) {
            return Collections.emptyList();
        }

        return Arrays.stream(byteArrayOutputStream.toString()
                .split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());
    }

    @Before
    public void setup() {
        initialiseByteOutputStream();
    }

    private void assertCollatzFunctions(int n, List<Integer> expectedOutput) {

        collatz(n);
        List<Integer> collatzOutput = getCollatzOutput();

        // Clean out the byteStream before running the second method.
        initialiseByteOutputStream();

        tailRecursiveCollatz(n);
        List<Integer> tailRecursiveCollatzOutput = getCollatzOutput();

        Assertions.assertThat(collatzOutput).isEqualTo(tailRecursiveCollatzOutput);
        Assertions.assertThat(collatzOutput).isEqualTo(expectedOutput);
    }

    /*
        Ensures that the function stops at the base case of the Collatz Conjecture
     */
    @Test
    public void testBaseCase() {
        this.assertCollatzFunctions(1, Collections.emptyList());
    }

    @Test
    public void testEvenCase() {
        this.assertCollatzFunctions(4,Arrays.asList(4,2));
    }

    @Test
    public void testOddCase() {
        this.assertCollatzFunctions(5, Arrays.asList(5, 16, 8, 4, 2));
    }
}
