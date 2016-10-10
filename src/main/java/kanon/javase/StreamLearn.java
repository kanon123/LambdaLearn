package kanon.javase;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.nio.channels.Pipe;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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


        System.out.println("mapStream1----------------------------------------");

        Stream<Student> mapStream1 = Stream.of(new Student("A", 10), new Student("A", 13), new Student("B", 11), new Student("B", 12));
        Stream<String> map1Result = mapStream1.map((str) -> str.getName());
        map1Result.forEach((System.out::println));

        System.out.println("mapStream2----------------------------------------");

        Stream<Student> mapStream2 = Stream.of(new Student("A", 10), new Student("A", 13), new Student("B", 11), new Student("B", 12));
        IntStream map2Result = mapStream2.mapToInt((str) -> str.getAge());
        map2Result.forEach((System.out::println));


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
        Stream<Stream<Integer>> aaa =Stream.of(listA, listB).map(n->{
            System.out.println("aaa");
            return n.stream();
        });
        aaa.forEach(System.out::println);
        //注释的部分输出[1,2] [3,4]并不是将两个List合并为一个Stream
        //Stream<List<Integer>> flatMap = Stream.of(listA, listB);
        //flatMap.forEach(System.out::println);
        Stream<Integer> flatMap = Stream.of(listA, listB).flatMap(num -> {
            System.out.println();
            return num.stream();
        });
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
        Stream<String> firstQ = Stream.of("abc", "d", "Query", "f");
        Optional<String> resultWord = firstQ.filter(str -> str.startsWith("Q")).findFirst();
        if (resultWord.isPresent()) {
            System.out.println(resultWord.get());
        }

        System.out.println("----------------------------------------");
        Stream<String> firstQFindAny = Stream.of("abc", "d", "Query", "f");
        Optional<String> resultFindAny = firstQFindAny.filter(str -> str.startsWith("Q")).findAny();
        if (resultWord.isPresent()) {
            System.out.println(resultWord.get());
        }

        System.out.println("----------------------------------------");
        Stream<String> firstQAnyMatch = Stream.of("abc", "d", "Query", "f");
        boolean resultAnyMatch = firstQAnyMatch.parallel().anyMatch(str -> str.startsWith("Q"));
        System.out.println(resultAnyMatch);

        System.out.println("----------------------------------------");
        Stream<String> optional = Stream.of("abc", "d", "Query", "f");
        Optional<String> optionalFindFirst = optional.filter(str -> str.startsWith("Q")).findFirst();
        optionalFindFirst.ifPresent(n -> System.out.println(n.toLowerCase()));

        System.out.println("----------------------------------------");
        Stream<String> optionalMap = Stream.of("abc", "d", "Query", "f");
        Optional<String> mapFunc = optionalMap.filter(str -> str.startsWith("Q")).findFirst();
        Optional<Boolean> mapFuncResult = optionalFindFirst.map(n -> null != n);
        System.out.println(mapFuncResult.get());

        System.out.println("----------------------------------------");
        Stream<String> optionalOrElse = Stream.of("abc", "d", "Query", "f");
        Optional<String> resultOrElse = optionalOrElse.filter(str -> str.startsWith("Q")).findFirst();
        //没有值时输出默认值
        //System.out.println(resultOrElse.orElseGet(()->"Stream中不存在符合匹配条件的元素"));
        //没有值时抛出异常
        resultOrElse.orElseThrow(() -> new NoSuchElementException());

        System.out.println("----------------------------------------");
        Stream<Integer> reduceOne = Stream.of(1, 2, 3, 4, 5);
        Optional<Integer> reduceOneResult = reduceOne.reduce((n, m) -> n + m);
        System.out.println(reduceOneResult.orElseGet(() -> -1));

        System.out.println("----------------------------------------");
        Stream<Integer> reduceTwo = Stream.of(1, 2, 3, 4, 5);
        Integer reduceTwoResult = reduceTwo.reduce(0, (n, m) -> n + m);
        System.out.println(reduceTwoResult);

        /*
         *使用reduce()方法实现复杂的操作
         */
        System.out.println("----------------------------------------");
        Stream<Integer> reduceTwo1 = Stream.of(1, 2, 3, 4, 5);
        Integer reduceTwoResult1 = reduceTwo1.reduce(0, (n, m) -> {
            if (m > 2) {
                return n + m;
            } else {
                return n;
            }
        });
        System.out.println(reduceTwoResult1);


         /*
         *收集器的使用
         */
        System.out.println("----------------------------------------");
        Stream<Integer> collect1 = Stream.of(1, 2, 3, 4, 5);
        List<Integer> toList = collect1.collect(Collectors.toList());
        System.out.println(toList);

        System.out.println("----------------------------------------");
        Stream<Integer> set1 = Stream.of(1, 2, 3, 4, 5);
        Set<Integer> toSet = set1.collect(Collectors.toSet());
        System.out.println(toSet);

        System.out.println("toCollection----------------------------------------");
        Stream<Integer> toCollection = Stream.of(1, 2, 3, 4, 5);
        TreeSet<Integer> toTreeSet = toCollection.collect(
                Collectors.toCollection(() -> new TreeSet<Integer>()));
        System.out.println(toTreeSet);

        /*
        *
        * 最基本的方式
        *
        */
