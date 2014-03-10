Toy project to do some load-testing comparisons of unfiltered between netty3 and netty4. Initial code based on unfiltered/unfiltered-netty.g8 template.

This was an attempt to minimally replicate an observed decrease in throughput after migrating from netty3 to netty4. More info at: https://groups.google.com/forum/#!topic/unfiltered-scala/A-HxTGMIy2Y

Unfortunately, the nature of the blocking IO has not been successfully replicated yet. For example, when the real app is run with netty4 and the unbounded ThreadPool cycle.Plan, it runs out of memory.

# Branches
`master`
* Uses `unfiltered-0.7.1`

`netty4`
* Uses unfiltered built against Scala 2.10.3 from master at: https://github.com/unfiltered/unfiltered/commit/a79dae21342b270029607c8885083e3bdac04821

# Environment
* Mac Mini 2.6GHz i7
* 16GB 1600 MHz DDR3

* OSX 10.9.2
* Scala 2.10.3
* java version "1.7.0_15"
* Java(TM) SE Runtime Environment (build 1.7.0_15-b03)
* Java HotSpot(TM) 64-Bit Server VM (build 23.7-b01, mixed mode)

# Results

Some load testing runs with [siege](http://www.joedog.org/siege-home/). Note that tests were run multiple times and selected results are typical of each run.

## Netty 3
````
        Date & Time,  Trans,  Elap Time,  Data Trans,  Resp Time,  Trans Rate,  Throughput,  Concurrent,    OKAY,   Failed
**** netty3 with async.Plan (http://localhost:8080/async-time) ****
2014-03-10 12:45:53,   3858,     119.57,           0,       0.46,       32.27,        0.00,       14.97,    3858,       0
**** netty3 with cycle.Plan and MemoryAwareThreadPoolExecutor (4 threads) (http://localhost:8080/cycle-time-4) ****
2014-03-10 14:31:25,   1870,     119.61,           0,       0.95,       15.63,        0.00,       14.92,    1870,       0
**** netty3 with cycle.Plan and MemoryAwareThreadPoolExecutor (8 threads) (http://localhost:8080/cycle-time-8) ****
2014-03-10 14:34:34,   3776,     119.45,           0,       0.47,       31.61,        0.00,       14.97,    3776,       0
**** netty3 with cycle.Plan and MemoryAwareThreadPoolExecutor (12 threads) (http://localhost:8080/cycle-time-12) ****
2014-03-10 14:38:04,   5799,     119.68,           0,       0.31,       48.45,        0.00,       14.97,    5799,       0
**** netty3 with cycle.Plan and MemoryAwareThreadPoolExecutor (16 threads) (http://localhost:8080/cycle-time-16) ****
2014-03-10 14:44:14,   7082,     119.59,           0,       0.25,       59.22,        0.00,       14.97,    7082,       0
**** netty3 with cycle.Plan and SynchronousExecution (http://localhost:8080/sync-cycle-time) ****
2014-03-10 15:41:39,   7150,     119.23,           0,       0.25,       59.97,        0.00,       14.98,    7150,       0
````

## Netty4
````
**** netty4 with future.Plan (http://localhost:8080/future-time) ****
2014-03-10 14:18:15,   3880,     119.30,           0,       0.46,       32.52,        0.00,       14.96,    3880,       0
**** netty4 with cycle.Plan and ThreadPool (http://localhost:8080/deferred-cycle-time) ****
2014-03-10 15:17:16,   7034,     119.12,           0,       0.25,       59.05,        0.00,       14.97,    7034,       0
**** netty4 with cycle.Plan and SynchronousExecution (http://localhost:8080/sync-cycle-time) ****
2014-03-10 15:20:14,   5761,     119.02,           0,       0.31,       48.40,        0.00,       14.96,    5761,       0
````