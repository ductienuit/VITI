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

package com.oriondev.Viti.ui.fragment.stub;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.oriondev.Viti.R;
import com.oriondev.Viti.ui.fragment.base.MultiPanelViewPagerFragment;

/**
 * Created by DucTien on 17/01/19.
 */
public class StubMultiPanelViewPagerFragment extends MultiPanelViewPagerFragment {

    @NonNull
    @Override
    protected PagerAdapter onCreatePagerAdapter(FragmentManager fragmentManager) {
        return new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return new StubListFragment();
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "Stub " + position;
            }
        };
    }

    @Override
    protected int getTitleRes() {
        return R.string.app_name;
    }
}