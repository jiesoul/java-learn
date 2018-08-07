package jcip;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Preloader {
    private final FutureTask<ProductInfo> future = new FutureTask<>(new Callable<ProductInfo>() {
        @Override
        public ProductInfo call() throws Exception {
            return loadProductInfo();
        }


    });
    private final Thread thread = new Thread(future);

    private ProductInfo loadProductInfo() {
        return null;
    }

    public void start() {
        thread.start();
    }

    public ProductInfo get() {
        try {
            return future.get();
        } catch (Exception e) {
            Throwable cause = e.getCause();

        }
        return null;
    }
}

class ProductInfo {

}
