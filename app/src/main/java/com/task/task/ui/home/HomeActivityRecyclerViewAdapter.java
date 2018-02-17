package com.task.task.ui.home;


import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.task.task.R;
import com.task.task.domain.model.RestaurantInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivityRecyclerViewAdapter extends RecyclerView.Adapter<HomeActivityRecyclerViewAdapter.RestaurantsViewHolder> {

    public interface Listener {

        Listener EMPTY = (restaurantInfo, position, deleteRestaurant) -> {
        };

        void onRestaurantClicked(RestaurantInfo restaurantInfo, int position, boolean deleteRestaurant);

    }

    public HomeActivityRecyclerViewAdapter() {
        setHasStableIds(true);
    }

    private HomeActivityRecyclerViewAdapter.Listener listener = HomeActivityRecyclerViewAdapter.Listener.EMPTY;

    private final List<RestaurantInfo> restaurantInfoList = new ArrayList<>();

    private long mLastClickTime = 0;

    @Override
    public HomeActivityRecyclerViewAdapter.RestaurantsViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new HomeActivityRecyclerViewAdapter.RestaurantsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final HomeActivityRecyclerViewAdapter.RestaurantsViewHolder holder, final int position) {
        holder.restaurantName.setText(restaurantInfoList.get(position).name);
        holder.restaurantAddress.setText(restaurantInfoList.get(position).address);
    }

    @Override
    public int getItemCount() {
        return restaurantInfoList.size();
    }

    @Override
    public long getItemId(int position) {
        return restaurantInfoList.get(position).id;
    }

    public void setData(final List<RestaurantInfo> data) {
        restaurantInfoList.addAll(data);
        notifyDataSetChanged();
    }

    public void setListener(final HomeActivityRecyclerViewAdapter.Listener listener) {
        this.listener = listener != null ? listener : HomeActivityRecyclerViewAdapter.Listener.EMPTY;
    }


    public class RestaurantsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.restaurant_item_name)
        protected TextView restaurantName;

        @BindView(R.id.restaurant_item_address)
        protected TextView restaurantAddress;

        @OnClick(R.id.restaurant_item_layout)
        public void onRestaurantClicked() {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

//                restaurantClicked = true;
//                new Handler().postDelayed(() -> restaurantClicked = false, 300);
            listener.onRestaurantClicked(restaurantInfoList.get(getAdapterPosition()), getAdapterPosition(), false);

        }

        @OnClick(R.id.restaurant_item_delete)
        public void onRestaurantDelete() {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
//                restaurantClicked = true;
//                new Handler().postDelayed(() -> restaurantClicked = false, 300);
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            listener.onRestaurantClicked(restaurantInfoList.get(getAdapterPosition()), getAdapterPosition(), true);
            restaurantInfoList.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), restaurantInfoList.size());


        }

        public RestaurantsViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
