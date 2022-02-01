package utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

// ------------------------------------------------------------
//                       MEMORY UTILS
// ------------------------------------------------------------
//
public enum MemoryUtils {
    ;

    // ----------------------------------------------
    //  SIZE CONVERSIONS.
    // ----------------------------------------------
    //
    public static int  bytesToGB(final int size)    { return size / 1073741824; }

    public static long bytesToGB(final double size) { return (long) size / 1073741824L; }

    public static int  bytesToMB(final int size)    { return size / 1048576; }

    public static long bytesToMB(final double size) { return (long) size / 1048576L; }

    public static int  bytesToKB(final int size)    { return size / 1024; }

    public static long bytesToKB(final double size) { return (long) size / 1024L; }

    public static int  GBToBytes(final int size)    { return size * 1073741824; }

    public static long GBToBytes(final double size) { return (long) size * 1073741824L; }

    public static int  MBToBytes(final int size)    { return size * 1048576; }

    public static long MBToBytes(final double size) { return (long) size * 1048576L; }

    public static int  KBToBytes(final int size)    { return size * 1024; }

    public static long KBToBytes(final double size) { return (long) size * 1024L; }

    // ----------------------------------------------
    //  UNSAFE ACCESS.
    // ----------------------------------------------
    //
    private static final Unsafe U;

    static {
        try {
            final PrivilegedExceptionAction<Unsafe> action = () -> {
                final Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                return (Unsafe) f.get(null);
            };
            U = AccessController.doPrivileged(action);
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // ----------------------------------------------
    //  CONSTANTS.
    // ----------------------------------------------
    //
    public static final int MEM_PAGE_SIZE = U.pageSize();
}
