package com.alan.rockonapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alan.rockonapp.R;
import com.alan.rockonapp.adapter.model.Setlist;

import java.util.List;

/**
 * Created by alan on 24/09/15.
 */
public class SetlistAdapter extends BaseAdapter {

    private Context mContext;
    private List mItems;

    /**
     * Constructor with parameters
     *
     * @param context - the context where the adapter is going to be used.
     * @param items - the list of items that the adapter must represent.
     */
    public SetlistAdapter(@NonNull final Context context, List items) {
        this.mContext = context;
        this.mItems = items;
    }

    public void setItems(List items) {
        this.mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if(null != convertView) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.setlist_item, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        bindViews(viewHolder, position);

        return convertView;
    }

    /**
     * Method that bind views with the data to be shown.
     *
     * @param viewHolder - the viewHolder used to set the data.
     * @param position - the position where of the data to get.
     */
    private void bindViews(@NonNull final ViewHolder viewHolder, final int position) {
        final Setlist setlist = (Setlist) getItem(position);

        viewHolder.setlistDate.setText(setlist.getEventDate());
    }

    /**
     * ViewHolder class used for caching the views needed by our adapter.
     *
     * @author alan
     */
    protected static class ViewHolder {
        private TextView setlistDate;

        /**
         * Constructor with parameters.
         *
         * @param view - The root view that contains all the views needed.
         */
        public ViewHolder(@NonNull final View view) {
            setlistDate = (TextView) view.findViewById(R.id.setlist_item_date);
        }
    }
}

