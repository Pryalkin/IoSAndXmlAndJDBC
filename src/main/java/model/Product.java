package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Product {

    private Integer id;
    private String name;
    private Double price;
    private Boolean promotional;

    public Product(Integer id, String name, Double price, Boolean promotional) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.promotional = promotional;
    }

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getPromotional() {
        return promotional;
    }

    public void setPromotional(Boolean promotional) {
        this.promotional = promotional;
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
}
