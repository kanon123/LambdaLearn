package kanon.javase;

import java.time.LocalDate;

/**
 * Created by kanon on 2016/8/25.
 */
public class Person {
    public enum Sex {
        MALE, FEMALE
    }
    String name;
    LocalDate birthday;
    Sex gender;
    String emailAddress;

    public LocalDate getBirthday() {
        return birthday;
    }
    public static int compareByAge(Person a, Person b) {
        return a.getBirthday().compareTo(b.birthday);
    }
}
