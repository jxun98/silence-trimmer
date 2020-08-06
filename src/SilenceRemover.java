import javax.sound.sampled.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

public class SilenceRemover {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Please specify the file path of your audio clip.");
            System.exit(-1);
        }

        String filePath = args[0];

        try {
            // Sets up the appropriate stream classes to take in audio data from file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            DataInputStream dataInputStream = new DataInputStream(audioInputStream);

            AudioFormat audioFormat = audioInputStream.getFormat();

            // Byte array length is determined by (length of audio stream in frames) * (bytes per audio frame).
            byte[] audioData = new byte[(int) (audioInputStream.getFrameLength() * audioFormat.getFrameSize())];
            dataInputStream.readFully(audioData);

            Utility.trimSilence(audioData, audioFormat.isBigEndian());

        } catch (UnsupportedAudioFileException e) {
            System.out.println("Sorry, this audio format is currently not supported.");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("There was an issue trying to read the file.");
            System.exit(-1);
        }
    }
}
