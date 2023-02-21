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

package magma.exa.adt.typelevel.nat;

/**
 * Type-level representation of the natural number 8.
 */
public class $8 extends $7 {
    /* package private */ $8() { }

    /** Instance of natural number 8. */
    public static final $8 nat = new $8();

    /** Returns {@code $7} as preceding natural. */
    @Override public $7 pred() { return $7.nat; }

    /** Returns the runtime value encoding as {@code int}. */
    @Override public int ordinal() { return 8; }

    /** Returns the string representation of this natural number.*/
    @Override public String toString()  { return "$8"; }
}
