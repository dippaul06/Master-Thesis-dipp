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

package magma.exa.control.exception;

import java.util.NoSuchElementException;

/**
 * Exception Factory.
 */
public enum Exceptions {
    ;
    /**
     * Constructs an illegal argument exception. This exception is
     * thrown to indicate that a method has been passed an illegal
     * or inappropriate argument.
     *
     * @return new {@link IllegalArgumentException} object.
     */
    public static IllegalArgumentException illegalArgument() {
        return new IllegalArgumentException();
    }

    /**
     * Constructs an illegal argument exception with the given detail message.
     * This exception is thrown to indicate that a method has been passed an
     * illegal or inappropriate argument.
     *
     * @param message detail message which is saved for later retrieval via
     *                {@link Throwable#getMessage}.
     * @return new {@link IllegalArgumentException} object.
     */
    public static IllegalArgumentException illegalArgument(final String message) {
        return new IllegalArgumentException(message);
    }

    /**
     * Constructs an illegal argument exception with the given detail message.
     * This exception is thrown to indicate that a method has been passed an
     * illegal or inappropriate argument.
     *
     * @param message detail message which is saved for later retrieval via
     *                {@link Throwable#getMessage}. The message argument is a
     *                <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args    arguments referenced by format specifiers in the message.
     *                If there are more arguments than format identifiers, the
     *                excess arguments are ignored. The number of arguments is
     *                variable and can be zero. The behavior for a null argument
     *                depends on the <a href="../util/Formatter.html#syntax">conversion</a>.
     * @return new {@link IllegalArgumentException} object.
     */
    public static IllegalArgumentException illegalArgument(final String message,
                                                           final Object... args) {
        return new IllegalArgumentException(String.format(message, args));
    }

    /**
     * Constructs an illegal argument exception with the given detail message
     * and cause. This exception is thrown to indicate that a method has been
     * passed an illegal or inappropriate argument.
     *
     * Note that the detail message associated with {@code cause} is NOT
     * automatically incorporated in this exception's detail message.
     *
     * @param message detail message which is saved for later retrieval via
     *                {@link Throwable#getMessage}.
     * @param cause   which is saved for later retrieval via {@link Throwable#getCause()}).
     *                A null value indicates a nonexistent or unknown cause.
     * @return new {@link IllegalArgumentException} object.
     */
    public static IllegalArgumentException illegalArgument(final String message,
                                                           final Throwable cause) {
        return new IllegalArgumentException(message, cause);
    }

    /**
     * Constructs an illegal argument exception with the given detail message
     * and cause. This exception is thrown to indicate that a method has been
     * passed an illegal or inappropriate argument.
     *
     * Note that the detail message associated with {@code cause} is NOT
     * automatically incorporated in this exception's detail message.
     *
     * @param message detail message which is saved for later retrieval via
     *                {@link Throwable#getMessage}. The message argument is a
     *                <a href="../util/Formatter.html#syntax">format string</a>.
     * @param cause   which is saved for later retrieval via {@link Throwable#getCause()}).
     *                A null value indicates a nonexistent or unknown cause.
     * @param args    arguments referenced by format specifiers in the message.
     *                If there are more arguments than format identifiers, the
     *                excess arguments are ignored. The number of arguments is
     *                variable and can be zero. The behavior for a null argument
     *                depends on the <a href="../util/Formatter.html#syntax">conversion</a>.
     * @return new {@link IllegalArgumentException} object.
     */
    public static IllegalArgumentException illegalArgument(final String message,
                                                           final Throwable cause,
                                                           final Object... args) {
        return new IllegalArgumentException(String.format(message, args), cause);
    }

    // ----------------------------------------------------------

    /**
     * Constructs a new illegal state exception. This exception is thrown to
     * indicate that a method has been invoked at an illegal or inappropriate
     * time. In other words, the Java environment or Java application is not
     * in an appropriate state for the requested operation.
     *
     * @return new {@link IllegalStateException} object.
     */
    public static IllegalStateException illegalState() {
        return new IllegalStateException();
    }

