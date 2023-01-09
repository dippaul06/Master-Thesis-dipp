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
 *
 * @param <A> type of value.
 */
public interface ISeq<A> extends IFlow.IProducer<A, ISeq<?>>,

        /// OPERATION.QUERY

        Operation.Query.Bulk.Membership<A, ISeq<?>>,

        Operation.Query.Quantify<A, ISeq<?>>,

        Operation.Query.FindValue<A, ISeq<?>>,

        Operation.Query.FindIndex<A, ISeq<?>>,

        /// OPERATION.ACCESS

        Operation.Access.Single<A, ISeq<?>>,

        Operation.Access.First<A, ISeq<?>>,

        Operation.Access.Last<A, ISeq<?>>,

        Operation.Access.At<A, ISeq<?>>,

        /// OPERATION.FOLD

        Operation.Fold.FoldLeft<A, ISeq<?>>,

        Operation.Fold.FoldRight<A, ISeq<?>>,

        Operation.Fold.Reduce<A, ISeq<?>>,

        Operation.Fold.Collect<A, ISeq<?>>,

        /// OPERATION.AGGREGATE

        Operation.Aggregate.Count<A, ISeq<?>>,

        Operation.Aggregate.Min<A, ISeq<?>>,

        Operation.Aggregate.Max<A, ISeq<?>>,

        /// OPERATION.CONVERT

        Operation.Convert.ToArray<A, ISeq<?>>,

        Operation.Convert.ToCollection<A, ISeq<?>>,

        /// ----------------------------------------------------------

        /// OPERATION.TRANSFORM

        Operation.Transform.Peek<A, ISeq<?>>,

        Operation.Transform.Filter<A, ISeq<?>>,

        Operation.Transform.Distinct<A, ISeq<?>>,

        Operation.Transform.Sort<A, ISeq<?>>,

        Operation.Transform.Reverse<A, ISeq<?>>,

        /// OPERATION.TRANSMUTE

        Operation.Transmute.Map<A, ISeq<?>>,

        Operation.Transmute.FilterMap<A, ISeq<?>>,

        Operation.Transmute.FlatMap<A, ISeq<?>>,

        /// OPERATION.PARTITION

        Operation.Partition.Take<A, ISeq<?>>,

        Operation.Partition.Drop<A, ISeq<?>>
{





}
