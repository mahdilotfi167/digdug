package ir.ac.kntu.util;

import java.util.List;

public class Random {
    private static java.util.Random random = new java.util.Random();

    public static int getInt() {
        return random.nextInt();
    }

    public static int getInt(int bound) {
        return random.nextInt(bound);
    }

    public static int getInt(int start, int bound) {
        return start + random.nextInt(bound - start);
    }

    public static boolean getBoolean() {
        return random.nextBoolean();
    }

    public static <T> T choice(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    public static <T> T choice(T[] array) {
        return array[random.nextInt(array.length)];
    }
}
