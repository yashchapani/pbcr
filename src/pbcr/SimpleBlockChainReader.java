package pbcr;
import java.util.ArrayList;
import java.io.*;

public class SimpleBlockChainReader implements BlockChainReader{
    private int curBlock;
    private int totalBlocks;
    private ArrayList<Transaction> transactions;
    private int curTransaction;
    SimpleBlockChainReader(int totalBlocks){
        this.totalBlocks = totalBlocks;
        try {
            FileInputStream fi = new FileInputStream(new File("blockChain/block0"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            Block block = (Block) oi.readObject();
            transactions = block.getTransactions();
            curTransaction = 0;
            curBlock = 0;
            oi.close();
            fi.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        curBlock = 0;
    }
    public Transaction getNextTransaction(){
        if(curTransaction < transactions.size())
            return transactions.get(curTransaction++);
        curBlock++;
        if(curBlock < totalBlocks){
            try {
                FileInputStream fi = new FileInputStream(new File("blockChain/block" + curBlock));
                ObjectInputStream oi = new ObjectInputStream(fi);
                Block block = (Block) oi.readObject();
                transactions = block.getTransactions();
                curTransaction = 0;
                oi.close();
                fi.close();
                return transactions.get(curTransaction++);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
