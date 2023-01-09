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

import static magma.exa.base.contract.Require.notNull;

/**
 * Function companion.
 */
public enum Fn {
    ;
    public static <R> Fn1.ToBool<R> ToBool(final Fn1.ToBool<R> expr) { return notNull(expr); }
    public static <R> Fn1.ToI8<R>   ToI8(final Fn1.ToI8<R>     expr) { return notNull(expr); }
    public static <R> Fn1.ToI16<R>  ToI16(final Fn1.ToI16<R>   expr) { return notNull(expr); }
    public static <R> Fn1.ToC16<R>  ToC16(final Fn1.ToC16<R>   expr) { return notNull(expr); }
    public static <R> Fn1.ToI32<R>  ToI32(final Fn1.ToI32<R>   expr) { return notNull(expr); }
    public static <R> Fn1.ToI64<R>  ToI64(final Fn1.ToI64<R>   expr) { return notNull(expr); }
    public static <R> Fn1.ToF32<R>  ToF32(final Fn1.ToF32<R>   expr) { return notNull(expr); }
    public static <R> Fn1.ToF64<R>  ToF64(final Fn1.ToF64<R>   expr) { return notNull(expr); }

    /// FUNCTION FACTORIES.

    /** Constructs a function of arity 0. */
    public static <R>
    Fn0<R> To(final Fn0<R> expr) {
        return notNull(expr);
    }

    /** Constructs a function of arity 1. */
    public static <A, R>
    Fn1<A, R> To(final Fn1<A, R> expr) {
        return notNull(expr);
    }

    /** Constructs a function of arity 2. */
    public static <A, B, R>
    Fn2<A, B, R> To(final Fn2<A, B, R> expr) {
        return notNull(expr);
    }

    /** Constructs a function of arity 3. */
    public static <A, B, C, R>
    Fn3<A, B, C, R> To(final Fn3<A, B, C, R> expr) {
        return notNull(expr);
    }

    /** Constructs a function of arity 4. */
    public static <A, B, C, D, R>
    Fn4<A, B, C, D, R> To(final Fn4<A, B, C, D, R> expr) {
        return notNull(expr);
    }

    /** Constructs a function of arity 5. */
    public static <A, B, C, D, E, R>
    Fn5<A, B, C, D, E, R> To(final Fn5<A, B, C, D, E, R> expr) {
        return notNull(expr);
    }

    /** Constructs a function of arity 6. */
    public static <A, B, C, D, E, F, R>
    Fn6<A, B, C, D, E, F, R> To(final Fn6<A, B, C, D, E, F, R> expr) {
        return notNull(expr);
    }

    /** Constructs a function of arity 7. */
    public static <A, B, C, D, E, F, G, R>
    Fn7<A, B, C, D, E, F, G, R> To(final Fn7<A, B, C, D, E, F, G, R> expr) {
        return notNull(expr);
    }

    /** Constructs a function of arity 8. */
    public static <A, B, C, D, E, F, G, H, R>
    Fn8<A, B, C, D, E, F, G, H, R> To(final Fn8<A, B, C, D, E, F, G, H, R> expr) {
        return notNull(expr);
    }

    // ---------------------------------------------------------

    /// PREDICATE FACTORIES.

    /** Constructs a predicate of arity 1. */
    public static <A>
    Fn1.Predicate<A> Predicate(final Fn1.Predicate<A> expr) {
        return notNull(expr);
    }

    /** Constructs a predicate of arity 2. */
    public static <A, B>
    Fn2.Predicate<A, B> Predicate(final Fn2.Predicate<A, B> expr) {
        return notNull(expr);
    }

    /** Constructs a predicate of arity 3. */
    public static <A, B, C>
    Fn3.Predicate<A, B, C> Predicate(final Fn3.Predicate<A, B, C> expr) {
        return notNull(expr);
    }

    /** Constructs a predicate of arity 4. */
    public static <A, B, C, D>
    Fn4.Predicate<A, B, C, D> Predicate(final Fn4.Predicate<A, B, C, D> expr) {
        return notNull(expr);
    }

    /** Constructs a predicate of arity 5. */
    public static <A, B, C, D, E>
    Fn5.Predicate<A, B, C, D, E> Predicate(final Fn5.Predicate<A, B, C, D, E> expr) {
        return notNull(expr);
    }

    /** Constructs a predicate of arity 6. */
    public static <A, B, C, D, E, F>
    Fn6.Predicate<A, B, C, D, E, F> Predicate(final Fn6.Predicate<A, B, C, D, E, F> expr) {
        return notNull(expr);
    }

    /** Constructs a predicate of arity 7. */
    public static <A, B, C, D, E, F, G>
    Fn7.Predicate<A, B, C, D, E, F, G> Predicate(final Fn7.Predicate<A, B, C, D, E, F, G> expr) {
        return notNull(expr);
    }

    /** Constructs a predicate of arity 8. */
    public static <A, B, C, D, E, F, G, H>
    Fn8.Predicate<A, B, C, D, E, F, G, H> Predicate(final Fn8.Predicate<A, B, C, D, E, F, G, H> expr) {
        return notNull(expr);
    }

    // ---------------------------------------------------------

    /// CONSUMER FACTORIES.

    /** Constructs a consumer of arity 1. */
    public static <A>
    Fn1.Consumer<A> Consumer(final Fn1.Consumer<A> expr) {
        return notNull(expr);
    }

    /** Constructs a consumer of arity 2. */
    public static <A, B>
    Fn2.Consumer<A, B> Consumer(final Fn2.Consumer<A, B> expr) {
        return notNull(expr);
    }

    /** Constructs a consumer of arity 3. */
    public static <A, B, C>
    Fn3.Consumer<A, B, C> Consumer(final Fn3.Consumer<A, B, C> expr) {
        return notNull(expr);
    }

