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

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;

import com.oriondev.Viti.R;
import com.oriondev.Viti.ui.activity.base.SinglePanelViewPagerActivity;
import com.oriondev.Viti.ui.adapter.pager.PeriodViewPagerAdapter;
import com.oriondev.Viti.utils.DateFormatter;

import java.util.Date;

/**
 * Created by DucTien on 13/10/2019.
 */
public class PeriodDetailActivity extends SinglePanelViewPagerActivity {

    public static final String START_DATE = "PeriodDetailActivity::Arguments::StartDate";
    public static final String END_DATE = "PeriodDetailActivity::Arguments::EndDate";

    @NonNull
    @Override
    protected PagerAdapter onCreatePagerAdapter(FragmentManager fragmentManager) {
        Intent intent = getIntent();
        if (intent != null) {
            Date startDate = (Date) intent.getSerializableExtra(START_DATE);
            Date endDate = (Date) intent.getSerializableExtra(END_DATE);
            if (startDate != null && endDate != null) {
                setToolbarSubtitle(DateFormatter.getDateRange(this, startDate, endDate));
            }
            return new PeriodViewPagerAdapter(fragmentManager, this, startDate, endDate);
        } else {
            return new PeriodViewPagerAdapter(fragmentManager, this, null, null);
        }
    }

    @Override
    protected int getActivityTitleRes() {
        return R.string.menu_overview;
    }

    @Override
    protected boolean isFloatingActionButtonEnabled() {
        return false;
    }
}