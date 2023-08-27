package com.project.pill_so_good.camera;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class ImageInfo {

    private static final ImageInfo IMAGE_INFO = new ImageInfo();

    private Optional<Bitmap> bitmap;

    private ImageInfo() {
        bitmap = Optional.empty();
    }

    public static ImageInfo getInstance() {
        return IMAGE_INFO;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = Optional.of(bitmap);
    }

    public Optional<Bitmap> getBitmap() {
        return bitmap;
    }

    public File convertBitmapToFile(Context context) {
        File file = new File(context.getApplicationContext().getCacheDir(), UUID.randomUUID().toString() + ".png");

        try(FileOutputStream fileOutputStream = new FileOutputStream(file);) {
            getBitmap()
                    .get()
                    .compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

            fileOutputStream.flush();
            fileOutputStream.close();

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
