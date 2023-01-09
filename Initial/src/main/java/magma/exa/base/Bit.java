//     _____
//    ╱     ╲┌────╭─╭──────╭─────────╮────╭─╮
//   ╱  ╲ ╱  ╲ ┌──╮ │ ╭──╮ ╮ ┬─╮ ┬─╮ ┌ ┌──╮ │
//  ╱    Y    ╲└──╰ ╵ ╰──╯ │ │ │ │ │ │ └──╰ │
//  ╲____│____╱╰──╰─┴╭───╯ ╰─╰─╯ ╰─╰─┴────╰─┴╲╲
//            ╲╭─────┘─────└────────────────╮╱╱
//
// Copyright (C) esentri.magma - All Rights Reserved.
//
// Unauthorized copying of this file, via any medium
// is strictly prohibited. Proprietary and confidential.

package magma.exa.base;

import magma.exa.control.exception.Exceptions;

import java.util.Arrays;

/**
 * Collection of bit-level utilities.
 */
public enum Bit {
    ;
    // ----------------------------------------------------------
    //  BIT.FORMAT
    // ----------------------------------------------------------

    /**
     * Determines whether the given {@code int} value is even.
     *
     * @param value value to be checked.
     * @return true iff the value is even.
     */
    public static boolean isEven(final int value) {
        return (value & 0b1) == 0;
    }

    /**
     * Determines whether the given {@code long} value is even.
     *
     * @param value value to be checked.
     * @return true iff the value is even.
     */
    public static boolean isEven(final long value) {
        return (value & 0b1) == 0;
    }

    /**
     * Determines whether the given {@code int} value is a positive power of 2.
     *
     * @param value value to be checked.
     * @return true iff a positive power of 2.
     */
    public static boolean isPowerOfTwo(final int value) {
        return value > 0 && ((value & (~value + 1)) == value);
    }

    /**
     * Determines whether the given {@code long} value is a positive power of 2.
     *
     * @param value value to be checked.
     * @return true iff a positive power of 2.
     */
    public static boolean isPowerOfTwo(final long value) {
        return value > 0 && ((value & (~value + 1)) == value);
    }

    /**
     * Returns log2 of given power of two.
     *
     * @param value whose log2 is to be returned.
     * @return log2 of given value.
     */
    public static int exactLog2(final int value) {
        if ((value & (value - 1)) != 0) {
            throw new IllegalArgumentException("illegal value: not a power of two.");
        }
        return Integer.numberOfTrailingZeros(value);
    }

    /**
     * Returns log2 of given power of two.
     *
     * @param value whose log2 is to be returned.
     * @return log2 of given value.
     */
    public static long exactLog2(final long value) {
        if ((value & (value - 1L)) != 0L) {
            throw new IllegalArgumentException("illegal value: not a power of two.");
        }
        return Long.numberOfTrailingZeros(value);
    }

    /**
     * Fast method of finding the next power of 2 greater than or equal to the
     * supplied value. If the value is <= 0 then 1 will be returned. This method
     * is not suitable for {@link Integer#MIN_VALUE} or numbers greater than 2^30.
     * When provided then {@link Integer#MIN_VALUE} will be returned.
     *
     * @param value value from which to search for next power of 2.
     * @return next power of 2 or the value itself if it is a power of 2.
     */
    public static int findNextPositivePowerOfTwo(final int value) {
        return 1 << (Integer.SIZE - Integer.numberOfLeadingZeros(value - 1));
    }

    /**
     * Fast method of finding the next power of 2 greater than or equal to the
     * supplied value. If the value is <= 0 0 then 1 will be returned. This method
     * is not suitable for {@link Long#MIN_VALUE} or numbers greater than 2^62.
     * When provided then {@link Long#MIN_VALUE} will be returned.
     *
     * @param value value from which to search for next power of 2.
     * @return next power of 2 or the value itself if it is a power of 2.
     */
    public static long findNextPositivePowerOfTwo(final long value) {
        return 1L << (Long.SIZE - Long.numberOfLeadingZeros(value - 1));
    }

    // ----------------------------------------------------------
    //  BIT.VECTOR
    // ----------------------------------------------------------

