package xml.factory;

import xml.XMLFactory;
import xml.model.XMLDiscountCard;
import xml.model.XMLProduct;
import xml.model.XMLProductWarehouse;
import xml.model.XMLReceipt;

public class ScoreXMLFactory implements XMLFactory {

    @Override
    public XMLProduct getXMLProduct() {
        return new XMLProduct();
    }

    @Override
    public XMLDiscountCard getXMLDiscountCard() {
        return new XMLDiscountCard();
    }

    @Override
    public XMLReceipt getXMLReceipt() {
        return new XMLReceipt();
    }

    @Override
    public XMLProductWarehouse getXMLProductWarehouse() {
        return new XMLProductWarehouse();
    }


}
