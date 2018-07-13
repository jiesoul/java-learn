# Java 虚拟机规范

## 介绍

## JVM的结构

### class 文件格式



### 数据类型

原始类型和引用类型

### 原始类型和值

三类：

* numeric
  * integral
     * byte 8位有正负二进制补码整数，默认0，包含-128到127(-2<sup>7</sup> 到 2<sup>7</sup>-1)
     * short 16位有正负二进制补码整数，默认0，包含-32768到32767(-2<sup>15</sup> 到 2<sup>15<sup>-1)
     * int 32位有正负二进制补码整数，默认0，包含-2147483648 to 2147483647 (-2<sup>31</sup> 到 2<sup>31</sup>-1)
     * long 64位正负二进制补码整数，默认0,包含-9223372036854775808 to 9223372036854775807 (-2<sup>63</sup> to 2<sup>63</sup>-1)
     * char 16位无正负整数表示Unicode 代码点，基于UTF-16,默认值是空代码点('\u0000') 从0到65535 包含
  * floating-point
     * float 值是浮动值集的元素或者浮点扩展指数集,默认值是正数0
     * double 值是双值集的元素或者双值扩展指数集，默认值是正数0
* boolean true 或 false 默认 false Java语言中的boolean在JVM中被编译成 int数据类型 JVM直接支持boolean数组，建立通过指令(§newarray),存储和修改(§baload,§bastore),JVM用1表示true，用0表示false。
* returnAddress JVM指令操作码的指针,不会和Java 语言类型相关联

### 引用类型和值

三种：class，array，interface

可能被指定为null引用，不指向对象,通过null表示。null引用初始化没有运行时类型，但是可能被分配任意类型。null引用类型的的默认值是空。

### 运行时数据区域

多种运行期区域，一些在JVM启动时建立，jvm退出时销毁.其它数据通过线程. 通过线程的数据区域在线程建立时建立且在线程退出是销毁。

* pc 寄存器(程序计数器) JVM一次能执行许多线程执行。每个JVM线程都有自己的程序计数器。在某一点上，每个JVM线程执行单一方法的代码,叫做线程的当前方法。如果方法不是native的，程序计数器包含当前JVM执行指令的地址，如果方法是native的，程序计数器是未定义。

* JVM栈 每个JVM线程都有一个JVM栈，和线程同时创建.JVM栈存储栈帧。JVM栈和C之类的语言栈相似：它持有局部变量和部分结果，运行方法调用和返回的一部分,因为JVM栈不会直接操作除了push和pop帧，帧可能是堆分配。JVM栈的内存不需要是连续的。允许固定大小或者根据需要动态扩大和收缩。当是固定大小时，栈可能是独立的。

  * 如果一个线程的计算需要比允计的更大时，异常StackOverFlowError
  * 如果栈能动态扩展，并且内存不能满足扩展或者内存不够建立一个新线程时，异常OutOfMemoryError
  
* 堆 JVM有一个堆是被所有的线程共享的。堆是运行时数据区域，包括所有已分配的类和数组实例。堆在JVM启动时创建，对象的堆存储的回收是通过一个自动内存管理系统(垃圾收集器)；对象永远不会明确的解除分配。堆内存不需要是连续的。
  * 如果计算需要的内存比自动内存管理系统可用的多，异常OutOfMemoryError。
  
* 方法区 所有线程共享，它存储每个类的结构像运行时常量池，域和方法数据，方法和构造器的代码,包括被用在类和实例初始化和接口初始化的特定方法。随虚拟机启动创建，虽然方法区逻辑上是堆的一部分，但是简单的执行可能不会去垃圾回收或者压缩它。固定大小上或者可扩展，可以被压缩，不必连续。
  * 如果可用内存不能满足分配需要，异常OutOfMemoryError
  
* 运行时常量池 class文件中每个类或每个接口运行时constant_pool表的表示。每个运行时常量池从JVM方法区分配，当类和接口随着JVM创建时常量池已经创建。
  * 当创建类和接口时，如果所需内存比JVM方法区的可用内存大，异常OutOfMemoryError
  
* 本地方法栈 

### 栈帧(Frames)

存储数据和部分结果，动态链接，方法返回值，异常跳转。

