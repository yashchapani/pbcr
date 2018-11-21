package pbcr;
import java.io.Serializable;

public class Transaction implements Serializable {

    private Identity sellerId;
    private Identity buyerId;
    private LandDescription landDescription;
    private long price;
    private long transactionId;
    private String pdfLink;

    public Transaction(Identity sellerId, Identity buyerId, LandDescription landDescription, long price, long transactionId, String pdfLink) {
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.landDescription = landDescription;
        this.price = price;
        this.transactionId = transactionId;
        this.pdfLink = pdfLink;
    }

    public Identity getSellerId() {
        return sellerId;
    }

    public void setSellerId(Identity sellerId) {
        this.sellerId = sellerId;
    }

    public long getTrasactionId(){
        return transactionId;
    }

    public void setTransactionId(long transactionId){
        this.transactionId = transactionId;
    }

    public String getPdfLink(){
        return pdfLink;
    }

    public void setPdfLink(String pdfLink){
        this.pdfLink = pdfLink;
    }

    public Identity getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Identity buyerId) {
        this.buyerId = buyerId;
    }

    public LandDescription getLandDescription() {
        return landDescription;
    }

    public void setLandDescription(LandDescription landDescription) {
        this.landDescription = landDescription;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

}
