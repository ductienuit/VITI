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

package com.oriondev.Viti.ui.preference;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;

import com.oriondev.Viti.R;
import com.oriondev.Viti.ui.view.CircleView;

/**
 * Created by DucTien on 13/10/2019.
 */
public class ColorPreference extends ThemedPreference {

    private int mColor;

    public ColorPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public ColorPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ColorPreference(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        setWidgetLayoutResource(R.layout.layout_preference_color_picker);
        mColor = Color.BLACK;
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        CircleView circleView = (CircleView) holder.findViewById(R.id.circle_view);
        if (circleView != null) {
            circleView.setColor(mColor);
        }
    }

    public void setColor(int newColor) {
        mColor = newColor;
        notifyChanged();
    }
}