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

package com.oriondev.Viti.ui.view.text;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * Created by DucTien on 30/01/19.
 */

public class NonEmptyTextValidator implements Validator {

    private final String mMessage;
    private final boolean mAuto;

    public NonEmptyTextValidator(String message) {
        this(message, false);
    }

    public NonEmptyTextValidator(String message, boolean auto) {
        mMessage = message;
        mAuto = auto;
    }

    public NonEmptyTextValidator(Context context, @StringRes int resId) {
        this(context, resId, false);
    }

    public NonEmptyTextValidator(Context context, @StringRes int resId, boolean auto) {
        mMessage = context.getString(resId);
        mAuto = auto;
    }

    @NonNull
    @Override
    public String getErrorMessage() {
        return mMessage;
    }

    @Override
    public boolean isValid(@NonNull CharSequence charSequence) {
        return !charSequence.toString().trim().isEmpty();
    }

    @Override
    public boolean autoValidate() {
        return mAuto;
    }
}
