package com.dbis.reservationsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dbis.reservationsystem.Entity.MeetingRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by shi12 on 2016/3/12.
 */
public class RoomListFragmant extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.fragment_room_list, container, false);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
                getRandomSublist()));
    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;            // color of background
        private List<MeetingRoom> mValues;  // list of item details

        public static class ViewHolder extends RecyclerView.ViewHolder {
            //public String mBoundString;

            public final View mView;
            public final ImageView mAuthority;
            public final TextView mName, mLocation, mCapacity, mOpentime, mDescription, mConfirm;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mName = (TextView) view.findViewById(R.id.room_name);
                mLocation = (TextView) view.findViewById(R.id.room_location);
                mCapacity = (TextView) view.findViewById(R.id.room_capacity);
                mOpentime = (TextView) view.findViewById(R.id.room_openTime);
                mDescription = (TextView) view.findViewById(R.id.room_description);
                mAuthority = (ImageView) view.findViewById(R.id.room_authority);
                mConfirm = (TextView) view.findViewById(R.id.room_confirm);
            }
        }

        public MeetingRoom getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<MeetingRoom> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_meetingroom, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            //holder.mBoundString = mValues.get(position);
            MeetingRoom tmpRoomItem = mValues.get(position);
            holder.mName.setText(tmpRoomItem.getRoomName());
            holder.mLocation.setText(tmpRoomItem.getLocation());
            holder.mCapacity.setText(tmpRoomItem.getCapacity());
            holder.mOpentime.setText(tmpRoomItem.getBeginTime() + " - " + tmpRoomItem.getEndTime());
            holder.mDescription.setText(tmpRoomItem.getDescription());
            if(tmpRoomItem.isNeedConfirm())
                holder.mConfirm.setVisibility(View.VISIBLE);
            else
            holder.mConfirm.setVisibility(View.INVISIBLE);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, bookdetailActivity.class);
                    //intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString);

                    context.startActivity(intent);
                }
            });

            Glide.with(holder.mAuthority.getContext())
                    .load(Cheeses.getRandomCheeseDrawable())
                    .fitCenter()
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}