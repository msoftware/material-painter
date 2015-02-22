package com.novoda.materialpainter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.novoda.materialpainter.view.PaletteView;
import com.novoda.notils.caster.Views;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PainterActivity extends ActionBarActivity {

    private static final int READ_REQUEST_CODE = 42;
    private static final int ANIMATION_START_DELAY = 300;
    private static final int ANIMATION_DURATION = 400;
    private static final float TENSION = 1.f;

    private View root;
    private TextView startingText;
    private PaletteView paletteView;
    private ImageButton selectImage;
    private ImageView imageView;
    private Toolbar toolbar;
    private ShareActionProvider shareActionProvider;

    private int fabHideTranslationY;
    private int toolbarHideTranslationY;

    private boolean viewsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabHideTranslationY = 2 * getResources().getDimensionPixelOffset(R.dimen.fab_min_size);
        toolbarHideTranslationY = -2 * getResources().getDimensionPixelOffset(R.dimen.toolbar_min_size);

        initViews();
        setListeners();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri;
            if (resultData != null) {
                uri = resultData.getData();
                showImage(uri);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
            if (shareActionProvider != null){
                shareActionProvider.setShareIntent(loadBitmapFromView(root));
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        root = Views.findById(this, R.id.root_view);
        startingText = Views.findById(this, R.id.starting_text);
        paletteView = Views.findById(this, R.id.palette);
        imageView = Views.findById(this, R.id.show_image);
        selectImage = Views.findById(this, R.id.fab_select_image);
    }

    private void setListeners() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewsVisible) {
                    hideViews();
                } else {
                    showViews();
                }
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTranslateAnimationWithEndAction(selectImage, fabHideTranslationY, performImageSearchRunnable);
            }
        });
    }

    private Runnable performImageSearchRunnable =
            new Runnable() {
                @Override
                public void run() {
                    performFileSearch();
                }
            };

    public void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    private void showImage(Uri uri) {
        try {
            Bitmap image = parcelImage(uri);
            generatePalette(image);
            hideViews();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Bitmap parcelImage(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor;
        parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        imageView.setImageBitmap(image);
        parcelFileDescriptor.close();
        return image;
    }

    private void generatePalette(Bitmap image) {
        Palette.generateAsync(image, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                if (palette != null) {
                    paletteView.updateWith(palette);
                }
            }
        });
    }

    private void showViews() {
        runTranslateAnimation(selectImage, 0);
        runTranslateAnimation(toolbar, 0);
        viewsVisible = true;
    }

    private void hideViews() {
        runTranslateAnimation(selectImage, fabHideTranslationY);
        runTranslateAnimation(toolbar, toolbarHideTranslationY);
        startingText.setVisibility(View.GONE);
        viewsVisible = false;
    }

    private void runTranslateAnimation(View view, int translateY) {
        view.animate()
                .translationY(translateY)
                .setInterpolator(new OvershootInterpolator(TENSION))
                .setStartDelay(ANIMATION_START_DELAY)
                .setDuration(ANIMATION_DURATION)
                .start();

    }

    private void runTranslateAnimationWithEndAction(View view, int translateY, Runnable runnable) {
        view.animate()
                .translationY(translateY)
                .setInterpolator(new OvershootInterpolator(TENSION))
                .setStartDelay(ANIMATION_START_DELAY)
                .setDuration(ANIMATION_DURATION)
                .withEndAction(runnable)
                .start();

    }

    public Intent loadBitmapFromView(View v) {
        Bitmap viewBitmap = Bitmap.createBitmap(v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            viewBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, bmpUri);

        return Intent.createChooser(intent, "Share Cover Image");
    }

}
