package pbcr;
import java.util.ArrayList;

class PanCountTask implements Runnable {

    private Thread t;
    private int localCounter = 0;
    private String panDetails;
    private State state;
    private int transactionsPerThread;
    private ArrayList<Transaction> transactions;
    private ArrayList<Result> resultId = new ArrayList<>();

    public PanCountTask(State state, String panDetails, int transactionsPerThread, ArrayList<Transaction> transactions){
        this.transactions = transactions;
        this.state = state;
        this.panDetails = panDetails;
        this.transactionsPerThread = transactionsPerThread;
        t = new Thread(this);
        t.start();
    }
    

    public Thread getT() {
        return t;
    }

    public void run(){

        while(true) {
            int entry;
            while ((entry = state.getNextBlock()) == -1) ;
            if (entry == -2)
                return;
            for (int i = entry * transactionsPerThread; i < entry * transactionsPerThread+ transactionsPerThread
                    && i < transactions.size(); i++) {
                    if (transactions.get(i).getBuyerId().getPanDetails().equals(panDetails)
                            || transactions.get(i).getSellerId().getPanDetails().equals(panDetails)){
                        localCounter++;
                        Result res = new Result(transactions.get(i).getTrasactionId(), transactions.get(i).getPdfLink());
//                        System.out.println(res.getPdfLink());
                                
                        resultId.add(new Result(transactions.get(i).getTrasactionId(), transactions.get(i).getPdfLink()));
                    }
                }
            }
    }


    public int getCounter() {
        return localCounter;
    }
    
    public ArrayList<Result> getResult(){
        return resultId;
    }
}

class TransactionProducer implements Runnable{

    private Thread t;
    private int transactionsPerThread;
    private ArrayList<Transaction> transactions;
    private BlockChainReader blockChainReader;
    private State state ;

    public TransactionProducer(int transactionsPerThread, ArrayList<Transaction> transactions,
                               BlockChainReader blockChainReader, State state){
        this.transactionsPerThread = transactionsPerThread;
        this.transactions = transactions;
        this.blockChainReader = blockChainReader;
        this.state = state;
        t = new Thread(this);
        t.start();
    }

    public Thread getT() {
        return t;
    }

    public void run() {
        Transaction transaction;
        int counter = 0;
        while((transaction = blockChainReader.getNextTransaction()) != null){
            transactions.add(transaction);
            counter++;
            if(counter == transactionsPerThread){
                state.incrementAvailabeBlocks();
                counter = 0;
            }
        }

        if(counter != 0)
            state.incrementAvailabeBlocks();

        state.setFinished();
    }
}

class State {

    private int availableBlocks = 0;
    private int nextBlock = 0;
    private boolean finished = false;

    public void incrementAvailabeBlocks(){
        availableBlocks ++;
    }

    public void setFinished(){
        finished = true;
    }

    public void resetNextBlock() { nextBlock = 0; };

    synchronized public int getNextBlock(){
        if(finished && nextBlock == availableBlocks)
            return -2;
        if(nextBlock == availableBlocks)
            return -1;
        return nextBlock ++;
    }
}


public class MultiThreadedQuery implements BlockchainQuery {

    private int threadCount;
    private int transactionsPerThread;
    private State state ;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private ArrayList<Result> resultId = new ArrayList<>();;

    public MultiThreadedQuery(int threadCount, int transactionsPerThread) {
        this.transactionsPerThread = transactionsPerThread;
        this.threadCount = threadCount;
        this.state = new State();
    }

    public ArrayList<Result> panCount(BlockChainReader blockChainReader, String panDetails) {

        state.resetNextBlock();

        PanCountTask[] panCountTasks = new PanCountTask[threadCount];

        for(int tid = 0; tid < threadCount; tid++) {
            panCountTasks[tid] = new PanCountTask(state, panDetails, transactionsPerThread, transactions);
        }

        TransactionProducer transactionProducer = new TransactionProducer(transactionsPerThread, transactions,
                blockChainReader, state);

        for (int i = 0; i < threadCount; i++) {
            try {
                panCountTasks[i].getT().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            transactionProducer.getT().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int count = 0;
        for (int i = 0; i < threadCount; i++) {
            count += panCountTasks[i].getCounter();
            resultId.addAll(panCountTasks[i].getResult());
        }
        return resultId;
    }
}
