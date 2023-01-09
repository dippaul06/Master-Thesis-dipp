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
import magma.exa.adt.typelevel.nat.$4;
import magma.exa.control.exception.Exceptions;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.control.functor.NFunctor;
import magma.exa.value.adt.product.Product;
import magma.exa.value.adt.product.Product4;

/**
 * A tuple of 4 heterogeneously typed components;
 * canonical representation of a {@link Product4}.
 *
 * @param <A> type of 1st component.
 * @param <B> type of 2nd component.
 * @param <C> type of 3rd component.
 * @param <D> type of 4th component.
 */
public class Tuple4<A, B, C, D> extends Tuple.AbstractBase<$4>

        implements Product4.Assignable<A, B, C, D, Tuple4<A, B, C, D>>,

        NFunctor.Of4<A, B, C, D, Tuple4<?, ?, ?, ?>>,

        HList.Prependable<Tuple4<A, B, C, D>>,

        HList.Appendable<Tuple4<A, B, C, D>>,

        HList.Cell<A, Tuple3<B, C, D>>
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

    /**
     * The 4th tuple component.
     **/
    public final D _4;

    // ----------------------------------------------------------

    /**
     * Constructs a tuple from the given values.
     */
    public Tuple4(final A _1, final B _2, final C _3, final D _4) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
    }

    /**
     * Constructs tuple from the given product.
     */
    public Tuple4(final Product4<? extends A, ? extends B, ? extends C, ? extends D> product) {
        this._1 = product._1();
        this._2 = product._2();
        this._3 = product._3();
        this._4 = product._4();
    }

    /**
     * Copy constructor.
     */
    public Tuple4(final Tuple4<A, B, C, D> tuple) {
        this._1 = tuple._1;
        this._2 = tuple._2;
        this._3 = tuple._3;
        this._4 = tuple._4;
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

    /**
     * Returns the 4th component.
     */
    @Override
    public final D _4() {
        return _4;
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
    public final Tuple3<B, C, D> tail() {
        return Tuple.of(_2, _3, _4);
    }

    /**
     * Returns this tuple as a corresponding heterogeneous list.
     */
    @Override
    public final Cell<A, Cell<B, Cell<C, Single<D>>>> hlist() {
        return HList.of(_1, _2, _3, _4);
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
            case 4 -> _4;
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
    public final Tuple4<A, B, C, D> _1(final A a) {
        return Tuple.of(a, _2, _3, _4);
    }

    /**
     * Returns a tuple with the given value in the 2nd component.
     *
     * @param b value of the 2nd component.
     * @return new tuple.
     */
    @Override
    public final Tuple4<A, B, C, D> _2(final B b) {
        return Tuple.of(_1, b, _3, _4);
    }

    /**
     * Returns a tuple with the given value in the 3rd component.
     *
     * @param c value of the 3rd component.
     * @return new tuple.
     */
    @Override
    public final Tuple4<A, B, C, D> _3(final C c) {
        return Tuple.of(_1, _2, c, _4);
    }

    /**
     * Returns a tuple with the given value in the 4th component.
     *
     * @param d value of the 4th component.
     * @return new tuple.
     */
    @Override
    public final Tuple4<A, B, C, D> _4(final D d) {
        return Tuple.of(_1, _2, _3, d);
    }

    // ----------------------------------------------------------

    /**
     * Constructs a tuple by prepending the given value
     * to the components of this tuple.
     *
     * @param value to be prepended.
     * @param <Z>   type of value to be prepended.
     * @return tuple of type {@link Tuple5}<Z, A, B, C, D>.
     */
    @Override
    public final <Z> Tuple5<Z, A, B, C, D> prepend(final Z value) {
        return Tuple.of(value, _1, _2, _3, _4);
    }

    /**
     * Constructs a tuple by appending the given value
     * to the components of this tuple.
     *
     * @param value to be appended.
     * @param <E>   type of value to be appended.
     * @return tuple of type {@link Tuple5}<A, B, C, D, E>.
     */
    @Override
    public final <E> Tuple5<A, B, C, D, E> append(final E value) {
        return Tuple.of(_1, _2, _3, _4, value);
    }

    // ----------------------------------------------------------

    /**
     * Returns a tuple with mapped 1st component {@code A -> I}
     * by applying the given function.
     *
     * @param f1 mapping function of the 1st component.
     * @return new {@link Tuple8}<I, B, C, D>.
     */
    @Override
    public <I> Tuple4<I, B, C, D> map1(final Fn1<? super A, ? extends I> f1) {
        return Tuple.of(f1.apply(_1), _2, _3, _4);
    }

    /**
     * Returns a tuple with mapped 2nd component {@code B -> J}
     * by applying the given function.
     *
     * @param f2 mapping function of the 2nd component.
     * @return new {@link Tuple8}<A, J, C, D>.
     */
    @Override
    public <J> Tuple4<A, J, C, D> map2(final Fn1<? super B, ? extends J> f2) {
        return Tuple.of(_1, f2.apply(_2), _3, _4);
    }

    /**
     * Returns a tuple with mapped 3rd component {@code C -> K}
     * by applying the given function.
     *
     * @param f3 mapping function of the 3rd component.
     * @return new {@link Tuple8}<A, B, K, D>.
     */
    @Override
    public <K> Tuple4<A, B, K, D> map3(final Fn1<? super C, ? extends K> f3) {
        return Tuple.of(_1, _2, f3.apply(_3), _4);
    }

    /**
     * Returns a tuple with mapped 4th component {@code C -> K}
     * by applying the given function.
     *
     * @param f4 mapping function of the 4th component.
     * @return new {@link Tuple8}<A, B, C, L>.
     */
    @Override
    public <L> Tuple4<A, B, C, L> map4(final Fn1<? super D, ? extends L> f4) {
        return Tuple.of(_1, _2, _3, f4.apply(_4));
    }

    // ----------------------------------------------------------

    /**
     * Returns a tuple with mapped components by applying all given functions.
     *
     * @param f1 mapping function of the 1st component.
     * @param f2 mapping function of the 2nd component.
     * @param f3 mapping function of the 3rd component.
     * @param f4 mapping function of the 4th component.
     * @return new {@link Tuple4}<I, J, K, L>.
     */
    @Override
    public <I, J, K, L>
    Tuple4<I, J, K, L> map(final Fn1<? super A, ? extends I> f1,
                           final Fn1<? super B, ? extends J> f2,
                           final Fn1<? super C, ? extends K> f3,
                           final Fn1<? super D, ? extends L> f4) {
        try {
            return Tuple.of(
                    f1.onApply(_1),
                    f2.onApply(_2),
                    f3.onApply(_3),
                    f4.onApply(_4)
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
        return new Object[] { _1, _2, _3, _4 };
    }

    // ----------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final Product4<?, ?, ?, ?> that))
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