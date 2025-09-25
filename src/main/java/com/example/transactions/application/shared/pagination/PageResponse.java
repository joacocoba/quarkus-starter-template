package com.example.transactions.application.shared.pagination;

import java.util.List;

/**
 * Response containing paginated results.
 *
 * @param <T> the type of items in the page
 */
public record PageResponse<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages,
    boolean hasNext,
    boolean hasPrevious) {

  /**
   * Creates a PageResponse with calculated metadata.
   *
   * @param content the list of items
   * @param pageRequest the original page request
   * @param totalElements the total number of elements across all pages
   * @param <T> the type of items
   * @return a new PageResponse
   */
  public static <T> PageResponse<T> of(
      List<T> content, PageRequest pageRequest, long totalElements) {

    int totalPages = (int) Math.ceil((double) totalElements / pageRequest.size());
    boolean hasNext = pageRequest.page() < totalPages - 1;
    boolean hasPrevious = pageRequest.page() > 0;

    return new PageResponse<>(
        content,
        pageRequest.page(),
        pageRequest.size(),
        totalElements,
        totalPages,
        hasNext,
        hasPrevious);
  }

  /**
   * Creates an empty page response.
   *
   * @param pageRequest the original page request
   * @param <T> the type of items
   * @return an empty PageResponse
   */
  public static <T> PageResponse<T> empty(PageRequest pageRequest) {
    return new PageResponse<>(
        List.of(), pageRequest.page(), pageRequest.size(), 0L, 0, false, false);
  }

  /**
   * Checks if this page is empty.
   *
   * @return true if the page has no content
   */
  public boolean isEmpty() {
    return content.isEmpty();
  }

  /**
   * Gets the number of items in this page.
   *
   * @return the number of items
   */
  public int getNumberOfElements() {
    return content.size();
  }
}
