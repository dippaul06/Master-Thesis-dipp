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
import magma.exa.adt.typelevel.nat.$7;
import magma.exa.control.exception.Exceptions;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.control.functor.NFunctor;
import magma.exa.value.adt.product.Product;
import magma.exa.value.adt.product.Product7;

/**
 * A tuple of 7 heterogeneously typed components;
 * canonical representation of a {@link Product7}.
 *
 * @param <A> type of 1st component.
 * @param <B> type of 2nd component.
 * @param <C> type of 3rd component.
 * @param <D> type of 4th component.
 * @param <E> type of 5th component.
 * @param <F> type of 6th component.
 * @param <G> type of 7th component.
 */
public class Tuple7<A, B, C, D, E, F, G> extends Tuple.AbstractBase<$7>

        implements Product7.Assignable<A, B, C, D, E, F, G, Tuple7<A, B, C, D, E, F, G>>,

        NFunctor.Of7<A, B, C, D, E, F, G, Tuple7<?, ?, ?, ?, ?, ?, ?>>,

        HList.Prependable<Tuple7<A, B, C, D, E, F, G>>,

        HList.Appendable<Tuple7<A, B, C, D, E, F, G>>,

        HList.Cell<A, Tuple6<B, C, D, E, F, G>>
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

    /**
     * The 5th tuple component.
     **/
    public final E _5;

    /**
     * The 6th tuple component.
     **/
    public final F _6;

    /**
     * The 7th tuple component.
     **/
    public final G _7;

    // ----------------------------------------------------------

    /**
     * Constructs a tuple from the given values.
     */
    public Tuple7(final A _1, final B _2, final C _3, final D _4, final E _5, final F _6, final G _7) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
        this._7 = _7;
    }

    /**
     * Constructs tuple from the given product.
     */
    public Tuple7(final Product7<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F, ? extends G> product) {
        this._1 = product._1();
        this._2 = product._2();
        this._3 = product._3();
        this._4 = product._4();
        this._5 = product._5();
        this._6 = product._6();
        this._7 = product._7();
    }

    /**
     * Copy constructor.
     */
    public Tuple7(final Tuple7<A, B, C, D, E, F, G> tuple) {
        this._1 = tuple._1;
        this._2 = tuple._2;
        this._3 = tuple._3;
        this._4 = tuple._4;
        this._5 = tuple._5;
        this._6 = tuple._6;
        this._7 = tuple._7;
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

    /**
     * Returns the 5th component.
     */
    @Override
    public final E _5() {
        return _5;
    }

    /**
     * Returns the 6th component.
     */
    @Override
    public final F _6() {
        return _6;
    }

    /**
     * Returns the 7th component.
     */
    @Override
    public final G _7() {
        return _7;
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
    public final Tuple6<B, C, D, E, F, G> tail() {
        return Tuple.of(_2, _3, _4, _5, _6, _7);
    }

    /**
     * Returns this tuple as a corresponding heterogeneous list.
     */
    @Override
    public final Cell<A, Cell<B, Cell<C, Cell<D, Cell<E, Cell<F, Single<G>>>>>>> hlist() {
        return HList.of(_1, _2, _3, _4, _5, _6, _7);
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
            case 5 -> _5;
            case 6 -> _6;
            case 7 -> _7;
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
    public final Tuple7<A, B, C, D, E, F, G> _1(final A a) {
        return Tuple.of(a, _2, _3, _4, _5, _6, _7);
    }

    /**
     * Returns a tuple with the given value in the 2nd component.
     *
     * @param b value of the 2nd component.
     * @return new tuple.
     */
    @Override
    public final Tuple7<A, B, C, D, E, F, G> _2(final B b) {
        return Tuple.of(_1, b, _3, _4, _5, _6, _7);
    }

    /**
     * Returns a tuple with the given value in the 3rd component.
     *
     * @param c value of the 3rd component.
     * @return new tuple.
     */
    @Override
    public final Tuple7<A, B, C, D, E, F, G> _3(final C c) {
        return Tuple.of(_1, _2, c, _4, _5, _6, _7);
    }

    /**
     * Returns a tuple with the given value in the 4th component.
     *
     * @param d value of the 4th component.
     * @return new tuple.
     */
    @Override
    public final Tuple7<A, B, C, D, E, F, G> _4(final D d) {
        return Tuple.of(_1, _2, _3, d, _5, _6, _7);
    }

    /**
     * Returns a tuple with the given value in the 5th component.
     *
     * @param e value of the 5th component.
     * @return new tuple.
     */
    @Override
    public final Tuple7<A, B, C, D, E, F, G> _5(final E e) {
        return Tuple.of(_1, _2, _3, _4, e, _6, _7);
    }

    /**
     * Returns a tuple with the given value in the 6th component.
     *
     * @param f value of the 6th component.
     * @return new tuple.
     */
    @Override
    public final Tuple7<A, B, C, D, E, F, G> _6(final F f) {
        return Tuple.of(_1, _2, _3, _4, _5, f, _7);
    }

    /**
     * Returns a tuple with the given value in the 7th component.
     *
     * @param g value of the 7th component.
     * @return new tuple.
     */
    @Override
    public final Tuple7<A, B, C, D, E, F, G> _7(final G g) {
        return Tuple.of(_1, _2, _3, _4, _5, _6, g);
    }

    // ----------------------------------------------------------

    /**
     * Constructs a tuple by prepending the given value
     * to the components of this tuple.
     *
     * @param value to be prepended.
     * @param <Z>   type of value to be prepended.
     * @return tuple of type {@link Tuple8}<Z, A, B, C, D, E, F, G>.
     */
    @Override
    public final <Z> Tuple8<Z, A, B, C, D, E, F, G> prepend(final Z value) {
        return Tuple.of(value, _1, _2, _3, _4, _5, _6, _7);
    }

    /**
     * Constructs a tuple by appending the given value
     * to the components of this tuple.
     *
     * @param value to be appended.
     * @param <H>   type of value to be appended.
     * @return tuple of type {@link Tuple8}<A, B, C, D, E, F, G, H>.
     */
    @Override
    public final <H> Tuple8<A, B, C, D, E, F, G, H> append(final H value) {
        return Tuple.of(_1, _2, _3, _4, _5, _6, _7, value);
    }

    // ----------------------------------------------------------

    /**
     * Returns a tuple with mapped 1st component {@code A -> I}
     * by applying the given function.
     *
     * @param f1 mapping function of the 1st component.
     * @return new {@link Tuple8}<I, B, C, D, E, F, G>.
     */
    @Override
    public <I> Tuple7<I, B, C, D, E, F, G> map1(final Fn1<? super A, ? extends I> f1) {
        return Tuple.of(f1.apply(_1), _2, _3, _4, _5, _6, _7);
    }

    /**
     * Returns a tuple with mapped 2nd component {@code B -> J}
     * by applying the given function.
     *
     * @param f2 mapping function of the 2nd component.
     * @return new {@link Tuple8}<A, J, C, D, E, F, G>.
     */
    @Override
    public <J> Tuple7<A, J, C, D, E, F, G> map2(final Fn1<? super B, ? extends J> f2) {
        return Tuple.of(_1, f2.apply(_2), _3, _4, _5, _6, _7);
    }

    /**
     * Returns a tuple with mapped 3rd component {@code C -> K}
     * by applying the given function.
     *
     * @param f3 mapping function of the 3rd component.
     * @return new {@link Tuple8}<A, B, K, D, E, F, G>.
     */
    @Override
    public <K> Tuple7<A, B, K, D, E, F, G> map3(final Fn1<? super C, ? extends K> f3) {
        return Tuple.of(_1, _2, f3.apply(_3), _4, _5, _6, _7);
    }

    /**
     * Returns a tuple with mapped 4th component {@code C -> K}
     * by applying the given function.
     *
     * @param f4 mapping function of the 4th component.
     * @return new {@link Tuple8}<A, B, C, L, E, F, G>.
     */
    @Override
    public <L> Tuple7<A, B, C, L, E, F, G> map4(final Fn1<? super D, ? extends L> f4) {
        return Tuple.of(_1, _2, _3, f4.apply(_4), _5, _6, _7);
    }

    /**
     * Returns a tuple with mapped 5th component {@code C -> K}
     * by applying the given function.
     *
     * @param f5 mapping function of the 5th component.
     * @return new {@link Tuple8}<A, B, C, D, M, F, G>.
     */
    @Override
    public <M> Tuple7<A, B, C, D, M, F, G> map5(final Fn1<? super E, ? extends M> f5) {
        return Tuple.of(_1, _2, _3, _4, f5.apply(_5), _6, _7);
    }

    /**
     * Returns a tuple with mapped 6th component {@code C -> K}
     * by applying the given function.
     *
     * @param f6 mapping function of the 6th component.
     * @return new {@link Tuple8}<A, B, C, D, E, N, G>.
     */
    @Override
    public <N> Tuple7<A, B, C, D, E, N, G> map6(final Fn1<? super F, ? extends N> f6) {
        return Tuple.of(_1, _2, _3, _4, _5, f6.apply(_6), _7);
    }

    /**
     * Returns a tuple with mapped 7th component {@code C -> K}
     * by applying the given function.
     *
     * @param f7 mapping function of the 7th component.
     * @return new {@link Tuple8}<A, B, C, D, E, F, O>.
     */
    @Override
    public <O> Tuple7<A, B, C, D, E, F, O> map7(final Fn1<? super G, ? extends O> f7) {
        return Tuple.of(_1, _2, _3, _4, _5, _6, f7.apply(_7));
    }

    // ----------------------------------------------------------

    /**
     * Returns a tuple with mapped components by applying all given functions.
     *
     * @param f1 mapping function of the 1st component.
     * @param f2 mapping function of the 2nd component.
     * @param f3 mapping function of the 3rd component.
     * @param f4 mapping function of the 4th component.
     * @param f5 mapping function of the 5th component.
     * @param f6 mapping function of the 6th component.
     * @param f7 mapping function of the 7th component.
     * @return new {@link Tuple7}<I, J, K, L, M, N, O>.
     */
    @Override
    public <I, J, K, L, M, N, O>
    Tuple7<I, J, K, L, M, N, O> map(final Fn1<? super A, ? extends I> f1,
                                    final Fn1<? super B, ? extends J> f2,
                                    final Fn1<? super C, ? extends K> f3,
                                    final Fn1<? super D, ? extends L> f4,
                                    final Fn1<? super E, ? extends M> f5,
                                    final Fn1<? super F, ? extends N> f6,
                                    final Fn1<? super G, ? extends O> f7) {
        try {
            return Tuple.of(
                    f1.onApply(_1),
                    f2.onApply(_2),
                    f3.onApply(_3),
                    f4.onApply(_4),
                    f5.onApply(_5),
                    f6.onApply(_6),
                    f7.onApply(_7)
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
        return new Object[] { _1, _2, _3, _4, _5, _6, _7 };
    }

    // ----------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final Product7<?, ?, ?, ?, ?, ?, ?> that))
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
