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

package com.oriondev.Viti.ui.activity.base;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.oriondev.Viti.R;
import com.oriondev.Viti.ui.activity.ToolbarController;
import com.oriondev.Viti.ui.fragment.base.MultiPanelFragment;

/**
 * Created by DucTien on 06/04/19.
 */
public abstract class MultiPanelActivity extends BaseActivity implements ToolbarController {

    private MultiPanelFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_container);
        FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = getMultiPanelFragmentTag();
        mFragment = (MultiPanelFragment) fragmentManager.findFragmentByTag(tag);
        if (mFragment != null) {
            fragmentManager.beginTransaction().show(mFragment).commit();
        } else {
            mFragment = onCreateMultiPanelFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mFragment, tag)
                    .commit();
        }
    }

    protected abstract MultiPanelFragment onCreateMultiPanelFragment();

    protected abstract String getMultiPanelFragmentTag();

    @Override
    public void onBackPressed() {
        if (mFragment != null && !mFragment.navigateBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }

        });
    }
}