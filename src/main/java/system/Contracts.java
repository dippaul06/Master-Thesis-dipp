package system;

import java.util.Collection;

import static system.Contracts.ExceptionType.*;

// ----------------------------------------------------------------
//                          CONTRACTS
// ----------------------------------------------------------------
/**
 * Static utilities for invariants, pre- and post-conditions.
 */
public enum Contracts {
    ;
    private static final String EMPTY_MSG = "";

    static String raise(final ExceptionType type) { return raise(type, EMPTY_MSG); }
    static String raise(final ExceptionType type, final String msg) {
        checkNotNull(type);
        {
            //Log.error(msg);
        }
        switch (checkNotNull(type)) {
            case ILLEGAL_ARGUMENT:
                throw new IllegalArgumentException(msg);
            case ILLEGAL_STATE:
                throw new IllegalStateException(msg);
            case OUT_OF_BOUNDS:
                throw new IndexOutOfBoundsException(msg);
            case NULL_POINTER:
                throw new NullPointerException(msg);
        }
        throw new RuntimeException(type + ": " + msg);
    }

    // --------------------------------------------------
    //  CHECK ARGUMENT.
    // --------------------------------------------------

    public static void checkArguments(Object o0, Object o1, Object o2) {
        int i = 0;
        if (o0 == null) raise(NULL_POINTER, formatMsg("element(%s) == null", i));
        ++i;
        if (o1 == null) raise(NULL_POINTER, formatMsg("element(%s) == null", i));
        ++i;
        if (o2 == null) raise(NULL_POINTER, formatMsg("element(%s) == null", i));
    }

    public static void checkArguments(Object o0, Object o1) {
        int i = 0;
        if (o0 == null) raise(NULL_POINTER, formatMsg("element(%s) == null", i));
        ++i;
        if (o1 == null) raise(NULL_POINTER, formatMsg("element(%s) == null", i));
    }

    public static void checkArguments(Object... os) {
        if (os == null) return;
        for (int i = 0; i < os.length; ++i) {
            if (os[i] == null) {
                raise(NULL_POINTER, formatMsg("element(%s) == null", i));
            }
        }
    }

    public static void checkArgument(boolean expr) {
        if (!expr) raise(ILLEGAL_ARGUMENT);
    }

    public static void checkArgument(boolean expr, Object errMsg) {
        if (!expr) raise(ILLEGAL_ARGUMENT, String.valueOf(errMsg));
    }

