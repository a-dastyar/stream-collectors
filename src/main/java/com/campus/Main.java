package com.campus;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    private static Random random = new Random();

    public static void main(String[] args) {
        // A compact solution
        var groups = generate(200)
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(Main::firstTowDigit), Main::filterAndCount));
        System.out.println(groups);

        // A more clear solution
        groups = generate(200)
                .collect(Collectors.groupingBy(Main::firstTowDigit, Collectors.counting()))
                .values().stream()
                .filter(e -> e >= 5)
                .count();
        System.out.println(groups);

    }

    private static Stream<Employee> generate(int count) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(Main::makeEmployee);
    }

    private static Employee makeEmployee(int i) {
        return new Employee(String.format("Employee[%d]", i), random.nextInt(10, 1000000));
    }

    private static String firstTowDigit(Employee e) {
        return String.valueOf(e.postalCode()).substring(0, 2);
    }

    private static long filterAndCount(Map<String, List<Employee>> source) {
        return source.values().stream()
                .filter(l -> l.size() >= 5)
                .count();
    }
}