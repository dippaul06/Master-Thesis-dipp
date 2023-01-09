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
 * Type-level representation of the natural number 6.
 */
public class $6 extends $5 {
    /* package private */ $6() { }

    /** Instance of natural number 6. */
    public static final $6 nat = new $6();

    /** Returns {@code $5} as preceding natural. */
    @Override public $5 pred() { return $5.nat; }

    /** Returns {@code $7} as succeeding natural. */
    @Override public $7 succ() { return $7.nat; }

    /** Returns the runtime value encoding as {@code int}. */
    @Override public int ordinal() { return 6; }

    /** Returns the string representation of this natural number.*/
    @Override public String toString()  { return "$6"; }
}
