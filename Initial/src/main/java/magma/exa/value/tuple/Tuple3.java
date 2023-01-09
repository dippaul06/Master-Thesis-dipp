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
import magma.exa.adt.typelevel.nat.$3;
import magma.exa.control.exception.Exceptions;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.control.functor.NFunctor;
import magma.exa.value.adt.product.Product;
import magma.exa.value.adt.product.Product3;

/**
 * A tuple of 3 heterogeneously typed components;
 * canonical representation of a {@link Product3}.
 *
 * @param <A> type of 1st component.
 * @param <B> type of 2nd component.
 * @param <C> type of 3rd component.
 */
public class Tuple3<A, B, C> extends Tuple.AbstractBase<$3>

        implements Product3.Assignable<A, B, C, Tuple3<A, B, C>>,

        NFunctor.Of3<A, B, C, Tuple3<?, ?, ?>>,

        HList.Prependable<Tuple3<A, B, C>>,

        HList.Appendable<Tuple3<A, B, C>>,

        HList.Cell<A, Tuple2<B, C>>
{
    /**
     * The 1st tuple component.
     **/
    public final A _1;

    /**
     * The 2nd tuple component.
     **/
    public final B _2;

    /**
     * The 3rd tuple component.
     **/
    public final C _3;

    // ----------------------------------------------------------

    /**
     * Constructs a tuple from the given values.
     */
    public Tuple3(final A _1, final B _2, final C _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    /**
     * Constructs tuple from the given product.
     */
    public Tuple3(final Product3<? extends A, ? extends B, ? extends C> product) {
        this._1 = product._1();
        this._2 = product._2();
        this._3 = product._3();
    }

    /**
     * Copy constructor.
     */
    public Tuple3(final Tuple3<A, B, C> tuple) {
        this._1 = tuple._1;
        this._2 = tuple._2;
        this._3 = tuple._3;
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

    /**
     * Returns the 3rd component.
     */
    @Override
    public final C _3() {
        return _3;
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
    public final Tuple2<B, C> tail() {
        return Tuple.of(_2, _3);
    }

    /**
     * Returns this tuple as a corresponding heterogeneous list.
     */
    @Override
    public final Cell<A, Cell<B, Single<C>>> hlist() {
        return HList.of(_1, _2, _3);
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
            case 3 -> _3;
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
    public final Tuple3<A, B, C> _1(final A a) {
        return Tuple.of(a, _2, _3);
    }

    /**
     * Returns a tuple with the given value in the 2nd component.
     *
     * @param b value of the 2nd component.
     * @return new tuple.
     */
    @Override
    public final Tuple3<A, B, C> _2(final B b) {
        return Tuple.of(_1, b, _3);
    }

    /**
     * Returns a tuple with the given value in the 3rd component.
     *
     * @param c value of the 3rd component.
     * @return new tuple.
     */
    @Override
    public final Tuple3<A, B, C> _3(final C c) {
        return Tuple.of(_1, _2, c);
    }

    // ----------------------------------------------------------

    /**
     * Constructs a tuple by prepending the given value
     * to the components of this tuple.
     *
     * @param value to be prepended.
     * @param <Z>   type of value to be prepended.
     * @return tuple of type {@link Tuple4}<Z, A, B, C>.
     */
    @Override
    public final <Z> Tuple4<Z, A, B, C> prepend(final Z value) {
        return Tuple.of(value, _1, _2, _3);
    }

    /**
     * Constructs a tuple by appending the given value
     * to the components of this tuple.
     *
     * @param value to be appended.
     * @param <D>   type of value to be appended.
     * @return tuple of type {@link Tuple4}<A, B, C, D>.
     */
    @Override
    public final <D> Tuple4<A, B, C, D> append(final D value) {
        return Tuple.of(_1, _2, _3, value);
    }

    // ----------------------------------------------------------

    /**
     * Returns a tuple with mapped 1st component {@code A -> I}
     * by applying the given function.
     *
     * @param f1 mapping function of the 1st component.
     * @return new {@link Tuple8}<I, B, C>.
     */
    @Override
    public <I> Tuple3<I, B, C> map1(final Fn1<? super A, ? extends I> f1) {
        return Tuple.of(f1.apply(_1), _2, _3);
    }

    /**
     * Returns a tuple with mapped 2nd component {@code B -> J}
     * by applying the given function.
     *
     * @param f2 mapping function of the 2nd component.
     * @return new {@link Tuple8}<A, J, C>.
     */
    @Override
    public <J> Tuple3<A, J, C> map2(final Fn1<? super B, ? extends J> f2) {
        return Tuple.of(_1, f2.apply(_2), _3);
    }

    /**
     * Returns a tuple with mapped 3rd component {@code C -> K}
     * by applying the given function.
     *
     * @param f3 mapping function of the 3rd component.
     * @return new {@link Tuple8}<A, B, K>.
     */
    @Override
    public <K> Tuple3<A, B, K> map3(final Fn1<? super C, ? extends K> f3) {
        return Tuple.of(_1, _2, f3.apply(_3));
    }

    // ----------------------------------------------------------

    /**
     * Returns a tuple with mapped components by applying all given functions.
     *
     * @param f1 mapping function of the 1st component.
     * @param f2 mapping function of the 2nd component.
     * @param f3 mapping function of the 3rd component.
     * @return new {@link Tuple3}<I, J, K>.
     */
    @Override
    public <I, J, K>
    Tuple3<I, J, K> map(final Fn1<? super A, ? extends I> f1,
                        final Fn1<? super B, ? extends J> f2,
                        final Fn1<? super C, ? extends K> f3) {
        try {
            return Tuple.of(
                    f1.onApply(_1),
                    f2.onApply(_2),
                    f3.onApply(_3)
            );
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
        return new Object[] { _1, _2, _3 };
    }

    // ----------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final Product3<?, ?, ?> that))
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