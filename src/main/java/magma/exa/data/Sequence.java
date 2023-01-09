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

import magma.exa.data.adt.ISequence;
import magma.exa.data.adt.operation.Operation;

/**
 * ________
 * __  ___/__________ ____  ________________________
 * _____ \_  _ \  __ `/  / / /  _ \_  __ \  ___/  _ \
 * ____/ //  __/ /_/ // /_/ //  __/  / / / /__ /  __/
 * /____/ \___/\__, / \__,_/ \___//_/ /_/\___/ \___/ADT
 *               /_/
 *
 * Foundational abstract data type to operate value groupings.
 * <p>
 * Refines {@link Flow} by adding value-search-, index-retrieval-,
 * access-, aggregation- and partition operations.
 *
 * @param <A> type of values.
 * @param <U> unification parameter.
 */
public interface Sequence<A, U extends ISequence<?, U>>

        extends ISequence<A, U>, Flow<A, U>,

        // ----------------------------------------------------------

        Operation.Query.Bulk.Membership.Of<A, U>,

        Operation.Query.FindValue.Of<A, U>,

        // ----------------------------------------------------------

        Operation.Access.Single.Of<A, U>,

        Operation.Access.First.Of<A, U>,

        Operation.Access.Last.Of<A, U>,

        Operation.Access.At.Of<A, U>,

        // ----------------------------------------------------------

        Operation.Aggregate.Count.Of<A, U>,

        Operation.Aggregate.Min.Of<A, U>,

        Operation.Aggregate.Max.Of<A, U>,

        // ----------------------------------------------------------

        Operation.Fold.FoldRight.Of<A, U>,

        Operation.Fold.Reduce.Of<A, U>
{
    // ----------------------------------------------------------
    //  SEQUENCE.PRODUCER
    // ----------------------------------------------------------

    /**
     * A producer flow refines {@link Sequence} by adding reordering
     * transformations and partition operations.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface Producer<A, U extends ISequence.IProducer<?, U>>

            extends ISequence.IProducer<A, U>, Flow.Producer<A, U>,

            Sequence<A, U>,

            // ----------------------------------------------------------

            Operation.Transform.Sort.Of<A, U>,

            Operation.Transform.Reverse.Of<A, U>,

            // ----------------------------------------------------------

            Operation.Partition.Take.Of<A, U>,

            Operation.Partition.Drop.Of<A, U>
    {
    }
}
