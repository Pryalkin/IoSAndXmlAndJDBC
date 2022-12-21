package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Receipt {

    private Integer id;
    private List<ProductWarehouse> productWarehouses;
    private DiscountCard discountCard;
    private Date createDate;

}
