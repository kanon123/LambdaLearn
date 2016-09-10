package kanon.javase;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.nio.channels.Pipe;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by kanon on 2016/9/3.
 */
public class StreamLearn {

    public StreamLearn() {
        init();
    }

    public void init() {
        List<String> wordList = new ArrayList<>();
        wordList.add("AAA");
        wordList.add("B");
        wordList.add("CCC");
        wordList.add("D");
        Stream<String> words = wordList.stream();
        Stream<String> longwords = words.filter((word -> word.length() > 2));
        longwords.forEach((str) -> {
            System.out.println(str);
        });
        Stream<String> strs = Stream.of("A", "B", "C");
        strs.forEach(s -> System.out.println(s));

        Stream<Integer> nums = Stream.of(1);
        nums.forEach(num -> System.out.println(num));

        Stream<Double> dStream = Stream.generate(() -> Math.random() * 100);
        dStream.limit(20).forEach((d) -> System.out.println(d));

        Stream.iterate(1, item -> item + 1).limit(10).forEach((result) -> System.out.println(result));

        System.out.println("----------------------------------------");

        List<String> filterDemo = new ArrayList<>();
        filterDemo.add("ABC");
        filterDemo.add("A");
        filterDemo.add("B");
        filterDemo.add("AB");
        Stream<String> filterStream = filterDemo.stream().filter((str) -> str.length() >= 2);
        filterStream.forEach(System.out::println);

        System.out.println("----------------------------------------");

        List<String> mapDemo = new ArrayList<>();
        mapDemo.add("a");
        mapDemo.add("b");
        mapDemo.add("c");
        mapDemo.add("d");
        Stream<String> mapStream = mapDemo.stream().map((str) -> str.toUpperCase());
        mapStream.forEach((System.out::println));

        System.out.println("----------------------------------------");
        List<String> sortedDemo = new ArrayList<>();
        sortedDemo.add("b");
        sortedDemo.add("a");
        sortedDemo.add("d");
        sortedDemo.add("c");
        Stream<String> sortedStream = sortedDemo.stream().sorted();
        sortedStream.forEach((System.out::println));

        System.out.println("----------------------------------------");

        List<Integer> listA = new ArrayList<>();
        listA.add(1);
        listA.add(2);
        List<Integer> listB = new ArrayList<>();
        listB.add(3);
        listB.add(4);
        //注释的部分输出[1,2] [3,4]并不是将两个List合并为一个Stream
        //Stream<List<Integer>> flatMap = Stream.of(listA, listB);
        //flatMap.forEach(System.out::println);
        Stream<Integer> flatMap = Stream.of(listA, listB).flatMap(num -> num.stream());
        flatMap.forEach((num) -> System.out.print(" " + num));

        System.out.println();
        System.out.println("----------------------------------------");

        Stream<String> limitDemo = Stream.of("A", "B", "C", "D", "E", "F").limit(4);
        limitDemo.forEach(str -> System.out.print("  " + str));

        System.out.println();
        System.out.println("----------------------------------------");


        Stream<Integer> skipDemo = Stream.of(1, 2, 3, 4, 5);
        skipDemo.skip(4).forEach(System.out::println);

        System.out.println("----------------------------------------");

        Stream<Integer> distinctDemo = Stream.of(1, 1, 2, 2, 3, 3, 4);
        distinctDemo.distinct().forEach(n -> System.out.print("  " + n));

        System.out.println();
        System.out.println("----------------------------------------");


        Stream<Integer> peekDemo = Stream.of(1, 2, 3, 4, 5);
        long result = peekDemo.peek(num -> System.out.print(" " + num)).count();
        System.out.println();
        System.out.println(result);

        System.out.println("----------------------------------------");

        Integer[] tmp = {1, 1, null, 2, 3, 4};
        List<Integer> demo = new ArrayList<>();
        demo.addAll(Arrays.asList(tmp));
        System.out.println("sum is:" + demo.stream().filter(num -> num != null).
                distinct().mapToInt(num -> num * 2).
                peek(System.out::println).skip(2).limit(4).sum());

        System.out.println("----------------------------------------");
        Stream<Integer> countDemo = Stream.of(1, 2, 3, 4);
        System.out.println(countDemo.count());

        System.out.println("----------------------------------------");
        Stream<Integer> maxDemo = Stream.of(1, 2, 3, 4, 5);
        Optional<Integer> max = maxDemo.max(Comparator.comparing(n -> n));
        if (max.isPresent()) {
            System.out.println(max.get());
        }

        System.out.println("----------------------------------------");
        Stream<String> firstQ = Stream.of("abc","d","Query","f");
        Optional<String> resultWord = firstQ.filter(str->str.startsWith("Q")).findFirst();
        if(resultWord.isPresent()){
            System.out.println(resultWord.get());
        }

        System.out.println("----------------------------------------");
        Stream<String> firstQFindAny = Stream.of("abc","d","Query","f");
        Optional<String> resultFindAny= firstQFindAny.filter(str->str.startsWith("Q")).findAny();
        if(resultWord.isPresent()){
            System.out.println(resultWord.get());
        }

        System.out.println("----------------------------------------");
        Stream<String> firstQAnyMatch = Stream.of("abc","d","Query","f");
        boolean resultAnyMatch= firstQAnyMatch.parallel().anyMatch(str->str.startsWith("Q"));
        System.out.println(resultAnyMatch);

        System.out.println("----------------------------------------");
        Stream<String> optional = Stream.of("abc","d","Query","f");
        Optional<String> optionalFindFirst= optional.filter(str->str.startsWith("Q")).findFirst();
        optionalFindFirst.ifPresent(n-> System.out.println(n.toLowerCase()));

        System.out.println("----------------------------------------");
        Stream<String> optionalMap = Stream.of("abc","d","Query","f");
        Optional<String> mapFunc= optionalMap.filter(str->str.startsWith("Q")).findFirst();
        Optional<Boolean> mapFuncResult = optionalFindFirst.map(n-> null != n);
        System.out.println(mapFuncResult.get());

        System.out.println("----------------------------------------");
        Stream<String> optionalOrElse = Stream.of("abc","d","Query","f");
        Optional<String> resultOrElse= optionalOrElse.filter(str->str.startsWith("B")).findFirst();
        //没有值时输出默认值
        //System.out.println(resultOrElse.orElseGet(()->"Stream中不存在符合匹配条件的元素"));
        //没有值时抛出异常
        resultOrElse.orElseThrow(()->new NoSuchElementException());





//        List<String> result = longwords.collect(Collectors.toList());
//        System.out.println(result);
    }


    public static void main(String[] args) {
        new StreamLearn();
    }

}
