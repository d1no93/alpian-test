package ex1;

public class ExerciseOne {

    public static int collatz(int n) {
        if (n > 1) {
            System.out.println(n);
            if (n % 2 != 0) {
                return collatz((3*n) + 1);
            } else {
                return collatz(n / 2);
            }
        }
        return 1;
    }

    public static int tailRecursiveCollatz(int n) {

        if (n == 1) {
            return n;
        }

        System.out.println(n);

        int nPrime;
        if (n % 2 != 0) {
            nPrime = (3*n) + 1;
        } else {
            nPrime = n / 2;
        }

        return collatz(nPrime);
    }
}
