package app.quick.com.quickcache.image;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.quick.com.common.QuickConstant;

/**
 * Created by quickmenu on 2016-07-10.
 */
public class QuickCacheGenerator {

    private static QuickCacheGenerator instance = new QuickCacheGenerator();

    public static QuickCacheGenerator getInstance() {
        return instance;
    }

    private HashMap<String, QuickCache> cacheHashMap = new HashMap<String, QuickCache>();

    private void checkAlreadyExists(String cacheName) {
        QuickCache cache = cacheHashMap.get(cacheName);
        if(cache != null) {
            try {
                throw new Exception(String.format("QuickCache[%s] already exists", cacheName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public QuickCache createMemoryCache(String cacheName, int maxSize) {
        synchronized (cacheHashMap) {
            QuickCache cache = new MemoryImageCache(1000);
            try {
                cacheHashMap.put(cacheName, cache);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cache;
        }
    }

    public QuickCache createDiskCache(Context context, String cacheName, int maxSize) {
        synchronized (cacheHashMap) {
            QuickCache cache = new DiskImageCache(context, cacheName, maxSize, QuickConstant.FILE_FORMAT_JPG, QuickConstant.FILE_QUALITY_MIDDLE);
            try {
                cacheHashMap.put(cacheName, cache);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cache;
        }
    }

    public QuickCache createCainCache(Context context, String cacheName, int maxSize) {
        synchronized (cacheHashMap) {
            checkAlreadyExists(cacheName);
            List<QuickCache> chain = new ArrayList<QuickCache>();
            chain.add(new MemoryImageCache(maxSize));
            chain.add(new DiskImageCache(context, cacheName, maxSize, QuickConstant.FILE_FORMAT_JPG, QuickConstant.FILE_QUALITY_MIDDLE));
            ChainImageCache cache = new ChainImageCache(chain);
            cacheHashMap.put(cacheName, cache);
            return cache;
        }
    }

    public QuickCache getCache(String cacheName) {
        QuickCache cache = cacheHashMap.get(cacheName);
        if(cache == null) {
            try {
                throw new Exception(String.format("ImageCache[%s] not founds", cacheName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cache;
    }

}
