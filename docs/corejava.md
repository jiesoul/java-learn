# core java

* /** */ 可以自动生成文档
* 原始类型 boolean、number、char， number 包括 整数和浮点数， 整数有 byte short int long， 浮点数包括 float double
* long 类型可以有后缀L或l，十六进制有前缀 0X 或 0x，八进制有前缀0， 从Java SE7 开始数字在非开关和末尾可以使用下划线分割。
* 下划线只是为了便于阅读，编译器会在编译时去除。
* float 后缀 F或f， double后缀 D或d
* 浮点数中，正数除0是正无数大，负数除0是负无数大，0/0 等于 NaN
* char 用单引号，可以使用十六进制表示
* unicode 代码单元用十六进制和前缀U+表示

## 接口 Lambda表达式和内部类

### 内部类

定义在另一个类中的类，原因：

* 内部类方法能在它们被定义的作用域处理数据包括 private 的数据
* 在相同的包内对另外的类隐藏
* 匿名内部类用来当你想定义回调时不用写太多代码

#### 使用内部类处理对象状态

## 泛型

### 泛型和虚拟机

虚拟机没有泛型对象，所有的对象都是普通类。

#### 类型擦除

每当你定义一个泛型时，相应的原生类型会自动提供。类型变量被擦除并且被约束类型(或者没有约束类型使用 Object) 代替。

替换时使用第一个约束替换。

```java
public class Interval<T extends Comparable & Serializable> implements Serializable
{
private T lower;
private T upper;

//....

public Interval(T first, T second)
{
if (first.compareTo(second) <= 0) { lower = first; upper = second; }
else { lower = second; upper = first; }
}
}
```

擦除后

```java
public class Interval implements Serializable
{
private Comparable lower;
private Comparable upper;

//...
public Interval(Comparable first, Comparable second) { /.. ../ }
}
```

#### 转换泛型表达式

当你的程序调用方法时，编译器在返回类型被擦除时插入转换。

#### 转换泛型方法

泛型方法

```java
public static <T extends Comparable> T min(T[] a);
```

擦除后

```java
public static Comparable min(Comparable[] a);
```

总结：

* 虚拟机中没有泛型，只有普通的类和方法
* 所有类型参数被它们的约束替换
* 桥接方法被综合成保持多态lf
* 强制转换必须插入以保持类型安全

#### 调用遗留代码

### 约束和限制

#### 类型参数不能使用原始类型

因为类型擦除会使参数变成 Object, 只有 Double 之类的包装类可以，原始类型不可以.

#### 运行时仅仅和原生类型工作

#### 不能创建参数化类型数组

#### 变参警告

#### 不能实例化类型变量

#### 不能构造泛型数组

#### 类型变量在泛型类的静态上下文中不是可用的

#### 不能抛出或捕获泛型类的实例

#### 可以定义失败检查

#### 擦除后当心冲突

### 泛型的继承规则

通常，Pair<S> 和 Pair<T> 没有任何关系，不管S 和 T 有怎样的关系。

### 通配符类型

#### 通配符概念

类型参数可是不同的类型

### 反射和泛型

## 集合

### Java 集合框架

#### Collection 接口

这个接口有两个方法

```java
public interface Collection<E>
{
boolean add(E element);
Iterator<E> iterator();
//...
}
```

#### 迭代器

```java
public interface Iterator<E>
{
E next();
boolean hasNext();
void remove();
default void forEachRemaining(Consumer<? super E> action);
}
```



#### Hash Set

Java 中， hash tables 用链表数组表示 。每个list叫一个 bucket.

> java8 中buckets 满了时它从链表变成平衡二叉树

#### Tree Set

相似于 hash set ，是已排序的。

## 并发

同时做多个任务，每个任务叫做线程。每个进程有它的完整的变量集，线程是共享一些数据的。

运行一个分开的线程步骤：

1. 把代码放在一个实现了 Runnable 的接口的类的 run 方法中。这个接口很简单只有一个方法：

   ```java
   public interface Runnable {
   void run();
   }
   ```

   因为只有一个方法，所以可以使用 lambda 表达式 

   ```java
   Runnable r = () -> { task code };
   ```

2. 从 Runnable 构造一个 Thread 对象

   ```java
   Thread t = new Tread(r);
   ```

