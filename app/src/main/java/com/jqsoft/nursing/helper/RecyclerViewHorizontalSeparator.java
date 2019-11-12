package com.jqsoft.nursing.helper;

import android.content.Context;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.Constants;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;


/**
 * Created by Administrator on 2018-04-03.
 */

public class RecyclerViewHorizontalSeparator extends Y_DividerItemDecoration {
    Context context;
    public RecyclerViewHorizontalSeparator(Context context) {
        super(context);
        this.context=context;
    }


    @Override
    public Y_Divider getDivider(int i) {
        Y_Divider divider = null;
        divider = new Y_DividerBuilder()
                .setBottomSideLine(true, context.getResources().getColor(R.color.recycler_view_separator_color),
                        Constants.RECYCLER_VIEW_HORIZONTAL_DIVIDER_HEIGHT, 0, 0)
                .create();
        return divider;
    }


}
