package free.optical.illusions.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import free.optical.illusions.Fragments.SelectedCategoryFragment;
import free.optical.illusions.R;
import free.optical.illusions.Constants;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.GifHolder> {

    private final Context mContext;
    private final SelectedCategoryFragment mFragment;
    private final ArrayList<Integer> mGifIds;

    private int mPlaceholderImageId;

    public RecyclerViewAdapter(Context mContext, SelectedCategoryFragment mFragment, int mCategory, ArrayList<ArrayList<Integer>> mIds) {
        this.mContext = mContext;
        this.mFragment = mFragment;
        this.mGifIds = mIds.get(mCategory);

        switch (mCategory) {
            case Constants.HYPNOSIS_CATEGORY:
                mPlaceholderImageId = R.drawable.hypnosis_logo;
                break;
            case Constants.PSYCHICAL_CATEGORY:
                mPlaceholderImageId = R.drawable.psychical_logo;
                break;
            case Constants.ILLUSION_CATEGORY:
                mPlaceholderImageId = R.drawable.illusion_logo;
                break;
        }
    }

    @NonNull
    @Override
    public GifHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.gif_item, viewGroup, false);
        return new GifHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GifHolder gifHolder, int i) {
        Ion.with(gifHolder.mGifView).placeholder(mPlaceholderImageId).load("android.resource://" + mContext.getPackageName() + "/" + mGifIds.get(i));

        gifHolder.mGifView.setOnClickListener((o) ->
                mFragment.onGifSelected(mGifIds.get(i))
        );
    }

    @Override
    public int getItemCount() {
        return mGifIds.size();
    }

    public class GifHolder extends RecyclerView.ViewHolder {

        private final ImageView mGifView;
        private final CardView mCardView;

        GifHolder(@NonNull View itemView) {
            super(itemView);
            mGifView = itemView.findViewById(R.id.gif_item_view);
            mCardView = itemView.findViewById(R.id.gif_card_view);

            mGifView.requestLayout();

            mGifView.getLayoutParams().width = (int) (mFragment.getFlexLayoutHeight() * 0.132);
            mGifView.getLayoutParams().height = (int) (mFragment.getFlexLayoutHeight() * 0.132);

            mCardView.setRadius((float) (mGifView.getLayoutParams().width / 2));


        }
    }
}
