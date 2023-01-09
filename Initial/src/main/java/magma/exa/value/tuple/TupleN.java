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

package magma.exa.value.tuple;

import java.util.StringJoiner;

import magma.exa.adt.generic.HList;
import magma.exa.adt.typelevel.nat.$N;
import magma.exa.base.Hash;
import magma.exa.base.Narrow;
import magma.exa.control.exception.TODO;

/**
 * A tuple cell containing a typed slot whose index is greater than 8.
 *
 * @param <V> type of head value.
 * @param <R> type of rest tuple chain.
 */
public class TupleN<V, R extends Tuple<?>> extends Tuple.AbstractBase<$N>


        implements

        HList.Prependable<TupleN<V, R>>,

        HList.Cell<V, R> {

    /**
     * The n-th slot value.
     */
    public final V head;

    /**
     * The linked rest of the tuple.
     */
    public final R tail;

    /**
     * Constructs tuple-cell containing
     *
     * @param head value contained in this tuple-cell.
     * @param tail link to tuple.
     */
    public TupleN(final V head, final R tail) {
        this.head = head;
        this.tail = tail;
    }

    // ----------------------------------------------------------

    @Override
    public $N arity() {
        throw TODO.notImplemented();
    }

    // ----------------------------------------------------------

    /**
     * Returns the data of the head element in this heterogeneous tuple.
     */
    @Override
    public final V head() {
        return head;
    }

    /**
     * Returns the tail of this heterogeneous tuple list.
     */
    @Override
    public final R tail() {
        return tail;
    }

    /**
     * Returns this tuple as a corresponding heterogeneous list.
     */
    @Override
    public HList.Cell<V, R> hlist() {
        return this;
    }

    // ----------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Object at(final long idx) {
        throw TODO.notImplemented();
    }

    // ----------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public <A> TupleN<A, TupleN<V, R>> prepend(final A next) {
        return new TupleN<>(next, this);
    }

    // ----------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public final Object[] toArray() {
        Cell<?, ?> cell = this;
        final var out = new Object[length()];
        for (int i = 0; cell != nil; ++i) {
            out[i++] = cell.head();
            cell = Narrow.cast(cell.tail());
        }
        return out;
    }

    // ----------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (o instanceof final Cell<?, ?> that) {
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

    // ----------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final var sj = new StringJoiner(", ", "(", ")");
        Cell<?, ?> cell = this;
        while (cell != nil) {
            sj.add(String.valueOf(cell.head()));
            cell = Narrow.cast(cell.tail());
        }
        return sj.toString();
    }
}
