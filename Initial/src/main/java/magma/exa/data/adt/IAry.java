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
import magma.exa.data.index.Range;

/**
 *
 */
public interface IAry<A> extends Mixin.Count, Range,

        Operation.Query.Membership<A, IAry<?>>,

        Operation.Query.Quantify<A, IAry<?>>,

        Operation.Query.FindValue<A, IAry<?>>,

        Operation.Query.FindIndex<A, IAry<?>>,

        // ----------------------------------------------------------

        Operation.Access.Single<A, IAry<?>>,

        Operation.Access.First<A, IAry<?>>,

        Operation.Access.Last<A, IAry<?>>,

        Operation.Access.At<A, IAry<?>>,

        // ----------------------------------------------------------

        Operation.Plus.Bulk.Prepend<A, IAry<?>>,

        Operation.Plus.Bulk.Append<A, IAry<?>>,

        Operation.Plus.Bulk.InsertAt<A, IAry<?>>,

        // ----------------------------------------------------------

        Operation.Minus.Remove<A, IAry<?>>,

        Operation.Minus.RemoveAll<A, IAry<?>>,

        Operation.Minus.RemoveFirst<A, IAry<?>>,

        Operation.Minus.RemoveLast<A, IAry<?>>,

        Operation.Minus.RemoveAt<A, IAry<?>>,

        Operation.Minus.RemoveRange<A, IAry<?>>,

        // ----------------------------------------------------------

        Operation.Transform.Filter<A, IAry<?>>,

        Operation.Transform.Distinct<A, IAry<?>>,

        Operation.Transform.Sort<A, IAry<?>>,

        Operation.Transform.Reverse<A, IAry<?>>,

        // ----------------------------------------------------------

        Operation.Transmute.FilterMap<A, IAry<?>>,

        Operation.Transmute.PolyMap<A, IAry<?>>,

        Operation.Transmute.PolyFlatMap<A, IAry<?>>,

        // ----------------------------------------------------------

        Operation.Fold.FoldLeft<A, IAry<?>>,

        Operation.Fold.FoldRight<A, IAry<?>>,

        Operation.Fold.Reduce<A, IAry<?>>,

        Operation.Fold.Collect<A, IAry<?>>,

        // ----------------------------------------------------------

        Operation.Aggregate.Count<A, IAry<?>>,

        Operation.Aggregate.Min<A, IAry<?>>,

        Operation.Aggregate.Max<A, IAry<?>>,

        // ----------------------------------------------------------

        Operation.Convert.ToArray<A, IAry<?>>,

        Operation.Convert.ToCollection<A, IAry<?>> {

}
