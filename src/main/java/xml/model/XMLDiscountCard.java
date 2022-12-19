package xml.model;

import model.DiscountCard;
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

public class XMLDiscountCard implements XMLParse<DiscountCard, Long> {

    static String fileName = "discount_card.xml";

    @Override
    public Map<Long, DiscountCard> get() {
        Map<Long, DiscountCard> discountCards = new HashMap<>();
        Path path = Paths.get(fileName);
        if(Files.exists(path)){
            XMLStreamReader xmlReader = null;
            Throwable ex = null;
            try {
                xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(fileName, new FileInputStream(fileName));
                long id = 0L;
                String number = null;
                double discount = 0.0;
                while (xmlReader.hasNext()){
                    xmlReader.next();
                    if (xmlReader.isStartElement()){
                        switch (xmlReader.getLocalName()){
                            case "Id" -> {
                                xmlReader.next();
                                if (xmlReader.hasText()){
                                    id = Long.parseLong(xmlReader.getText());
                                }
                                break;
                            }
                            case "Number" -> {
                                xmlReader.next();
                                if (xmlReader.hasText()){
                                    number = xmlReader.getText();
                                }
                                break;
                            }
                            case "Discount" -> {
                                xmlReader.next();
                                if (xmlReader.hasText()){
                                    discount = Double.parseDouble(xmlReader.getText());
                                }
                                break;
                            }
                        }
                    }
                    if (xmlReader.isEndElement()){
                        if (Objects.equals(xmlReader.getLocalName(), "DiscountCard")){
                            DiscountCard discountCard = new DiscountCard.Builder().setNumber(number).setId(id).setDiscount(discount).build();
                            discountCards.put(discountCard.getId(), discountCard);
                        }
                    }
                }
            } catch (Throwable e) {
                ex = e;
            } finally {
                if (ex == null){
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
        return discountCards;
    }

    @Override
    public void set(Collection<DiscountCard> discountCards) {
        try{
            XMLOutputFactory output = XMLOutputFactory.newFactory();

            XMLStreamWriter xmlWrite = output.createXMLStreamWriter(new FileOutputStream(fileName), "UTF-8");

            xmlWrite.writeStartDocument("UTF-8","1.0");
            xmlWrite.writeStartElement("DiscountCards");

            discountCards.forEach(discountCard -> {
                try {
                    xmlWrite.writeStartElement("DiscountCard");

                    // Id
                    xmlWrite.writeStartElement("Id");
                    xmlWrite.writeCharacters(String.valueOf(discountCard.getId()));
                    xmlWrite.writeEndElement();

                    // Number
                    xmlWrite.writeStartElement("Number");
                    xmlWrite.writeCharacters(discountCard.getNumber());
                    xmlWrite.writeEndElement();

                    // Discount
                    xmlWrite.writeStartElement("Discount");
                    xmlWrite.writeCharacters(String.valueOf(discountCard.getDiscount()));
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
