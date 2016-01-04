package com.indeed.imhotep.io.caching.RemoteCaching;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileStore;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by darren on 10/13/15.
 */
public abstract class RemoteFileStore extends FileStore {
    protected static final String DELIMITER = "/";

    @Override
    public long getTotalSpace() throws IOException {
        return 0;
    }

    @Override
    public long getUsableSpace() throws IOException {
        return 0;
    }

    @Override
    public long getUnallocatedSpace() throws IOException {
        return 0;
    }

    public abstract ArrayList<RemoteFileInfo> listDir(RemoteCachingPath path) throws IOException;

    public RemoteFileInfo readInfo(RemoteCachingPath path) throws IOException {
        return readInfo(path.toString());
    }

    public abstract RemoteFileInfo readInfo(String shardPath) throws IOException;

    public abstract void downloadFile(RemoteCachingPath path, Path tmpPath) throws IOException;

    public abstract InputStream getInputStream(String path,
                                               long startOffset,
                                               long length) throws IOException;

    public static class RemoteFileInfo {
        String path;
        long size;
        final boolean isFile;

        public RemoteFileInfo(String path, long size, boolean isFile) {
            this.path = path;
            this.size = size;
            this.isFile = isFile;
        }
    }
}
