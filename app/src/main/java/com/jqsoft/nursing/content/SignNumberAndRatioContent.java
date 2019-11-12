package com.jqsoft.nursing.content;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.response.SignNumberAndRatioBean;
import com.jqsoft.nursing.util.Util;

/**
 * Created by Administrator on 2017/5/13.
 */

public class SignNumberAndRatioContent {
    private Context context;
    private View view;
    public SignNumberAndRatioContent(Context context) {
        this.context=context;
    }

    public void initView(SignNumberAndRatioBean bean){
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
//        View   dialog = inflater.inflate(R.layout.layout_recyclerview_with_padding,(ViewGroup) getActivity().findViewById(R.id.root));
        View rootView = inflater.inflate(R.layout.layout_sign_number_and_ratio, null);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView signNumber = (TextView) rootView.findViewById(R.id.tv_sign_number);
        TextView signRatio = (TextView) rootView.findViewById(R.id.tv_sign_ratio);
        String signNumberString = bean.getSignNumberHint()+ Constants.COLON_STRING+bean.getSignNumber()+Constants.PERSON;
        String signRatioString = bean.getSignRatioHint()+Constants.COLON_STRING+bean.getSignRatio()/*+Constants.PERCENTAGE_SIGN*/;
        signNumber.setText(Util.trimString(signNumberString));
        signRatio.setText(Util.trimString(signRatioString));

        view=rootView;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
