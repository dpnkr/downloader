package com.applications.downloader;

import com.applications.downloader.agents.UnsupportedProtocol;
import com.applications.downloader.download.InputContext;
import com.applications.downloader.download.Manager;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Executor {

    public void execute(String url, String filePath, boolean inParts, int noParts)
            throws IOException, InterruptedException, ExecutionException, UnsupportedProtocol {

        InputContext inputContext = new InputContext();
        inputContext.setUrlPath(url);
        inputContext.setDownloadInParts(inParts);
        inputContext.setNoParts(noParts);
        inputContext.setOutputPath(filePath);

        Manager manager = new Manager(inputContext);
        manager.exec();
    }
}
