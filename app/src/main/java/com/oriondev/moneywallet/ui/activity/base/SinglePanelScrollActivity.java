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
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.oriondev.Viti.R;
import com.oriondev.Viti.utils.Utils;

/**
 * Created by DucTien on 13/10/2019.
 */
public abstract class SinglePanelScrollActivity extends SinglePanelActivity {

    protected void onInflateRootLayout() {
        // this layout has no floating action button in the root
        setContentView(R.layout.activity_single_panel_scroll);
    }

    protected void onConfigureRootLayout(Bundle savedInstanceState) {
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup headerContainer = findViewById(R.id.app_bar_container);
        ViewGroup panelContainer = Utils.findViewGroupByIds(this,
                R.id.primary_panel_container_frame_layout,
                R.id.primary_panel_container_card_view,
                R.id.primary_panel_container_linear_layout,
                R.id.primary_panel_container_coordinator_layout
        );
        onCreateHeaderView(inflater, headerContainer, savedInstanceState);
        onCreatePanelView(inflater, panelContainer, savedInstanceState);
    }

    protected abstract void onCreateHeaderView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);

    protected abstract void onCreatePanelView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);
}