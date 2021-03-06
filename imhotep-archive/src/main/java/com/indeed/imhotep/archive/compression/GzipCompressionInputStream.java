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
 package com.indeed.imhotep.archive.compression;

import com.indeed.util.compress.CompressionInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * @author jsgroth
 */
public class GzipCompressionInputStream extends CompressionInputStream {
    public GzipCompressionInputStream(InputStream in) throws IOException {
        super(new ResettableGZIPInputStream(in));
    }

    @Override
    public int read(byte[] bytes, int off, int len) throws IOException {
        return in.read(bytes, off, len);
    }

    @Override
    public void resetState() throws IOException {
        ((ResettableGZIPInputStream)in).resetState();
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    private static class ResettableGZIPInputStream extends GZIPInputStream {
        private ResettableGZIPInputStream(InputStream in) throws IOException {
            super(in);
        }

        private void resetState() {
            inf.reset();
        }
    }
}
