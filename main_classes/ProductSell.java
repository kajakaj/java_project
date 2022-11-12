package main_classes;

public enum ProductSell {
    TULIP_FLOWER(20),
    ROSE_FLOWER(50);

    private int price;    

    private ProductSell(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}