    /**
     * Constructs a new exception with the given detail message.
     * This exception is thrown to indicate that a method has been invoked at
     * an illegal or inappropriate time. In other words, the Java environment
     * or Java application is not in an appropriate state for the requested
     * operation.
     *
     * @param message detail message which is saved for later retrieval via
     *                {@link Throwable#getMessage}.
     * @return new {@link IllegalStateException} object.
     */
    public static IllegalStateException illegalState(final String message) {
        return new IllegalStateException(message);
    }

    /**
     * Constructs a new exception with the given detail message and cause.
     * This exception is thrown to indicate that a method has been invoked at
     * an illegal or inappropriate time. In other words, the Java environment
     * or Java application is not in an appropriate state for the requested
     * operation.
     *
     * @param message detail message which is saved for later retrieval via
     *                {@link Throwable#getMessage}. The message argument is a
     *                <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args    arguments referenced by format specifiers in the message.
     *                If there are more arguments than format identifiers, the
     *                excess arguments are ignored. The number of arguments is
     *                variable and can be zero. The behavior for a null argument
     *                depends on the <a href="../util/Formatter.html#syntax">conversion</a>.
     * @return new {@link IllegalStateException} object.
     */
    public static IllegalStateException illegalState(final String message,
                                                     final Object... args) {
        return illegalState(String.format(message, args));
    }

    /**
     * Constructs a new exception with the given detail message and cause.
     * This exception is thrown to indicate that a method has been invoked at
     * an illegal or inappropriate time. In other words, the Java environment
     * or Java application is not in an appropriate state for the requested
     * operation.
     *
     * Note that the detail message associated with {@code cause} is NOT
     * automatically incorporated in this exception's detail message.
     *
     * @param message detail message which is saved for later retrieval via
     *                {@link Throwable#getMessage}.
     * @param cause   which is saved for later retrieval via {@link Throwable#getCause()}).
     *                A null value indicates a nonexistent or unknown cause.
     * @return new {@link IllegalStateException} object.
     */
    public static IllegalStateException illegalState(final String message,
                                                     final Throwable cause) {
        return new IllegalStateException(message, cause);
    }

    /**
     * Constructs a new exception with the given detail message and cause.
     * This exception is thrown to indicate that a method has been invoked at
     * an illegal or inappropriate time. In other words, the Java environment
     * or Java application is not in an appropriate state for the requested
     * operation.
     *
     * Note that the detail message associated with {@code cause} is NOT
     * automatically incorporated in this exception's detail message.
     *
     * @param message detail message which is saved for later retrieval via
     *                {@link Throwable#getMessage}. The message argument is a
     *                <a href="../util/Formatter.html#syntax">format string</a>.
     * @param cause   which is saved for later retrieval via {@link Throwable#getCause()}).
     *                A null value indicates a nonexistent or unknown cause.
     * @param args    arguments referenced by the format specifiers in the format
     *                string. If there are more arguments than format specifiers, the
     *                extra arguments are ignored. The number of arguments is variable
     *                and may be zero. The behaviour on a {@code null} argument depends
     *                on the <a href="../util/Formatter.html#syntax">conversion</a>.
     * @return new {@link IllegalStateException} object.
     */
    public static IllegalStateException illegalState(final String message,
                                                     final Throwable cause,
                                                     final Object... args) {
        return illegalState(String.format(message, args), cause);
    }

    // ----------------------------------------------------------

    /**
     * Constructs a new null pointer exception without detail message.
     * This exception is thrown to indicate that an application attempts to use
     * {@code null} in a case where an object is required.
     *
     * @return new {@link NullPointerException} object.
     */
    public static NullPointerException nullPointer() {
        return new NullPointerException();
    }

    /**
     * Constructs a new null pointer exception with detail message.
     * This exception is thrown to indicate that an application attempts to use
     * {@code null} in a case where an object is required.
     *
     * @param message detail message which is saved for later retrieval via
     *                {@link Throwable#getMessage}. The message argument is a
     *                <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args    arguments referenced by format specifiers in the message.
     *                If there are more arguments than format identifiers, the
     *                excess arguments are ignored. The number of arguments is
     *                variable and can be zero. The behavior for a null argument
     *                depends on the <a href="../util/Formatter.html#syntax">conversion</a>.
     * @return new {@link NullPointerException} object.
     */
    public static NullPointerException nullPointer(final String message,
                                                   final Object... args) {
        return new NullPointerException(String.format(message, args));
    }