    /**
     * Operations to manage compact, fix-sized vectors of bit values.
     * Bit vectors are packed into arrays of 'blocks'. Currently, a
     * block is a {@code long} consisting of 64 bits and requiring 6
     * address bits. The choice of block size is determined entirely
     * by performance considerations.
     */
    public enum Vec {
        ;
        static final long[] EMPTY = { };

        public static final long BLOCK_MASK = 0xFFFFFFFFFFFFFFFFL;

        public static final int  ADDR_BITS  = 6;

        public static final int  DATA_BITS  = 1 << ADDR_BITS;

        public static final int  INDEX_MASK = DATA_BITS - 1;

        // ----------------------------------------------------------

        /**
         * Given a bit index, return block-index containing it.
         */
        public static int blockIndex(final int bitIndex) {

            return bitIndex >> ADDR_BITS;
        }

        // ----------------------------------------------------------

        /**
         * Allocates a new bit-vector with the given minimum capacity.
         *
         * @param capacity required by the new bit-vector.
         * @return new bit-vector.
         */
        public static long[] make(final int capacity) {

            return (capacity > 0)

                    ? new long[((capacity - 1) >> ADDR_BITS) + 1]

                    : EMPTY;
        }

        // ----------------------------------------------------------

        /**
         * Ensures the {@code required} capacity of the given bit-vector.
         * If the length of the source bit-vector is greater than or equal
         * to the required bit-capacity, this operation has no effect. If
         * the required capacity is greater than the bit-capacity of the
         * given source bit-vector, a new bit-vector is allocated. The
         * contents of the given source vector are transferred to the
         * new bit-vector.
         *
         * @param bits     source bit-vector whose capacity is to be ensured.
         * @param required capacity to be ensured for the source bit-vector.
         * @return original or a resized bit-vector.
         */
        public static long[] ensure(final long[] bits, final int required) {

            final int words;

            if ((words = ((required - 1) >> ADDR_BITS) + 1) > bits.length) {

                final var resized = new long[words];

                System.arraycopy(bits, 0, resized, 0, bits.length);

                return resized;
            }

            return bits;
        }

        // ----------------------------------------------------------

        /**
         * Returns the bit-capacity of the given bit-vector.
         *
         * @param bits bit-vector whose bit-capacity is to be returned.
         * @return bit-capacity.
         */
        public static int capacity(final long[] bits) {

            return bits.length << ADDR_BITS;
        }

        // ----------------------------------------------------------

        /**
         * Returns the "logical size" of the given bit-vector, i.e. the
         * index of the highest set bit in the given bit-vector plus one.
         * Returns zero if the bit-vector contains no set bits.
         *
         * @return logical count of the given bit-vector.
         */
        public static int count(final long[] bits) {

            final int blockCount = bits.length;

            if (blockCount == 0) return 0;

            return (DATA_BITS * (blockCount - 1))

                    + (DATA_BITS - Long.numberOfLeadingZeros(bits[blockCount - 1]));
        }

        // ----------------------------------------------------------

        /**
         * Returns the value of the bit with the given index in the
         * given bit-vector.
         *
         * @param bits bit-vector to be queried.
         * @param idx  index of the bit to query.
         * @return bit at the given index.
         */
        public static boolean at(final long[] bits, int idx) {

            final int bix = idx >> ADDR_BITS;

            if (bix >= bits.length) {

                throw outOfBounds(idx, capacity(bits));
            }

            return (bits[bix] & (1L << idx)) != 0L;
        }

        // ----------------------------------------------------------

        /**
         * Sets the bit at the specified index to the complement
         * of its current value.
         *
         * @param bits bit-vector to be manipulated.
         * @param idx  index of the bit to flip.
         */
        public static boolean flip(final long[] bits, final int idx) {

            final int bix = idx >> ADDR_BITS;

            if (bix >= bits.length) {

                throw outOfBounds(idx, capacity(bits));
            }

            return (bits[bix] ^= (1L << idx)) != 0L;
        }

        // ----------------------------------------------------------

        /**
         * Sets the bit at the given index to true.
         *
         * @param bits bit-vector to be manipulated.
         * @param idx  index of the bit to set.
         */
        public static void set(final long[] bits, final int idx) {

            final int bix = idx >> ADDR_BITS;

            if (bix >= bits.length) {

                throw outOfBounds(idx, capacity(bits));
            }

            bits[bix] |= (1L << idx);
        }

        // ----------------------------------------------------------

