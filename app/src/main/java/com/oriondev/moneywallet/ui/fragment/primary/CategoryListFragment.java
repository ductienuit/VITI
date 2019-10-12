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
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;

import com.oriondev.Viti.R;
import com.oriondev.Viti.broadcast.LocalAction;
import com.oriondev.Viti.broadcast.Message;
import com.oriondev.Viti.storage.database.Contract;
import com.oriondev.Viti.storage.database.DataContentProvider;
import com.oriondev.Viti.ui.adapter.recycler.AbstractCursorAdapter;
import com.oriondev.Viti.ui.adapter.recycler.CategoryCursorAdapter;
import com.oriondev.Viti.ui.fragment.base.CursorListFragment;
import com.oriondev.Viti.ui.view.AdvancedRecyclerView;

/**
 * Created by DucTien on 13/10/2019.
 */
public class CategoryListFragment extends CursorListFragment implements CategoryCursorAdapter.CategoryActionListener {

    private static final String ARG_CATEGORY_TYPE = "CategoryListFragment::Arguments::CategoryType";
    private static final String ARG_SHOW_CHILDREN = "CategoryListFragment::Arguments::ShowChildren";

    private Controller mController;

    public static CategoryListFragment newInstance(Contract.CategoryType type, boolean showChildren) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_CATEGORY_TYPE, type);
        arguments.putBoolean(ARG_SHOW_CHILDREN, showChildren);
        CategoryListFragment fragment = new CategoryListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void onPrepareRecyclerView(AdvancedRecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setEmptyText(R.string.message_no_category_found);
    }

    @Override
    protected AbstractCursorAdapter onCreateAdapter() {
        return new CategoryCursorAdapter(this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Activity activity = getActivity();
        Bundle arguments = getArguments();
        if (activity != null && arguments != null) {
            // unpack the argument bundle
            Contract.CategoryType type = (Contract.CategoryType) arguments.getSerializable(ARG_CATEGORY_TYPE);
            if (type == null) {
                throw new IllegalArgumentException("Unknown category type for this fragment");
            }
            boolean showChildren = arguments.getBoolean(ARG_SHOW_CHILDREN, true);
            // query the content provider
            Uri uri = DataContentProvider.CONTENT_CATEGORIES;
            String[] projection = new String[] {
                    Contract.Category.ID,
                    Contract.Category.ICON,
                    Contract.Category.NAME,
                    Contract.Category.PARENT
            };
            String selection = Contract.Category.TYPE + " = ?";
            if (!showChildren) {
                selection += " AND " + Contract.Category.PARENT + " IS NULL";
            }
            String[] selectionArgs = new String[] {String.valueOf(type.getValue())};
            String sortOrder = Contract.Category.GROUP_INDEX + " ASC, " + Contract.Category.GROUP_NAME +
                    " ASC, " + Contract.Category.GROUP_ID + " ASC, " + Contract.Category.PARENT +
                    " IS NULL DESC, " + Contract.Category.NAME + " ASC";
            return new CursorLoader(activity, uri, projection, selection, selectionArgs, sortOrder);
        }
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Controller) {
            mController = (Controller) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mController = null;
    }

    @Override
    public void onCategoryClick(long id) {
        if (mController != null) {
            mController.onCategoryClick(id);
        } else {
            Activity activity = getActivity();
            if (activity != null) {
                Intent intent = new Intent(LocalAction.ACTION_ITEM_CLICK);
                intent.putExtra(Message.ITEM_ID, id);
                intent.putExtra(Message.ITEM_TYPE, Message.TYPE_CATEGORY);
                LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
            }
        }
    }

    public interface Controller {

        void onCategoryClick(long id);
    }
}