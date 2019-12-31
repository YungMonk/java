public class Hello {
    private int puppyAge;

    public static void main(String[] args) {
        System.out.println("Hello World");

        System.out.println(FreshJuiceSize.MEDIUM);

        Hello puppy = new Hello();
        puppy.setAge(2);
        puppy.getAge();
        System.out.println("变量值：" + puppy.puppyAge);

        System.exit(0);
    }

    public int getAge() {
        System.out.println("this dog's age is :" + puppyAge);
        return puppyAge;
    }

    public void setAge(int age) {
        puppyAge = age;
    }

    private enum FreshJuiceSize {
        SMALL, MEDIUM, LARGE
    }
}