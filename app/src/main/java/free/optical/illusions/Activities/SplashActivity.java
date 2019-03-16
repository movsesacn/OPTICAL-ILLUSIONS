package free.optical.illusions.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import free.optical.illusions.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        final Button startButtonContainer = findViewById(R.id.start);
        final TextView gameNameTextView = findViewById(R.id.game_name_text);

        final ViewTreeObserver observer = gameNameTextView.getViewTreeObserver();

        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gameNameTextView.requestLayout();
                startButtonContainer.requestLayout();
                startButtonContainer.getLayoutParams().width = gameNameTextView.getWidth();
                gameNameTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        startButtonContainer.setOnClickListener((o) -> {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.enter_from_left, R.anim.stay);
        });
    }
}
