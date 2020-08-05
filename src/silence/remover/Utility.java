package silence.remover;

public class Utility {

    public static byte[] removeSilence(byte[] data, boolean isBigEndian) {
        if (isBigEndian) {
            return removeSilenceBigEndian(data);
        } else {
            return removeSilenceLittleEndian(data);
        }
    }

    private static byte[] removeSilenceBigEndian(byte[] data){
        return new byte[0];
    }

    private static byte[] removeSilenceLittleEndian(byte[] data){
        return new byte[0];
    }

}
