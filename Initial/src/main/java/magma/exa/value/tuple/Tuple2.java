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
import magma.exa.adt.typelevel.nat.$2;
import magma.exa.control.exception.Exceptions;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.control.functor.NFunctor;
import magma.exa.value.adt.product.Product;
import magma.exa.value.adt.product.Product2;

/**
 * A tuple of 2 heterogeneously typed components;
 * canonical representation of a {@link Product2}.
 *
 * @param <A> type of 1st component.
 * @param <B> type of 2nd component.
 */
public class Tuple2<A, B> extends Tuple.AbstractBase<$2>

        implements Product2.Assignable<A, B, Tuple2<A, B>>,

        NFunctor.Of2<A, B, Tuple2<?, ?>>,

        HList.Prependable<Tuple2<A, B>>,

        HList.Appendable<Tuple2<A, B>>,

        HList.Cell<A, Tuple1<B>>
{
    /**
     * The 1st tuple component.
     **/
    public final A _1;

    /**
     * The 2nd tuple component.
     **/
    public final B _2;

    // ----------------------------------------------------------

    /**
     * Constructs a tuple from the given values.
     */
    public Tuple2(final A _1, final B _2) {
        this._1 = _1;
        this._2 = _2;
    }

    /**
     * Constructs tuple from the given product.
     */
    public Tuple2(final Product2<? extends A, ? extends B> product) {
        this._1 = product._1();
        this._2 = product._2();
    }

    /**
     * Copy constructor.
     */
    public Tuple2(final Tuple2<A, B> tuple) {
        this._1 = tuple._1;
        this._2 = tuple._2;
    }

    // ----------------------------------------------------------

    /**
     * Returns the 1st component.
     */
    @Override
    public final A _1() {
        return _1;
    }

    /**
     * Returns the 2nd component.
     */
    @Override
    public final B _2() {
        return _2;
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
     * Returns the tail of this heterogeneous tuple list.
     */
    @Override
    public final Tuple1<B> tail() {
        return Tuple.of(_2);
    }

    /**
     * Returns this tuple as a corresponding heterogeneous list.
     */
    @Override
    public final Cell<A, Single<B>> hlist() {
        return HList.of(_1, _2);
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
        return switch ((int) idx) {
            case 1 -> _1;
            case 2 -> _2;
            default -> throw Exceptions.outOfBounds("illegal index: %d", idx);
        };
    }

    // ----------------------------------------------------------

    /**
     * Returns a tuple with the given value in the 1st component.
     *
     * @param a value of the 1st component.
     * @return new tuple.
     */
    @Override
    public final Tuple2<A, B> _1(final A a) {
        return Tuple.of(a, _2);
    }

    /**
     * Returns a tuple with the given value in the 2nd component.
     *
     * @param b value of the 2nd component.
     * @return new tuple.
     */
    @Override
    public final Tuple2<A, B> _2(final B b) {
        return Tuple.of(_1, b);
    }

    // ----------------------------------------------------------

    /**
     * Constructs a tuple by prepending the given value
     * to the components of this tuple.
     *
     * @param value to be prepended.
     * @param <Z>   type of value to be prepended.
     * @return tuple of type {@link Tuple3}<Z, A, B, C>.
     */
    @Override
    public final <Z> Tuple3<Z, A, B> prepend(final Z value) {
        return Tuple.of(value, _1, _2);
    }

    /**
     * Constructs a tuple by appending the given value
     * to the components of this tuple.
     *
     * @param value to be appended.
     * @param <C>   type of value to be appended.
     * @return tuple of type {@link Tuple3}<A, B, C>.
     */
    @Override
    public final <C> Tuple3<A, B, C> append(final C value) {
        return Tuple.of(_1, _2, value);
    }

    // ----------------------------------------------------------

    /**
     * Returns a tuple with mapped 1st component {@code A -> I}
     * by applying the given function.
     *
     * @param f1 mapping function of the 1st component.
     * @return new {@link Tuple8}<I, B>.
     */
    @Override
    public <I> Tuple2<I, B> map1(final Fn1<? super A, ? extends I> f1) {
        return Tuple.of(f1.apply(_1), _2);
    }

    /**
     * Returns a tuple with mapped 2nd component {@code B -> J}
     * by applying the given function.
     *
     * @param f2 mapping function of the 2nd component.
     * @return new {@link Tuple8}<A, J>.
     */
    @Override
    public <J> Tuple2<A, J> map2(final Fn1<? super B, ? extends J> f2) {
        return Tuple.of(_1, f2.apply(_2));
    }

    // ----------------------------------------------------------

    /**
     * Returns a tuple with mapped components by applying all given functions.
     *
     * @param f1 mapping function of the 1st component.
     * @param f2 mapping function of the 2nd component.
     * @return new {@link Tuple2}<I, J>.
     */
    @Override
    public <I, J>
    Tuple2<I, J> map(final Fn1<? super A, ? extends I> f1,
                     final Fn1<? super B, ? extends J> f2) {
        try {
            return Tuple.of(f1.onApply(_1), f2.onApply(_2));
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns an array consisting of all components
     * contained in this tuple in the given order.
     */
    @Override
    public final Object[] toArray() {
        return new Object[] { _1, _2 };
    }

    // ----------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final Product2<?, ?> that))
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