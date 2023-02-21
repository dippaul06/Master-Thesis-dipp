package magma.utils;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import magma.utils.StrUtils.Color;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collector;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.util.concurrent.TimeUnit.SECONDS;

// ------------------------------------------------------------
//                        MAIN UTILS
// ------------------------------------------------------------
//
public enum MainUtils {
    ;
    // ----------------------------------------------
    //  NOT EQUALS.
    // ----------------------------------------------
    public static boolean notEquals(Object a, Object b) {
        return !a.equals(b);
    }

    // ----------------------------------------------
    //  SLEEP.
    // ----------------------------------------------
    public static void sleep(int sec) {
        try { Thread.sleep(SECONDS.toMillis(sec)); }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------
    //  COLLECTORS.
    // ----------------------------------------------
    //
    public static Collector<Long, LongSet, LongSet> toSet() {
        return Collector.of(
                LongOpenHashSet::new,
                (set, elm) -> set.add((long) elm),
                (s1, s2) -> { s1.addAll(s2);return s1; }
        );
    }


    // ----------------------------------------------
    //  CONSTANTS.
    // ----------------------------------------------
    //
    public static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;

    public static final int MAX_CAPACITY = MAX_ARRAY_LENGTH;

    public static final int INITIAL_CAPACITY = 10;


    // ----------------------------------------------
    //  TCAST.
    // ----------------------------------------------
    //
    public enum TCast {
        ;
        @SuppressWarnings("unchecked") public static <T> Class<T> of(Class<?> c) {
            return (Class<T>) c;
        }
        // type magma.system loophole as 'language-extension'
        public static <T> T cast(Object o) {
            return TCast.<T> of(Object.class).cast(o);
        }
    }

    // ----------------------------------------------

    // Rethrow an 'Throwable' preserving the stack trace but making it unchecked.
    public static <T extends Throwable> void rethrow(final Throwable th) throws T {
        throw TCast.<T> of(Throwable.class).cast(th);
    }

    // ----------------------------------------------

    public static DoIt TODO(final String message) { return new DoIt(message); }
    public static final class DoIt extends RuntimeException {
        public DoIt(String message) { super(Color.boldRed("\nTODO('" + message + "')")); }
    }

    // ----------------------------------------------

    public static byte[] asByteArray(final InputStream is) {
        try {
            checkNotNull(is, "illegal input stream");
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    // ----------------------------------------------

    public static <E> List<E> listOf(final Collection<? extends E> cs) {
        return new ArrayList<>(cs);
    }

    @SafeVarargs
    public static <E> List<E> listOf(final Iterable<? extends E>... cs) {
        ArrayList<E> ls = new ArrayList<>();
        for (Iterable<? extends E> c : cs)
            for (E e : c) ls.add(e);
        return ls;
    }

    @SafeVarargs
    public static <E> List<E> listOf(final E... cs) {
        final ArrayList<E> ls = new ArrayList<>(cs.length);
        for (int i = 0; i < cs.length; ++i) ls.add(cs[i]);
        return ls;
    }

    // ----------------------------------------------

    public static boolean isBoxed(Object o) {
        return (o != null) && isBoxed(o.getClass());
    }

    public static boolean isBoxed(Class<?> clazz) {
        if (clazz == null) return false;
        return clazz == Boolean.class || clazz == Character.class || clazz == Byte.class || clazz == Short.class || clazz == Integer.class || clazz == Long.class || clazz == Float.class || clazz == Double.class;
    }

    // ----------------------------------------------

    public static Class<?> arrayElementClass(Class<?> clazz) {
        checkState(clazz.isArray());
        while (clazz.isArray()) {
            clazz = clazz.getComponentType();
        }
        return clazz;
    }

    // ----------------------------------------------

    public static boolean isArray(Class<?> c) {
        return c.isArray();
    }

    public static boolean isArray(Object o) {
        return (o != null) && isArray(o.getClass());
    }

    public static boolean isObjectArray(Class<?> c) {
        return c.isArray() && !c.getComponentType().isPrimitive();
    }

    public static boolean isObjectArray(Object o) {
        return (o != null) && isObjectArray(o.getClass());
    }

    public static boolean isPrimitiveArray(Class<?> c) {
        return c.isArray() && c.getComponentType().isPrimitive();
    }

    public static boolean isPrimitiveArray(Object o) {
        return (o != null) && isPrimitiveArray(o.getClass());
    }

    // ----------------------------------------------

    public static String classNameOf(Class<?> c) {
        return (c == null) ? "null" : c.getSimpleName();
    }

    public static String classNameOf(Object o) {
        return (o == null) ? "null" : o.getClass().getSimpleName();
    }

    public static String objectNameOf(Object o) {
        return (o == null) ? "null" : (classNameOf(o) + '@' + hexIDNameOf(o));
    }

    public static String hexIDNameOf(Object o) {
        return (o == null) ? "null" : Integer.toHexString(System.identityHashCode(o));
    }

    // ----------------------------------------------

    public static <T> T newObject(Class<? extends T> clazz, Object... args) {
        args = (args == null) ? new Object[0] : args;
        Constructor<?>[] cs = clazz.getDeclaredConstructors();
        for (int i = 0; i < cs.length; ++i) {
            Constructor<?> ctor = cs[i];
            if (ctor.getParameterCount() != args.length) {
                continue;
            }
            boolean match = true;
            Class<?>[] pt = ctor.getParameterTypes();
            for (int j = 0; j < pt.length; ++j) {
                if (args[i] == null) {
                    continue;
                }
                Class<?> at = args[i].getClass();
                if (!pt[i].isAssignableFrom(at) && !(pt[i].isPrimitive() && isBoxed(at))) {
                    match = false;
                    break;
                }
            }
            if (match) {
                try {
                    return TCast.cast(ctor.newInstance(args));
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                    rethrow(ex);
                    throw new IllegalStateException();
                }
            }
        }
        throw new RuntimeException("No constructor found. " + "{ new <" + clazz.getName() + ">" + Arrays.toString(args) + " }");
    }

    // ----------------------------------------------
    //  ARRAY ALLOCATION.
    // ----------------------------------------------
    //
    public static <T> T[] newArray(Class<? extends T> elementKlass, int dim0) {
        return TCast.cast(Array.newInstance(elementKlass, dim0));
    }

    public static <T> T[] newArray(Class<? extends T> elementKlass, Collection<? extends T> elements) {
        T[] array = newArray(elementKlass, elements.size());
        int ix = 0;
        for (T e : elements) {
            array[ix++] = e;
        }
        return array;
    }

    public static <T> T[][] newArray(Class<? extends T> c, int d0, int d1) {
        return TCast.cast(Array.newInstance(c, d0, d1));
    }

    public static <T> T[][][] newArray(Class<? extends T> c, int d0, int d1, int d2) {
        return TCast.cast(Array.newInstance(c, d0, d1, d2));
    }
}

