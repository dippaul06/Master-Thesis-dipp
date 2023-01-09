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

import magma.exa.adt.generic.HList;
import magma.exa.adt.typelevel.nat.$1;
import magma.exa.control.exception.Exceptions;
import magma.exa.control.function.Fn1;
import magma.exa.control.functor.NFunctor;
import magma.exa.value.adt.product.Product;
import magma.exa.value.adt.product.Product1;

/**
 * A tuple of a single component; canonical representation
 * of a {@link Product1}.
 *
 * @param <A> type of component.
 */
public class Tuple1<A> extends Tuple.AbstractBase<$1>

        implements Product1.Assignable<A, Tuple1<A>>,

        NFunctor.Of1<A, Tuple1<?>>,

        HList.Prependable<Tuple1<A>>,

        HList.Appendable<Tuple1<A>>,

        HList.Cell<A, HList.Cell.NIL>
{
    /**
     * The 1st tuple component.
     **/
    public final A _1;

    // ----------------------------------------------------------

    /**
     * Constructs a tuple from the given value.
     */
    public Tuple1(final A _1) {
        this._1 = _1;
    }

    /**
     * Constructs tuple from the given product.
     */
    public Tuple1(final Product1<? extends A> product) {
        this._1 = product._1();
    }

    /**
     * Copy constructor.
     */
    public Tuple1(final Tuple1<A> tuple) {
        this._1 = tuple._1;
    }

    // ----------------------------------------------------------

    /**
     * Returns the 1st component.
     */
    @Override
    public final A _1() {
        return _1;
    }

    // ----------------------------------------------------------

    /**
     * Returns the data of the head component in this heterogeneous tuple.
     */
    @Override
    public final A head() {
        return _1;
    }

    /**
     * Returns {@link HList#nil} as the right-most end.
     */
    @Override
    public final NIL tail() {
        return HList.nil;
    }

    /**
     * Returns this tuple as a corresponding heterogeneous list.
     */
    @Override
    public final Single<A> hlist() {
        return HList.of(_1);
    }

    // ----------------------------------------------------------

    /**
     * Returns the value of the component located at the given index.
     *
     * @param idx index of the component to be accessed.
     * @return value of the accessed component.
     */
    @Override
    public final Object at(final long idx) {
        if (idx == 1) return _1;
        throw Exceptions.outOfBounds("illegal index: %d", idx);
    }

    // ----------------------------------------------------------

    /**
     * Returns a tuple with the given value in the 1st component.
     *
     * @param a value of the 1st component.
     * @return new tuple.
     */
    @Override
    public final Tuple1<A> _1(final A a) {
        return Tuple.of(a);
    }

    // ----------------------------------------------------------

    /**
     * Constructs a tuple by prepending the given value
     * to the components of this tuple.
     *
     * @param value to be prepended.
     * @param <Z>   type of value to be prepended.
     * @return tuple of type {@link Tuple2}<Z, A>.
     */
    @Override
    public final <Z> Tuple2<Z, A> prepend(final Z value) {
        return Tuple.of(value, _1);
    }

    /**
     * Constructs a tuple by appending the given value
     * to the components of this tuple.
     *
     * @param value to be appended.
     * @param <B>   type of value to be appended.
     * @return tuple of type {@link Tuple2}<A, B>.
     */
    @Override
    public final <B> Tuple2<A, B> append(final B value) {
        return Tuple.of(_1, value);
    }

    // ----------------------------------------------------------

    /**
     * Returns a tuple with mapped 1st component {@code A -> I}
     * by applying the given function.
     *
     * @param f1 mapping function of the 1st component.
     * @return new {@link Tuple8}<I>.
     */
    @Override
    public <I> Tuple1<I> map(final Fn1<? super A, ? extends I> f1) {
        return Tuple.of(f1.apply(_1));
    }

    // ----------------------------------------------------------

    /**
     * Returns an array consisting of all components
     * contained in this tuple in the given order.
     */
    @Override
    public final Object[] toArray() {
        return new Object[] { _1 };
    }

    // ----------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final Product1<?> that))
            return false;
        return Product.equals(this, that);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Product.hashCode(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return Product.toString(this);
    }
}