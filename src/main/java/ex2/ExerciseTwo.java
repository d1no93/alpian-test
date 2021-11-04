package ex2;

import com.google.common.collect.Streams;
import javafx.util.Pair;

import java.util.List;

public class ExerciseTwo {

    public static Integer dotProduct (List<Integer> a, List<Integer> b) {

        return Streams.zip(a.stream(), b.stream(), Pair::new)
                .map(p -> p.getKey() * p.getValue())
                .reduce(Integer::sum).get();
    }
}
