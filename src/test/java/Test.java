import com.applications.downloader.Constants;
import com.applications.downloader.Executor;
import com.applications.downloader.agents.UnsupportedProtocol;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Test {

    public static void main(String[] args) throws IOException, InterruptedException,
            ExecutionException, UnsupportedProtocol {
        Executor executor = new Executor();
//        String url = "https://speed.hetzner.de/1GB.bin";
//        String url = "http://ipv4.download.thinkbroadband.com/200MB.zip";
//        String url = "http://speedtest.ftp.otenet.gr/files/test10Mb.db";
        String url = "https://speed.hetzner.de/100MB.bin";
//        String url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";
//        String url = "http://file-examples.com/wp-content/uploads/2017/10/file_example_JPG_2500kB.jpg";
        executor.execute(url, Constants.TEST_OUTPUT_LOCATION, true, 4);
    }
}
