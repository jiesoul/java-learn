package com.jiesoul.jvm;

import java.lang.reflect.Method;

public class JavaClassExecuter {
    public static String execute(byte[] classByte) {
        HackSystem.clearBuffer();
        ClassModifier cm = new ClassModifier(classByte);
        byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System", "com/jiesoul/jvm/HackSystem");
        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class classz = loader.loadByte(modiBytes);
        try {
            Method method = classz.getMethod("main", new Class[]{String[].class});
            method.invoke(null, new String[]{null});
        } catch (Throwable e) {
            e.printStackTrace(HackSystem.out);
        }
        return HackSystem.getBufferString();
    }
}
