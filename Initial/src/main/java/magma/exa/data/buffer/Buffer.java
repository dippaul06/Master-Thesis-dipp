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

package magma.exa.data.buffer;

import magma.exa.adt.mixin.Mixin;
import magma.exa.base.Bit;
import magma.exa.base.Force;
import magma.exa.base.Narrow;
import magma.exa.control.exception.Exceptions;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.control.traversal.Traversable;
import magma.exa.control.traversal.Traverser;
import magma.exa.data.Array;
import magma.exa.data.adt.operation.Operation;
import magma.exa.data.index.Ix1D;
import magma.exa.value.adt.product.Product2;
import magma.exa.value.scalar.Bool;
import magma.exa.value.scalar.C16;
import magma.exa.value.scalar.F32;
import magma.exa.value.scalar.F64;
import magma.exa.value.scalar.I16;
import magma.exa.value.scalar.I32;
import magma.exa.value.scalar.I64;
import magma.exa.value.scalar.I8;

/**
 * Base class for a data structure to collect values in a buffer 
 * and then iterate those values. Manages a table (array) of arrays 
 * of increasing size so that there are no copying costs associated 
 * with growing the data structure. 
 * <p> 
 * One or more arrays are used to store values. The use of a multiple
 * arrays has better performance characteristics than a single array
 * used by {@link java.util.ArrayList}, as when the capacity of the
 * list needs to be increased no copying of values is required.
 * This is usually beneficial in the case where the results will be
 * traversed a small number of times.
 * <p>
 * We optimistically hope that all the data will fit into the first
 * chunk, so we try to avoid inflating the table and count[] arrays
 * prematurely. So methods must be prepared to deal with these arrays
 * being null. If table[] is non-null, then spineIndex points to the 
 * current chunk within the table[], otherwise it is zero. The table[]
 * and count[] arrays are always the same size, and for any i <= tix,
 * count[i] is the sum of the sizes of all the prior chunks.
 * <p>
 * The chunk[] pointer is always valid.  The elementIndex is the index
 * of the next value to be written in chunk[]; this may be past the 
 * end of chunk[] so we have to check before writing. When we inflate 
 * the table[] array, chunk[] becomes the first value in it. When we
 * clear the buffer, we discard all chunks except the first one, which
 * we clear, restoring it to the initial single-chunk state.
 *
 * @param <T>     type of value.
 * @param <T_ARY> type of array.
 * @param <SELF>  F-bound parameter.
 */
