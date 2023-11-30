package brickGame;


import java.io.Serializable;

//converts blocks to a byte stream and later reconstructed
//TODO add color
public class BlockSerializable implements Serializable {
    public final int row;
    public final int j;
    public final int type;

    public BlockSerializable(int row , int j , int type) {
        this.row = row;
        this.j = j;
        this.type = type;
    }
}
