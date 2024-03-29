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

package com.oriondev.Viti.storage.cache;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DucTien on 13/10/2019.
 */
public class TypefaceCache {

    public static final String ROBOTO_MEDIUM = "Roboto-Medium";
    public static final String ROBOTO_REGULAR = "Roboto-Regular";

    private static final Map<String, Typeface> mCache = new HashMap<>();

    public static Typeface get(Context context, String name) {
        synchronized(mCache){
            if(!mCache.containsKey(name)) {
                String path = String.format("fonts/%s.ttf", name);
                Typeface typeface = null;
                try {
                    typeface = Typeface.createFromAsset(context.getAssets(), path);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                if (typeface != null) {
                    mCache.put(name, typeface);
                }
                return typeface;
            }
            return mCache.get(name);
        }
    }

    public static void apply(TextView textView, String name) {
        Typeface typeface = get(textView.getContext(), name);
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
    }
}