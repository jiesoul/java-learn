# Java 虚拟机规范

## JVM的结构

### class 文件格式

class 文件准确的定义了类或接口的表示，包括明细。

### 数据类型

两种类型：原始类型和引用类型

JVM 预期差不多所有的类型检查在运行期之前，典型的像编译器。JVM 指令包含了操作值的类型。

JVM 包含了对象的支持。一个对象不是类就是数组。对象的引入在 JVM 中是用 reference 表示。对象通过引用被操作的。

### 原始类型和值

三类：数字类型(numeric)，逻辑类型(boolean)，返回地址类型(returnAddress)

* numeric 整数(integral) 和 浮点数(floating-point)类型
  * integral
     * byte 8位有正负二进制补码整数，默认0，包含-128到127($-2^7$-2 到 $2^7-1$)
     * short 16位有正负二进制补码整数，默认0，包含-32768到32767($-2^{15}$ 到 $2^{15}-1$)
     * int 32位有正负二进制补码整数，默认0，包含-2147483648 to 2147483647 ($-2^{31}$ 到 $2^{31}-1$)
     * long 64位正负二进制补码整数，默认0,包含-9223372036854775808 to 9223372036854775807 ($-2^{63}$ to $2^{32}$)
     * char 16位无正负整数表示Unicode 代码点，基于UTF-16,默认值是空代码点('\u0000') 从0到65535 包含
  * floating-point  5个特殊值：正0，负0，正无穷大，负无穷大，非数(NaN). 除了 NaN 都是有序的。从小到大依次是负无穷大 - 负有限值 - 负0 - 正0 - 正有限值 - 正无穷大。正0和负0是相等的。除正0是等于正无穷大，除负0是负无穷大。NaNs 是非有序的，如果两个操作数都是或其中一个是  NaN，这时比较和测试相等是 false。
     * float 值是浮动值集的元素或者被支持的浮点扩展值集的元素, 默认值是正0
     * double 值是双值集的元素或者被支持的双值扩展值集的元素，默认值是正0
* boolean true 或 false 默认 false  仅仅提供限制，JVM 指令集没有对应的操作 boolean 值的指令。它会被编译成 int 类型.JVM 不直接支持 boolean 数组，boolean 数组的创建使用 newarray 指令。boolean 类型的数组的访问和修改是使用 byte 数组指令 baload 和 bastore。1 表示 true， 0 表示 false。
* returnAddress 通过 JVM 的 jsr，ret，和 jsr_w 指令使用，JVM指令操作码的指针,不会和Java 语言类型相关联

### 引用类型和值

三种：class，array，interface

可能被指定为null引用，不指向对象,通过null表示。null引用初始化没有运行时类型，但是可能被分配任意类型。null引用类型的的默认值是空。

### 运行时数据区域

多种运行期区域，一些在JVM启动时建立，jvm退出时销毁.其它数据通过线程. 通过线程的数据区域在线程建立时建立且在线程退出是销毁。

* pc(程序计数器) 寄存器： JVM支持一次能执行多个线程。每个 JVM 线程都有自己的程序计数器。在某一点上，每个JVM线程执行单一方法的代码,叫做线程的当前方法。如果方法不是native的，pc 寄存器包含当前JVM执行指令的地址，如果方法是native的，程序计数器是未定义。
* JVM栈 每个JVM线程都有一个私有 JVM 栈，和线程同时创建.JVM 栈存储栈帧。JVM 栈和 C 之类的语言栈相似：它持有局部变量和部分结果，运行方法中一部分的调用和返回,因为 JVM 栈不会直接操作除了push和pop帧，帧可能是由堆分配。JVM栈的内存不需要是连续的。允许固定大小或者根据需要动态扩大和收缩。当是固定大小时，栈可能是独立的。下面是和 JVM 栈关联的极好的条件： 

  * 如果一个线程的计算需要比允计的更大时，出现异常 StackOverFlowError
  * 如果栈能动态扩展，尝试扩展但是内存不够扩展时或内存不够用于创建新线程的栈，出现异常 OutOfMemoryError
