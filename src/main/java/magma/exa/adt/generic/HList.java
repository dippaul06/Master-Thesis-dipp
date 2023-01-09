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

package magma.exa.adt.generic;

import magma.exa.adt.mixin.Mixin;
import magma.exa.base.Force;
import magma.exa.base.Hash;
import magma.exa.base.Narrow;
import magma.exa.control.function.Fn1;
import magma.exa.control.function.Fn2;
import magma.exa.value.Unit;

import java.util.StringJoiner;

import static magma.exa.adt.generic.HList.Cell.*;
import static magma.exa.adt.generic.HList.Cell.cons;
import static magma.exa.adt.generic.HList.Cell.single;

/**
 * A statically typed heterogeneous list container which provide type
 * safety for arbitrary depths via a linear recursive type signature.
 * <p>
 * A {@link HList} is essentially a single linked list encoded in the
 * type signature via recursively nested {@link Cell}s. A Cell
 * is a pair of a {@link Cell#head()} and {@link Cell#tail()} slot.
 * The head slot carries a value of type {@code A}, and the tail links
 * either a nested Cell, or {@link NIL}.
 */
public interface HList extends Mixin.Length {

    /**
     * Constant of the right-most end of a heterogeneous list.
     */
    NIL nil = NIL.cell;

    /**
     * Constructs a heterogeneous list with the given value.
     */
    static <A> Single<A> of(final A _1) {
        return Cell.single(_1);
    }

    /**
     * Constructs a heterogeneous list with the given 2 values.
     */
    static <A, B>
    Cell<A, Single<B>>
    of(final A _1, final B _2) {
        return cons(_1, single(_2));
    }

    /**
     * Constructs a heterogeneous list with the given 3 values.
     */
    static <A, B, C>
    Cell<A, Cell<B, Single<C>>>
    of(final A _1, final B _2, final C _3) {
        return cons(_1, cons(_2, single(_3)));
    }

    /**
     * Constructs a heterogeneous list with the given 4 values.
     */
    static <A, B, C, D>
    Cell<A, Cell<B, Cell<C, Single<D>>>>
    of(final A _1, final B _2, final C _3, final D _4) {
        return cons(_1, cons(_2, cons(_3, single(_4))));
    }

    /**
     * Constructs a heterogeneous list with the given 5 values.
     */
    static <A, B, C, D, E>
    Cell<A, Cell<B, Cell<C, Cell<D, Single<E>>>>>
    of(final A _1, final B _2, final C _3, final D _4, final E _5) {
        return cons(_1, cons(_2, cons(_3, cons(_4, single(_5)))));
    }

    /**
     * Constructs a heterogeneous list with the given 6 values.
     */
    static <A, B, C, D, E, F>
    Cell<A, Cell<B, Cell<C, Cell<D, Cell<E, Single<F>>>>>>
    of(final A _1, final B _2, final C _3, final D _4, final E _5, final F _6) {
        return cons(_1, cons(_2, cons(_3, cons(_4, cons(_5, single(_6))))));
    }

    /**
     * Constructs a heterogeneous list with the given 7 values.
     */
    static <A, B, C, D, E, F, G>
    Cell<A, Cell<B, Cell<C, Cell<D, Cell<E, Cell<F, Single<G>>>>>>>
    of(final A _1, final B _2, final C _3, final D _4, final E _5, final F _6, final G _7) {
        return cons(_1, cons(_2, cons(_3, cons(_4, cons(_5, cons(_6, single(_7)))))));
    }

    /**
     * Constructs a heterogeneous list with the given 8 values.
     */
    static <A, B, C, D, E, F, G, H>
    Cell<A, Cell<B, Cell<C, Cell<D, Cell<E, Cell<F, Cell<G, Single<H>>>>>>>>
    of(final A _1, final B _2, final C _3, final D _4, final E _5, final F _6, final G _7, final H _8) {
        return cons(_1, cons(_2, cons(_3, cons(_4, cons(_5, cons(_6, cons(_7, single(_8))))))));
    }

    // ----------------------------------------------------------
    //  HLIST.PREPENDER
    // ----------------------------------------------------------

    /**
     * Mixin type that declares a prepend-constructor for
     * a heterogeneous list of type {@code L}.
     *
     * @param <L> type of primary {@link HList}.
     */
    @Mixin interface Prependable<L extends HList & Prependable<L>> {

        /**
         * Constructs a cell with the given head prepended to this list of type {@code L}.
         *
         * @param head of the cell to be prepended.
         * @param <A>   type of prepended data head.
         * @return new cell that prepends this list.
         */
        <A> Cell<A, ? extends L> prepend(A head);
    }

    // ----------------------------------------------------------
    //  HLIST.APPENDABLE
    // ----------------------------------------------------------

    /**
     * Mixin type that declares a prepend-constructor for
     * a heterogeneous list of type {@code L}.
     *
     * @param <L> type of primary {@link HList}.
     */
    @Mixin interface Appendable<L extends HList & Appendable<L>> {

