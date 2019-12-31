public class Computer{
    public String brand;
    public double price;
    public String color;
    public double size;
    public String id;

    /**
     * 构造方法
     */
    public Computer(){

    }

    /**
     * 输出方法
     */
    public void show(){
        System.out.println("品牌:"+brand+"\n价格："+price+"\n颜色："+color+"\n尺寸"+size+"\n型号"+id);
    }
}