3. 启动线程

   ```java
   t.start();
   ```

当 run 方法返回(执行 return，执行方法体的最后一个语句，或者出现异常)时线程中断。

interrupt 方法可以请求一个线程的终止。

当 interrupt 方法被调用时，线程的中断状态 (interrupt status) 被设置。这是一个 boolean 类型的标记。

要找出线程状态是否设置，首先调用方法 Thread.currentThread 得到当前线程，然后调用 isInterrupt 方法

```java
while (!Thread.currentThread().isInterrupted() && more work to do)
{
do more work
}
```

尽管如此，如果线程被阻塞，它不会检查线程状态。这是 InterruptedException 要进来的地方。当 interrupt 方法调用在一个阻塞的(调用sleep 或 wait)线程上，阻塞调用被 InterruptedExcption 终止。

线程有六个状态 ：New, Runnable, Blocked, Waiting, Timed Waiting, Terminated。使用 getStatus 得到状态 。

* New Threads 使用 new 创建线程时，线程并没有运行，它在 New 状态，不会执行里面的代码。

* Runnable Threads 调用 start 方法，线程进入 Runnable 状态。一个 Runnable 的线程可能不会真实运行，它需要操作系统给它时间去运行。

* Blocked and Waiting Threads 当处理这两种状态，线程不工作。它不执行任何代码并且消费最小的资源。依赖上层的线程调度器重新激活它。

* Terminated Theads 两个原因：run 方法正常退出时自然死亡，出现异常时不正常死亡。

  每个线程都有一个优先级，默认继承创建它的线程的，使用 setPriority 方法增加或减少线程的优先级。

#### Monitor 监视器

三个属性：

* 是一个仅仅有一个私有域的类
* 类的每个对象都有一个关联锁
* 所有方法都被锁加锁
* 锁有一系列关联条件

java 中每个对象都有一个内置锁和内置条件。如果方法被用 synchronized 关键字申明，它就像一个监视器方法，条件变量通过调用 wait / notifAll / notify 处理。

为了线程安全，一个 Java 对象和 一个监视器有三个不同：

* 域不必是 private
* 方法不必是 synchronized
* 内置锁对客户端是可用的

#### Volatile 域

有时仅仅读写一两个实例域对同步的花费过多了。

volatile 关键字提供了同步处理一个实例域的自由锁。如果你定义一个域为 volatile ，然后编译器和虚拟机变成域能被其它络同步更新的账。

#### Final 域

对于读取， final 域是可以共享处理的.操作map 不是线程安全的，多线程修改和读取时同样需要同步。

#### Atomic

 java.util.concurrent.atomic 包下的类在不用锁时可有效的用机器级指令保护其它操作的原子性。例如 AtomicInteger 有方法 incrementAndGet 和 	decrementAndGet 来原子性的增加或减少一个 Integer 值。

如果你想要更复杂的更新，你需要 compareAndSet 方法。假如你想保持跟踪不同线程里观察到的最大的值，下面的代码不工作：

```java
public static AtomicLong largest = new AomicLong();
// some thread
largest.set(Math.max(largest.get(), observed)); // error--race condition
```

这个更新不是原子的，代替的在计算新值且在循环中使用 compareAndSet

```java
do {
    oldValue = largest.get();
    newValue = Math.max(oldValue, observed);
} while (!lagrest.compareAndSet(oldValue, newValue));
```

JAVA SE 8 中不用使用循环格式，可以使用 lambda 表达式，

```java
largest.updateAndGet(x -> Math.max(x, observed));

largest.accumlateAndGet(observed, Math::max);
```

accumulateAndGet 方法是一个二进制操作，被用来组合原子价和供给的参数。

getAndUpdate 和 getAndAccumulate 会返回旧值。

当你有一个非常大数量的线程thgj处理相同的原子的值，因为乐观更新需要太多的重审性能损失很大。Java SE 8 提供类 LongAdder 和 LongAccumul 解决这个问题。

如果有预期更多，

```java
final LongAdder adder = new LongAdder();
for (. . .)
pool.submit(() -> {
while (. . .) {
. . .
if (. . .) adder.increment();
}
});
. . .
long total = adder.sum());
```

```java
LongAccumulator adder = new LongAccumulator(Long::sum, 0);
// In some thread...
adder.accumulate(value);
```

#### 死锁

