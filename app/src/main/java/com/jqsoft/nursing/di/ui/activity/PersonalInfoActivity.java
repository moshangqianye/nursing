package com.jqsoft.nursing.di.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.Identity;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.grassroots_civil_administration.PersonalInfoBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;
import com.jqsoft.nursing.di.contract.PersonalInfoContract;
import com.jqsoft.nursing.di.module.PersonalInfoActivityModule;
import com.jqsoft.nursing.di.presenter.PersonalInfoPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di_app.DaggerApplication;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

//我的信息
public class PersonalInfoActivity extends AbstractActivity implements PersonalInfoContract.View {

    @BindView(R.id.edit_name)
    TextView tv_name;
    @BindView(R.id.edit_phone)
    TextView edit_phone;

    @BindView(R.id.EmployeeSex)
    TextView EmployeeSex;
    @BindView(R.id.sOrgName)
    TextView sOrgName;
    @BindView(R.id.tv_loginname)
    TextView tv_loginname;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Inject
    PersonalInfoPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setToolBar(toolbar, Constants.EMPTY_STRING);
        tv_loginname.setText( Identity.srcInfo.getLoginSuccessUsername());
        tv_name.setText(Identity.srcInfo.getTrueName());
        EmployeeSex.setText(Identity.srcInfo.getEmployeeSex());
        sOrgName.setText(Identity.srcInfo.getsOrgName());
    }

    @Override
    protected void loadData() {
//        String name= IdentityManager.getLoginSuccessUsername(getApplicationContext());
//        Map<String, String> map = ParametersFactory.getGCAPersonInfoMap(this, name, "personInfo.personalInformation");
//        mPresenter.main(map);



    }





    @Override
    protected void initInject() {
        super.initInject();
        DaggerApplication.get(this)
                .getAppComponent()
                .addPersonalInfoActivity(new PersonalInfoActivityModule(this))
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_my_info, menu);
        return super.onCreateOptionsMenu(menu);
    }



//
//    public void showFetchedInfo(PersonalInfoBean bean){
//        if (bean!=null){
//
//            Spanned html = Html.fromHtml("<u>"+phoneNumber+"</u>");
//            tvContactNumber.setText(html);
//            tvContactNumber.setTextColor(getResources().getColor(R.color.blue));
//            tvContactNumber.setOnClickListener(new NoDoubleClickListener() {
//                @Override
//                public void onNoDoubleClick(View v) {
//                    super.onNoDoubleClick(v);
//
//                    if (!StringUtils.isBlank(phoneNumber)) {
//                        AppUtils.actionDial(PersonalInfoActivity.this, phoneNumber);
//                    } else {
//                        Util.showToast(PersonalInfoActivity.this, Constants.HINT_PHONE_NUMBER_EMPTY);
//                    }
//                }
//            });
//            btnUpdatePhoneNumber.setOnClickListener(new NoDoubleClickListener() {
//                @Override
//                public void onNoDoubleClick(View v) {
//                    super.onNoDoubleClick(v);
////                    RxBus.getDefault().post(Constants.EVENT_TYPE_WOULD_SCROLL_TO_WORKBENCH_INDEX, Constants.WORKBENCH_TRANSACT);
//
////                    Util.showToast(MyInfoActivity.this, "更新电话号码按钮被点击");
//                    Util.showEditTextMaterialDialog(PersonalInfoActivity.this, Constants.HINT, Constants.HINT_UPDATE_PHONE, Constants.HINT_PLEASE_INPUT_PHONE_NUMBER, phoneNumber,
//                            false, 1, 20, Constants.EDIT_INPUT_TYPE_PHONE, new MaterialDialog.InputCallback() {
//                                @Override
//                                public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
////                                    updatePhoneNumber(input.toString());
//
//                                }
//                            });
//                }
//            });
//        } else {
//            Util.showToast(this, Constants.HINT_GET_MY_INFO_EMPTY);
//        }
//    }
    private String checknull(String str){
        if (str==null){
            return "暂无数据";

        }else {
            return str;
        }

        }

    @Override
    public void onLoadListSuccess(GCAHttpResultBaseBean<PersonalInfoBean> bean) {
        PersonalInfoBean bean1= bean.getData();
//        edit_cardid.setText(checknull(bean1.getCardNo()));
//        edit_address.setText(checknull(bean1.getArea()));
        edit_phone.setText(checknull(bean1.getMobiePhone()));

        tv_loginname.setText(checknull(bean1.getUserName()));




    }

    @Override
    public void onPostSuccess(GCAHttpResultBaseBean<PersonalInfoBean> bean) {
        Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadListFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
