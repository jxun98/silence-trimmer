import java.util.Arrays;

public class AudioProcessor {

    public static byte[] trimSilence(byte[] data, boolean isBigEndian) {
        if (isBigEndian) {
            return trimSilenceBigEndian(data);
        } else {
            return trimSilenceLittleEndian(data);
        }
    }

    private static byte[] trimSilenceBigEndian(byte[] rawData){

        // Start of sound in clip, inclusive.
        int startIndex = getStartIndexBigEndian(rawData);

        // The whole data stream is silence, no point in extra processing.
        if (startIndex == -1) {
            return new byte[0];
        }

        // End of sound in clip, exclusive.
        int endIndex = getEndIndexBigEndian(rawData);

        byte[] newData = Arrays.copyOfRange(rawData, startIndex, endIndex);

        return newData;
    }

    private static int getStartIndexBigEndian(byte[] data) {

        int index = -1;

        for (int i=0; i<data.length-1; i+=2) {
            short bigHalf = (short) data[i];
            short smallHalf = (short) data[i+1];

            bigHalf = (short) ((bigHalf & 0xff) << 8);
            smallHalf = (short) (smallHalf & 0xff);

            short sampleValue = (short) (bigHalf + smallHalf);

            // Non-zero sample found, we've reached some sound.
            if (sampleValue != 0) {
                index = i;
                return index;
            }
        }

        return index;
    }

    private static int getEndIndexBigEndian(byte[] data) {

        int index = -1;

        for (int i = data.length-2; i>=0; i-=2) {
            short bigHalf = (short) data[i];
            short smallHalf = (short) data[i+1];

            bigHalf = (short) ((bigHalf & 0xff) << 8);
            smallHalf = (short) (smallHalf & 0xff);

            short sampleValue = (short) (bigHalf + smallHalf);

            // Non-zero sample found, we've reached some sound.
            // index + 2 as we want to keep this current sample when cutting.
            if (sampleValue != 0) {
                index = i+2;
                return index;
            }
        }

        return index;
    }

    private static byte[] trimSilenceLittleEndian(byte[] rawData){
        // Start of sound in clip, inclusive.
        int startIndex = getStartIndexLittleEndian(rawData);

        // The whole data stream is silence, no point in extra processing.
        if (startIndex == -1) {
            return new byte[0];
        }

        // End of sound in clip, exclusive.
        int endIndex = getEndIndexLittleEndian(rawData);

        byte[] newData = Arrays.copyOfRange(rawData, startIndex, endIndex);

        return newData;
    }

    private static int getStartIndexLittleEndian(byte[] data) {
        int index = -1;

        for (int i=0; i<data.length-1; i+=2) {
            short bigHalf = (short) data[i+1];
            short smallHalf = (short) data[i];

            bigHalf = (short) ((bigHalf & 0xff) << 8);
            smallHalf = (short) (smallHalf & 0xff);

            short sampleValue = (short) (bigHalf + smallHalf);

            // Non-zero sample found, we've reached some sound.
            if (sampleValue > 0) {
                index = i;
                return index;
            }
        }

        return index;
    }

    private static int getEndIndexLittleEndian(byte[] data) {
        int index = -1;

        for (int i = data.length-2; i>=0; i-=2) {
            short bigHalf = (short) data[i+1];
            short smallHalf = (short) data[i];

            bigHalf = (short) ((bigHalf & 0xff) << 8);
            smallHalf = (short) (smallHalf & 0xff);

            short sampleValue = (short) (bigHalf + smallHalf);

            // Non-zero sample found, we've reached some sound.
            // index + 2 as we want to keep this current sample when cutting.
            if (sampleValue > 0) {
                index = i+2;
                return index;
            }
        }

        return index;
    }
}