    /** Constructs a consumer of arity 4. */
    public static <A, B, C, D>
    Fn4.Consumer<A, B, C, D> Consumer(final Fn4.Consumer<A, B, C, D> expr) {
        return notNull(expr);
    }

    /** Constructs a consumer of arity 5. */
    public static <A, B, C, D, E>
    Fn5.Consumer<A, B, C, D, E> Consumer(final Fn5.Consumer<A, B, C, D, E> expr) {
        return notNull(expr);
    }

    /** Constructs a consumer of arity 6. */
    public static <A, B, C, D, E, F>
    Fn6.Consumer<A, B, C, D, E, F> Consumer(final Fn6.Consumer<A, B, C, D, E, F> expr) {
        return notNull(expr);
    }

    /** Constructs a consumer of arity 7. */
    public static <A, B, C, D, E, F, G>
    Fn7.Consumer<A, B, C, D, E, F, G> Consumer(final Fn7.Consumer<A, B, C, D, E, F, G> expr) {
        return notNull(expr);
    }

    /** Constructs a consumer of arity 8. */
    public static <A, B, C, D, E, F, G, H>
    Fn8.Consumer<A, B, C, D, E, F, G, H> Consumer(final Fn8.Consumer<A, B, C, D, E, F, G, H> expr) {
        return notNull(expr);
    }

    // ---------------------------------------------------------

    /// IDENTITY OPERATOR.

    /**
     * Returns first-class function identity operator.
     */
    public static <A> Fn1<A, A> identity() {
        final class Identity implements Fn1<A, A> {
            private static final Identity operator = new Identity();
            private Identity() { /* singleton */ }

            /**
             * Returns the given argument.
             */
            @Override
            public A onApply(final A a) {
                return a;
            }
        }
        return Identity.operator;
    }

    // ----------------------------------------------------------

    /// COMPOSITION OPERATOR.

    /**
     * Partially apply the composition operator to the given function operand.
     *
     * @param <A> type of argument of the 1st function operand.
     * @param <B> type of argument of the 2nd function operand.
     * @param <R> type of result of the composed function.
     * @return unary function composition operator.
     */
    static <A, B, R> Fn1<Fn1<? super B, ? extends R>, Fn1<A, R>> compose(final Fn1<? super A, ? extends B> fa) {
        return Fn.<A, B, R> compose().apply(notNull(fa));
    }

    /**
     * Apply the composition operator to the two given function operands.
     *
     * @param fa  1st function operand to composed with 2nd operand.
     * @param fb  2nd function operand to composed with 1st operand.
     * @param <A> type of argument of the 1st function operand.
     * @param <B> type of argument of the 2nd function operand.
     * @param <R> type of result of the composed function.
     * @return composed function.
     */
    static <A, B, R> Fn1<A, R> compose(final Fn1<? super A, ? extends B> fa, final Fn1<? super B, ? extends R> fb) {
        return Fn.<A, B, R> compose().apply(notNull(fa), notNull(fb));
    }

    /**
     * Returns first-class composition operator.
     *
     * @param <A> type of argument of the 1st function operand.
     * @param <B> type of argument of the 2nd function operand.
     * @param <R> type of result of the composed function.
     * @return binary function composition operator.
     */
    static <A, B, R> Fn2<Fn1<? super A, ? extends B>, Fn1<? super B, ? extends R>, Fn1<A, R>> compose() {
        final class Compose implements Fn2<Fn1<? super A, ? extends B>, Fn1<? super B, ? extends R>, Fn1<A, R>> {
            private static final Compose operator = new Compose();
            private Compose() { /* singleton */ }

            /**
             * Returns a composed function that applies the 1st function
             * operand to its input, and then applies the 2nd function
             * operand to the result.
             *
             * @param fa 1st function operand.
             * @param fb 2nd function operand.
             * @return composed function.
             * @throws Throwable permits any exception to be thrown.
             */
            @Override
            public Fn1<A, R> onApply(final Fn1<? super A, ? extends B> fa,
                                     final Fn1<? super B, ? extends R> fb) throws Throwable {
                return x -> fb.onApply(fa.onApply(x));
            }
        }
        return Compose.operator;
    }

    // ---------------------------------------------------------

    /// CONSTANTLY OPERATOR.

    /**
     * Partially applies constantly operator by passing the first argument.
     *
     * @param a   1st argument.
     * @param <A> type of first argument and result.
     * @param <B> type second (ignored) argument.
     * @return partially applied operator.
     */
    public static <A, B> Fn1<B, A> constantly(final A a) {
        return Fn.<A, B> constantly().apply(a);
    }

    /**
     * Applies constantly operator to given arguments-
     *
     * @param a   1st argument.
     * @param b   2nd argument.
     * @param <A> type of first argument and result.
     * @param <B> type second (ignored) argument.
     * @return 1st argument.
     */
    public static <A, B> A constantly(final A a, final B b) {
        return Fn.<A, B> constantly().apply(a, b);
    }

    /**
     * Returns first-class binary constantly operator.
     *
     * @param <A> type of first argument and result.
     * @param <B> type second (ignored) argument.
     * @return constantly operator.
     */
    public static <A, B> Fn2<A, B, A> constantly() {
        final class Constantly implements Fn2<A, B, A> {
            private static final Constantly operator = new Constantly();
            private Constantly() { /* singleton */ }

            /**
             * Returns the 1st of the 2 given operands.
             *
             * @param a 1st operand.
             * @param b 2nd operand.
             * @return 1st operand.
             */
            @Override
            public A onApply(final A a, final B b) {
                return a;
            }
        }
        return Constantly.operator;
    }
}
