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
import android.util.AttributeSet;

import com.oriondev.Viti.ui.view.CardButton;

/**
 * Created by DucTien on 20/08/19.
 */
public class ThemedCardButton extends CardButton implements ThemeEngine.ThemeConsumer {

    public ThemedCardButton(Context context) {
        super(context);
    }

    public ThemedCardButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedCardButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        setTextColor(theme.getColorAccent());
    }
}