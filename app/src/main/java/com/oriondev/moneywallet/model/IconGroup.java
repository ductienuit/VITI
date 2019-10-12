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

package com.oriondev.Viti.model;

import java.util.List;

/**
 * Created by DucTien on 13/10/2019.
 */
public class IconGroup {

    private final String mGroupName;
    private final List<Icon> mGroupIcons;

    public IconGroup(String name, List<Icon> iconList) {
        mGroupName = name;
        mGroupIcons = iconList;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public List<Icon> getGroupIcons() {
        return mGroupIcons;
    }

    public int size() {
        return mGroupIcons.size();
    }
}