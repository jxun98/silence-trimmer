import java.util.ArrayList;
public class Utility {

    public static byte[] removeSilence(byte[] data, boolean isBigEndian) {
        if (isBigEndian) {
            return removeSilenceBigEndian(data);
        } else {
            return removeSilenceLittleEndian(data);
        }
    }

    private static byte[] removeSilenceBigEndian(byte[] rawData){
        ArrayList<Byte> newData = new ArrayList<>();
        return new byte[12];
    }

    private static byte[] removeSilenceLittleEndian(byte[] rawData){
        return new byte[0];
    }

}
