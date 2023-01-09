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

package magma.exa.base;

import java.io.IOException;
import java.util.function.Function;

/**
 * Javadoc is an important part of Java programming, but there is
 * relatively little discussion about what makes a good Javadoc style.
 *
 * Official Oracle documentation
 * @see <a href="blog.joda.org/2012/11/javadoc-coding-standards.html">
 *     Javadoc coding standards</a>
 *
 * Rules were taken here from:
 * @see <a href="blog.joda.org/2012/11/javadoc-coding-standards.html">
 *     Javadoc coding standards</a>
 * Credits: Stephen Colebourne (tight hobby!)
 * (Except for rule 7 and those that this author does not follow)
 */
enum JavaDoc {

    A_CODING_STANDARD,

    /**
     * WRITE JAVADOC TO BE READ AS SOURCE CODE
     *
     * When we think of 'Javadoc' we often think of the online Javadoc HTML pages.
     * However, this is not the only way that Javadoc is consumed. A key way of
     * absorbing Javadoc is reading source code, either of code you or your team
     * wrote, or third party libraries.
     *
     * => Making Javadoc readable as source code is critical,
     *    and these standards are guided by this principal.
     */
    RULE_1,

    /**
     * PUBLIC & PROTECTED
     *
     * All public and protected methods should be fully defined with Javadoc.
     * Package and private methods do not have to be, but may benefit from it.
     *
     * If a method is overridden in a subclass, Javadoc should only be present
     * if it says something distinct to the original definition of the method.
     * The {@literal @Override} annotation should be used to indicate to source
     * code readers that the Javadoc is inherited in addition to its normal
     * meaning.
     */
    RULE_2,

    /**
     * USE THE STANDARD STYLE FOR THE JAVADOC COMMENT
     *
     * Javadoc only requires '/**' at the start and a */
    /* at the end. In addition to this, use a single star on
     * each additional line:
     */
    RULE_3 {

        /** <= Javadoc only requires at the start and
         *  <= a single star on each additional line
         *     and at the
      => */
        interface end { }

        /**
         * Standard comment.
         */
        interface foo { }

        /** Compressed comment. */
        interface bar { }
    },


    /**
     * Use simple HTML tags, not valid XHTML
     *
     * Javadoc uses HTML tags to identify paragraphs and other
     * elements. Many developers get drawn to the thought that
     * XHTML is necessarily best, ensuring that all tags open
     * and close correctly.
     *
     * This is a mistake. XHTML adds many extra tags that make
     * the Javadoc harder to read as source code. The Javadoc
     * parser will interpret the incomplete HTML tag soup just
     * fine.
     */
    RULE_4,

    /**
     * Use a single <p> tag between paragraphs
     *
     * Longer Javadoc always needs multiple paragraphs. This naturally
     * results in a question of how and where to add the paragraph tags.
     *
     * Place a single <p> tag on the blank line between paragraphs:
     */
    RULE_5 {

        /**
         * First paragraph.
         * <p>
         * Second paragraph.
         * May be on multiple lines.
         * <p>
         * Third paragraph.
         */
        void sample() { }
    },

    /**
     * Use a single <li> tag for items in a list
     *
     * Lists are useful in Javadoc when explaining a set of options,
     * choices or issues. These standards place a single <li> tag at
     * the start of the line and no closing tag.
     *
     * In order to get correct paragraph formatting,
     * extra paragraph tags are required:
     */
    RULE_6 {

        /**
         * First paragraph.
         * <p><ul>
         * <li>the first item
         * <li>the second item
         * <li>the third item
         * </ul><p>
         * Second paragraph.
         */
        void sample() { }
    },

    /**
     * The first sentence gives the class or type-level entity a substantial
     * right to exist. Sealed with a dot. So avoid noise and consider the
     * protocol interaction with whom and why and that in generally short.
     * <p>
     * While not required, it is recommended that the first sentence of your
     * type theorem is a paragraph to itself. This helps to preserve the Work
     * (physics) for the participants of your type.
     * <p>
     * I'll buy you a beer if you do this before you write a line of code.
     */
    RULE_7,

    /**
     * Use "this" to refer to an instance of the class
     *
     * When referring to an instance of the class being documented,
     * use "this" to reference it. For example, "Returns a copy of
     * this foo with the bar value updated".
     */
    RULE_8,

    /**
     * Aim for short single line sentences
     *
     * Wherever possible, make Javadoc sentences fit on a single line.
     * Allow flexibility in line length, favouring between 80 and 120
     * characters to make this work. In most cases, each new sentence
     * should start on a new line.
     *
     * This aids readability as source code, and simplifies refactoring
     * re-writes of complex Javadoc.
     */
    RULE_9 {

        /**
         * This is the first paragraph, on one line.
         * <p>
         * This is the first sentence of the second paragraph, on one line.
         * This is the second sentence of the second paragraph, on one line.
         * This is the third sentence of the second paragraph which is a bit
         * longer so has been split onto a second line, as that makes sense.
         * This is the fourth sentence, which starts a new line, even though
         * there is space above.
         */
        void sample() { }
    },

