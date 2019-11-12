package com.jqsoft.nursing.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils.GlideUtils;

/**
 * Created by Administrator on 2018-01-18.
 */

public class ServiceNameAmountPriceHorizontalLayout extends LinearLayout {
    private ImageView ivImage;
    private TextView tvName;
    private TextView tvItemPrice;
    private TextView tvAmount;
    private TextView tvSumPrice;


    private ClickListener clickListener;


    public ServiceNameAmountPriceHorizontalLayout(Context context, ServiceNameAmountPriceBean bean) {
        super(context);
        initView(context, bean);
    }

    public ServiceNameAmountPriceHorizontalLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ServiceNameAmountPriceHorizontalLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context, ServiceNameAmountPriceBean bean) {
        View.inflate(context, R.layout.layout_service_name_amount_price_horizontal, this);

        ivImage = (ImageView) findViewById(R.id.iv_image);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvItemPrice = (TextView) findViewById(R.id.tv_item_price);
        tvAmount = (TextView) findViewById(R.id.tv_amount);
        tvSumPrice = (TextView) findViewById(R.id.tv_sum_price);

        if (bean!=null){
            GlideUtils.load(context, bean.getPictureUrl(), ivImage);
            tvName.setText(bean.getName());
            tvItemPrice.setText(Constants.RMB_STRING+bean.getItemPrice());
            tvAmount.setText(Constants.MULTIPLIER_STRING+bean.getAmount());
            tvSumPrice.setText(Constants.RMB_STRING+bean.getSumPrice());
        }

        initClickListener();
    }


    private void initClickListener(){
        Util.setViewClickListener(this, new Runnable() {
            @Override
            public void run() {
                if (clickListener!=null){
                    clickListener.layoutDidClick();
                }
            }
        });
    }

    public static class ServiceNameAmountPriceBean {
        private String pictureUrl;
        private String name;
        private String itemPrice;
        private String amount;
        private String sumPrice;

        public ServiceNameAmountPriceBean() {
        }

        public ServiceNameAmountPriceBean(String pictureUrl, String name, String itemPrice, String amount, String sumPrice) {
            this.pictureUrl = pictureUrl;
            this.name = name;
            this.itemPrice = itemPrice;
            this.amount = amount;
            this.sumPrice = sumPrice;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(String itemPrice) {
            this.itemPrice = itemPrice;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getSumPrice() {
            return sumPrice;
        }

        public void setSumPrice(String sumPrice) {
            this.sumPrice = sumPrice;
        }
    }

    public ClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void layoutDidClick();
    }

}
