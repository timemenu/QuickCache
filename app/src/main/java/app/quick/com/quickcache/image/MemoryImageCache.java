package app.quick.com.quickcache.image;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

import app.quick.com.common.QuickConstant;
import app.quick.com.quickcache.util.ConvertUtil;

/**
 * Created by quickmenu on 2016-07-05.
 * 이미지 메모리 캐시
 */
public class MemoryImageCache implements QuickCache {

    private LruCache<String, Bitmap> lruCache;

    // Constructor
    public MemoryImageCache(int maxSize) {
        this.lruCache = new LruCache<String,Bitmap>(maxSize);
    }

    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        if(StringUtils.isBlank(key) || bitmap == null) return;
        lruCache.put(key, bitmap);
    }

    @Override
    public void putBitmap(String key, File file) {
        if(StringUtils.isBlank(key) || file == null || file.exists()) return;
        lruCache.put(key, ConvertUtil.file2Bitmap(file));
    }

    @Override
    public Bitmap getBitmap(String key) {
        if(StringUtils.isBlank(key)) {
            Log.e(QuickConstant.TAG, "[MemoryImageCache] getBitmap : 키값이 존재하지 않습니다.");
            return null;
        } else {
            return lruCache.get(key);
        }
    }

    @Override
    public void clear() {
        lruCache.evictAll();
    }
}
