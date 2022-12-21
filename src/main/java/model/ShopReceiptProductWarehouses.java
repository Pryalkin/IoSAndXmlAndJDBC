package model;

public class ShopReceiptProductWarehouses {

    private Long id;
    private ShopReceipt check;
    private ProductWarehouse productWarehouse;

    public ShopReceiptProductWarehouses(Long id, ShopReceipt check, ProductWarehouse productWarehouse) {
        this.id = id;
        this.check = check;
        this.productWarehouse = productWarehouse;
    }

    public ShopReceiptProductWarehouses() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShopReceipt getCheck() {
        return check;
    }

    public void setCheck(ShopReceipt check) {
        this.check = check;
    }

    public ProductWarehouse getProductWarehouse() {
        return productWarehouse;
    }

    public void setProductWarehouse(ProductWarehouse productWarehouse) {
        this.productWarehouse = productWarehouse;
    }
}
