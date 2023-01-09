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

package magma.exa.control.traversal;

import magma.exa.base.Force;
import magma.exa.base.contract.Assert;
import magma.exa.control.adt.traversal.ICursor;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.data.index.Ix1D;
import magma.exa.data.index.Range;
import magma.exa.value.scalar.*;

/**
 *
 */
public enum Traversal {
    ;
    /**
     * Returns an of adapter over values of type {@code A} for the given iterator.
     *
     * @param itr to be adapted.
     * @param <A> type of value.
     * @return adapter.
     */
    public static <A> Traverser<A> adapt(final Iterable<? extends A> itr) {
        java.util.Objects.requireNonNull(itr);
        if (itr == Iterators.empty()) return Traverser.empty();

        final class Adapter implements Traverser<A> {
            private final java.util.Iterator<? extends A> it = itr.iterator();

            @Override
            public boolean tryNext(Fn1.Consumer<? super A> action) {
                if (!it.hasNext()) return false;
                action.accept(it.next());
                return true;
            }
        }
        return new Adapter();
    }

    // ----------------------------------------------------------

    /// REFERENCE TRAVERSAL.

    public static <A> Traverser.Duplex.Indexed<A> single(final A value) {
        return of(Force.<A[]>cast(new Object[] { value }));
    }

    public static <A> Traverser.Duplex.Indexed<A> of(final A[] data) {
        return of(data, 0, data.length, 0);
    }

    public static <A> Traverser.Duplex.Indexed<A> of(final A[] data, final int p0) {
        return of(data, 0, data.length, p0);
    }

    public static <A> Traverser.Duplex.Indexed<A> of(final A[] data, final int lo, final int hi) {
        return of(data, lo, hi, lo);
    }

