package free.optical.illusions.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.flexbox.FlexboxLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;

import free.optical.illusions.Adapter.RecyclerViewAdapter;
import free.optical.illusions.CustomAnimation.SlideAnimation;
import free.optical.illusions.CustomView.GridRecyclerView;
import free.optical.illusions.R;
import free.optical.illusions.Constants;

public class SelectedCategoryFragment extends Fragment implements View.OnClickListener {

    private int mCategory;

    private FlexboxLayout mFlexLayout;
    private ImageView mBackgroundView;
    private ImageView mPsychicalIcon;
    private ImageView mHypnosislIcon;
    private ImageView mIllusionIcon;
    private GridRecyclerView mGifListView;

    private ArrayList<ArrayList<Integer>> mIds;

    private OnFragmentInteractionListener mListener;

    public SelectedCategoryFragment() {
        // Required empty public constructor
    }

    public static SelectedCategoryFragment newInstance(int category) {
        SelectedCategoryFragment fragment = new SelectedCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getInt(Constants.CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_selected_category, container, false);

        try {
            getGifsId();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        initViews(view);

        hideCurrentCategory(mCategory);

        final ViewTreeObserver observer = mFlexLayout.getViewTreeObserver();

        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                requestLayouts();

                mGifListView.getLayoutParams().height = (int) (mFlexLayout.getHeight() * 0.74);

                if (Constants.isNavBarAvailable(getContext())) {
                    resizeFlexLayout();
                    resizeBackgroundView();
                }

                animateBackgroundView();

                mFlexLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        mGifListView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));

        mGifListView.setAdapter(new RecyclerViewAdapter(getContext(), this, mCategory, mIds));

        runAnimation();

        return view;
    }

    private void requestLayouts() {
        mBackgroundView.requestLayout();
        mFlexLayout.requestLayout();
        mGifListView.requestLayout();
    }

    private void animateBackgroundView() {
        final Animation animation = new SlideAnimation(mBackgroundView, 1, (int) (mFlexLayout.getHeight() * 0.97));

        animation.setInterpolator(new AccelerateInterpolator());
        animation.setDuration(750);
        mBackgroundView.setAnimation(animation);
        mBackgroundView.startAnimation(animation);
    }

    private void resizeBackgroundView() {
        final RelativeLayout.LayoutParams paramsImageView = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mBackgroundView.getLayoutParams().height);
        paramsImageView.setMargins(0,0,0, (Constants.getNavigationBarHeight(getContext()) + Constants.convertDpToPixel(30, getContext())));
        paramsImageView.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mBackgroundView.setLayoutParams(paramsImageView);
    }

    private void resizeFlexLayout() {
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0,0,0, Constants.getNavigationBarHeight(getContext()));
        mFlexLayout.setLayoutParams(params);
    }

    private void runAnimation() {
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.grid_layout_animation);
        mGifListView.setLayoutAnimation(animation);
        mGifListView.scheduleLayoutAnimation();
    }

    private void initViews(View view) {
        mFlexLayout = view.findViewById(R.id.selected_category_flex_container);
        mGifListView = view.findViewById(R.id.gif_recycler_view);
        mBackgroundView = view.findViewById(R.id.selected_category_background_view);
        mPsychicalIcon = view.findViewById(R.id.psychical_logo_view);
        mHypnosislIcon = view.findViewById(R.id.hypnosis_logo_view);
        mIllusionIcon = view.findViewById(R.id.illusion_logo_view);

        mPsychicalIcon.setOnClickListener(this);
        mHypnosislIcon.setOnClickListener(this);
        mIllusionIcon.setOnClickListener(this);
    }

    private void hideCurrentCategory(int category) {
        if (category == Constants.PSYCHICAL_CATEGORY) {
            mPsychicalIcon.setVisibility(View.GONE);
            mHypnosislIcon.setVisibility(View.VISIBLE);
            mIllusionIcon.setVisibility(View.VISIBLE);

            return;
        }

        if (category == Constants.HYPNOSIS_CATEGORY) {
            mPsychicalIcon.setVisibility(View.VISIBLE);
            mHypnosislIcon.setVisibility(View.GONE);
            mIllusionIcon.setVisibility(View.VISIBLE);

            return;
        }

        mPsychicalIcon.setVisibility(View.VISIBLE);
        mHypnosislIcon.setVisibility(View.VISIBLE);
        mIllusionIcon.setVisibility(View.GONE);
    }

    private void getGifsId() throws IllegalAccessException {

        mIds = new ArrayList<>();

        final ArrayList<Integer> mPsychicalIds = new ArrayList<>();
        final ArrayList<Integer> mHypnosislIds = new ArrayList<>();
        final ArrayList<Integer> mIllusionIds = new ArrayList<>();

        final Field[] fields = R.raw.class.getFields();
        for (int count = 0; count < fields.length; count++) {

            if (count <= 11) {
                mPsychicalIds.add(fields[count].getInt(fields[count]));
            } else if (count <= 23) {
                mIllusionIds.add(fields[count].getInt(fields[count]));
            } else if (count <= 35) {
                mHypnosislIds.add(fields[count].getInt(fields[count]));
            }
        }

        mIds.add(mPsychicalIds);
        mIds.add(mHypnosislIds);
        mIds.add(mIllusionIds);
    }

    public void onGifSelected(Integer imageId) {
        mListener.onImageSelected(imageId);
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
            case R.id.psychical_logo_view:
                hideCurrentCategory(0);
                mGifListView.setAdapter(new RecyclerViewAdapter(getContext(), this, 0, mIds));
                runAnimation();
                break;

            case R.id.hypnosis_logo_view:
                hideCurrentCategory(2);
                mGifListView.setAdapter(new RecyclerViewAdapter(getContext(), this, 2, mIds));
                runAnimation();
                break;

            case R.id.illusion_logo_view:
                hideCurrentCategory(1);
                mGifListView.setAdapter(new RecyclerViewAdapter(getContext(), this, 1, mIds));
                runAnimation();
                break;
        }
    }

    /**
     * Returns height of FlexBox container.
     */
    public int getFlexLayoutHeight() {
        return mFlexLayout.getHeight();
    }

    public interface OnFragmentInteractionListener {
        void onImageSelected(int imageId);
    }
}
