import com.sun.media.sound.WaveFileWriter;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

public class SilenceTrimmer {

    public static void main(String[] args) {

        while (true) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV audio files", "wav");
            fileChooser.setFileFilter(filter);

            int chooserResult = fileChooser.showOpenDialog(null);

            // Select workflow based upon user's decisions in JFileChooser.
            switch (chooserResult) {
                case JFileChooser.APPROVE_OPTION: {
                    int trimResult = removeSilence(fileChooser.getSelectedFile().getPath());

                    if (trimResult == 0) {
                        JOptionPane.showMessageDialog(null, "Finished trimming!", "Done", JOptionPane.INFORMATION_MESSAGE);
                    }

                    int continueResult = JOptionPane.showConfirmDialog(null, "Would you like to trim another file?", "Continue?", JOptionPane.YES_NO_OPTION);

                    if (continueResult == JOptionPane.NO_OPTION || continueResult == JOptionPane.CLOSED_OPTION) {
                        System.exit(0);
                    }

                    break;
                }
                case JFileChooser.ERROR_OPTION: {
                    JOptionPane.showMessageDialog(null, "An error occurred while trying to grab your file.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                case JFileChooser.CANCEL_OPTION: {
                    System.exit(0);
                    break;
                }
            }
        }
    }

    private static int removeSilence(String filePath) {
        try {
            // Sets up the appropriate stream classes to take in audio data from file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            AudioFormat audioFormat = audioInputStream.getFormat();

            // Calculate appropriate length for byte stream array.
            int length = (int) (audioInputStream.getFrameLength() * audioFormat.getFrameSize());

            byte[] audioData = new byte[length];
            DataInputStream dataInputStream = new DataInputStream(audioInputStream);
            dataInputStream.readFully(audioData);

            // Trim the silence from the original byte array and place into new one.
            byte[] newData = AudioProcessor.trimSilence(audioData, audioFormat.isBigEndian());
            String newFilePath = generateNewFilePath(filePath);

            WaveFileWriter waveFileWriter = new WaveFileWriter();
            waveFileWriter.write(new WaveInputStream(newData, audioFormat), AudioFileFormat.Type.WAVE, new File(newFilePath));

            return 0;
        } catch (UnsupportedAudioFileException e) {
            JOptionPane.showMessageDialog(null, "Sorry, this file format is currently not supported.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An issue occurred while trying to read the selected file.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    private static String generateNewFilePath(String originalFilePath) {
        String newFilePath = new StringBuilder(originalFilePath).insert(originalFilePath.length()-4, "_trimmed").toString();

        return newFilePath;
    }

}
