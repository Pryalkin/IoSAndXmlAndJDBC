package model;

import java.util.Objects;

public class ProductWarehouse {

    private Long id;
    private Product product;
    private Integer amount;

    public ProductWarehouse(Builder builder) {
        this.id = builder.id;
        this.product = builder.product;
        this.amount = builder.amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "ProductWarehouse{" +
                "id=" + id +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductWarehouse that = (ProductWarehouse) o;
        return Objects.equals(id, that.id) && Objects.equals(product, that.product) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, amount);
    }

    public static class Builder{
        private Long id = 0L;
        private Product product;
        private Integer amount = 0;

        public Builder(ProductWarehouse productWarehouse) {
            this.id = productWarehouse.id;
            this.product = productWarehouse.product;
            this.amount = productWarehouse.amount;
        }

        public Builder() {
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public ProductWarehouse build(){
            return new ProductWarehouse(this);
        }

    }
}
