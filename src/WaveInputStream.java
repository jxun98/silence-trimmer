import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayInputStream;

public class WaveInputStream extends AudioInputStream{

    public WaveInputStream(byte[] data, AudioFormat format) {
        super(new ByteArrayInputStream(data), format, data.length / format.getFrameSize());
    }
}
