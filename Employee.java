public class Employee {

    int age;
    double salary;
    String name;
    String designation;
    private double empSalary;

    public Employee(final String name) {
        this.name = name;
    }

    public void emAge(final int emAge) {
        age = emAge;
    }

    public void empDesignation(final String empDesig) {
        designation = empDesig;
    }

    public void empSalary(final double empSalary) {
        this.empSalary = empSalary;
    }

    public void printEmployee() {
        System.out.println("名字：" + this.name);
        System.out.println("年龄：" + this.age);
        System.out.println("职位：" + this.designation);
        System.out.println("薪水：" + this.empSalary);
    }
}