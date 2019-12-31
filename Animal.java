abstract class Animal{
    protected String name;
    protected String color;
    protected int age;

    /**
     * @MethodName: setName
     * @Description: TODO
     * @Param: [name]
     * @Return: void
     * @Author: Yung
     * @Date: 2019/12/31
     **/
    public abstract void setName(String name);
    
    /**
     * @MethodName: setColor
     * @Description: TODO
     * @Param: [color]
     * @Return: void
     * @Author: Yung
     * @Date: 2019/12/31
     **/
    public abstract void setColor(String color);
    
    /**
     * @MethodName: setAge
     * @Description: TODO
     * @Param: [age]
     * @Return: void
     * @Author: Yung
     * @Date: 2019/12/31
     **/
    public abstract void setAge(int age);

    /**
     * @MethodName: print
     * @Description: TODO
     * @Param: []
     * @Return: void
     * @Author: Yung
     * @Date: 2019/12/31
     **/
    public void print(){
        System.out.printf("姓名：%s，颜色：%s，年龄：%d\n", name,color,age);
    }
}

class Cat extends Animal{
    /**
     * @MethodName: setName
     * @Description: TODO
     * @Param: [name]
     * @Return: void
     * @Author: Yung
     * @Date: 2019/12/31
     **/
    public void setName(String name){
        super.name = name;
    }

    /***
     * @MethodName: setColor
     * @Description: TODO
     * @Param: [color]
     * @Return: void
     * @Author: Yung
     * @Date: 2019/12/31
     **/
    public void setColor(String color){
        super.color = color;
    }

    /***
     * @MethodName: setAge
     * @Description: TODO
     * @Param: [age]
     * @Return: void
     * @Author: Yung
     * @Date: 2019/12/31
     **/
    public void setAge(int age) {
        super.age = age;
    }
}

class Dog extends Animal{
    /**
     * @MethodName: setName
     * @Description: TODO
     * @Param: [name]
     * @Return: void
     * @Author: Yung
     * @Date: 2019/12/31
     **/
    public void setName(String name){
        super.name = name;
    }

    /**
     * @MethodName: setColor
     * @Description: TODO
     * @Param: [color]
     * @Return: void
     * @Author: Yung
     * @Date: 2019/12/31
     **/
    public void setColor(String color){
        super.color = color;
    }

    /**
     * @MethodName: setAge
     * @Description: TODO
     * @Param: [age]
     * @Return: void
     * @Author: Yung
     * @Date: 2019/12/31
     **/
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

