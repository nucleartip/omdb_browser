package techfist.dev.omdbbrowser.ui.landing;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import techfist.dev.omdbbrowser.BR;
import techfist.dev.omdbbrowser.R;

/**
 * adapter to work along with {@link RecyclerView}, for displaying movies
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.DataBindingViewHolder> {

    @VisibleForTesting List<MovieModel> items;
    private LayoutInflater layoutInflater;

    void appendList(@NonNull List<MovieModel> models) {

        if (items == null) {
            items = new ArrayList<>();
        }

        items.addAll(models);
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public DataBindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ViewDataBinding binder = DataBindingUtil.inflate(layoutInflater, R.layout.movie_list_item, parent, false);
        return new DataBindingViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBindingViewHolder holder, int position) {
        holder.binder.setVariable(BR.vm, items.get(position));
        holder.binder.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    class DataBindingViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binder;

        DataBindingViewHolder(@NonNull ViewDataBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }
}
