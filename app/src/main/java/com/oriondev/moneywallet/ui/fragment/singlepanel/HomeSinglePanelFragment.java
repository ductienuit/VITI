package com.oriondev.moneywallet.ui.fragment.singlepanel;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.oriondev.Viti.R;
import com.oriondev.Viti.dev.R;
import com.oriondev.Viti.model.OverviewData;
import com.oriondev.Viti.model.OverviewSetting;
import com.oriondev.Viti.model.PeriodMoney;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeSinglePanelFragment extends com.oriondev.Viti.ui.fragment.base.SinglePanelFragment implements com.oriondev.Viti.picker.OverviewSettingPicker.Controller, LoaderManager.LoaderCallbacks<com.oriondev.Viti.model.OverviewData>, com.oriondev.Viti.ui.adapter.recycler.OverviewItemAdapter.Controller, com.oriondev.Viti.storage.preference.CurrentWalletController {
    private static final int LOADER_HOME_DATA = 34848;

    private static final String TAG_SETTING_PICKER = "OverviewSinglePanelFragment::Tag::OverviewSettingPicker";

    private com.oriondev.Viti.ui.adapter.pager.OverviewChartViewPagerAdapter mViewPagerAdapter;
    private com.oriondev.Viti.ui.adapter.recycler.OverviewItemAdapter mRecyclerViewAdapter;

    private com.oriondev.Viti.picker.OverviewSettingPicker mOverviewSettingPicker;

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreatePanelView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_panel_overview, parent, true);
        ViewPager viewPager = view.findViewById(R.id.chart_view_pager);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // initialize adapters
        mViewPagerAdapter = new com.oriondev.Viti.ui.adapter.pager.OverviewChartViewPagerAdapter();
        mRecyclerViewAdapter = new com.oriondev.Viti.ui.adapter.recycler.OverviewItemAdapter(this);
        // attach adapters
        viewPager.setAdapter(mViewPagerAdapter);
        recyclerView.setAdapter(mRecyclerViewAdapter);
        // initialize picker
        FragmentManager fragmentManager = getChildFragmentManager();
        mOverviewSettingPicker = com.oriondev.Viti.picker.OverviewSettingPicker.createPicker(fragmentManager, TAG_SETTING_PICKER);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBroadcastReceiver = com.oriondev.Viti.storage.preference.PreferenceManager.registerCurrentWalletObserver(context, this);
    }

    @Override
    public void onDetach() {
        com.oriondev.Viti.storage.preference.PreferenceManager.unregisterCurrentWalletObserver(getActivity(), mBroadcastReceiver);
        super.onDetach();
    }
    @Override
    protected int getTitleRes() {
        return R.string.menu_overview;
    }

    @MenuRes
    protected int onInflateMenu() {
        return R.menu.menu_overview_fragment;
    }

    @Override
    protected boolean isFloatingActionButtonEnabled() {
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.advanced_settings) {
            mOverviewSettingPicker.showPicker();
        }
        return false;
    }

    @Override
    public void onOverviewSettingChanged(String tag, OverviewSetting overviewSetting) {
        getLoaderManager().restartLoader(LOADER_HOME_DATA, null, this);
    }

    @NonNull
    @Override
    public Loader<OverviewData> onCreateLoader(int id, Bundle args) {
        OverviewSetting setting = mOverviewSettingPicker.getCurrentSettings();
        return new com.oriondev.Viti.background.OverviewDataLoader(getActivity(), setting);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<OverviewData> loader, OverviewData data) {
        mViewPagerAdapter.setData(data);
        mRecyclerViewAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<OverviewData> loader) {
        // nothing to release
    }

    @Override
    public void onPeriodClick(PeriodMoney periodMoney) {
        Intent intent = new Intent(getActivity(), com.oriondev.Viti.ui.activity.PeriodDetailActivity.class);
        intent.putExtra(com.oriondev.Viti.ui.activity.PeriodDetailActivity.START_DATE, periodMoney.getStartDate());
        intent.putExtra(com.oriondev.Viti.ui.activity.PeriodDetailActivity.END_DATE, periodMoney.getEndDate());
        startActivity(intent);
    }

    @Override
    public void onCurrentWalletChanged(long walletId) {
        getLoaderManager().restartLoader(LOADER_HOME_DATA, null, this);
    }
}
