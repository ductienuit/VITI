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

package com.oriondev.Viti.ui.fragment.primary;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;

import com.oriondev.Viti.R;
import com.oriondev.Viti.broadcast.LocalAction;
import com.oriondev.Viti.broadcast.Message;
import com.oriondev.Viti.storage.database.Contract;
import com.oriondev.Viti.storage.database.DataContentProvider;
import com.oriondev.Viti.storage.preference.PreferenceManager;
import com.oriondev.Viti.ui.activity.NewEditItemActivity;
import com.oriondev.Viti.ui.activity.NewEditTransactionActivity;
import com.oriondev.Viti.ui.adapter.recycler.AbstractCursorAdapter;
import com.oriondev.Viti.ui.adapter.recycler.TransactionModelCursorAdapter;
import com.oriondev.Viti.ui.fragment.base.CursorListFragment;
import com.oriondev.Viti.ui.fragment.multipanel.ModelMultiPanelViewPagerFragment;
import com.oriondev.Viti.ui.view.AdvancedRecyclerView;

/**
 * Created by DucTien on 13/10/2019.
 */
public class TransactionModelListFragment extends CursorListFragment implements TransactionModelCursorAdapter.ActionListener {

    @Override
    protected void onPrepareRecyclerView(AdvancedRecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setEmptyText(R.string.message_no_model_found);
    }

    @Override
    protected AbstractCursorAdapter onCreateAdapter() {
        return new TransactionModelCursorAdapter(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Activity activity = getActivity();
        if (activity != null) {
            Uri uri = DataContentProvider.CONTENT_TRANSACTION_MODELS;
            String[] projection = new String[] {
                    Contract.TransactionModel.ID,
                    Contract.TransactionModel.CATEGORY_NAME,
                    Contract.TransactionModel.CATEGORY_ICON,
                    Contract.TransactionModel.WALLET_CURRENCY,
                    Contract.TransactionModel.DIRECTION,
                    Contract.TransactionModel.MONEY,
                    Contract.TransactionModel.DESCRIPTION
            };
            String selection;
            String[] selectionArgs;
            long currentWallet = PreferenceManager.getCurrentWallet();
            if (currentWallet == PreferenceManager.TOTAL_WALLET_ID) {
                selection = Contract.TransactionModel.WALLET_COUNT_IN_TOTAL + " = 1";
                selectionArgs = null;
            } else {
                selection = Contract.TransactionModel.WALLET_ID + " = ?";
                selectionArgs = new String[] {String.valueOf(currentWallet)};
            }
            return new CursorLoader(activity, uri, projection, selection, selectionArgs, null);
        }
        return null;
    }

    @Override
    public void onModelClick(long modelId) {
        Activity activity = getActivity();
        if (activity != null) {
            Intent intent = new Intent(LocalAction.ACTION_ITEM_CLICK);
            intent.putExtra(Message.ITEM_ID, modelId);
            intent.putExtra(Message.ITEM_TYPE, Message.TYPE_TRANSACTION_MODEL);
            LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        }
    }

    @Override
    public void onModelAddClick(long modelId) {
        Uri uri = NewEditTransactionActivity.insertTransactionFromModel(getActivity(), modelId);
        if (uri != null) {
            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof ModelMultiPanelViewPagerFragment) {
                ((ModelMultiPanelViewPagerFragment) parentFragment).onTransactionAdded(uri);
            }
        } else {
            // TODO: alert user of the failure.
        }
    }

    @Override
    public void onModelEditClick(long modelId) {
        Intent intent = new Intent(getActivity(), NewEditTransactionActivity.class);
        intent.putExtra(NewEditItemActivity.MODE, NewEditItemActivity.Mode.NEW_ITEM);
        intent.putExtra(NewEditTransactionActivity.TYPE, NewEditTransactionActivity.TYPE_MODEL);
        intent.putExtra(NewEditTransactionActivity.MODEL_ID, modelId);
        startActivity(intent);
    }

    @Override
    protected boolean shouldRefreshOnCurrentWalletChange() {
        // this fragment content is dependant on the current
        // wallet when the loader is created, so the query
        // operation must be recreated from the beginning.
        return true;
    }
}