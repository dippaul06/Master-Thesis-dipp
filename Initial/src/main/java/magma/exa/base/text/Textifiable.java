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

package magma.exa.base.text;

import magma.exa.adt.mixin.Mixin;
import magma.exa.control.adt.function.IFn1;
import magma.exa.control.function.Fn2;

/**
 * Mixin type that declares operation
 */
@Mixin interface Textifiable<T extends Textifiable<T>> {

   default Fn2.Consumer<Text.Formatter<T>, T> textify() {
      return IFn1::apply;
   }
}
