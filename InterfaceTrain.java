interface IA {
    int M = 10;
    double N = 20.5d;

    void m1();
}

interface IB {
    void m2();
}

/*
接口的概念：接口是一种特殊的抽象类，接口遵循类的所有基础语法，接口相对于类来说，有特殊之处
    1.接口中所有的属性都是公开静态常量
    2.接口中所有的方法都是公开抽象方法
    3.接口中没有构造方法
接口的使用：
    1.接口主要是用来被子类继承的，在接口中叫作实现，子类叫作接口的实现类
        interface IA
        class B implements IA
    2.接口中定义的方法使用来被实现类覆盖，在接口中也叫作实现
        interface IA{
        }

        class B implements IA {
            public void m(){
            }
        }
接口的特点：
    1.类对接口的多重实现，一个类可以实现多个接口
        interface IA{
            void m();
        }
        interface IB{
            void m2();
        }
        class MyClass implements IA,IB{
            public void m(){
            }
            public void m2{
            }
        }
        IA ia = new MyClass();
        ia.m();
        IB ib = new MyClass();
        ib.m2();
    2.接口可以继承接口，并且接口之间的继续支持多继承
        interface IA{
            void m1();
        }
        interface IB{
            void m2();
        }
        interface IC{
            void m3();
        }
        interface ID extends IA,IB,IC{
            void m4():
        }
        class MyClass implements ID{
            // 继承ID中所有的常量
            // 也继承ID中所有方法，并且可以对每个方法进行实现
        }
    3.一个类可以继承另一个类，并且同时可以实现多个接口
        interface IA{
            void m1();
        }
        interface IB{
            void m2();
        }
        abstract class C{
            public void m3();
        }
        class MyClass extends C implements IA,IB{
            public void m1(){
                System.out.println("m1()");
            }
            public void m2(){
                System.out.println("m2()");
            }
            public void m3(){
                System.out.println("m3()");
            }
        }
接口的作用：
    1.利用接口可以实现多继承，而且不会破坏类和类之间的树状继承关系的简单性
    2.利用接口定义一种标准，能够实现类和类之前的解耦合
        A:s() --------- C --------> B:m()
                       C:main()
接口回调：在定义接口之后，先有接口的使用者，后有接口的实现者，使用者通过接口调用实现者中的方法
    A --------------> B
    A -----> C -----> B
 */

public class InterfaceTrain {
    public static void main(String[] args) {
        IA ia = new CB();
        ia.m1();
        System.out.println(IA.M);
        System.out.println(IA.N);
        System.out.println(CB.CM);
        System.out.println(CB.CN);

        IB ib = new CB();
        ib.m2();

        CA ca = new CB();
        ca.m3();

        CB cb = new CB();
        cb.m1();
        cb.m2();
        cb.m3();

        int[] arr = {2, 3, 5, 1, 0, 8, 3};
        java.util.Arrays.sort(arr);

        for (int value : arr) {
            System.out.println(value);
        }

        System.out.println("=======================");

        Student[] stuArr = {
                new Student("张三", 24),
                new Student("李四", 55),
                new Student("王五", 63),
                new Student("赵六", 37),
        };
        java.util.Arrays.sort(stuArr);
        for (Student value : stuArr) {
            System.out.printf("name=%s，age=%d\n", value.name, value.age);
        }

    }
}

abstract class CA {
    public static final int CM = 10;
    public static final double CN = 20.5d;

    public abstract void m3();
}

class CB extends CA implements IA, IB {
    public void m1() {
        System.out.println("interface IA m1()");
    }

    public void m2() {
        System.out.println("interface IB m2()");
    }

    public void m3() {
        System.out.println("abstract CA m3()");
    }
}

class Student implements Comparable<Student> {
    String name;
    int age;

    public Student() {

    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int compareTo(Student o) {
        return Integer.compare(this.age, o.age);
    }
}

