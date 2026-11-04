package week7;

public class Week7 {
    public static void main(String[] args) {

        Customer customer1 = new Customer("Ahmet Yilmaz", "05551112233", "Ataturk Cad. No:5", "Melikgazi", "Kayseri");
        Customer customer2 = new Customer("Ayse Kaya", "05449998877", "Sivas Cad. No:12", "Talas", "Kayseri");

        try {
            Order order1 = new Order(customer1);
            Order order2 = new Order(customer2);

            System.out.println("Otomatik Atanan Siparis Numaralari:");
            System.out.println("Siparis 1 No: " + order1.getOrderNumber());
            System.out.println("Siparis 2 No: " + order2.getOrderNumber());
            System.out.println();

            order1.addItem(new MenuItem("Pizza", 150.0, 1));
            order1.addItem(new MenuItem("Burger", 100.0, 1));
            order1.addItem(new MenuItem("Pizza", 150.0, 1));
            order1.addItem(new MenuItem("Kola", 30.0, 1));

            order1.updateQuantity("Burger", 2);
            order1.removeItem("Kola");

            Coupon coupon1 = new Coupon("INDIRIM20", 0.20, 200.0);
            order1.applyCoupon(coupon1);

            System.out.println("--- Siparis 1 Checkout (Kupon Uygulandi) ---");
            order1.checkout();

            order2.addItem(new MenuItem("Pide", 120.0, 1));
            Coupon coupon2 = new Coupon("LUX50", 0.30, 500.0);
            order2.applyCoupon(coupon2);

            System.out.println("--- Siparis 2 Checkout (Kupon Uygulanmadi - Minimum Tutar Yetersiz) ---");
            order2.checkout();

        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
        }

        System.out.println("===== OZEL HATA (EXCEPTION) TESTLERI =====\n");

        try {
            System.out.println("1. InvalidQuantityException Testi:");
            MenuItem invalidItem = new MenuItem("Tatli", 50.0, 0);
        } catch (InvalidQuantityException e) {
            System.out.println("Yakalanan Hata: " + e.getMessage());
        }

        try {
            System.out.println("\n2. ItemNotFoundException Testi:");
            Order testOrder = new Order(customer1);
            testOrder.addItem(new MenuItem("Lahmacun", 60.0, 2));
            testOrder.removeItem("Ayran");
        } catch (ItemNotFoundException e) {
            System.out.println("Yakalanan Hata: " + e.getMessage());
        } catch (InvalidQuantityException e) {
            System.out.println("Yakalanan Hata: " + e.getMessage());
        }

        try {
            System.out.println("\n3. MinimumOrderException Testi:");
            Order lowOrder = new Order(customer2);
            lowOrder.addItem(new MenuItem("Su", 15.0, 2));
            lowOrder.checkout();
        } catch (MinimumOrderException e) {
            System.out.println("Yakalanan Hata: " + e.getMessage());
        } catch (InvalidQuantityException e) {
            System.out.println("Yakalanan Hata: " + e.getMessage());
        }

        System.out.println("\n===== STATIK ISTATISTIKLER =====");
        System.out.println("Tamamlanan Siparis Sayisi : " + Order.getCompletedOrders());
        System.out.println("Toplam Gelir              : " + Order.getTotalRevenue() + " TL");
    }
}