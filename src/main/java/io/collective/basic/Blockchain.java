package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private final List<Block> chain;

    public Blockchain(){
        this.chain = new ArrayList<>();
    }

    public boolean isEmpty() {
        return this.chain.isEmpty();
    }

    public void add(Block block) {
        this.chain.add(block);
    }

    public int size() {
        return this.chain.size();
    }

    public boolean isValid() throws NoSuchAlgorithmException {
        for(int i = 0; i < chain.size(); i++) {

            Block currentBlock = chain.get(i);

            if (!isMined(currentBlock)) {
                return false;
            }

            if (!currentBlock.getHash().equals(currentBlock.calculatedHash())) {
                return false;
            }

            if (i == 0) {
                continue;
            }

            Block previousBlock = chain.get(i - 1);
            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                return false;
            }
        }
        return true;
    }

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }
        return mined;
    }

    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }
}