    // ----------------------------------------------------------

    /**
     * Constructs a new null pointer exception without detail message.
     * This exception is thrown to indicate that an application attempts to use
     * {@code null} in a case where an object is required.
     *
     * @return new {@link NoSuchElementException} object.
     */
    public static NoSuchElementException noSuchElement() {
        return new NoSuchElementException();
    }

    /**
     * Constructs a new null pointer exception with detail message.
     * This exception is thrown to indicate that an application attempts to use
     * {@code null} in a case where an object is required.
     *
     * @param message detail message which is saved for later retrieval via
     *                {@link Throwable#getMessage}. The message argument is a
     *                <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args    arguments referenced by format specifiers in the message.
     *                If there are more arguments than format identifiers, the
     *                excess arguments are ignored. The number of arguments is
     *                variable and can be zero. The behavior for a null argument
     *                depends on the <a href="../util/Formatter.html#syntax">conversion</a>.
     * @return new {@link NoSuchElementException} object.
     */
    public static NoSuchElementException noSuchElement(final String message,
                                                       final Object... args) {
        return new NoSuchElementException(String.format(message, args));
    }

    // ----------------------------------------------------------

    /**
     * Constructs a new index out of bounds exception with the causing index.
     * This exception is thrown to indicate that an index of some sort
     * (such as to an array, to a string, or to a vector) is out of range.
     *
     * @param index being out of unknown bounds.
     * @return new {@link IndexOutOfBoundsException} object.
     */
    public static IndexOutOfBoundsException outOfBounds(final long index) {
        return new IndexOutOfBoundsException(String.format("index[%d]", index));
    }

    /**
     * Constructs a new index out of bounds exception with detail message.
     * This exception is thrown to indicate that an index of some sort
     * (such as to an array, to a string, or to a vector) is out of range.
     *
     * @param message detail message which is saved for later retrieval via
     *                {@link Throwable#getMessage}. The message argument is a
     *                <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args    arguments referenced by format specifiers in the message.
     *                If there are more arguments than format identifiers, the
     *                excess arguments are ignored. The number of arguments is
     *                variable and can be zero. The behavior for a null argument
     *                depends on the <a href="../util/Formatter.html#syntax">conversion</a>.
     * @return new {@link IndexOutOfBoundsException} object.
     */
    public static IndexOutOfBoundsException outOfBounds(final String message,
                                                        final Object... args) {
        return new IndexOutOfBoundsException(String.format(message, args));
    }

    // ----------------------------------------------------------

    /**
     * Constructs an unsupported operation exception with detail message.
     * This exception is thrown to indicate that the requested operation
     * is not supported.
     *
     * @return new {@link UnsupportedOperationException} object.
     */
    public static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException();
    }

    /**
     * Constructs an unsupported operation exception with detail message.
     * This exception is thrown to indicate that the requested operation
     * is not supported.
     *
     * @param message detail message which is saved for later retrieval via
     *                {@link Throwable#getMessage}. The message argument is a
     *                <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args    arguments referenced by format specifiers in the message.
     *                If there are more arguments than format identifiers, the
     *                excess arguments are ignored. The number of arguments is
     *                variable and can be zero. The behavior for a null argument
     *                depends on the <a href="../util/Formatter.html#syntax">conversion</a>.
     * @return new {@link UnsupportedOperationException} object.
     */
    public static UnsupportedOperationException unsupported(final String message,
                                                            final Object... args) {
        return new UnsupportedOperationException(String.format(message, args));
    }

    /**
     * Constructs a new impossible state exception. This exception is never
     * thrown and exists to indicate that fact in code.
     *
     * @return {@literal ⊥}
     */
    public static IllegalStateException impossible() {
        throw new IllegalStateException("provable turd");
    }
}
