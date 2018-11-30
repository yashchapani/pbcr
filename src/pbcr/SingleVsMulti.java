package pbcr;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import io.ipfs.api.IPFS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

public class SingleVsMulti {
    
    
    public static String createBlockchain(){
        IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
        Gson gson = new Gson();
        Block gen = new Block(0,(new Date()).toString(),null,null,null);
        String blockInJson = gson.toJson(gen);
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(blockInJson.getBytes());
        MerkleNode a = null;
        try {
            a = ipfs.add(file).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String prevHash = a.hash.toHex();

        Random objGenerator = new Random();
        String[] name = {"Aashish","Ayushman", "Yash", "Aditya", "Manasi", "Mansi", "Jyot", "Preetpal", "Manmeet", "Deepti"};
        String[] address1 = {"Civil Lines", "Alkapuri", "Marine drive", "Bandra", "Howra", "Lawrence road", "Ellorapark", "Teliargunj", "Malad", "Goregaon"};
        String[] address2 = {"Allahabad", "Amritsar", "Vadodara", "Mumbai", "Kolkata", "Bhopal", "Panji", "Patna", "Gangtok", "Gauwahati"};
        String[] address3 = {"UP", "Punjab", "Gujarat", "Maharastra", "West-Bengal", "MP", "Goa", "Bihar", "Sikkim", "Assam"};

        for(int i = 0; i < 1500; i++) {
            ArrayList<Transaction> transactions = new ArrayList<>();
            for (int j = 0; j < 200; j++) {
                int ran = objGenerator.nextInt(100000000);
//                String pan = "BEKPC5019N";
                if(i % 300 == 0 && j == 127){
                    ran = 342904;
                }
                int ran1 = objGenerator.nextInt(10);
                int ran2 = objGenerator.nextInt(10);
                int ran3 = objGenerator.nextInt(10);
                int ran4 = objGenerator.nextInt(10);

                Identity buyer = new Identity(ran + "", ran + "", name[ran1]);
                Identity seller = new Identity(ran + "", ran + "", name[ran2]);
                LandDescription landDescription = new LandDescription(ran, ran, address1[ran3] + ", " + address2[ran4] +  ", " + address3[ran4]);
                String pdfLink = "tr.pdf";
                if(i % 100 == 0 && j == 0){
                    System.out.println(ran);
                }
                Transaction transaction = new Transaction(seller, buyer, landDescription, ran, i * 200 + j + 1, pdfLink);
                transactions.add(transaction);
            }
            String timestamp = (new Date()).toString();
            Block t = new Block(i, timestamp, transactions, null, prevHash);
            blockInJson = gson.toJson(t);
            file = new NamedStreamable.ByteArrayWrapper(blockInJson.getBytes());
            MerkleNode merkleNode = null;
            try {
                merkleNode = ipfs.add(file).get(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            prevHash = merkleNode.hash.toHex();
        }
        System.out.println("The hash of the leading blockchain is " + prevHash);

        return prevHash;
    }

    public static ArrayList<Result> findTransactionDetails(int numberOfThreads, String search, String detail, String prevHash){
        IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");

        BlockChainReader blockChainReader = new IPFSBlockChainReader(prevHash, ipfs);
        BlockchainQuery blockchainQuery = new MultiThreadedQuery(numberOfThreads, 10000);
        long startTime = System.nanoTime();
        ArrayList<Result> result = blockchainQuery.count(blockChainReader, search, detail);
        long endTime = System.nanoTime();
        System.out.println("" + (endTime - startTime));

        return result;
    }

    public static void main(String args[]) {
        String hash = createBlockchain();
        /*ArrayList<Result> result = findTransactionDetails(1, "92331829", "122054098ef67ce6a4654e7f079810a8da15f3229f50ac0f034cb1f31843d9582282");
        for(int i = 0; i < result.size(); i++){
            System.out.println(result.get(i).getTransactionId() + " : " + result.get(i).getPdfLink());
        }*/
        /*for (int x = 1; x < 9; x++)
            for(int y = 1; y < 26; y++) {
                BlockChainReader blockChainReader = new SimpleBlockChainReader(3000);
                BlockchainQuery blockchainQuery = new MultiThreadedQuery(x, y*1600);
                long startTime = System.nanoTime();
                int count = blockchainQuery.panCount(blockChainReader, "97324985");
                long endTime = System.nanoTime();
                System.out.println(x + " " + y*8 + " " + (endTime - startTime));
            }
*/
        // IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
      /*  for(int x = 1; x <= 8; x++) {
            BlockChainReader blockChainReader = new IPFSBlockChainReader(
                    "1220709C6315EBD94498A296403216223EB6B4DC8B14A2BA998CB5B6906DFA494B9A", ipfs);
            BlockchainQuery blockchainQuery = new MultiThreadedQuery(x, 10000);
            long startTime = System.nanoTime();
            for(int y = 0; y < 50 ; y++) {
                int count = blockchainQuery.panCount(blockChainReader, "97324985");
            }
            long endTime = System.nanoTime();
            System.out.println("" + (endTime - startTime));
        }*/
        // Gson gson = new Gson();
        // Block gen = new Block(0,(new Date()).toString(),null,null,null);
        // String blockInJson = gson.toJson(gen);
        // NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(blockInJson.getBytes());
        // MerkleNode a = null;
        // try {
        //     a = ipfs.add(file).get(0);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        // String prevHash = a.hash.toHex();
        /*System.out.println("previous hash is " + prevHash);

        byte[] arr = new byte[0];
        try {
            arr = ipfs.cat( Multihash.fromHex(prevHash));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String g = null;
        try {
            g = new String(arr,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println(g);
*/
        // Random objGenerator = new Random();
        // for(int i = 0; i < 1500; i++) {
        //     ArrayList<Transaction> transactions = new ArrayList<>();
        //     for (int j = 0; j < 200; j++) {
        //         int ran = objGenerator.nextInt(100000000);
        //         Identity buyer = new Identity(ran + "", ran + "");
        //         Identity seller = new Identity(ran + "", ran + "");
        //         LandDescription landDescription = new LandDescription(ran, ran, ran + "");
        //         String pdfLink = "tr.pdf";
        //         Transaction transaction = new Transaction(seller, buyer, landDescription, ran, i * 200 + j + 1, pdfLink);
        //         transactions.add(transaction);
        //     }
        //     String timestamp = (new Date()).toString();
        //     Block t = new Block(0, timestamp, transactions, null, prevHash);
        //     blockInJson = gson.toJson(t);
        //     file = new NamedStreamable.ByteArrayWrapper(blockInJson.getBytes());
        //     MerkleNode merkleNode = null;
        //     try {
        //         merkleNode = ipfs.add(file).get(0);
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     }
        //     prevHash = merkleNode.hash.toHex();
        // }
        // System.out.println("The hash of the leading blockchain is " + prevHash);
    }
}
//Leading Block Hash = 1220709C6315EBD94498A296403216223EB6B4DC8B14A2BA998CB5B6906DFA494B9A;
/*
 Multithreading 3551309694 4786985739 3528476699 3453930826
 Singlethreading 4961137704 5098365120 5108707928 5057620632
 speed up 1.32
 */