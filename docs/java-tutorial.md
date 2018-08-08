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

### 定义和启动一个线程

创建 Thread 实例的程序必须提供 run 方法。有两个方法：

* 提供 Runnable 对象， Runnable 接口只有一个 run 方法。 Runnable 对象被传递给 Thread 构造器。

···java
public class HelloRunnable implements Runnable {

    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        (new Thread(new HelloRunnable())).start();
    }
}
···