* 堆 JVM 堆是被所有的线程共享。堆是运行时数据区域，包括所有已分配的类和数组实例。堆在 JVM 启动时创建，对象的堆存储的回收是通过一个自动内存管理系统(垃圾收集器)；对象永远不会明确的回收。堆内存不需要是连续的。
  * 如果计算需要的内存比自动内存管理系统可用的多，异常OutOfMemoryError。
* 方法区 所有线程共享，相似于传统语言编译后的代码存储区或操作系统进程中的 text 片段。它存储每个类的结构像运行时常量池，域和方法数据，方法和构造器的代码,包括被用在类和实例初始化和接口初始化的特定方法。随虚拟机启动创建，虽然方法区逻辑上是堆的一部分，但是简单的执行可能不会去垃圾回收或者压缩它。固定大小上或者可扩展，可以被压缩，不必连续。
  * 如果可用内存不能满足分配需要，异常 OutOfMemoryError
* 运行时常量池 class 文件中每个类或每个接口运行时 constant_pool 表的表示。包括多种常量，编译期已知的数字字面量和必须在运行期决定的域引用。相似于传统编程语言的符号表，虽然它包括更宽范围的数据。每个运行时常量池从 JVM 方法区中分配，当类和接口随着 JVM 创建时，类或接口的运行时的常量池被创建。
  * 当创建类和接口时，如果所需内存比 JVM 方法区的可用内存大，异常 OutOfMemoryError
* 本地方法栈 JVM 可能会使用传统栈，白话叫 C 栈，用来支持 native 方法(用其它语言写的)。也被用于 JVM 指令集的解释器的实现。JVM 实现不会加载 native 方法也不需要了解传统栈
  * 如果线程计算中需要巨大的本地方法栈，且比允许的大， JVM 抛出 StackOverflowError.
  * 如果本地方法栈能动态扩展且动态扩展尝试不足够的内存可用时，或可用内存不够创建新线程的， JVM 抛出 OutOfMemoryError

### 栈帧(Frames)

存储数据和部分结果,像众所周知的执行动态链接，方法返回值，异常跳转。

方法调用时创建，完成时销毁,无论正常还是异常完成。分配正在创建栈帧的线程的JVM栈上。每个有自己的局部变量数组，自己的操作栈，当前方法的类的运行时常量池引用。

局部变量数组和操作栈的大小在编译期确定且提供给关联栈帧的方法

在一个给定的线程中，任意一点内对于执行方法只有一个帧是活动的，这个帧是当前帧，这个方法是当前方法，这个类是当前类。

* 局部变量 每个栈帧都包含一个变量数组做为它的局部变量，数组大小在编译时确定。单一的局部变量能表示boolean , byte , char , short , int , float , reference , or returnAddress。一对变量表示 double 或者 long。局部变量通过索引定位。第一个变量是索引是 0。double 和 long 的值使用两个连续的局部变量，因此定位使用小的索引。在方法调用上使用局部变量传递参数。在类方法调用上，任何参数都是从局部变量 0 开始的连续变量。在实例方法调用上，局部变量 0 始终被用来传递引用到实例方法被调用的对象(Java 语言里的 this)。在从 1 开始的连续局部变量里任意参数被接着传递。

* 操作数栈 每个栈帧都包含一个后进先出的操作数栈。一个帧的操作数栈的最大深度在编译时确定并且供给相关方法的代码。当帧被容器启动时操作栈是空的。JVM 支持指令从局部变量和域中加载变量或常量到操作栈。另一些 JVM 指令从操作栈中获取操作数，操作它们，把结果返回操作栈。操作栈也被用来传递方法参数并且接受返回结果。操作栈上的值必须是在相应的类型操作上。
  * iadd 指令 把两个 int 值加在一起，它取出上一部操作的栈顶的两个数加在一起然后把结果放入栈顶。

* 动态链接 每个帧包含一个常量池引用，为了当前方法的类型去支持方法代码的动态链接。类文件代码为了方法能引用被调用的方法和通过符号引用处理的变量。动态链接转换符号方法引用进具体的方法引用中，加载必须的类获取未定义的符号，并且转换变量到合适的位置。

* 正常方法调用完成 如果调用不会导致异常抛出，又或者从 JVM 直接或执行的返回结果有一个明确的 throw 语句。如果方法正常完成，方法会返回一个值。这个发生在方法执行返回操作时。