        /**
         * Constructs a cell with the given head appended to this list of type {@code L}.
         *
         * @param head of the cell to be appended.
         * @param <A>   type of appended data head.
         * @return new cell that appends this list.
         */
        <A> Cell<?, ?> append(A head);
    }

    // ----------------------------------------------------------
    //  HLIST.CELL
    // ----------------------------------------------------------

    /**
     * General type representing the most basic non-empty cell of a HList.
     * A Cons cell is a product of two slots, the 1st slot {@link #head()}
     * carries a value of type {@code A}, and the 2nd slot {@link #tail()}
     * links either the next cons cell in the list, or else {@link #nil},
     * which designates the end of the list, e.g. [A, [B, [C, nil]]].
     *
     * @param <A> type of data head.
     * @param <L> type of list tail.
     */
    interface Cell<A, L extends HList> extends HList {

        /**
         * Returns the associated data value.
         */
        A head();

        /**
         * Returns the linked list tail.
         */
        L tail();

        // ----------------------------------------------------------
        //  HLIST.CELL.SELF
        // ----------------------------------------------------------

        /**
         * Specialization to be used whenever recursive cell type definitions
         * and subtyping are used.
         *
         * @param <A> type of head data.
         * @param <L> type of tail list.
         * @param <T> F-bound parameter.
         */
        interface Self<A, L extends HList, T extends Self<A, L, T>>
                extends Mixin.Self<T>, Cell<A, L> {
        }

        // ----------------------------------------------------------
        //  HLIST.CELL.BASE
        // ----------------------------------------------------------

        /**
         * Base implementation of a list cell.
         *
         * @param <A> type of head data.
         * @param <L> type of list tail.
         */
        class Base<A, L extends HList> implements Cell<A, L> {

            /** Head data of this cell. */
            public final A head;

            /** Tail list of this cell. */
            public final L tail;

            /** Constructs a cell from given data head and tail list. */
            public Base(final A head, final L tail) {
                this.head = head;
                this.tail = tail;
            }

            /**
             * Returns the associated data value.
             */
            @Override
            public final A head() {
                return head;
            }

            /**
             * Returns the linked list tail.
             */
            @Override
            public final L tail() {
                return tail;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean equals(final Object other) {
                if (other instanceof Cell<?, ?> that) {
                    return this.head.equals(that.head()) &&
                           this.tail.equals(that.tail());
                }
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int hashCode() {
                return Hash.code(head, tail);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                final var sj = new StringJoiner(" :: ", "⟨", "⟩");
                Cell<?, ?> cell = this;
                while (cell != nil) {
                    sj.add(String.valueOf(cell.head()));
                    cell = Narrow.cast(cell.tail());
                }
                return sj.toString();
            }
        }

        // ----------------------------------------------------------

        /**
         * Returns Swap the data values between the linked cell pair.
         *
         * @param <A>  type of the 1st cell data value.
         * @param <B>  type of the 2nd cell data value.
         * @param <L>  type of the list tail.
         * @return cell pair with swapped data values.
         */
        static <A, B, L extends HList>
        Cell<A, ? extends Cell<B, L>> swap(final Cell<B, ? extends Cell<A, L>> cell) {
            return Cell.<A, B, L> swap().apply(cell);
        }

        /**
         * Returns Swap the data values between the linked cell pair.
         *
         * @param <A>  type of the 1st cell data value.
         * @param <B>  type of the 2nd cell data value.
         * @param <L>  type of the list tail.
         * @return cell pair with swapped data values.
         */
        static <A, B, L extends HList>
        Fn1<Cell<B, ? extends Cell<A, L>>, Cell<A, ? extends Cell<B, L>>> swap() {
            // Swap operator.
            final class Swap implements Fn1<Cell<B, ? extends Cell<A, L>>, Cell<A, ? extends Cell<B, L>>> {
                private static final Swap operator = new Swap();
                private Swap() { }

                /**
                 * Returns a new cell with swapped items.
                 *
                 * @param cell pair whose items are to be swapped.
                 * @return new cell with head and tail.
                 */
                @Override
                public Cell<A, ? extends Cell<B, L>> onApply(final Cell<B, ? extends Cell<A, L>> cell) {
                    final L tail = cell.tail().tail();
                    final A prev = cell.tail().head();
                    final B next = cell.head();
                    return cons(prev, cons(next, tail));
                }
            }
            return Swap.operator;
        }

        // ----------------------------------------------------------

        /**
         * Constructs a singleton cell with the given head.
         *
         * @param head of the cell to be constructed.
         * @param <A> type of head.
         * @return single cell.
         */
        static <A> Single<A> single(final A head) {
            return new Single<>(head);
        }

        /**
         * Constructs a cell with the given head and tail list.
         *
         * @param head of the cell to be constructed.
         * @param tail of the cell to be constructed.
         * @param <A> type of cell head.
         * @param <L> type of cell tail.
         * @return new cell.
         */
        static <A, L extends HList>
        Cell<A, L> cons(final A head, final L tail) {
            return Cell.<A, L> cons().apply(head, tail);
        }

        /**
         * Partially applies the cell constructor by passing the given head.
         *
         * @param head to be passed to
         * @param <A> type of cell head.
         * @param <L> type of cell tail.
         * @return cell constructor taking the cell tail.
         */
        static <A, L extends HList>
        Fn1<L, Cell<A, L>> cons(final A head) {
            return Cell.<A, L> cons().apply(head);
        }

        /**
         * Returns first-class cell constructor function.
         *
         * @param <A> type of cell head.
         * @param <L> type of cell tail.
         * @return cell constructor.
         */
        static <A, L extends HList>
        Fn2<A, L, Cell<A, L>> cons() {
            return Base::new;
        }
    }

