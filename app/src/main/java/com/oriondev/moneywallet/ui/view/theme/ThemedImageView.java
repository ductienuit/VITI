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

package com.oriondev.Viti.ui.view.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.oriondev.Viti.R;

/**
 * Created by DucTien on 20/08/19.
 */
public class ThemedImageView extends AppCompatImageView implements ThemeEngine.ThemeConsumer {

    private BackgroundColor mBackgroundColor;

    public ThemedImageView(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public ThemedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public ThemedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThemedImageView, defStyleAttr, 0);
        try {
            mBackgroundColor = BackgroundColor.fromValue(typedArray.getInt(R.styleable.ThemedImageView_theme_backgroundColor, 0));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        if (mBackgroundColor != null) {
            int color = mBackgroundColor.getColor(theme);
            setColorFilter(theme.getBestIconColor(color), PorterDuff.Mode.SRC_IN);
        }
    }
}