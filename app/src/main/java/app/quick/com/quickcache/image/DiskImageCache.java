package app.quick.com.quickcache.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.appcompat.BuildConfig;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import app.quick.com.common.QuickConstant;
import app.quick.com.quickcache.util.DirectoryUtil;

/**
 * Created by quickmenu on 2016-07-08.
 */
public class DiskImageCache implements QuickCache {

    private DiskLruCache diskLruCache;
    private Bitmap.CompressFormat mCompressFormat;
    private int mCompressQuality;
    private static final int APP_VERSION = 1;
    private static final int VALUE_COUNT = 1;

    // 생성자
    public DiskImageCache(Context context, String directoryName, int maxSize, int format, int quality) {
        format(format);
        quality(quality);
        final File directory = getDiskCacheDirectory(context, directoryName);
        try {
            diskLruCache = DiskLruCache.open(directory, APP_VERSION, VALUE_COUNT, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 파일포멧 지정(디스크 캐시)
    private void format(int format) {
        switch (format) {
            case QuickConstant.FILE_FORMAT_JPG :
                mCompressFormat = Bitmap.CompressFormat.JPEG;
                break;
            case QuickConstant.FILE_FORMAT_PNG :
                mCompressFormat = Bitmap.CompressFormat.PNG;
                break;
            default :
                mCompressFormat = Bitmap.CompressFormat.PNG;
                break;
        }
    }

    // 파일퀄리티 지정(디스크 캐시)
    private void quality(int quality) {
        switch (quality) {
            case QuickConstant.FILE_QUALITY_HIGH :
                mCompressQuality = 100;
                break;
            case QuickConstant.FILE_QUALITY_MIDDLE :
                mCompressQuality = 70;
                break;
            case QuickConstant.FILE_QUALITY_ROW :
                mCompressQuality = 50;
                break;
            default :
                mCompressFormat = Bitmap.CompressFormat.PNG;
                break;
        }
    }

    // 파일 캐시 저장
    private boolean writeBitmapToFile(Bitmap bitmap, DiskLruCache.Editor editor) throws IOException, FileNotFoundException {
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(editor.newOutputStream(0), QuickConstant.IO_BUFFER_SIZE);
            return bitmap.compress(mCompressFormat, mCompressQuality, os);
        } finally {
            if(os != null) {
                os.close();
            }
        }
    }

    // 디스크 캐시 디렉토리 경로 확인
    private File getDiskCacheDirectory(Context context, String directory) {
        final String path = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !DirectoryUtil.isExternalStorageRemovable() ? DirectoryUtil.getExternalCacheDirectory(context).getPath() : context.getCacheDir().getPath();
        return new File(path + File.separator + directory);
    }

    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        DiskLruCache.Editor editor = null;
        try {
            editor = diskLruCache.edit(key);
            if(editor == null) return;

            if(writeBitmapToFile(bitmap, editor)) {
                diskLruCache.flush();
                editor.commit();
            } else {
                if(BuildConfig.DEBUG) {
                    Log.d(QuickConstant.TAG, "[writeBitmapToFile] error key : " + key);
                }
            }
        } catch (IOException e) {
            if(BuildConfig.DEBUG) {
                Log.d(QuickConstant.TAG, "[IOException] error key : " + key);
            }
            try {
                editor.abort();
            } catch (IOException ignored) {  }
        }
    }

    @Override
    public void putBitmap(String key, File file) {
        if(file == null || !file.exists()) return;

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        putBitmap(key, bitmap);
    }

    @Override
    public Bitmap getBitmap(String key) {
        Bitmap bitmap = null;
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = diskLruCache.get(key);
            if(snapshot == null) return null;

            final InputStream is = snapshot.getInputStream(0);

            if(is != null) {
                final BufferedInputStream bis = new BufferedInputStream(is, QuickConstant.IO_BUFFER_SIZE);
                bitmap = BitmapFactory.decodeStream(bis);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(snapshot != null) {
                snapshot.close();
            }
        }
        if(BuildConfig.DEBUG) {
            Log.d(QuickConstant.TAG, bitmap == null ? "[NullPointException] error key : " + key : "put key : " + key);
        }
        return bitmap;
    }

    @Override
    public void clear() {
        if(BuildConfig.DEBUG) {
            Log.d(QuickConstant.TAG, "cleared");
        }
        try {
            diskLruCache.delete();
        } catch (IOException e) {
            Log.d(QuickConstant.TAG, "[IOException] cleared");
        }
    }
}
