package com.novoda.materialpainter.view;

import android.content.Context;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novoda.materialpainter.R;
import com.novoda.notils.caster.Views;

public class SwatchView extends LinearLayout {

    private TextView title;
    private TextView body;

    public SwatchView(Context context) {
        super(context);
    }

    public SwatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SwatchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        LayoutInflater.from(getContext()).inflate(R.layout.view_swatch, this, true);

        title = Views.findById(this, R.id.swatch_title_text);
        body = Views.findById(this, R.id.swatch_body_text);
    }

    public void updateWith(Palette.Swatch swatch) {
        setBackgroundColor(swatch.getRgb());
        title.setTextColor(swatch.getTitleTextColor());
        body.setTextColor(swatch.getBodyTextColor());
    }
}
