package xml.model;

import model.DiscountCard;
import model.Product;
import model.ProductWarehouse;
import model.Receipt;
import xml.XMLParse;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class XMLReceipt  implements XMLParse<Receipt, Long> {

    static String fileName = "receipt.xml";

    @Override
    public Map<Long, Receipt> get() {
        Map<Long, Receipt> receipts = new HashMap<>();
        Path path = Paths.get(fileName);
        if(Files.exists(path)) {
            XMLStreamReader xmlReader = null;
            Throwable ex = null;

            try {
                xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(fileName, new FileInputStream(fileName));
                long id = 0L;

                // ProductWarehouse
                List<ProductWarehouse> productWarehouses = new ArrayList<>();
                long idProductWarehouse = 0L;
                int amount = 0;

                // Product
                Product product = null;
                long idProduct = 0L;
                String name = null;
                double price = 0.0;
                boolean promotional = false;

                // DiscountCard
                DiscountCard discountCard = null;
                long idDiscountCard = 0L;
                String number = null;
                double discount = 0.0;

                String createDate = null;

                while (xmlReader.hasNext()) {
                    xmlReader.next();
                    if (xmlReader.isStartElement()) {
                        switch (xmlReader.getLocalName()) {
                            case "Id" -> {
                                xmlReader.next();
                                if (xmlReader.hasText()) {
                                    id = Long.parseLong(xmlReader.getText());
                                }
                            }
                            case "ProductWarehouses" -> {
                                xmlReader.next();
                                while (!xmlReader.isEndElement() || !Objects.equals(xmlReader.getLocalName(), "ProductWarehouses")){
                                    if (xmlReader.isStartElement()) {
                                        switch (xmlReader.getLocalName()) {
                                            case "Id" -> {
                                                xmlReader.next();
                                                if (xmlReader.hasText()) {
                                                    idProductWarehouse = Long.parseLong(xmlReader.getText());
                                                }
                                            }
                                            case "Product" -> {
                                                xmlReader.next();
                                                while (!xmlReader.isEndElement() || !Objects.equals(xmlReader.getLocalName(), "Product")) {
                                                    if (xmlReader.isStartElement()) {
                                                        switch (xmlReader.getLocalName()) {
                                                            case "Id" -> {
                                                                xmlReader.next();
                                                                if (xmlReader.hasText()) {
                                                                    idProduct = Long.parseLong(xmlReader.getText());
                                                                }
                                                            }
                                                            case "Name" -> {
                                                                xmlReader.next();
                                                                if (xmlReader.hasText()) {
                                                                    name = xmlReader.getText();
                                                                }
                                                            }
                                                            case "Price" -> {
                                                                xmlReader.next();
                                                                if (xmlReader.hasText()) {
                                                                    price = Double.parseDouble(xmlReader.getText());
                                                                }
                                                            }
                                                            case "Promotional" -> {
                                                                xmlReader.next();
                                                                if (xmlReader.hasText()) {
                                                                    promotional = Boolean.parseBoolean(xmlReader.getText());
                                                                }
                                                            }
                                                        }
                                                    } else xmlReader.next();
                                                }
                                                if (xmlReader.isEndElement()) {
                                                    if (Objects.equals(xmlReader.getLocalName(), "Product")) {
                                                        product = new Product.Builder().setId(idProduct).setName(name).setPrice(price).setPromotional(promotional).build();
                                                    }
                                                }
                                            }
                                            case "Amount" -> {
                                                xmlReader.next();
                                                if (xmlReader.hasText()) {
                                                    amount = Integer.parseInt(xmlReader.getText());
                                                }
                                            } default -> xmlReader.next();
                                        }
                                    } else xmlReader.next();
                                    if (xmlReader.isEndElement()) {
                                        if (Objects.equals(xmlReader.getLocalName(), "ProductWarehouse")) {
                                            ProductWarehouse productWarehouse = new ProductWarehouse.Builder()
                                                    .setId(idProductWarehouse)
                                                    .setProduct(product)
                                                    .setAmount(amount).build();
                                            productWarehouses.add(productWarehouse);
                                        }
                                    }
                                }
                            }
                            case "DiscountCard" -> {
                                xmlReader.next();
                                if (xmlReader.hasText()){
                                    discountCard = null;
                                    break;
                                } else {
                                    while (!xmlReader.isEndElement() || !Objects.equals(xmlReader.getLocalName(), "DiscountCard")) {
                                        if (xmlReader.isStartElement()) {
                                            switch (xmlReader.getLocalName()) {
                                                case "Id" -> {
                                                    xmlReader.next();
                                                    if (xmlReader.hasText()) {
                                                        idDiscountCard = Long.parseLong(xmlReader.getText());
                                                    }
                                                }
                                                case "Number" -> {
                                                    xmlReader.next();
                                                    if (xmlReader.hasText()) {
                                                        number = xmlReader.getText();
                                                    }
                                                }
                                                case "Discount" -> {
                                                    xmlReader.next();
                                                    if (xmlReader.hasText()) {
                                                        discount = Double.parseDouble(xmlReader.getText());
                                                    }
                                                }
                                            }
                                        } else xmlReader.next();
                                    }
                                }
                                if (xmlReader.isEndElement()) {
                                    if (Objects.equals(xmlReader.getLocalName(), "DiscountCard")) {
                                        discountCard = new DiscountCard.Builder().setId(idDiscountCard).setNumber(number).setDiscount(discount).build();
                                    }
                                }
                            }
                            case "Date" -> {
                                xmlReader.next();
                                if (xmlReader.hasText()) {
                                    createDate = xmlReader.getText();
                                }
                            }
                        }
                    }
                    if (xmlReader.isEndElement()) {
                        if (Objects.equals(xmlReader.getLocalName(), "Receipts")) {
                            SimpleDateFormat format = new SimpleDateFormat();
                            format.applyPattern("dd.MM.yyyy");
                            Date date= format.parse(createDate);
                            Receipt receipt = new Receipt.Builder()
                                    .setId(id)
                                    .setProductWarehouses(productWarehouses)
                                    .setDiscountCard(discountCard)
                                    .setCreateDate(date)
                                    .build();
                            receipts.put(receipt.getId(), receipt);
                        }
                    }
                }
            } catch (Throwable e) {
                ex = e;
            } finally {
                if (ex == null) {
                    try {
                        xmlReader.close();
                    } catch (XMLStreamException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        xmlReader.close();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return receipts;
    }

    @Override
    public void set(Collection<Receipt> receipts) {
        try{
            XMLOutputFactory output = XMLOutputFactory.newFactory();

            XMLStreamWriter xmlWrite = output.createXMLStreamWriter(new FileOutputStream(fileName), "UTF-8");

            xmlWrite.writeStartDocument("UTF-8","1.0");
            xmlWrite.writeStartElement("Receipts");

            receipts.forEach(receipt -> {
                try {
                    xmlWrite.writeStartElement("Receipt");

                    // Id
                    xmlWrite.writeStartElement("Id");
                    xmlWrite.writeCharacters(String.valueOf(receipt.getId()));
                    xmlWrite.writeEndElement();

                    // ProductWarehouses
                    xmlWrite.writeStartElement("ProductWarehouses");

                    receipt.getProductWarehouses().forEach(productWarehouse -> {
                        try {
                            xmlWrite.writeStartElement("ProductWarehouse");

                            // Id
                            xmlWrite.writeStartElement("Id");
                            xmlWrite.writeCharacters(String.valueOf(productWarehouse.getId()));
                            xmlWrite.writeEndElement();

                            // Product
                            xmlWrite.writeStartElement("Product");

                            // Id
                            xmlWrite.writeStartElement("Id");
                            xmlWrite.writeCharacters(String.valueOf(productWarehouse.getProduct().getId()));
                            xmlWrite.writeEndElement();

                            // Name
                            xmlWrite.writeStartElement("Name");
                            xmlWrite.writeCharacters(productWarehouse.getProduct().getName());
                            xmlWrite.writeEndElement();

                            // Price
                            xmlWrite.writeStartElement("Price");
                            xmlWrite.writeCharacters(String.valueOf(productWarehouse.getProduct().getPrice()));
                            xmlWrite.writeEndElement();

                            // Promotional
                            xmlWrite.writeStartElement("Promotional");
                            xmlWrite.writeCharacters(String.valueOf(productWarehouse.getProduct().getPromotional()));
                            xmlWrite.writeEndElement();

                            xmlWrite.writeEndElement();

                            // Amount
                            xmlWrite.writeStartElement("Amount");
                            xmlWrite.writeCharacters(String.valueOf(productWarehouse.getAmount()));
                            xmlWrite.writeEndElement();

                            xmlWrite.writeEndElement();
                        } catch (XMLStreamException e) {
                            e.printStackTrace();
                        }
                    });

                    xmlWrite.writeEndElement();

                    // DiscountCard
                    xmlWrite.writeStartElement("DiscountCard");

                    if (receipt.getDiscountCard() == null){
                        xmlWrite.writeCharacters("null");
                        xmlWrite.writeEndElement();
                    } else {
                        // Id
                        xmlWrite.writeStartElement("Id");
                        xmlWrite.writeCharacters(String.valueOf(receipt.getDiscountCard().getId()));
                        xmlWrite.writeEndElement();

                        // Number
                        xmlWrite.writeStartElement("Number");
                        xmlWrite.writeCharacters(receipt.getDiscountCard().getNumber());
                        xmlWrite.writeEndElement();

                        // Discount
                        xmlWrite.writeStartElement("Discount");
                        xmlWrite.writeCharacters(String.valueOf(receipt.getDiscountCard().getDiscount()));
                        xmlWrite.writeEndElement();

                        xmlWrite.writeEndElement();
                    }

                    // Date
                    xmlWrite.writeStartElement("Date");
                    SimpleDateFormat DateFor = new SimpleDateFormat("dd.MM.yyyy");
                    String stringDate= DateFor.format(receipt.getCreateDate());
                    xmlWrite.writeCharacters(stringDate);
                    xmlWrite.writeEndElement();

                    xmlWrite.writeEndElement();
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                }
            });

            xmlWrite.writeEndElement();

            xmlWrite.writeEndDocument();
            xmlWrite.flush();
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
    }
}
