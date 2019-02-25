package com.jiesoul.java8inaction.ch04;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by zhangyunjie on 2017/4/19.
 */
public class Test {

    public static List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH) );

    public static void main(String[] args) {

//        List<String> names = new ArrayList<>();
//        for (Dish d : menu) {
//            names.add(d.getName());
//        }
//        System.out.println(names);
//
//        names = menu.stream()
//                .filter(d -> {
//                    System.out.println("filter " + d.getName());
//                    return d.getCalories() > 300;
//                })
//                .map(dish -> {
//                    System.out.println("map " + dish.getName());
//                    return dish.getName();
//                })
//                .limit(3)
//                .collect(Collectors.toList());
//
//        System.out.println(names);
//
//        menu.stream().forEach(System.out::println);

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //1
        List<Transaction> list = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(list);

        //2
        List<String> cities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(cities);

        //3
        List<Trader> traders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

        System.out.println(traders);

        //4
        String names = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
//                .reduce("", (s, s2) -> s + s2);
                .collect(Collectors.joining());     //效率高 内部使用 StringBuilder
        System.out.println(names);

        //5
        boolean isMilan = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(isMilan);

        //6
        List<Integer> values = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .collect(Collectors.toList());
        System.out.println(values);

        //7
        Optional<Integer> maxValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        System.out.println(maxValue);

        //8
        Optional<Transaction> minValue = transactions.stream()
                .reduce((transaction, transaction2) -> transaction.getValue() < transaction2.getValue() ? transaction : transaction2);

        minValue = transactions.stream().min(Comparator.comparing(Transaction::getValue));
        System.out.println(minValue);
    }
}
