package com.scanbook.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.loopj.android.http.BinaryHttpResponseHandler;
import com.scanbook.common.FileUtils;

import org.apache.http.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by FangJie on 15/2/27.
 */
public abstract class FileDownloadHandler extends BinaryHttpResponseHandler{

    public FileDownloadHandler(String[] allow){
        super(allow);
    }
    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        String tempPath = FileUtils.getCachePath() + "/temp.jpg";
        File file = new File(tempPath);
        if (file.exists())
            file.delete();
        DownFail();
    }

    @Override
    public void onSuccess(int i, Header[] headers, byte[] bytes) {
        String tempPath = FileUtils.getCachePath() + "/temp.jpg";
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        File file = new File(tempPath);
        // 压缩格式
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        // 压缩比例
        int quality = 100;
        try {
            if (file.exists())
                file.delete();
            file.createNewFile();
            OutputStream stream = new FileOutputStream(file);
            bmp.compress(format, quality, stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DownSuccess();
    }

    public abstract void DownSuccess();
    public abstract void DownFail();
}
