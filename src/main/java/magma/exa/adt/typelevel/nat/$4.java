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
 * Type-level representation of the natural number 4.
 */
public class $4 extends $3 {
    /* package private */ $4() { }

    /** Instance of natural number 4. */
    public static final $4 nat = new $4();

    /** Returns {@code $3} as preceding natural. */
    @Override public $3 pred() { return $3.nat; }

    /** Returns {@code $5} as succeeding natural. */
    @Override public $5 succ() { return $5.nat; }

    /** Returns the runtime value encoding as {@code int}. */
    @Override public int ordinal() { return 4; }

    /** Returns the string representation of this natural number.*/
    @Override public String toString()  { return "$4"; }
}

