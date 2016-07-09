package app.quick.com.quickcache.image;

import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quickmenu on 2016-07-10.
 */
public class ChainImageCache implements QuickCache {

    private List<QuickCache> chain;

    public ChainImageCache(List<QuickCache> chain) {
        this.chain = chain;
    }

    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        for (QuickCache cache : chain) {
            cache.putBitmap(key, bitmap);
        }
    }

    @Override
    public void putBitmap(String key, File file) {
        for (QuickCache cache : chain) {
            cache.putBitmap(key, file);
        }
    }

    @Override
    public Bitmap getBitmap(String key) {
        Bitmap bitmap = null;
        List<QuickCache> caches = new ArrayList<QuickCache>();
        for (QuickCache cache : chain) {
            bitmap = cache.getBitmap(key);
            if(bitmap != null) {
                break;
            }
            caches.add(cache);
        }
        if(bitmap == null) return null;

        if(!caches.isEmpty()) {
            for (QuickCache cache : caches) {
                cache.putBitmap(key, bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public void clear() {
        for (QuickCache cache : chain) {
            cache.clear();
        }
    }
}
