package com.example.allamoda;

import android.graphics.*;
import android.view.View;

import java.io.ByteArrayOutputStream;

public class BitmapUtils {
    public static Bitmap getCropBitmap(Bitmap bitmap, Path path){
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0XFF000000);

        canvas.drawPath(path, paint);

        // Keeps the source pixels that cover the destination pixels,
        // discards the remaining source and destination pixels.
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, 0, 0, paint);

        return output;
    }
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