        /**
         * Sets the bit specified by the index to false.
         *
         * @param bits bit-vector to be manipulated.
         * @param idx  index of the bit to clear.
         */
        public static void clear(final long[] bits, final int idx) {

            final int bix = idx >> ADDR_BITS;

            if (bix >= bits.length) {

                throw outOfBounds(idx, capacity(bits));
            }

            bits[bix] &= ~(1L << idx);
        }

        // ----------------------------------------------------------

        /**
         * Sets all bits in the given bit-vector to false.
         *
         * @param bits bit-vector to be cleared.
         * @return bit-vector.
         */
        public static long[] clear(final long[] bits) {

            Arrays.fill(bits, 0L);

            return bits;
        }

        // ----------------------------------------------------------

        /**
         * Returns the index of the first bit that is set to true that
         * occurs on or after the specified starting index in the given
         * bit-vector. If no such bit exists then {@code -1} is returned.
         *
         * @param bitIndex to start (inclusive).
         * @return index of the next set bit, or -1.
         */
        public static int nextSetBit(final long[] bits, final int bitIndex) {

            int bix = bitIndex >> ADDR_BITS;

            final var blockCount = bits.length;

            if (bix >= blockCount) return -1;

            long block = bits[bix] & (BLOCK_MASK << bitIndex);

            while (true) {

                if (block != 0) {

                    return (bix * DATA_BITS) + Long.numberOfTrailingZeros(block);
                }

                if (++bix == blockCount) return -1;

                block = bits[bix];
            }
        }

        // ----------------------------------------------------------

        /**
         * Returns the index of the first bit that is set to false that
         * occurs on or after the given starting index in the given bit-
         * vector.
         *
         * @param bitIndex to start (inclusive).
         * @return index of the next cleared bit, or -1.
         */
        public static int nextClearBit(final long[] bits, final int bitIndex) {

            int bix = bitIndex >> ADDR_BITS;

            final var blockCount = bits.length;

            if (bix >= blockCount) return bitIndex;

            long block = ~bits[bix] & (BLOCK_MASK << bitIndex);

            while (true) {

                if (block != 0) {

                    return (bix * DATA_BITS) + Long.numberOfTrailingZeros(block);
                }

                if (++bix == blockCount) return blockCount * DATA_BITS;

                block = ~bits[bix];
            }
        }

        // ----------------------------------------------------------

        /**
         * Returns the index of the nearest bit that is set to true
         * that occurs on or before the given starting index. If no
         * such bit exists, or if -1 is given as the starting index,
         * then -1 is returned.
         *
         * @param bitIndex to start (inclusive).
         * @return index of the previous set bit, or -1.
         */
        @SuppressWarnings("ShiftOutOfRange")
        public static int prevSetBit(final long[] bits, final int bitIndex) {

            if (bitIndex < 0) {

                if (bitIndex == -1) return -1;

                throw negativeIndex(bitIndex);
            }

            int bix = blockIndex(bitIndex);

            if (bix >= bits.length) return bits.length - 1;

            long block = bits[bix] & (BLOCK_MASK >>> -(bitIndex + 1));

            while (true) {

                if (block != 0) {

                    return (bix + 1) * DATA_BITS - 1 - Long.numberOfLeadingZeros(block);
                }

                if (bix-- == 0) return -1;

                block = bits[bix];
            }
        }

        // ----------------------------------------------------------

        /**
         * Returns the index of the nearest bit that is set to false that
         * occurs on or before the given starting index. If no such bit
         * exists, or if -1 is given as the start index, then -1 is returned.
         *
         * @param bitIndex to start (inclusive).
         * @return index of the previous cleared bit, or -1.
         */
        @SuppressWarnings("ShiftOutOfRange")
        public static int prevClearBit(final long[] bits, final int bitIndex) {

            if (bitIndex < 0) {

                if (bitIndex == -1) return -1;

                throw negativeIndex(bitIndex);
            }

            int bix = blockIndex(bitIndex);

            if (bix >= bits.length) return bitIndex;

            long block = ~bits[bix] & (BLOCK_MASK >>> -(bitIndex + 1));

            while (true) {

                if (block != 0) {

                    return (bix + 1) * DATA_BITS - 1 - Long.numberOfLeadingZeros(block);
                }

                if (bix-- == 0) {

                    return -1;
                }

                block = ~bits[bix];
            }
        }

        // ----------------------------------------------------------

