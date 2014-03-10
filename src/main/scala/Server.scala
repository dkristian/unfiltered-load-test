package com.example

/** embedded server */
object Server {
  val logger = org.clapper.avsl.Logger(Server.getClass)

  def main(args: Array[String]) {
    unfiltered.netty.Http(8080)
      .handler(FutureTime)
      .handler(SyncCycleTime)
      .handler(DeferredCycleTime)
      .run { s =>
        logger.info("starting unfiltered app at localhost on port %s"
                    .format(s.port))
      }
    dispatch.Http.shutdown()
  }
}
