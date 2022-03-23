package be;

public record PriceGroup(String name, int price, String currency) {

    public PriceGroup(String name, int price, String currency) {
        this.name = name;
        this.price = price;
        this.currency = currency;
    }
}
