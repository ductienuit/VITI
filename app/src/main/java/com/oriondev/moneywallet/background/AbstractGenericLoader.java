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

package com.oriondev.Viti.background;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by DucTien on 12/10/2019.
 */
public abstract class AbstractGenericLoader<T> extends AsyncTaskLoader<T> {

    private T mGenericData;

    public AbstractGenericLoader(Context context) {
        super(context);
    }

    @Override
    public abstract T loadInBackground();

    /* Runs on the UI thread */
    @Override
    public void deliverResult(T genericData) {
        mGenericData = genericData;
        if (isStarted()) {
            super.deliverResult(genericData);
        }
    }

    /**
     * Starts an asynchronous load of the contacts list data. When the result is ready the callbacks
     * will be called on the UI thread. If a previous load has been completed and is still valid
     * the result may be passed to the callbacks immediately.
     * <p/>
     * Must be called from the UI thread
     */
    @Override
    protected void onStartLoading() {
        if (mGenericData != null) {
            deliverResult(mGenericData);
        }
        if (takeContentChanged() || mGenericData == null) {
            forceLoad();
        }
    }

    /**
     * Must be called from the UI thread
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        // Ensure the loader is stopped
        onStopLoading();
        mGenericData = null;
    }
}