package model;

public class DiscountCard {

    private Long id;
    private String number;
    private Double discount;

    public DiscountCard(Builder builder) {
        this.id = builder.id;
        this.number = builder.number;
        this.discount = builder.discount;
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

    public Double getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }

    public static class Builder{
        private Long id = 0L;
        private String number;
        private Double discount = 0.0;

        public Builder(DiscountCard discountCard) {
            this.id = discountCard.id;
            this.number = discountCard.number;
            this.discount = discountCard.discount;
        }

        public Builder() {
        }

        public Builder setNumber(String number) {
            this.number = number;
            return  this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return  this;
        }

        public Builder setDiscount(Double discount) {
            this.discount = discount;
            return  this;
        }

        public DiscountCard build(){
            return new DiscountCard(this);
        }

    }
}
