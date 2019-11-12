package com.jqsoft.livebody_verify_lib.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.arcsoft.arcfacedemo.util.IDCard;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jqsoft.livebody_verify_lib.bean.CardBean;
import com.jqsoft.livebody_verify_lib.bean.MapString;
import com.jqsoft.livebody_verify_lib.bean.OCRBean;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * 服务器交互类
 *
 * @author gujunqi date：2014-1-1
 */
public class WebServiceUtils {
	// 定义webservice的命名空间
	public static String nameSpace = "http://tempuri.org/";

	// 定义webservice提供服务的url
	// public static String serviceURL =
	// "http://60.171.146.122:9098/WebService/AndrewsWebService.asmx";

	// 传入 调用 的方法 此方法不需传参
	public static String requestNoParams(String methodName, String serviceURL) {
		// 创建HttpTransportSE传输对象
		HttpTransportSE ht = new HttpTransportSE(serviceURL);
		try {
			ht.debug = true;
			// 使用SOAP1.1协议创建Envelop对象
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// 实例化SoapObject对象
			SoapObject soapObject = new SoapObject(nameSpace, methodName);
			envelope.bodyOut = soapObject;
			// 设置与.NET提供的webservice保持较好的兼容性
			envelope.dotNet = true;

			// 调用webservice
			ht.call(nameSpace + methodName, envelope);
			if (envelope.getResponse() != null) {
				// 获取服务器响应返回的SOAP消息
				SoapObject object = (SoapObject) envelope.bodyIn;
				String result = object.getProperty(0).toString();
				return result;
			}
		} catch (SoapFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}




	// 需要传参类
	// 传入 调用的方法
	public static String requestByParams(String methodName, String strJson,
										 String serviceURL) {
		// 创建httptransportSE传输对象
		HttpTransportSE ht = new HttpTransportSE(serviceURL);
		ht.debug = true;
		// 实例化SoapObject对象
		SoapObject soapObject = new SoapObject(nameSpace, methodName);
		// 添加一个请求参数
		// soapObject.addProperty("sLoginName", "admin");
		// soapObject.addProperty("sPassword", "123");
		soapObject.addProperty("strJson", strJson);
		// 使用soap1.1协议创建envelop对象
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER10);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = soapObject;
		// 设置与.NET提供的webservice保持较好的兼容性
		envelope.dotNet = true;

		// 调用webservice
		try {
			ht.call(nameSpace + methodName, envelope);
			if (envelope.getResponse() != null) {
				// 获取服务器响应返回的SOAP消息
				SoapObject object = (SoapObject) envelope.bodyIn;
				String result = object.getProperty(0).toString();
				Log.e("ArcSaveBitmapUrl",result);
				return result;
			}
		} catch (SoapFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	public static String StringSplit(String value){
		if (!TextUtils.isEmpty(value)){
			String[] names = value.split("]\\*");
			for (int i = 0; i < names.length; i++) {
				System.out.println(names[i]);
			}
			return names[0] +"]";
		}
		return " ";
	}

	public static String StringSplitgaoxueya(String value){
		if (!TextUtils.isEmpty(value)){
			String[] names = value.split("]\\*");
			for (int i = 0; i < names.length; i++) {
				System.out.println(names[i]);
			}
			return names[0]+"]" ;
		}
		return " ";
	}

	public static String StringSplit2(String value){
		if (!TextUtils.isEmpty(value)){
			String[] names = value.split("]\\*");
			for (int i = 0; i < names.length; i++) {
				System.out.println(names[i]);
			}
			return names[1];
		}
		return  " ";
	}


	public static CardBean redCardId(String imgString, Context context){
		ArrayList<OCRBean>infoList = new ArrayList<>();
		org.apache.http.HttpResponse httpResponse = null;
		CardBean cardBean = new CardBean();
		SharedPreferences userSettings = context.getSharedPreferences("setting", 0);
		String url= userSettings.getString("imgStringUrl","http://60.168.132.92:12280/ocr");
		if(TextUtils.isEmpty(url)){
			url = "http://60.168.132.92:12280/ocr";
		}
//		String   url="http://60.168.132.92:12280/ocr";
//	  String   url="http://192.168.45.49:8080/ocr";
		// 第一步，创建HttpPost对象
		HttpPost httpPost = new HttpPost(url);
		Gson gson = new Gson();
		MapString mapString = new MapString();
		mapString.setStr("data:image/jpeg;base64,"+imgString);
		// 设置HTTP POST请求参数必须用NameValuePair对象
		List<NameValuePair> params = new ArrayList<>();
		String json = new GsonBuilder().disableHtmlEscaping().create()
				.toJson(mapString);
		params.add(new BasicNameValuePair("imgString", json
				.replaceAll("\\\\n", "")));
		Log.i("jie01", json+"入参");
		try {
			// 设置httpPost请求参数 //new UrlEncodedFormEntity(params,"GBK")
			httpPost.setEntity(new StringEntity(json, HTTP.UTF_8));
			httpPost.setHeader("Content-Type", "application/json");
			DefaultHttpClient client = new DefaultHttpClient();
			client.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
			httpResponse = client.execute(httpPost);
			System.out.println(httpResponse.getStatusLine().getStatusCode());
			Log.i("jerry01", httpResponse.getStatusLine().getStatusCode()+"");
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 第三步，使用getEntity方法活得返回结果
				Log.i("jie01", httpResponse.getEntity()+"我是结果");
				String result = EntityUtils.toString(httpResponse.getEntity(),"utf-8");
				result=  result.substring(1,result.length()-1);
				result =result.replaceAll("\\\\","");
				Log.i("jie01", result+"result.length()-1");
				try {
					JSONObject jsonObject = new JSONObject(result);
					String  str = jsonObject.getString("res");
					Log.i("jie01", result+"str我是结果");
					JSONArray array = new JSONArray(str);
					infoList.clear();
					for (int i = 0; i < array.length(); i++)
						if (array.get(i) != null) {
							JSONObject object1 = array.getJSONObject(i);
							OCRBean bean = null;
							try {
								bean = gson.fromJson(object1.toString(),new TypeToken<OCRBean>(){}.getType());
							}catch (Exception e){
								Log.e("WebServiceUtil",e.getMessage());
							}
							if (bean != null){
								infoList.add(bean);
							}
						}
					String sCardNo="";
					Boolean bIsEnd = false;
					String address="",address1="",address2="",address3="";
					if(infoList.size()>0){
						try {
							for(int j=0;j<infoList.size();j++) {
								String text = infoList.get(j).getText();

								for (int i = 0; i < text.length(); i++) {
									if (Character.isDigit(text.charAt(i)))//如果循环行中的字符是数字
									{
										String sTemp_CardNo = "";
										if (i + 18 <= text.length())//如果数字开始之后，后面的字符有18位则截取
										{
											sTemp_CardNo = text.substring(i, i+18);
										} else if (i + 15 <= text.length())//如果数字开始之后，后面的字符有15位则截取
										{
											sTemp_CardNo = text.substring(i, i+15);
										}

										if (IDCard.IDCardValidate(sTemp_CardNo).equals(""))//判断身份证号码是否校验成功。如果成功，取出身份证，退出所有的循环
										{
											sCardNo = sTemp_CardNo;
											bIsEnd = true;
											break;
										}

									}

									if(j==0){
										if(text.contains("姓") || text.contains("名")){
											if(text.length()>2)
												cardBean.setsPersonName(text.substring(2,infoList.get(j).getText().length()));

										}
									}else if(j==1){
										if(text.contains("民") ||text.contains("名")){
											if(text.length()>2)
												cardBean.setsNation(text.substring(5,infoList.get(j).getText().length()));
										}
									}else if(j==3){
                                        address1="";
										address1=	text;

									}else if(j==4){
                                        address2="";
										address2=	text;
									}else if(j==5){
										if(text.length()>10){
											address3="";
										}else {
											address3="";
											address3=	text;
										}


									}





								}
								if (bIsEnd) {
									break;
								}
							}
                            address="";
							address=address1+address2+address3;
							cardBean.setsAddress(address.substring(2,address.length()));
							cardBean.setsCardNo(sCardNo);

//					  		if(text.contains("公民身份号码")){
//								text=Util.removeCh(text);
//								text = Util.filterAlphabet(text);
//								cardBean.setsCardNo(text);
//							}else
//
							for(int j=0;j<infoList.size();j++){
								String text =infoList.get(j).getText();

							}



						} catch (Exception e) {
							e.printStackTrace();
							cardBean .setsCardNo("");
						}
					}else{
						cardBean.setsCardNo("");
					}

				} catch (JSONException e) {
					e.printStackTrace();
					Log.i("jie01","jjiexi异常");

					cardBean.setsCardNo("");
				}

			}else{
				Log.i("jie01","非200");
				cardBean =null;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			cardBean =null;
			Log.i("jie01","非200+++");
		} catch (IOException e) {
			try {
				cardBean =null;
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return cardBean;
	}
	public static CardBean readCardM(String imgString){
		MapString mapString = new MapString();
		mapString.setStr("data:image/jpeg;base64,"+imgString);
		final   CardBean cardBean=null;
		RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
				, new Gson().toJson(mapString));
//		MediaType mediaType = MediaType.parse("text/json; charset=utf-8");
		Map<String, String> params = new HashMap<>();
		params.put("imgString", imgString);
		Request request = new Request.Builder()
				.url("http://60.168.132.92:12280/ocr")
				.post(requestBody)
				.build();
		OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.d(TAG, "onFailure: " + e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
//				Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
//				Headers headers = response.headers();
//				for (int i = 0; i < headers.size(); i++) {
//					Log.d(TAG, headers.name(i) + ":" + headers.value(i));
//				}

				Log.d(TAG, "onResponse:" + response.body().string());
			}
		});

		return cardBean;
	}

}
