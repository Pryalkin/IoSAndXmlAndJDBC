package model;

import java.util.Date;
import java.util.List;

public class Receipt {

    private Long id;
    private List<ProductWarehouse> productWarehouses;
    private DiscountCard discountCard;
    private Date createDate;

    public Receipt(List<ProductWarehouse> productWarehouses, DiscountCard discountCard, Date createDate) {
        this.productWarehouses = productWarehouses;
        this.discountCard = discountCard;
        this.createDate = createDate;
    }

    public Receipt() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductWarehouse> getProductWarehouses() {
        return productWarehouses;
    }

    public void setProductWarehouses(List<ProductWarehouse> productWarehouses) {
        this.productWarehouses = productWarehouses;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public void setDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", productWarehouses=" + productWarehouses +
                ", discountCard=" + discountCard +
                ", createDate=" + createDate +
                '}';
    }
}
