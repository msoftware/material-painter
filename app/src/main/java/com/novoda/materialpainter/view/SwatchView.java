package com.novoda.materialpainter.view;

import android.content.Context;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.novoda.materialpainter.R;
import com.novoda.notils.caster.Views;

public class SwatchView extends LinearLayout {

    private ViewFlipper flipper;
    private TextView title;
    private TextView body;
    private TextView blurb;

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
        blurb = Views.findById(this, R.id.swatch_blurb);
        flipper = Views.findById(this, R.id.swatch_flipper);
    }

    public void updateWith(Palette.Swatch swatch, String swatchTitle) {
        setBackgroundColor(swatch.getRgb());
        body.setTextColor(swatch.getBodyTextColor());
        title.setText(swatchTitle);
        title.setTextColor(swatch.getTitleTextColor());
        blurb.setTextColor(swatch.getTitleTextColor());
        blurb.setText(swatch.toString());
        flipper.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                flipper.showNext();
            }
        });
    }
}
