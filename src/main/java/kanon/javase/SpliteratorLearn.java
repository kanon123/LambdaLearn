package kanon.javase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by kanon on 2016/9/24.
 */
public class SpliteratorLearn {
    public static void main(String[] args) {
        new SpliteratorLearn();
    }

    public SpliteratorLearn() {
        init();
    }

    public void init() {
        List<String> stringList = new ArrayList<>();
        stringList.add("A");
        stringList.add("B");
        stringList.add("C");
        stringList.add("D");
        Spliterator<String> stringSpliterator = stringList.spliterator();
        Consumer<String> consumer = n -> {
            System.out.println(n);
            n = n + "  " + n;
        };
        while (stringSpliterator.tryAdvance(consumer)) ;
        System.out.println(stringSpliterator.characteristics());

        System.out.println("stream-----------------------------------------");
        //使用Iterator
        Stream<Integer> stream = Stream.of(1, 2, 3, 4);
        Iterator<Integer> iterator = stream.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("stream1-----------------------------------------");
        Stream<Integer> stream1 = Stream.of(4, 3, 2, 1);
        stream1.iterator().forEachRemaining(n-> System.out.println(n));

        System.out.println("stream2-----------------------------------------");
        Stream<Integer> stream2 = Stream.of(4, 3, 2, 1);
        Spliterator<Integer> spliterator1 = stream2.spliterator();
        Spliterator<Integer> spliterator2 = spliterator1.trySplit();
        System.out.println("spliterator1:");
        spliterator1.tryAdvance(n-> System.out.print(n+" "));
        System.out.println();
        System.out.println("spliterator2:");
        spliterator2.tryAdvance(n-> System.out.print(n+" "));

    }
}
