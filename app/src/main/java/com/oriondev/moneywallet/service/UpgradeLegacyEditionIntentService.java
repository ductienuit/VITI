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

package com.oriondev.Viti.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.oriondev.Viti.broadcast.LocalAction;
import com.oriondev.Viti.broadcast.RecurrenceBroadcastReceiver;
import com.oriondev.Viti.storage.database.DataContentProvider;
import com.oriondev.Viti.storage.database.LegacyEditionImporter;
import com.oriondev.Viti.storage.preference.PreferenceManager;
import com.oriondev.Viti.utils.CurrencyManager;

/**
 * This service is used by the LauncherActivity when a legacy database is detected at startup.
 * The goal is to correctly import all the data coming from the old database and shared preferences
 * into the new data structures.
 */
public class UpgradeLegacyEditionIntentService extends IntentService {

    public static final String ERROR_MESSAGE = "UpgradeLegacyEditionIntentService::ErrorMessage";

    private final LocalBroadcastManager mBroadcastManager;

    public static boolean isLegacyEditionDetected(Context context) {
        // check if exists a legacy database to import
        return context.getDatabasePath(LegacyEditionImporter.DATABASE_NAME).exists();
    }

    public UpgradeLegacyEditionIntentService() {
        super("UpgradeLegacyEditionIntentService");
        mBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        notifyServiceStarted();
        Exception exception = null;
        try {
            LegacyEditionImporter importer = new LegacyEditionImporter(this);
            importer.importDatabase();
            importer.importAttachments();
            importer.importPreferences();
        } catch (Exception e) {
            exception = e;
        }
        if (exception != null) {
            notifyServiceFailed(exception.getMessage());
        } else {
            DataContentProvider.notifyDatabaseIsChanged(this);
            CurrencyManager.invalidateCache(this);
            PreferenceManager.setLastTimeDataIsChanged(0L);
            RecurrenceBroadcastReceiver.scheduleRecurrenceTask(this);
            notifyServiceFinished();
        }
    }

    private void notifyServiceStarted() {
        Intent intent = new Intent(LocalAction.ACTION_LEGACY_EDITION_UPGRADE_STARTED);
        mBroadcastManager.sendBroadcast(intent);
    }

    private void notifyServiceFailed(String message) {
        Intent intent = new Intent(LocalAction.ACTION_LEGACY_EDITION_UPGRADE_FAILED);
        intent.putExtra(ERROR_MESSAGE, message);
        mBroadcastManager.sendBroadcast(intent);
    }

    private void notifyServiceFinished() {
        Intent intent = new Intent(LocalAction.ACTION_LEGACY_EDITION_UPGRADE_FINISHED);
        mBroadcastManager.sendBroadcast(intent);
    }
}