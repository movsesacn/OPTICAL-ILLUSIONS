package free.optical.illusions;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Constants {

    public static final String CATEGORY = "CATEGORY";
    public static final String ID = "ID";

    public static final int PSYCHICAL_CATEGORY = 0;
    public static final int ILLUSION_CATEGORY = 1;
    public static final int HYPNOSIS_CATEGORY = 2;

    public static boolean isNavBarAvailable(Context context) {
        final Resources resources = context.getResources();

        int idNavBarAvailable = resources.getIdentifier("config_showNavigationBar", "bool", "android");

        return resources.getBoolean(idNavBarAvailable);
    }

    public static int getNavigationBarHeight(Context context) {
        final Resources resources = context.getResources();

        final int idNavHeight = resources.getIdentifier("navigation_bar_height" , "dimen", "android");

        if (idNavHeight > 0) {
            return resources.getDimensionPixelSize(idNavHeight)  ;
        }

        return 0;
    }

    public static int convertDpToPixel(float dp, Context context){
        return (int) dp * (context.getResources().getDisplayMetrics( ).densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
