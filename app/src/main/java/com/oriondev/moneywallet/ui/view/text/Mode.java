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

/**
 * Created by DucTien on 01/05/19.
 */
public enum Mode {

    STANDARD(0), FLOATING_LABEL(1);

    /*package-local*/ int mMode;

    Mode(int mode) {
        mMode = mode;
    }

    public Mode getMode() {
        return getMode(mMode);
    }

    public static Mode getMode(int value) {
        switch (value) {
            case 0:
                return STANDARD;
            case 1:
                return FLOATING_LABEL;
        }
        return null;
    }
}