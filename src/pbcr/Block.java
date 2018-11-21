package pbcr;

import java.io.Serializable;
import java.util.ArrayList;

public class Block implements Serializable {

    private int index;
    private String timestamp;
    private ArrayList<Transaction> transactions;
    private String hash;
    private String prevHash;

    public Block(int index, String timestamp, ArrayList<Transaction> transactions, String hash, String prevHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.hash = hash;
        this.prevHash = prevHash;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }
}
