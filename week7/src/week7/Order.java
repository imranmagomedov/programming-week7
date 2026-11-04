
package week7;

import java.util.ArrayList;

public class Order {
    private static int nextOrderNumber = 1001;
    public static final double MIN_ORDER = 100.0;
    public static final double DELIVERY_FEE = 20.0;
    private static int completedOrders = 0;
    private static double totalRevenue = 0.0;

    private int orderNumber;
    private Customer customer;
    private ArrayList<MenuItem> items;
    private Coupon appliedCoupon;
    private boolean isCheckedOut;

    public Order(Customer customer) {
        this.orderNumber = nextOrderNumber++;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.appliedCoupon = null;
        this.isCheckedOut = false;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void addItem(MenuItem item) throws InvalidQuantityException {
        for (MenuItem existing : items) {
            if (existing.getName().equalsIgnoreCase(item.getName())) {
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(new MenuItem(item.getName(), item.getPrice(), item.getQuantity()));
    }

    public void removeItem(String name) throws ItemNotFoundException {
        MenuItem target = null;
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                target = item;
                break;
            }
        }
        if (target == null) {
            throw new ItemNotFoundException("Silinmek istenen urun bulunamadi: " + name);
        }
        items.remove(target);
    }

    public void updateQuantity(String name, int quantity) throws ItemNotFoundException, InvalidQuantityException {
        MenuItem target = null;
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                target = item;
                break;
            }
        }
        if (target == null) {
            throw new ItemNotFoundException("Guncellenmek istenen urun bulunamadi: " + name);
        }
        target.setQuantity(quantity);
    }

    public double getSubtotal() {
        double subtotal = 0.0;
        for (MenuItem item : items) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    public ArrayList<MenuItem> getItems() {
        ArrayList<MenuItem> copy = new ArrayList<>();
        for (MenuItem item : items) {
            try {
                copy.add(new MenuItem(item.getName(), item.getPrice(), item.getQuantity()));
            } catch (InvalidQuantityException e) {
                /* Gecerli urunler kopyalanir */
            }
        }
        return copy;
    }

    public void applyCoupon(Coupon coupon) {
        this.appliedCoupon = coupon;
    }

    public double checkout() throws MinimumOrderException {
        if (isCheckedOut) {
            System.out.println("Siparis #" + orderNumber + " zaten tamamlanmis!");
            return 0.0;
        }

        double subtotal = getSubtotal();

        if (subtotal < MIN_ORDER) {
            throw new MinimumOrderException("Siparis tutari (" + subtotal + " TL), minimum siparis tutarinin (" + MIN_ORDER + " TL) altindadir!");
        }

        double discount = 0.0;
        boolean couponApplied = false;

        if (appliedCoupon != null) {
            if (subtotal >= appliedCoupon.getMinSubtotal()) {
                discount = subtotal * appliedCoupon.getDiscountRate();
                couponApplied = true;
            } else {
                System.out.println("Kupon '" + appliedCoupon.getCode() + "' uygulanamadi! (Minimum ara toplam: " + appliedCoupon.getMinSubtotal() + " TL)");
            }
        }

        double total = subtotal - discount + DELIVERY_FEE;

        isCheckedOut = true;
        completedOrders++;
        totalRevenue += total;

        System.out.println("================ SIPARIS FISI ================");
        System.out.println("Siparis No     : " + orderNumber);
        System.out.println("Musteri Adı    : " + customer.getName());
        System.out.println("Teslimat Adresi: " + customer.getAddress());
        System.out.println("----------------------------------------------");
        System.out.println("Urunler:");
        for (MenuItem item : items) {
            System.out.println("  - " + item);
        }
        System.out.println("----------------------------------------------");
        System.out.println("Ara Toplam     : " + subtotal + " TL");
        if (couponApplied) {
            System.out.println("Kupon Indirimi : -" + discount + " TL (" + appliedCoupon.getCode() + ")");
        } else {
            System.out.println("Kupon Indirimi : 0.0 TL");
        }
        System.out.println("Teslimat Ucreti: " + DELIVERY_FEE + " TL");
        System.out.println("TOPLAM TUTAR   : " + total + " TL");
        System.out.println("==============================================\n");

        return total;
    }

    public static int getCompletedOrders() {
        return completedOrders;
    }

    public static double getTotalRevenue() {
        return totalRevenue;
    }
}