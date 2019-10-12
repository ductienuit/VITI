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

package com.oriondev.Viti.storage.database;

import android.content.Context;

import com.oriondev.Viti.R;
import com.oriondev.Viti.model.ColorIcon;
import com.oriondev.Viti.picker.IconPicker;
import com.oriondev.Viti.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DucTien on 01/03/19.
 */
/*package-local*/ class SystemCategory {

    /*package-local*/ static final List<SystemCategory> mSystemCategories;

    static {
        mSystemCategories = new ArrayList<>();
        mSystemCategories.add(new SystemCategory(R.string.system_category_transfer, Schema.CategoryTag.TRANSFER, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_transfer_tax, Schema.CategoryTag.TRANSFER_TAX, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_debt, Schema.CategoryTag.DEBT, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_credit, Schema.CategoryTag.CREDIT, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_debt_paid, Schema.CategoryTag.PAID_DEBT, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_credit_paid, Schema.CategoryTag.PAID_CREDIT, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_generic_tax, Schema.CategoryTag.TAX, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_saving_in, Schema.CategoryTag.SAVING_DEPOSIT, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_saving_out, Schema.CategoryTag.SAVING_WITHDRAW, Utils.getRandomMDColor()));
    }

    private final int mName;
    private final String mTag;
    private final String mColor;

    private SystemCategory(int name, String tag, int color) {
        mName = name;
        mTag = tag;
        mColor = Utils.getHexColor(color);
    }

    /*package-local*/ String getName(Context context) {
        return context.getString(mName);
    }

    /*package-local*/ String getTag() {
        return mTag;
    }

    /*package-local*/ String getIcon(Context context) {
        String source = IconPicker.getColorIconString(context.getString(mName));
        return new ColorIcon(mColor, source).toString();
    }

    /*package-local*/ String getUUID() {
        return "system-uuid-" + mTag;
    }
}