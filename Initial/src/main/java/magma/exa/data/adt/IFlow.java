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

import magma.exa.adt.mixin.Mixin;
import magma.exa.data.adt.operation.Operation;

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
public interface IFlow<A, U extends IFlow<?, U>>

        extends Mixin.IsEmpty, Mixin.Count,

        Operation.Query.Membership<A, U>,

        Operation.Query.Quantify<A, U>,

        // ----------------------------------------------------------

        Operation.Fold.FoldLeft<A, U>,

        Operation.Fold.Collect<A, U>,

        // ----------------------------------------------------------

        Operation.Convert.ToArray<A, U>,

        Operation.Convert.ToCollection<A, U>
{
    // ----------------------------------------------------------
    //  FLOW.PRODUCER
    // ----------------------------------------------------------

    /**
     * A producer flow refines {@link IFlow} by adding transformation
     * and transmutation operations.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface IProducer<A, U extends IProducer<?, U>> extends IFlow<A, U>,

            Operation.Transform.Peek<A, U>,

            Operation.Transform.Filter<A, U>,

            // ----------------------------------------------------------

            Operation.Transmute.Map<A, U>,

            Operation.Transmute.FilterMap<A, U>,

            Operation.Transmute.FlatMap<A, U>
    {
    }
}