* 意外方法调用完成 如果方法内部的 JVM 指令导致 JVM 抛出一个异常，并且异常没有在方法内部处理。从来不会返回一个值。

### 对象的表示

不要求任何特定的对象结构

### 浮点数计算

### JVM 浮点数和IEEE754

* JVM浮点数操作不会抛出异常，受限和其它IEEE异常信号，除0，上溢，下溢，不精确。没有NaN值。
* JVM 浮点数计算不支持 IEEE 754 浮点数比较
* 舍入使用IEEE 754 最接近的舍入操作。不精确的结果被舍入到最可能接近表示的值，接近零最低有效位。这是IEEE 754 的默认模式。

#### 浮点数模式

每个方法都有浮点数模式，要么严格要么不严格。方法的浮点数模式通过方法定义中 method_info 结构中的 access_flags 项中的 ACC_STRICT 标记设置的。

#### 值集转换

* float 类型的值不是 float 值集的元素时，它映射成最接近的 float 值集元素。
* double 同理

### 特殊方法

#### 实例初始化方法

一个类有0个或多个实例实例初始化方法，对应 java 语言中写的构造方法。

判断是否实例初始化方法：

* 定义在类中，不是接口中。
* 特殊的名字 <init>
* 是 void 的

类中非 void 的方法就算名字是 <init> 也不是实例初始化方法。接口中任何名称是 <init> 的方法都不是实例初始化方法。这样的方法不能通过 JVM 指令调用且被格式化检查拒绝。

实例初始化方法的定义和使用是通过 JVM 限制的。对于定义，方法的 access_flags 项和 code 数组被限制。对于使用实例初始化方法仅仅能被未初始化的类实例的 invokespecial 指令调用。

> <init> 不是有效的 Java 语言标识符， 

#### 类初始化方法

一个类或接口至少有一个类或实例初始化方法，并且是由JVM调用方法来初始化。

判断是不是一个类或接口初始化方法：

* 方法名<clinit>
* void
* 类文件中版本号是51.0或更高，方法有自己的 ACC_STATIC 标记设置且没有参数。
  * ACC_STATIC 在 JAVA SE 7 引入，无参数在 JAVA SE 9，在版本50.0及以下，名称是 <clinit> 返回是 void 的方法 无论 ACC_STATIC 的设置或是否有参数。

class 文件中其它命名为 <clinit> 并不是类或接口初始化方法，它们永远不会由 JVM 自己调用，不能被 JVM 指令调用，不能通过格式化检查。

#### Signature Polymorphic Methods(签名多态方法)

判断是真的：

* 定义在 java.lang.invoke.MethodHandle 或 java.lang.invoke.VarHandle 类中
* 一个简单的 object[] 形参
* 有 ACC_VARARGS 和 ACC_NATIVE 标记设置

com.jiesoul.jvm 使用特殊指令 invokevirtual 处理签名多态方法，为了在 java.lang.invoke.VarHandle 高效的处理方法句柄的调用或高效处理方法引用。

方法句柄是动态强类型的并且直接执行引用到隐含的方法，构造器，域，或者相似的更低级的操作。java.lang.invoke.VarHandle 的实例是强类型的引用，引用一个或者多个变量，包含静态域，非静态域，数组元素，或一个离堆数据结构的组合。

### 异常

使用 Throwable 或者它的子类的实例表示。

三种原因：
* athrow 指令执行
* 意外执行条件被 JVM 同步发现，这些异常不会抛出在程序的任意一点上，而是仅仅同步在指令的异常后：
  * 特殊的异常作为可能的结果：
    * 当指令表现一个违反 Java 语言的语义的操作，例如索引越界
    * 当出现在程序的加载和链接部分
  * 资源限制导致的溢出，如太多内存使用
* 异步的异常出现
  * Thread 或 ThreadGroup 类的 stop 方法被调用
  * JVM 实现的内部错误出现

  stop 方法可能是一个线程调用，作用于另一个线程或特定的线程组。它们是异步的因为异常可能出现在另一个线程或线程组中的任何一点。内部错误也可能是异步的。

Java虚拟机可能允许在抛出异步异常之前发生少量但有限的执行量。这个推迟可能允许代码优化的发现并且在遵守 Java 语言语义的时候在抛出异常的点能被处理。

