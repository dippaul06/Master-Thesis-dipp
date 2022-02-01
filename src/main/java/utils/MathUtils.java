package utils;

import static com.google.common.base.Preconditions.checkState;
import static java.lang.Integer.numberOfLeadingZeros;

// ------------------------------------------------------------
//                       MATH UTILS
// ------------------------------------------------------------
//
public enum MathUtils {
    ;

    // ----------------------------------------------
    //  CONSTANTS.
    // ----------------------------------------------
    //
    public static final int MAX_INT_POW_2 = 1073741824;

    // ----------------------------------------------
    //  FUNCTIONS.
    // ----------------------------------------------
    //
    public static boolean fitsLongInInt(long x) { return (int) x == x; }

    public static boolean isPowerOfTwo(final int v) { return v != 0 && (v & v - 1) == 0; }

    public static boolean isPowerOfTwo(final long v) { return v != 0 && (v & v - 1) == 0; }

    public static int log2p(final int val) { checkState(val != 0L);return 31 - numberOfLeadingZeros(val); }

    public static int log2p(final long val) { checkState(val != 0L);return 63 - Long.numberOfLeadingZeros(val); }

    public static int nextPowerOfTwo(final int value) { return 1 << (32 - numberOfLeadingZeros(value - 1)); }

    public static long nextPowerOfTwo( long x ) {
        if ( x == 0 ) return 1;
        x--;
        x |= x >> 1;
        x |= x >> 2;
        x |= x >> 4;
        x |= x >> 8;
        x |= x >> 16;
        return ( x | x >> 32 ) + 1;
    }
}
