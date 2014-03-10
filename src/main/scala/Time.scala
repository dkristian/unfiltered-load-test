package com.example

import unfiltered.request._
import unfiltered.response._

import unfiltered.netty._

trait Time extends {
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

/** Asynchronous plan that gets the time in a ridiculous fashion.
 *  (But imagine that it's using a vital external HTTP service to
 *  inform its response--this is a fine way to do that.) */
object AsyncTime extends async.Plan with Time
  with ServerErrorResponse {

  def intent = {
    case req @ GET(Path("/async-time")) =>
      implicit val executionContext = scala.concurrent.ExecutionContext.Implicits.global
      for {
        time <- scala.concurrent.Future(blockingTime())
      } {
        req.respond(view(time))
      }
  }
}

import cycle.{DeferralExecutor,DeferredIntent}
class CycleTime(threads:Int) extends cycle.Plan with Time
  with DeferralExecutor with DeferredIntent
  with ServerErrorResponse {
  
  import org.jboss.netty.handler.execution.MemoryAwareThreadPoolExecutor
  lazy val underlying = new MemoryAwareThreadPoolExecutor(threads, 0, 0)

  val path = "/cycle-time-" + threads.toString
  def intent = {
    case req @ GET(Path(`path`)) =>
      view(blockingTime())
  }
}
object CycleTime4 extends CycleTime(4)
object CycleTime8 extends CycleTime(8)
object CycleTime12 extends CycleTime(12)
object CycleTime16 extends CycleTime(16)

object SyncCycleTime extends cycle.Plan with Time
  with cycle.SynchronousExecution
  with ServerErrorResponse {
  
  def intent = {
    case req @ GET(Path("/sync-cycle-time")) =>
      view(blockingTime())
  }
}

