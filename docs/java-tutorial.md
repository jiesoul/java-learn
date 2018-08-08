# Java Tutorial

## 并发

### 进程和线程

java中主要使用线程。

#### 进程

进程有自包含的执行环境。通常有完整的，私有的基本运行期资源集。实际上，每个进程有自己的内存空间。

JVM 的许多实现都是运行在单进程上。

#### 线程

有时叫做轻量级进程。创建时比进程需要的资源少。

线程在进程内部，每个进程至少有一个。线程共享进程的资源，包括内存和打开的文件。

多进程执行是 Java 平台的基本特性。

### 线程对象

每个线程都是类 Thread 的实例，两个方法使用线程去创建并发应用程序：

* 直接控制线程创建和管理，每次都简单的实例化 Thread，当程序需要一个异步任务时。

* 从程序中抽象线程管理，传递程序任务到一个 executor。

#### 定义和启动一个线程

创建 Thread 实例的程序必须提供 run 方法。有两个方法：

* 提供 Runnable 对象， Runnable 接口只有一个 run 方法。 Runnable 对象被传递给 Thread 构造器。

```java
public class HelloRunnable implements Runnable {

    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        (new Thread(new HelloRunnable())).start();
    }
}
```

* 作为 Thread 的子类。

```java
public class HelloThread extends Thread {

    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        (new HelloThread()).start();
    }

}
```

#### 使用 Sleep 暂停执行

Thread.sleep 使当前线程暂停执行。

```java
public class SleepMessage {

    public static void main(String[] args) throws InterruptedException {
        String[] immportantInfo = {
                "Mares eat oats",
                "Does eat oats",
                "Little lambs eat ivy",
                "A kid will eat ivy too"
        };

        for (int i = 0; i < immportantInfo.length; i++) {
            Thread.sleep(4000);
            System.out.println(immportantInfo[i]);
        }
    }
}
```

#### 中断

中断是一个线程应该停止的标示。InterruptedException 异常,许多方法都会导致

#### joins

join 方法允许一个线程去等待另一个线程的完成。t.join() 会导致当前线程暂停执行直到 t 的线程终止。join 的重载方法可指定等待时间。

### 同步

线程交流主要通过共享处理域和域引用的对象。这种形式是极其有效的，但是也会造成两个错误可能：线程干扰和内存一致性错误。防止这两个错误的工具是同步。

但是，同步会引入线程竞争，当两个或更多线程处理相同的资源时，会导致 Java 运行执行一个或多个线程缓慢，甚至挂起它们的执行

#### 线程干扰

多个线程执行在同一个数据上时，可能会造成某个线程的更新丢失。

#### 内存一致性错误

不同的线程看相同的数据显示不一致的结果。

#### 同步方法

两个基本的用法： synchronized 方法 和 synchronized 声明。在方法定义中添加 synchronized 关键词可以使方法是同步的。

构造方法不能被同步

#### 内置锁和同步

同步被构建在叫做内置锁或监视器锁的周围。内置锁有两个规则：强制执行独享的对象状态和建立之前发生的关系是可见的。

每个对象都有一个和它相关联的内置锁。通过约定，一个需要独享和一致处理一个对象的域的线程在处理它们之前必须获得对象的内置锁。
然后在处理完成之后释放内置锁。一个线程拥有锁的意思是它在请求到锁和释放锁之间。一旦一个线程获得了内置锁，不会有其它线程能获得相同的锁。
当其它线程尝试获得锁时，它将被阻塞。

当线程调用同步方法时，它会自动获得方法对象的内置锁且在方法返回时释放锁。锁也会在出现异常时释放。

静态方法中，线程获得的是 Class 对象的内置锁。实例的锁是完全不同的。

同步申明时必须指定对象

```java
public void addName(String name) {
    synchronized(this) {
        lastName = name;
        nameCount++;
    }
    nameList.add(name);
}
```

同步申明的形式可以更细粒度的并发。

```java
public class MsLunch {
    private long c1 = 0;
    private long c2 = 0;
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void inc1() {
        synchronized(lock1) {
            c1++;
        }
    }

    public void inc2() {
        synchronized(lock2) {
            c2++;
        }
    }
}
```

重新调用一个线程不能获得另一个线程拥有的锁，但是线程可以获得它自己拥有的锁。

#### 原子处理

### 不可变对象

定义不可变对象的策略：

1. 不提供 setter 方法
2. 所有的域都是 final 和 private 的
3. 不允许子类重写方法。最简单的方法是定义类为 final 的。更常用的方法是构造方法是 private 和工厂方法构造实例。
4. 如果实例域包含可变对象的引用，不要允许这些对象可被改变：
   * 不提供修改可变对象的方法
   * 不共享可变对象的引用。永远不存储引用到外部，可变对象传递给构造方法，如有必要，创建副本，且存储引用到副本。

### 高级并发对象

#### Lock 对象

Lock 对象工作非常像使用同步代码的隐式锁。就像使用隐式锁，一个线程一次只能有一个 Lock。Lock 对象也支持 wait/notify 机制，通过和它关联的 Condition 对象。

Lock 对象比隐式锁最大的优势是它们能够回退到尝试获得锁的外面。tryLock 方法回退如果锁直接不可用或超时。lockInteruptiby 方法加退如果在锁被获得之前另一个线程发送了中断。

#### Executors

java.util.concurrent 包有三个 executor 接口：

* Executor， 简单的接口支持运行新任务
* ExecutorService， Executor 的子接口，添加了帮助管理生命周期的特征，包括单独任务和 executor 自己
* ScheduledExecutorService， ExecutorService 的子接口，支持周期性的任务。

Executor 接口有一个单一方法 execute，如果 r 是 Runnable 对象，e 是 Executor 对象，你可以替换

```java
(new Thread(r)).start();
```

为

```java
e.execute(r);
```

