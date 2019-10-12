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

package com.oriondev.Viti;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.oriondev.Viti.broadcast.AutoBackupBroadcastReceiver;
import com.oriondev.Viti.broadcast.DailyBroadcastReceiver;
import com.oriondev.Viti.broadcast.RecurrenceBroadcastReceiver;
import com.oriondev.Viti.storage.preference.BackendManager;
import com.oriondev.Viti.storage.preference.PreferenceManager;
import com.oriondev.Viti.ui.notification.NotificationContract;
import com.oriondev.Viti.ui.view.theme.ThemeEngine;
import com.oriondev.Viti.utils.CurrencyManager;

/**
 * Created by DucTien on 17/01/19.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.initialize(this);
        BackendManager.initialize(this);
        ThemeEngine.initialize(this);
        CurrencyManager.initialize(this);
        NotificationContract.initializeNotificationChannels(this);
        initializeScheduledTimers();
    }

    private void initializeScheduledTimers() {
        // The application may be killed by the OS when resources are needed or by the user for
        // every kind of reasons. When the application is killed all the scheduled operations are
        // canceled by the OS. This is the best place where all those things can be scheduled again.
        DailyBroadcastReceiver.scheduleDailyNotification(this);
        RecurrenceBroadcastReceiver.scheduleRecurrenceTask(this);
        AutoBackupBroadcastReceiver.scheduleAutoBackupTask(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}