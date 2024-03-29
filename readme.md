# Java Concurrency in Practice
I created this code to complement the book by showing the pitfalls of using threads in java and by showing the correct use of some concurrency primitives since java 1.5. It is no replacement for the book and does not necessarily cover all of its aspects. Some code goes beyond the book for instance when trying to actually prove what is said, for instance the thread-unsafety of java.util.HashMap.
The code is presented in 'puzzler' style. So it can contain unsafe code and it's up to the reader to discover that. This readme is added as an explanation.

## Chapter 1 Fundamentals
- [`UnsafeSequence`](src/chapter1/UnsafeSequence.java) Shows that concurrent reads will see the same value, resulting in undefined behavior. The code uses the treadsafe _CopyOnWriteArrayList_ instead of printing directly to stdout to prove that the writes/updates are not ordered, not the calls to _println()_. The code also introduces the use of _TestHarness_ that allows any number of threads to operate on the class under test. Internally it uses a _CountdownLatch_ to assure that the threads al start the exact same time instead of creation time (the threads themselves are created sequentially). This technique is described in JCiP ch. 5.1.1
- [`SafeSequence`](src/chapter1/SafeSequence.java) Uses *synchronized* to guarantee ordered reads/updates.
- [`Waiter`](src/chapter1/Waiter.java) Object.wait() is tricky. For instance the user must use _synchronized_ but in this case does not lock on the monitor. Other threads can still 'synchronize' on it as shown. The more well known catch is that there can be spurious wakeups, so the user needs some boolean value to make sure a wakeup was valid (not shown here).

