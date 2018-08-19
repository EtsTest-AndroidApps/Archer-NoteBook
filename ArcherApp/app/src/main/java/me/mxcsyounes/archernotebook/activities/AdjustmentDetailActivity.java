package me.mxcsyounes.archernotebook.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

import me.mxcsyounes.archernotebook.R;
import me.mxcsyounes.archernotebook.adapters.AdjustmentsAdapter;
import me.mxcsyounes.archernotebook.database.entities.Adjustment;

public class AdjustmentDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjustment_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Adjustment adjustment = getIntent().getParcelableExtra(AdjustmentsAdapter.KEY_ADJUSTMENT);
        if (adjustment != null) {
            ((TextView) findViewById(R.id.detail_adjust_distance)).
                    setText(AdjustmentsAdapter.getDistance(adjustment.distance));

            String date = new SimpleDateFormat("E dd MMM", Locale.getDefault()).format(adjustment.date);
            ((TextView) findViewById(R.id.detail_adjust_date)).
                    setText(date);

            if (adjustment.description == null)
                ((TextView) findViewById(R.id.detail_adjust_description)).
                        setText(R.string.no_description);
            else
                ((TextView) findViewById(R.id.detail_adjust_description)).
                        setText(adjustment.description);

            if (adjustment.v_adj == null)
                ((TextView) findViewById(R.id.detail_adjust_vertical_adjs)).
                        setText(R.string.nan);
            else
                ((TextView) findViewById(R.id.detail_adjust_vertical_adjs)).
                        setText(adjustment.v_adj);


            if (adjustment.h_adj == null)
                ((TextView) findViewById(R.id.detail_adjust_horizontal_adjs)).
                        setText(R.string.nan);
            else
                ((TextView) findViewById(R.id.detail_adjust_horizontal_adjs)).
                        setText(adjustment.h_adj);

            if (adjustment.path == null)
                findViewById(R.id.detail_adjust_no_photo).setVisibility(View.VISIBLE);
            else {
                LinearLayout imagesLayout = findViewById(R.id.detail_linear_layout);
                String[] paths = adjustment.path.split(";");
                for (String path : paths) {
                    ImageView imageView = new ImageView(this);

                    LinearLayout.LayoutParams params =
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    params.setMargins(0, 8, 0, 8);
                    imageView.setLayoutParams(params);
                    Picasso.get().load(new File(path)).resize(imagesLayout.getLayoutParams().width,imagesLayout.getLayoutParams().width).into(imageView);
                    imagesLayout.addView(imageView);
                }
            }

        } else {
            finish();
        }

    }

}
