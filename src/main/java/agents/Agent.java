package agents;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent {

    public static void premain(String arg) {
        System.setProperty("my-property", "foo");
    }

    public static void premain(String arg, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
//                class<?> dynamicType = new ByteBuddy()
                return new byte[0];
            }
        });
    }
}


