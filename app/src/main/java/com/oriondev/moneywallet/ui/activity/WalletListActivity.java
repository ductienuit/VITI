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

import com.oriondev.Viti.ui.activity.base.MultiPanelActivity;
import com.oriondev.Viti.ui.fragment.base.MultiPanelFragment;
import com.oriondev.Viti.ui.fragment.multipanel.WalletMultiPanelFragment;

/**
 * Created by DucTien on 17/01/19.
 */
public class WalletListActivity extends MultiPanelActivity {

    private static final String TAG_FRAGMENT_WALLET_LIST = "WalletListActivity::WalletMultiPanelFragment";

    @Override
    protected MultiPanelFragment onCreateMultiPanelFragment() {
        return new WalletMultiPanelFragment();
    }

    @Override
    protected String getMultiPanelFragmentTag() {
        return TAG_FRAGMENT_WALLET_LIST;
    }
}