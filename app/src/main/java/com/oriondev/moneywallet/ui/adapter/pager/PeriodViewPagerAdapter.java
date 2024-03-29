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

package com.oriondev.Viti.ui.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oriondev.Viti.R;
import com.oriondev.Viti.ui.fragment.single.PeriodDetailFlowFragment;
import com.oriondev.Viti.ui.fragment.single.PeriodDetailSummaryFragment;

import java.util.Date;

/**
 * Created by DucTien on 13/10/2019.
 */
public class PeriodViewPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private final Date mStartDate;
    private final Date mEndDate;

    public PeriodViewPagerAdapter(FragmentManager fm, Context context, Date startDate, Date endDate) {
        super(fm);
        mContext = context;
        mStartDate = startDate;
        mEndDate = endDate;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return PeriodDetailFlowFragment.newInstance(mStartDate, mEndDate, true);
            case 1:
                return PeriodDetailFlowFragment.newInstance(mStartDate, mEndDate, false);
            case 2:
                return PeriodDetailSummaryFragment.newInstance(mStartDate, mEndDate);
            default:
                throw new IllegalArgumentException("Invalid position");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.hint_incomes);
            case 1:
                return mContext.getString(R.string.hint_expenses);
            case 2:
                return mContext.getString(R.string.hint_summary);
        }
        return null;
    }
}