JVM 抛出的异常是清晰的：当控制发生转移时，在抛出异常的点之前执行的指令的所有效果必须看起来已经发生。指令不会发生在抛出异常点之后。如果优化代码执行在跟随在异常出现的点的指令，这个代码必须准备从用户可见的程序状态来隐藏异常。

JVM 中的可能会分配 0 个或多个异常处理器。异常处理程序指定实现异常处理程序处于活动状态的方法的Java虚拟机代码的偏移范围，

### 指令集总结

JVM 指令由一个字节的指定操作的操作码，后面跟着零个或多个操作数作为参数或者操作要使用的数据构成。许多指令没有操作数且只有一个操作码。

操作数的数量和大小是通过操作码确定的。如果操作数大于一个字节，它是大端法存储，高级顺序优先。

字节码指令流只有单字节排列，除了 lookupswitch 和 tableswitch 指令，它们操作数的一部分可能是4字节排列的。

* 类型和 JVM 大部分指令在它们的操作里编码了类型信息。例如 iload 指令加载一个局部常量，必须是 int 类型。fload 相似的必须是 float 类型。这两个可能实现相同，但是是截然不同的指令。指令类型通过操作码的一个字母表示，i 代表 int，l 代表 long，s 代表 short， b 代表 byte， c 代表 char， f 代表 float， d 代表 double，a 代表 reference.

#### 类型和 JVM

JVM 指令集中的许多指令编码了它们操作的类型信息。例如，iload 指令加载局部变量的内容，变量必须是 int 进入操作栈。

i 对应 int，l 对应 long，s 对应 short，b 对应 byte，f 对应 float，d 对应 double，a 对应引用(reference).

#### 加载和保存指令

load 和 store 指令转换在 JVM 栈帧的局部变量和操作栈之间的值。

* 加载局部变量到操作栈，iload 等
* 存储操作栈上的值到局部变量，istore 等
* 加载常量到操作栈， bipush 等
*  使用范围索引处理更多的局部变量

### 类库

类可能需要特别的支持：
* 反射 包 java.lang.reflect 和 类 Class.
* 类和接口的加载和创建。 ClassLoader 
* 类和接口的链接和初始化
* 安全 包 java.security 和 SecurityManager
* 多线程 Thread 类
* 弱引用 包 java.lang.ref

### 公开设计,私有实现

实现器操作的界限：

* 在加载时转换 JVM 代码或在另一个虚拟机指令集中执行
* 在加载时转换 JVM 代码或在 CPU 的本发指令集上运行(JIT 即时编译)

## JVM的编译

javac 命令编译 java 文件到 class 文件，javap 可以输出 class 文件的虚拟机汇编语言。

### 格式的例子

虚拟机汇编语言的格式

```com.jiesoul.jvm
<index> <opcode> [ <operand1> [<operand2> ]] [<comment>]

<index> 是这个方法的JVM代码的字节数组的指令操作码索引。<opcode> 是指令的操作码的助记，0个或多个<operandN> 指令的操作对象，可选的<comment>注释放在行末。

8 bipush 100 //Push int constant 100
```

### 常量，局部变量和控制结构的使用

JVM是面向栈的，大多数操作是从JVM当前栈帧得到1个或多个操作数或者把结果放入操作栈。新栈帧在方法调用时创建，它创建一个新的操作栈和局部变量集。看起来有很多操作栈一起或者嵌套，但是活动的只有当前的。

````com.jiesoul.jvm
0 iconst_0 //push int constant 0
1 istore_1 //store into local variable 1 （i=0）
````

### 算术

JVM 算术通常在操作栈上做(iinc 是例外，它直接递增局部变量的值)。

### 处理运行时常量池

### 更多控制实例

### 授受参数

### 调用方法

invokestatic 用来调用类方法，invokespecial 必须用来调用实例初始化方法，也被用来调用超类方法，和调用 private 方法，

### 和类实例一起工作

new 指令建立类实例。实例变量处理使用 getfield 和 putfield 指令。

### 数组

newarray 建立数字类型的数组，anewarray 建立一维数组，也可以用来建立多维数组的第一维。multianewarray 用来一次建立我维数组。arraylength 数组长度。

