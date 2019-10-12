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
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.oriondev.Viti.ui.view.calendar.TimelineView;

/**
 * Created by DucTien on 13/10/2019.
 */
public class ThemedTimelineView extends TimelineView implements ThemeEngine.ThemeConsumer {

    public ThemedTimelineView(Context context) {
        super(context);
    }

    public ThemedTimelineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedTimelineView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        int textColor = theme.getTextColorPrimary();
        setDayLabelColor(textColor);
        setDateLabelColor(textColor);
        setDateLabelSelectedColor(theme.getColorAccent());
        EdgeGlowUtil.setEdgeGlowColor(this, theme.getColorPrimary(), null);
    }
}