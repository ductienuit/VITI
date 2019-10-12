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

package com.oriondev.Viti.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.oriondev.Viti.R;
import com.oriondev.Viti.ui.activity.base.SinglePanelActivity;
import com.oriondev.Viti.ui.fragment.single.AboutFragment;

/**
 * Created by andre on 22/03/2019.
 */
public class AboutActivity extends SinglePanelActivity {

    private static final String FRAGMENT_TAG = "AboutActivity::Tag::AboutFragment";

    @Override
    protected void onCreatePanelView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .replace(parent.getId(), new AboutFragment(), FRAGMENT_TAG)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .show(fragment)
                    .commit();
        }
    }

    @Override
    protected int getActivityTitleRes() {
        return R.string.title_activity_about;
    }

    @Override
    protected boolean isFloatingActionButtonEnabled() {
        return false;
    }
}