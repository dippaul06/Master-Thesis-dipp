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
import magma.exa.adt.typelevel.nat.$N;
import magma.exa.value.Unit;
import magma.exa.value.adt.product.*;
import magma.exa.value.adt.slot.Slot;

/**
 * A tuple type is a shallowly immutable, transparent carrier for
 * a fixed set of values, called the tuple components.
 *
 *
 *
 * A tuple is a composite value consisting of a fixed number of
 * differently typed components. Tuples are strictly immutable
 * and all transformations produce new tuples and never update
 * existing contents.
 *
 * Tuple Companion.
 * TODO...
 */
public interface Tuple<N extends $N> extends HList, Product<N> {

    /**
     * Returns the empty tuple.
     */
    static Unit empty() {
        return Unit.value;
    }

    /**
     * Constructs a {@link Tuple1} with the given component.
     */
    static <A> Tuple1<A> of(final A a) {
        return new Tuple1<>(a);
    }

    /**
     * Constructs a {@link Tuple2} with the given components.
     */
    static <A, B> Tuple2<A, B> of(final A a, final B b) {
        return new Tuple2<>(a, b);
    }

    /**
     * Constructs a {@link Tuple3} with the given components.
     */
    static <A, B, C> Tuple3<A, B, C> of(final A a, final B b, final C c) {
        return new Tuple3<>(a, b, c);
    }

    /**
     * Constructs a {@link Tuple4} with the given components.
     */
    static <A, B, C, D> Tuple4<A, B, C, D> of(final A a, final B b, final C c, final D d) {
        return new Tuple4<>(a, b, c, d);
    }

    /**
     * Constructs a {@link Tuple5} with the given components.
     */
    static <A, B, C, D, E> Tuple5<A, B, C, D, E> of(final A a, final B b, final C c, final D d, final E e) {
        return new Tuple5<>(a, b, c, d, e);
    }

    /**
     * Constructs a {@link Tuple6} with the given components.
     */
    static <A, B, C, D, E, F> Tuple6<A, B, C, D, E, F> of(final A a, final B b, final C c, final D d, final E e, final F f) {
        return new Tuple6<>(a, b, c, d, e, f);
    }

    /**
     * Constructs a {@link Tuple7} with the given components.
     */
    static <A, B, C, D, E, F, G> Tuple7<A, B, C, D, E, F, G> of(final A a, final B b, final C c, final D d, final E e, final F f, final G g) {
        return new Tuple7<>(a, b, c, d, e, f, g);
    }

    /**
     * Constructs a {@link Tuple8} with the given components.
     */
    static <A, B, C, D, E, F, G, H> Tuple8<A, B, C, D, E, F, G, H> of(final A a, final B b, final C c, final D d, final E e, final F f, final G g, final H h) {
        return new Tuple8<>(a, b, c, d, e, f, g, h);
    }

    // ----------------------------------------------------------

    static <A> Tuple1<A> of(final Product1<? extends A> p) { return new Tuple1<>(p); }

    static <A, B> Tuple2<A, B> of(final Product2<? extends A, ? extends B> p) { return new Tuple2<>(p); }

    static <A, B, C> Tuple3<A, B, C> of(final Product3<? extends A, ? extends B, ? extends C> p) { return new Tuple3<>(p); }

    static <A, B, C, D> Tuple4<A, B, C, D> of(final Product4<? extends A, ? extends B, ? extends C, ? extends D> p) { return new Tuple4<>(p); }

    static <A, B, C, D, E> Tuple5<A, B, C, D, E> of(final Product5<? extends A, ? extends B, ? extends C, ? extends D, ? extends E> p) { return new Tuple5<>(p); }

    static <A, B, C, D, E, F> Tuple6<A, B, C, D, E, F> of(final Product6<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F> p) { return new Tuple6<>(p); }

    static <A, B, C, D, E, F, G> Tuple7<A, B, C, D, E, F, G> of(final Product7<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F, ? extends G> p) { return new Tuple7<>(p); }

