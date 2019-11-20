package com.example.homework07;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.homework07.TripsFragment.OnListFragmentInteractionListener;
import com.example.homework07.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TripsRecyclerViewAdapter extends RecyclerView.Adapter<TripsRecyclerViewAdapter.ViewHolder> {

    private final List<Trip> trips;
    private final OnListFragmentInteractionListener mListener;

    public TripsRecyclerViewAdapter(List<Trip> items, OnListFragmentInteractionListener listener) {
        trips = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.triptitle.setText(trips.get(position).title);
        holder.triplocationtext.setText(trips.get(position).location);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(trips.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView triptitle;
        public final TextView triplocationtext;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            triptitle = view.findViewById(R.id.cardviewbglayout).findViewById(R.id.tripitemtitle);
            triplocationtext = view.findViewById(R.id.locationlayout).findViewById(R.id.location);
        }


    }
}