方法调用时创建，完成时销毁,无论正常还是异常。分配在线程建立的帧的JVM栈上。有自己的局部变量数组，自己的操作栈，当前方法的类的运行时常量池引用。在一个给定的线程中，任意一点内对于执行方法只有一个帧是活动的，这个帧是当前帧，这个方法是当前方法，这个类是当前类。

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

一个类有0个或多个实例实例初始化方法，每个通常都是在java 语言中写的构造方法。

判断实例初始化方法：

* 它定义在类中，不是接口中。
* 特殊的名字 <init>
* 它是 void 的

类中非void的方法就算名字是 <init> 也不是实例初始化方法。接口中任何名称是 <init> 的方法都不是实例初始化方法。这样的方法不能通过 JVM 指令调用且被格式化检查拒绝。

实例初始化方法的定义和使用是通过 JVM 限制的。对于定义，方法的 access_flags 项和 code 数组被限制。对于使用实例初始化方法仅仅能被未初始化的类实例的 invokespecial 指令调用。

> <init> 不是有效的 Java 语言标识符， 

在 JVM 级别上，每个 Java 语言写的构造器作为实例初始化方法出现有一个特别的名字 <init>。这个名字被编译器支持。因为 <init> 不是一个有效的标识符，它不能直接在 Java 语言中使用。实例初始化方法可能被调用仅仅在 JVM 内部通过 invokespecial 指令，并且它们可能被调用仅仅在一个未初始化的类实例上。一个实例化方法得到构造器的许可从它的派生。

类和接口的至少有一个类或接口初始化方法，且在方法调用时被初始化。类和接口的初始化有特定的名字 <clinit>,没有参数，是空的。class 文件中的版本号是 51.0 或者更大，方法必须加上它的 ACC_STATIC 标记类和接口初始化方法的顺序。<clinit> 被编译器支持，因为它不是有效的标识符，它不能直接在 Java 语言中使用。类和接口的初始化方法通过 JVM 隐式调用；它们从来不会直接由 JVM 的指令调用，调用仅仅作为类初始化过程中的一部分。

### 异常

Throwable或者它的子类的实例。

三种原因：
* athrow 指令执行
* 意外执行条件被 JVM 同时检测到。
* 异步的异常出现
  * Thread 或 ThreadGroup 类的 stop 方法被调用
  * JVM 实现的内部错误出现

### 指令集总结

JVM 指令由一个字节的指定操作的操作码，后面跟着零个或多个操作数作为参数或者操作要使用的数据构成。许多指令没有操作数且只有一个操作码。

操作数的数量和大小是通过操作码确定的。如果操作数大于一个字节，它是大端法存储，高级顺序优先。

字节码指令流只有单字节排列，除了 lookupswitch 和 tableswitch 指令，它们操作数的一部分可能是4字节排列的。

* 类型和 JVM 大部分指令在它们的操作里编码了类型信息。例如 iload 指令加载一个局部常量，必须是 int 类型。fload 相似的必须是 float 类型。这两个可能实现相同，但是是截然不同的指令。指令类型通过操作码的一个字母表示，i 代表 int，l 代表 long，s 代表 short， b 代表 byte， c 代表 char， f 代表 float， d 代表 double，a 代表 reference.

### 类库

类可能需要特别的支持：
* 反射 包 java.lang.reflect 和 类 Class.
* 类和接口的加载和创建。 ClassLoader 
* 类和接口的链接和初始化
* 安全 包 java.security 和 SecurityManager
* 多线程 Thread 类
* 弱引用 包 java.lang.ref

### 公开设计,私有实现

## JVM的编译

javac 命令编译 java 文件到 class 文件，javap 可以输出 class 文件的虚拟机汇编语言。

### 格式的例子

虚拟机汇编语言的格式

<index> <opcode> [ <operand1> [<operand2> ]] [<comment>]

<index> 是这个方法的JVM代码的字节数组的指令操作码索引。<opcode> 是指令的操作码的助记，0个或多个<operandN> 指令的操作对象，可选的<comment>注释放在行末。

```
8 bipush 100 //Push int constant 100
```

### 常量，局部变量和控制结构的使用

JVM是栈导向的，大多数操作是从JVM当前栈帧得到1个或多个操作数或者把结果放入操作栈。新栈帧在方法调用时创建，它创建一个新的操作栈和局部变量集。看起来有很多操作栈一起或者嵌套，但是活动的只有当前的。

0 iconst_0 //push int constant 0
1 istore_1 //store into local variable 1 （i=0）


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

