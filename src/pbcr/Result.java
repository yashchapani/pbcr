package pbcr;
import java.io.Serializable;

public class Result implements Serializable {
    private long transactionId;
    private String pdfLink;
    
    public Result(long transactionId, String pdfLink){
        this.transactionId = transactionId;
        this.pdfLink = pdfLink;
    }
    
    public long getTransactionId(){
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
    
}