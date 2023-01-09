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

package magma.exa.value.scalar;

import magma.exa.adt.typelevel.signature.$Fn0;
import magma.exa.adt.typelevel.signature.$Fn1;
import magma.exa.base.Hash;
import magma.exa.base.Narrow;
import magma.exa.base.contract.Assert;
import magma.exa.control.adt.traversal.ICursor;
import magma.exa.control.adt.traversal.ITraversable;
import magma.exa.control.adt.traversal.ITraverser;
import magma.exa.control.traversal.Traversal;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn0;
import magma.exa.control.function.Fn1;
import magma.exa.data.Array;
import magma.exa.data.adt.IFlow;
import magma.exa.data.adt.IOption;
import magma.exa.data.adt.operation.Operation;
import magma.exa.data.index.Ix1D;
import magma.exa.value.Unit;

/**
 * Magma {@code long} type companion.
 */
public enum I64 {
    ;
    /** Constant of the smallest representable value of {@code I64} type. */
    public static final long MIN = Long.MIN_VALUE + 1;

    /** Constant of the largest representable value of {@code I64} type. */
    public static final long MAX = Long.MAX_VALUE;

    /** Constant holding the » Not-a-Number (NaN) « value of {@code I64} type. */
    public static final long NaN = Long.MIN_VALUE;

    /** Determines whether the given value is the Not-a-Number (NaN) value. */
    public static boolean isNaN(final long value) { return I64.NaN == value; }

    // ----------------------------------------------------------

    /// FUNCTION CONSTRUCTION.

    /// Factory methods to construct {@code long} specialized functions
    /// from method references or lambda expressions see JLS §15.13, §15.27.

    /** Constructs a specialized {@code long -> R} function. */
    public static <R> To<R> to(final To<R> fn) { return fn; }

    /** Constructs a specialized {@code long -> byte} function. */
    public static ToI8 ToI8(final ToI8 fn) { return fn; }

    /** Constructs a specialized {@code long -> short} function. */
    public static ToI16 ToI16(final ToI16 fn) { return fn; }

    /** Constructs a specialized {@code long -> char} function. */
    public static ToC16 ToC16(final ToC16 fn) { return fn; }

    /** Constructs a specialized {@code long -> int} function. */
    public static ToI32 ToI32(final ToI32 fn) { return fn; }

    /** Constructs a specialized {@code long -> long} function. */
    public static ToI64 ToI64(final ToI64 fn) { return fn; }

    /** Constructs a specialized {@code long -> float} function. */
    public static ToF32 ToF32(final ToF32 fn) { return fn; }

    /** Constructs a specialized {@code long -> double} function. */
    public static ToF64 ToF64(final ToF64 fn) { return fn; }

    /** Constructs a specialized {@code long} predicate. */
    public static Predicate Predicate(final Predicate pd) { return pd; }

    /** Constructs a specialized {@code long} supplier. */
    public static Supplier Supplier(final Supplier sp) { return sp; }

    /** Constructs a specialized {@code long} consumer. */
    public static Consumer Consumer(final Consumer cn) { return cn; }

    // ----------------------------------------------------------
    //  I64.SUPPLIER
    // ----------------------------------------------------------

    /**
     * Function that takes no argument and returns a {@code long} result.
     * <p>
     * Acts as a supplier of {@code long} values, and makes no guarantees
     * for invocations returning a new or different result.
     */
    @FunctionalInterface
    public interface Supplier extends $Fn0<Long> {

        /**
         * Invokes this supplier with no argument.
         *
         * @return supplier result.
         * @throws Throwable permits any exception to be thrown.
         */
        long onApply() throws Throwable;

