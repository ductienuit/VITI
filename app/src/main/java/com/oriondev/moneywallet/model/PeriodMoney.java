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

import com.oriondev.Viti.utils.DateUtils;

import java.util.Date;

/**
 * Created by DucTien on 12/10/2019.
 */
public class PeriodMoney {

    private final Date mStartDate;
    private final Date mEndDate;
    private final Money mIncomes;
    private final Money mExpenses;
    private final Money mNetIncomes;

    public PeriodMoney(Date startDate, Date endDate) {
        mStartDate = startDate;
        mEndDate = endDate;
        mIncomes = new Money();
        mExpenses = new Money();
        mNetIncomes = new Money();
    }

    public void addIncome(String currency, long money) {
        mIncomes.addMoney(currency, money);
        mNetIncomes.addMoney(currency, money);
    }

    public void addExpense(String currency, long money) {
        mExpenses.addMoney(currency, money);
        mNetIncomes.removeMoney(currency, money);
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public Money getIncomes() {
        return mIncomes;
    }

    public Money getExpenses() {
        return mExpenses;
    }

    public Money getNetIncomes() {
        return mNetIncomes;
    }

    public boolean isTimeRange() {
        return DateUtils.isTheSameDayAndHour(mStartDate, mEndDate);
    }
}