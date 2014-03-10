package com.example

import io.netty.channel.ChannelHandler.Sharable

import unfiltered.request._
import unfiltered.response._

import unfiltered.netty._

trait Time {
  def blockingTime() = {
    val delay = scala.util.Random.nextInt(500)
    Thread.sleep(delay)
    new java.util.Date().toString
  }

  def view(time: String) = {
    Html(
     <html><body>
       The current time is: { time }
     </body></html>
   )
  }
}

@Sharable
object FutureTime extends future.Plan with Time
  with ServerErrorResponse {
  val logger = org.clapper.avsl.Logger(getClass)
  
  implicit def executionContext = scala.concurrent.ExecutionContext.Implicits.global
  def intent = {
    case req @ GET(Path("/future-time")) => 
      scala.concurrent.Future(view(blockingTime()))
  }
}

import cycle.{DeferralExecutor,DeferredIntent,SynchronousExecution}
@Sharable
object DeferredCycleTime extends cycle.Plan with Time
  with cycle.ThreadPool
  with ServerErrorResponse {
  val logger = org.clapper.avsl.Logger(getClass)

  def intent = {
    case req @ GET(Path("/deferred-cycle-time")) => 
      view(blockingTime())
  }
}

@Sharable
object SyncCycleTime extends cycle.Plan with Time
  with SynchronousExecution
  with ServerErrorResponse {
  val logger = org.clapper.avsl.Logger(getClass)
  
  def intent = {
    case req @ GET(Path("/sync-cycle-time")) => 
      view(blockingTime())
  }
}
