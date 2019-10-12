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

package com.oriondev.Viti.storage.database.model;

/**
 * Created by DucTien on 27/10/19.
 */
public class Debt extends BaseItem {

    public Long mId;
    public int mType;
    public String mIcon;
    public String mDescription;
    public String mDate;
    public String mExpirationDate;
    public Long mWallet;
    public String mNote;
    public Long mPlace;
    public long mMoney;
    public boolean mArchived;
    public String mTag;
}