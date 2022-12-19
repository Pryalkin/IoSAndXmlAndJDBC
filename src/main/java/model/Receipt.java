package model;

import java.util.Date;
import java.util.List;

public class Receipt {

    private Long id;
    private List<ProductWarehouse> productWarehouses;
    private DiscountCard discountCard;
    private Date createDate;

    private Receipt(Builder builder) {
        this.id = builder.id;
        this.productWarehouses = builder.productWarehouses;
        this.discountCard = builder.discountCard;
        this.createDate = builder.createDate;
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

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public Date getCreateDate() {
        return createDate;
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

    public static class Builder{

        private Long id = 0L;
        private List<ProductWarehouse> productWarehouses;
        private DiscountCard discountCard;
        private Date createDate;

        public Builder(Receipt receipt) {
            this.id = receipt.id;
            this.productWarehouses = receipt.productWarehouses;
            this.discountCard = receipt.discountCard;
            this.createDate = receipt.createDate;
        }

        public Builder() {
        }

        public Builder setProductWarehouses(List<ProductWarehouse> productWarehouses) {
            this.productWarehouses = productWarehouses;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setDiscountCard(DiscountCard discountCard) {
            this.discountCard = discountCard;
            return this;
        }

        public Builder setCreateDate(Date createDate) {
            this.createDate = createDate;
            return this;
        }

        public Receipt build(){
            return new Receipt(this);
        }
    }

}
