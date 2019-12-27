//自定义异常类，继承Exception类
public class InsufficientFundsException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // 此处的amount用来储存当出现异常（取出钱多于余额时）所缺乏的钱
    private double amount;

    public InsufficientFundsException(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}