    // ----------------------------------------------------------
    //  HLIST.SINGLETON
    // ----------------------------------------------------------

    /**
     * Specialized cell for a head without tail.
     *
     * @param <A> type of head.
     */
    final class Single<A> implements Cell<A, NIL> {

        /** Head data of this cell. */
        public final A head;

        private Single(final A head) {
            this.head = head;
        }

        /**
         * Returns the associated data value.
         */
        @Override
        public A head() {
            return head;
        }

        /**
         * Returns the linked list tail.
         */
        @Override
        public NIL tail() {
            return nil;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof Single<?> cell))
                return false;
            return head.equals(cell.head);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return Hash.code(head);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return String.format("⟨%s⟩", head);
        }
    }

    // ----------------------------------------------------------
    //  HLIST.CELL.NIL
    // ----------------------------------------------------------

    /**
     * Cell that represents the right-most end of a heterogeneous list.
     */
    enum NIL implements Cell<Unit, NIL> {
        cell;

        /**
         * {@inheritDoc}
         */
        @Override
        public int length() {
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Unit head() {
            return Unit.value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public NIL tail() {
            return cell;
        }
    }

    // ----------------------------------------------------------
    //  HLIST.INDEX
    // ----------------------------------------------------------

    /**
     * HList indexes representing a value at arbitrary depth in some
     * compatible HList. HList compatibility requires identical element
     * types up to and including the target element, but thereafter is
     * unconstrained in length and element type.
     *
     * @param <A> target element type
     * @param <L> type of compatible HList
     */
    abstract class Index<A, L extends Cell<?, ?>> {
        private Index() {}

        /**
         * Nest this index deeper by one element.
         *
         * @param <B> the type of the preceding element
         * @return an index at the same Target, nested one level deep
         */
        public final <B> Index<A, Cell<B, ? extends L>> tail() {
            return new N<>(this);
        }

        /**
         * Retrieve the value at this index in hList.
         *
         * @param list the hList
         * @return the value at this index
         */
        public abstract A peek(L list);

        /**
         * Set a new value of the same type at this index in an {@link HList}.
         *
         * @param <M>        the inferred tail type of the HList
         * @param list      the HList
         * @param head the new value
         * @return the updated HList
         */
        public abstract <M extends L> M poke(M list, A head);

        // ----------------------------------------------------------

        private static final class Z<A> extends Index<A, Cell<A, ?>> {
            private static final Z<?> operator = new Z<>();
            private Z() { }

            @Override
            public A peek(final Cell<A, ?> list) {
                return list.head();
            }

            @Override
            @SuppressWarnings("unchecked")
            public <L extends Cell<A, ?>> L poke(final L list, final A head) {
                return (L) Cell.cons(head, Force.cast(list.tail()));
            }

            @SuppressWarnings("unchecked")
            public static <Target> Z<Target> operator() {
                return (Z<Target>) operator;
            }
        }

        // ----------------------------------------------------------

        private static final class N<A, H, L extends Cell<?, ?>, P extends Index<A, L>>
                extends Index<A, Cell<H, ? extends L>> {

            private final P prev;
            private N(final P prev) {
                this.prev = prev;
            }

            @Override
            public A peek(final Cell<H, ? extends L> list) {
                return prev.peek(list.tail());
            }

            @Override
            @SuppressWarnings("unchecked")
            public <M extends Cell<H, ? extends L>> M poke(final M list, final A head) {
                return (M) Cell.cons(list.head(), prev.poke(list.tail(), head));
            }
        }
    }

    /**
     * Returns an index for a cell with a data head of type {@code A}.
     *
     * @param <A> type of head.
     * @return root index.
     */
    static <A> Index<A, Cell<A, ?>> index() {
        return Index.Z.operator();
    }

    // ----------------------------------------------------------

    /**
     * Returns the length of this HList type.
     */
    @Override
    default int length() {
        int length = 0;
        for (Cell<?, ?> cell = Force.cast(this); cell != nil; ++length) {
            cell = Narrow.cast(cell.tail());
        }
        return length;
    }
}
