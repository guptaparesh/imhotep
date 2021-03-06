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
 package com.indeed.imhotep.metrics;

import com.google.common.primitives.Longs;
import com.indeed.flamdex.api.IntValueLookup;
import com.indeed.imhotep.MemoryReserver;
import com.indeed.imhotep.api.ImhotepOutOfMemoryException;

/**
 * A precomputed, cached version of an arbitrary metric
 * @author dwahler
 */
public class CachedMetric implements IntValueLookup {
    private final MemoryReserver memory;
    private long[] values;
    private long min, max;

    public CachedMetric(IntValueLookup original, int numDocs, MemoryReserver memory) throws ImhotepOutOfMemoryException {
        this.memory = memory;

        if (!memory.claimMemory(numDocs * 8L)) {
            throw new ImhotepOutOfMemoryException();
        }

        this.values = new long[numDocs];
        fillValues(original);
    }

    private void fillValues(IntValueLookup original) {
        final int BUFFER_SIZE = 8192;
        final int[] idBuffer = new int[BUFFER_SIZE];
        final long[] valBuffer = new long[BUFFER_SIZE];

        for (int start = 0; start < values.length; start += BUFFER_SIZE) {
            final int end = Math.min(values.length, start+BUFFER_SIZE), n = end-start;
            for (int i = 0; i < n; i++) {
                idBuffer[i] = start + i;
            }
            original.lookup(idBuffer, valBuffer, n);
            System.arraycopy(valBuffer, 0, values, start, n);
        }

        min = Longs.min(values);
        max = Longs.max(values);
    }

    @Override
    public long getMin() {
        return min;
    }

    @Override
    public long getMax() {
        return max;
    }

    @Override
    public void lookup(int[] docIds, long[] values, int n) {
        for (int i = 0; i < n; i++) {
            values[i] = this.values[docIds[i]];
        }
    }

    @Override
    public long memoryUsed() {
        return 8L * values.length;
    }

    @Override
    public void close() {
        final long bytesToFree = memoryUsed();
        values = null;
        memory.releaseMemory(bytesToFree);
    }
}
