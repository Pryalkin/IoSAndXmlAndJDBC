package xml;

import xml.model.XMLDiscountCard;
import xml.model.XMLProduct;
import xml.model.XMLProductWarehouse;
import xml.model.XMLReceipt;

public interface XMLFactory {

    XMLProduct getXMLProduct();

    XMLDiscountCard getXMLDiscountCard();

    XMLReceipt getXMLReceipt();

    XMLProductWarehouse getXMLProductWarehouse();

}
