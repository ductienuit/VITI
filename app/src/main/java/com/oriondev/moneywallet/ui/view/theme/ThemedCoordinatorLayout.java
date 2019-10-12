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
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import com.oriondev.Viti.R;

/**
 * Created by DucTien on 24/08/19.
 */
public class ThemedCoordinatorLayout extends CoordinatorLayout implements ThemeEngine.ThemeConsumer {

    private BackgroundColor mBackgroundColor;

    public ThemedCoordinatorLayout(Context context) {
        super(context);
        initialize(context, null);
    }

    public ThemedCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public ThemedCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThemedCoordinatorLayout, 0, 0);
        try {
            mBackgroundColor = BackgroundColor.fromValue(typedArray.getInt(R.styleable.ThemedCoordinatorLayout_theme_backgroundColor, 0));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        if (mBackgroundColor != null) {
            int background = mBackgroundColor.getColor(theme);
            setBackgroundColor(background);
        }
    }
}