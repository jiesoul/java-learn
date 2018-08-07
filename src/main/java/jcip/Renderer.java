package jcip;

import com.sun.scenario.effect.ImageData;

import java.util.List;
import java.util.concurrent.*;

public class Renderer {
    private final ExecutorService executor;

    Renderer(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        List<ImageInfo> info = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executor);
        for (final ImageInfo imageInfo : info) {
            completionService.submit(() -> imageInfo.downloadImage());
        }

        renderText(source);

        try {
            for (int t = 0, n = info.size(); t < n; t++) {
                Future<ImageData> f = completionService.take();
                ImageData imageData = f.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {

        }

    }

    private void renderImage(ImageData imageData) {

    }

    private void renderText(CharSequence source) {

    }

    private List<ImageInfo> scanForImageInfo(CharSequence source) {
        return null;
    }
}

class ImageInfo {

    public ImageData downloadImage() {
        return null;
    }
}
