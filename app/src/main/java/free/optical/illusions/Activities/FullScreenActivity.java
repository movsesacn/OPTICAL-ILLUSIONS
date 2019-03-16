package free.optical.illusions.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

import java.util.Objects;

import free.optical.illusions.R;
import free.optical.illusions.Constants;

public class FullScreenActivity extends AppCompatActivity {

    private ImageView mGifView, mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        initViews();

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        // Load selected gif onto ImageView.
        Ion.with(mGifView).load(getPath(Objects.requireNonNull(getIntent().getExtras()).getInt(Constants.ID)));

        mBackButton.setOnClickListener((o) -> finish());
    }

    private void initViews() {
        mGifView = findViewById(R.id.gif);
        mBackButton = findViewById(R.id.back_button);
    }

    private String getPath(int id) {
        return "android.resource://" + getPackageName() + "/" + id;
    }
}
