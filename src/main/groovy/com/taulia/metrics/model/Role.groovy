package com.taulia.metrics.model

enum Role {
  /**
   * Full-time code slinger
   */
  Engineer,

  /**
   * Quality Engineer
   */
  Quality,

  /**
   * Manager, part-time code slinger
   */
  Manager

  boolean isSoftwareEngineer() {
    this in [Engineer, Manager]
  }

  boolean isFullTimeEngineer() {
    this == Engineer
  }
}