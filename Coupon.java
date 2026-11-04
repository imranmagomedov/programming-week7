    
package week7;

public class Coupon {
    private String code;
    private double discountRate;
    private double minSubtotal;

    public Coupon(String code, double discountRate, double minSubtotal) {
        this.code = code;
        if (discountRate > 0.50) {
            this.discountRate = 0.50;
        } else if (discountRate < 0.0) {
            this.discountRate = 0.0;
        } else {
            this.discountRate = discountRate;
        }
        this.minSubtotal = minSubtotal;
    }

    public String getCode() {
        return code;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public double getMinSubtotal() {
        return minSubtotal;
    }
}