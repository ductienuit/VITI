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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oriondev.Viti.storage.preference.CurrentWalletController;
import com.oriondev.Viti.storage.preference.PreferenceManager;
import com.oriondev.Viti.ui.adapter.recycler.AbstractCursorAdapter;
import com.oriondev.Viti.ui.view.AdvancedRecyclerView;

/**
 * Created by DucTien on 11/02/19.
 */
public abstract class CursorListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor>, CurrentWalletController {

    private static final int DEFAULT_LOADER_ID = 24;

    private AdvancedRecyclerView mAdvancedRecyclerView;
    private AbstractCursorAdapter mAbstractCursorAdapter;

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBroadcastReceiver = PreferenceManager.registerCurrentWalletObserver(context, this);
    }

    @Override
    public void onDetach() {
        PreferenceManager.unregisterCurrentWalletObserver(getActivity(), mBroadcastReceiver);
        super.onDetach();
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAdvancedRecyclerView = new AdvancedRecyclerView(getActivity());
        onPrepareRecyclerView(mAdvancedRecyclerView);
        mAbstractCursorAdapter = onCreateAdapter();
        mAdvancedRecyclerView.setAdapter(mAbstractCursorAdapter);
        mAdvancedRecyclerView.setOnRefreshListener(this);
        mAdvancedRecyclerView.setState(AdvancedRecyclerView.State.LOADING);
        return mAdvancedRecyclerView;
    }

    protected abstract void onPrepareRecyclerView(AdvancedRecyclerView recyclerView);

    protected abstract AbstractCursorAdapter onCreateAdapter();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getLoaderManager().restartLoader(DEFAULT_LOADER_ID, null, this);
    }

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

    protected boolean shouldRefreshOnCurrentWalletChange() {
        return false;
    }

    @Override
    public void onCurrentWalletChanged(long walletId) {
        if (shouldRefreshOnCurrentWalletChange()) {
            getLoaderManager().restartLoader(DEFAULT_LOADER_ID, null, this);
            mAdvancedRecyclerView.setState(AdvancedRecyclerView.State.LOADING);
        }
    }
}