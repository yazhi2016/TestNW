package com.xmzlb.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import com.xinbo.utils.VolleyListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.os.AsyncTask;
import android.util.Log;

public class MyImageUp extends AsyncTask<String, Integer, Object> {

	private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
	// 随机生成
	private Map<String, String> al;
	// private String picPath;
	/** 请求回调 */
	private Context ct;
	private String note;
	/** 是否显示进度条 */
	private boolean isShowProssDialog = true;
	/** 进度条对话框 */
	// private ProgressDialog progressDialog;
	private ArrayList<String> imagelist;
	String[] str;
	VolleyListener listener;

	// post
	public MyImageUp(Context ct, ArrayList<String> imagelist, Map<String, String> arrayList, boolean isShowProssDialog,
			VolleyListener listener, String note, String[] str) {
		this.ct = ct;
		this.imagelist = imagelist;
		this.al = arrayList;
		this.listener = listener;
		this.isShowProssDialog = isShowProssDialog;
		this.note = note;
		this.str = str;
	}

	@Override
	protected Object doInBackground(String... params) {
		Object result = null;
		URL url = null;
		try {
			url = new URL(params[0]);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10 * 1000);
			conn.setConnectTimeout(10 * 1000);
			conn.setDoInput(true); // 允许输入
			conn.setDoOutput(true); // 允许输出
			conn.setUseCaches(false); // 不允许使用缓
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", "utf-8"); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Content-Type", "multipart/form-data" + ";boundary=" + BOUNDARY);

			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			StringBuffer sb = null;
			String paramss = "";

			if (al != null && al.size() > 0) {
				for (int i = 0; i < al.size(); i++) {
					sb = null;
					sb = new StringBuffer();
					String key = str[i];
					String value = al.get(str[i]);
					sb.append("--").append(BOUNDARY).append("\r\n");
					sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append("\r\n")
							.append("\r\n");
					sb.append(value).append("\r\n");
					paramss = sb.toString();
					dos.write(paramss.getBytes());
				}
			}

			if (imagelist.size() != 0) {
				for (int i = 0; i < imagelist.size(); i++) {
					sb = null;
					params = null;
					File file = new File(imagelist.get(i));
					sb = new StringBuffer();
					sb.append("--").append(BOUNDARY).append("\r\n");
					Log.e("==", file.getName());
					sb.append("Content-Disposition:form-data; name=\"" + "headimg" + "\"; filename=\"" + file.getName()
							+ "\"" + "\r\n");
					sb.append("Content-Type:image/pjpeg" + "\r\n");
					sb.append("\r\n");
					paramss = sb.toString();
					sb = null;
					dos.write(paramss.getBytes());
					if (imagelist.get(i).equals("") == false) {

						BitmapFactory.Options newOpts = new BitmapFactory.Options();
						newOpts.inJustDecodeBounds = true;// 只读边,不读内容
						Bitmap bitmap = BitmapFactory.decodeFile(imagelist.get(i), newOpts);

						newOpts.inJustDecodeBounds = false;
						// 读取出图片实际的宽高
						int w = newOpts.outWidth;
						int h = newOpts.outHeight;
						float hh = 800f;//
						float ww = 480f;//
						int be = 1;
						if (w > h && w > ww) {
							be = (int) (w / ww);
						} else if (w < h && h > hh) {
							be = (int) (h / hh);
						}
						if (be <= 0)
							be = 1;
						newOpts.inSampleSize = be;// 设置压缩比例

						newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
						newOpts.inPurgeable = true;// 同时设置才会有效
						newOpts.inInputShareable = true;// 当系统内存不够时候图片自动被回收

						bitmap = BitmapFactory.decodeFile(imagelist.get(i), newOpts);

						// Bitmap smallBitmap = ImageCompression
						// .getSmallBitmap(imagelist.get(i));
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
//						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
						InputStream is = new ByteArrayInputStream(stream.toByteArray());
						// int lens=(int)file.length();

						byte[] bytes = new byte[1024];
						int len = 0;
						int curLen = 0;
						while ((len = is.read(bytes)) != -1) {
							curLen += len;
							dos.write(bytes, 0, len);
						}
						dos.write("\r\n".getBytes());
						is.close();
					}

				}
			}

			byte[] end_data = ("--" + BOUNDARY + "--" + "\r\n").getBytes();
			dos.write(end_data);
			dos.flush();
			// dos.write(tempOutputStream.toByteArray());

			InputStream input = conn.getInputStream();
			StringBuffer sb1 = new StringBuffer();
			int ss;
			while ((ss = input.read()) != -1) {
				sb1.append((char) ss);
			}
			result = new String(sb1.toString().getBytes("ISO-8859-1"), "utf-8");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		listener.onResponse(result + "");
	}

}