abstract class Buffer<T, T_ARY, SELF extends Buffer<T, T_ARY, SELF>>
        
        implements Mixin.Capacity, Mixin.Count, Mixin.IsEmpty,
        
        Operation.Access.At<T, SELF>,
        
        Operation.Plus.Bulk.Insert<T, SELF>,

        Operation.Minus.Clear<T, SELF>,
        
        Operation.Convert.ToArray<T, SELF>,
        
        Operation.Convert.ToCollection<T, SELF>
{
    // ----------------------------------------------------------

    /// CONSTANTS.

    private static final int MIN_CHUNK_POWER = 4;
    private static final int MAX_CHUNK_POWER = 30;
    private static final int MIN_CHUNK_COUNT = 1 << MIN_CHUNK_POWER;
    private static final int MIN_TABLE_COUNT = 8;
    
    // ----------------------------------------------------------
    //  BUFFER.ALLOCATOR
    // ----------------------------------------------------------

    /**
     * Allocator encapsulates
     *
     * @param <T_ARY> type of array.
     */
    interface Allocator<T_ARY> extends Product2<I32.To<T_ARY>, I32.To<T_ARY[]>> {
        default I32.To<T_ARY>    _1() { return this::chunk; }
        default I32.To<T_ARY[]>  _2() { return this::table; }

        T_ARY   chunk(int length);
        T_ARY[] table(int length);
    }
    
    // ----------------------------------------------------------
    //  BUFFER.CHUNK
    // ----------------------------------------------------------
    
    /**
     * A chunk buffer specialized for reference values of type {@code A}.
     * 
     * @param <A> type of value.
     */
    public static final class Chunk<A> extends Buffer<A, A[], Chunk<A>>

            implements Traversable<A>, 
            
            Operation.Access.At.Of<A, Chunk<A>>,

            Operation.Plus.Bulk.Insert.Of<A, Chunk<A>>,

            Operation.Minus.Clear.Of<A, Chunk<A>>,

            Operation.Convert.ToCollection.Of<A, Chunk<A>>,

            Operation.Convert.ToArray.Of<A, Chunk<A>>
    {
        /**
         * Constructs a chunk buffer with an initial capacity of 8.
         * 
         * @param <A> type of value.
         * @return chunked buffer of type {@code A}.
         */
        public static <A> Chunk<A> make() {
            return Chunk.make(Array.INITIAL_CAPACITY);
        }
        
        /**
         * Constructs a chunk buffer with the given minimum initial capacity.
         * 
         * @param initialCapacity required.
         * @param <A> type of value.
         * @return chunked buffer of type {@code A}.
         */
        public static <A> Chunk<A> make(final int initialCapacity) {
            return new Chunk<>(initialCapacity);
        }

        // ----------------------------------------------------------
        
        /**
         * Allocator specialized for {@code Object[]} array.
         */
        private enum Allocator implements Buffer.Allocator<Object[]> {
            instance {
                public Object[]   chunk(final int len) { return new Object[len];   }
                public Object[][] table(final int len) { return new Object[len][]; }
            }
        }

        // ----------------------------------------------------------

        /**
         * Constructs a chunk with the given initial capacity.
         */
        private Chunk(final int initialCapacity) {
            super(Force.cast(Allocator.instance), initialCapacity);
        }

        // ----------------------------------------------------------

        /**
         * Returns the {@code boolean} value at the given index.
         *
         * @param idx whose corresponding value is to be returned.
         * @return {@code boolean} value.
         */
        @Override
        public A at(final long idx) {
            final var tix = this.tix;
            if (tix == 0) {
                if (idx < vix) {
                    return chunk[((int) idx)];
                } else {
                    throw Exceptions.outOfBounds(idx);
                }
            }
            if (idx >= count()) {
                throw Exceptions.outOfBounds(idx);
            }
            for (int j = 0; j <= tix; ++j) {
                if (idx < counts[j] + table[j].length) {
                    return table[j][((int) (idx - counts[j]))];
                }
            }
            throw Exceptions.outOfBounds(idx);
        }

        /**
         * Inserts the given {@code boolean} value.
         *
         * @param val value to be inserted.
         * @return instance of unified type.
         */
        @Override
        public Chunk<A> insert(final A val) {
            if (vix == chunk.length) {
                inflateTable();
                if (tix + 1 >= table.length || table[tix + 1] == null) {
                    grow();
                }
                vix = 0;
                ++tix;
                chunk = table[tix];
            }
            chunk[vix++] = val;
            return this;
        }

        /**
         * Inserts the {@code boolean} values contained in the given array.
         *
         * @param ary array of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        @SafeVarargs
        public final Chunk<A> insert(final A... ary) {
            final int length;
            if ((length = ary.length) == 0) {
                return this;
            }
            var chunk = this.chunk;
            for (int i = 0; i < length; ++i) {
                if (vix == chunk.length) {
                    inflateTable();
                    if (tix + 1 >= table.length || table[tix + 1] == null) {
                        grow();
                    }
                    vix = 0;
                    ++tix;
                    chunk = this.chunk = table[tix];
                }
                chunk[vix++] = ary[i];
            }
            return this;
        }

        /**
         * Inserts the {@code boolean} values contained in the given iterable.
         *
         * @param itr iterable of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public Chunk<A> insert(final Iterable<? extends A> itr) {
            if (itr instanceof Bool.Traverser)
                appendTraverser(Force.cast(itr));
            else
                appendIterator(itr.iterator());
            return this;
        }

        // ----------------------------------------------------------

        /**
         * Removes all values by releasing acquired resources.
         *
         * @return 'this' for flow coding.
         */
        @Override
        public Chunk<A> clear() {
            if (table != null) {
                java.util.Arrays.fill(chunk = table[0], false);
                table  = null;
                counts = null;
            }
            vix = 0;
            tix = 0;
            return this;
        }

        // ----------------------------------------------------------

        /**
         * Performs the given action on each value stored in this buffer.
         */
        public void forNext(final Fn1.Consumer<? super A> action) {
            try {
                A[] chunk;
                { // saturated chunks, if any...
                    final A[][]  table = this.table;
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int i = 0; i < length; ++i) {
                            action.onAccept(chunk[i]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (int i = 0; i < vix; ++i) {
                        action.onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }
        
        /**
         * Performs the given action on each indexed value stored in this buffer.
         */
        public void forNextIndexed(final Ix1D<Fn1.Consumer<? super A>> action) {
            try {
                int i = 0;
                var chunk = table[0];
                { // saturated chunks, if any...
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int k = 0; k < length; ++k) {
                            action.index(i++).onAccept(chunk[k]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (; i < vix; ++i) {
                        action.index(i).onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }
        
        // ----------------------------------------------------------

        /**
         * Returns the contents of this buffer as {@code A[]} array.
         */
        @Override
        public A[] toArray() {
            return toArray(Force.cast(new Object[Narrow.I32(count())]), 0);
        }

        @Override
        public <R> R[] toArray(final I32.To<R[]> factory) {
            return toArray(factory.apply(Narrow.I32(count())));
        }

        @Override
        public <R> R[] toArray(final R[] target) {
            return Force.cast(toArray(Force.cast(target), 0));
        }

        /**
         * Returns the contents of this buffer as {@code A[]} array.
         *
         * @param dst target array to store buffered values.
         * @param dstIndex index to start in the destination buffer.
         * @return values stored in the given {@code A[]} array.
         */
        public A[] toArray(final A[] dst, int dstIndex) {
            final var dstOffset = dstIndex + count();
            if (dstOffset > dst.length || dstOffset < dstIndex) {
                throw Exceptions.outOfBounds("illegal target array");
            }
            if (tix == 0) {
                System.arraycopy(chunk, 0, dst, dstIndex, vix);
            } else { // Copy full chunks.
                for (int i = 0; i < tix; ++i) {
                    final var length = Array.length(table[i]);
                    System.arraycopy(table[i], 0, dst, dstIndex, length);
                    dstIndex += length;
                }
                if (vix > 0) {
                    System.arraycopy(chunk, 0, dst, dstIndex, vix);
                }
            }
            return dst;
        }

        // ----------------------------------------------------------

        /**
         * Returns a bidirectional indexed traverser over the buffer contents.
         */
        @Override
        public Traverser.Duplex.Indexed<A> traverser() {
            // Denotes the beginning of the critical section
            // for parallel modifications (not concurrent!).
            final class ChunkTraverser implements Traverser.Duplex.Indexed<A> {
                private final int lo = 0;
                private final int hi = Narrow.I32(count());
                private final int p0 = 0;
                private int px = p0;

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNext(final Fn1.Consumer<? super A> action) {
                    if (px >= hi) return false;
                    action.accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNextIndexed(final Ix1D<Fn1.Consumer<? super A>> action) {
                    if (px >= hi) return false;
                    action.index(px).accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrev(final Fn1.Consumer<? super A> action) {
                    if (px <= lo) return false;
                    action.accept(at(--px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrevIndexed(final Ix1D<Fn1.Consumer<? super A>> action) {
                    if (px <= lo) return false;
                    action.index(--px).accept(at(px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNext(final Fn1.Consumer<? super A> action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) action.onAccept(at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNextIndexed(final Ix1D<Fn1.Consumer<? super A>> action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) invoke(action, px, at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrev(final Fn1.Consumer<? super A> action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) action.onAccept(at(--px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrevIndexed(final Ix1D<Fn1.Consumer<? super A>> action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) invoke(action, --px, at(px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNext(final Until<Control, Fn1.Consumer<? super A>> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            action.onAccept(at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNextIndexed(final Until<Control, Ix1D<Fn1.Consumer<? super A>>> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            invoke(action, px, at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrev(final Until<Control, Fn1.Consumer<? super A>> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            action.onAccept(at(--px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrevIndexed(final Until<Control, Ix1D<Fn1.Consumer<? super A>>> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            invoke(action, --px, at(px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                private static <A> void invoke(final Ix1D<Fn1.Consumer<? super A>> action, final int px, final A value) throws Throwable {
                    action.index(px).onAccept(value);
                }
            }
            return new ChunkTraverser();
        }

        // ----------------------------------------------------------

        /**
         * Appends the content provided by the given traverser.
         */
        private void appendTraverser(final Traverser<A> traverser) {
            final class AppendTraverser implements Fn1.Consumer<A> {
                private A[] chunk = Chunk.this.chunk;

                private AppendTraverser() { traverser.forNext(this); }

                @Override
                public void onAccept(final A val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendTraverser();
        }

        /**
         * Appends the content provided by the given iterator.
         */
        private void appendIterator(final java.util.Iterator<? extends A> it) {
            final class AppendIterator implements java.util.function.Consumer<A> {
                private A[] chunk = Chunk.this.chunk;
                AppendIterator() { it.forEachRemaining(this); }

                @Override
                public void accept(final A val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendIterator();
        }
    }

    // ----------------------------------------------------------
    //  BUFFER.BOOL-CHUNK
    // ----------------------------------------------------------

    /**
     * A chunk buffer specialized for {@code boolean} values.
     */
    public static final class BoolChunk extends Buffer<Boolean, boolean[], BoolChunk>

            implements Operation.Access.At.OfBool<BoolChunk>,

            Operation.Plus.Bulk.Insert.OfBool<BoolChunk>,

            Operation.Minus.Clear.OfBool<BoolChunk>,

            Operation.Convert.ToCollection.OfBool<BoolChunk>,

            Operation.Convert.ToArray.OfBool<BoolChunk>,
            
            Bool.Traversable
    {
        /**
         * Constructs a chunk buffer with an initial capacity of 8.
         *
         * @return chunked {@code boolean} buffer.
         */
        public static BoolChunk make() {
            return BoolChunk.make(Array.INITIAL_CAPACITY);
        }

        /**
         * Constructs a chunk buffer with the given minimum initial capacity.
         *
         * @param initialCapacity required.
         * @return chunked {@code boolean} buffer.
         */
        public static BoolChunk make(final int initialCapacity) {
            return new BoolChunk(initialCapacity);
        }

        // ----------------------------------------------------------
        
        /**
         * Allocator specialized for {@code boolean[]} array.
         */
        private enum Allocator implements Buffer.Allocator<boolean[]> {
            instance {
                public boolean[]   chunk(final int len) { return new boolean[len];   }
                public boolean[][] table(final int len) { return new boolean[len][]; }
            }
        }

        // ----------------------------------------------------------

        /**
         * Constructs a chunk with the given initial capacity.
         */
        private BoolChunk(final int initialCapacity) {
            super(Allocator.instance, initialCapacity);
        }

        // ----------------------------------------------------------

        /**
         * Returns the {@code boolean} value at the given index.
         *
         * @param idx whose corresponding value is to be returned.
         * @return {@code boolean} value.
         */
        @Override
        public boolean at(final long idx) {
            final var tix = this.tix;
            if (tix == 0) {
                if (idx < vix) {
                    return chunk[((int) idx)];
                } else {
                    throw Exceptions.outOfBounds(idx);
                }
            }
            if (idx >= count()) {
                throw Exceptions.outOfBounds(idx);
            }
            for (int j = 0; j <= tix; ++j) {
                if (idx < counts[j] + table[j].length) {
                    return table[j][((int) (idx - counts[j]))];
                }
            }
            throw Exceptions.outOfBounds(idx);
        }

        /**
         * Inserts the given {@code boolean} value.
         *
         * @param val value to be inserted.
         * @return instance of unified type.
         */
        @Override
        public BoolChunk insert(final boolean val) {
            if (vix == chunk.length) {
                inflateTable();
                if (tix + 1 >= table.length || table[tix + 1] == null) {
                    grow();
                }
                vix = 0;
                ++tix;
                chunk = table[tix];
            }
            chunk[vix++] = val;
            return this;
        }

        /**
         * Inserts the {@code boolean} values contained in the given array.
         *
         * @param ary array of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public BoolChunk insert(final boolean... ary) {
            final int length;
            if ((length = ary.length) == 0) {
                return this;
            }
            var chunk = this.chunk;
            for (int i = 0; i < length; ++i) {
                if (vix == chunk.length) {
                    inflateTable();
                    if (tix + 1 >= table.length || table[tix + 1] == null) {
                        grow();
                    }
                    vix = 0;
                    ++tix;
                    chunk = this.chunk = table[tix];
                }
                chunk[vix++] = ary[i];
            }
            return this;
        }

        /**
         * Inserts the {@code boolean} values contained in the given iterable.
         *
         * @param itr iterable of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public BoolChunk insert(final Iterable<Boolean> itr) {
            if (itr instanceof Bool.Traverser)
                appendTraverser((Bool.Traverser) itr);
            else
                appendIterator(itr.iterator());
            return this;
        }

        /**
         * Removes all values by releasing acquired resources.
         *
         * @return 'this' for flow coding.
         */
        @Override
        public BoolChunk clear() {
            if (table != null) {
                java.util.Arrays.fill(chunk = table[0], false);
                table  = null;
                counts = null;
            }
            vix = 0;
            tix = 0;
            return this;
        }
        
        // ----------------------------------------------------------

        /**
         * Performs the given action on each value stored in this buffer.
         */
        public void forNext(final Bool.Consumer action) {
            try {
                boolean[] chunk;
                { // saturated chunks, if any...
                    final boolean[][]  table = this.table;
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int i = 0; i < length; ++i) {
                            action.onAccept(chunk[i]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (int i = 0; i < vix; ++i) {
                        action.onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * Performs the given action on each indexed value stored in this buffer.
         */
        public void forNextIndexed(final Ix1D<Bool.Consumer> action) {
            try {
                int i = 0;
                var chunk = table[0];
                { // saturated chunks, if any...
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int k = 0; k < length; ++k) {
                            action.index(i++).onAccept(chunk[k]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (; i < vix; ++i) {
                        action.index(i).onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        // ----------------------------------------------------------

        /**
         * Returns the contents of this buffer as {@code boolean[]} array.
         */
        @Override
        public boolean[] toArray() {
            return toArray(new boolean[Narrow.I32(count())], 0);
        }

        /**
         * Returns the contents of this buffer as {@code double[]} array.
         *
         * @param dst target array to store buffered values.
         * @param dstIndex index to start in the destination buffer.
         * @return values stored in the given {@code double[]} array.
         */
        public boolean[] toArray(final boolean[] dst, int dstIndex) {
            final var dstOffset = dstIndex + count();
            if (dstOffset > dst.length || dstOffset < dstIndex) {
                throw Exceptions.outOfBounds("illegal target array");
            }
            if (tix == 0) {
                System.arraycopy(chunk, 0, dst, dstIndex, vix);
            } else { // Copy full chunks.
                for (int i = 0; i < tix; ++i) {
                    final var length = Array.length(table[i]);
                    System.arraycopy(table[i], 0, dst, dstIndex, length);
                    dstIndex += length;
                }
                if (vix > 0) {
                    System.arraycopy(chunk, 0, dst, dstIndex, vix);
                }
            }
            return dst;
        }

        // ----------------------------------------------------------

        /**
         * Returns a bidirectional indexed traverser over the buffer contents.
         */
        @Override
        public Bool.Traverser.Duplex.Indexed traverser() {
            // Denotes the beginning of the critical section
            // for parallel modifications (not concurrent!).
            final class ChunkTraverser implements Bool.Traverser.Duplex.Indexed {
                private final int lo = 0;
                private final int hi = Narrow.I32(count());
                private final int p0 = 0;
                private int px = p0;

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNext(final Bool.Consumer action) {
                    if (px >= hi) return false;
                    action.accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNextIndexed(final Ix1D<Bool.Consumer> action) {
                    if (px >= hi) return false;
                    action.index(px).accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrev(final Bool.Consumer action) {
                    if (px <= lo) return false;
                    action.accept(at(--px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrevIndexed(final Ix1D<Bool.Consumer> action) {
                    if (px <= lo) return false;
                    action.index(--px).accept(at(px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNext(final Bool.Consumer action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) action.onAccept(at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNextIndexed(final Ix1D<Bool.Consumer> action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) invoke(action, px, at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrev(final Bool.Consumer action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) action.onAccept(at(--px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrevIndexed(final Ix1D<Bool.Consumer> action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) invoke(action, --px, at(px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNext(final Until<Control, Bool.Consumer> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            action.onAccept(at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNextIndexed(final Until<Control, Ix1D<Bool.Consumer>> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            invoke(action, px, at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrev(final Until<Control, Bool.Consumer> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            action.onAccept(at(--px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrevIndexed(final Until<Control, Ix1D<Bool.Consumer>> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            invoke(action, --px, at(px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                private static void invoke(final Ix1D<Bool.Consumer> action, final int px, final boolean value) throws Throwable {
                    action.index(px).onAccept(value);
                }
            }
            return new ChunkTraverser();
        }

        // ----------------------------------------------------------

        /**
         * Appends the content provided by the given traverser.
         */
        private void appendTraverser(final Bool.Traverser traverser) {
            final class AppendTraverser implements Bool.Consumer {
                private boolean[] chunk = BoolChunk.this.chunk;

                private AppendTraverser() { traverser.forNext(this); }

                @Override
                public void onAccept(final boolean val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendTraverser();
        }

        /**
         * Appends the content provided by the given iterator.
         */
        private void appendIterator(final java.util.Iterator<Boolean> it) {
            final class AppendIterator implements java.util.function.Consumer<Boolean> {
                private boolean[] chunk = BoolChunk.this.chunk;
                AppendIterator() { it.forEachRemaining(this); }

                @Override
                public void accept(final Boolean val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendIterator();
        }
    }

    // ----------------------------------------------------------
    //  BUFFER.I8-CHUNK
    // ----------------------------------------------------------

    /**
     * A chunk buffer specialized for {@code byte} values.
     */
    public static final class I8Chunk extends Buffer<Byte, byte[], I8Chunk>

            implements Operation.Access.At.OfI8<I8Chunk>,

            Operation.Plus.Bulk.Insert.OfI8<I8Chunk>,

            Operation.Minus.Clear.OfI8<I8Chunk>,

            Operation.Convert.ToCollection.OfI8<I8Chunk>,

            Operation.Convert.ToArray.OfI8<I8Chunk>,
            
            I8.Traversable
    {
        /**
         * Constructs a chunk buffer with an initial capacity of 8.
         *
         * @return chunked {@code byte} buffer.
         */
        public static I8Chunk make() {
            return I8Chunk.make(Array.INITIAL_CAPACITY);
        }

        /**
         * Constructs a chunk buffer with the given minimum initial capacity.
         *
         * @param initialCapacity required.
         * @return chunked {@code byte} buffer.
         */
        public static I8Chunk make(final int initialCapacity) {
            return new I8Chunk(initialCapacity);
        }
        
        // ----------------------------------------------------------

        /**
         * Allocator specialized for {@code byte[]} array.
         */
        private enum Allocator implements Buffer.Allocator<byte[]> {
            instance {
                public byte[]   chunk(final int len) { return new byte[len];   }
                public byte[][] table(final int len) { return new byte[len][]; }
            }
        }

        /**
         * Constructs a chunk with the given initial capacity.
         */
        private I8Chunk(final int initialCapacity) {
            super(Allocator.instance, initialCapacity);
        }

        /**
         * Returns the {@code byte} value at the given index.
         *
         * @param idx whose corresponding value is to be returned.
         * @return {@code byte} value.
         */
        @Override
        public byte at(final long idx) {
            final var tix = this.tix;
            if (tix == 0) {
                if (idx < vix) {
                    return chunk[((int) idx)];
                } else {
                    throw Exceptions.outOfBounds(idx);
                }
            }
            if (idx >= count()) {
                throw Exceptions.outOfBounds(idx);
            }
            for (int j = 0; j <= tix; ++j) {
                if (idx < counts[j] + table[j].length) {
                    return table[j][((int) (idx - counts[j]))];
                }
            }
            throw Exceptions.outOfBounds(idx);
        }

        /**
         * Inserts the given {@code byte} value.
         *
         * @param val value to be inserted.
         * @return instance of unified type.
         */
        @Override
        public I8Chunk insert(final byte val) {
            if (vix == chunk.length) {
                inflateTable();
                if (tix + 1 >= table.length || table[tix + 1] == null) {
                    grow();
                }
                vix = 0;
                ++tix;
                chunk = table[tix];
            }
            chunk[vix++] = val;
            return this;
        }

        /**
         * Inserts the {@code byte} values contained in the given array.
         *
         * @param ary array of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public I8Chunk insert(final byte... ary) {
            final int length;
            if ((length = ary.length) == 0) {
                return this;
            }
            var chunk = this.chunk;
            for (int i = 0; i < length; ++i) {
                if (vix == chunk.length) {
                    inflateTable();
                    if (tix + 1 >= table.length || table[tix + 1] == null) {
                        grow();
                    }
                    vix = 0;
                    ++tix;
                    chunk = this.chunk = table[tix];
                }
                chunk[vix++] = ary[i];
            }
            return this;
        }

        /**
         * Inserts the {@code byte} values contained in the given iterable.
         *
         * @param itr iterable of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public I8Chunk insert(final Iterable<Byte> itr) {
            if (itr instanceof I8.Traverser)
                appendTraverser((I8.Traverser) itr);
            else
                appendIterator(itr.iterator());
            return this;
        }

        /**
         * Removes all values by releasing acquired resources.
         *
         * @return 'this' for flow coding.
         */
        @Override
        public I8Chunk clear() {
            if (table != null) {
                java.util.Arrays.fill(chunk = table[0], (byte) 0);
                table  = null;
                counts = null;
            }
            vix = 0;
            tix = 0;
            return this;
        }

        // ----------------------------------------------------------

        /**
         * Performs the given action on each value stored in this buffer.
         */
        public void forNext(final I8.Consumer action) {
            try {
                byte[] chunk;
                { // saturated chunks, if any...
                    final byte[][]  table = this.table;
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int i = 0; i < length; ++i) {
                            action.onAccept(chunk[i]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (int i = 0; i < vix; ++i) {
                        action.onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * Performs the given action on each indexed value stored in this buffer.
         */
        public void forNextIndexed(final Ix1D<I8.Consumer> action) {
            try {
                int i = 0;
                var chunk = table[0];
                { // saturated chunks, if any...
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int k = 0; k < length; ++k) {
                            action.index(i++).onAccept(chunk[k]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (; i < vix; ++i) {
                        action.index(i).onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        // ----------------------------------------------------------

        /**
         * Returns the contents of this buffer as {@code byte[]} array.
         */
        @Override
        public byte[] toArray() {
            return toArray(new byte[Narrow.I32(count())], 0);
        }

        /**
         * Returns the contents of this buffer as {@code double[]} array.
         *
         * @param dst target array to store buffered values.
         * @param dstIndex index to start in the destination buffer.
         * @return values stored in the given {@code double[]} array.
         */
        public byte[] toArray(final byte[] dst, int dstIndex) {
            final var dstOffset = dstIndex + count();
            if (dstOffset > dst.length || dstOffset < dstIndex) {
                throw Exceptions.outOfBounds("illegal target array");
            }
            if (tix == 0) {
                System.arraycopy(chunk, 0, dst, dstIndex, vix);
            } else { // Copy full chunks.
                for (int i = 0; i < tix; ++i) {
                    final var length = Array.length(table[i]);
                    System.arraycopy(table[i], 0, dst, dstIndex, length);
                    dstIndex += length;
                }
                if (vix > 0) {
                    System.arraycopy(chunk, 0, dst, dstIndex, vix);
                }
            }
            return dst;
        }

        // ----------------------------------------------------------

        /**
         * Returns a bidirectional indexed traverser over the buffer contents.
         */
        @Override
        public I8.Traverser.Duplex.Indexed traverser() {
            // Denotes the beginning of the critical section
            // for parallel modifications (not concurrent!).
            final class ChunkTraverser implements I8.Traverser.Duplex.Indexed {
                private final int lo = 0;
                private final int hi = Narrow.I32(count());
                private final int p0 = 0;
                private int px = p0;

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNext(final I8.Consumer action) {
                    if (px >= hi) return false;
                    action.accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNextIndexed(final Ix1D<I8.Consumer> action) {
                    if (px >= hi) return false;
                    action.index(px).accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrev(final I8.Consumer action) {
                    if (px <= lo) return false;
                    action.accept(at(--px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrevIndexed(final Ix1D<I8.Consumer> action) {
                    if (px <= lo) return false;
                    action.index(--px).accept(at(px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNext(final I8.Consumer action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) action.onAccept(at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNextIndexed(final Ix1D<I8.Consumer> action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) invoke(action, px, at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrev(final I8.Consumer action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) action.onAccept(at(--px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrevIndexed(final Ix1D<I8.Consumer> action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) invoke(action, --px, at(px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNext(final Until<Control, I8.Consumer> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            action.onAccept(at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNextIndexed(final Until<Control, Ix1D<I8.Consumer>> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            invoke(action, px, at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrev(final Until<Control, I8.Consumer> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            action.onAccept(at(--px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrevIndexed(final Until<Control, Ix1D<I8.Consumer>> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            invoke(action, --px, at(px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                private static <A> void invoke(final Ix1D<I8.Consumer> action, final int px, final byte value) throws Throwable {
                    action.index(px).onAccept(value);
                }
            }
            return new ChunkTraverser();
        }

        // ----------------------------------------------------------

        /**
         * Appends the content provided by the given traverser.
         */
        private void appendTraverser(final I8.Traverser traverser) {
            final class AppendTraverser implements I8.Consumer {
                private byte[] chunk = I8Chunk.this.chunk;

                private AppendTraverser() { traverser.forNext(this); }

                @Override
                public void onAccept(final byte val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendTraverser();
        }

        /**
         * Appends the content provided by the given iterator.
         */
        private void appendIterator(final java.util.Iterator<Byte> it) {
            final class AppendIterator implements java.util.function.Consumer<Byte> {
                private byte[] chunk = I8Chunk.this.chunk;
                AppendIterator() { it.forEachRemaining(this); }

                @Override
                public void accept(final Byte val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendIterator();
        }
    }
    
    // ----------------------------------------------------------
    //  BUFFER.I16-CHUNK
    // ----------------------------------------------------------

    /**
     * A chunk buffer specialized for {@code short} values.
     */
    public static final class I16Chunk extends Buffer<Short, short[], I16Chunk>

            implements Operation.Access.At.OfI16<I16Chunk>,

            Operation.Plus.Bulk.Insert.OfI16<I16Chunk>,

            Operation.Minus.Clear.OfI16<I16Chunk>,

            Operation.Convert.ToCollection.OfI16<I16Chunk>,

            Operation.Convert.ToArray.OfI16<I16Chunk>,
    
            I16.Traversable
    {
        /**
         * Constructs a chunk buffer with an initial capacity of 8.
         *
         * @return chunked {@code short} buffer.
         */
        public static I16Chunk make() {
            return I16Chunk.make(Array.INITIAL_CAPACITY);
        }

        /**
         * Constructs a chunk buffer with the given minimum initial capacity.
         *
         * @param initialCapacity required.
         * @return chunked {@code short} buffer.
         */
        public static I16Chunk make(final int initialCapacity) {
            return new I16Chunk(initialCapacity);
        }

        // ----------------------------------------------------------

        /**
         * Allocator specialized for {@code short[]} array.
         */
        private enum Allocator implements Buffer.Allocator<short[]> {
            instance {
                public short[]   chunk(final int len) { return new short[len];   }
                public short[][] table(final int len) { return new short[len][]; }
            }
        }

        /**
         * Constructs a chunk with the given initial capacity.
         */
        private I16Chunk(final int initialCapacity) {
            super(Allocator.instance, initialCapacity);
        }

        /**
         * Returns the {@code short} value at the given index.
         *
         * @param idx whose corresponding value is to be returned.
         * @return {@code short} value.
         */
        @Override
        public short at(final long idx) {
            final var tix = this.tix;
            if (tix == 0) {
                if (idx < vix) {
                    return chunk[((int) idx)];
                } else {
                    throw Exceptions.outOfBounds(idx);
                }
            }
            if (idx >= count()) {
                throw Exceptions.outOfBounds(idx);
            }
            for (int j = 0; j <= tix; ++j) {
                if (idx < counts[j] + table[j].length) {
                    return table[j][((int) (idx - counts[j]))];
                }
            }
            throw Exceptions.outOfBounds(idx);
        }

        /**
         * Inserts the given {@code short} value.
         *
         * @param val value to be inserted.
         * @return instance of unified type.
         */
        @Override
        public I16Chunk insert(final short val) {
            if (vix == chunk.length) {
                inflateTable();
                if (tix + 1 >= table.length || table[tix + 1] == null) {
                    grow();
                }
                vix = 0;
                ++tix;
                chunk = table[tix];
            }
            chunk[vix++] = val;
            return this;
        }

        /**
         * Inserts the {@code short} values contained in the given array.
         *
         * @param ary array of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public I16Chunk insert(final short... ary) {
            final int length;
            if ((length = ary.length) == 0) {
                return this;
            }
            var chunk = this.chunk;
            for (int i = 0; i < length; ++i) {
                if (vix == chunk.length) {
                    inflateTable();
                    if (tix + 1 >= table.length || table[tix + 1] == null) {
                        grow();
                    }
                    vix = 0;
                    ++tix;
                    chunk = this.chunk = table[tix];
                }
                chunk[vix++] = ary[i];
            }
            return this;
        }

        /**
         * Inserts the {@code short} values contained in the given iterable.
         *
         * @param itr iterable of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public I16Chunk insert(final Iterable<Short> itr) {
            if (itr instanceof I16.Traverser)
                appendTraverser((I16.Traverser) itr);
            else
                appendIterator(itr.iterator());
            return this;
        }

        /**
         * Removes all values by releasing acquired resources.
         *
         * @return 'this' for flow coding.
         */
        @Override
        public I16Chunk clear() {
            if (table != null) {
                java.util.Arrays.fill(chunk = table[0], (short) 0);
                table  = null;
                counts = null;
            }
            vix = 0;
            tix = 0;
            return this;
        }

        // ----------------------------------------------------------

        /**
         * Performs the given action on each value stored in this buffer.
         */
        public void forNext(final I16.Consumer action) {
            try {
                short[] chunk;
                { // saturated chunks, if any...
                    final var  table = this.table;
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int i = 0; i < length; ++i) {
                            action.onAccept(chunk[i]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (int i = 0; i < vix; ++i) {
                        action.onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * Performs the given action on each indexed value stored in this buffer.
         */
        public void forNextIndexed(final Ix1D<I16.Consumer> action) {
            try {
                int i = 0;
                var chunk = table[0];
                { // saturated chunks, if any...
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int k = 0; k < length; ++k) {
                            action.index(i++).onAccept(chunk[k]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (; i < vix; ++i) {
                        action.index(i).onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        // ----------------------------------------------------------

        /**
         * Returns the contents of this buffer as {@code short[]} array.
         */
        @Override
        public short[] toArray() {
            return toArray(new short[Narrow.I32(count())], 0);
        }

        /**
         * Returns the contents of this buffer as {@code double[]} array.
         *
         * @param dst target array to store buffered values.
         * @param dstIndex index to start in the destination buffer.
         * @return values stored in the given {@code double[]} array.
         */
        public short[] toArray(final short[] dst, int dstIndex) {
            final var dstOffset = dstIndex + count();
            if (dstOffset > dst.length || dstOffset < dstIndex) {
                throw Exceptions.outOfBounds("illegal target array");
            }
            if (tix == 0) {
                System.arraycopy(chunk, 0, dst, dstIndex, vix);
            } else { // Copy full chunks.
                for (int i = 0; i < tix; ++i) {
                    final var length = Array.length(table[i]);
                    System.arraycopy(table[i], 0, dst, dstIndex, length);
                    dstIndex += length;
                }
                if (vix > 0) {
                    System.arraycopy(chunk, 0, dst, dstIndex, vix);
                }
            }
            return dst;
        }

        // ----------------------------------------------------------
        
        @Override
        public I16.Traverser.Duplex.Indexed traverser() {
            // Denotes the beginning of the critical section
            // for parallel modifications (not concurrent!).
            final class ChunkTraverser implements I16.Traverser.Duplex.Indexed {
                private final int lo = 0;
                private final int hi = Narrow.I32(count());
                private final int p0 = 0;
                private int px = p0;

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNext(final I16.Consumer action) {
                    if (px >= hi) return false;
                    action.accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNextIndexed(final Ix1D<I16.Consumer> action) {
                    if (px >= hi) return false;
                    action.index(px).accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrev(final I16.Consumer action) {
                    if (px <= lo) return false;
                    action.accept(at(--px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrevIndexed(final Ix1D<I16.Consumer> action) {
                    if (px <= lo) return false;
                    action.index(--px).accept(at(px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNext(final I16.Consumer action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) action.onAccept(at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNextIndexed(final Ix1D<I16.Consumer> action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) invoke(action, px, at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrev(final I16.Consumer action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) action.onAccept(at(--px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrevIndexed(final Ix1D<I16.Consumer> action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) invoke(action, --px, at(px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNext(final Until<Control, I16.Consumer> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            action.onAccept(at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNextIndexed(final Until<Control, Ix1D<I16.Consumer>> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            invoke(action, px, at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrev(final Until<Control, I16.Consumer> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            action.onAccept(at(--px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrevIndexed(final Until<Control, Ix1D<I16.Consumer>> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            invoke(action, --px, at(px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                private static <A>
                void invoke(final Ix1D<I16.Consumer> action, final int px, final short value) throws Throwable {
                    action.index(px).onAccept(value);
                }
            }
            return new ChunkTraverser();
        }
        
        // ----------------------------------------------------------

        /**
         * Appends the content provided by the given traverser.
         */
        private void appendTraverser(final I16.Traverser traverser) {
            final class AppendTraverser implements I16.Consumer {
                private short[] chunk = I16Chunk.this.chunk;

                private AppendTraverser() { traverser.forNext(this); }

                @Override
                public void onAccept(final short val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendTraverser();
        }

        /**
         * Appends the content provided by the given iterator.
         */
        private void appendIterator(final java.util.Iterator<Short> it) {
            final class AppendIterator implements java.util.function.Consumer<Short> {
                private short[] chunk = I16Chunk.this.chunk;
                AppendIterator() { it.forEachRemaining(this); }

                @Override
                public void accept(final Short val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendIterator();
        }
    }

    // ----------------------------------------------------------
    //  BUFFER.C16-CHUNK
    // ----------------------------------------------------------

    /**
     * A chunk buffer specialized for {@code char} values.
     */
    public static final class C16Chunk extends Buffer<Character, char[], C16Chunk>

            implements Operation.Access.At.OfC16<C16Chunk>,

            Operation.Plus.Bulk.Insert.OfC16<C16Chunk>,

            Operation.Minus.Clear.OfC16<C16Chunk>,

            Operation.Convert.ToCollection.OfC16<C16Chunk>,

            Operation.Convert.ToArray.OfC16<C16Chunk>
    {
        /**
         * Constructs a chunk buffer with an initial capacity of 8.
         *
         * @return chunked {@code char} buffer.
         */
        public static C16Chunk make() {
            return C16Chunk.make(Array.INITIAL_CAPACITY);
        }

        /**
         * Constructs a chunk buffer with the given minimum initial capacity.
         *
         * @param initialCapacity required.
         * @return chunked {@code char} buffer.
         */
        public static C16Chunk make(final int initialCapacity) {
            return new C16Chunk(initialCapacity);
        }

        // ----------------------------------------------------------

        /**
         * Allocator specialized for {@code char[]} array.
         */
        private enum Allocator implements Buffer.Allocator<char[]> {
            instance {
                public char[]   chunk(final int len) { return new char[len];   }
                public char[][] table(final int len) { return new char[len][]; }
            }
        }

        /**
         * Constructs a chunk with the given initial capacity.
         */
        private C16Chunk(final int initialCapacity) {
            super(Allocator.instance, initialCapacity);
        }

        /**
         * Returns the {@code char} value at the given index.
         *
         * @param idx whose corresponding value is to be returned.
         * @return {@code char} value.
         */
        @Override
        public char at(final long idx) {
            final var tix = this.tix;
            if (tix == 0) {
                if (idx < vix) {
                    return chunk[((int) idx)];
                } else {
                    throw Exceptions.outOfBounds(idx);
                }
            }
            if (idx >= count()) {
                throw Exceptions.outOfBounds(idx);
            }
            for (int j = 0; j <= tix; ++j) {
                if (idx < counts[j] + table[j].length) {
                    return table[j][((int) (idx - counts[j]))];
                }
            }
            throw Exceptions.outOfBounds(idx);
        }

        /**
         * Inserts the given {@code char} value.
         *
         * @param val value to be inserted.
         * @return instance of unified type.
         */
        @Override
        public C16Chunk insert(final char val) {
            if (vix == chunk.length) {
                inflateTable();
                if (tix + 1 >= table.length || table[tix + 1] == null) {
                    grow();
                }
                vix = 0;
                ++tix;
                chunk = table[tix];
            }
            chunk[vix++] = val;
            return this;
        }

        /**
         * Inserts the {@code char} values contained in the given array.
         *
         * @param ary array of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public C16Chunk insert(final char... ary) {
            final int length;
            if ((length = ary.length) == 0) {
                return this;
            }
            var chunk = this.chunk;
            for (int i = 0; i < length; ++i) {
                if (vix == chunk.length) {
                    inflateTable();
                    if (tix + 1 >= table.length || table[tix + 1] == null) {
                        grow();
                    }
                    vix = 0;
                    ++tix;
                    chunk = this.chunk = table[tix];
                }
                chunk[vix++] = ary[i];
            }
            return this;
        }

        /**
         * Inserts the {@code char} values contained in the given iterable.
         *
         * @param itr iterable of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public C16Chunk insert(final Iterable<Character> itr) {
            if (itr instanceof C16.Traverser)
                appendTraverser((C16.Traverser) itr);
            else
                appendIterator(itr.iterator());
            return this;
        }

        /**
         * Removes all values by releasing acquired resources.
         *
         * @return 'this' for flow coding.
         */
        @Override
        public C16Chunk clear() {
            if (table != null) {
                java.util.Arrays.fill(chunk = table[0], (char) 0);
                table  = null;
                counts = null;
            }
            vix = 0;
            tix = 0;
            return this;
        }

        // ----------------------------------------------------------

        /**
         * Performs the given action on each value stored in this buffer.
         */
        public void forNext(final C16.Consumer action) {
            try {
                char[] chunk;
                { // saturated chunks, if any...
                    final char[][]  table = this.table;
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int i = 0; i < length; ++i) {
                            action.onAccept(chunk[i]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (int i = 0; i < vix; ++i) {
                        action.onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * Performs the given action on each indexed value stored in this buffer.
         */
        public void forNextIndexed(final Ix1D<C16.Consumer> action) {
            try {
                int i = 0;
                var chunk = table[0];
                { // saturated chunks, if any...
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int k = 0; k < length; ++k) {
                            action.index(i++).onAccept(chunk[k]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (; i < vix; ++i) {
                        action.index(i).onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        // ----------------------------------------------------------

        /**
         * Returns the contents of this buffer as {@code char[]} array.
         */
        @Override
        public char[] toArray() {
            return toArray(new char[Narrow.I32(count())], 0);
        }

        /**
         * Returns the contents of this buffer as {@code double[]} array.
         *
         * @param dst target array to store buffered values.
         * @param dstIndex index to start in the destination buffer.
         * @return values stored in the given {@code double[]} array.
         */
        public char[] toArray(final char[] dst, int dstIndex) {
            final var dstOffset = dstIndex + count();
            if (dstOffset > dst.length || dstOffset < dstIndex) {
                throw Exceptions.outOfBounds("illegal target array");
            }
            if (tix == 0) {
                System.arraycopy(chunk, 0, dst, dstIndex, vix);
            } else { // Copy full chunks.
                for (int i = 0; i < tix; ++i) {
                    final var length = Array.length(table[i]);
                    System.arraycopy(table[i], 0, dst, dstIndex, length);
                    dstIndex += length;
                }
                if (vix > 0) {
                    System.arraycopy(chunk, 0, dst, dstIndex, vix);
                }
            }
            return dst;
        }

        // ----------------------------------------------------------

        /**
         * Appends the content provided by the given traverser.
         */
        private void appendTraverser(final C16.Traverser traverser) {
            final class AppendTraverser implements C16.Consumer {
                private char[] chunk = C16Chunk.this.chunk;

                private AppendTraverser() { traverser.forNext(this); }

                @Override
                public void onAccept(final char val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendTraverser();
        }

        /**
         * Appends the content provided by the given iterator.
         */
        private void appendIterator(final java.util.Iterator<Character> it) {
            final class AppendIterator implements java.util.function.Consumer<Character> {
                private char[] chunk = C16Chunk.this.chunk;
                AppendIterator() { it.forEachRemaining(this); }

                @Override
                public void accept(final Character val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendIterator();
        }
    }

    // ----------------------------------------------------------
    //  BUFFER.I32-CHUNK
    // ----------------------------------------------------------

    /**
     * A chunk buffer specialized for {@code int} values.
     */
    public static final class I32Chunk extends Buffer<Integer, int[], I32Chunk>

            implements Operation.Access.At.OfI32<I32Chunk>,

            Operation.Plus.Bulk.Insert.OfI32<I32Chunk>,

            Operation.Minus.Clear.OfI32<I32Chunk>,

            Operation.Convert.ToCollection.OfI32<I32Chunk>,

            Operation.Convert.ToArray.OfI32<I32Chunk>,
            
            I32.Traversable
    {
        /**
         * Constructs a chunk buffer with an initial capacity of 8.
         *
         * @return chunked {@code boolean} buffer.
         */
        public static I32Chunk make() {
            return I32Chunk.make(Array.INITIAL_CAPACITY);
        }

        /**
         * Constructs a chunk buffer with the given minimum initial capacity.
         *
         * @param initialCapacity required.
         * @return chunked {@code boolean} buffer.
         */
        public static I32Chunk make(final int initialCapacity) {
            return new I32Chunk(initialCapacity);
        }

        // ----------------------------------------------------------

        /**
         * Allocator specialized for {@code byte[]} array.
         */
        private enum Allocator implements Buffer.Allocator<int[]> {
            instance {
                public int[]   chunk(final int len) { return new int[len];   }
                public int[][] table(final int len) { return new int[len][]; }
            }
        }

        /**
         * Constructs a chunk with the given initial capacity.
         */
        private I32Chunk(final int initialCapacity) {
            super(Allocator.instance, initialCapacity);
        }

        /**
         * Returns the {@code int} value at the given index.
         *
         * @param idx whose corresponding value is to be returned.
         * @return {@code int} value.
         */
        @Override
        public int at(final long idx) {
            final var tix = this.tix;
            if (tix == 0) {
                if (idx < vix) {
                    return chunk[((int) idx)];
                } else {
                    throw Exceptions.outOfBounds(idx);
                }
            }
            if (idx >= count()) {
                throw Exceptions.outOfBounds(idx);
            }
            for (int j = 0; j <= tix; ++j) {
                if (idx < counts[j] + table[j].length) {
                    return table[j][((int) (idx - counts[j]))];
                }
            }
            throw Exceptions.outOfBounds(idx);
        }

        /**
         * Inserts the given {@code int} value.
         *
         * @param val value to be inserted.
         * @return instance of unified type.
         */
        @Override
        public I32Chunk insert(final int val) {
            if (vix == chunk.length) {
                inflateTable();
                if (tix + 1 >= table.length || table[tix + 1] == null) {
                    grow();
                }
                vix = 0;
                ++tix;
                chunk = table[tix];
            }
            chunk[vix++] = val;
            return this;
        }

        /**
         * Inserts the {@code int} values contained in the given array.
         *
         * @param ary array of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public I32Chunk insert(final int... ary) {
            final int length;
            if ((length = ary.length) == 0) {
                return this;
            }
            var chunk = this.chunk;
            for (int i = 0; i < length; ++i) {
                if (vix == chunk.length) {
                    inflateTable();
                    if (tix + 1 >= table.length || table[tix + 1] == null) {
                        grow();
                    }
                    vix = 0;
                    ++tix;
                    chunk = this.chunk = table[tix];
                }
                chunk[vix++] = ary[i];
            }
            return this;
        }

        /**
         * Inserts the {@code int} values contained in the given iterable.
         *
         * @param itr iterable of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public I32Chunk insert(final Iterable<Integer> itr) {
            if (itr instanceof I32.Traverser)
                appendTraverser((I32.Traverser) itr);
            else
                appendIterator(itr.iterator());
            return this;
        }

        /**
         * Removes all values by releasing acquired resources.
         *
         * @return 'this' for flow coding.
         */
        @Override
        public I32Chunk clear() {
            if (table != null) {
                java.util.Arrays.fill(chunk = table[0], 0);
                table  = null;
                counts = null;
            }
            vix = 0;
            tix = 0;
            return this;
        }

        // ----------------------------------------------------------

        /**
         * Performs the given action on each value stored in this buffer.
         */
        public void forNext(final I32.Consumer action) {
            try {
                int[] chunk;
                { // saturated chunks, if any...
                    final int[][]  table = this.table;
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int i = 0; i < length; ++i) {
                            action.onAccept(chunk[i]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (int i = 0; i < vix; ++i) {
                        action.onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * Performs the given action on each indexed value stored in this buffer.
         */
        public void forNextIndexed(final Ix1D<I32.Consumer> action) {
            try {
                int i = 0;
                var chunk = table[0];
                { // saturated chunks, if any...
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int k = 0; k < length; ++k) {
                            action.index(i++).onAccept(chunk[k]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (; i < vix; ++i) {
                        action.index(i).onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        // ----------------------------------------------------------

        /**
         * Returns the contents of this buffer as {@code int[]} array.
         */
        @Override
        public int[] toArray() {
            return toArray(new int[Narrow.I32(count())], 0);
        }

        /**
         * Returns the contents of this buffer as {@code double[]} array.
         *
         * @param dst target array to store buffered values.
         * @param dstIndex index to start in the destination buffer.
         * @return values stored in the given {@code double[]} array.
         */
        public int[] toArray(final int[] dst, int dstIndex) {
            final var dstOffset = dstIndex + count();
            if (dstOffset > dst.length || dstOffset < dstIndex) {
                throw Exceptions.outOfBounds("illegal target array");
            }
            if (tix == 0) {
                System.arraycopy(chunk, 0, dst, dstIndex, vix);
            } else { // Copy full chunks.
                for (int i = 0; i < tix; ++i) {
                    final var length = Array.length(table[i]);
                    System.arraycopy(table[i], 0, dst, dstIndex, length);
                    dstIndex += length;
                }
                if (vix > 0) {
                    System.arraycopy(chunk, 0, dst, dstIndex, vix);
                }
            }
            return dst;
        }

        // ----------------------------------------------------------

        @Override
        public I32.Traverser.Duplex.Indexed traverser() {
            // Denotes the beginning of the critical section
            // for parallel modifications (not concurrent!).
            final class ChunkTraverser implements I32.Traverser.Duplex.Indexed {
                private final int lo = 0;
                private final int hi = Narrow.I32(count());
                private final int p0 = 0;
                private int px = p0;

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNext(final I32.Consumer action) {
                    if (px >= hi) return false;
                    action.accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNextIndexed(final Ix1D<I32.Consumer> action) {
                    if (px >= hi) return false;
                    action.index(px).accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrev(final I32.Consumer action) {
                    if (px <= lo) return false;
                    action.accept(at(--px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrevIndexed(final Ix1D<I32.Consumer> action) {
                    if (px <= lo) return false;
                    action.index(--px).accept(at(px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNext(final I32.Consumer action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) action.onAccept(at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNextIndexed(final Ix1D<I32.Consumer> action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) invoke(action, px, at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrev(final I32.Consumer action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) action.onAccept(at(--px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrevIndexed(final Ix1D<I32.Consumer> action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) invoke(action, --px, at(px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNext(final Until<Control, I32.Consumer> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            action.onAccept(at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNextIndexed(final Until<Control, Ix1D<I32.Consumer>> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            invoke(action, px, at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrev(final Until<Control, I32.Consumer> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            action.onAccept(at(--px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrevIndexed(final Until<Control, Ix1D<I32.Consumer>> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            invoke(action, --px, at(px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                private static void invoke(final Ix1D<I32.Consumer> action, final int px, final int value) throws Throwable {
                    action.index(px).onAccept(value);
                }
            }
            return new ChunkTraverser();
        }

        // ----------------------------------------------------------

        /**
         * Appends the content provided by the given traverser.
         */
        private void appendTraverser(final I32.Traverser traverser) {
            final class AppendTraverser implements I32.Consumer {
                private int[] chunk = I32Chunk.this.chunk;

                private AppendTraverser() { traverser.forNext(this); }

                @Override
                public void onAccept(final int val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendTraverser();
        }

        /**
         * Appends the content provided by the given iterator.
         */
        private void appendIterator(final java.util.Iterator<Integer> it) {
            final class AppendIterator implements java.util.function.Consumer<Integer> {
                private int[] chunk = I32Chunk.this.chunk;
                AppendIterator() { it.forEachRemaining(this); }

                @Override
                public void accept(final Integer val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendIterator();
        }
    }
    
    // ----------------------------------------------------------
    //  BUFFER.I64-CHUNK
    // ----------------------------------------------------------

    /**
     * A chunk buffer specialized for {@code long} values.
     */
    public static final class I64Chunk extends Buffer<Long, long[], I64Chunk>

            implements Operation.Access.At.OfI64<I64Chunk>,

            Operation.Plus.Bulk.Insert.OfI64<I64Chunk>,

            Operation.Minus.Clear.OfI64<I64Chunk>,

            Operation.Convert.ToCollection.OfI64<I64Chunk>,

            Operation.Convert.ToArray.OfI64<I64Chunk>,
            
            I64.Traversable
    {
        /**
         * Constructs a chunk buffer with an initial capacity of 8.
         *
         * @return chunked {@code boolean} buffer.
         */
        public static I64Chunk make() {
            return I64Chunk.make(Array.INITIAL_CAPACITY);
        }

        /**
         * Constructs a chunk buffer with the given minimum initial capacity.
         *
         * @param initialCapacity required.
         * @return chunked {@code boolean} buffer.
         */
        public static I64Chunk make(final int initialCapacity) {
            return new I64Chunk(initialCapacity);
        }

        // ----------------------------------------------------------

        /**
         * Allocator specialized for {@code long[]} array.
         */
        private enum Allocator implements Buffer.Allocator<long[]> {
            instance {
                public long[]   chunk(final int len) { return new long[len];   }
                public long[][] table(final int len) { return new long[len][]; }
            }
        }

        /**
         * Constructs a chunk with the given initial capacity.
         */
        private I64Chunk(final int initialCapacity) {
            super(Allocator.instance, initialCapacity);
        }

        /**
         * Returns the {@code long} value at the given index.
         *
         * @param idx whose corresponding value is to be returned.
         * @return {@code long} value.
         */
        @Override
        public long at(final long idx) {
            final var tix = this.tix;
            if (tix == 0) {
                if (idx < vix) {
                    return chunk[((int) idx)];
                } else {
                    throw Exceptions.outOfBounds(idx);
                }
            }
            if (idx >= count()) {
                throw Exceptions.outOfBounds(idx);
            }
            for (int j = 0; j <= tix; ++j) {
                if (idx < counts[j] + table[j].length) {
                    return table[j][((int) (idx - counts[j]))];
                }
            }
            throw Exceptions.outOfBounds(idx);
        }

        /**
         * Inserts the given {@code long} value.
         *
         * @param val value to be inserted.
         * @return instance of unified type.
         */
        @Override
        public I64Chunk insert(final long val) {
            if (vix == chunk.length) {
                inflateTable();
                if (tix + 1 >= table.length || table[tix + 1] == null) {
                    grow();
                }
                vix = 0;
                ++tix;
                chunk = table[tix];
            }
            chunk[vix++] = val;
            return this;
        }

        /**
         * Inserts the {@code long} values contained in the given array.
         *
         * @param ary array of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public I64Chunk insert(final long... ary) {
            final int length;
            if ((length = ary.length) == 0) {
                return this;
            }
            var chunk = this.chunk;
            for (int i = 0; i < length; ++i) {
                if (vix == chunk.length) {
                    inflateTable();
                    if (tix + 1 >= table.length || table[tix + 1] == null) {
                        grow();
                    }
                    vix = 0;
                    ++tix;
                    chunk = this.chunk = table[tix];
                }
                chunk[vix++] = ary[i];
            }
            return this;
        }

        /**
         * Inserts the {@code long} values contained in the given iterable.
         *
         * @param itr iterable of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public I64Chunk insert(final Iterable<Long> itr) {
            if (itr instanceof I64.Traverser)
                appendTraverser((I64.Traverser) itr);
            else
                appendIterator(itr.iterator());
            return this;
        }

        /**
         * Removes all values by releasing acquired resources.
         *
         * @return 'this' for flow coding.
         */
        @Override
        public I64Chunk clear() {
            if (table != null) {
                java.util.Arrays.fill(chunk = table[0], 0L);
                table  = null;
                counts = null;
            }
            vix = 0;
            tix = 0;
            return this;
        }

        // ----------------------------------------------------------

        /**
         * Performs the given action on each value stored in this buffer.
         */
        public void forNext(final I64.Consumer action) {
            try {
                long[] chunk;
                { // saturated chunks, if any...
                    final long[][]  table = this.table;
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int i = 0; i < length; ++i) {
                            action.onAccept(chunk[i]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (int i = 0; i < vix; ++i) {
                        action.onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * Performs the given action on each indexed value stored in this buffer.
         */
        public void forNextIndexed(final Ix1D<I64.Consumer> action) {
            try {
                int i = 0;
                var chunk = table[0];
                { // saturated chunks, if any...
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int k = 0; k < length; ++k) {
                            action.index(i++).onAccept(chunk[k]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (; i < vix; ++i) {
                        action.index(i).onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        // ----------------------------------------------------------

        /**
         * Returns the contents of this buffer as {@code long[]} array.
         */
        @Override
        public long[] toArray() {
            return toArray(new long[Narrow.I32(count())], 0);
        }

        /**
         * Returns the contents of this buffer as {@code double[]} array.
         *
         * @param dst target array to store buffered values.
         * @param dstIndex index to start in the destination buffer.
         * @return values stored in the given {@code double[]} array.
         */
        public long[] toArray(final long[] dst, int dstIndex) {
            final var dstOffset = dstIndex + count();
            if (dstOffset > dst.length || dstOffset < dstIndex) {
                throw Exceptions.outOfBounds("illegal target array");
            }
            if (tix == 0) {
                System.arraycopy(chunk, 0, dst, dstIndex, vix);
            } else { // Copy full chunks.
                for (int i = 0; i < tix; ++i) {
                    final var length = Array.length(table[i]);
                    System.arraycopy(table[i], 0, dst, dstIndex, length);
                    dstIndex += length;
                }
                if (vix > 0) {
                    System.arraycopy(chunk, 0, dst, dstIndex, vix);
                }
            }
            return dst;
        }

        // ----------------------------------------------------------
        
        @Override
        public I64.Traverser.Duplex.Indexed traverser() {
            // Denotes the beginning of the critical section
            // for parallel modifications (not concurrent!).
            final class ChunkTraverser implements I64.Traverser.Duplex.Indexed {
                private final int lo = 0;
                private final int hi = Narrow.I32(count());
                private final int p0 = 0;
                private int px = p0;

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNext(final I64.Consumer action) {
                    if (px >= hi) return false;
                    action.accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNextIndexed(final Ix1D<I64.Consumer> action) {
                    if (px >= hi) return false;
                    action.index(px).accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrev(final I64.Consumer action) {
                    if (px <= lo) return false;
                    action.accept(at(--px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrevIndexed(final Ix1D<I64.Consumer> action) {
                    if (px <= lo) return false;
                    action.index(--px).accept(at(px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNext(final I64.Consumer action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) action.onAccept(at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNextIndexed(final Ix1D<I64.Consumer> action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) invoke(action, px, at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrev(final I64.Consumer action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) action.onAccept(at(--px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrevIndexed(final Ix1D<I64.Consumer> action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) invoke(action, --px, at(px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNext(final Until<Control, I64.Consumer> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            action.onAccept(at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNextIndexed(final Until<Control, Ix1D<I64.Consumer>> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            invoke(action, px, at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrev(final Until<Control, I64.Consumer> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            action.onAccept(at(--px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrevIndexed(final Until<Control, Ix1D<I64.Consumer>> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            invoke(action, --px, at(px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                private static void invoke(final Ix1D<I64.Consumer> action, final int px, final long value) throws Throwable {
                    action.index(px).onAccept(value);
                }
            }
            return new ChunkTraverser();
        }

        // ----------------------------------------------------------
        
        /**
         * Appends the content provided by the given traverser.
         */
        private void appendTraverser(final I64.Traverser traverser) {
            final class AppendTraverser implements I64.Consumer {
                private long[] chunk = I64Chunk.this.chunk;
                private AppendTraverser() { traverser.forNext(this); }

                @Override
                public void onAccept(final long val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendTraverser();
        }

        /**
         * Appends the content provided by the given iterator.
         */
        private void appendIterator(final java.util.Iterator<Long> it) {
            final class AppendIterator implements java.util.function.Consumer<Long> {
                private long[] chunk = I64Chunk.this.chunk;
                AppendIterator() { it.forEachRemaining(this); }

                @Override
                public void accept(final Long val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendIterator();
        }
    }
    
    // ----------------------------------------------------------
    //  BUFFER.F32-CHUNK
    // ----------------------------------------------------------

    /**
     * A chunk buffer specialized for {@code float} values.
     */
    public static final class F32Chunk extends Buffer<Float, float[], F32Chunk>

            implements Operation.Access.At.OfF32<F32Chunk>,

            Operation.Plus.Bulk.Insert.OfF32<F32Chunk>,

            Operation.Minus.Clear.OfF32<F32Chunk>,

            Operation.Convert.ToCollection.OfF32<F32Chunk>,

            Operation.Convert.ToArray.OfF32<F32Chunk>,

            F32.Traversable
    {
        /**
         * Constructs a chunk buffer with an initial capacity of 8.
         *
         * @return chunked {@code boolean} buffer.
         */
        public static F32Chunk make() {
            return F32Chunk.make(Array.INITIAL_CAPACITY);
        }

        /**
         * Constructs a chunk buffer with the given minimum initial capacity.
         *
         * @param initialCapacity required.
         * @return chunked {@code boolean} buffer.
         */
        public static F32Chunk make(final int initialCapacity) {
            return new F32Chunk(initialCapacity);
        }

        // ----------------------------------------------------------

        /**
         * Allocator specialized for {@code float[]} array.
         */
        private enum Allocator implements Buffer.Allocator<float[]> {
            instance {
                public float[]   chunk(final int len) { return new float[len];   }
                public float[][] table(final int len) { return new float[len][]; }
            }
        }

        /**
         * Constructs a chunk with the given initial capacity.
         */
        private F32Chunk(final int initialCapacity) {
            super(Allocator.instance, initialCapacity);
        }

        /**
         * Returns the {@code float} value at the given index.
         *
         * @param idx whose corresponding value is to be returned.
         * @return {@code float} value.
         */
        @Override
        public float at(final long idx) {
            final var tix = this.tix;
            if (tix == 0) {
                if (idx < vix) {
                    return chunk[((int) idx)];
                } else {
                    throw Exceptions.outOfBounds(idx);
                }
            }
            if (idx >= count()) {
                throw Exceptions.outOfBounds(idx);
            }
            for (int j = 0; j <= tix; ++j) {
                if (idx < counts[j] + table[j].length) {
                    return table[j][((int) (idx - counts[j]))];
                }
            }
            throw Exceptions.outOfBounds(idx);
        }

        /**
         * Inserts the given {@code float} value.
         *
         * @param val value to be inserted.
         * @return instance of unified type.
         */
        @Override
        public F32Chunk insert(final float val) {
            if (vix == chunk.length) {
                inflateTable();
                if (tix + 1 >= table.length || table[tix + 1] == null) {
                    grow();
                }
                vix = 0;
                ++tix;
                chunk = table[tix];
            }
            chunk[vix++] = val;
            return this;
        }

        /**
         * Inserts the {@code float} values contained in the given array.
         *
         * @param ary array of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public F32Chunk insert(final float... ary) {
            final int length;
            if ((length = ary.length) == 0) {
                return this;
            }
            var chunk = this.chunk;
            for (int i = 0; i < length; ++i) {
                if (vix == chunk.length) {
                    inflateTable();
                    if (tix + 1 >= table.length || table[tix + 1] == null) {
                        grow();
                    }
                    vix = 0;
                    ++tix;
                    chunk = this.chunk = table[tix];
                }
                chunk[vix++] = ary[i];
            }
            return this;
        }

        /**
         * Inserts the {@code float} values contained in the given iterable.
         *
         * @param itr iterable of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public F32Chunk insert(final Iterable<Float> itr) {
            if (itr instanceof F32.Traverser)
                appendTraverser((F32.Traverser) itr);
            else
                appendIterator(itr.iterator());
            return this;
        }

        /**
         * Removes all values by releasing acquired resources.
         *
         * @return 'this' for flow coding.
         */
        @Override
        public F32Chunk clear() {
            if (table != null) {
                java.util.Arrays.fill(chunk = table[0], 0.f);
                table  = null;
                counts = null;
            }
            vix = 0;
            tix = 0;
            return this;
        }

        // ----------------------------------------------------------

        /**
         * Performs the given action on each value stored in this buffer.
         */
        public void forNext(final F32.Consumer action) {
            try {
                float[] chunk;
                { // saturated chunks, if any...
                    final float[][]  table = this.table;
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int i = 0; i < length; ++i) {
                            action.onAccept(chunk[i]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (int i = 0; i < vix; ++i) {
                        action.onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * Performs the given action on each indexed value stored in this buffer.
         */
        public void forNextIndexed(final Ix1D<F32.Consumer> action) {
            try {
                int i = 0;
                var chunk = table[0];
                { // saturated chunks, if any...
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int k = 0; k < length; ++k) {
                            action.index(i++).onAccept(chunk[k]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (; i < vix; ++i) {
                        action.index(i).onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        // ----------------------------------------------------------

        /**
         * Returns the contents of this buffer as {@code float[]} array.
         */
        @Override
        public float[] toArray() {
            return toArray(new float[Narrow.I32(count())], 0);
        }

        /**
         * Returns the contents of this buffer as {@code double[]} array.
         *
         * @param dst target array to store buffered values.
         * @param dstIndex index to start in the destination buffer.
         * @return values stored in the given {@code double[]} array.
         */
        public float[] toArray(final float[] dst, int dstIndex) {
            final var dstOffset = dstIndex + count();
            if (dstOffset > dst.length || dstOffset < dstIndex) {
                throw Exceptions.outOfBounds("illegal target array");
            }
            if (tix == 0) {
                System.arraycopy(chunk, 0, dst, dstIndex, vix);
            } else { // Copy full chunks.
                for (int i = 0; i < tix; ++i) {
                    final var length = Array.length(table[i]);
                    System.arraycopy(table[i], 0, dst, dstIndex, length);
                    dstIndex += length;
                }
                if (vix > 0) {
                    System.arraycopy(chunk, 0, dst, dstIndex, vix);
                }
            }
            return dst;
        }

        // ----------------------------------------------------------

        /**
         * Returns a bidirectional indexed-traverser over this buffer.
         */
        @Override
        public F32.Traverser.Duplex.Indexed traverser() {
            // Denotes the beginning of the critical section
            // for parallel modifications (not concurrent!).
            final class ChunkTraverser implements F32.Traverser.Duplex.Indexed {
                private final int lo = 0;
                private final int hi = Narrow.I32(count());
                private final int p0 = 0;
                private int px = p0;

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNext(final F32.Consumer action) {
                    if (px >= hi) return false;
                    action.accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNextIndexed(final Ix1D<F32.Consumer> action) {
                    if (px >= hi) return false;
                    action.index(px).accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrev(final F32.Consumer action) {
                    if (px <= lo) return false;
                    action.accept(at(--px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrevIndexed(final Ix1D<F32.Consumer> action) {
                    if (px <= lo) return false;
                    action.index(--px).accept(at(px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNext(final F32.Consumer action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) action.onAccept(at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNextIndexed(final Ix1D<F32.Consumer> action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) invoke(action, px, at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrev(final F32.Consumer action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) action.onAccept(at(--px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrevIndexed(final Ix1D<F32.Consumer> action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) invoke(action, --px, at(px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNext(final Until<Control, F32.Consumer> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            action.onAccept(at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNextIndexed(final Until<Control, Ix1D<F32.Consumer>> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            invoke(action, px, at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrev(final Until<Control, F32.Consumer> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            action.onAccept(at(--px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrevIndexed(final Until<Control, Ix1D<F32.Consumer>> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            invoke(action, --px, at(px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                private static void invoke(final Ix1D<F32.Consumer> action, final int px, final float value) throws Throwable {
                    action.index(px).onAccept(value);
                }
            }
            return new ChunkTraverser();
        }

        // ----------------------------------------------------------

        /**
         * Appends the content provided by the given traverser.
         */
        private void appendTraverser(final F32.Traverser traverser) {
            final class AppendTraverser implements F32.Consumer {
                private float[] chunk = F32Chunk.this.chunk;
                private AppendTraverser() { traverser.forNext(this); }

                @Override
                public void onAccept(final float val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendTraverser();
        }
        
        /**
         * Appends the content provided by the given iterator.
         */
        private void appendIterator(final java.util.Iterator<Float> it) {
            final class AppendIterator implements java.util.function.Consumer<Float> {
                private float[] chunk = F32Chunk.this.chunk;
                AppendIterator() { it.forEachRemaining(this); }

                @Override
                public void accept(final Float val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendIterator();
        }
    }

    // ----------------------------------------------------------
    //  BUFFER.F64-CHUNK
    // ----------------------------------------------------------

    /**
     * A chunk buffer specialized for {@code double} values.
     */
    public static final class F64Chunk extends Buffer<Double, double[], F64Chunk>

            implements Operation.Access.At.OfF64<F64Chunk>,

            Operation.Plus.Bulk.Insert.OfF64<F64Chunk>,
    
            Operation.Minus.Clear.OfF64<F64Chunk>,

            Operation.Convert.ToCollection.OfF64<F64Chunk>,

            Operation.Convert.ToArray.OfF64<F64Chunk>,
            
            F64.Traversable
    {
        /**
         * Constructs a chunk buffer with an initial capacity of 8.
         *
         * @return chunked {@code boolean} buffer.
         */
        public static F64Chunk make() {
            return F64Chunk.make(Array.INITIAL_CAPACITY);
        }

        /**
         * Constructs a chunk buffer with the given minimum initial capacity.
         *
         * @param initialCapacity required.
         * @return chunked {@code boolean} buffer.
         */
        public static F64Chunk make(final int initialCapacity) {
            return new F64Chunk(initialCapacity);
        }
        
        // ----------------------------------------------------------

        /**
         * Allocator specialized for {@code double[]} array.
         */
        private enum Allocator implements Buffer.Allocator<double[]> {
            instance {
                public double[]   chunk(final int len) { return new double[len];   }
                public double[][] table(final int len) { return new double[len][]; }
            }
        }

        /**
         * Constructs a chunk with the given initial capacity.
         */
        private F64Chunk(final int initialCapacity) {
            super(Allocator.instance, initialCapacity);
        }

        /**
         * Returns the {@code double} value at the given index.
         *
         * @param idx whose corresponding value is to be returned.
         * @return {@code double} value.
         */
        @Override
        public double at(final long idx) {
            final var tix = this.tix;
            if (tix == 0) {
                if (idx < vix) {
                    return chunk[((int) idx)];
                } else {
                    throw Exceptions.outOfBounds(idx);
                }
            }
            if (idx >= count()) {
                throw Exceptions.outOfBounds(idx);
            }
            for (int j = 0; j <= tix; ++j) {
                if (idx < counts[j] + table[j].length) {
                    return table[j][((int) (idx - counts[j]))];
                }
            }
            throw Exceptions.outOfBounds(idx);
        }

        /**
         * Inserts the given {@code double} value.
         *
         * @param val value to be inserted.
         * @return instance of unified type.
         */
        @Override
        public F64Chunk insert(final double val) {
            if (vix == chunk.length) {
                inflateTable();
                if (tix + 1 >= table.length || table[tix + 1] == null) {
                    grow();
                }
                vix = 0;
                ++tix;
                chunk = table[tix];
            }
            chunk[vix++] = val;
            return this;
        }

        /**
         * Inserts the {@code double} values contained in the given array.
         *
         * @param ary array of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public F64Chunk insert(final double... ary) {
            final int length;
            if ((length = ary.length) == 0) {
                return this;
            }
            var chunk = this.chunk;
            for (int i = 0; i < length; ++i) {
                if (vix == chunk.length) {
                    inflateTable();
                    if (tix + 1 >= table.length || table[tix + 1] == null) {
                        grow();
                    }
                    vix = 0;
                    ++tix;
                    chunk = this.chunk = table[tix];
                }
                chunk[vix++] = ary[i];
            }
            return this;
        }

        /**
         * Inserts the {@code double} values contained in the given iterable.
         *
         * @param itr iterable of values to be inserted.
         * @return instance of unified type.
         */
        @Override
        public F64Chunk insert(final Iterable<Double> itr) {
            if (itr instanceof F64.Traverser)
                appendTraverser((F64.Traverser) itr);
            else
                appendIterator(itr.iterator());
            return this;
        }

        /**
         * Removes all values by releasing acquired resources.
         *
         * @return 'this' for flow coding.
         */
        public F64Chunk clear() {
            if (table != null) {
                java.util.Arrays.fill(chunk = table[0], 0.);
                table  = null;
                counts = null;
            }
            vix = 0;
            tix = 0;
            return this;
        }

        // ----------------------------------------------------------

        /**
         * Performs the given action on each value stored in this buffer.
         */
        public void forNext(final F64.Consumer action) {
            try {
                double[] chunk;
                { // saturated chunks, if any...
                    final double[][]  table = this.table;
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int i = 0; i < length; ++i) {
                            action.onAccept(chunk[i]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (int i = 0; i < vix; ++i) {
                        action.onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * Performs the given action on each indexed value stored in this buffer.
         */
        public void forNextIndexed(final Ix1D<F64.Consumer> action) {
            try {
                int i = 0;
                var chunk = table[0];
                { // saturated chunks, if any...
                    final var tix = this.tix;
                    for (int j = 0; j < tix; ++j) {
                        final var length = (chunk = table[j]).length;
                        for (int k = 0; k < length; ++k) {
                            action.index(i++).onAccept(chunk[k]);
                        }
                    }
                }
                final var vix = this.vix;
                {
                    chunk = this.chunk;
                    for (; i < vix; ++i) {
                        action.index(i).onAccept(chunk[i]);
                    }
                }
            } catch (Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        // ----------------------------------------------------------

        /**
         * Returns the contents of this buffer as {@code double[]} array.
         */
        @Override
        public double[] toArray() {
            return toArray(new double[Narrow.I32(count())], 0);
        }

        /**
         * Returns the contents of this buffer as {@code double[]} array.
         *
         * @param dst target array to store buffered values.
         * @param dstIndex index to start in the destination buffer.
         * @return values stored in the given {@code double[]} array.
         */
        public double[] toArray(final double[] dst, int dstIndex) {
            final var dstOffset = dstIndex + count();
            if (dstOffset > dst.length || dstOffset < dstIndex) {
                throw Exceptions.outOfBounds("illegal target array");
            }
            if (tix == 0) {
                System.arraycopy(chunk, 0, dst, dstIndex, vix);
            } else { // Copy full chunks.
                for (int i = 0; i < tix; ++i) {
                    final var length = Array.length(table[i]);
                    System.arraycopy(table[i], 0, dst, dstIndex, length);
                    dstIndex += length;
                }
                if (vix > 0) {
                    System.arraycopy(chunk, 0, dst, dstIndex, vix);
                }
            }
            return dst;
        }

        // ----------------------------------------------------------

        /**
         * Returns a bidirectional indexed-traverser over this buffer.
         */
        @Override
        public F64.Traverser.Duplex.Indexed traverser() {
            // Denotes the beginning of the critical section
            // for parallel modifications (not concurrent!).
            final class ChunkTraverser implements F64.Traverser.Duplex.Indexed {
                private final int lo = 0;
                private final int hi = Narrow.I32(count());
                private final int p0 = 0;
                private int px = p0;

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNext(final F64.Consumer action) {
                    if (px >= hi) return false;
                    action.accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryNextIndexed(final Ix1D<F64.Consumer> action) {
                    if (px >= hi) return false;
                    action.index(px).accept(at(px++));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrev(final F64.Consumer action) {
                    if (px <= lo) return false;
                    action.accept(at(--px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public boolean tryPrevIndexed(final Ix1D<F64.Consumer> action) {
                    if (px <= lo) return false;
                    action.index(--px).accept(at(px));
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNext(final F64.Consumer action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) action.onAccept(at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forNextIndexed(final Ix1D<F64.Consumer> action) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return;
                        }
                        while (px < hi) invoke(action, px, at(px++));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrev(final F64.Consumer action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) action.onAccept(at(--px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void forPrevIndexed(final Ix1D<F64.Consumer> action) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return;
                        }
                        while (px > lo) invoke(action, --px, at(px));
                        this.px = px;
                    } catch (Throwable ex) {
                        Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNext(final Until<Control, F64.Consumer> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            action.onAccept(at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whileNextIndexed(final Until<Control, Ix1D<F64.Consumer>> driver) {
                    try {
                        int px;
                        final int hi;
                        if ((px = this.px) >= (hi = this.hi)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px < hi && state[0] == State.S_INIT) {
                            invoke(action, px, at(px++));
                        }
                        if ((this.px = px) >= hi) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrev(final Until<Control, F64.Consumer> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            action.onAccept(at(--px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public long whilePrevIndexed(final Until<Control, Ix1D<F64.Consumer>> driver) {
                    try {
                        int px;
                        final int lo;
                        if ((px = this.px) <= (lo = this.lo)) {
                            return State.S_COMPLETED;
                        }
                        final int[] state = { State.S_INIT };
                        final Control control = () -> state[0] |= State.S_EXITED;
                        final var action = driver.onApply(control);
                        while (px > lo && state[0] == State.S_INIT) {
                            invoke(action, --px, at(px));
                        }
                        if ((this.px = px) <= lo) {
                            state[0] |= State.S_COMPLETED;
                        }
                        return state[0];
                    } catch (Throwable ex) {
                        return Throw.sneaky(ex);
                    }
                }

                private static void invoke(final Ix1D<F64.Consumer> action, final int px, final double value) throws Throwable {
                    action.index(px).onAccept(value);
                }
            }
            return new ChunkTraverser();
        }
        
        // ----------------------------------------------------------

        /**
         * Appends the content provided by the given traverser.
         */
        private void appendTraverser(final F64.Traverser traverser) {
            final class AppendTraverser implements F64.Consumer {
                private double[] chunk = F64Chunk.this.chunk;

                private AppendTraverser() { traverser.forNext(this); }

                @Override
                public void onAccept(final double val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendTraverser();
        }
        
        /**
         * Appends the content provided by the given iterator.
         */
        private void appendIterator(final java.util.Iterator<Double> it) {
            final class AppendIterator implements java.util.function.Consumer<Double> {
                private double[] chunk = F64Chunk.this.chunk;
                AppendIterator() { it.forEachRemaining(this); }

                @Override
                public void accept(final Double val) {
                    if (vix == chunk.length) {
                        inflateTable();
                        if (tix + 1 >= table.length || table[tix + 1] == null) {
                            grow();
                        }
                        vix = 0;
                        ++tix;
                        chunk = update(table[tix]);
                    }
                    chunk[vix++] = val;
                }
            }
            new AppendIterator();
        }
    }

    // ----------------------------------------------------------

    /** Encapsulates chunk and table allocation.  */ 
    protected final Allocator<T_ARY> allocator;
    
    protected final int initialChunkPower;

    /** Spine in which chunks are organized. */
    protected T_ARY[] table;

    /** Current chunk determined by append index. */
    protected T_ARY   chunk;

    /** Length of all saturated chunks. */
    protected long[]  counts;

    /** Value-index in current chunk. */
    protected int vix;

    /** Selecting current chunk. */
    protected int tix;

    // ----------------------------------------------------------

    /**
     * Constructs a buffer with the given allocator and initial capacity. 
     */
    protected Buffer(final Allocator<T_ARY> allocator, final int initialCapacity) {
        this.allocator = allocator;
        this.initialChunkPower = (initialCapacity > 0) 
                ? Math.max(MIN_CHUNK_POWER, Bit.findNextPositivePowerOfTwo(initialCapacity)) 
                : MIN_CHUNK_POWER;
        this.chunk = allocator.chunk(1 << initialChunkPower);
    }

    // ----------------------------------------------------------

    /**
     * Returns the current capacity of this buffer.
     */
    @Override
    public final long capacity() {
        return (tix > 0) ? counts[tix] + Array.length(table[tix]) : Array.length(chunk);
    }

    /**
     * Determines whether the given buffer is empty.
     */
    @Override
    public final boolean isEmpty() {
        return (tix == 0) && (vix == 0);
    }

    /**
     * Returns the number of values stored in this buffer.
     */
    @Override
    public final long count() {
        return (tix > 0) ? counts[tix] + vix : vix;
    }
    
    // ----------------------------------------------------------

    final T_ARY   update(final T_ARY   chunk) { return this.chunk = chunk; }
    final T_ARY[] update(final T_ARY[] table) { return this.table = table; }

    final void grow() {
        ensureCapacity(capacity() + 1);
    }

    final void ensureCapacity(final long targetSize) {
        long capacity = capacity();
        if (targetSize > capacity) {
            inflateTable();
            for (int i = tix + 1; targetSize > capacity; ++i) {
                if (i >= table.length) {
                    final var newTableSize = table.length << 1;
                    table  = java.util.Arrays.copyOf(table,  newTableSize);
                    counts = java.util.Arrays.copyOf(counts, newTableSize);
                }
                final int nextSize = chunkSize(initialChunkPower, i);
                table[i]  = allocator.chunk(nextSize);
                counts[i] = counts[i - 1] + Array.length(table[i - 1]);
                capacity += nextSize;
            }
        }
    }

    final void inflateTable() {
        if (table == null) {
            table    = allocator.table(MIN_TABLE_COUNT);
            counts   = new long[MIN_TABLE_COUNT];
            table[0] = chunk;
        }
    }

    private static int chunkSize(final int initialChunkPower, final int tix) {
        return 1 << ((tix != 0 && tix != 1) 
                ? Math.min(initialChunkPower + tix - 1, Buffer.MAX_CHUNK_POWER) 
                : initialChunkPower);
    }
}
