package pbcr;
import java.io.Serializable;

public class LandDescription implements Serializable {

    private int length;
    private int width;
    private String address;

    public LandDescription(int length, int width, String address) {
        this.length = length;
        this.width = width;
        this.address = address;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

