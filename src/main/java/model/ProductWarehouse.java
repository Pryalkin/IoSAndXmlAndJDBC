package model;

public class ProductWarehouse {

    private Integer id;
    private Product product;
    private Integer amount;

    public ProductWarehouse(Integer id, Product product, Integer amount) {
        this.id = id;
        this.product = product;
        this.amount = amount;
    }

    public ProductWarehouse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ProductWarehouse{" +
                "id=" + id +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }
}
