# 字节码基础

## JVM 数据类型

JVM 定义的数据类型

1. 原始类型
    * 数字类型：byte(8位二进制)，short(16位二进制)，int(32位二进制)，long(64位二进制)，
    char(16位无符号Unicode),float(32位IEEE754 有符号精度)，double(64位IEEE754 有符号精度)
    * boolean
    * returnAddress 指向指令的指针
   
2. 引入类型
    * Classes 类型
    * Array 类型
    * Interface 类型
    
boolean 类型在字节码有限制，没有直接操作 boolean 的指令，boolean 类型被编译器转换成 int，然后由相应的
int指令运行。

## 基于栈的架构

PC register： 每个一个运行在 java 的程序线程，PC register 保存的是当前指令的地址。

JVM stack： 每个线程，stack 上分配局部变量，方法参数，和保存的返回值。

Head： 被所有线程和存储的对象(类实例和数组)共享的内存。对象回收是由 GC 管理的。

Method Area: 对于每一个加载类，它存储方法代码，符号表，常量做为常量池。

JVM 栈由栈帧组成，方法调用时压入，方法完成(要么正常返回要么抛出异常)时弹出。

栈帧组成：
    1. 局部变量数组，索引从 0 到它的长度减 1。长度由编译器计算。除了 long 和 double 局部变量能持有任何类型的值，
    long 和 double 有两个局部变量。
    2. 操作数栈用来存储中间值，指令的操作数或者方法调用的参数。
    
## 二进制探索

每个java类文件中的方法都由一系列指令组成。