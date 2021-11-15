package ca.nait.dmit.dmit2504;

public class Product {

    private int productId;
    private String productName;
    private double unitPrice;
    private int categoryId;

    public Product() {
    }

    public Product(int productId, String productName, double unitPrice, int categoryId) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.categoryId = categoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", unitPrice=" + unitPrice +
                ", categoryId=" + categoryId +
                '}';
    }
}
