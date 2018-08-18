package techfist.dev.omdbbrowser.utils;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import techfist.dev.omdbbrowser.R;
import techfist.dev.omdbbrowser.ui.landing.MoviesActivityViewModel;


/**
 * binding calls, works in tandem with Android data binding library
 * https://developer.android.com/topic/libraries/data-binding/
 *
 * for the purpose of loading Images over network am using Glide(https://github.com/bumptech/glide)
 * with all sorts of cache enabled to assist in faster loading of images when requested again,
 * saving both time and bandwidth.
 *
 * to have some transformation on top of downloaded Images, am using a publically available transformation sets
 * https://github.com/wasabeef/glide-transformations
 *
 */

public class DataBindingUtils {

    @BindingAdapter({"dataAdapter", "scrollListener"})
    public static void setDataAdapter(@NonNull RecyclerView recyclerView,
                                      @NonNull RecyclerView.Adapter adapter,
                                      @NonNull MoviesActivityViewModel moviesActivityViewModel) {

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayout.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(moviesActivityViewModel);
    }


    @BindingAdapter({"imageUrl"})
    public static void loadImage(@NonNull final ImageView imageView, @Nullable final String imageUrl) {

        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
            return;
        }
        int radius = imageView.getResources()
                .getDimensionPixelSize(R.dimen.radius);
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(new RequestOptions().centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new RoundedCornersTransformation(radius, 0)))
                .into(imageView);
    }

    @BindingAdapter({"posterUrl"})
    public static void loadPoster(@NonNull final ImageView imageView, @Nullable final String imageUrl) {

        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
            return;
        }
        int radius = imageView.getResources()
                .getDimensionPixelSize(R.dimen.radius);
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(new RequestOptions().centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }

}
