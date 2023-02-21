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
 * Type-level representation of the natural number 3.
 */
public class $3 extends $2 {
    /* package private */ $3() { }

    /** Instance of natural number 3. */
    public static final $3 nat = new $3();

    /** Returns {@code $2} as preceding natural. */
    @Override public $2 pred() { return $2.nat; }

    /** Returns {@code $4} as succeeding natural. */
    @Override public $4 succ() { return $4.nat; }

    /** Returns the runtime value encoding as {@code int}. */
    @Override public int ordinal() { return 3; }

    /** Returns the string representation of this natural number.*/
    @Override public String toString()  { return "$3"; }
}
