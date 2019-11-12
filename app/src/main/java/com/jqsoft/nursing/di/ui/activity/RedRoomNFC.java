package com.jqsoft.nursing.di.ui.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.TextView;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.di.ui.activity.nursing.NursingDetailActivity;
import com.jqsoft.nursing.di.ui.activity.nursing.RoundRoomDetailActivity;
import com.jqsoft.nursing.util.Util;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/8/9.
 */
public class RedRoomNFC extends Activity {
    private NfcAdapter nfcAdapter;
    private String readResult = "";
    private PendingIntent pendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private boolean isFirst = true;
    private TextView ifo_NFC;
    private IntentFilter ndef;
    private SharedPreferences sharepre;
    private SharedPreferences.Editor editor;
    private String username, userpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_red_room);
        initView();

    }

    /**
     * 检测工作,判断设备的NFC支持情况
     *
     * @return
     */
    private Boolean ifNFCUse() {
        // TODO Auto-generated method stub
        if (nfcAdapter == null) {
            ifo_NFC.setText("设备不支持NFC！");
            finish();
            return false;
        }
        if (nfcAdapter != null && !nfcAdapter.isEnabled()) {
            ifo_NFC.setText("请在系统设置中先启用NFC功能！");
            finish();
            return false;
        }
        return true;
    }

    private void initView() {
        sharepre = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        sharepre = getSharedPreferences("userinfo", MODE_PRIVATE);
        editor = sharepre.edit();
        username = sharepre.getString("username", "");
        userpass = sharepre.getString("userpass", "");
        ifo_NFC = (TextView) findViewById(R.id.tv_nfc_notion);
        //NFC适配器，所有的关于NFC的操作从该适配器进行
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (!ifNFCUse()) {
            return;
        }
        //将被调用的Intent，用于重复被Intent触发后将要执行的跳转
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        //设定要过滤的标签动作，这里只接收ACTION_NDEF_DISCOVERED类型
        ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        ndef.addCategory("*/*");
        mFilters = new IntentFilter[]{ndef};// 过滤器
        mTechLists = new String[][]{new String[]{NfcA.class.getName()},
                new String[]{NfcF.class.getName()},
                new String[]{NfcB.class.getName()},
                new String[]{NfcV.class.getName()}};// 允许扫描的标签类型

        if (isFirst) {
            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent()
                    .getAction())) {
                System.out.println(getIntent().getAction());
                if (readFromTag(getIntent())) {
                    if (!TextUtils.isEmpty(readResult)) {
                        String[] readRoomResult = readResult.split(",");
//                        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(userpass)) {
                        if (LoginActivityNew.HAS_LOGGED_IN) {
                            if (readRoomResult[0].equals("0")) {
//                                Intent intent = new Intent(RedRoomNFC.this, NursingDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putBoolean(Constants.NURSING_IS_FROM_NFC_KEY, true);
                                bundle.putString(Constants.NURSING_BED_ID_KEY, readRoomResult[2]);
                                Util.gotoActivityWithBundle(RedRoomNFC.this, NursingDetailActivity.class, bundle);
                                finish();
                            } else if (readRoomResult[0].equals("1")) {
                               // Intent intent = new Intent(RedRoomNFC.this, RoundRoomDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("roomid", readRoomResult[1]);
                                bundle.putString("roomNo", readRoomResult[2]);
                                bundle.putString("roomType", readRoomResult[3]);
                                bundle.putBoolean(Constants.NURSING_IS_FROM_NFC_KEY, true);
                                bundle.putString("roomExtra", "");
                                Util.gotoActivityWithBundle(RedRoomNFC.this,RoundRoomDetailActivity.class, bundle);
                             //   startActivity(intent,bundle);
                                finish();
                            }
                        } else {
                            Intent intent = new Intent(RedRoomNFC.this, LoginActivityNew.class);
                            intent.putExtra(Constants.NURSING_READ_RESULT_KEY, readResult);
                            intent.putExtra(Constants.NURSING_IS_FROM_NFC_KEY, true);
                            startActivity(intent);
                            finish();


                        }

                    } else {
                        ifo_NFC.setText("标签数据为空");
                    }
                    System.out.println("1.5...");
                } else {
                    ifo_NFC.setText("标签数据为空");
                }
            }
            isFirst = false;
        }
        System.out.println("onCreate...");
    }

    /*
     * 重写onResume回调函数的意义在于处理多次读取NFC标签时的情况
	 * (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // 前台分发系统,这里的作用在于第二次检测NFC标签时该应用有最高的捕获优先权.
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters,
                mTechLists);


        //   MobclickAgent.onResume(this);
    }

    /*
     * 有必要要了解onNewIntent回调函数的调用时机,请自行上网查询
     *  (non-Javadoc)
     * @see android.app.Activity#onNewIntent(android.content.Intent)
     */
    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        System.out.println("onNewIntent1...");
        System.out.println(intent.getAction());
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            System.out.println("onNewIntent2...");
            if (readFromTag(intent)) {
                ifo_NFC.setText(readResult);
                System.out.println("onNewIntent3...");
            } else {
                ifo_NFC.setText("标签数据为空");
            }
        }

    }

    /**
     * 读取NFC标签数据的操作
     *
     * @param intent
     * @return
     */
    private boolean readFromTag(Intent intent) {
        Parcelable[] rawArray = intent
                .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawArray != null) {
            NdefMessage mNdefMsg = (NdefMessage) rawArray[0];
            NdefRecord mNdefRecord = mNdefMsg.getRecords()[0];
            try {
                if (mNdefRecord != null) {
                    readResult = new String(mNdefRecord.getPayload(), "UTF-8");
                    return true;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }


    public void onPause() {
        super.onPause();
        // MobclickAgent.onPause(this);
    }

}
