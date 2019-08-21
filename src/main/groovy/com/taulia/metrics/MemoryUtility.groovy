package com.taulia.metrics

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MemoryUtility {
  
  private static Logger logger = LoggerFactory.getLogger(MemoryUtility)
  
  static void printMemoryStatistics(String marker) {
    logger.debug "\n${marker.toUpperCase()}"

    int mb = 1024*1024

    //Getting the runtime reference from system
    Runtime runtime = Runtime.getRuntime()

    logger.debug("##### Heap utilization statistics [MB] #####")

    //Print used memory
    logger.debug("Used Memory:"
      + (runtime.totalMemory() - runtime.freeMemory()) / mb)

    //Print free memory
    logger.debug("Free Memory:"
      + runtime.freeMemory() / mb)

    //Print total available memory
    logger.debug("Total Memory:" + runtime.totalMemory() / mb)

    //Print Maximum available memory
    logger.debug("Max Memory:" + runtime.maxMemory() / mb)

    logger.debug ''
  }
}
