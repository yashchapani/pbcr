package pbcr;
import java.util.ArrayList;

public interface BlockchainQuery {
    public ArrayList<Result> panCount(BlockChainReader blockChainReader, String panDetails);
}
