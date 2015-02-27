package com.scanbook;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.scanbook.common.FileUtils;
import com.tencent.mm.sdk.platformtools.Util;

import java.io.File;
import mp.dt.c;
/**
 * Created by Jim on 2015/2/3.
 */
public class GlApplication extends Application {
    private static SharedPreferences mSpSetting;
    @Override
    public void onCreate() {
        super.onCreate();
        initSharedPreferences(getApplicationContext());
        initImageLoad(getApplicationContext());
        c.i(getApplicationContext());
    }

    public static void initImageLoad(Context context){
        File cacheDir = new File(FileUtils.getCachePath());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder((context))
                .threadPriority(Thread.NORM_PRIORITY - 2)  //线程池内加载的数量
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        ImageLoader.getInstance().init(config);
    }

    //初始化SharePreference
    public static void initSharedPreferences(Context context) {
        mSpSetting = PreferenceManager.getDefaultSharedPreferences(context);
    }

    //初次打开
    public static void setisFirst(boolean is){
        SharedPreferences.Editor editor = mSpSetting.edit();
        editor.putBoolean("ISFIRST",is).commit();
    }

    //判断是不是第一次打开
    public static boolean isFirst(){
        return mSpSetting.getBoolean("ISFIRST", true);
    }
}
