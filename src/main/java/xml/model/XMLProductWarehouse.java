package xml.model;

import model.Product;
import model.ProductWarehouse;
import xml.XMLParse;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class XMLProductWarehouse implements XMLParse<ProductWarehouse, Long> {

    static String fileName = "product_warehouse.xml";

    @Override
    public Map<Long, ProductWarehouse> get() {
        Map<Long, ProductWarehouse> productWarehouses = new HashMap<>();
        Path path = Paths.get(fileName);
        if(Files.exists(path)) {
            XMLStreamReader xmlReader = null;
            Throwable ex = null;

            try {
                xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(fileName, new FileInputStream(fileName));
                long id = 0L;
                //Product
                Product product = null;
                long idProduct = 0L;
                String name = null;
                double price = 0.0;
                boolean promotional = false;

                int amount = 0;

                while (xmlReader.hasNext()) {
                    xmlReader.next();
                    if (xmlReader.isStartElement()) {
                        switch (xmlReader.getLocalName()) {
                            case "Id" -> {
                                xmlReader.next();
                                if (xmlReader.hasText()) {
                                    id = Long.parseLong(xmlReader.getText());
                                }
                                break;
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
                                                break;
                                            }
                                            case "Name" -> {
                                                xmlReader.next();
                                                if (xmlReader.hasText()) {
                                                    name = xmlReader.getText();
                                                }
                                                break;
                                            }
                                            case "Price" -> {
                                                xmlReader.next();
                                                if (xmlReader.hasText()) {
                                                    price = Double.parseDouble(xmlReader.getText());
                                                }
                                                break;
                                            }
                                            case "Promotional" -> {
                                                xmlReader.next();
                                                if (xmlReader.hasText()) {
                                                    promotional = Boolean.parseBoolean(xmlReader.getText());
                                                }
                                                break;
                                            }
                                        }
                                    } else xmlReader.next();
                                }
                                if (xmlReader.isEndElement()) {
                                    if (Objects.equals(xmlReader.getLocalName(), "Product")) {
                                        product = new Product.Builder().setId(idProduct)
                                                .setName(name).setPrice(price)
                                                .setPromotional(promotional).build();
                                    }
                                }
                                break;
                            }
                            case "Amount" -> {
                                xmlReader.next();
                                if (xmlReader.hasText()) {
                                    amount = Integer.parseInt(xmlReader.getText());
                                }
                                break;
                            }
                        }
                    }
                    if (xmlReader.isEndElement()) {
                        if (Objects.equals(xmlReader.getLocalName(), "ProductWarehouse")) {
                            ProductWarehouse productWarehouse = new ProductWarehouse.Builder().setProduct(product).setId(id).setAmount(amount).build();
                            productWarehouses.put(productWarehouse.getId(), productWarehouse);
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
        return productWarehouses;
    }

    @Override
    public void set(Collection<ProductWarehouse> productWarehouses) {
        try{
            XMLOutputFactory output = XMLOutputFactory.newFactory();

            XMLStreamWriter xmlWrite = output.createXMLStreamWriter(new FileOutputStream(fileName), "UTF-8");

            xmlWrite.writeStartDocument("UTF-8","1.0");
            xmlWrite.writeStartElement("ProductWarehouses");

            productWarehouses.forEach(productWarehouse -> {
                try {
                    xmlWrite.writeStartElement("ProductWarehouse");

                    // Id
                    xmlWrite.writeStartElement("Id");
                    xmlWrite.writeCharacters(String.valueOf(productWarehouse.getId()));
                    xmlWrite.writeEndElement();

                    // Product
                    xmlWrite.writeStartElement("Product");

                    // Product.Id
                    xmlWrite.writeStartElement("Id");
                    xmlWrite.writeCharacters(String.valueOf(productWarehouse.getProduct().getId()));
                    xmlWrite.writeEndElement();

                    // Product.Name
                    xmlWrite.writeStartElement("Name");
                    xmlWrite.writeCharacters(productWarehouse.getProduct().getName());
                    xmlWrite.writeEndElement();

                    // Product.Price
                    xmlWrite.writeStartElement("Price");
                    xmlWrite.writeCharacters(String.valueOf(productWarehouse.getProduct().getPrice()));
                    xmlWrite.writeEndElement();

                    // Product.Promotional
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

            xmlWrite.writeEndDocument();
            xmlWrite.flush();
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
    }
}

