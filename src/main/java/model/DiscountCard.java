package model;

public class DiscountCard {

    private Long id;
    private String number;
    private Double discount;

    public DiscountCard(String number, Double discount) {
        this.number = number;
        this.discount = discount;
    }

    public DiscountCard() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
