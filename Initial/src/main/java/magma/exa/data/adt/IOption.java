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
import magma.exa.value.adt.coproduct.Coproduct1;

/**
 * _______        __________
 * __  __ \_________  /___(_)____________
 * _  / / /__  __ \  __/_  /_  __ \_  __ \
 * / /_/ /__  /_/ / /_ _  / / /_/ /  / / /
 * \____/ _  .___/\__/ /_/  \____//_/ /_/ADT
 *        /_/
 *
 * An optional value: every Option is either Some
 * and contains a value, or None, and does not.
 *
 * @param <A> type of value.
 */
public interface IOption<A> extends IFlow.IProducer<A, IOption<?>>,

        Operation.Access.Value<A, IOption<?>>,

        Operation.Transmute.PolyMap<A, IOption<?>>,

        Operation.Transmute.PolyFlatMap<A, IOption<?>>,

        Coproduct1<A> {
}