### Compiling Switches

使用 tableswitch 和 lookupswitch 指令。tableswitch 当 switch 的情况能够用表格的索引表示。tableswitch 和 lookupswitch 只有操作 int 类型数据。

### 在操作数栈上的操作

### 异常的扔出和处理

### 编译 finally

### 同步

### 注解

## class 文件格式

class 文件由8位字节流组成。16位、32位、64位由2，4，8个连续的8位字节读取。多字节数据用大端法保存。

### ClassFile 结构

```
ClassFile {
        u4 magic;
        u2 minor_version;
        u2 major_version;
        u2 constant_pool_count;
        cp_info constant_pool[constant_pool_count-1];
        u2 access_flags;
        u2 this_class;
        u2 super_class;
        u2 interfaces_count;
        u2 interfaces[interfaces_count];
        u2 fields_count;
        field_info fields[fields_count];
        u2 methods_count;
        method_info methods[methods_count];
        u2 attributes_count;
        attribute_info attributes[attributes_count];
}
```

* magic class 文件格式的魔法数字标识，值是0xCAFEBABE。
* minor_version, major_version class 文件的将要版本和主要版本，组合起来确定文件的版本，
* constant_pool_count 等于 constant_pool 表加 1 的数量，constant_pool 的索引比 0 大比 constant_pool_count 小。
* constant_pool[] 表示多个字符串常量，类和接口名字，域名字，其它 ClassFile 组织和它的子类中的常量引用。每个 constant_pool 表条目通过它的第一个 “tag” 位表示，constant_pool 的索引从 1 到 constant_pool_count-1.
* access_flags 许可表。

  Flag Name      | Value  | Interpretation
  -------------- | ------ | --------------
  ACC_PUBLIC     | 0x0001 | Declared public;  may be accessed from outside its package.
  ACC_FINAL      | 0x0010 | Declared final; no subclasses allowed.
  ACC_SUPER      | 0x0020 | Treat superclass methods specially when invoked by  the invokespecial instruction.
  ACC_INTERFACE  | 0x0200 | Is an interface, not a class.
  ACC_ABSTRACT   | 0x0400 | Declared abstract; must not be instantiated.
  ACC_SYNTHETIC  | 0x1000 | Declared synthetic; not present in the source code.
  ACC_ANNOTATION | 0x2000 | Declared as an annotation type.
  ACC_ENUM       | 0x4000 | Declared as an enum type.

  * ACC_INTERFACE 定义接口，如果不设置，就是类。 
  * 如果 ACC_INTERFACE 设置了，ACC_ABSTRACT 也必须设置，并且 ACC_FINAL、ACC_SUPER、ACC_ENUM 不能设置。
  * 如果 ACC_INTERFACE 不设置，除了 ACC_ANNOTATION 其它都可设置。尽管如此，一个 class 文件不会同时出现 ACC_FINAL 和 ACC_ABSTRACT
  * ACC_SUPER 表示 J2SE 8 以后 所有 class 文件都设置
  * ACC_SYNTHETIC 表示通过编译器生成，不会出现在源代码中。
  * ACC_ANNOTATION 注解类型必须使用，ACC_INTERFACE 同时也必须设置
  * ACC_ENUM 表示枚举类型
  * All bits of the access_flags item not assigned in Table 4.1-A are reserved for future use. They should be set to zero in generated class files and should be ignored by Java Virtual Machine implementations. 

