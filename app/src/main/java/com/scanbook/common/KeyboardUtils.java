package com.scanbook.common;


import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {

    public static void showKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
        }
    }


	/*
	 * 关闭键盘
	 */
	public static void closeKeyBoard(Context context){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);  
		//得到InputMethodManager的实例 
		if (imm.isActive()) { 
			//如果开启 
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		} 
	}
}

