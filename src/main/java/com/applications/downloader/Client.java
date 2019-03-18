package com.applications.downloader;

import com.applications.downloader.agents.UnsupportedProtocol;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException,
            ExecutionException, UnsupportedProtocol {
//        System.out.println("welcome to downloader");
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("Enter url : ");
//        String urlPath = br.readLine();
//        System.out.println("Enter file location : ");
//        String filePath = br.readLine();

        Executor executor = new Executor();
        executor.execute("https://speed.hetzner.de/100MB.bin",
                Constants.TEST_OUTPUT_LOCATION, true, 16);

    }
}
