/*
 * Copyright (C) 2014 Indeed Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
 package com.indeed.imhotep.archive;

import com.indeed.imhotep.archive.compression.SquallArchiveCompressor;

/**
 * @author jsgroth
 */
public class FileMetadata {
    private String filename;
    private final long size;
    private final long timestamp;
    private final String checksum;
    private final long startOffset;
    private final SquallArchiveCompressor compressor;
    private String archiveFilename;

    public FileMetadata(String filename,
                        long size,
                        long timestamp,
                        String checksum,
                        long startOffset,
                        SquallArchiveCompressor compressor,
                        String archiveFilename) {
        this.filename = filename;
        this.size = size;
        this.timestamp = timestamp;
        this.checksum = checksum;
        this.startOffset = startOffset;
        this.compressor = compressor;
        this.archiveFilename = archiveFilename;
    }

    public String getFilename() {
        return filename;
    }

    public long getSize() {
        return size;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getChecksum() {
        return checksum;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public SquallArchiveCompressor getCompressor() {
        return compressor;
    }

    public String getArchiveFilename() {
        return archiveFilename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileMetadata that = (FileMetadata) o;

        if (size != that.size) return false;
        if (startOffset != that.startOffset) return false;
        if (timestamp != that.timestamp) return false;
        if (archiveFilename != null ? !archiveFilename.equals(that.archiveFilename) : that.archiveFilename != null) {
            return false;
        }
        if (checksum != null ? !checksum.equals(that.checksum) : that.checksum != null) return false;
        if (compressor != that.compressor) return false;
        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = filename != null ? filename.hashCode() : 0;
        result = 31 * result + (int) (size ^ (size >>> 32));
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (checksum != null ? checksum.hashCode() : 0);
        result = 31 * result + (int) (startOffset ^ (startOffset >>> 32));
        result = 31 * result + (compressor != null ? compressor.hashCode() : 0);
        result = 31 * result + (archiveFilename != null ? archiveFilename.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FileMetadata{" +
                "filename='" + filename + '\'' +
                ", size=" + size +
                ", timestamp=" + timestamp +
                ", checksum='" + checksum + '\'' +
                ", startOffset=" + startOffset +
                ", compressor=" + compressor +
                '}';
    }

    public void setFilename(String str) {
        this.filename = str;
    }

    public void setArchiveFilename(String str) {
        this.archiveFilename = str;
    }
}
