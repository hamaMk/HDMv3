package types;

import java.math.BigInteger;

public class Progress {

    private int part;
    private BigInteger startBit;
    private BigInteger size;

    public Progress(int part, BigInteger startBit, BigInteger size) {
        this.part = part;
        this.startBit = startBit;
        this.size = size;
    }

    public BigInteger getStartBit() {
        return startBit;
    }

    public void setStartBit(BigInteger startBit) {
        this.startBit = startBit;
    }

    public BigInteger getSize() {
        return size;
    }

    public void setSize(BigInteger size) {
        this.size = size;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }
}
