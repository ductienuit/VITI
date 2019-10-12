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

package com.oriondev.Viti.ui.fragment.base;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oriondev.Viti.R;
import com.oriondev.Viti.ui.adapter.recycler.AbstractCursorAdapter;
import com.oriondev.Viti.ui.view.AdvancedRecyclerView;

/**
 * Created by DucTien on 09/02/19.
 */
public abstract class MultiPanelCursorListFragment extends MultiPanelFragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DEFAULT_LOADER_ID = 24;

    private AdvancedRecyclerView mAdvancedRecyclerView;

    private AbstractCursorAdapter mAbstractCursorAdapter;

    @Override
    protected void onCreatePrimaryPanel(LayoutInflater inflater, @NonNull ViewGroup primaryPanel, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fab_list_primary_panel, primaryPanel, true);
        mAdvancedRecyclerView = view.findViewById(R.id.advanced_recycler_view);
        onPrepareRecyclerView(mAdvancedRecyclerView);
        mAbstractCursorAdapter = onCreateAdapter();
        mAdvancedRecyclerView.setAdapter(mAbstractCursorAdapter);
        mAdvancedRecyclerView.setOnRefreshListener(this);
        getLoaderManager().initLoader(DEFAULT_LOADER_ID, null, this);
        mAdvancedRecyclerView.setState(AdvancedRecyclerView.State.LOADING);
    }

    protected abstract void onPrepareRecyclerView(AdvancedRecyclerView recyclerView);

    protected abstract AbstractCursorAdapter onCreateAdapter();

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mAbstractCursorAdapter.changeCursor(cursor);
        if (cursor != null && cursor.getCount() > 0) {
            mAdvancedRecyclerView.setState(AdvancedRecyclerView.State.READY);
        } else {
            mAdvancedRecyclerView.setState(AdvancedRecyclerView.State.EMPTY);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAbstractCursorAdapter.changeCursor(null);
    }

    @Override
    public void onRefresh() {
        getLoaderManager().restartLoader(DEFAULT_LOADER_ID, null, this);
        mAdvancedRecyclerView.setState(AdvancedRecyclerView.State.REFRESHING);
    }

    protected void recreateLoader() {
        getLoaderManager().restartLoader(DEFAULT_LOADER_ID, null, this);
        mAdvancedRecyclerView.setState(AdvancedRecyclerView.State.LOADING);
    }
}