    static <A, B, C, D, E, F, G, H> Tuple8<A, B, C, D, E, F, G, H> of(final Product8<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F, ? extends G, ? extends H> p) { return new Tuple8<>(p); }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Tuple2} from the given {@link Product1} and {@link Product1}.
     */
    static <A, B> Tuple2<A, B> of(final Product1<? extends A> a, final Product1<? extends B> b) {
        return Tuple.of(a._1(), b._1());
    }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Tuple3} from the given {@link Product2} and {@link Product1}.
     */
    static <A, B, C> Tuple3<A, B, C> of(final Product2<? extends A, ? extends B> a, final Product1<? extends C> b) {
        return Tuple.of(a._1(), a._2(), b._1());
    }

    /**
     * Constructs a {@link Tuple3} from the given {@link Product1} and {@link Product2}.
     */
    static <A, B, C> Tuple3<A, B, C> of(final Product1<? extends A> a, final Product2<? extends B, ? extends C> b) {
        return Tuple.of(a._1(), b._1(), b._2());
    }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Tuple4} from the given {@link Product3} and {@link Product1}.
     */
    static <A, B, C, D> Tuple4<A, B, C, D> of(final Product3<? extends A, ? extends B, ? extends C> a, final Product1<? extends D> b) {
        return Tuple.of(a._1(), a._2(), a._3(), b._1());
    }

    /**
     * Constructs a {@link Tuple4} from the given {@link Product2} and {@link Product2}.
     */
    static <A, B, C, D> Tuple4<A, B, C, D> of(final Product2<? extends A, ? extends B> a, final Product2<? extends C, ? extends D> b) {
        return Tuple.of(a._1(), a._2(), b._1(), b._2());
    }

    /**
     * Constructs a {@link Tuple4} from the given {@link Product1} and {@link Product3}.
     */
    static <A, B, C, D> Tuple4<A, B, C, D> of(final Product1<? extends A> a, final Product3<? extends B, ? extends C, ? extends D> b) {
        return Tuple.of(a._1(), b._1(), b._2(), b._3());
    }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Tuple5} from the given {@link Product4} and {@link Product1}.
     */
    static <A, B, C, D, E> Tuple5<A, B, C, D, E> of(final Product4<? extends A, ? extends B, ? extends C, ? extends D> a, final Product1<? extends E> b) {
        return Tuple.of(a._1(), a._2(), a._3(), a._4(), b._1());
    }

    /**
     * Constructs a {@link Tuple5} from the given {@link Product3} and {@link Product2}.
     */
    static <A, B, C, D, E> Tuple5<A, B, C, D, E> of(final Product3<? extends A, ? extends B, ? extends C> a, final Product2<? extends D, ? extends E> b) {
        return Tuple.of(a._1(), a._2(), a._3(), b._1(), b._2());
    }

    /**
     * Constructs a {@link Tuple5} from the given {@link Product2} and {@link Product3}.
     */
    static <A, B, C, D, E> Tuple5<A, B, C, D, E> of(final Product2<? extends A, ? extends B> a, final Product3<? extends C, ? extends D, ? extends E> b) {
        return Tuple.of(a._1(), a._2(), b._1(), b._2(), b._3());
    }

    /**
     * Constructs a {@link Tuple5} from the given {@link Product1} and {@link Product4}.
     */
    static <A, B, C, D, E> Tuple5<A, B, C, D, E> of(final Product1<? extends A> a, final Product4<? extends B, ? extends C, ? extends D, ? extends E> b) {
        return Tuple.of(a._1(), b._1(), b._2(), b._3(), b._4());
    }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Tuple6} from the given {@link Product5} and {@link Product1}.
     */
    static <A, B, C, D, E, F> Tuple6<A, B, C, D, E, F> of(final Product5<? extends A, ? extends B, ? extends C, ? extends D, ? extends E> a, final Product1<? extends F> b) {
        return Tuple.of(a._1(), a._2(), a._3(), a._4(), a._5(), b._1());
    }

    /**
     * Constructs a {@link Tuple6} from the given {@link Product4} and {@link Product2}.
     */
    static <A, B, C, D, E, F> Tuple6<A, B, C, D, E, F> of(final Product4<? extends A, ? extends B, ? extends C, ? extends D> a, final Product2<? extends E, ? extends F> b) {
        return Tuple.of(a._1(), a._2(), a._3(), a._4(), b._1(), b._2());
    }

    /**
     * Constructs a {@link Tuple6} from the given {@link Product3} and {@link Product3}.
     */
    static <A, B, C, D, E, F> Tuple6<A, B, C, D, E, F> of(final Product3<? extends A, ? extends B, ? extends C> a, final Product3<? extends D, ? extends E, ? extends F> b) {
        return Tuple.of(a._1(), a._2(), a._3(), b._1(), b._2(), b._3());
    }

    /**
     * Constructs a {@link Tuple6} from the given {@link Product2} and {@link Product4}.
     */
    static <A, B, C, D, E, F> Tuple6<A, B, C, D, E, F> of(final Product2<? extends A, ? extends B> a, final Product4<? extends C, ? extends D, ? extends E, ? extends F> b) {
        return Tuple.of(a._1(), a._2(), b._1(), b._2(), b._3(), b._4());
    }

    /**
     * Constructs a {@link Tuple6} from the given {@link Product1} and {@link Product5}.
     */
    static <A, B, C, D, E, F> Tuple6<A, B, C, D, E, F> of(final Product1<? extends A> a, final Product5<? extends B, ? extends C, ? extends D, ? extends E, ? extends F> b) {
        return Tuple.of(a._1(), b._1(), b._2(), b._3(), b._4(), b._5());
    }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Tuple7} from the given {@link Product6} and {@link Product1}.
     */
    static <A, B, C, D, E, F, G> Tuple7<A, B, C, D, E, F, G> of(final Product6<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F> a, final Product1<? extends G> b) {
        return Tuple.of(a._1(), a._2(), a._3(), a._4(), a._5(), a._6(), b._1());
    }

    /**
     * Constructs a {@link Tuple7} from the given {@link Product5} and {@link Product2}.
     */
    static <A, B, C, D, E, F, G> Tuple7<A, B, C, D, E, F, G> of(final Product5<? extends A, ? extends B, ? extends C, ? extends D, ? extends E> a, final Product2<? extends F, ? extends G> b) {
        return Tuple.of(a._1(), a._2(), a._3(), a._4(), a._5(), b._1(), b._2());
    }

    /**
     * Constructs a {@link Tuple7} from the given {@link Product4} and {@link Product3}.
     */
    static <A, B, C, D, E, F, G> Tuple7<A, B, C, D, E, F, G> of(final Product4<? extends A, ? extends B, ? extends C, ? extends D> a, final Product3<? extends E, ? extends F, ? extends G> b) {
        return Tuple.of(a._1(), a._2(), a._3(), a._4(), b._1(), b._2(), b._3());
    }

    /**
     * Constructs a {@link Tuple7} from the given {@link Product3} and {@link Product4}.
     */
    static <A, B, C, D, E, F, G> Tuple7<A, B, C, D, E, F, G> of(final Product3<? extends A, ? extends B, ? extends C> a, final Product4<? extends D, ? extends E, ? extends F, ? extends G> b) {
        return Tuple.of(a._1(), a._2(), a._3(), b._1(), b._2(), b._3(), b._4());
    }

    /**
     * Constructs a {@link Tuple7} from the given {@link Product2} and {@link Product5}.
     */
    static <A, B, C, D, E, F, G> Tuple7<A, B, C, D, E, F, G> of(final Product2<? extends A, ? extends B> a, final Product5<? extends C, ? extends D, ? extends E, ? extends F, ? extends G> b) {
        return Tuple.of(a._1(), a._2(), b._1(), b._2(), b._3(), b._4(), b._5());
    }

    /**
     * Constructs a {@link Tuple7} from the given {@link Product1} and {@link Product6}.
     */
    static <A, B, C, D, E, F, G> Tuple7<A, B, C, D, E, F, G> of(final Product1<? extends A> a, final Product6<? extends B, ? extends C, ? extends D, ? extends E, ? extends F, ? extends G> b) {
        return Tuple.of(a._1(), b._1(), b._2(), b._3(), b._4(), b._5(), b._6());
    }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Tuple8} from the given {@link Product7} and {@link Product1}.
     */
    static <A, B, C, D, E, F, G, H> Tuple8<A, B, C, D, E, F, G, H> of(final Product7<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F, ? extends G> a, final Product1<? extends H> b) {
        return Tuple.of(a._1(), a._2(), a._3(), a._4(), a._5(), a._6(), a._7(), b._1());
    }

    /**
     * Constructs a {@link Tuple8} from the given {@link Product6} and {@link Product2}.
     */
    static <A, B, C, D, E, F, G, H> Tuple8<A, B, C, D, E, F, G, H> of(final Product6<? extends A, ? extends B, ? extends C, ? extends D, ? extends E, ? extends F> a, final Product2<? extends G, ? extends H> b) {
        return Tuple.of(a._1(), a._2(), a._3(), a._4(), a._5(), a._6(), b._1(), b._2());
    }

    /**
     * Constructs a {@link Tuple8} from the given {@link Product5} and {@link Product3}.
     */
    static <A, B, C, D, E, F, G, H> Tuple8<A, B, C, D, E, F, G, H> of(final Product5<? extends A, ? extends B, ? extends C, ? extends D, ? extends E> a, final Product3<? extends F, ? extends G, ? extends H> b) {
        return Tuple.of(a._1(), a._2(), a._3(), a._4(), a._5(), b._1(), b._2(), b._3());
    }

    /**
     * Constructs a {@link Tuple8} from the given {@link Product4} and {@link Product4}.
     */
    static <A, B, C, D, E, F, G, H> Tuple8<A, B, C, D, E, F, G, H> of(final Product4<? extends A, ? extends B, ? extends C, ? extends D> a, final Product4<? extends E, ? extends F, ? extends G, ? extends H> b) {
        return Tuple.of(a._1(), a._2(), a._3(), a._4(), b._1(), b._2(), b._3(), b._4());
    }

    /**
     * Constructs a {@link Tuple8} from the given {@link Product3} and {@link Product5}.
     */
    static <A, B, C, D, E, F, G, H> Tuple8<A, B, C, D, E, F, G, H> of(final Product3<? extends A, ? extends B, ? extends C> a, final Product5<? extends D, ? extends E, ? extends F, ? extends G, ? extends H> b) {
        return Tuple.of(a._1(), a._2(), a._3(), b._1(), b._2(), b._3(), b._4(), b._5());
    }

    /**
     * Constructs a {@link Tuple8} from the given {@link Product2} and {@link Product6}.
     */
    static <A, B, C, D, E, F, G, H> Tuple8<A, B, C, D, E, F, G, H> of(final Product2<? extends A, ? extends B> a, final Product6<? extends C, ? extends D, ? extends E, ? extends F, ? extends G, ? extends H> b) {
        return Tuple.of(a._1(), a._2(), b._1(), b._2(), b._3(), b._4(), b._5(), b._6());
    }

    /**
     * Constructs a {@link Tuple8} from the given {@link Product1} and {@link Product7}.
     */
    static <A, B, C, D, E, F, G, H> Tuple8<A, B, C, D, E, F, G, H> of(final Product1<? extends A> a, final Product7<? extends B, ? extends C, ? extends D, ? extends E, ? extends F, ? extends G, ? extends H> b) {
        return Tuple.of(a._1(), b._1(), b._2(), b._3(), b._4(), b._5(), b._6(), b._7());
    }

    // ----------------------------------------------------------
    //  TUPLE.ABSTRACT-BASE
    // ----------------------------------------------------------

    /**
     * Abstract base implementation of all defined tuples.
     *
     * @param <N> type-level arity.
     */
    abstract class AbstractBase<N extends $N> implements Tuple<N> {

        protected AbstractBase() {
        }

        /**
         * Returns this tuple as a corresponding heterogeneous list.
         */
        public abstract HList hlist();

        /**
         * Returns the value of the component located at the given index.
         *
         * @param idx index of the component to be accessed.
         * @return value of the accessed component.
         */
        public abstract Object at(long idx);

        /**
         * Returns the value of the component located at the given slot.
         *
         * @param slot label of the component to be accessed.
         * @return value of the accessed component.
         */
        public final Object at(final Slot slot) {
            return at(slot.ordinal() + 1);
        }

        /**
         * Returns an array consisting of all tuple components.
         */
        public abstract Object[] toArray();
    }
}