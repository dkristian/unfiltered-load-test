package com.example

/** embedded server */
object Server {
  val logger = org.clapper.avsl.Logger(Server.getClass)

  def main(args: Array[String]) {
    unfiltered.netty.Http(8080)
      .handler(AsyncTime)
      .handler(CycleTime4)
      .handler(CycleTime8)
      .handler(CycleTime12)
      .handler(CycleTime16)
      .handler(SyncCycleTime)
      .run { s =>
        logger.info("starting unfiltered app at localhost on port %s"
                    .format(s.port))
      }
    dispatch.Http.shutdown()
  }
}
