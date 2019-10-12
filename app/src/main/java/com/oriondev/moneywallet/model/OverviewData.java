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

package com.oriondev.Viti.model;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.RadarData;

import java.util.List;

/**
 * Created by DucTien on 17/08/19.
 */
public class OverviewData {

    private final BarData mBarData;
    private final LineData mLineData;
    private final RadarData mRadarData;
    private final List<PeriodMoney> mPeriodMoneyList;

    public OverviewData(BarData barData, LineData lineData, RadarData radarData, List<PeriodMoney> periodMoneyList) {
        mBarData = barData;
        mLineData = lineData;
        mRadarData = radarData;
        mPeriodMoneyList = periodMoneyList;
    }

    public BarData getBarData() {
        return mBarData;
    }

    public LineData getLineData() {
        return mLineData;
    }

    public RadarData getRadarData() {
        return mRadarData;
    }

    public PeriodMoney getPeriodMoney(int index) {
        return mPeriodMoneyList.get(index);
    }

    public int getPeriodCount() {
        return mPeriodMoneyList.size();
    }
}