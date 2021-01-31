package com.example.smartpad;

public final class ChargeResult {

  public static com.example.smartpad.ChargeResult success() {
    return new com.example.smartpad.ChargeResult(true, false, null);
  }

  public static com.example.smartpad.ChargeResult error(String message) {
    return new com.example.smartpad.ChargeResult(false, false, message);
  }

  public static com.example.smartpad.ChargeResult networkError() {
    return new com.example.smartpad.ChargeResult(false, true, null);
  }

  public final boolean success;
  public final boolean networkError;

  /**
   * Set if {@link #success} is false and {@link #networkError} is false.
   */
  public final String errorMessage;

  private ChargeResult(boolean success, boolean networkError, String errorMessage) {
    this.success = success;
    this.networkError = networkError;
    this.errorMessage = errorMessage;
  }
}