        /**
         *  TODO: NOT TESTED
         * Returns a new bit-vector composed of bits from the given bit-
         * vector from {@code lo} (inclusive) to {@code hi} (exclusive).
         *
         * @param lo index of the first bit to include.
         * @param hi index after the last bit to include.
         * @return new bit-vector from a range of the source bit-vector.
         */
        public static long[] at(final long[] bits, int lo, int hi) {

            final int length = capacity(bits);

            // If no set bits in range return empty bitset.
            //
            if (length <= lo || lo == hi) {

                return EMPTY;
            }

            // An optimization.

            if (hi > length) hi = length;

            final var out = make(hi - lo);

            final int targetWords = blockIndex(hi - lo - 1) + 1;

            int sourceIndex = blockIndex(lo);

            final boolean aligned = 0 == (lo & INDEX_MASK);

            // Process all words but the last word.

            for (int i = 0; i < targetWords - 1; ++i, ++sourceIndex) {

                out[i] = aligned ? bits[sourceIndex] : (bits[sourceIndex] >>> lo) | (bits[sourceIndex + 1] << -lo);
            }

            // Process the last word.

            long lastWordMask = BLOCK_MASK >>> -hi;

            out[targetWords - 1] = ((hi - 1) & INDEX_MASK) < (lo & INDEX_MASK)

                    ? // straddles source words
                    ((bits[sourceIndex] >>> lo) | (bits[sourceIndex + 1] & lastWordMask) << -lo)
                    :
                    ((bits[sourceIndex] & lastWordMask) >>> lo)
                    ;

            if (targetWords != out.length) {

                throw Exceptions.illegalState("ficken");
            }

            return out;
        }

        // ----------------------------------------------------------

        /**
         * Returns the number of bits set to true of the given bit-vector.
         *
         * @param bits bit-vector whose cardinality is to be determined.
         * @return number of bits set to true.
         */
        public static int cardinality(final long[] bits) {

            int count = 0;

            for (int i = 0; i < bits.length; ++i) {

                count += Long.bitCount(bits[i]);
            }
            return count;
        }

        // ----------------------------------------------------------

        private static IndexOutOfBoundsException negativeIndex(final int bitIndex) {
            throw Exceptions.outOfBounds("illegal-index: idx(%d) < 0", bitIndex);
        }

        private static IndexOutOfBoundsException outOfBounds(final int bitIndex, final int capacity) {
            throw Exceptions.outOfBounds("illegal-index: idx(%d) < vector[%d]", bitIndex, capacity);
        }
    }

    // ----------------------------------------------------------
    //  BIT.SHOW
    // ----------------------------------------------------------

    /*public enum Show {
        ;
        public static String asString(final short value) { return String.format("%16s", Integer.toBinaryString(value)).replaceAll(" ", "0"); }
        public static String asString(final short value, final int block) { return asString(value, block, " "); }
        public static String asString(final short value, final int block, final String delimiter) {
            return bitToString(asString(value), block, delimiter);
        }

        public static String asString(final char value) { return String.format("%16s", Integer.toBinaryString(value)).replaceAll(" ", "0"); }
        public static String asString(final char value, final int block) { return bitToString(asString(value), block, " "); }
        public static String asString(final char value, final int block, final String delimiter) {
            return bitToString(asString(value), block, delimiter);
        }

        public static String asString(final int value) { return String.format("%32s", Integer.toBinaryString(value)).replaceAll(" ", "0"); }
        public static String asString(final int value, final int block) { return bitToString(asString(value), block, ""); }
        public static String asString(final int value, final int block, final String delimiter) {
            return bitToString(asString(value), block, delimiter);
        }

        public static String asString(final long value) { return String.format("%64s", Long.toBinaryString(value)).replaceAll(" ", "0"); }
        public static String asString(final long value, final int block) { return bitToString(asString(value), block, " "); }
        public static String asString(final long value, final int block, final String delimiter) {
            return bitToString(asString(value), block, delimiter);
        }

        private static String bitToString(final String value, final int block, final String delimiter) {
            final int l = value.length(), k = l / block;
            final var s = new String[k];
            for (int i = 0, j = 0; i < s.length; j += block, ++i) {
                s[i] = value.substring(j, Math.min(j + block, l));
            }
            return String.join(delimiter, s);
        }
    }*/
}
