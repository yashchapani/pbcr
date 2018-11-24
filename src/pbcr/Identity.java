package pbcr;
import java.io.Serializable;

public class Identity implements Serializable {
    private String panDetails;
    private String aadharNo;
    private String name;

    public Identity(String panDetails, String aadharNo, String name) {
        this.panDetails = panDetails;
        this.aadharNo = aadharNo;
        this.name = name;
    }
    
     public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPanDetails() {
        return panDetails;
    }

    public void setPanDetails(String panDetails) {
        this.panDetails = panDetails;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

}
