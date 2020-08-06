import com.sun.media.sound.WaveFileWriter;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

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
            AudioFormat audioFormat = audioInputStream.getFormat();

            int length = (int) (audioInputStream.getFrameLength() * audioFormat.getFrameSize());

            byte[] audioData = new byte[length];
            DataInputStream dataInputStream = new DataInputStream(audioInputStream);
            dataInputStream.readFully(audioData);

            // Trim the silence from the original byte array and place into new one.
            byte[] newData = Utility.trimSilence(audioData, audioFormat.isBigEndian());
            String newFilePath = Utility.generateNewFilePath(filePath);

            WaveFileWriter waveFileWriter = new WaveFileWriter();
            waveFileWriter.write(new WaveInputStream(newData, audioFormat), AudioFileFormat.Type.WAVE, new File(newFilePath));

        } catch (UnsupportedAudioFileException e) {
            System.out.println("Sorry, this audio format is currently not supported.");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("There was an issue trying to read the file.");
            System.exit(-1);
        }
    }
}
