package app.quick.com.quickcache.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * Created by quickmenu on 2016-07-08.
 */
public class DirectoryUtil {

    // 외부 스토리지 접근가능 버전 확인
    public static boolean isExternalStorageRemovable() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    // 외부 스토리지 API 존재 버전 확인
    public static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    // 외부 스토리지 캐시 디렉토리 확인
    public static File getExternalCacheDirectory(Context context) {
        if(hasExternalCacheDir()) {
            return context.getExternalCacheDir();
        }
        final String cacheDirectory = "/Android/data/" + context.getPackageName() + "/cache";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDirectory);
    }

}
