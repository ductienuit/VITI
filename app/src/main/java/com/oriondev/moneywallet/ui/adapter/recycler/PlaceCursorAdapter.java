/*
 * Copyright (c) 2019.
 *
 * This file is part of Viti.
 *
 * Viti is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Viti is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Viti.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.oriondev.Viti.ui.adapter.recycler;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oriondev.Viti.R;
import com.oriondev.Viti.model.Icon;
import com.oriondev.Viti.storage.database.Contract;
import com.oriondev.Viti.utils.IconLoader;

/**
 * Created by DucTien on 04/03/19.
 */
public class PlaceCursorAdapter extends AbstractCursorAdapter<PlaceCursorAdapter.ViewHolder> {

    private final ActionListener mActionListener;

    private int mIndexId;
    private int mIndexName;
    private int mIndexIcon;
    private int mIndexAddress;

    public PlaceCursorAdapter(ActionListener actionListener) {
        super(null, Contract.Place.ID);
        mActionListener = actionListener;
    }

    @Override
    protected void onLoadColumnIndices(@NonNull Cursor cursor) {
        mIndexId = cursor.getColumnIndex(Contract.Place.ID);
        mIndexName = cursor.getColumnIndex(Contract.Place.NAME);
        mIndexIcon = cursor.getColumnIndex(Contract.Place.ICON);
        mIndexAddress = cursor.getColumnIndex(Contract.Place.ADDRESS);
    }

    @Override
    public void onBindViewHolder(PlaceCursorAdapter.ViewHolder holder, Cursor cursor) {
        Icon icon = IconLoader.parse(cursor.getString(mIndexIcon));
        IconLoader.loadInto(icon, holder.mAvatarImageView);
        holder.mNameTextView.setText(cursor.getString(mIndexName));
        String address = cursor.getString(mIndexAddress);
        if (TextUtils.isEmpty(address)) {
            holder.mAddressTextView.setText(R.string.hint_address_unknown);
        } else {
            holder.mAddressTextView.setText(address);
        }
    }

    @Override
    public PlaceCursorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_place_item, parent, false);
        return new ViewHolder(itemView);
    }

    /*package-local*/ class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mAvatarImageView;
        private TextView mNameTextView;
        private TextView mAddressTextView;

        /*package-local*/ ViewHolder(View itemView) {
            super(itemView);
            mAvatarImageView = itemView.findViewById(R.id.avatar_image_view);
            mNameTextView = itemView.findViewById(R.id.name_text_view);
            mAddressTextView = itemView.findViewById(R.id.address_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mActionListener != null) {
                Cursor cursor = getSafeCursor(getAdapterPosition());
                if (cursor != null) {
                    mActionListener.onPlaceClick(cursor.getLong(mIndexId));
                }
            }
        }
    }

    public interface ActionListener {

        void onPlaceClick(long id);
    }
}
