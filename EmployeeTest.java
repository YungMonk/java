import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeTest {
    public static void main(String[] args) throws InterruptedException {
        Employee empOne = new Employee("Runoob1");
        Employee empTwo = new Employee("Runoob2");

        empOne.emAge(26);
        empOne.empDesignation("高级程序员");
        empOne.empSalary(10000);
        empOne.printEmployee();

        empTwo.emAge(21);
        empTwo.empDesignation("菜鸟程序员");
        empTwo.empSalary(15000);
        empTwo.printEmployee();

        // byte
        System.out.println("基本类型：byte 二进制位数：" + Byte.SIZE);
        System.out.println("包装类：java.lang.Byte");
        System.out.println("最小值：Byte.MIN_VALUE=" + Byte.MIN_VALUE);
        System.out.println("最大值：Byte.MAX_VALUE=" + Byte.MAX_VALUE);
        System.out.println();

        // short
        System.out.println("基本类型：short 二进制位数：" + Short.SIZE);
        System.out.println("包装类：java.lang.Short");
        System.out.println("最小值：Short.MIN_VALUE=" + Short.MIN_VALUE);
        System.out.println("最大值：Short.MAX_VALUE=" + Short.MAX_VALUE);
        System.out.println();

        // int
        System.out.println("基本类型：int 二进制位数：" + Integer.SIZE);
        System.out.println("包装类：java.lang.Integer");
        System.out.println("最小值：Integer.MIN_VALUE=" + Integer.MIN_VALUE);
        System.out.println("最大值：Integer.MAX_VALUE=" + Integer.MAX_VALUE);
        System.out.println();

        // long
        System.out.println("基本类型：long 二进制位数：" + Long.SIZE);
        System.out.println("包装类：java.lang.Long");
        System.out.println("最小值：Long.MIN_VALUE=" + Long.MIN_VALUE);
        System.out.println("最大值：Long.MAX_VALUE=" + Long.MAX_VALUE);
        System.out.println();

        // float
        System.out.println("基本类型：float 二进制位数：" + Float.SIZE);
        System.out.println("包装类：java.lang.Float");
        System.out.println("最小值：Float.MIN_VALUE=" + Float.MIN_VALUE);
        System.out.println("最大值：Float.MAX_VALUE=" + Float.MAX_VALUE);
        System.out.println();

        // double
        System.out.println("基本类型：double 二进制位数：" + Double.SIZE);
        System.out.println("包装类：java.lang.Double");
        System.out.println("最小值：Double.MIN_VALUE=" + Double.MIN_VALUE);
        System.out.println("最大值：Double.MAX_VALUE=" + Double.MAX_VALUE);
        System.out.println();

        // char
        System.out.println("基本类型：char 二进制位数：" + Character.SIZE);
        System.out.println("包装类：java.lang.Character");
        // 以数值形式而不是字符形式将Character.MIN_VALUE输出到控制台
        System.out.println("最小值：Character.MIN_VALUE=" + (int) Character.MIN_VALUE);
        // 以数值形式而不是字符形式将Character.MAX_VALUE输出到控制台
        System.out.println("最大值：Character.MAX_VALUE=" + (int) Character.MAX_VALUE);

        String site = "www.runoob.com";
        int len = site.length();
        site.concat("is the site length");
        System.out.printf("the site length is :%d\n", len);

        double myList[] = { 1.9, 2.9, 3.4, 3.5 };
        for (double elem : myList) {
            System.out.println(elem);
        }

        Date date = new Date();
        System.out.println(date);

        System.out.printf("当前时间是：%1$tF %1$tT %n", date);

        String input = args.length == 0 ? "2019-11-16 10:26:00" : args[0];
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            System.out.println(fmt.parse(input));
        } catch (Exception e) {
            System.out.printf("Unparserabe using %s", fmt);
        }

        Thread.sleep(1000 * 3);
        Date dt = new Date();
        System.out.printf("当前时间是：%1$tF %1$tT %n", dt);

    }

    public static int max(int num1, int num2) {
        return num1 > num2 ? num1 : num2;
    }

    public static double max(double num1, double num2) {
        return num1 > num2 ? num1 : num2;
    }
}