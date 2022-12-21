package model;

public class DiscountCard {

    private Integer id;
    private String number;
    private Double discount;

    public DiscountCard(Integer id, String number, Double discount) {
        this.id = id;
        this.number = number;
        this.discount = discount;
    }

    public DiscountCard() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", discount=" + discount +
                '}';
    }
}
