package com.example.transactions.shared.result;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A generic Result type for handling success and error cases.
 *
 * <p>This class provides a functional approach to error handling, allowing operations to return
 * either a successful result or an error without throwing exceptions.
 *
 * @param <T> the type of the success value
 * @param <E> the type of the error
 */
public sealed interface Result<T, E> permits Result.Success, Result.Error {

  /**
   * Creates a successful result.
   *
   * @param value the success value
   * @param <T> the type of the success value
   * @param <E> the type of the error
   * @return a successful result
   */
  static <T, E> Result<T, E> success(T value) {
    return new Success<>(value);
  }

  /**
   * Creates an error result.
   *
   * @param error the error value
   * @param <T> the type of the success value
   * @param <E> the type of the error
   * @return an error result
   */
  static <T, E> Result<T, E> error(E error) {
    return new Error<>(error);
  }

  /**
   * Checks if this result is a success.
   *
   * @return true if this is a success result
   */
  boolean isSuccess();

  /**
   * Checks if this result is an error.
   *
   * @return true if this is an error result
   */
  boolean isError();

  /**
   * Gets the success value if present.
   *
   * @return an Optional containing the success value, or empty if this is an error
   */
  Optional<T> getValue();

  /**
   * Gets the error value if present.
   *
   * @return an Optional containing the error value, or empty if this is a success
   */
  Optional<E> getError();

  /**
   * Maps the success value to a new type.
   *
   * @param mapper the mapping function
   * @param <U> the new success type
   * @return a new result with the mapped value, or the same error
   */
  <U> Result<U, E> map(Function<T, U> mapper);

  /**
   * Flat maps the success value to a new result.
   *
   * @param mapper the mapping function
   * @param <U> the new success type
   * @return the result of the mapping function, or the same error
   */
  <U> Result<U, E> flatMap(Function<T, Result<U, E>> mapper);

  /**
   * Executes an action if this is a success.
   *
   * @param action the action to execute
   * @return this result
   */
  Result<T, E> onSuccess(Consumer<T> action);

  /**
   * Executes an action if this is an error.
   *
   * @param action the action to execute
   * @return this result
   */
  Result<T, E> onError(Consumer<E> action);

  /**
   * Gets the success value or throws an exception.
   *
   * @param exceptionMapper function to map error to exception
   * @param <X> the exception type
   * @return the success value
   * @throws X if this is an error result
   */
  <X extends Throwable> T getOrThrow(Function<E, X> exceptionMapper) throws X;

  /** Successful result implementation. */
  record Success<T, E>(T value) implements Result<T, E> {
    @Override
    public boolean isSuccess() {
      return true;
    }

    @Override
    public boolean isError() {
      return false;
    }

    @Override
    public Optional<T> getValue() {
      return Optional.of(value);
    }

    @Override
    public Optional<E> getError() {
      return Optional.empty();
    }

    @Override
    public <U> Result<U, E> map(Function<T, U> mapper) {
      return success(mapper.apply(value));
    }

    @Override
    public <U> Result<U, E> flatMap(Function<T, Result<U, E>> mapper) {
      return mapper.apply(value);
    }

    @Override
    public Result<T, E> onSuccess(Consumer<T> action) {
      action.accept(value);
      return this;
    }

    @Override
    public Result<T, E> onError(Consumer<E> action) {
      return this;
    }

    @Override
    public <X extends Throwable> T getOrThrow(Function<E, X> exceptionMapper) {
      return value;
    }
  }

  /** Error result implementation. */
  record Error<T, E>(E error) implements Result<T, E> {
    @Override
    public boolean isSuccess() {
      return false;
    }

    @Override
    public boolean isError() {
      return true;
    }

    @Override
    public Optional<T> getValue() {
      return Optional.empty();
    }

    @Override
    public Optional<E> getError() {
      return Optional.of(error);
    }

    @Override
    public <U> Result<U, E> map(Function<T, U> mapper) {
      return Result.error(error);
    }

    @Override
    public <U> Result<U, E> flatMap(Function<T, Result<U, E>> mapper) {
      return Result.error(error);
    }

    @Override
    public Result<T, E> onSuccess(Consumer<T> action) {
      return this;
    }

    @Override
    public Result<T, E> onError(Consumer<E> action) {
      action.accept(error);
      return this;
    }

    @Override
    public <X extends Throwable> T getOrThrow(Function<E, X> exceptionMapper) throws X {
      throw exceptionMapper.apply(error);
    }
  }
}
