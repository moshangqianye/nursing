package com.jqsoft.nursing.di.ui.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.bean.BedList;
import com.jqsoft.nursing.bean.RoomOBJ;

import java.io.IOException;

import butterknife.BindView;

public class WriteNFC extends Activity {
    private IntentFilter[] mWriteTagFilters;
    private NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    String[][] mTechLists;
    private Boolean ifWrite = true;
    private String RoomID, RoomNO, BuildingNO, FloorNo, RoomTypeName, IsRead, sOrganizationCode, sOrganizationName;
    private String text, SelectLogo;//SelectLogo 0 写床位  1 写房间
    private BedList bedList;
    private RoomOBJ roomInfo;
    TextView nursing_title;


   private  LinearLayout ll_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_nfc);

        ll_back=(LinearLayout)findViewById(R.id.ll_back);


        nursing_title = (TextView) findViewById(R.id.nursing_title);
        bedList = (BedList) getIntent().getSerializableExtra("bedinfo");
        roomInfo = (RoomOBJ) getIntent().getSerializableExtra("roomInfo");
        SelectLogo = getIntent().getStringExtra("SelectLogo");
        RoomID = getIntent().getStringExtra("RoomID");
        RoomNO = getIntent().getStringExtra("RoomNO");
        BuildingNO = getIntent().getStringExtra("BuildingNO");
        FloorNo = getIntent().getStringExtra("FloorNO");
        RoomTypeName = getIntent().getStringExtra("RoomTypeName");
        IsRead = getIntent().getStringExtra("IsRead");
        sOrganizationCode = getIntent().getStringExtra("sOrganizationCode");


        sOrganizationName = getIntent().getStringExtra("sOrganizationName");
//        text = SelectLogo + "," + RoomID + "," + RoomNO + "," + FloorNo + "," + RoomTypeName + "," + BuildingNO + "," + IsRead + "," + sOrganizationCode + "," + sOrganizationName;
        if (SelectLogo.equals("0")) {
            nursing_title.setText("床位写卡");
            text = SelectLogo + "," + bedList.getFKRoomID() + "," + bedList.getBedID();
        } else {
            nursing_title.setText("房间写卡");
            text = SelectLogo + "," + roomInfo.getRoomID() + "," + roomInfo.getRoomNO() + "," + roomInfo.getRoomTypeName();
        }

        init();

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            Toast.makeText(getApplicationContext(), "该手机没有NFC功能!",
                    Toast.LENGTH_SHORT).show();
        } else {
            pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                    getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
            ndef.addCategory("*/*");
            mWriteTagFilters = new IntentFilter[]{ndef};
            mTechLists = new String[][]{new String[]{NfcA.class.getName()},
                    new String[]{NfcF.class.getName()},
                    new String[]{NfcB.class.getName()},
                    new String[]{NfcV.class.getName()}};
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (nfcAdapter == null) {
            Toast.makeText(getApplicationContext(), "该手机没有NFC功能!",
                    Toast.LENGTH_SHORT).show();
        } else {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent,
                    mWriteTagFilters, mTechLists);
        }


    }

    @Override
    protected void onNewIntent(Intent intent) {

        //  System.out.println("1.5....");

        if (text == null) {
            Toast.makeText(getApplicationContext(), "数据不能为空!",
                    Toast.LENGTH_SHORT).show();
            //      System.out.println("2....");

            return;
        }
        if (ifWrite == true) {
            //   System.out.println("2.5....");
            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) ||
                    NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                Ndef ndef = Ndef.get(tag);
                try {
                    //数据的写入过程一定要有连接操作
                    ndef.connect();
                    //构建数据包，也就是你要写入标签的数据
                    NdefRecord ndefRecord = new NdefRecord(
                            NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(),
                            new byte[]{}, text.getBytes());
                    NdefRecord[] records = {ndefRecord};
                    NdefMessage ndefMessage = new NdefMessage(records);
                    ndef.writeNdefMessage(ndefMessage);
                    //   System.out.println("3....");
                    Toast.makeText(getApplicationContext(), "数据写入成功!",
                            Toast.LENGTH_LONG).show();
                    //   displayControl(false);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (FormatException e) {
                    Toast.makeText(getApplicationContext(), "数据写入失败!",
                            Toast.LENGTH_LONG).show();
                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "数据写入失败。!",
                            Toast.LENGTH_LONG).show();
                }
            }
        }


    }


    public void onPause() {
        super.onPause();

    }
}
