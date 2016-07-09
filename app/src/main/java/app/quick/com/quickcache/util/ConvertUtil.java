package app.quick.com.quickcache.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by quickmenu on 2016-07-05.
 */
public class ConvertUtil {

    /**
     * 파일을 비트맵으로 변환합니다.
     * @param file 변경값
     * @return Bitmap 결과값
     */
    public static Bitmap file2Bitmap(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        return bitmap;
    }

}