        /**
         * Applies this supplier (to no argument).
         */
        default long apply() {
            try {
                return onApply();
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  I64.CONSUMER
    // ----------------------------------------------------------

    /**
     * Consumer operation that takes a single {@code long} argument
     * and returns no value. Consumer operates via side effects.
     */
    @FunctionalInterface
    public interface Consumer extends $Fn1<Long, Unit> {

        /** The effect-less consumer. */
        Consumer empty = Const.empty;

        /**
         * Performs this operation with the given {@code long} argument.
         *
         * @param val consumer argument.
         */
        void onAccept(long val) throws Throwable;

        /**
         * Applies this consumer to the given {@code long} argument.
         *
         * @param val consumer argument.
         */
        default void accept(final long val) {
            try {
                onAccept(val);
            } catch (final Throwable ex) {
                Throw.sneaky(ex);
            }
        }

        /**
         * Constant consumer functions.
         */
        enum Const implements Consumer {
            empty { public void onAccept(long val) { } }
        }
    }

    // ----------------------------------------------------------
    //  I64.PREDICATE
    // ----------------------------------------------------------

    /**
     * Predicate (boolean-valued function) that takes a single {@code long} argument.
     */
    @FunctionalInterface
    public interface Predicate extends $Fn1<Long, Boolean> {

        /** Predicate that always evaluates {@code true}. */
        Predicate True  = Const.True;

        /** Predicate that always evaluates {@code false}. */
        Predicate False = Const.False;

        /**
         * Evaluates this predicate with the given {@code long} argument.
         *
         * @param val predicate argument.
         * @return true iff this predicate is satisfied.
         * @throws Throwable permits any exception to be thrown.
         */
        boolean onEval(long val) throws Throwable;

        /**
         * Applies this predicate to the given {@code long} argument.
         *
         * @param val predicate argument.
         * @return true iff this predicate is satisfied.
         */
        default boolean eval(final long val) {
            try {
                return onEval(val);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }

        /**
         * Constant predicate functions.
         */
        enum Const implements Predicate {
            True  { public boolean onEval(long __) { return true;  } },
            False { public boolean onEval(long __) { return false; } }
        }
    }

    // ----------------------------------------------------------
    //  I64.TO
    // ----------------------------------------------------------

    /**
     * Function that takes a single {@code long} argument
     * and returns a result of type {@code R}.
     *
     * @param <R> type of function result.
     */
    @FunctionalInterface
    public interface To<R> extends $Fn1<Long, R> {

        /**
         * Invokes this function with the given {@code long} argument.
         *
         * @param val function argument.
         * @return result of type {@code R}.
         * @throws Throwable permits any exception to be thrown.
         */
        R onApply(long val) throws Throwable;

        /**
         * Applies this function to the given {@code long} argument.
         *
         * @param val function argument.
         * @return result of type {@code R}.
         */
        default R apply(final long val) {
            try {
                return onApply(val);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  I64.TO-I8
    // ----------------------------------------------------------

    /**
     * Function that takes a single {@code long} argument
     * and returns a result of type {@code boolean}.
     */
    @FunctionalInterface
    public interface ToBool extends $Fn1<Long, Boolean> {

        /**
         * Invokes this function with the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code boolean} result.
         * @throws Throwable permits any exception to be thrown.
         */
        boolean onApply(long val) throws Throwable;

        /**
         * Applies this function to the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code boolean} result.
         */
        default boolean apply(final long val) {
            try {
                return onApply(val);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  I64.TO-I8
    // ----------------------------------------------------------

    /**
     * Function that takes a single {@code long} argument
     * and returns a result of type {@code byte}.
     */
    @FunctionalInterface
    public interface ToI8 extends $Fn1<Long, Byte> {

        /**
         * Invokes this function with the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code byte} result.
         * @throws Throwable permits any exception to be thrown.
         */
        byte onApply(long val) throws Throwable;

        /**
         * Applies this function to the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code byte} result.
         */
        default byte apply(final long val) {
            try {
                return onApply(val);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  I64.TO-I16
    // ----------------------------------------------------------

    /**
     * Function that takes a single {@code long} argument
     * and returns a result of type {@code short}.
     */
    @FunctionalInterface
    public interface ToI16 extends $Fn1<Long, Short> {

        /**
         * Invokes this function with the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code short} result.
         * @throws Throwable permits any exception to be thrown.
         */
        short onApply(long val) throws Throwable;

        /**
         * Applies this function to the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code short} result.
         */
        default short apply(final long val) {
            try {
                return onApply(val);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  I64.TO-C16
    // ----------------------------------------------------------

    /**
     * Function that takes a single {@code long} argument
     * and returns a result of type {@code char}.
     */
    @FunctionalInterface
    public interface ToC16 extends $Fn1<Long, Character> {

        /**
         * Invokes this function with the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code char} result.
         * @throws Throwable permits any exception to be thrown.
         */
        char onApply(long val) throws Throwable;

        /**
         * Applies this function to the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code char} result.
         */
        default char apply(final long val) {
            try {
                return onApply(val);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  I64.TO-I32
    // ----------------------------------------------------------

    /**
     * Function that takes a single {@code long} argument
     * and returns a result of type {@code int}.
     */
    @FunctionalInterface
    public interface ToI32 extends $Fn1<Long, Integer> {

        /**
         * Invokes this function with the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code int} result.
         * @throws Throwable permits any exception to be thrown.
         */
        int onApply(long val) throws Throwable;

        /**
         * Applies this function to the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code int} result.
         */
        default int apply(final long val) {
            try {
                return onApply(val);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  I64.TO-I64
    // ----------------------------------------------------------

    /**
     * Function that takes a single {@code long} argument
     * and returns a result of type {@code long}.
     */
    @FunctionalInterface
    public interface ToI64 extends $Fn1<Long, Long> {

        /**
         * Invokes this function with the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code long} result.
         * @throws Throwable permits any exception to be thrown.
         */
        long onApply(long val) throws Throwable;

        /**
         * Applies this function to the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code long} result.
         */
        default long apply(final long val) {
            try {
                return onApply(val);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  I64.TO-F32
    // ----------------------------------------------------------

    /**
     * Function that takes a single {@code long} argument
     * and returns a result of type {@code float}.
     */
    @FunctionalInterface
    public interface ToF32 extends $Fn1<Long, Float> {

        /**
         * Invokes this function with the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code float} result.
         * @throws Throwable permits any exception to be thrown.
         */
        float onApply(long val) throws Throwable;

        /**
         * Applies this function to the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code float} result.
         */
        default float apply(final long val) {
            try {
                return onApply(val);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  I64.TO-F64
    // ----------------------------------------------------------

    /**
     * Function that takes a single {@code long} argument
     * and returns a result of type {@code double}.
     */
    @FunctionalInterface
    public interface ToF64 extends $Fn1<Long, Double> {

        /**
         * Invokes this function with the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code double} result.
         * @throws Throwable permits any exception to be thrown.
         */
        double onApply(long val) throws Throwable;

        /**
         * Applies this function to the given {@code long} argument.
         *
         * @param val function argument.
         * @return {@code double} result.
         */
        default double apply(final long val) {
            try {
                return onApply(val);
            } catch (final Throwable ex) {
                return Throw.sneaky(ex);
            }
        }
    }

    // ----------------------------------------------------------
    //  I64.TRAVERSER
    // ----------------------------------------------------------

    /**
     * Encapsulates the unidirectional traversal of an underlying source of values.
     * <p>
     * The traverser is designed as an 'internal iterator' that accepts
     * a (higher order) action provided by the traversal client.
     */
    public interface Traverser extends ITraverser<Long, Consumer> {

        // ----------------------------------------------------------
        //  I64.TRAVERSER.INDEXED
        // ----------------------------------------------------------

        /**
         * The Indexed Traverser refines {@link Traverser} by providing
         * an additional indexed variant for each traverser operation.
         */
        interface Indexed extends ITraverser.Indexed<Long, Consumer>, Traverser {

            /**
             * {@inheritDoc}
             */
            @Override
            default boolean tryNext(final Consumer action) {
                return tryNextIndexed(ICursor.lift1D(action));
            }
        }

        // ----------------------------------------------------------
        //  I64.TRAVERSER.DUPLEX
        // ----------------------------------------------------------

        /**
         * The Duplex Traverser refines {@link Traverser} with the
         * ability to additionally traverse in the reverse direction.
         */
        interface Duplex extends ITraverser.Duplex<Long, Consumer>, Traverser {

            // ----------------------------------------------------------
            //  I64.TRAVERSER.DUPLEX.INDEXED
            // ----------------------------------------------------------

            /**
             * The Indexed Duplex Traverser refines {@link Traverser.Duplex} by providing an
             * additional indexed variant for each traverser operation in reverse direction.
             */
            interface Indexed extends ITraverser.Duplex.Indexed<Long, Consumer>,
                    Traverser.Indexed, Traverser.Duplex {

                /**
                 * {@inheritDoc}
                 */
                @Override
                default boolean tryPrev(final Consumer action) {
                    return tryPrevIndexed(ICursor.lift1D(action));
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        default java.util.Iterator<Long> iterator() {
            // Weak iterator contract...
            final class It implements java.util.Iterator<Long>, Consumer {
                private Long next;
                @Override public boolean hasNext() { return tryNext(this); }
                @Override public Long next() { return next; }
                @Override public void onAccept(final long next) {
                    this.next = next;
                }
            }
            return new It();
        }

        // ----------------------------------------------------------
        //  I64.TRAVERSER.EMPTY
        // ----------------------------------------------------------

        /**
         * The empty traverser.
         */
        Duplex.Indexed empty = Empty.traverser;

        /**
         * Empty traverser type.
         */
        enum Empty implements Duplex.Indexed {
            traverser;
            public boolean  tryNext(Consumer a) { Assert.notNull(a); return false; }
            public boolean  tryPrev(Consumer a) { Assert.notNull(a); return false; }
            public void     forNext(Consumer a) { Assert.notNull(a); }
            public void     forPrev(Consumer a) { Assert.notNull(a); }
            public long     whileNext(Until<Control, Consumer> d) { Assert.notNull(d); return State.S_INIT; }
            public long     whilePrev(Until<Control, Consumer> d) { Assert.notNull(d); return State.S_INIT; }
            public void     forNextIndexed(Ix1D<Consumer> a) { Assert.notNull(a); }
            public void     forPrevIndexed(Ix1D<Consumer> a) { Assert.notNull(a); }
            public boolean  tryNextIndexed(Ix1D<Consumer> a) { Assert.notNull(a); return false; }
            public boolean  tryPrevIndexed(Ix1D<Consumer> a) { Assert.notNull(a); return false; }
            public long     whileNextIndexed(Until<Control, Ix1D<Consumer>> d) { Assert.notNull(d); return State.S_INIT; }
            public long     whilePrevIndexed(Until<Control, Ix1D<Consumer>> d) { Assert.notNull(d); return State.S_INIT; }
        }
    }

    // ----------------------------------------------------------
    //  I64.TRAVERSABLE
    // ----------------------------------------------------------

   /**
    * Exposes a {@link Traverser}, which enables traversal over a source of values.
    */
    public interface Traversable extends Iterable<Long>, ITraversable<Consumer> {

        /**
         * Returns an traverser.
         */
        Traverser traverser();

        /**
         * {@inheritDoc}
         */
        @Override
        default java.util.Iterator<Long> iterator() {
            return traverser().iterator();
        }
    }

    // ----------------------------------------------------------
    //  I64.FLOW
    // ----------------------------------------------------------

    /**
     * _______________
     * ___  ____/__  /________      __
     * __  /_   __  /_  __ \_ | /| / /
     * _  __/   _  / / /_/ /_ |/ |/ /
     * /_/      /_/  \____/____/|__/ADT
     *
     * Foundational abstract data type for operating on one or more values.
     * Compose basic query-, fold-, aggregation-, and conversion-operations.
     *
     * @param <U> unification parameter.
     */
    public interface Flow<U extends IFlow<?, U>> extends IFlow<Long, U>,

            Traversable,

            // ----------------------------------------------------------

            Operation.Query.Membership.OfI64<U>,

            Operation.Query.Quantify.OfI64<U>,

            // ----------------------------------------------------------

            Operation.Fold.FoldLeft.OfI64<U>,

            Operation.Fold.Collect.OfI64<U>,

            // ----------------------------------------------------------

            Operation.Convert.ToArray.OfI64<U>,

            Operation.Convert.ToCollection.OfI64<U>
    {
        /**
         * Determines whether the given {@code long} value is contained.
         *
         * @param val value whose presence is to be determined.
         * @return true iff the given value is present.
         */
        @Override
        default boolean contains(final long val) {
            return ICursor.exited(traverser().whileNext(control -> x -> {
                if (x == val) {
                    control.exit();
                }
            }));
        }

        /**
         * Determines whether any {@code long} value satisfies the given predicate.
         * <p>
         * This corresponds to evaluating the existential quantification of
         * the given predicate over the values {@code '∃(x) ∈ Flow.OfI64 P(x)'}.
         * The predicate is not evaluated for all values if it is not required
         * to determine the result. If no values are present, false is returned
         * and the predicate is not evaluated.
         *
         * @param predicate to be evaluated on contained values.
         * @return true if the predicate is true for any value.
         */
        @Override
        default boolean anyMatch(final Predicate predicate) {
            return ICursor.exited(traverser().whileNext(control -> x -> {
                if (predicate.onEval(x)) {
                    control.exit();
                }
            }));
        }

        /**
         * Determines whether all {@code long} values satisfy the given predicate.
         * <p>
         * This corresponds to the evaluation of the universal quantification
         * of the predicate over the values {@code '∀(x) ∈ Flow.OfI64 P(x)'}.
         * The predicate is not evaluated for all values if it is not required
         * to determine the result. If no values are present, the quantification
         * is said to be 'vacuously satisfied', and always true is returned,
         * independent of P(x).
         *
         * @param predicate to be evaluated on contained values.
         * @return true if the predicate is satisfied for all values,
         *         or no value is present.
         */
        @Override
        default boolean allMatch(final Predicate predicate) {
            return ICursor.completed(traverser().whileNext(control -> x -> {
                if (!predicate.onEval(x)) {
                    control.exit();
                }
            }));
        }

        /**
         * Determines whether no {@code long} values satisfy the given predicate.
         * <p>
         * This corresponds to the evaluation of the universal quantification of
         * the negated predicate over the array {@code '∀(x) ∈ Flow.OfI64 ¬P(x)'}.
         * The predicate is not evaluated for all values if it is not required
         * to determine the result. If no values are present, the quantification
         * is said to be 'vacuously satisfied', and always true is returned,
         * independent of P(x).
         *
         * @param predicate to be evaluated on contained values.
         * @return true if the predicate is never satisfied for all values,
         *         or no value is present.
         */
        @Override
        default boolean noneMatch(final Predicate predicate) {
            return ICursor.completed(traverser().whileNext(control -> x -> {
                if (predicate.onEval(x)) {
                    control.exit();
                }
            }));
        }

        /**
         * Performs a left fold on the contained {@code long} values using the
         * given combining function. If no values are present, the initial value
         * accumulator is returned.
         * <p>
         * A left fold operation starts with the given initial value and applies
         * the given combining function to the current accumulator state and each
         * contained value, from left to right, to compute the accumulated result.
         *
         * @param initial  accumulator value.
         * @param combiner to integrate values into the accumulator.
         * @return accumulated result of type {@code Z}.
         */
        @Override
        default <Z> Z foldLeft(final Z initial, final Fn1<Z, To<Z>> combiner) {
            Assert.notNull(initial, combiner);
            final class Fold implements Consumer {
                private Z state = initial;
                {
                    traverser().forNext(this);
                }
                @Override
                public void onAccept(final long value) throws Throwable {
                    this.state = combiner.onApply(state).onApply(value);
                }
            }
            return new Fold().state;
        }

        /**
         * Returns a result container of type {@code C} populated with the
         * contained {@code long} values. The container is created using
         * the given factory. The values are integrated using the given
         * accumulator function by updating the state of the container
         * (instead of replacing the result).
         *
         * @param factory     used to instantiate the result container.
         * @param accumulator function to fold a value into the container.
         * @param <C>         type of container.
         * @return container of type {@code C}.
         */
        @Override
        default <C> C collect(final I32.To<? extends C> factory,
                              final Fn1<C, Consumer> accumulator) {
            Assert.notNull(factory, accumulator);
            final class Collect implements Consumer {
                private final C state;
                {
                    state = factory.apply(Narrow.I32(count()));
                    traverser().forNext(this);
                }
                @Override
                public void onAccept(final long value) throws Throwable {
                    accumulator.onApply(state).onAccept(value);
                }
            }
            return new Collect().state;
        }

        // ----------------------------------------------------------
        //  I64.FLOW.PRODUCER
        // ----------------------------------------------------------

        /**
         * A producer flow refines {@link Flow} by adding transformation
         * and transmutation operations.
         *
         * @param <U> unification parameter.
         */
        interface Producer<U extends IProducer<?, U>> extends Flow<U>,

                IProducer<Long, U>,

                // ----------------------------------------------------------

                Transform.Peek.OfI64<U>,

                Transform.Filter.OfI64<U>,

                // ----------------------------------------------------------

                Transmute.Map.OfI64<U>,

                Transmute.FlatMap.OfI64<U> {
        }
    }

    // ----------------------------------------------------------
    //  I64.OPTION
    // ----------------------------------------------------------

    /**
     * The empty option.
     */
    public static final Option none = Option.None.option;

    /**
     * Constructs an option with the given value.
     *
     * @param value to be wrapped in option.
     * @return optional value.
     */
    public static Option some(final long value) {
        return new Option.Some(value);
    }

    /**
     * Constructs an option depending on the given condition. If the condition
     * is true, an option with the given value is returned, otherwise the empty
     * option is returned.
     *
     * @param condition to be true to return a present option.
     * @param value to be wrapped in option.
     * @return optional value.
     */
    public static Option when(final boolean condition, final long value) {
        return condition ? some(value) : none;
    }

    /**
     * Constructs an option depending on the given condition. If the condition
     * is true, the lazy value gets evaluated, and the option with the result is
     * returned, otherwise the empty option is returned, given thunk stays un-
     * evaluated
     *
     * @param condition to be true to return a present option.
     * @param lazy evaluated value (thunk).
     * @return optional value.
     */
    public static Option when(final boolean condition, final Supplier lazy) {
        return condition ? some(lazy.apply()) : none;
    }

    /**
     * _______        __________
     * __  __ \_________  /___(_)____________
     * _  / / /__  __ \  __/_  /_  __ \_  __ \
     * / /_/ /__  /_/ / /_ _  / / /_/ /  / / /
     * \____/ _  .___/\__/ /_/  \____//_/ /_/ TYPE
     *        /_/
     *
     * An optional value: every Option is either {@link Some} and contains
     * a value, or {@link None}, and does not. Option types are very common
     * in magma code and has many uses.
     * <p>
     * Most idiomatic way to operate option is to treat it as a container
     * or monad and use filter, map, flatMap, or forEach.
     */
    public interface Option extends IOption<Long>,

            Operation.Access.Value.OfI64<IOption<?>>,

            Operation.Transmute.PolyMap.OfI64<IOption<?>>,

            Operation.Transmute.PolyFlatMap.OfI64<IOption<?>>,

            Flow.Producer<IOption<?>>
    {
        /**
         * Determines whether some value is present.
         */
        boolean isPresent();

        /**
         * Determines whether this option is empty.
         */
        boolean isEmpty();

        // ----------------------------------------------------------

        /**
         * Returns 1 for Some, and 0 for None.
         */
        long count();

        // ----------------------------------------------------------

        /// CONTAINMENT.

        /**
         * Determines whether some present value is equal to the given value.
         *
         * @param value whose presence is to be determined.
         * @return true iff the value is contained.
         */
        boolean contains(long value);

        // ----------------------------------------------------------

        /// QUANTIFICATION.

        /**
         * Determines whether some present value satisfies the given predicate.
         * <p>
         * Evaluates the existential quantification of the given predicate over some
         * present value, ∃(x) ∈ Option P(x). On the empty option false is returned
         * without evaluating the predicate.
         *
         * @param predicate to be evaluated for some present value.
         * @return true for a matching value.
         */
        boolean anyMatch(Predicate predicate);

        /**
         * Determines whether some present value satisfies the given predicate.
         * <p>
         * Evaluates the universal quantification of the given predicate over some
         * present value, ∀(x) ∈ Option P(x). On the empty option true is returned
         * without evaluating the predicate. The quantification is said to be
         * 'vacuously satisfied' and is always true, independent of P(x).
         *
         * @param predicate to be evaluated for some present value.
         * @return true for a matching value or empty option.
         */
        boolean allMatch(Predicate predicate);

        /**
         * Determines whether some present value does not satisfy the given predicate.
         * <p>
         * Evaluates universal quantification of the given negated predicate over some
         * present value, ∀(x) ∈ Option ¬P(x). On the empty option true is returned
         * without evaluating the predicate. The quantification is said to be
         * 'vacuously satisfied' and is always true, independent of P(x).
         *
         * @param predicate to be evaluated for some present value.
         * @return true for a matching value or empty option.
         */
        boolean noneMatch(Predicate predicate);

        // ----------------------------------------------------------

        /**
         * Match on some present value and the empty option. Type-safe convergence.
         *
         * @param some function to be applied on some present value.
         * @param none function to be applied on the empty option.
         * @param <R> type of match result.
         * @return match result.
         */
        <R> R match(Fn1<? super Long, ? extends R> some,
                    Fn0<? extends R> none);

        /**
         * If a value is present, performs the given action with the present value,
         * otherwise does nothing.
         *
         * @param action to be performed, if a value is present.
         */
        default void ifPresent(final Consumer action) {
            java.util.Objects.requireNonNull(action);
            if (isPresent()) action.accept(value());
        }

        // ----------------------------------------------------------

        /**
         * Performs the given (side effect) action on some present value and returns
         * this options, otherwise the empty option is returned, without performing
         * the given action.
         *
         * @param action to be preformed on some present value.
         * @return this option.
         */
        Option peek(Consumer action);

        // ----------------------------------------------------------

        /// FILTER TRANSFORMATION.

        /**
         * Returns this option if some present value satisfies the given predicate,
         * otherwise the empty option is returned.
         *
         * @param predicate to be evaluated on some present value.
         * @return filtered option.
         */
        Option filter(Predicate predicate);

        /**
         * Returns this option iff some present value satisfies the given predicate,
         * otherwise the empty option is returned.
         *
         * @param predicate to be evaluated on some present value.
         * @return filtered option.
         */
        Option filterNot(Predicate predicate);

        // ----------------------------------------------------------

        /**
         * Returns an optional value of type {@code A} obtained by applying the
         * given mapper function to some contained value.
         *
         * @param mapper function to be applied on each value.
         * @return optional value of type {@code A}.
         */
        <A> magma.exa.data.Option<A> map(To<? extends A> mapper);

        /**
         * Returns an optional {@code int} obtained by applying the given mapper
         * function to some contained value.
         *
         * @param mapper function to be applied on each value.
         * @return optional {@code int}.
         */
        I32.Option mapI32(ToI32 mapper);

        /**
         * Returns an optional {@code long} obtained by applying the given mapper
         * function to some contained value.
         *
         * @param mapper function to be applied on each value.
         * @return optional {@code long}.
         */
        Option mapI64(ToI64 mapper);

        /**
         * Returns an optional {@code float} obtained by applying the given mapper
         * function to some contained value.
         *
         * @param mapper function to be applied on each value.
         * @return optional {@code float}.
         */
        F32.Option mapF32(ToF32 mapper);

        /**
         * Returns an optional {@code double} obtained by applying the given mapper
         * function to some contained value.
         *
         * @param mapper function to be applied on Some value.
         * @return optional {@code double}.
         */
        F64.Option mapF64(ToF64 mapper);

        // ----------------------------------------------------------

        /**
         * Returns an optional value of type {@code A} containing the result obtained
         * by applying the given mapper function to some contained value and wrapping
         * the first (optional) value returned by the given iterable.
         *
         * @param mapper function to be applied on some contained value.
         * @return optional value of type {@code A}.
         */
        <A> magma.exa.data.Option<A> flatMap(To<? extends Iterable<? extends A>> mapper);

        /**
         * Returns an optional {@code int} containing the result obtained by applying
         * the given mapper function to some contained value and wrapping the first
         * (optional) {@code int} value produced by the given iterable.
         *
         * @param mapper function to be applied on some contained value.
         * @return optional {@code int}.
         */
        I32.Option flatMapI32(To<? extends Iterable<Integer>> mapper);

        /**
         * Returns an optional {@code long} containing the result obtained by applying
         * the given mapper function to some contained value and wrapping the first
         * (optional) {@code long} value produced by the given iterable.
         *
         * @param mapper function to be applied on some contained value.
         * @return optional {@code long}.
         */
        Option flatMapI64(To<? extends Iterable<Long>> mapper);

        /**
         * Returns an optional {@code float} containing the result obtained by applying
         * the given mapper function to some contained value and wrapping the first
         * (optional) {@code float} value produced by the given iterable.
         *
         * @param mapper function to be applied on some contained value.
         * @return optional {@code float}.
         */
        F32.Option flatMapF32(To<? extends Iterable<Float>> mapper);

        /**
         * Returns an optional {@code double} containing the result obtained by applying
         * the given mapper function to some contained value and wrapping the first
         * (optional) {@code double} value produced by the given iterable.
         *
         * @param mapper function to be applied on some contained value.
         * @return optional {@code double}.
         */
        F64.Option flatMapF64(To<? extends Iterable<Double>> mapper);

        // ----------------------------------------------------------

        /**
         * Returns either some present value or throws an exception.
         */
        long value();

        /**
         * Returns either some present value or throws an exception.
         */
        default long get() { return value(); }

        // ----------------------------------------------------------

        /**
         * Returns a value iff present or the given other value.
         *
         * @param other to be returned iff no value is present.
         * @return present- or another value.
         */
        long orElse(long other);

        /**
         * Returns a value iff present or another lazy computed value.
         *
         * @param other value to be computed iff required.
         * @return present- or another computed value.
         */
        long orElse(Supplier other);

        // ----------------------------------------------------------

        /**
         * Returns either some present value or throws an exception.
         */
        long orElseThrow();

        /**
         * Returns either some present value or throws the lazy supplied exception.
         *
         * @param exception that is lazy provided iff required.
         * @param <X>      type of the lazy supplied exception.
         * @return present value.
         * @throws X exception that is lazily passed and thrown iff empty.
         */
        <X extends Throwable> long orElseThrow(magma.exa.value.Lazy<? extends X> exception) throws X;

        // ----------------------------------------------------------

        /**
         * Returns either this option if some value is present, otherwise
         * the given default option is returned. The passed argument is
         * eagerly evaluated. If the default option is the result of some
         * invocation then lazy supplied variant is to be preferred.
         *
         * @param other option to be returned iff empty.
         * @return this or the given option.
         */
        Option or(Option other);

        /**
         * Returns either this option if some value is present, otherwise
         * the lazy evaluated option obtained by the lazy evaluated supplier
         * is returned.
         *
         * @param other option to be supplied iff required.
         * @return this or lazy passed option.
         */
        Option or(magma.exa.value.Lazy<? extends Option> other);

        // ----------------------------------------------------------

        /**
         * Folds all values contained in this flow by using the given operator.
         * If this flow is empty, the initial value is returned.
         * <p>
         * The fold operation starts with the given initial accumulator value and
         * applies the given combiner to the current accumulator value and each
         * next value in this flow, from left to right, to compute the final
         * accumulator value.
         *
         * @param initial accumulator value to start with.
         * @param combiner function to integrate a value into the accumulator value.
         * @return accumulated value.
         */
        <Z> Z foldLeft(Z initial, Fn1<Z, To<Z>> combiner);

        // ----------------------------------------------------------

        /**
         * Performs a mutable accumulation on the values in this flow. Such an
         * accumulation returns a mutable result container, into which values are
         * included by updating the state of the container instead of replacing
         * the result.
         *
         * @param factory    supplier that creates the mutable result container.
         * @param accumulator function to fold a value into the result container.
         * @param <C>        type of the result container.
         * @return result of accumulation.
         */
        <C> C collect(I32.To<? extends C> factory, Fn1<C, Consumer> accumulator);

        // ----------------------------------------------------------
        //  I64.OPTION.SOME
        // ----------------------------------------------------------

        /**
         * The populated option that contains a value.
         */
        @SuppressWarnings("ConstantConditions")
        final class Some implements Option {
            private final long value;

            Some(final long value) {
                this.value = value;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean isPresent() {
                return true;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean isEmpty() {
                return false;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public long count() {
                return 1L;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean contains(final long value) {
                return this.value == value;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean anyMatch(final Predicate predicate) {
                return predicate.eval(value);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean allMatch(final Predicate predicate) {
                return predicate.eval(value);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean noneMatch(final Predicate predicate) {
                return !predicate.eval(value);
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public <R> R match(final Fn1<? super Long, ? extends R> some,
                               final Fn0<? extends R> none) {
                java.util.Objects.requireNonNull(none);
                return some.apply(value);
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public long value() {
                return value;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public long orElse(final long other) {
                return value;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public long orElse(final Supplier other) {
                java.util.Objects.requireNonNull(other);
                return value;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public long orElseThrow() {
                return value;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public <X extends Throwable> long orElseThrow(final magma.exa.value.Lazy<? extends X> exception) {
                java.util.Objects.requireNonNull(exception);
                return value;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public Option or(final Option other) {
                java.util.Objects.requireNonNull(other);
                return this;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Option or(final magma.exa.value.Lazy<? extends Option> other) {
                java.util.Objects.requireNonNull(other);
                return this;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public Option peek(final Consumer action) {
                action.accept(value);
                return this;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public Option filter(final Predicate predicate) {
                return predicate.eval(value) ? this : none;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Option filterNot(final Predicate predicate) {
                return !predicate.eval(value) ? this : none;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public <A> magma.exa.data.Option<A> map(final To<? extends A> mapper) {
                return  magma.exa.data.Option.some(mapper.apply(value));
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public I32.Option mapI32(final ToI32 mapper) {
                return I32.some(mapper.apply(value));
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Option mapI64(final ToI64 mapper) {
                return I64.some(mapper.apply(value));
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public F32.Option mapF32(final ToF32 mapper) {
                return F32.some(mapper.apply(value));
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public F64.Option mapF64(final ToF64 mapper) {
                return F64.some(mapper.apply(value));
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public <A> magma.exa.data.Option<A> flatMap(final To<? extends Iterable<? extends A>> mapper) {
                final var itr = mapper.apply(value);
                // Strict defensive on data being passed.
                if (null != itr) {
                    final var it = itr.iterator();
                    return magma.exa.data.Option.when(null != it && it.hasNext(), it.next());
                }
                return magma.exa.data.Option.none();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public I32.Option flatMapI32(final To<? extends Iterable<Integer>> mapper) {
                final var itr = mapper.apply(value);
                // Strict defensive on data being passed.
                if (null != itr) {
                    final var it = itr.iterator();
                    return I32.when(null != it && it.hasNext(), it.next());
                }
                return I32.none;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Option flatMapI64(final To<? extends Iterable<Long>> mapper) {
                final var itr = mapper.apply(value);
                // Strict defensive on data being passed.
                if (null != itr) {
                    final var it = itr.iterator();
                    return I64.when(null != it && it.hasNext(), it.next());
                }
                return I64.none;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public F32.Option flatMapF32(final To<? extends Iterable<Float>> mapper) {
                final var itr = mapper.apply(value);
                // Strict defensive on data being passed.
                if (null != itr) {
                    final var it = itr.iterator();
                    return F32.when(null != it && it.hasNext(), it.next());
                }
                return F32.none;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public F64.Option flatMapF64(final To<? extends Iterable<Double>> mapper) {
                final var itr = mapper.apply(value);
                // Strict defensive on data being passed.
                if (null != itr) {
                    final var it = itr.iterator();
                    return F64.when(null != it && it.hasNext(), it.next());
                }
                return F64.none;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public <Z> Z foldLeft(final Z initial, final Fn1<Z, To<Z>> combiner) {
                return combiner.apply(initial).apply(value);
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public <C> C collect(final I32.To<? extends C> factory, final Fn1<C, Consumer> accumulator) {
                final C container = factory.apply(1);
                accumulator.apply(container).accept(value);
                return container;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public Traverser traverser() {
                return Traversal.single(value);
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Option some)) {
                    return false;
                }
                return value == some.value();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int hashCode() {
                return Hash.code(value);
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                return '[' + String.valueOf(value) + ']';
            }
        }

        // ----------------------------------------------------------
        //  I64.OPTION.NONE
        // ----------------------------------------------------------

        /**
         * The empty option that has no value.
         */
        enum None implements Option {
            option;

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean isPresent() {
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean isEmpty() {
                return true;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public long count() {
                return 0L;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean contains(final long value) {
                return false;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean anyMatch(final Predicate predicate) {
                java.util.Objects.requireNonNull(predicate);
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean allMatch(final Predicate predicate) {
                java.util.Objects.requireNonNull(predicate);
                return true;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean noneMatch(final Predicate predicate) {
                java.util.Objects.requireNonNull(predicate);
                return true;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public <R> R match(final Fn1<? super Long, ? extends R> some,
                               final Fn0<? extends R> none) {
                java.util.Objects.requireNonNull(some);
                return none.apply();
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public long value() {
                throw new java.util.NoSuchElementException();
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public long orElse(final long other) {
                return other;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public long orElse(final Supplier other) {
                return other.apply();
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public long orElseThrow() {
                throw new java.util.NoSuchElementException();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public <X extends Throwable> long orElseThrow(final magma.exa.value.Lazy<? extends X> exception) throws X {
                throw exception.apply();
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public Option or(final Option other) {
                return java.util.Objects.requireNonNull(other);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Option or(final magma.exa.value.Lazy<? extends Option> other) {
                return java.util.Objects.requireNonNull(other.apply());
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public Option peek(final Consumer action) {
                java.util.Objects.requireNonNull(action);
                return none;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public Option filter(final Predicate predicate) {
                java.util.Objects.requireNonNull(predicate);
                return none;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Option filterNot(final Predicate predicate) {
                java.util.Objects.requireNonNull(predicate);
                return none;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public <A> magma.exa.data.Option<A> map(final To<? extends A> mapper) {
                java.util.Objects.requireNonNull(mapper);
                return  magma.exa.data.Option.none();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public I32.Option mapI32(final ToI32 mapper) {
                java.util.Objects.requireNonNull(mapper);
                return  I32.none;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Option mapI64(final ToI64 mapper) {
                java.util.Objects.requireNonNull(mapper);
                return  I64.none;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public F32.Option mapF32(final ToF32 mapper) {
                java.util.Objects.requireNonNull(mapper);
                return  F32.none;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public F64.Option mapF64(final ToF64 mapper) {
                java.util.Objects.requireNonNull(mapper);
                return  F64.none;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public <A> magma.exa.data.Option<A> flatMap(final To<? extends Iterable<? extends A>> mapper) {
                java.util.Objects.requireNonNull(mapper);
                return magma.exa.data.Option.none();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public I32.Option flatMapI32(final To<? extends Iterable<Integer>> mapper) {
                java.util.Objects.requireNonNull(mapper);
                return I32.none;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Option flatMapI64(final To<? extends Iterable<Long>> mapper) {
                java.util.Objects.requireNonNull(mapper);
                return I64.none;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public F32.Option flatMapF32(final To<? extends Iterable<Float>> mapper) {
                java.util.Objects.requireNonNull(mapper);
                return F32.none;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public F64.Option flatMapF64(final To<? extends Iterable<Double>> mapper) {
                java.util.Objects.requireNonNull(mapper);
                return F64.none;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public <Z> Z foldLeft(final Z initial, final Fn1<Z, To<Z>> combiner) {
                java.util.Objects.requireNonNull(combiner);
                return initial;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public <C> C collect(final I32.To<? extends C> factory, final Fn1<C, Consumer> accumulator) {
                java.util.Objects.requireNonNull(accumulator);
                return factory.apply(0);
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public Traverser traverser() {
                return Traverser.Empty.traverser;
            }

            // ----------------------------------------------------------

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                return "[]";
            }
        }
    }

    // ----------------------------------------------------------
    //  I64.LAZY
    // ----------------------------------------------------------

    /**
     * A lazy evaluated {@code long} value.
     */
    public interface Lazy extends Supplier {

        /**
         * Constructs a value whose content is a calculation deferred
         * until the first access; this is done with memoization to be
         * strict on subsequent accesses.
         *
         * @param thunk deferred computation until first access.
         * @return lazy value accessor.
         */
        static Lazy of(final Supplier thunk) {
            java.util.Objects.requireNonNull(thunk);
            final class LazyBox implements Lazy {
                private volatile boolean initialized = false;
                private long value;
                @Override
                public long onApply() throws Throwable {
                    if (!initialized) {
                        synchronized (this) {
                            if (!initialized) {
                                value = thunk.apply();
                                initialized = true;
                            }
                        }
                    }
                    return value;
                }
            }
            return new LazyBox();
        }
    }

    // ----------------------------------------------------------
    //  I64.RELATION
    // ----------------------------------------------------------

    /**
     * Relational operators.
     */
    public enum Relation implements To<Predicate> {
        EQ { @Override public Predicate onApply(final long val) { return x -> x == val; } },
        NE { @Override public Predicate onApply(final long val) { return x -> x != val; } },
        GT { @Override public Predicate onApply(final long val) { return x -> x >  val; } },
        GE { @Override public Predicate onApply(final long val) { return x -> x >= val; } },
        LT { @Override public Predicate onApply(final long val) { return x -> x <  val; } },
        LE { @Override public Predicate onApply(final long val) { return x -> x <= val; } }
    }


    // ----------------------------------------------------------

    public static Predicate equal        (final long val) { return Relation.EQ.apply(val); }
    public static Predicate notEqual     (final long val) { return Relation.NE.apply(val); }
    public static Predicate greaterThan  (final long val) { return Relation.GT.apply(val); }
    public static Predicate greaterEqual (final long val) { return Relation.GE.apply(val); }
    public static Predicate lessThan     (final long val) { return Relation.LT.apply(val); }
    public static Predicate lessEqual    (final long val) { return Relation.LE.apply(val); }

    // ----------------------------------------------------------

    public static Predicate any(final long... ary) {
        return (ary.length != 0) ? x -> Array.anyMatch(ary, equal(x)) : Predicate.False;
    }
    public static Predicate all(final long... ary) {
        return (ary.length != 0) ? x -> Array.allMatch(ary, equal(x)) : Predicate.True;
    }
}