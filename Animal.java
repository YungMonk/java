abstract class Animal{
    String name;
    String color;
    int age;

    public abstract void setName(String name);
    public abstract void setColor(String color);
    public abstract void setAge(int age);

    public void print(){
        System.out.printf("姓名：%s，颜色：%s，年龄：%d\n", name,color,age);
    }
}

class Cat extends Animal{
    @Override
    public void setName(String name){
        super.name = name;
    }

    @Override
    public void setColor(String color){
        super.color = color;
    }

    @Override
    public void setAge(int age) {
        super.age = age;
    }
}

class Dog extends Animal{
    @Override
    public void setName(String name){
        super.name = name;
    }

    @Override
    public void setColor(String color){
        super.color = color;
    }

    @Override
    public void setAge(int age) {
        super.age = age;
    }
}

class AnimalTest{
    public static void main(String[] args) {
        Animal c = new Cat();
        c.setName("HelloKitty");
        c.setColor("white");
        c.setAge(1);
        c.print();

        Animal d = new Dog();
        d.setName("TaiDi");
        d.setColor("yellow");
        d.setAge(1);
        d.print();
    }
}

