package repository.impl;

import model.Receipt;
import repository.ReceiptRepository;
import xml.XMLParse;
import xml.factory.ScoreXMLFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class ReceiptRepositoryImpl implements ReceiptRepository {

    private static ReceiptRepository receiptRepository;
    private static XMLParse<Receipt, Long> xmlParse = new ScoreXMLFactory().getXMLReceipt();
    private Long id = 0L;
    private Map<Long, Receipt> receipts = xmlParse.get();

    private ReceiptRepositoryImpl() {
    }

    public static ReceiptRepository getInstance() {
        if (receiptRepository == null) {
            receiptRepository = new ReceiptRepositoryImpl();
            return receiptRepository;
        }
        return receiptRepository;
    }

    @Override
    public Receipt save(Receipt receipt) {
        if (receipt.getId() == 0){
            id++;
            receipt.setId(id);
            receipts.put(id, receipt);
        } else {
            receipts.remove(receipt.getId());
            receipts.put(receipt.getId(), receipt);
        }
        setReceiptsInXML(receipts.values());
        return receipt;
    }

    @Override
    public Map<Long, Receipt> getAll() {
        return receipts;
    }

    @Override
    public Optional<Receipt> getById(Long aLong) {
        if (receipts.containsKey(id)){
            return Optional.of(receipts.get(id));
        }
        return Optional.empty();
    }

    private void setReceiptsInXML(Collection<Receipt> receipts) {
        xmlParse.set(receipts);
    }
}

