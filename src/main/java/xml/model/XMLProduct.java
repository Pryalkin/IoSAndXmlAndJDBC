package xml.model;

import model.Product;
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

public class XMLProduct implements XMLParse<Product, Long> {

    static String fileName = "product.xml";

    @Override
    public Map<Long, Product> get(){
        Map<Long, Product> products = new HashMap<>();
        Path path = Paths.get(fileName);
        if(Files.exists(path)) {
            XMLStreamReader xmlReader = null;
            Throwable ex = null;
            try {
                xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(fileName, new FileInputStream(fileName));
                long id = 0L;
                String name = null;
                double price = 0.0;
                boolean promotional = false;
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
                    }
                    if (xmlReader.isEndElement()) {
                        if (Objects.equals(xmlReader.getLocalName(), "Product")) {
                            Product product = new Product.Builder().setId(id).setName(name).setPrice(price).setPromotional(promotional).build();
                            products.put(product.getId(), product);
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
        return products;
    }

    @Override
    public void set(Collection<Product> products) {
        try{
            XMLOutputFactory output = XMLOutputFactory.newFactory();

            XMLStreamWriter xmlWrite = output.createXMLStreamWriter(new FileOutputStream(fileName), "UTF-8");

            xmlWrite.writeStartDocument("UTF-8","1.0");
            xmlWrite.writeStartElement("Products");

            products.forEach(product -> {
                try {
                    xmlWrite.writeStartElement("Product");

                    // Id
                    xmlWrite.writeStartElement("Id");
                    xmlWrite.writeCharacters(String.valueOf(product.getId()));
                    xmlWrite.writeEndElement();

                    // Name
                    xmlWrite.writeStartElement("Name");
                    xmlWrite.writeCharacters(product.getName());
                    xmlWrite.writeEndElement();

                    // Price
                    xmlWrite.writeStartElement("Price");
                    xmlWrite.writeCharacters(String.valueOf(product.getPrice()));
                    xmlWrite.writeEndElement();

                    // Promotional
                    xmlWrite.writeStartElement("Promotional");
                    xmlWrite.writeCharacters(String.valueOf(product.getPromotional()));
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
