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

import magma.exa.base.Narrow;
import magma.exa.base.contract.Assert;
import magma.exa.control.adt.traversal.ICursor;
import magma.exa.control.function.Fn1;
import magma.exa.control.function.Fn2;
import magma.exa.control.traversal.Traversable;
import magma.exa.data.adt.IFlow;
import magma.exa.data.adt.operation.Operation;
import magma.exa.value.scalar.I32;

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
 * @param <A> type of value.
 * @param <U> unification parameter.
 */
public interface Flow<A, U extends IFlow<?, U>> extends IFlow<A, U>,

        Traversable<A>,

        // ----------------------------------------------------------

        Operation.Query.Membership.Of<A, U>,

        Operation.Query.Quantify.Of<A, U>,

        // ----------------------------------------------------------

        Operation.Fold.FoldLeft.Of<A, U>,

        Operation.Fold.Collect.Of<A, U>,

        // ----------------------------------------------------------

        Operation.Convert.ToArray.Of<A, U>,

        Operation.Convert.ToCollection.Of<A, U>
{
    /**
     * Determines whether the given value is contained.
     *
     * @param val value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    @Override
    default boolean contains(final Object val) {
        return ICursor.exited(traverser().whileNext(control -> x -> {
            if (java.util.Objects.equals(x, val)) {
                control.exit();
            }
        }));
    }

    /**
     * Determines whether any value of type {@code A} satisfies the given predicate.
     * <p>
     * This corresponds to evaluating the existential quantification of
     * the given predicate over the values {@code '∃(x) ∈ Flow<A> P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If no values are present, false is returned
     * and the predicate is not evaluated.
     *
     * @param predicate to be evaluated on contained values.
     * @return true iff any value matches.
     */
    @Override
    default boolean anyMatch(final Fn1.Predicate<? super A> predicate) {
        return ICursor.exited(traverser().whileNext(control -> x -> {
            if (predicate.onEval(x)) {
                control.exit();
            }
        }));
    }

    /**
     * Determines whether all values of type {@code A} satisfy the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification
     * of the predicate over the values {@code '∀(x) ∈ Flow<A> P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If no values are present, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param predicate to be evaluated on contained values.
     * @return true iff all values match or no values are present.
     */
    @Override
    default boolean allMatch(final Fn1.Predicate<? super A> predicate) {
        return ICursor.completed(traverser().whileNext(control -> x -> {
            if (!predicate.onEval(x)) {
                control.exit();
            }
        }));
    }

    /**
     * Determines whether no values of type {@code A} satisfy the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ Flow<A> ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If no values are present, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param predicate to be evaluated on contained values.
     * @return true iff no value matches or no values are present.
     */
    @Override
    default boolean noneMatch(final Fn1.Predicate<? super A> predicate) {
        return ICursor.completed(traverser().whileNext(control -> x -> {
            if (predicate.onEval(x)) {
                control.exit();
            }
        }));
    }

    /**
     * Performs a left fold on the contained values of type {@code A} using
     * the given combining function. If no values are present, the initial
     * value accumulator is returned.
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
    default <Z> Z foldLeft(final Z initial, final Fn2<Z, ? super A, Z> combiner) {
        Assert.notNull(initial, combiner);
        final class Fold implements Fn1.Consumer<A> {
            private Z state = initial;
            {
                traverser().forNext(this);
            }
            @Override
            public void onAccept(final A value) throws Throwable {
                this.state = combiner.onApply(state, value);
            }
        }
        return new Fold().state;
    }

    /**
     * Returns a result container of type {@code C} populated with the
     * contained values of type {@code A}. The container is created using
     * the given factory. The contained values are integrated using the
     * given accumulator function by updating the state of the container
     * (instead of replacing the result).
     *
     * @param factory     used to instantiate the result container.
     * @param accumulator function to fold a value into the container.
     * @param <C>         type of container.
     * @return container of type {@code C}.
     */
    @Override
    default <C> C collect(final I32.To<? extends C> factory,
                          final Fn2.Consumer<C, ? super A> accumulator)
    {
        Assert.notNull(factory, accumulator);
        final class Collect implements Fn1.Consumer<A> {
            private final C state;
            {
                state = factory.apply(Narrow.I32(count()));
                traverser().forNext(this);
            }
            @Override
            public void onAccept(final A value) throws Throwable {
                accumulator.onApply(state, value);
            }
        }
        return new Collect().state;
    }

    // ----------------------------------------------------------
    //  FLOW.PRODUCER
    // ----------------------------------------------------------

    /**
     * A producer flow refines {@link Flow} by adding transformation
     * and transmutation operations.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface Producer<A, U extends IProducer<?, U>>

            extends IFlow.IProducer<A, U>, Flow<A, U>,

            Operation.Transform.Peek.Of<A, U>,

            Operation.Transform.Filter.Of<A, U>,

            // ----------------------------------------------------------

            Operation.Transmute.Map.Of<A, U>,

            Operation.Transmute.FilterMap.Of<A, U>,

            Operation.Transmute.FlatMap.Of<A, U> {
    }
}
