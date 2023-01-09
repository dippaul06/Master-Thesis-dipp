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
import magma.exa.control.adt.traversal.ITraverser;
import magma.exa.control.function.Fn1;
import magma.exa.data.index.Ix1D;

import java.util.Iterator;

/**
 * Encapsulates the unidirectional traversal of an underlying source of values.
 * <p>
 * The traverser is designed as an 'internal iterator' that accepts
 * a (higher order) action provided by the traversal client.
 *
 * @param <A> type of value.
 */
public interface Traverser<A> extends ITraverser<A, Fn1.Consumer<? super A>> {

    // ----------------------------------------------------------
    //  TRAVERSER.INDEXED
    // ----------------------------------------------------------

    /**
     * The Indexed Traverser refines {@link Traverser} by providing
     * an additional indexed variant for each traverser operation.
     *
     * @param <A> type of value.
     */
    interface Indexed<A> extends ITraverser.Indexed<A, Fn1.Consumer<? super A>>, Traverser<A> {

        /**
         * {@inheritDoc}
         */
        @Override
        default boolean tryNext(final Fn1.Consumer<? super A> action) {
            return tryNextIndexed(ICursor.lift1D(action));
        }
    }

    // ----------------------------------------------------------
    //  TRAVERSER.DUPLEX
    // ----------------------------------------------------------

    /**
     * The Duplex Traverser refines {@link Traverser} with the ability to
     * additionally traverse in the reverse direction.
     *
     * @param <A> type of value.
     */
    interface Duplex<A> extends ITraverser.Duplex<A, Fn1.Consumer<? super A>>, Traverser<A> {

        // ----------------------------------------------------------
        //  TRAVERSER.DUPLEX.INDEXED
        // ----------------------------------------------------------

        /**
         * The Indexed Duplex Traverser refines {@link Traverser.Duplex} by providing an
         * additional indexed variant for each traverser operation in reverse direction.
         *
         * @param <A> type of value.
         */
        interface Indexed<A> extends ITraverser.Duplex.Indexed<A, Fn1.Consumer<? super A>>,
                Traverser.Indexed<A>, Traverser.Duplex<A> {

            /**
             * {@inheritDoc}
             */
            @Override
            default boolean tryPrev(final Fn1.Consumer<? super A> action) {
                return tryPrevIndexed(ICursor.lift1D(action));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Iterator<A> iterator() {
        // Weak iterator contract...
        final class It implements Iterator<A>, Fn1.Consumer<A> {
            private A next;
            @Override public boolean hasNext() { return tryNext(this); }
            @Override public A       next()    { return next; }
            @Override public void onAccept(final A next) {
                this.next = next;
            }
        }
        return new It();
    }

    // ----------------------------------------------------------
    //  TRAVERSER.EMPTY
    // ----------------------------------------------------------

    /**
     * The empty traverser.
     */
    static <A> Duplex.Indexed<A> empty() {
        return Force.cast(Empty.traverser);
    }

    /**
     * The empty traverser type.
     */
    enum Empty implements Duplex.Indexed<Object> {
        traverser;
        public boolean  tryNext(Fn1.Consumer<? super Object> a) { Assert.notNull(a); return false; }
        public boolean  tryPrev(Fn1.Consumer<? super Object> a) { Assert.notNull(a); return false; }
        public void     forNext(Fn1.Consumer<? super Object> a) { Assert.notNull(a); }
        public void     forPrev(Fn1.Consumer<? super Object> a) { Assert.notNull(a); }
        public long     whileNext(Until<Control, Fn1.Consumer<? super Object>> d) { Assert.notNull(d); return State.S_INIT; }
        public long     whilePrev(Until<Control, Fn1.Consumer<? super Object>> d) { Assert.notNull(d); return State.S_INIT; }
        public void     forNextIndexed(Ix1D<Fn1.Consumer<? super Object>> a) { Assert.notNull(a); }
        public void     forPrevIndexed(Ix1D<Fn1.Consumer<? super Object>> a) { Assert.notNull(a); }
        public boolean  tryNextIndexed(Ix1D<Fn1.Consumer<? super Object>> a) { Assert.notNull(a); return false; }
        public boolean  tryPrevIndexed(Ix1D<Fn1.Consumer<? super Object>> a) { Assert.notNull(a); return false; }
        public long     whileNextIndexed(Until<Control, Ix1D<Fn1.Consumer<? super Object>>> d) { Assert.notNull(d); return State.S_INIT; }
        public long     whilePrevIndexed(Until<Control, Ix1D<Fn1.Consumer<? super Object>>> d) { Assert.notNull(d); return State.S_INIT; }
    }
}
