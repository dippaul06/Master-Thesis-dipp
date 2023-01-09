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

package magma.exa.data;

import magma.exa.base.Force;
import magma.exa.base.Hash;
import magma.exa.control.function.Fn0;
import magma.exa.control.function.Fn1;
import magma.exa.control.function.Fn2;
import magma.exa.control.traversal.Traversal;
import magma.exa.control.traversal.Traverser;
import magma.exa.data.adt.IOption;
import magma.exa.data.adt.operation.Operation;
import magma.exa.value.Lazy;
import magma.exa.value.scalar.F32;
import magma.exa.value.scalar.F64;
import magma.exa.value.scalar.I32;
import magma.exa.value.scalar.I64;

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
 *
 * @param <A> type of value.
 */
public interface Option<A> extends IOption<A>,

        Operation.Access.Value.Of<A, IOption<?>>,

        Operation.Transmute.PolyMap.Of<A, IOption<?>>,

        Operation.Transmute.PolyFlatMap.Of<A, IOption<?>>,

        Flow.Producer<A, IOption<?>>
{
    /**
     * Returns the empty option coerced to target type.
     *
     * @param <V> target type.
     * @return empty option.
     */
    static <V> Option<V> none() {
        return Force.cast(None.option);
    }

    // ----------------------------------------------------------

    /**
     * Constructs an option with a given non-null value,
     * otherwise the empty option is returned.
     *
     * @param val value to be wrapped in option.
     * @param <V> type of value.
     * @return optional value.
     */
    static <V> Option<V> some(final V val) {
        return val != null ? new Some<>(val) : none();
    }

    /**
     * @see #some(Object)
     */
    static <V> Option<V> of(final V val) {
        return some(val);
    }

    /**
     * Constructs an option adapted from the given {@link java.util.Optional}.
     *
     * @param opt from JDK to be adapted.
     * @param <V> type of the wrapped value.
     * @return optional value.
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    static <V> Option<V> some(final java.util.Optional<? extends V> opt) {
        return narrow(opt.map(Option::some).orElseGet(Option::none));
    }

    // ----------------------------------------------------------

    /**
     * Constructs an option depending on the given condition. If the
     * condition is true, an option with the given value is returned,
     * otherwise the empty option is returned.
     *
     * @param condition to be true to return a present option.
     * @param val       to be wrapped in option.
     * @param <A>       type of the wrapped value.
     * @return optional value.
     */
    static <A> Option<A> when(final boolean condition, final A val) {
        return condition ? some(val) : none();
    }

    /**
     * Constructs an option depending on the given conditional value.
     * If the condition is true, an option with the lazy supplied value
     * is returned, otherwise the empty option is returned.
     *
     * @param condition to be true to return a present option.
     * @param lazy      supplier of some value to be wrapped.
     * @param <A>       type of the wrapped value.
     * @return optional value.
     */
    static <A> Option<A> when(final boolean condition, final Fn0<? extends A> lazy) {
        java.util.Objects.requireNonNull(lazy);
        return condition ? some(lazy.apply()) : none();
    }

    // ----------------------------------------------------------

    /**
     * Narrow conversion of (invariant) type parameter {@code V}. Type-safe!
     */
    static <V> Option<V> narrow(final Option<? extends V> opt) {
        return Force.cast(opt);
    }

    // ----------------------------------------------------------

    /**
     * Determines whether some value is present.
     */
    @Override
    boolean isPresent();

    /**
     * Determines whether this option is empty.
     */
    @Override
    boolean isEmpty();

    /**
     * Returns 1 for Some, and 0 for None.
     */
    @Override
    long count();

    // ----------------------------------------------------------

    /// CONTAINMENT.

    /**
     * Determines whether some present value is equal to the given value.
     *
     * @param val value whose presence is to be determined.
     * @return true iff the value is contained.
     */
    @Override
    boolean contains(Object val);

    // ----------------------------------------------------------

    /// QUANTIFICATION.

    /**
     * Determines whether some present value satisfies the given predicate.
     * <p>
     * Evaluates the existential quantification of the given predicate
     * over some present value, ∃(x) ∈ Option P(x). On the empty option
     * false is returned without evaluating the predicate.
     *
     * @param predicate to be evaluated for some present value.
     * @return true for a matching value.
     */
    @Override
    boolean anyMatch(Fn1.Predicate<? super A> predicate);

    /**
     * Determines whether some present value satisfies the given predicate.
     * <p>
     * Evaluates the universal quantification of the given predicate
     * over some present value, ∀(x) ∈ Option P(x). On the empty option
     * true is returned without evaluation. The quantification is said
     * to be 'vacuously satisfied' and is always true, independent of P(x).
     *
     * @param predicate to be evaluated for some present value.
     * @return true for a matching value or empty option.
     */
    @Override
    boolean allMatch(Fn1.Predicate<? super A> predicate);

    /**
     * Determines whether some present value does not satisfy the given predicate.
     * <p>
     * Evaluates universal quantification of the given negated predicate
     * over some present value, ∀(x) ∈ Option ¬P(x). On the empty option
     * true is returned without evaluation. The quantification is said
     * to be 'vacuously satisfied' and is always true, independent of P(x).
     *
     * @param predicate to be evaluated for some present value.
     * @return true for a matching value or empty option.
     */
    @Override
    boolean noneMatch(Fn1.Predicate<? super A> predicate);

    // ----------------------------------------------------------

    /**
     * Match on some present value and the empty option. Type-safe.
     *
     * @param some function to be applied on a present value.
     * @param none function to be applied on empty option.
     * @param <R>  type of match result.
     * @return result of matching function.
     */
    @Override
    <R> R match(Fn1<? super A, ? extends R> some, Fn0<? extends R> none);

    // ----------------------------------------------------------

    /**
     * Returns a value iff present or throws an exception.
     */
    @Override
    A value();

    /**
     * @see #value()
     */
    default A get() {
        return value();
    }

    /**
     * Returns a value iff present or null is returned.
     */
    A orNull();

    // ----------------------------------------------------------

    /**
     * Returns a value iff present or the given other value.
     *
     * @param other to be returned iff no value is present.
     * @return present- or another value.
     */
    A orElse(A other);

    /**
     * Returns a value iff present or another lazy computed value.
     *
     * @param other value to be computed iff required.
     * @return present- or another computed value.
     */
    A orElse(Lazy<? extends A> other);

    // ----------------------------------------------------------

    /**
     * Returns either some present value or throws an exception.
     */
    A orElseThrow();

    /**
     * Returns either some present value or throws a lazy supplied exception.
     *
     * @param <X>      type of the lazy supplied exception.
     * @param exception that is lazy provided iff required.
     * @return present value.
     * @throws X exception that is lazily passed and thrown iff empty.
     */
    <X extends Throwable> A orElseThrow(Lazy<? extends X> exception) throws X;

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
    Option<A> or(Option<? extends A> other);

    /**
     * Returns either this option if some value is present, otherwise
     * the lazy evaluated option obtained by the lazy evaluated supplier
     * is returned.
     *
     * @param other option to be supplied iff required.
     * @return this or lazy passed option.
     */
    Option<A> or(Lazy<? extends Option<? extends A>> other);

    // ----------------------------------------------------------

    /**
     * Performs the given (side effect) action on some present value
     * and returns this options, otherwise the empty option is returned,
     * without performing the given action.
     *
     * @param action to be preformed on some present value.
     * @return this option.
     */
    @Override
    Option<A> peek(Fn1.Consumer<? super A> action);

    /**
     * @see #peek(Fn1.Consumer)
     */
    default Option<A> ifPresent(final Fn1.Consumer<? super A> action) {
        if (isPresent()) action.accept(value());
        return this;
    }

    // ----------------------------------------------------------

    /// FILTER TRANSFORMATION.

    /**
     * Returns an optional value of type {@code A} consisting of the
     * value of this optional that do satisfy the given predicate.
     *
     * @param predicate to be applied to each value to determine inclusion.
     * @return filtered optional value of type {@code A}.
     */
    @Override
    Option<A> filter(Fn1.Predicate<? super A> predicate);

    /**
     * Returns an optional value of type {@code A} consisting of
     * the value of this optional that do not satisfy the specified
     * predicate.
     *
     * @param predicate to be applied to each value to determine exclusion.
     * @return filtered optional value of type {@code A}.
     */
    @Override
    Option<A> filterNot(Fn1.Predicate<? super A> predicate);

    /**
     * Returns an optional value of type {@code B} consisting of an
     * assignment compatible values of this option.
     * Assignment compatibility is determined via the given class object.
     *
     * @param type to which a value must be assignable in order to be present.
     * @return filter-mapped optional value of type {@code B}.
     */
    @Override
    <B> Option<B> filter(Class<? extends B> type);

    // ----------------------------------------------------------

    /**
     * Returns an optional value of type {@code A} obtained by
     * applying the given mapper function to some contained value.
     *
     * @param mapper function to be applied on each value.
     * @return optional value of type {@code A}.
     */
    @Override
    <B> Option<B> map(Fn1<? super A, ? extends B> mapper);

    /**
     * Returns an optional {@code int} obtained by applying the
     * given mapper function to some contained value.
     *
     * @param mapper function to be applied on each value.
     * @return optional {@code int}.
     */
    @Override
    I32.Option mapI32(Fn1.ToI32<? super A> mapper);

    /**
     * Returns an optional {@code long} obtained by applying
     * the given mapper function to some contained value.
     *
     * @param mapper function to be applied on each value.
     * @return optional {@code long}.
     */
    @Override
    I64.Option mapI64(Fn1.ToI64<? super A> mapper);

    /**
     * Returns an optional {@code float} obtained by applying
     * the given mapper function to some contained value.
     *
     * @param mapper function to be applied on each value.
     * @return optional {@code float}.
     */
    @Override
    F32.Option mapF32(Fn1.ToF32<? super A> mapper);

    /**
     * Returns an optional {@code double} obtained by applying
     * the given mapper function to some contained value.
     *
     * @param mapper function to be applied on Some value.
     * @return optional {@code double}.
     */
    @Override
    F64.Option mapF64(Fn1.ToF64<? super A> mapper);

    // ----------------------------------------------------------

    /**
     * Returns an optional value of type {@code A} containing the
     * result obtained by applying the given mapper function to some
     * contained value and wrapping the first (optional) value
     * returned by the given iterable.
     *
     * @param mapper function to be applied on some contained value.
     * @return optional value of type {@code A}.
     */
    @Override
    <B> Option<B> flatMap(Fn1<? super A, ? extends Iterable<? extends B>> mapper);

    /**
     * Returns an optional {@code int} containing the result obtained
     * by applying the given mapper function to some contained value and
     * wrapping the first (optional) {@code int} value produced by the
     * given iterable.
     *
     * @param mapper function to be applied on some contained value.
     * @return optional {@code int}.
     */
    @Override
    I32.Option flatMapI32(Fn1<? super A, ? extends Iterable<Integer>> mapper);

    /**
     * Returns an optional {@code long} containing the result obtained
     * by applying the given mapper function to some contained value and
     * wrapping the first (optional) {@code long} value produced by the
     * given iterable.
     *
     * @param mapper function to be applied on some contained value.
     * @return optional {@code long}.
     */
    @Override
    I64.Option flatMapI64(Fn1<? super A, ? extends Iterable<Long>> mapper);

    /**
     * Returns an optional {@code float} containing the result obtained
     * by applying the given mapper function to some contained value and
     * wrapping the first (optional) {@code float} value produced by the
     * given iterable.
     *
     * @param mapper function to be applied on some contained value.
     * @return optional {@code float}.
     */
    @Override
    F32.Option flatMapF32(Fn1<? super A, ? extends Iterable<Float>> mapper);

    /**
     * Returns an optional {@code double} containing the result obtained
     * by applying the given mapper function to some contained value and
     * wrapping the first (optional) {@code double} value produced by the
     * given iterable.
     *
     * @param mapper function to be applied on some contained value.
     * @return optional {@code double}.
     */
    @Override
    F64.Option flatMapF64(Fn1<? super A, ? extends Iterable<Double>> mapper);

    // ----------------------------------------------------------

    /**
     * Folds all values contained in this optional value by using the
     * given operator. If this option is empty, the initial value is
     * returned.
     * <p>
     * The fold operation starts with the given initial accumulator
     * value and applies the given combiner to the current accumulator
     * value and each next value in this optional value, from left to
     * right, to compute the final accumulator value.
     *
     * @param initial  accumulator value to start with.
     * @param combiner function to integrate a value into the accumulator.
     * @return accumulated value.
     */
    @Override
    <Z> Z foldLeft(Z initial, Fn2<Z, ? super A, Z> combiner);

    /**
     * Performs a mutable accumulation on the values in this optional
     * value. Such an accumulation returns a mutable result container,
     * into which values are included by updating the state of the
     * container instead of replacing the result.
     *
     * @param factory     supplier that creates the mutable container.
     * @param accumulator function to fold a value into the container.
     * @param <C>         type of the result container.
     * @return mutable container.
     */
    @Override
    <C> C collect(I32.To<? extends C> factory, Fn2.Consumer<C, ? super A> accumulator);

    // ----------------------------------------------------------
    //  OPTION.SOME
    // ----------------------------------------------------------

    /**
     * The populated option that contains a value.
     */
    final class Some<A> implements Option<A> {
        private final A value;

        /**
         * Constructs some optional with the given value.
         */
        Some(final A value) {
            this.value = value;
        }

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

        /**
         * {@inheritDoc}
         */
        @Override
        public long count() {
            return 1L;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(final Object val) {
            return this.value == val;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean anyMatch(final Fn1.Predicate<? super A> predicate) {
            return predicate.eval(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean allMatch(final Fn1.Predicate<? super A> predicate) {
            return predicate.eval(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean noneMatch(final Fn1.Predicate<? super A> predicate) {
            return !predicate.eval(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <R> R match(final Fn1<? super A, ? extends R> some,
                           final Fn0<? extends R> none) {
            java.util.Objects.requireNonNull(none);
            return some.apply(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public A value() {
            return value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public A orNull() {
            return value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public A orElse(final A other) {
            return value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public A orElse(final Lazy<? extends A> other) {
            java.util.Objects.requireNonNull(other);
            return value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public A orElseThrow() {
            return value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <X extends Throwable> A orElseThrow(final Lazy<? extends X> exception) {
            java.util.Objects.requireNonNull(exception);
            return value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Option<A> or(final Option<? extends A> other) {
            java.util.Objects.requireNonNull(other);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Option<A> or(final Lazy<? extends Option<? extends A>> other) {
            java.util.Objects.requireNonNull(other);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Option<A> peek(final Fn1.Consumer<? super A> action) {
            action.accept(value);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Option<A> filter(final Fn1.Predicate<? super A> predicate) {
            return predicate.eval(value) ? this : Option.none();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Option<A> filterNot(final Fn1.Predicate<? super A> predicate) {
            return !predicate.eval(value) ? this : Option.none();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public IOption<?> filterNull() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <B> Option<B> filter(final Class<? extends B> type) {
            return type.isInstance(value) ? Force.cast(this) : Option.none();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <B> Option<B> map(final Fn1<? super A, ? extends B> mapper) {
            return some(mapper.apply(value));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public I32.Option mapI32(final Fn1.ToI32<? super A> mapper) {
            return I32.some(mapper.apply(value));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public I64.Option mapI64(final Fn1.ToI64<? super A> mapper) {
            return I64.some(mapper.apply(value));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public F32.Option mapF32(final Fn1.ToF32<? super A> mapper) {
            return F32.some(mapper.apply(value));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public F64.Option mapF64(final Fn1.ToF64<? super A> mapper) {
            return F64.some(mapper.apply(value));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("ConstantConditions")
        public <B> Option<B> flatMap(final Fn1<? super A, ? extends Iterable<? extends B>> mapper) {
            final var itr = mapper.apply(value);
            // Strict defensive on data being passed.
            if (null != itr) {
                final var it = itr.iterator();
                return when(null != it && it.hasNext(), it::next);
            }
            return Option.none();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("ConstantConditions")
        public I32.Option flatMapI32(final Fn1<? super A, ? extends Iterable<Integer>> mapper) {
            final var itr = mapper.apply(value);
            // Strict defensive on data being passed.
            if (null != itr) {
                final var it = itr.iterator();
                return I32.when(null != it && it.hasNext(), it::next);
            }
            return I32.none;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("ConstantConditions")
        public I64.Option flatMapI64(final Fn1<? super A, ? extends Iterable<Long>> mapper) {
            final var itr = mapper.apply(value);
            // Strict defensive on data being passed.
            if (null != itr) {
                final var it = itr.iterator();
                return I64.when(null != it && it.hasNext(), it::next);
            }
            return I64.none;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("ConstantConditions")
        public F32.Option flatMapF32(final Fn1<? super A, ? extends Iterable<Float>> mapper) {
            final var itr = mapper.apply(value);
            // Strict defensive on data being passed.
            if (null != itr) {
                final var it = itr.iterator();
                return F32.when(null != it && it.hasNext(), it::next);
            }
            return F32.none;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("ConstantConditions")
        public F64.Option flatMapF64(final Fn1<? super A, ? extends Iterable<Double>> mapper) {
            final var itr = mapper.apply(value);
            // Strict defensive on data being passed.
            if (null != itr) {
                final var it = itr.iterator();
                return F64.when(null != it && it.hasNext(), it::next);
            }
            return F64.none;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <U> U foldLeft(final U initial, final Fn2<U, ? super A, U> combiner) {
            return combiner.apply(initial, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <C> C collect(final I32.To<? extends C> factory,
                             final Fn2.Consumer<C, ? super A> accumulator) {
            final C container = factory.apply(1);
            accumulator.apply(container).accept(value);
            return container;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Traverser<A> traverser() {
            return Traversal.single(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof Option<?> some)) {
                return false;
            }
            return java.util.Objects.equals(value, some.value());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return Hash.code(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return '[' + String.valueOf(value) + ']';
        }
    }

    // ----------------------------------------------------------
    //  OPTION.NONE
    // ----------------------------------------------------------

    /**
     * The empty option that has no value.
     */
    enum None implements Option<Object> {
        option;

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

        /**
         * {@inheritDoc}
         */
        @Override
        public long count() {
            return 0L;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(final Object val) {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean anyMatch(final Fn1.Predicate<? super Object> predicate) {
            java.util.Objects.requireNonNull(predicate);
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean allMatch(final Fn1.Predicate<? super Object> predicate) {
            java.util.Objects.requireNonNull(predicate);
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean noneMatch(final Fn1.Predicate<? super Object> predicate) {
            java.util.Objects.requireNonNull(predicate);
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <R> R match(final Fn1<? super Object, ? extends R> some,
                           final Fn0<? extends R> none) {
            java.util.Objects.requireNonNull(some);
            return none.apply();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object value() {
            throw new java.util.NoSuchElementException();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object orNull() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object orElse(final Object other) {
            return other;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object orElse(final Lazy<?> other) {
            return other.apply();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object orElseThrow() {
            throw new java.util.NoSuchElementException();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <X extends Throwable> Object orElseThrow(final Lazy<? extends X> exception) throws X {
            throw exception.apply();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Option<Object> or(final Option<?> other) {
            return Option.narrow(other);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Option<Object> or(final Lazy<? extends Option<?>> other) {
            return Option.narrow(other.apply());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Option<Object> peek(final Fn1.Consumer<? super Object> action) {
            java.util.Objects.requireNonNull(action);
            return None.option;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Option<Object> filter(final Fn1.Predicate<? super Object> predicate) {
            java.util.Objects.requireNonNull(predicate);
            return None.option;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Option<Object> filterNot(final Fn1.Predicate<? super Object> predicate) {
            java.util.Objects.requireNonNull(predicate);
            return None.option;
        }

        @Override
        public Option<Object> filterNull() {
            return None.option;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <B> Option<B> filter(final Class<? extends B> type) {
            java.util.Objects.requireNonNull(type);
            return Option.none();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <B> Option<B> map(final Fn1<? super Object, ? extends B> mapper) {
            java.util.Objects.requireNonNull(mapper);
            return Option.none();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public I32.Option mapI32(final Fn1.ToI32<? super Object> mapper) {
            java.util.Objects.requireNonNull(mapper);
            return I32.none;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public I64.Option mapI64(final Fn1.ToI64<? super Object> mapper) {
            java.util.Objects.requireNonNull(mapper);
            return I64.none;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public F32.Option mapF32(final Fn1.ToF32<? super Object> mapper) {
            java.util.Objects.requireNonNull(mapper);
            return F32.none;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public F64.Option mapF64(final Fn1.ToF64<? super Object> mapper) {
            java.util.Objects.requireNonNull(mapper);
            return F64.none;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <B> Option<B> flatMap(final Fn1<? super Object, ? extends Iterable<? extends B>> mapper) {
            java.util.Objects.requireNonNull(mapper);
            return Option.none();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public I32.Option flatMapI32(final Fn1<? super Object, ? extends Iterable<Integer>> mapper) {
            java.util.Objects.requireNonNull(mapper);
            return I32.none;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public I64.Option flatMapI64(final Fn1<? super Object, ? extends Iterable<Long>> mapper) {
            java.util.Objects.requireNonNull(mapper);
            return I64.none;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public F32.Option flatMapF32(final Fn1<? super Object, ? extends Iterable<Float>> mapper) {
            java.util.Objects.requireNonNull(mapper);
            return F32.none;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public F64.Option flatMapF64(final Fn1<? super Object, ? extends Iterable<Double>> mapper) {
            java.util.Objects.requireNonNull(mapper);
            return F64.none;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <U> U foldLeft(final U initial, final Fn2<U, ? super Object, U> combiner) {
            java.util.Objects.requireNonNull(combiner);
            return initial;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <C> C collect(final I32.To<? extends C> factory, final Fn2.Consumer<C, ? super Object> accumulator) {
            java.util.Objects.requireNonNull(accumulator);
            return factory.apply(0);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Traverser<Object> traverser() {
            return Traverser.empty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "[]";
        }
    }
}