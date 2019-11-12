package com.jqsoft.nursing.di.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.PersonSignAgreement;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.di.contract.SignedAgreementContract;
import com.jqsoft.nursing.di.module.SignAgreementActivityModule;
import com.jqsoft.nursing.di.presenter.SignedAgreementPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.util.Util;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import okhttp3.RequestBody;

/**
 * Created by Jerry on 2017/8/30.
 */

public class SignedAgreement extends AbstractActivity implements SignedAgreementContract.View {
    @BindView(R.id.online_consultation_title)
    TextView online_consultation_title;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.username)
    TextView personName;
    @BindView(R.id.useraddress)
    TextView areaTownName;
    @BindView(R.id.useriphone)
    TextView personPhone;
    @BindView(R.id.doctorname)
    TextView doctorName;
    @BindView(R.id.yifangdaibiao)
    TextView docOrganizationName;
    @BindView(R.id.doctoriphone)
    TextView doctorPhone;
    @BindView(R.id.orgdepartment)
    TextView signDeptName;
    @BindView(R.id.leadername)
    TextView signTeamHeaderName;
    @BindView(R.id.leaderiphnoe)
    TextView signTeamHeaderPhone;
    @BindView(R.id.server_content)
    TextView server_content;
    @BindView(R.id.serverdata)
    TextView serverdata;

    @Inject
    SignedAgreementPresenter signedAgreementPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.signedagreementactivity;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        online_consultation_title.setText("我的签约协议");
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void loadData() {
        Map<String, String> map = getRemindListRequestMap();
        RequestBody body = Util.getBodyFromMap(map);
        signedAgreementPresenter.getPersonSignAgreement(body);
    }

    private Map<String, String> getRemindListRequestMap() {
//        String cardNo = Identity.getCardNo();
        String cardNo = IdentityManager.getCardNo(this);
        String personID = IdentityManager.getPersonID(this);
        Map<String, String> map = ParametersFactory.getRemindDataMap(this, cardNo, personID);//"341222195011132094"
        return map;
    }

    @Override
    protected void initInject() {
        DaggerApplication.get(this)
                .getAppComponent()
                .addSignAgreenmentActivity(new SignAgreementActivityModule(this))
                .inject(this);
    }

    @Override
    public void onLoginSuccess(HttpResultBaseBean<PersonSignAgreement> bean) {
        Util.hideGifProgressDialog(this);
        if (bean != null) {
            personName.setText(bean.getData().getPersonName());
            personPhone.setText(bean.getData().getPersonPhone());
            areaTownName.setText(bean.getData().getAreaTownName()+bean.getData().getAreaVillageName()+bean.getData().getAreaGroup());
            docOrganizationName.setText(bean.getData().getDocOrganizationName());
            doctorName.setText(bean.getData().getDoctorName());
            doctorPhone.setText(bean.getData().getDoctorPhone());
            signDeptName.setText(bean.getData().getSignDeptName());
            signTeamHeaderName.setText(bean.getData().getSignTeamHeaderName());
            signTeamHeaderPhone.setText(bean.getData().getSignTeamHeaderPhone());
            // 甲方根据本人实际情况，决定选择服务包中的  　　 包 　　　  　型，并一次性缴纳服务费用  　   //元。具体服务项目另附。
            server_content.setText("甲方根据本人实际情况，决定选择服务包中的  " + bean.getData().getServerPackageName() + "    并一次性缴纳服务费用  " + bean.getData().getShouldSelfFee() + "元。具体服务项目另附。");
        }
//        4、本协议一式三份，家庭医生（团队）、签约对象和乡镇卫生院各持一份。有效期为年  月  日-    年  月  日，期满后自动解约。本协议未尽事宜，由双方协商解决。
        String timaStart = bean.getData().getStartExec().substring(0, 10);
        String timeEnd = bean.getData().getEndExec().substring(0, 10);
        serverdata.setText("本协议一式三份，家庭医生（团队）、签约对象和乡镇卫生院各持一份。有效期为  " + timaStart + "--" + timeEnd + " ，期满后自动解约。本协议未尽事宜，由双方协商解决。");
    }

    @Override
    public void onLoginFailure(String message) {

    }
}
