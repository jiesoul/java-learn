package jcip;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// NOT
public class ThreadDeadlock {
    ExecutorService exec = Executors.newSingleThreadExecutor();

    public class RenderPageTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            Future<String> header, footer;
            header = exec.submit(new LoadFileTask("header.html"));
            footer = exec.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            //deadlock
            return header.get() + page + footer.get();
        }

        private String renderBody() {
            return null;
        }
    }

    public class LoadFileTask implements Callable<String> {
        private String name;
        LoadFileTask(String name) {
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            return null;
        }
    }
}
