# into-jvm

```java
@OnMethod(clazz="jvm.BTraceTest", method="add", location=@Location(Kind.RETURN))
    public static void func(@Self jvm.BTraceTest instance, int a, int b, @Return int result) {
        println("调用堆栈:");
        jstack();
        println(strcat("方法参数A:", str (a)));
        println(strcat("方法参数B:", str (b)));
        println(strcat("方法结果result:", str (result)));
    }
```