* this_class 必须是 constant_pool 表中的有效索引，constant_pool 条目所在的索引必须是由这个 class 文件定义的表示类和接口 CONSTANT_Class_info 结构。
* super_class 必须是 0 个或者 constant_pool 表中有效索引的一个，如果 super_class 条目非 0，对应的索引表示的这个类的直接父类的 CONSTANT_Class_info 结构。它的直接父类或者任何的超类在它的 ClassFile 结构中的 access_flags 项目中不能出现 ACC_FLNAL 标记。如果 super_class 是 0，然后这个 class 文件表示类 Object。 对于接口，super_class 索引在 constant_pool 表中，所在索引的条目是表示 Object 的 CONSTANT_Class_info 结构。
* interfaces_count 这个类或接口的直接父接口数量
* interfaces[] interfaces 数组中的每个值必须是 constant_pool 表中的有效索引。索引所在条目为表示为这个类或接口的父接口的 CONSTANT_Class_info 结构。
* fields_count fields 表中 field_info 结构的数量。field_info 表示所有域，通过这个类或接口的类型定义的类变量和实例变量。
* fields[] fields 表中的每个值必须是这个类或接口中一个域的完整的描述 field_info 结构，只包括它们自己定义的域，不包括父类和父接口定义。
* methods_count methods 表中 method_info 的数量
* methods[] methods 表中的每个值必须是这个类或接口完整定义的方法 method_info 结构。ACC_NATIVE 和 ACC_ABSTRACT 不会在 method_info 结构 access_flags 项目设置中。method_info 结构表示这个类或接口的所有方法定义，包括实例方法，类方法，实例初始化方法，和任意类或接口的初始化方法。methods 表不包括从父类或超类继承的方法。
* attributes_count 这个类中 attributes 表的属性数量
* attributes[] attributes 表中每个值是 attribute_info 结构。

### 命名的内部形式

