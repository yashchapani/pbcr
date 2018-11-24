package pbcr;
import java.util.ArrayList;

public interface BlockchainQuery {
    public ArrayList<Result> count(BlockChainReader blockChainReader, String Search, String details);
}
