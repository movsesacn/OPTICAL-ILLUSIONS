package free.optical.illusions.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.flexbox.FlexboxLayout;

import free.optical.illusions.CustomAnimation.SlideAnimation;
import free.optical.illusions.R;
import free.optical.illusions.Constants;

public class CategoryFragment extends Fragment implements View.OnClickListener {

    private ImageView mBackgroundView;
    private FlexboxLayout mFlexLayout;
    private RelativeLayout mPsychicalCategory;
    private RelativeLayout mIllusionCategory;
    private RelativeLayout mHypnosislCategory;
    private ImageView mIllusionRotate;
    private ImageView mPsychicalRotate;
    private ImageView mHypnosislRotate;

    private OnFragmentInteractionListener mListener;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_category, container, false);

        initViews(view);

        setListeners();

        final ViewTreeObserver observer = mFlexLayout.getViewTreeObserver();

        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                requestLayouts();

                setSizesOfCategories((int) (mFlexLayout.getWidth() * 0.3));

                animateBackgroundView();

                mFlexLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        return view;
    }

    private void animateBackgroundView() {
        final Animation animation = new SlideAnimation(mBackgroundView, mBackgroundView.getLayoutParams().height, (int) (mFlexLayout.getHeight() * 0.52));
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setDuration(650);
        mBackgroundView.setAnimation(animation);
        mBackgroundView.startAnimation(animation);
    }

    private void setSizesOfCategories(int size) {
        mPsychicalCategory.getLayoutParams().height = size;
        mPsychicalCategory.getLayoutParams().width = size;

        mIllusionCategory.getLayoutParams().height = size;
        mIllusionCategory.getLayoutParams().width = size;

        mHypnosislCategory.getLayoutParams().height = size;
        mHypnosislCategory.getLayoutParams().width = size;
    }

    private void requestLayouts() {
        mPsychicalCategory.requestLayout();
        mIllusionCategory.requestLayout();
        mHypnosislCategory.requestLayout();
        mBackgroundView.requestLayout();
        mFlexLayout.requestLayout();
    }

    private void animateItem(View view) {
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.ROTATION, 0.0f, 360.0f);

        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(Animation.INFINITE);
        objectAnimator.setInterpolator(new LinearInterpolator());

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                view.animate()
                        .alpha(0.0f)
                        .setDuration(1000);
            }
        });

        objectAnimator.start();
    }

    private void initViews(View view) {
        mBackgroundView = view.findViewById(R.id.category_background_view);
        mFlexLayout = view.findViewById(R.id.flex);

        mPsychicalCategory = view.findViewById(R.id.psychical);
        mIllusionCategory = view.findViewById(R.id.illusion);
        mHypnosislCategory = view.findViewById(R.id.hypnosis);

        mPsychicalRotate = view.findViewById(R.id.psychical_rotate);
        mIllusionRotate = view.findViewById(R.id.illusion_rotate);
        mHypnosislRotate = view.findViewById(R.id.hypnosis_rotate);
    }

    private void setListeners() {
        mPsychicalCategory.setOnClickListener(this);
        mIllusionCategory.setOnClickListener(this);
        mHypnosislCategory.setOnClickListener(this);

        animateItem(mIllusionRotate);
        animateItem(mPsychicalRotate);
        animateItem(mHypnosislRotate);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hypnosis:
                mListener.onCategorySelected(Constants.HYPNOSIS_CATEGORY);
                break;
            case R.id.illusion:
                mListener.onCategorySelected(Constants.ILLUSION_CATEGORY);
                break;
            case R.id.psychical:
                mListener.onCategorySelected(Constants.PSYCHICAL_CATEGORY);
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onCategorySelected(int category);
    }
}
