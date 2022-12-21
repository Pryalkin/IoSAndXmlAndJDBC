package model;

import java.util.Date;

public class ShopReceipt {

    private Integer id;
    private DiscountCard discountCard;
    private Date createDate;

    public ShopReceipt(Integer id, DiscountCard discountCard, Date createDate) {
        this.id = id;
        this.discountCard = discountCard;
        this.createDate = createDate;
    }

    public ShopReceipt() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
