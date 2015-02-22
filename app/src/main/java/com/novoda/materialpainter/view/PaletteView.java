package com.novoda.materialpainter.view;

import android.content.Context;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.novoda.materialpainter.R;
import com.novoda.notils.caster.Views;

public class PaletteView extends LinearLayout {

    SwatchView mutedSwatchView;
    SwatchView mutedDarkSwatchView;
    SwatchView mutedLightSwatchView;
    SwatchView vibrantSwatchView;
    SwatchView vibrantDarkSwatchView;
    SwatchView vibrantLightSwatchView;

    public PaletteView(Context context) {
        super(context);
    }

    public PaletteView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaletteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PaletteView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        LayoutInflater.from(getContext()).inflate(R.layout.view_palette, this, true);

        mutedSwatchView = Views.findById(this, R.id.muted_swatch);
        mutedDarkSwatchView = Views.findById(this, R.id.muted_dark_swatch);
        mutedLightSwatchView = Views.findById(this, R.id.muted_light_swatch);
        vibrantSwatchView = Views.findById(this, R.id.vibrant_swatch);
        vibrantDarkSwatchView = Views.findById(this, R.id.vibrant_dark_swatch);
        vibrantLightSwatchView = Views.findById(this, R.id.vibrant_light_swatch);
    }

    public void updateWith(Palette palette){
        if (palette.getMutedSwatch() != null){
            mutedSwatchView.updateWith(palette.getMutedSwatch(), getResources().getString(R.string.swatch_muted));
        }

        if (palette.getDarkMutedSwatch() != null) {
            mutedDarkSwatchView.updateWith(palette.getDarkMutedSwatch(), getResources().getString(R.string.swatch_muted_dark));
        }

        if (palette.getLightMutedSwatch() != null) {
            mutedLightSwatchView.updateWith(palette.getLightMutedSwatch(), getResources().getString(R.string.swatch_muted_light));
        }

        if (palette.getVibrantSwatch() != null) {
            vibrantSwatchView.updateWith(palette.getVibrantSwatch(), getResources().getString(R.string.swatch_vibrant));
        }

        if (palette.getDarkVibrantSwatch() != null) {
            vibrantDarkSwatchView.updateWith(palette.getDarkVibrantSwatch(), getResources().getString(R.string.swatch_vibrant_dark));
        }

        if (palette.getLightVibrantSwatch() != null) {
            vibrantLightSwatchView.updateWith(palette.getLightVibrantSwatch(), getResources().getString(R.string.swatch_vibrant_light));
        }

    }
}

