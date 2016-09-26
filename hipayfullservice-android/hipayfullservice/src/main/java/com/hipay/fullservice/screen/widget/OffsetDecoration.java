package com.hipay.fullservice.screen.widget;

/**
 * Created by nfillion on 29/02/16.
 */

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class OffsetDecoration extends RecyclerView.ItemDecoration {

    private final int mOffset;

    public OffsetDecoration(int offset) {
        mOffset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = mOffset;
        outRect.right = mOffset;
        outRect.bottom = mOffset;
        outRect.top = mOffset;
    }
}