    public static <A> Traverser.Duplex.Indexed<A> of(final A[] data, final int lo, final int hi, final int p0) {
        Assert.range(lo, hi, data.length);
        Assert.position(p0, lo, hi);

        final class RefTraverser extends AbstractBase<A[]> implements Traverser.Duplex.Indexed<A> {

            private RefTraverser(A[] data, int lo, int hi, int p0) {
                super(data, lo, hi, p0);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNext(final Fn1.Consumer<? super A> action) {
                if (px >= hi) return false;
                action.accept(data[px++]);
                return true;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNextIndexed(final Ix1D<Fn1.Consumer<? super A>> action) {
                if (px >= hi) return false;
                action.index(px).accept(data[px++]);
                return true;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrev(final Fn1.Consumer<? super A> action) {
                if (px <= lo) return false;
                action.accept(data[--px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrevIndexed(final Ix1D<Fn1.Consumer<? super A>> action) {
                if (px <= lo) return false;
                action.index(--px).accept(data[px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void forNext(final Fn1.Consumer<? super A> action) {
                try {
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) invoke(action, --px, data[px]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        invoke(action, --px, data[px]);
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
            void invoke(final Ix1D<Fn1.Consumer<? super A>> action,
                        final int px, final A value) throws Throwable {
                action.index(px).onAccept(value);
            }
        }
        return new RefTraverser(data, lo, hi, p0);
    }

    // ----------------------------------------------------------

    /// BOOL TRAVERSAL.

    public static Bool.Traverser.Duplex.Indexed single(final boolean value) {
        return of(new boolean[] { value });
    }

    public static Bool.Traverser.Duplex.Indexed of(final boolean[] data) {
        return of(data, 0, data.length, 0);
    }

    public static Bool.Traverser.Duplex.Indexed of(final boolean[] data, final int p0) {
        return of(data, 0, data.length, p0);
    }

    public static Bool.Traverser.Duplex.Indexed of(final boolean[] data, final int lo, final int hi) {
        return of(data, lo, hi, lo);
    }

    public static Bool.Traverser.Duplex.Indexed of(final boolean[] data, final int lo, final int hi, final int p0) {
        Assert.range(lo, hi, data.length);
        Assert.position(p0, lo, hi);

        final class ArrayTraverser extends AbstractBase<boolean[]> implements Bool.Traverser.Duplex.Indexed {

            private ArrayTraverser(boolean[] data, int lo, int hi, int p0) {
                super(data, lo, hi, p0);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNext(final Bool.Consumer action) {
                if (px >= hi) return false;
                action.accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNextIndexed(final Ix1D<Bool.Consumer> action) {
                if (px >= hi) return false;
                action.index(px).accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrev(final Bool.Consumer action) {
                if (px <= lo) return false;
                action.accept(data[--px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrevIndexed(final Ix1D<Bool.Consumer> action) {
                if (px <= lo) return false;
                action.index(--px).accept(data[px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void forNext(final Bool.Consumer action) {
                try {
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) invoke(action, --px, data[px]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        invoke(action, --px, data[px]);
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
        return new ArrayTraverser(data, lo, hi, p0);
    }

    // ----------------------------------------------------------

    /// I8 TRAVERSAL.

    public static I8.Traverser.Duplex.Indexed single(final byte value) {
        return of(new byte[] { value });
    }

    public static I8.Traverser.Duplex.Indexed of(final byte[] data) {
        return of(data, 0, data.length, 0);
    }

    public static I8.Traverser.Duplex.Indexed of(final byte[] data, final int p0) {
        return of(data, 0, data.length, p0);
    }

    public static I8.Traverser.Duplex.Indexed of(final byte[] data, final int lo, final int hi) {
        return of(data, lo, hi, lo);
    }

    public static I8.Traverser.Duplex.Indexed of(final byte[] data, final int lo, final int hi, final int p0) {
        Assert.range(lo, hi, data.length);
        Assert.position(p0, lo, hi);

        final class ArrayTraverser extends AbstractBase<byte[]> implements I8.Traverser.Duplex.Indexed {

            private ArrayTraverser(byte[] data, int lo, int hi, int p0) {
                super(data, lo, hi, p0);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNext(final I8.Consumer action) {
                if (px >= hi) return false;
                action.accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNextIndexed(final Ix1D<I8.Consumer> action) {
                if (px >= hi) return false;
                action.index(px).accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrev(final I8.Consumer action) {
                if (px <= lo) return false;
                action.accept(data[--px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrevIndexed(final Ix1D<I8.Consumer> action) {
                if (px <= lo) return false;
                action.index(--px).accept(data[px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void forNext(final I8.Consumer action) {
                try {
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) invoke(action, --px, data[px]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        invoke(action, --px, data[px]);
                    }
                    if ((this.px = px) <= lo) {
                        state[0] |= State.S_COMPLETED;
                    }
                    return state[0];
                } catch (Throwable ex) {
                    return Throw.sneaky(ex);
                }
            }

            private static void invoke(final Ix1D<I8.Consumer> action, final int px, final byte value) throws Throwable {
                action.index(px).onAccept(value);
            }
        }
        return new ArrayTraverser(data, lo, hi, p0);
    }

    // ----------------------------------------------------------

    /// I16 TRAVERSAL.

    public static I16.Traverser.Duplex.Indexed single(final short value) {
        return of(new short[] { value });
    }

    public static I16.Traverser.Duplex.Indexed of(final short[] data) {
        return of(data, 0, data.length, 0);
    }

    public static I16.Traverser.Duplex.Indexed of(final short[] data, final int p0) {
        return of(data, 0, data.length, p0);
    }

    public static I16.Traverser.Duplex.Indexed of(final short[] data, final int lo, final int hi) {
        return of(data, lo, hi, lo);
    }

    public static I16.Traverser.Duplex.Indexed of(final short[] data, final int lo, final int hi, final int p0) {
        Assert.range(lo, hi, data.length);
        Assert.position(p0, lo, hi);

        final class ArrayTraverser extends AbstractBase<short[]> implements I16.Traverser.Duplex.Indexed {

            private ArrayTraverser(short[] data, int lo, int hi, int p0) {
                super(data, lo, hi, p0);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNext(final I16.Consumer action) {
                if (px >= hi) return false;
                action.accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNextIndexed(final Ix1D<I16.Consumer> action) {
                if (px >= hi) return false;
                action.index(px).accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrev(final I16.Consumer action) {
                if (px <= lo) return false;
                action.accept(data[--px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrevIndexed(final Ix1D<I16.Consumer> action) {
                if (px <= lo) return false;
                action.index(--px).accept(data[px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void forNext(final I16.Consumer action) {
                try {
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) invoke(action, --px, data[px]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        invoke(action, --px, data[px]);
                    }
                    if ((this.px = px) <= lo) {
                        state[0] |= State.S_COMPLETED;
                    }
                    return state[0];
                } catch (Throwable ex) {
                    return Throw.sneaky(ex);
                }
            }

            private static void invoke(final Ix1D<I16.Consumer> action, final int px, final short value) throws Throwable {
                action.index(px).onAccept(value);
            }
        }
        return new ArrayTraverser(data, lo, hi, p0);
    }

    // ----------------------------------------------------------

    /// C16 TRAVERSAL.

    public static C16.Traverser.Duplex.Indexed single(final char value) {
        return of(new char[] { value });
    }

    public static C16.Traverser.Duplex.Indexed of(final char[] data) {
        return of(data, 0, data.length, 0);
    }

    public static C16.Traverser.Duplex.Indexed of(final char[] data, final int p0) {
        return of(data, 0, data.length, p0);
    }

    public static C16.Traverser.Duplex.Indexed of(final char[] data, final int lo, final int hi) {
        return of(data, lo, hi, lo);
    }

    public static C16.Traverser.Duplex.Indexed of(final char[] data, final int lo, final int hi, final int p0) {
        Assert.range(lo, hi, data.length);
        Assert.position(p0, lo, hi);

        final class ArrayTraverser extends AbstractBase<char[]> implements C16.Traverser.Duplex.Indexed {

            private ArrayTraverser(char[] data, int lo, int hi, int p0) {
                super(data, lo, hi, p0);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNext(final C16.Consumer action) {
                if (px >= hi) return false;
                action.accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNextIndexed(final Ix1D<C16.Consumer> action) {
                if (px >= hi) return false;
                action.index(px).accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrev(final C16.Consumer action) {
                if (px <= lo) return false;
                action.accept(data[--px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrevIndexed(final Ix1D<C16.Consumer> action) {
                if (px <= lo) return false;
                action.index(--px).accept(data[px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void forNext(final C16.Consumer action) {
                try {
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) action.onAccept(data[px++]);
                    this.px = px;
                } catch (Throwable ex) {
                    Throw.sneaky(ex);
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void forNextIndexed(final Ix1D<C16.Consumer> action) {
                try {
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) invoke(action, px, data[px++]);
                    this.px = px;
                } catch (Throwable ex) {
                    Throw.sneaky(ex);
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void forPrev(final C16.Consumer action) {
                try {
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) action.onAccept(data[--px]);
                    this.px = px;
                } catch (Throwable ex) {
                    Throw.sneaky(ex);
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void forPrevIndexed(final Ix1D<C16.Consumer> action) {
                try {
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) invoke(action, --px, data[px]);
                    this.px = px;
                } catch (Throwable ex) {
                    Throw.sneaky(ex);
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public long whileNext(final Until<Control, C16.Consumer> driver) {
                try {
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        action.onAccept(data[px++]);
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
            public long whileNextIndexed(final Until<Control, Ix1D<C16.Consumer>> driver) {
                try {
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        invoke(action, px, data[px++]);
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
            public long whilePrev(final Until<Control, C16.Consumer> driver) {
                try {
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        action.onAccept(data[--px]);
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
            public long whilePrevIndexed(final Until<Control, Ix1D<C16.Consumer>> driver) {
                try {
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        invoke(action, --px, data[px]);
                    }
                    if ((this.px = px) <= lo) {
                        state[0] |= State.S_COMPLETED;
                    }
                    return state[0];
                } catch (Throwable ex) {
                    return Throw.sneaky(ex);
                }
            }

            private static void invoke(final Ix1D<C16.Consumer> action, final int px, final char value) throws Throwable {
                action.index(px).onAccept(value);
            }
        }
        return new ArrayTraverser(data, lo, hi, p0);
    }

    // ----------------------------------------------------------

    /// I32 TRAVERSAL.

    public static I32.Traverser.Duplex.Indexed single(final int value) {
        return of(new int[] { value });
    }

    public static I32.Traverser.Duplex.Indexed of(final int[] data) {
        return of(data, 0, data.length, 0);
    }

    public static I32.Traverser.Duplex.Indexed of(final int[] data, final int p0) {
        return of(data, 0, data.length, p0);
    }

    public static I32.Traverser.Duplex.Indexed of(final int[] data, final int lo, final int hi) {
        return of(data, lo, hi, lo);
    }

    public static I32.Traverser.Duplex.Indexed of(final int[] data, final int lo, final int hi, final int p0) {
        Assert.range(lo, hi, data.length);
        Assert.position(p0, lo, hi);

        final class I32Traverser extends AbstractBase<int[]> implements I32.Traverser.Duplex.Indexed {

            private I32Traverser(int[] data, int lo, int hi, int p0) {
                super(data, lo, hi, p0);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNext(final I32.Consumer action) {
                if (px >= hi) return false;
                action.accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNextIndexed(final Ix1D<I32.Consumer> action) {
                if (px >= hi) return false;
                action.index(px).accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrev(final I32.Consumer action) {
                if (px <= lo) return false;
                action.accept(data[--px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrevIndexed(final Ix1D<I32.Consumer> action) {
                if (px <= lo) return false;
                action.index(--px).accept(data[px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void forNext(final I32.Consumer action) {
                try {
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) invoke(action, --px, data[px]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        invoke(action, --px, data[px]);
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
        return new I32Traverser(data, lo, hi, p0);
    }

    // ----------------------------------------------------------

    /// I64 TRAVERSAL.

    public static I64.Traverser.Duplex.Indexed single(final long value) {
        return of(new long[] { value });
    }

    public static I64.Traverser.Duplex.Indexed of(final long[] data) {
        return of(data, 0, data.length, 0);
    }

    public static I64.Traverser.Duplex.Indexed of(final long[] data, final int p0) {
        return of(data, 0, data.length, p0);
    }

    public static I64.Traverser.Duplex.Indexed of(final long[] data, final int lo, final int hi) {
        return of(data, lo, hi, lo);
    }

    public static I64.Traverser.Duplex.Indexed of(final long[] data, final int lo, final int hi, final int p0) {
        Assert.range(lo, hi, data.length);
        Assert.position(p0, lo, hi);

        final class ArrayTraverser extends AbstractBase<long[]> implements I64.Traverser.Duplex.Indexed {

            private ArrayTraverser(long[] data, int lo, int hi, int p0) {
                super(data, lo, hi, p0);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNext(final I64.Consumer action) {
                if (px >= hi) return false;
                action.accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNextIndexed(final Ix1D<I64.Consumer> action) {
                if (px >= hi) return false;
                action.index(px).accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrev(final I64.Consumer action) {
                if (px <= lo) return false;
                action.accept(data[--px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrevIndexed(final Ix1D<I64.Consumer> action) {
                if (px <= lo) return false;
                action.index(--px).accept(data[px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void forNext(final I64.Consumer action) {
                try {
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) invoke(action, --px, data[px]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        invoke(action, --px, data[px]);
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
        return new ArrayTraverser(data, lo, hi, p0);
    }

    // ----------------------------------------------------------

    /// F32 TRAVERSAL.

    public static F32.Traverser.Duplex.Indexed single(final float value) {
        return of(new float[] { value });
    }

    public static F32.Traverser.Duplex.Indexed of(final float[] data) {
        return of(data, 0, data.length, 0);
    }

    public static F32.Traverser.Duplex.Indexed of(final float[] data, final int p0) {
        return of(data, 0, data.length, p0);
    }

    public static F32.Traverser.Duplex.Indexed of(final float[] data, final int lo, final int hi) {
        return of(data, lo, hi, lo);
    }

    public static F32.Traverser.Duplex.Indexed of(final float[] data, final int lo, final int hi, final int p0) {
        Assert.range(lo, hi, data.length);
        Assert.position(p0, lo, hi);

        final class ArrayTraverser extends AbstractBase<float[]> implements F32.Traverser.Duplex.Indexed {

            private ArrayTraverser(float[] data, int lo, int hi, int p0) {
                super(data, lo, hi, p0);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNext(final F32.Consumer action) {
                if (px >= hi) return false;
                action.accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNextIndexed(final Ix1D<F32.Consumer> action) {
                if (px >= hi) return false;
                action.index(px).accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrev(final F32.Consumer action) {
                if (px <= lo) return false;
                action.accept(data[--px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrevIndexed(final Ix1D<F32.Consumer> action) {
                if (px <= lo) return false;
                action.index(--px).accept(data[px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void forNext(final F32.Consumer action) {
                try {
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) invoke(action, --px, data[px]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        invoke(action, --px, data[px]);
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
        return new ArrayTraverser(data, lo, hi, p0);
    }

    // ----------------------------------------------------------

    /// F64 TRAVERSAL.

    public static F64.Traverser.Duplex.Indexed single(final double value) {
        return of(new double[] { value });
    }

    public static F64.Traverser.Duplex.Indexed of(final double[] data) {
        return of(data, 0, data.length, 0);
    }

    public static F64.Traverser.Duplex.Indexed of(final double[] data, final int p0) {
        return of(data, 0, data.length, p0);
    }

    public static F64.Traverser.Duplex.Indexed of(final double[] data, final int lo, final int hi) {
        return of(data, lo, hi, lo);
    }

    public static F64.Traverser.Duplex.Indexed of(final double[] data, final int lo, final int hi, final int p0) {
        Assert.range(lo, hi, data.length);
        Assert.position(p0, lo, hi);
        
        final class ArrayTraverser extends AbstractBase<double[]> implements F64.Traverser.Duplex.Indexed {

            private ArrayTraverser(double[] data, int lo, int hi, int p0) {
                super(data, lo, hi, p0);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNext(final F64.Consumer action) {
                if (px >= hi) return false;
                action.accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryNextIndexed(final Ix1D<F64.Consumer> action) {
                if (px >= hi) return false;
                action.index(px).accept(data[px++]);
                return true; //px < hi;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrev(final F64.Consumer action) {
                if (px <= lo) return false;
                action.accept(data[--px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean tryPrevIndexed(final Ix1D<F64.Consumer> action) {
                if (px <= lo) return false;
                action.index(--px).accept(data[px]);
                return true; //px > lo;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void forNext(final F64.Consumer action) {
                try {
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return;
                    }
                    final var data = this.data;
                    while (px < hi) invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return;
                    }
                    final var data = this.data;
                    while (px > lo) invoke(action, --px, data[px]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        action.onAccept(data[px++]);
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
                    int px; final int hi;
                    if ((px = this.px) >= (hi = this.hi)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px < hi && state[0] == State.S_INIT) {
                        invoke(action, px, data[px++]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        action.onAccept(data[--px]);
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
                    int px; final int lo;
                    if ((px = this.px) <= (lo = this.lo)) {
                        return State.S_COMPLETED;
                    }
                    final int[]   state   = { State.S_INIT };
                    final Control control = () -> state[0] |= State.S_EXITED;
                    final var     action  = driver.onApply(control);
                    final var     data    = this.data;
                    while (px > lo && state[0] == State.S_INIT) {
                        invoke(action, --px, data[px]);
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
        return new ArrayTraverser(data, lo, hi, p0);
    }

    // ----------------------------------------------------------
    //  TRAVERSAL.ABSTRACT-BASE
    // ----------------------------------------------------------

    private static abstract class AbstractBase<T> implements Range, ICursor.Resettable {
        protected final T data;
        protected final int lo;
        protected final int hi;
        protected final int p0;
        protected int px;

        protected AbstractBase(T data, int lo, int hi, int p0) {
            this.data = data;
            this.lo = lo;
            this.hi = hi;
            this.p0 = p0;
            this.px = p0;
        }

        @Override public long lo() { return lo; }
        @Override public long hi() { return hi; }
        @Override public boolean reset() {
            this.px = p0;
            return true;
        }
    }
}