### Cretans
_'All the Cretans are liars'_  -- <cite>Epimenides the Cretan</cite>
 (https://en.wikipedia.org/wiki/Epimenides_paradox)

The motivation for this code is that it's easy to shoot yourself in the foot with threads in java and how to prevent that. I would like to add here that java has evolved over the years while keeping less useful concepts (ie. object.wait(), or volatile), while more modern languages have fewer outdated threading primitives and are therefore safer to use.

- [`Cretan`](src/chapter1/cretans/Cretan.java) A simple class that has mutable state and that has no protection from being shared in a dysfunctional way.
- [`CretanAttack`](src/chapter1/cretans/CretanAttack.java) Shows how the invariant for Cretan can be violated
- [`SynchronizedCretan`](src/chapter1/cretans/SynchronizedCretan.java) Shows that all access to shared mutable state needs to be guarded by locks, not just the writes
- [`SynchronizedCretan2`](src/chapter1/cretans/SynchronizedCretan2.java) All access is now guarded by _synchronized_ so 'WTF' no longer occurs. While this style is safe, it's not always the most scalable approach.
- [`SynchronizedCretan3`](src/chapter1/cretans/SynchronizedCretan3.java) Shows that you can synchronize on anything, but this code is tricky because it only works because the compiler *interns* the String literal, so it becomes a reference to the same object.
- [`SynchronizedCretan4`](src/chapter1/cretans/SynchronizedCretan4.java) Is wrong, because *new String()* always creates a new object, so it no longer serves as a valid lock.
- [`SynchronizedCretan5`](src/chapter1/cretans/SynchronizedCretan5.java) Is tricky because _synchronized_ on a method uses _this_ as the lock object. So update() and compare() might not use the same lock object.

### Sleepers
This code shows the many ways you historically have to make the thread stop for a while. Should never be necessary ... in theory. It's still very common even in production code. Sometimes you just can't get around it.
* [`Sleeper1`](src/chapter1/sleepers/Sleeper1.java) Still the most used way, using Thread.sleep() where the argument is the number of milliseconds. It is not theoretical that a developer mistakenly thinks they are seconds.
* [`Sleeper2`](src/chapter1/sleepers/Sleeper2.java) The more modern remedy where the unit is explicit. 
* [`Sleeper3a`](src/chapter1/sleepers/Sleeper3a.java) Again using Object.wait().
* [`Sleeper3b`](src/chapter1/sleepers/Sleeper3b.java) Synchronized still doesn't synchronize on _this_
* [`Sleeper4`](src/chapter1/sleepers/Sleeper4.java) Thread.yield(). Don't use it.


## Chapter 2 Thread Safety
- [`LazyInit`](src/chapter2/LazyInit.java) Taken from Listing 2.3. Lazy initialization might seem a good way to postpone initialization until it is actually needed, but introduces new problems (race conditions). This was not uncommon in older applications. 
- [`SafeLazyInit`](src/chapter2/SafeLazyInit.java) Is safe while not being very scalable. Readonly singletons are better initialized while starting up, for instance using dependency injection. See https://www.javaworld.com/article/2074979/double-checked-locking--clever--but-broken.html for the definitive answer on double-checked locking 

## Chapter 3 Sharing Objects
- [`AttemptToShowReordering`](src/chapter3/AttemptToShowReordering.java) The java memory model facilitates modern processors that are free to reorder instructions, if (and only if) reordering would not harm sequential execution. While this is accepted theory, it is surprisingly difficult (if not impossible) to show reordering on Intel processors. The code tries to find a situation where variables _b_ or _c_ are set but _a_ is not.
- [`VolatileStatus`](src/chapter3/VolatileStatus.java) The only valid use case for _volatile_ is when you have 1 writing thread and n>0 reading threads. This is useful for state changes.
- [`Visibility`](src/chapter3/Visibility.java) The idea here is that the write to _value_ might occur **after** the write to _ready_ resulting in printing 0. This is because there is no explicit happens-before relation between lines 27 and 28 and the processor is free to reorder the instructions. Using a memory barrier would establish such a happens before. In java that would mean using volatile, an AtomicReference or synchronized. So far I have not seen it print 0. See https://stackoverflow.com/questions/52648800/how-to-demonstrate-java-instruction-reordering-problems
- [`VolatileCretan`](src/chapter3/VolatileCretan.java) The _volatile_ clause only guarantees visibility. It is not sufficient for thread safety.
- [`NonAtomicDoubleUpdates`](src/chapter3/NonAtomicDoubleUpdates.java) This is again a failed attempt to show something that you shouldn't do. Doubles and Longs are 8 bytes in size. Writes to memory are atomic for 4 bytes only. Two writes of 4 bytes should result in corrupt values if executed from multiple threads. I guess this could lead to negative values whereas the code should only write positives. #fail
- [`Publication`](src/chapter3/Publication.java) Constants are guaranteed to be published safely.

## Chapter 4 Composing Objects
- [`Ownership`](src/chapter4/Ownership.java) and [`Ownership2`](src/chapter4/Ownership2.java) These classes are added to show the concept of ownership which is made 'invisible' in java. The only difference in runtime behavior is the _car_ being garbage collected or not. In **rust** this code would not pass the borrow checker because a reference in that language must only exist in one place (no borrowing of mutable references), so the compiler knows when to deallocate the object safely.
- [`Atomicity`](src/chapter4/Atomicity.java) The CopyOnWriteArrayList is threadsafe, but non-atomic reads/writes are not. Use compound methods like addIfAbsent

## Chapter 5 Building Blocks
- [`ShowMeTheValues`](src/chapter5/ShowMeTheValues.java) fails with a ConcurrentModificationException because toString() on a list will iterate over it and in this case it is also being updated.
### juc
- [`HashMapTest`](src/chapter5/juc/HashMapTest.java) Written by Artem Novikov on [stackoverflow](https://stackoverflow.com/questions/18542037/how-to-prove-that-hashmap-in-java-is-not-thread-safe). Showing the regular _HashMap_ is not threadsafe is easy. While one thread adds keys to the map continually, another thread checks for the existence of a previously added key/value. Note that IntStream.range is exclusive for the upper bound, so targetKey will never be overwritten in the map.

## Chapter 6 Task Execution

### Futures
- [`TheFuture`](src/chapter6/futures/TheFuture.java) Shows the correct use of java.util.concurrent.Future. A call to getData() is asynchronous and returns a Future immediately. Calls to Future.get() block until a value is set in another thread.
- [`CancelledFuture`](src/chapter6/futures/CancelledFuture.java) Shows what cancellation does to the future. Calls to Future.get() fail after cancellation.
- [`ExceptionalFuture`](src/chapter6/futures/ExceptionalFuture.java) Shows what happens when the calculation raises an error. The Exception 'hides' until Future.get() is called.
- [`CompletionServiceExample`](src/chapter6/futures/CompletionServiceExample.java) The JUC CompletionService combines the use of Futures with a BlockingQueue. As tasks are completed, they are added to the queue, where they can be retrieved with take(), which blocks while no completed Future is available. 

### Threadpools
- [`CachedThreadPool`](src/chapter6/threadpools/CachedThreadPool.java) Shows the use of a cached threadpool. It has a potentially unlimited number of threads and reuses them when they are discarded by the user.
- [`FixedThreadPoolWebserver`](src/chapter6/threadpools/FixedThreadPoolWebserver.java) Shows the use of a fixed threadpool. It has a limited number of threads and reuses them when they are discarded by the user.
- [`SingleThreadExecutor`](src/chapter6/threadpools/SingleThreadExecutor.java) Serves to prove that the queue used is guaranteed FIFO so the numbers are being printed in ascending order.

## Chapter 7 Task Cancellation
- [`TrickyTaskCancellation`](src/chapter7/TrickyTaskCancellation.java) Shows the subtle difference between interupted() and isInterrupted.
- [`Shutdown`](src/chapter7/Shutdown.java) and [`ShutdownNow`](src/chapter7/ShutdownNow.java) Show the difference between both methods. There are still a lot of tasks in queue when shutdown runs, and they will still be executed. After shutdownNow() there are less dots indicating earlier stop. The currently running task is being interrupted while sleeping.
- [`Finalizing`](src/chapter7/Finalizing.java) The single Finalizer thread is busy finalizing, only until the JVM exits. If you have resources to be closed outside the process, you have no guarantee that that will actually happen. @Deprecated
- [`OnShutdown`](src/chapter7/OnShutdown.java) Shutdown hooks are still a valid way to do things before the JVM exits.

## Chapter8 Applying Thread Pools
- [`Submissions`](src/chapter8/Submissions.java) As long as you submit _Runnables_ to a single workerthread, they end up in the queue and are run in order. But adding _Callables_ and hence _Futures_ can lead to deadlocks if tasks are interdependent. The outcome of the outer task is dependent in the inner task, but the inner cannot run as long as the outer is running. So the intended value is never printed.
- [`ThreadLocals`](src/chapter8/ThreadLocals.java) I'm open to suggestions here, because I think when an uncaught exception occurs in a task, the thread will be replaced.The code in java.util.concurrent.ThreadPoolExecutor (lines 1142-1160 and 994-1020) does indeed suggest that. But the string _'Value is not set'_ is never printed, implying the worker thread is never actually replaced. Beware though that this may always happen.
- [`BlockingQueue`](src/chapter8/BlockingQueues.java) juc.BlockingQueue can be used to pass objects to other threads. The take() method blocks until an object becomes available.
- [`SynchronousQueues`](src/chapter8/SynchronousQueues.java) A juc.SynchronousQueue offers a way to hand over a single object between threads. This is an optimization in that it uses no queue. The receiving thread must always call take() before the value can be handed over. Therefore this code raises the IllegalStateException 'Queue full'
- [`ParallelStreams`](src/chapter8/ParallelStreams.java) Shows multithreaded task execution using parallel streams. A funny thing is that the main thread is also used as a worker, which makes good sense when you think of it.
- [`CustomThreadPoolExecutor`](src/chapter8/CustomThreadPoolExecutor.java) Executors can be extended using provided hooks. This code (inspired by listing 8.9) shows how to capture task execution times.

## chapter 10 Avoiding Liveness Hazards
- [`Bank`](src/chapter10/Bank.java) Proves the point made in listing 10.2 concerning deadlock due to dynamic lock-reordering. The from account and to account will very likely be the same (but reversed) in 2 different threads. Locking on the accounts must always happen in the same order to avoid deadlock. The cure for this is in the book in listing 10.3. This code will at some point stop working and the account (lock) reversal is visible in stdout. 