    /**
     * Use {@link } and {@code } wisely
     *
     * Many Javadoc descriptions reference other methods and classes.
     * This can be achieved most effectively using the @link and @code
     * features.
     *
     * The @link feature creates a visible hyperlink in generated Javadoc
     * to the target. The @link target is one of the following forms:
     */
    RULE_10 {

        /**
         * First paragraph.
         * <p>
         * Link to a class named 'Foo': {@link Foo}.
         * Link to a method 'bar' on a class named 'Foo': {@link Foo#bar}.
         * Link to a method 'baz' on this class: {@link #sample}.
         * Link specifying text of the hyperlink after a space:  {@link Foo the Foo class}.
         * Link to a method handling method overload {@link Foo#bar(String,int)}.
         */
        interface Foo {
            void bar();
            void bar(String s, int i);
        }

        void sample() { }

        /**
         * The @code feature provides a section of fixed-width font, ideal
         * for references to methods and class names. While @link references
         * are checked by the Javadoc compiler, @code references are not.
         *
         * Only use @link on the first reference to a specific class or
         * method. Use @code for subsequent references. This avoids excessive
         * hyperlinks cluttering up the Javadoc.
         */
        enum NOTE {}
    },

    /**
     * Never use {@link} in the first sentence
     *
     * The first sentence is used in the higher level Javadoc.
     *
     * Adding a hyperlink in that first sentence makes
     * the higher level documentation more confusing.
     *
     * Always use {@code} in the first sentence if necessary.
     *
     * {@link} can be used from the second sentence/paragraph onwards.
     */
    RULE_11,

    /**
     * Do not use @code for 'null', 'true' or 'false'
     *
     * The concepts of 'null', 'true' and 'false' are natural in Javadoc.
     *
     * Adding {@code} for every occurrence is a burden to both the
     * reader & writer of the Javadoc and adds no real value.
     */
    RULE_12,

    /**
     * Use @param, @return and @throws
     *
     * Almost all methods take in a parameter, return a result or both.
     *
     * The @param and @return features specify those inputs and outputs.
     *
     * The @throws feature specifies the thrown exceptions.
     *
     * The @param entries should be specified in parameters declaration order.
     *
     * The @return should be after the @param entries, followed by @throws.
     */
    RULE_13,

    /**
     * Use @param for generics
     *
     * If a class or method has generic type parameters, then these should
     *
     * be documented. The correct approach is a @param tag with the parameter
     *
     * name of <T> where T is the type parameter name.
     */
    RULE_14 {

        /**
         * A Functor declares a generic covariant functorial operation
         * {@link #fmap} over a parameter {@code A}. (poor Hacker's typeclass)
         * <p>
         * Functor abstracts the ability to map over a computational context.
         *
         * @param <A> type to be mapped over.
         * @param <U> unification parameter.
         */
        interface Functor<A, U extends Functor<?, U>> {

            /**
             * Covariantly transmute this functor's parameter using the
             * given mapping function.
             * <p>
             * Generally, the result type of this operation is to be
             * specialized to the implementing functor type.
             * (via return-type covariance).
             *
             * @param fn  mapping function over the functor parameter
             * @param <B> type of produced functor parameter
             * @return functor instance over parameter {@code B}
             */
            <B> Functor<B, U> fmap(Function<? super A, ? extends B> fn);
        }
    },

    /**
     * Use one blank line before @param
     *
     * There should be one blank line between the Javadoc text
     * and the first @param or @return. This aids readability.
     *
     * @see JavaDoc#RULE_14
     */
    RULE_15,

    /**
     * Treat @throws as an if clause
     *
     * The @throws feature should normally be followed by "if" and
     * the rest of the phrase describing the condition.
     *
     * For example, "@throws IllegalArgumentException if the file could not be found". This aids readability in source code and when generated.
     */
    RULE_16 {

        /**
         * Throws an exception to bring up a showcase.
         *
         * @param path to be stalked for files by luke
         * @throws IOException if the file could not be found
         */
        void lukeFileStalker(java.nio.file.Path path) throws IOException {
            throw new IOException();
        }
    },

    /**
     * Avoid @author
     *
     * The @author feature can be used to record the authors of the class.
     * This should be avoided, as it is usually out of date, and it can
     * promote code ownership by an individual. The source control system
     * is in a much better position to record authors.
     */
    RULE_17 { /* Unless you're proud of your code turd. */ },

    /**
     * If you are even half way capable of documenting code like this,
     * then you are working at the wrong company ;)
     *
     * {@link java.util.concurrent.ConcurrentHashMap}
     */
    RULE_18,

    /**
     * Roughly the opposite of that:
     * @see <a href="https://github.com/google/guava/blob/master/guava/src/com/google/common/collect/Iterables.java">
     *     abc.xyz fail</a>
     */
    SUMMARY
}
