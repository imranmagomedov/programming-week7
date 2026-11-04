
package week7;


public class MenuItem {
    private String name;
    private double price;
    private int quantity;

    public MenuItem(String name, double price, int quantity) throws InvalidQuantityException {
        if (price <= 0) {
            throw new IllegalArgumentException("Fiyat 0'dan buyuk olmalidir.");
        }
        this.name = name;
        this.price = price;
        setQuantity(quantity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Fiyat 0'dan buyuk olmalidir.");
        }
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws InvalidQuantityException {
        if (quantity <= 0) {
            throw new InvalidQuantityException("Miktar 0 veya negatif olamaz! Girilen miktar: " + quantity);
        }
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return name + " x" + quantity + " - Birim Fiyat: " + price + " TL, Toplam: " + getTotalPrice() + " TL";
    }
}