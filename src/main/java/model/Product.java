package model;

import java.util.Objects;

public class Product {

    private Long id;
    private String name;
    private Double price;
    private Boolean promotional;

    private Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.promotional = builder.promotional;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getPromotional() {
        return promotional;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", promotional=" + promotional +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(promotional, product.promotional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, promotional);
    }

    public static class Builder{
        private Long id = 0L;
        private String name = "Пустота";
        private Double price = 0.0;
        private Boolean promotional = false;

        public Builder(Product product) {
            this.id = product.id;
            this.name = product.name;
            this.price = product.price;
            this.promotional = product.promotional;
        }

        public Builder() {
        }

        public Builder setId(Long id) {
            this.id = id;
            return  this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;

        }

        public Builder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public Builder setPromotional(Boolean promotional) {
            this.promotional = promotional;
            return this;
        }

        public Product build(){
            return new Product(this);
        }

    }

}
