package app.quick.com.quickcache.image;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by quickmenu on 2016-07-05.
 * 이미지 캐시 인터페이스
 */
public interface QuickCache {

    public void putBitmap(String key, Bitmap bitmap);

    public void putBitmap(String key, File file);

    public Bitmap getBitmap(String key);

    public void clear();

}