//        Stream<Integer> myCollect = Stream.of(1, 2, 3);
//        LinkedList<Integer> linkedList = myCollect.parallel().collect(
//                () -> new LinkedList<>(),
//                LinkedList<Integer>::new,
//                (list, ele) -> list.add(ele),
//                (list1, list2) -> list1.addAll(list2));
//        System.out.println(linkedList);


        /*
         *不使用并行
         */
//        Stream<Integer> myCollect = Stream.of(1, 2, 3);
//        LinkedList<Integer> linkedList = myCollect.collect(() -> new LinkedList<>(),
//                (list, ele) -> {
//                    list.add(ele);
//                    System.out.println("list:" + list);
//                },
//                (list1, list2) -> {
//                    list1.addAll(list2);
//                    System.out.println("list1:" + list1);
//                    System.out.println("list2:" + list2);
//                });
//        System.out.println(linkedList);

        System.out.println("----------------------------------------");
        /*
        *使用并行
        */
        Stream<Integer> myCollect = Stream.of(1, 2, 3);
        LinkedList<Integer> linkedList = myCollect.parallel().collect(() -> new LinkedList<>(),
                (list, ele) -> {
                    list.add(ele);
                    System.out.println("list:" + list);
                },
                (list1, list2) -> {
                    list1.addAll(list2);
                    System.out.println("list1:" + list1);
                    System.out.println("list2:" + list2);
                });
        System.out.println(linkedList);


        System.out.println("----------------------------------------");
        Stream<String> myJoining = Stream.of("1", "2", "3");
        String resultJoining = myJoining.collect(Collectors.joining());
        System.out.println(resultJoining);

        System.out.println("----------------------------------------");
        Stream<Integer> myJoining1 = Stream.of(1, 2, 3);
        String resultJoining1 = myJoining1.map(obj -> obj.toString()).collect(Collectors.joining(","));
        System.out.println(resultJoining1);

        System.out.println("----------------------------------------");
        Stream<Integer> summary = Stream.of(1, 2, 3);
        IntSummaryStatistics summaryStatistics = summary.collect(Collectors.summarizingInt(n -> n));
        System.out.println("平均值：" + summaryStatistics.getAverage());
        System.out.println("数量：" + summaryStatistics.getCount());
        System.out.println("最大值：" + summaryStatistics.getMax());
        System.out.println("最大值：" + summaryStatistics.getMin());
        System.out.println("总和：" + summaryStatistics.getSum());

        System.out.println("toMap----------------------------------------");
        Stream<Student> toMapDemo = Stream.of(new Student("A", 10), new Student("A", 12), new Student("B", 11), new Student("B", 12));