* 二进制类和接口命名 类或接口的命名在 class 文件结构中是用二进制命名是全限定名表示。命名使用 CONSTANT_Utf8_info 结构。方法、域、局部变量、形式参数存储为非全限定名。包含至少一个 Unicode 代码单元，不能包含任何 ASCII 字符 . ; [ / 。除了 <init> 和 <clinit>方法命名也不能包含 < 和 >。

### 描述符

方法或域的类型的字符串表示。

* 域描述符  Object 表示成 Ljava/lang/Object， double[][][] 表示成 [[[D。 int -> I, byte -> B , short -> S, char -> C, double -> D, float -> F, long -> J, boolean -> Z, reference(类) -> L ClassName, reference(数组) -> [.
* 方法描述符 Object m(int i, double d, Thread t) {...} -> (IDLjava/lang/Thread;)Ljava/lang/Object;

### 常量池

JVM 指令不知道类、接口、类实例、数组的运行时布局，代替的，指令查阅 constant_pool 表中的符号信息。

constant_pool 条目的格式

```
cp_info {
  u1 tag;
  u1 info[];
}
```

info 内容随 tag 改变。

Constant pool tags

Constant Type                 | Value
----------------------------- | -----
CONSTANT_Class                | 7
CONSTANT_Fieldref             | 9
CONSTANT_Methodref            | 10
CONSTANT_InterfaceMethodref   | 11
CONSTANT_String               | 8
CONSTANT_Integer              | 3
CONSTANT_Float                | 4
CONSTANT_Long                 | 5
CONSTANT_Double               | 6
CONSTANT_NameAndType          | 12
CONSTANT_Utf8                 | 1
CONSTANT_MethodHandle         | 15
CONSTANT_MethodType           | 16
CONSTANT_InvokeDynamic        | 18

CONSTANT_Class_info 表示类或接口的结构

```
CONSTANT_Class_info {
  u1 tag;  // 值为CONSTANT_Class (7)
  u2 name_index; // constant_pool 表中的有效索引，所在条目是编码在内部形式中的表示有效的二进制类或接口名字。
}
```

int[][] is [[I ，Thread[] is [Ljava/lang/Thread。

CONSTANT_Fieldref_info , CONSTANT_Methodref_info , and CONSTANT_InterfaceMethodref_info 相似。class_index 是 constant_pool 表中的有效索引，条目结构为 CONSTANT_Class_info 结构。name_and_type_index 是 constant_pool 表中的有效索引，条目结构为 CONSTANT_NameAndType_info。

```
CONSTANT_Fieldref_info {
  u1 tag; // CONSTANT_Fieldref (9)
  u2 class_index; // 类 或 接口
  u2 name_and_type_index; // 域描述符
}
CONSTANT_Methodref_info {
  u1 tag; // CONSTANT_Methodref (10)
  u2 class_index; 必须是类 不是接口
  u2 name_and_type_index; // 方法描述符 如果以 ‘<’ 开始必须是方法 <init> 返回类型是 void。
}
CONSTANT_InterfaceMethodref_info {
  u1 tag; // CONSTANT_InterfaceMethodref (11)
  u2 class_index; // 必须是接口
  u2 name_and_type_index; // 方法描述符
}
```

CONSTANT_String_info 表示 String 类型的常量对象

```
CONSTANT_String_info {
  u1 tag; // CONSTANT_String (8).
  u2 string_index; //常量池中的有效索引，结构是 CONSTANT_Utf8_info
}
```

CONSTANT_Integer_info CONSTANT_Float_info 表示 4 字节数字常量

```
CONSTANT_Integer_info {
  u1 tag; //CONSTANT_Integer (3).
  u4 bytes; // int 常量值 大端法保存
}
CONSTANT_Float_info {
  u1 tag; //CONSTANT_Integer (4).
  u4 bytes; // float 常量值，IEEE 754 格式，大端法保存
}
```

CONSTANT_Long_info and CONSTANT_Double_info 8 字节数字常量

```
CONSTANT_Long_info {
  u1 tag; // CONSTANT_Long (5)
  u4 high_bytes;  // ((long) high_bytes << 32) + low_bytes
  u4 low_bytes; // 大端法保存
}
CONSTANT_Double_info {
  u1 tag; // CONSTANT_Long (6)
  u4 high_bytes;  // ((long) high_bytes << 32) + low_bytes
  u4 low_bytes; // 大端法保存
}
```

CONSTANT_NameAndType_info 表示方法或域，没有标示它属于类或接口类型：

```
CONSTANT_NameAndType_info {
  u1 tag; // CONSTANT_NameAndType (12).
  u2 name_index;  // CONSTANT_Utf8_info 结构表示 <init> 方法或有效的方法或域的命名。
  u2 descriptor_index; // CONSTANT_Utf8_info 结构 表示有效的方法或域的描述符
}
```

CONSTANT_Utf8_info 常量字符串值
```
CONSTANT_Utf8_info {
  u1 tag; // CONSTANT_Utf8 (1)
  u2 length; // 字节数组的大小
  u1 bytes[length]; // 字符串的字节
}
```

CONSTANT_MethodHandle_info 表示方法处理

```
CONSTANT_MethodHandle_info {
  u1 tag;             // CONSTANT_MethodHandle (15).
  u1 reference_kind;  // 值必须在 1 到 9 之间，表示处理的种类，表示它的二进制行为。
  u2 reference_index; // constant_pool 中的有效索引。
}

/**
1 ( REF_getField ), 2 ( REF_getStatic ), 3 ( REF_putField ), 4 ( REF_putStatic ) -> CONSTANT_Fieldref_info 结构。
5 ( REF_invokeVirtual ) or 8 ( REF_newInvokeSpecial ) -> CONSTANT_Methodref_info 结构。
6 ( REF_invokeStatic ) or 7 ( REF_invokeSpecial ) -> 如果 class 版本号小于52.0 CONSTANT_Methodref_info 结构 -> 如果大于等于52.0 CONSTANT_Methodref_info 或者 CONSTANT_InterfaceMethodref_info 表示 类方法和接口方法
9 ( REF_invokeInterface ) -> CONSTANT_InterfaceMethodref_info 结构
*/
```

### 域

### 方法

### 属性

### 模式检查 

### JVM 代码上的限制

### class 文件的验证

### JVM的局限

## 加载，链接和初始化

JVM 动态加载、链接和初始化类和接口。加载是用特定名字找到类或接口类型的二进制表示且从二进制表示建立类和接口的过程。链接是得到一个类或接口且混合到 JVM 的运行时状态因此它可以执行的过程。类或接口的初始化是由类或接口的初始化方法 <clinit> 构成的。

### 运行时常量池

JVM 维护着一个预知类型的常量池，一个运行期数据结构。

constant_pool 表在类或接口的二进制表示中，用来根据类或接口创建的运行时常量池。所有的引用都在运行时常量池中被初始为符号。

### JVM启动

### 创建和加载

两种类加载器，JVM 提供的引导类加载器和用户定义的类加载器。

### 链接

### 初始化

### 绑定本地文件实现

### JVM退出

## JVM 指令集

### 假设：must的意思

### 扣留操作码

### 虚拟机错误

### 指令描述格式

### 指令 

### 通过操作码的操作码记忆术

