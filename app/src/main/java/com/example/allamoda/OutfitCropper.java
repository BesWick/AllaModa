package com.example.allamoda;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

public class OutfitCropper  {



    public static Bitmap getShortShirt(Bitmap bitmap){
        Path path = getTShirtPath(bitmap);
        Bitmap image = BitmapUtils.getCropBitmap(bitmap, path);
        return image;
    }


    private static Path getLongShirtPath(Bitmap src) {
        return resizePath(PathParser.createPathFromPathData(OutfitOutline.LONG_SHIRT),
                src.getWidth(), src.getHeight());
    }

    public static Bitmap getShoes(Bitmap bitmap){
        Path path = getShoesPath(bitmap);
        Bitmap image = BitmapUtils.getCropBitmap(bitmap, path);
        return image;
    }

    private static Path getShoesPath(Bitmap src) {
        return resizePath(PathParser.createPathFromPathData(OutfitOutline.SHOES),
                src.getWidth(), src.getHeight());
    }

    public static Bitmap getLongShirt(Bitmap bitmap){
        Path path = getLongShirtPath(bitmap);
        Bitmap image = BitmapUtils.getCropBitmap(bitmap, path);
        return image;
    }


    private static Path getTShirtPath(Bitmap src) {
        return resizePath(PathParser.createPathFromPathData(OutfitOutline.SHORT_SHIRT),
                src.getWidth(), src.getHeight());
    }


    private static Path getPantPath(Bitmap src) {
        return resizePath(PathParser.createPathFromPathData(OutfitOutline.PANTS),
                src.getWidth(), src.getHeight());
    }
    public static Bitmap getPant(Bitmap bitmap){
        Path path = getPantPath(bitmap);
        Bitmap image = BitmapUtils.getCropBitmap(bitmap, path);
        return image;
    }
    private static Path getHatPath(Bitmap src) {
        return resizePath(PathParser.createPathFromPathData(OutfitOutline.HAT),
                src.getWidth(), src.getHeight());
    }
    public static Bitmap getHat(Bitmap bitmap){
        Path path = getHatPath(bitmap);
        Bitmap image = BitmapUtils.getCropBitmap(bitmap, path);
        return image;
    }

    public static Path resizePath(Path path, float width, float height) {
        RectF bounds = new RectF(0, 0, width, height);
        Path resizedPath = new Path(path);
        RectF src = new RectF();
        resizedPath.computeBounds(src, true);

        Matrix resizeMatrix = new Matrix();
        resizeMatrix.setRectToRect(src, bounds, Matrix.ScaleToFit.CENTER);
        resizedPath.transform(resizeMatrix);

        return resizedPath;
    }
}
