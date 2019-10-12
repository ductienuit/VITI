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

package com.oriondev.Viti.ui.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oriondev.Viti.R;
import com.oriondev.Viti.model.PeriodDetailSummaryData;
import com.oriondev.Viti.model.PeriodMoney;
import com.oriondev.Viti.utils.DateFormatter;
import com.oriondev.Viti.utils.MoneyFormatter;

/**
 * Created by DucTien on 14/08/19.
 */
public class PeriodDetailSummaryAdapter extends RecyclerView.Adapter<PeriodDetailSummaryAdapter.ViewHolder> {

    private final Controller mController;

    private PeriodDetailSummaryData mData;

    private final MoneyFormatter mMoneyFormatter;

    public PeriodDetailSummaryAdapter(Controller controller) {
        mController = controller;
        mMoneyFormatter = MoneyFormatter.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_period_summary_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PeriodMoney periodMoney = mData.getPeriodMoney(position);
        if (!periodMoney.isTimeRange()) {
            DateFormatter.applyDateRange(holder.mNameTextView, periodMoney.getStartDate(), periodMoney.getEndDate());
        } else {
            DateFormatter.applyTimeRange(holder.mNameTextView, periodMoney.getStartDate(), periodMoney.getEndDate());
        }
        // TODO: maybe can be useful to display also incomes and expenses
        mMoneyFormatter.applyTinted(holder.mMoneyTextView, periodMoney.getNetIncomes());
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.getPeriodCount() : 0;
    }

    public void setData(PeriodDetailSummaryData data) {
        mData = data;
        notifyDataSetChanged();
    }

    /*package-local*/ class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameTextView;
        private TextView mMoneyTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.name_text_view);
            mMoneyTextView = itemView.findViewById(R.id.money_text_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mController != null) {
                int index = getAdapterPosition();
                if (mData != null) {
                    PeriodMoney periodMoney = mData.getPeriodMoney(index);
                    mController.onPeriodClick(periodMoney);
                }
            }
        }
    }

    public interface Controller {

        void onPeriodClick(PeriodMoney periodMoney);
    }
}