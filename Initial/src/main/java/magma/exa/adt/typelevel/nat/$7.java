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
 * Type-level representation of the natural number 7.
 */
public class $7 extends $6 {
    /* package private */ $7() { }

    /** Instance of natural number 7. */
    public static final $7 nat = new $7();

    /** Returns {@code $6} as preceding natural. */
    @Override public $6 pred() { return $6.nat; }

    /** Returns {@code $8} as succeeding natural. */
    @Override public $8 succ() { return $8.nat; }

    /** Returns the runtime value encoding as {@code int}. */
    @Override public int ordinal() { return 7; }

    /** Returns the string representation of this natural number.*/
    @Override public String toString()  { return "$7"; }
}
