/**
 * @ClassName Persion
 * @Description: 单例练习
 * @Author Yung
 * @Date 2020/1/2
 * @Version V1.0
 **/
public class Person {
    private static Person person = null;

    private Person() {
    }

    public static Person GetPersion() {
        return person == null ? new Person() : person;
    }
}