//        Map<String,Student> toMapResult = toMapDemo.collect(Collectors.toMap(Student::getName,stu->stu));
//        Map<String,Student> toMapResult = toMapDemo.collect(Collectors.toMap(Student::getName,Function.identity()));
        Map<String, Set<Student>> toMapResult = toMapDemo.collect(Collectors.toMap(Student::getName,
//                value -> {
//                    Set<Student> temp = new HashSet<Student>();
//                    temp.add(value);
//                    return temp;
//                },
                value -> Collections.singleton(value),
                (oldValue, newValue) -> {
                    Set<Student> studentSet = new HashSet<Student>(oldValue);
                    studentSet.addAll(newValue);
                    return studentSet;
                }, TreeMap::new));
        System.out.println(toMapResult);

        System.out.println("----------------------------------------");
        Stream<Student> groupingByDemo = Stream.of(new Student("A", 10), new Student("A", 13), new Student("B", 11), new Student("B", 12));
        Map<String, List<Student>> groupingByResult = groupingByDemo.collect(Collectors.groupingBy(stu -> stu.getName()));
        System.out.println(groupingByResult);

        System.out.println("----------------------------------------");
        Stream<Student> groupingByDemo2 = Stream.of(new Student("A", 10), new Student("A", 13), new Student("B", 11));
        Map<String, Set<Student>> groupingByResult2 = groupingByDemo2.collect(
                Collectors.groupingBy(stu -> stu.getName(),
                        Collectors.toSet()));
        System.out.println(groupingByResult2);

        System.out.println("----------------------------------------");
        Stream<Student> mappingDemo = Stream.of(new Student("A", 10), new Student("A", 13), new Student("B", 11));
        Map<String, List<Integer>> mappingDemoResult = mappingDemo.collect(
                Collectors.groupingBy(stu -> stu.getName(), Collectors.mapping(Student::getAge, Collectors.toList())));
        System.out.println(mappingDemoResult);

        System.out.println("----------------------------------------");
        Stream<Student> partitioningBy = Stream.of(new Student("A", 10), new Student("A", 13), new Student("B", 11));
        Map<Boolean, List<Student>> partitioningByResult = partitioningBy.collect(
                Collectors.partitioningBy(stu -> stu.getAge() >= 11));
        System.out.println(partitioningByResult);

        System.out.println("----------------------------------------");
        Stream<Student> partitioningBy2 = Stream.of(new Student("A", 10), new Student("A", 13), new Student("B", 11));
        Map<Boolean, Set<Student>> partitioningByResult2 = partitioningBy2.collect(
                Collectors.partitioningBy(stu -> stu.getAge() >= 11, Collectors.toSet()));
        System.out.println(partitioningByResult2);

        System.out.println("----------------------------------------");
        Stream<Student> countingDemo = Stream.of(new Student("A", 10), new Student("A", 13), new Student("B", 11));
        Map<String, Long> countingResult = countingDemo.collect(Collectors.groupingBy(stu -> stu.getName(), Collectors.counting()));
        System.out.println(countingResult);

        System.out.println("----------------------------------------");
        Stream<Student> summingDemo = Stream.of(new Student("A", 10), new Student("A", 13), new Student("B", 11));
        Map<String, Integer> summingResult = summingDemo.collect(Collectors.groupingBy(stu -> stu.getName(),
                Collectors.summingInt(stu -> stu.getAge())));
        System.out.println(summingResult);

        System.out.println("----------------------------------------");
        Stream<Student> maxByDemo = Stream.of(new Student("A", 10), new Student("A", 13), new Student("B", 11), new Student("B", 9));
        Map<String, Optional<Student>> maxByResult = maxByDemo.collect(Collectors.groupingBy(Student::getName,
                Collectors.maxBy(Comparator.comparing(Student::getAge))));
        System.out.println(maxByResult);

        System.out.println("----------------------------------------");
        Stream<Student> summaryDemo = Stream.of(new Student("A", 10), new Student("A", 13), new Student("B", 11), new Student("B", 9));
        Map<String, IntSummaryStatistics> summaryResult = summaryDemo.collect(Collectors.groupingBy(Student::getName,
                Collectors.summarizingInt(Student::getAge)));
        System.out.println(summaryResult);

        System.out.println("----------------------------------------");
        Stream<Student> reducingDemo = Stream.of(new Student("A", 10), new Student("A", 13), new Student("B", 11));
        Map<String, Integer> reducingResult = reducingDemo.collect(Collectors.groupingBy(Student::getName,
                Collectors.reducing(0, Student::getAge, (stuA, stuB) -> stuA + stuB)));
        System.out.println(reducingResult);

        System.out.println("----------------------------------------");
        IntStream intStream = IntStream.of(1, 2, 3, 4);
        intStream.forEach(i -> System.out.println(i));


        System.out.println("----------------------------------------");
        int[] arrayInt = new int[]{1, 34, 5};
        IntStream intStream2 = Arrays.stream(arrayInt, 0, arrayInt.length);
        intStream2.forEach(i -> System.out.println(i));


        System.out.println("----------------------------------------");
        IntStream zeroToNinetyNine = IntStream.range(0, 100);//不包括上限
        zeroToNinetyNine.forEach(i -> System.out.println(i));


        System.out.println("----------------------------------------");
        IntStream zeroToHundred = IntStream.rangeClosed(0, 100);//包括上限
        zeroToHundred.forEach(i -> System.out.println(i));

        System.out.println("----------------------------------------");
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4);
        IntStream mapToIntDemo = integerStream.mapToInt(n -> n.intValue());
        mapToIntDemo.forEach(i -> System.out.println(i));


        System.out.println("----------------------------------------");
        Stream<Integer> integers = IntStream.range(2, 10).boxed();
        integers.forEach(System.out::println);


        System.out.println("strStream----------------------------------------");
        Stream<String> strStream =Stream.of("A","b","cc","v","dd","A","b","cc","v","dd","A","b","cc","v","dd");
        int[] shortWord = new int[3];
        strStream.parallel().forEach(s->{
            if(s.length()<3)
                shortWord[s.length()]++;
        });
        for(int i =0 ;i<shortWord.length;i++){
            System.out.println(shortWord[i]);
        }



//        LinkedList<Integer> linkedList1 = myCollect.collect(
//                LinkedList<Integer>::new,
//                LinkedList::add,
//                LinkedList::addAll);


//        List<String> result = longwords.collect(Collectors.toList());
//        System.out.println(result);
    }


    public static void main(String[] args) {
        new StreamLearn();
    }

}