    public static void checkArgument(boolean expr, String errMsg, Object... errMsgArgs) {
        if (!expr) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, errMsgArgs));
    }

    public static void checkArgument(boolean b, String errMsg, char p1) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1));
    }

    public static void checkArgument(boolean b, String errMsg, int p1) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1));
    }

    public static void checkArgument(boolean b, String errMsg, long p1) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1));
    }

    public static void checkArgument(boolean b, String errMsg, Object p1) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1));
    }

    public static void checkArgument(boolean b, String errMsg, char p1, char p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, char p1, int p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, char p1, long p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, char p1, Object p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, int p1, char p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, int p1, int p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, int p1, long p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, int p1, Object p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, long p1, char p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, long p1, int p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, long p1, long p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, long p1, Object p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, Object p1, char p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, Object p1, int p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, Object p1, long p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, Object p1, Object p2) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2));
    }

    public static void checkArgument(boolean b, String errMsg, Object p1, Object p2, Object p3) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2, p3));
    }

    public static void checkArgument(boolean b, String errMsg, Object p1, Object p2, Object p3, Object p4) {
        if (!b) raise(ILLEGAL_ARGUMENT, formatMsg(errMsg, p1, p2, p3, p4));
    }

    // --------------------------------------------------
    //  CHECK STATE.
    // --------------------------------------------------

    public static void checkState(boolean expr) {
        if (!expr) raise(ILLEGAL_STATE);
    }

    public static void checkState(boolean expr, Object errMsg) {
        if (!expr) raise(ILLEGAL_STATE, String.valueOf(errMsg));
    }

    public static void checkState(boolean expr, String errMsg, Object... errMsgArgs) {
        if (!expr) raise(ILLEGAL_STATE, formatMsg(errMsg, errMsgArgs));
    }

    public static void checkState(boolean b, String errMsg, char p1) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1));
    }

    public static void checkState(boolean b, String errMsg, int p1) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1));
    }

    public static void checkState(boolean b, String errMsg, long p1) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1));
    }

    public static void checkState(boolean b, String errMsg, Object p1) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1));
    }

    public static void checkState(boolean b, String errMsg, char p1, char p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, char p1, int p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, char p1, long p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, char p1, Object p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, int p1, char p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, int p1, int p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, int p1, long p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, int p1, Object p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, long p1, char p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, long p1, int p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, long p1, long p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, long p1, Object p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, Object p1, char p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, Object p1, int p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, Object p1, long p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, Object p1, Object p2) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
    }

    public static void checkState(boolean b, String errMsg, Object p1, Object p2, Object p3) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2, p3));
    }

    public static void checkState(boolean b, String errMsg, Object p1, Object p2, Object p3, Object p4) {
        if (!b) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2, p3, p4));
    }

    // --------------------------------------------------
    //  CHECK IS NULL.
    // --------------------------------------------------

    public static <T> T checkIsNull(T o) {
        if (o != null) raise(ILLEGAL_STATE);
        return o;
    }

    public static <T> T checkIsNull(T o, Object errMsg) {
        if (o != null) raise(ILLEGAL_STATE, String.valueOf(errMsg));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, Object... errMsgArgs) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, errMsgArgs));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, char p1) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, int p1) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, long p1) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, Object p1) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, char p1, char p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, char p1, int p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, char p1, long p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, char p1, Object p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, int p1, char p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, int p1, int p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, int p1, long p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, int p1, Object p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, long p1, char p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, long p1, int p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, long p1, long p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, long p1, Object p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, Object p1, char p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, Object p1, int p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, Object p1, long p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, Object p1, Object p2) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, Object p1, Object p2, Object p3) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2, p3));
        return o;
    }

    public static <T> T checkIsNull(T o, String errMsg, Object p1, Object p2, Object p3, Object p4) {
        if (o != null) raise(ILLEGAL_STATE, formatMsg(errMsg, p1, p2, p3, p4));
        return o;
    }


    // --------------------------------------------------
    //  CHECK NOT NULL.
    // --------------------------------------------------

    public static <T> T checkNotNull(T o) {
        if (o == null) raise(NULL_POINTER);
        return o;
    }

    public static <T> T checkNotNull(T o, Object errMsg) {
        if (o == null) raise(NULL_POINTER, String.valueOf(errMsg));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, Object... errMsgArgs) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, errMsgArgs));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, char p1) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, int p1) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, long p1) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, Object p1) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, char p1, char p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, char p1, int p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, char p1, long p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, char p1, Object p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, int p1, char p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, int p1, int p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, int p1, long p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, int p1, Object p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, long p1, char p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, long p1, int p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, long p1, long p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, long p1, Object p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, Object p1, char p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, Object p1, int p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, Object p1, long p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, Object p1, Object p2) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, Object p1, Object p2, Object p3) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2, p3));
        return o;
    }

    public static <T> T checkNotNull(T o, String errMsg, Object p1, Object p2, Object p3, Object p4) {
        if (o == null) raise(NULL_POINTER, formatMsg(errMsg, p1, p2, p3, p4));
        return o;
    }

    // --------------------------------------------------
    //  CHECK-ALL NOT NULL.
    // --------------------------------------------------

    public static void checkAllNotNull(Object v1, Object v2) {
        if (v1 == null) raise(NULL_POINTER, "v1 == null");
        if (v2 == null) raise(NULL_POINTER, "v2 == null");
    }

    public static void checkAllNotNull(Object v1, Object v2, Object v3) {
        if (v1 == null) raise(NULL_POINTER, "v1 == null");
        if (v2 == null) raise(NULL_POINTER, "v2 == null");
        if (v3 == null) raise(NULL_POINTER, "v2 == null");
    }

    public static void checkAllNotNull(Object v1, Object v2, Object v3, Object v4) {
        if (v1 == null) raise(NULL_POINTER, "v1 == null");
        if (v2 == null) raise(NULL_POINTER, "v2 == null");
        if (v3 == null) raise(NULL_POINTER, "v2 == null");
        if (v4 == null) raise(NULL_POINTER, "v2 == null");
    }

    public static void checkAllNotNull(Object v1, Object v2, Object v3, Object v4, Object v5) {
        if (v1 == null) raise(NULL_POINTER, "v1 == null");
        if (v2 == null) raise(NULL_POINTER, "v2 == null");
        if (v3 == null) raise(NULL_POINTER, "v2 == null");
        if (v4 == null) raise(NULL_POINTER, "v2 == null");
        if (v5 == null) raise(NULL_POINTER, "v2 == null");
    }

    public static void checkAllNotNull(Object v1, Object v2, Object v3, Object v4, Object v5, Object v6) {
        if (v1 == null) raise(NULL_POINTER, "v1 == null");
        if (v2 == null) raise(NULL_POINTER, "v2 == null");
        if (v3 == null) raise(NULL_POINTER, "v2 == null");
        if (v4 == null) raise(NULL_POINTER, "v2 == null");
        if (v5 == null) raise(NULL_POINTER, "v2 == null");
        if (v6 == null) raise(NULL_POINTER, "v6 == null");
    }

    // --------------------------------------------------
    //  CHECK ELEMENT-INDICES.
    // --------------------------------------------------

    public static int checkElementIndex(int ix, int size) {
        return checkElementIndex(ix, size, "ix");
    }

    public static long checkElementIndex(long ix, long size) {
        return checkElementIndex(ix, size, "ix");
    }

    public static int checkElementIndex(int ix, int size, String desc) {
        if (ix < 0 || ix >= size)
            raise(OUT_OF_BOUNDS, badElementIndex(ix, size, desc));
        return ix;
    }

    public static long checkElementIndex(long ix, long size, String desc) {
        if (ix < 0 || ix >= size)
            raise(OUT_OF_BOUNDS, badElementIndex(ix, size, desc));
        return ix;
    }

    private static String badElementIndex(long ix, long size, String desc) {
        if (ix < 0) return formatMsg("%s (%s) must not be negative", desc, ix);
        else if (size < 0) {
            raise(ILLEGAL_ARGUMENT, "negative size: " + size);
            return EMPTY_MSG;
        } else { // index >= size
            return formatMsg("%s (%s) must be less than size (%s)", desc, ix, size);
        }
    }

    // --------------------------------------------------
    //  CHECK POSITION INDICES.
    // --------------------------------------------------

    public static int checkPositionIndex(int ix, int size) {
        return checkPositionIndex(ix, size, "ix");
    }

    public static long checkPositionIndex(long ix, long size) {
        return checkPositionIndex(ix, size, "ix");
    }

    public static int checkPositionIndex(int ix, int size, String desc) {
        if (ix < 0 || ix > size)
            raise(OUT_OF_BOUNDS, badPosIndex(ix, size, desc));
        return ix;
    }

    public static long checkPositionIndex(long ix, long size, String desc) {
        if (ix < 0 || ix > size)
            raise(OUT_OF_BOUNDS, badPosIndex(ix, size, desc));
        return ix;
    }

    public static void checkPositionIndexes(int start, int end, int size) {
        if (start < 0 || end < start || end > size)
            raise(OUT_OF_BOUNDS, badPosIndices(start, end, size));
    }

    public static void checkPositionIndexes(long start, long end, long size) {
        if (start < 0 || end < start || end > size)
            raise(OUT_OF_BOUNDS, badPosIndices(start, end, size));
    }

    private static String badPosIndex(long ix, long size, String desc) {
        if (ix < 0) return formatMsg("%s (%s) must not be negative", desc, ix);
        else if (size < 0) return raise(ILLEGAL_ARGUMENT, "negative size: " + size);
        else return formatMsg("%s (%s) must not be greater than size (%s)", desc, ix, size);
    }

    private static String badPosIndices(long start, long end, long size) {
        if (start < 0L || start > size) return badPosIndex(start, size, "start ix");
        if (end < 0L || end > size) return badPosIndex(end, size, "end ix");
        return formatMsg("end ix (%s) must not be less than start ix (%s)", end, start);
    }

    // --------------------------------------------------
    //  CHECK RANGES.
    // --------------------------------------------------

    public static void checkRange(int from, int to, int size) {
        if (from < 0 || from > size)
            raise(OUT_OF_BOUNDS, badRange(from, 0, size, ""));
        if (to < 0 || to > size)
            raise(OUT_OF_BOUNDS, badRange(to, 0, size, ""));
    }

    public static int checkIndex(int ix, int min, int max, String desc) {
        if (ix < min || ix >= max)
            raise(OUT_OF_BOUNDS, badRange(ix, min, max, desc));
        return ix;
    }

    private static String badRange(int ix, int min, int max, String desc) {
        if (ix < 0) return formatMsg("%s (%s) must not be less than (%s)", desc, ix, min);
        else return formatMsg("%s (%s) must not be greater than size (%s)", desc, ix, max);
    }

    // --------------------------------------------------
    //  ARRAY SIZE PRECONDITIONS.
    // --------------------------------------------------

    //public static void checkMaxArraySize(int size) {
    //    checkMaxArraySize(size, LangUtils.MAX_ARRAY_SIZE);
    //}

    public static void checkMaxArraySize(int size, int maximum) {
        if (size > maximum) {
            raise(ILLEGAL_STATE, "provided(" + size + ") exceeds" +
                    " maximum array size (= " + maximum + ").");
            badSize(maximum, size);
        }
    }

    public static <E> E[] checkContainerSize(E[] array, int required) {
        checkContainer(array);
        if (array.length < required) badSize(array.length, required);
        return array;
    }

    public static <E, C extends Collection<E>> C checkContainerSize(C collection, int required) {
        checkContainer(collection);
        final int size = collection.size();
        if (size < required) badSize(size, required);
        return collection;
    }

    public static int checkSize(int size, int required) {
        if (size < required) badSize(size, required);
        return size;
    }

    public static long checkSize(long size, long required) {
        if (size < required) badSize(size, required);
        return size;
    }

    private static void badSize(long size, long required) {
        raise(ILLEGAL_STATE, "insufficient size: provided(" + size + ") < required(" + required + ")");
    }

    private static void checkContainer(Collection<?> c) {
        raise(NULL_POINTER, "collection == null");
    }

    private static void checkContainer(Object o) {
        raise(NULL_POINTER, "array == null");
    }

    // --------------------------------------------------
    //  MATH FUNCTION PRECONDITIONS.
    // --------------------------------------------------

    public static int checkPositive(int x) {
        if (x <= 0) {
            throw new IllegalArgumentException("(" + x + ") must be > 0");
        }
        return x;
    }

    public static long checkPositive(long x) {
        if (x <= 0) {
            throw new IllegalArgumentException("(" + x + ") must be > 0");
        }
        return x;
    }

    public static int checkNonNegative(int x) {
        if (x < 0) {
            throw new IllegalArgumentException("(" + x + ") must be >= 0");
        }
        return x;
    }

    public static long checkNonNegative(long x) {
        if (x < 0) {
            throw new IllegalArgumentException("(" + x + ") must be >= 0");
        }
        return x;
    }

    public static double checkNonNegative(double x) {
        if (!(x >= 0)) { // not x < 0, to work with NaN.
            throw new IllegalArgumentException("(" + x + ") must be >= 0");
        }
        return x;
    }

    // --------------------------------------------------
    //  INTERNALS.
    // --------------------------------------------------

    private static String formatMsg(String template, Object... args) {
        template = String.valueOf(template); // null -> "null"
        args = args == null ? new Object[]{"(Object[])null"} : args;
        StringBuilder sb = new StringBuilder(template.length() + 16 * args.length);
        int tmStart = 0;
        int i = 0;
        while (i < args.length) {
            int phStart = template.indexOf("%s", tmStart);
            if (phStart == -1) break;
            sb.append(template, tmStart, phStart);
            sb.append(args[i++]);
            tmStart = phStart + 2;
        }
        sb.append(template, tmStart, template.length());
        if (i < args.length) {
            sb.append(" [");
            sb.append(args[i++]);
            while (i < args.length) {
                sb.append(", ");
                sb.append(args[i++]);
            }
            sb.append(']');
        }
        return sb.toString();
    }

    enum ExceptionType {
        ILLEGAL_ARGUMENT,   // IllegalArgumentException
        ILLEGAL_STATE,      // IllegalStateException
        OUT_OF_BOUNDS,      // IndexOutOfBoundsException
        NULL_POINTER,       // NullPointerException
    }
}

