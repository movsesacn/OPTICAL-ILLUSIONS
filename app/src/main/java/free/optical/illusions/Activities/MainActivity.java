package free.optical.illusions.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import free.optical.illusions.Fragments.CategoryFragment;
import free.optical.illusions.Fragments.SelectedCategoryFragment;
import free.optical.illusions.R;
import free.optical.illusions.Constants;

public class MainActivity extends AppCompatActivity implements
        CategoryFragment.OnFragmentInteractionListener,
        SelectedCategoryFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.parseColor("#e1e1e3"));

        // show start fragment.
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, CategoryFragment.newInstance())
                .commit();
    }


    @Override
    public void onCategorySelected(int category) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, SelectedCategoryFragment.newInstance(category))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onImageSelected(int imageId) {
        final Intent intent = new Intent(this, FullScreenActivity.class);
        intent.putExtra(Constants.ID, imageId);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_left, R.anim.stay);
    }
}
