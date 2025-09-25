package com.example.transactions.application.shared.pagination;

/**
 * Request parameters for pagination.
 *
 * @param page the page number (0-based)
 * @param size the page size
 */
public record PageRequest(int page, int size) {

  /**
   * Creates a PageRequest with validation.
   *
   * @param page the page number (non-negative)
   * @param size the page size (positive, max 100)
   */
  public PageRequest {
    if (page < 0) {
      throw new IllegalArgumentException("Page must be non-negative");
    }
    if (size <= 0) {
      throw new IllegalArgumentException("Size must be positive");
    }
    if (size > 100) {
      throw new IllegalArgumentException("Size cannot exceed 100");
    }
  }

  /**
   * Creates a default page request (page 0, size 20).
   *
   * @return a default PageRequest
   */
  public static PageRequest defaultRequest() {
    return new PageRequest(0, 20);
  }

  /**
   * Calculates the offset for this page request.
   *
   * @return the offset (page * size)
   */
  public int getOffset() {
    return page * size;
  }

  /**
   * Creates the next page request.
   *
   * @return a PageRequest for the next page
   */
  public PageRequest next() {
    return new PageRequest(page + 1, size);
  }

  /**
   * Creates the previous page request.
   *
   * @return a PageRequest for the previous page, or this request if already at page 0
   */
  public PageRequest previous() {
    if (page == 0) {
      return this;
    }
    return new PageRequest(page - 1, size);
  }
}
