package com.nqzero.agent;

import java.lang.instrument.Instrumentation;
import javax.servlet.http.HttpServletResponse;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.Transformer;
import net.bytebuddy.asm.Advice;
import static net.bytebuddy.matcher.ElementMatchers.hasSuperType;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class HeaderAgent {
    static class ServiceAdvice {
        @Advice.OnMethodEnter
        public static void doHandle(@Advice.Argument(1) HttpServletResponse response) {
            response.setHeader("X-Instrumented-By", "Sq"+"reen");
        }
    }

    public static void premain(String arg, Instrumentation inst) throws Exception {
        new AgentBuilder.Default()
                .type(hasSuperType(named("javax.servlet.Servlet")))
                .transform(new Transformer.ForAdvice().advice(named("service"), ServiceAdvice.class.getName()))
                .installOn(inst);
    }
}

