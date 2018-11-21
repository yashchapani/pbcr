package pbcr;
import com.google.gson.Gson;
import io.ipfs.api.IPFS;

import java.io.*;
import java.util.ArrayList;
import io.ipfs.multihash.Multihash;

public class IPFSBlockChainReader implements BlockChainReader{

    private String prevHexCode;
    private IPFS ipfs;
    private ArrayList<Transaction> transactions;
    private int curTransaction = 0;
    private Gson gson;
    IPFSBlockChainReader(String hexCode, IPFS ipfs){
        gson = new Gson();
        String curHexCode = hexCode;
        this.ipfs = ipfs;
        byte[] arr = new byte[0];
        try {
            arr = ipfs.cat( Multihash.fromHex(curHexCode));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String blockInJson = null;
        try {
            blockInJson = new String(arr,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Block block = gson.fromJson(blockInJson, Block.class);
        transactions = block.getTransactions();
        prevHexCode = block.getPrevHash();

    }
    public Transaction getNextTransaction(){
        if(transactions == null)
            return null;
        if(curTransaction < transactions.size())
            return transactions.get(curTransaction++);
        byte[] arr = new byte[0];
        try {
            arr = ipfs.cat( Multihash.fromHex(prevHexCode));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String blockInJson = null;
        try {
            blockInJson = new String(arr,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Block block = gson.fromJson(blockInJson, Block.class);
        transactions = block.getTransactions();
        if(transactions == null)
            return null;
        curTransaction = 0;
        prevHexCode = block.getPrevHash();
        return transactions.get(curTransaction++);

    }
}
