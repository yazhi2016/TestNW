package com.xmzlb.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

public class Content {
	public static final boolean isWrite = true;
	public static final boolean writeLog = true;

	public static final String SAFE_CENTER_REQUEST_CODE = "SafeCenter_Pwd";

	public static class URL {
		private static final String HOST = "http://192.168.0.101/APPAPI/";
		public static final String PHOTOBANNER = HOST + "?url=/home/ad";
		public static final String ALLPRODUCT = HOST + "?url=/main";
		public static final String SIGNIN = HOST + "?url=/user/signin";
	}

	public static class REQUESTCODE {
		public static final int CHANGENAME = 1;
	}

	public static class RESULTCODE {
		public static final int CHANGENAME = 1;
	}

	public static class EXTRA_NAME {
		public static final String CHANGENAME = "name";
	}

	public static void writeToLocal(String arg0, Context activity) {
		if (isWrite) {
			try {
				FileOutputStream openFileOutput = activity.openFileOutput("123.txt", 0); // 0代表MODE_PRIVATE
				openFileOutput.write(arg0.getBytes());
				openFileOutput.close();
				Log.e("==", "writeSuccess" + arg0);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
