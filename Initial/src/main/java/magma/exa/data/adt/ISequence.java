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

package magma.exa.data.adt;

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
 * Refines {@link IFlow} by adding value-search-, index-retrieval-,
 * access-, aggregation- and partition operations.
 *
 * @param <A> type of values.
 * @param <U> unification parameter.
 */
public interface ISequence<A, U extends ISequence<?, U>>

        extends IFlow<A, U>,

        Operation.Query.Bulk.Membership<A, U>,

        Operation.Query.FindValue<A, U>,

        Operation.Query.FindIndex<A, U>,

        // ----------------------------------------------------------

        Operation.Access.Single<A, U>,

        Operation.Access.First<A, U>,

        Operation.Access.Last<A, U>,

        Operation.Access.At<A, U>,

        // ----------------------------------------------------------

        Operation.Aggregate.Count<A, U>,

        Operation.Aggregate.Min<A, U>,

        Operation.Aggregate.Max<A, U>,

        // ----------------------------------------------------------

        Operation.Fold.FoldRight<A, U>,

        Operation.Fold.Reduce<A, U>
{
    // ----------------------------------------------------------
    //  ISEQUENCE.IPRODUCER
    // ----------------------------------------------------------

    /**
     * A producer flow refines {@link ISequence} by adding reordering
     * transformations and partition operations.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface IProducer<A, U extends IProducer<?, U>>

            extends IFlow.IProducer<A, U>, ISequence<A, U>,

            Operation.Transform.Sort<A, U>,

            Operation.Transform.Reverse<A, U>,

            // ----------------------------------------------------------

            Operation.Partition.Take<A, U>,

            Operation.Partition.Drop<A, U>
    {
    }
}
