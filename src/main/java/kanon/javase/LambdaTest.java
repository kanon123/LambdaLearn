package kanon.javase;


import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by kanon on 2016/8/22.
 */
public class LambdaTest {
    public static void main(String[] args) {
        new LambdaTest().test9();

        /*测试factory方法
        StudentTest studentTest = Student::new;
        Student s = factory(studentTest,"aaa");
        System.out.println(s.getName());
        Student b = studentTest.create("bbb");
        System.out.println(b.getName());
        */
    }

    public void test() {
        Button button = new Button();
        final String name = "Username";
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hi " + name);
            }
        });
    }

    public void test1() {
        Button button = new Button();
        String name = "Username";
        // name = "change";
        button.addActionListener(event -> System.out.println("hi" + name));
        button.addActionListener(System.out::println);
        ActionListener l = event -> System.out.println("Click");
        Map<String, Integer> oldWordCounts = new HashMap<String, Integer>();
        Map<String, Integer> diamondWordCounts = new HashMap<>();
    }

    public void test3() {
        MyNumber myNum;
        myNum = () -> 123.45;
        System.out.println(myNum.getValue());
        myNum = () -> Math.random() * 100;
        System.out.println("A random value:" + myNum.getValue());
        System.out.println("Another random value:" + myNum.getValue());
//        myNum = () -> "123.03";
    }

    /**
     * 使用自定义的MyNumber函数式接口
     */
    public void test2() {
        MyNumber myNumber;
        myNumber = () -> 123.45;
        System.out.println(myNumber.getValue());
    }

    /**
     * 使用自定义接口NumbericTest
     */
    public void test4() {
        NumbericTest2 isFactor = (n, d) -> (n % d) == 0;
        if (isFactor.test(10, 2) == true) {
            System.out.println();
        }
    }

    /**
     * 使用自定义的MyLambda表达式
     */
    public void test5() {
        MyLambda myLambda = (s) -> {
            System.out.println(s);
        };
        myLambda.sayString("MyLambda");
    }


    /**
     *
     */
    public void test6() {
        final Function<Student, Integer> byAgeFuc = (stu) -> stu.getAge();
        Comparator<Student> byAge = Comparator.comparing(byAgeFuc);
    }

    public void test7() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student());
        Consumer<Student> consumer = student -> {
            student.setName(String.valueOf(Math.round(Math.random() * 2000)));
            student.setAge((int) Math.round(Math.random() * 30));
        };
        students.forEach(consumer);
        System.out.println(students);
    }

    /**
     * @param s
     * @param name
     * @return
     */
    public static Student factory(StudentTest s, String name) {
        return s.create(name);
    }

    /**
     * 为了验证代码是否有错误
     */
    public void test8() {
        //普通写法：
        List<Student> studentList = new ArrayList<>();
        Collections.sort(studentList, new Comparator<Student>() {
            public int compare(Student x, Student y) {
                return x.getName().compareTo(y.getName());
            }
        });
        //使用lambda表达式写法：
        studentList.sort(Comparator.comparing(Student::getName));

        //第一步：去掉冗余的匿名类
        Collections.sort(studentList, (Student x, Student y) -> x.getName().compareTo(y.getName()));

        // 第二步：使用Comparator里的comparing方法
        Collections.sort(studentList, Comparator.comparing((Student p) -> p.getName()));

        //第三步：类型推导和静态导入
        Collections.sort(studentList, Comparator.comparing(p -> p.getName()));

        //第四步：方法引用
        Collections.sort(studentList, Comparator.comparing(Student::getName));

        //第五步：使用List本身的sort更优
        studentList.sort(Comparator.comparing(Student::getName));
    }

    public void test9() {
        long t0 = System.nanoTime();
        //初始化一个范围10万整数流,求能被2整除的数字，toArray（）是终点方法
        int a[] = IntStream.range(0, 100_000).filter(p -> p % 2 == 0).toArray();
        long t1 = System.nanoTime();
        //和上面功能一样，这里是用并行流来计算
        int b[] = IntStream.range(0, 100_000).parallel().filter(p -> p % 2 == 0).toArray();
        long t2 = System.nanoTime();
        //我本机的结果是serial: 0.06s, parallel 0.02s，证明并行流确实比顺序流快
        System.out.printf("serial: %.2fs, parallel %.2fs%n", (t1 - t0) * 1e-9, (t2 - t1) * 1e-9);
        BinaryOperator<String> binaryOperator;
    }
}

@FunctionalInterface
interface NumbericTest2 {
    boolean test(int n, int d);
}

@FunctionalInterface
interface MyNumber {
    double getValue();
}

@FunctionalInterface
interface MyLambda {
    void sayString(String str);
}

@FunctionalInterface
interface StudentTest {
    Student create(String name);
}

