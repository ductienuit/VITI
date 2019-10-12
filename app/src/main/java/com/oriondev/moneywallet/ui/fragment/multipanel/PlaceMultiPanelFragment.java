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

package com.oriondev.Viti.ui.fragment.multipanel;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import com.oriondev.Viti.R;
import com.oriondev.Viti.storage.database.Contract;
import com.oriondev.Viti.storage.database.DataContentProvider;
import com.oriondev.Viti.ui.activity.MapActivity;
import com.oriondev.Viti.ui.activity.NewEditItemActivity;
import com.oriondev.Viti.ui.activity.NewEditPlaceActivity;
import com.oriondev.Viti.ui.adapter.recycler.AbstractCursorAdapter;
import com.oriondev.Viti.ui.adapter.recycler.PlaceCursorAdapter;
import com.oriondev.Viti.ui.fragment.base.MultiPanelCursorListItemFragment;
import com.oriondev.Viti.ui.fragment.base.SecondaryPanelFragment;
import com.oriondev.Viti.ui.fragment.secondary.PlaceItemFragment;
import com.oriondev.Viti.ui.view.AdvancedRecyclerView;

/**
 * Created by DucTien on 02/03/19.
 */
public class PlaceMultiPanelFragment extends MultiPanelCursorListItemFragment implements PlaceCursorAdapter.ActionListener {

    private static final String SECONDARY_FRAGMENT_TAG = "PlaceMultiPanelFragment::Tag::SecondaryPanelFragment";

    @Override
    protected void onPrepareRecyclerView(AdvancedRecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setEmptyText(R.string.message_no_place_found);
    }

    @Override
    protected AbstractCursorAdapter onCreateAdapter() {
        return new PlaceCursorAdapter(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Activity activity = getActivity();
        if (activity != null) {
            Uri uri = DataContentProvider.CONTENT_PLACES;
            String[] projection = new String[] {
                    Contract.Place.ID,
                    Contract.Place.NAME,
                    Contract.Place.ICON,
                    Contract.Place.ADDRESS
            };
            String sortOrder = Contract.Place.NAME + " DESC";
            return new CursorLoader(activity, uri, projection, null, null, sortOrder);
        }
        return null;
    }

    @Override
    protected SecondaryPanelFragment onCreateSecondaryPanel() {
        return new PlaceItemFragment();
    }

    @Override
    protected String getSecondaryFragmentTag() {
        return SECONDARY_FRAGMENT_TAG;
    }

    @Override
    protected int getTitleRes() {
        return R.string.menu_place;
    }

    @MenuRes
    protected int onInflateMenu() {
        return R.menu.menu_place_multipanel_fragment;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_map:
                startActivity(new Intent(getActivity(), MapActivity.class));
                break;
        }
        return false;
    }

    @Override
    public void onPlaceClick(long id) {
        showItemId(id);
        showSecondaryPanel();
    }

    @Override
    protected void onFloatingActionButtonClick() {
        Intent intent = new Intent(getActivity(), NewEditPlaceActivity.class);
        intent.putExtra(NewEditItemActivity.MODE, NewEditItemActivity.Mode.NEW_ITEM);
        startActivity(intent);
    }
}