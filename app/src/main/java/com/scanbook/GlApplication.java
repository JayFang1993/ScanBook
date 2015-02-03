package com.scanbook;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Jim on 2015/2/3.
 */
public class GlApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoad(getApplicationContext());

    }

    public static void initImageLoad(Context context){
//        File cacheDir = new File(Util.getCachePath());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder((context))
                .threadPriority(Thread.NORM_PRIORITY - 2)  //线程池内加载的数量
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        ImageLoader.getInstance().init(config);
    }
}
