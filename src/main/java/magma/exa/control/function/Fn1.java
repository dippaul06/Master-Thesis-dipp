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

package magma.exa.control.function;

import magma.exa.adt.typelevel.nat.$1;
import magma.exa.adt.typelevel.signature.$Fn1;
import magma.exa.base.Force;
import magma.exa.control.adt.function.IFn1;
import magma.exa.control.exception.Throw;
import magma.exa.control.functor.Functor;
import magma.exa.control.functor.Profunctor;
import magma.exa.value.Unit;
import magma.exa.value.adt.product.Product1;

/**
 * Function that takes a single argument and returns a result of type {@code R}.
 * <p>
 * This is the core function type from which all other function types
 * are derived from and auto-curry with.
 *
 * @param <A> type of 1st parameter.
 * @param <R> type of function result.
 */
@FunctionalInterface
public interface Fn1<A, R> extends IFn1<$1, A, R>,

        Profunctor<A, R, Fn1<?, ?>>,

        Functor<R, Fn1<A, ?>>
{
    /**
     * Returns the function arity.
     */
    @Override
    default $1 arity() {
        return $1.nat;
    }

    // ----------------------------------------------------------

    /// LAZY APPLICATION.

    /**
     * Produces a thunk by fixing the arguments and deferring
     * the computation until the result is needed.
     */
    default Fn0<R> thunk(final A a) {
        return () -> onApply(a);
    }

    // ----------------------------------------------------------

    /// RIGHT-TO-LEFT COMPOSITION.

    /**
     * Returns first-class right-to-left composition operator.
     */
    default <Z> Fn1<Fn1<? super Z, ? extends A>, Fn1<Z, R>> compose() {
        return this::compose;
    }

    /**
     * Returns a right-to-left composed function formed by
     * the given function and this function.
     */
    default <Z> Fn1<Z, R> compose(final Fn1<? super Z, ? extends A> fn) {
        return Fn.compose(fn, this);
    }

    /**
     * Right-to-left composition.
     */
    @Override
    default <Z> Fn1<Z, R> contraMap(final Fn1<? super Z, ? extends A> fn) {
        return Fn.compose(fn, this);
    }

    // ----------------------------------------------------------

    /// LEFT-TO-RIGHT COMPOSITION.

    /**
     * Returns first-class left-to-right composition operator.
     */
    default <S> Fn1<Fn1<? super R, ? extends S>, Fn1<A, S>> then() {
        return Fn.compose(this);
    }

    /**
     * Returns a left-to-right composed function formed by
     * this function and the given function.
     */
    default <S> Fn1<A, S> then(final Fn1<? super R, ? extends S> fn) {
        return Fn.compose(this, fn);
    }

    /**
     * Left-to-right composition.
     */
    @Override
    default <T> Fn1<A, T> fmap(final Fn1<? super R, ? extends T> fn) {
        return Fn.compose(this, fn);
    }

    // ----------------------------------------------------------

    /// PROFUNCTOR

    /**
     * Simultaneously map contravariantly over the argument and covariantly
     * over the return value of this function, producing a new function that
     * takes a new argument type, and a new result type.
     *
     * @param fa contravariant argument mapping function.
     * @param fb covariant result mapping function.
     * @param <Z> type of the new argument.
     * @param <T> type of the new result.
     * @return function of type {@link Fn1}<Z, T>.
     */
    @Override
    default <Z, T> Fn1<Z, T> dimap(final Fn1<? super Z, ? extends A> fa,
                                   final Fn1<? super R, ? extends T> fb) {
        return Fn.compose(Fn.compose(fa, this), fb);
    }

    /**
     * Contravariantly map over the argument to this function, producing a new
     * function that takes the new argument type, and produces the same result.
     *
     * @param fa  contravariant argument mapping function.
     * @param <Z> type of the new argument.
     * @return function of type {@link Fn1}<Z, R>.
     */
    @Override
    default <Z> Fn1<Z, R> dimapL(final Fn1<? super Z, ? extends A> fa) {
        return Fn.compose(fa, this);
    }

    /**
     * Covariantly map over the return value of this function, producing a new
     * function that takes the same argument, and produces the new result type.
     *
     * @param fb  covariant result mapping function.
     * @param <S> type of the new result.
     * @return function of type {@link Fn1}<A, S>.
     */
    @Override
    default <S> Fn1<A, S> dimapR(final Fn1<? super R, ? extends S> fb) {
        return Fn.compose(this, fb);
    }

    // ----------------------------------------------------------

    /// UNCURRY ISOMORPHISM.

    /**
     * Produces the tupled function form: instead of 1 arguments,
     * it accepts a single {@link Product1} argument.
     */
    default Fn1<Product1<? extends A>, R> tupled() {
        return p -> onApply(p._1());
    }

    // ----------------------------------------------------------
    //  FN1.PREDICATE
    // ----------------------------------------------------------

    /**
     * A predicate (boolean-valued function) that takes a single argument.
     *
     * @param <A> type of 1st parameter.
     */
    @FunctionalInterface
    interface Predicate<A> extends Fn1<A, Boolean>, IPredicate<$1, A> {

        /** Predicate that always evaluates to {@code true}. */
        static <A> Predicate<A> True() {
            return Force.cast(Constant.True);
        }

        /** Predicate that always evaluates to {@code false}. */
        static <A> Predicate<A> False() {
            return Force.cast(Constant.False);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Predicate thunk(final A a) {
            return () -> onEval(a);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled predicate form: instead of 1 arguments,
         * it accepts a single {@link Product1} argument.
         */
        default Predicate<Product1<? extends A>> tupled() {
            return p -> onEval(p._1());
        }
    }

    // ----------------------------------------------------------
    //  FN1.CONSUMER
    // ----------------------------------------------------------

    /**
     * A side effect operation that accepts a single argument.
     *
     * @param <A> type of 1st parameter.
     */
    @FunctionalInterface
    interface Consumer<A> extends Fn1<A, Unit>, IConsumer<$1, A> {

        /** Returns an empty consumer. */
        static <A> Consumer<A> Empty() {
            return Force.cast(Constant.empty);
        }

        /// LAZY APPLICATION.

        /**
         * Produces a thunk by fixing the arguments and deferring
         * the computation until the result is needed.
         */
        @Override default Fn0.Consumer thunk(final A a) {
            return () -> onAccept(a);
        }

        /// UNCURRY ISOMORPHISM.

        /**
         * Produces the tupled consumer form: instead of 1 arguments,
         * it accepts a single {@link Product1} argument.
         */
        default Consumer<Product1<? extends A>> tupled() {
            return p -> onAccept(p._1());
        }
    }

    // ----------------------------------------------------------
    //  FN1.TO-BOOL
    // ----------------------------------------------------------

    /**
     * A function that takes one parameter and returns a {@code boolean} result.
     *
     * @param <A> type of parameter.
     */
    @FunctionalInterface
    interface ToBool<A> extends $Fn1<A, Boolean> {

        /**
         * Invokes this function with the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         * @throws Throwable permits any exception to be thrown.
         */
        boolean onApply(A a) throws Throwable;

        /**
         * Applies this function to the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         */
        default boolean apply(final A a) {
            try {
                return onApply(a);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  FN1.TO-I8
    // ----------------------------------------------------------

    /**
     * A function that takes one parameter and returns a {@code byte} result.
     *
     * @param <A> type of parameter.
     */
    @FunctionalInterface
    interface ToI8<A> extends $Fn1<A, Byte> {

        /**
         * Invokes this function with the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         * @throws Throwable permits any exception to be thrown.
         */
        byte onApply(A a) throws Throwable;

        /**
         * Applies this function to the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         */
        default byte apply(final A a) {
            try {
                return onApply(a);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  FN1.TO-I16
    // ----------------------------------------------------------

    /**
     * A function that takes one parameter and returns a {@code short} result.
     *
     * @param <A> type of parameter.
     */
    @FunctionalInterface
    interface ToI16<A> extends $Fn1<A, Short> {

        /**
         * Invokes this function with the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         * @throws Throwable permits any exception to be thrown.
         */
        short onApply(A a) throws Throwable;

        /**
         * Applies this function to the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         */
        default short apply(final A a) {
            try {
                return onApply(a);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  FN1.TO-C16
    // ----------------------------------------------------------

    /**
     * A function that takes one parameter and returns a {@code char} result.
     *
     * @param <A> type of parameter.
     */
    @FunctionalInterface
    interface ToC16<A> extends $Fn1<A, Character> {

        /**
         * Invokes this function with the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         * @throws Throwable permits any exception to be thrown.
         */
        char onApply(A a) throws Throwable;

        /**
         * Applies this function to the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         */
        default char apply(final A a) {
            try {
                return onApply(a);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  FN1.TO-I32
    // ----------------------------------------------------------

    /**
     * A function that takes one parameter and returns a {@code int} result.
     *
     * @param <A> type of parameter.
     */
    @FunctionalInterface
    interface ToI32<A> extends $Fn1<A, Integer> {

        /**
         * Invokes this function with the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         * @throws Throwable permits any exception to be thrown.
         */
        int onApply(A a) throws Throwable;

        /**
         * Applies this function to the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         */
        default int apply(final A a) {
            try {
                return onApply(a);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  FN1.TO-I64
    // ----------------------------------------------------------

    /**
     * A function that takes one parameter and returns a {@code long} result.
     *
     * @param <A> type of parameter.
     */
    @FunctionalInterface
    interface ToI64<A> extends $Fn1<A, Long> {

        /**
         * Invokes this function with the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         * @throws Throwable permits any exception to be thrown.
         */
        long onApply(A a) throws Throwable;

        /**
         * Applies this function to the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         */
        default long apply(final A a) {
            try {
                return onApply(a);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  FN1.TO-F32
    // ----------------------------------------------------------

    /**
     * A function that takes one parameter and returns a {@code float} result.
     *
     * @param <A> type of parameter.
     */
    @FunctionalInterface
    interface ToF32<A> extends $Fn1<A, Float> {

        /**
         * Invokes this function with the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         * @throws Throwable permits any exception to be thrown.
         */
        float onApply(A a) throws Throwable;

        /**
         * Applies this function to the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         */
        default float apply(final A a) {
            try {
                return onApply(a);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  FN1.TO-F64
    // ----------------------------------------------------------

    /**
     * A function that takes one parameter and returns a {@code double} result.
     *
     * @param <A> type of parameter.
     */
    @FunctionalInterface
    interface ToF64<A> extends $Fn1<A, Double> {

        /**
         * Invokes this function with the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         * @throws Throwable permits any exception to be thrown.
         */
        double onApply(A a) throws Throwable;

        /**
         * Applies this function to the given argument.
         *
         * @param a 1st argument.
         * @return function result.
         */
        default double apply(final A a) {
            try {
                return onApply(a);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------

    /**
     * Constructs a {@link Fn1} function that invariably returns the given value.
     */
    static <A, R> Fn1<A, R> constant(final R val) {
        return a -> val;
    }
}
