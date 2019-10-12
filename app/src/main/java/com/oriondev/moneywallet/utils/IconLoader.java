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

package com.oriondev.Viti.utils;

import android.widget.ImageView;

import com.oriondev.Viti.model.ColorIcon;
import com.oriondev.Viti.model.Icon;
import com.oriondev.Viti.model.VectorIcon;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DucTien on 27/01/19.
 */

public class IconLoader {

    public static final Icon UNKNOWN = new ColorIcon("#FFC107", "?");

    public static Icon parse(String icon) {
        if (icon != null) {
            try {
                JSONObject object = new JSONObject(icon);
                switch (Icon.getType(object)) {
                    case RESOURCE:
                        return new VectorIcon(object);
                    case COLOR:
                        return new ColorIcon(object);
                }
            } catch (JSONException e) {
                // TODO keep track?
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void parseAndLoad(String encoded, ImageView imageView) {
        loadInto(parse(encoded), imageView);
    }

    public static void parseAndLoad(String encoded, Icon fallback, ImageView imageView) {
        loadInto(parse(encoded), fallback, imageView);
    }

    public static void loadInto(Icon icon, ImageView imageView) {
        loadInto(icon, UNKNOWN, imageView);
    }

    public static void loadInto(Icon icon, Icon fallback, ImageView imageView) {
        if (icon == null || !icon.apply(imageView)) {
            if (fallback != null) {
                fallback.apply(imageView);
            }
        }
    }
}