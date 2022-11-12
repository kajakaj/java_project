package main_classes;

public enum ProductBuy {
    TULIP_SEED(10), 
    ROSE_SEED(20),
    FIELD(200),
    WATER(1),
    SOIL(2);

    private int price;    

    private ProductBuy(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}

