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

package com.oriondev.Viti.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.oriondev.Viti.R;
import com.oriondev.Viti.broadcast.LocalAction;
import com.oriondev.Viti.service.UpgradeLegacyEditionIntentService;
import com.oriondev.Viti.storage.preference.PreferenceManager;
import com.oriondev.Viti.ui.activity.base.ThemedActivity;
import com.oriondev.Viti.ui.view.theme.ThemedDialog;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by DucTien on 13/10/2019.
 */
public class LauncherActivity extends ThemedActivity {

    private static final String SS_UPGRADE_ERROR = "LauncherActivity::SavedState::UpgradeLegacyEditionError";

    private static final int REQUEST_FIRST_START = 273;

    private String mUpgradeLegacyEditionError = null;

    private ProgressWheel mProgressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UpgradeLegacyEditionIntentService.isLegacyEditionDetected(this)) {
            setContentView(R.layout.activity_launcher_legacy_edition_upgrade);
            mProgressWheel = findViewById(R.id.progress_wheel);
            // prepare the broadcast receiver
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(LocalAction.ACTION_LEGACY_EDITION_UPGRADE_STARTED);
            intentFilter.addAction(LocalAction.ACTION_LEGACY_EDITION_UPGRADE_FINISHED);
            intentFilter.addAction(LocalAction.ACTION_LEGACY_EDITION_UPGRADE_FAILED);
            LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, intentFilter);
            // start the service
            if (savedInstanceState == null) {
                mProgressWheel.setVisibility(View.INVISIBLE);
                startService(new Intent(this, UpgradeLegacyEditionIntentService.class));
            } else {
                mUpgradeLegacyEditionError = savedInstanceState.getString(SS_UPGRADE_ERROR);
                showUpgradeLegacyEditionErrorMessage();
            }
        } else {
            if (!PreferenceManager.isFirstStartDone()) {
                setContentView(R.layout.activity_launcher_first_start);
                Button firstStartButton = findViewById(R.id.first_start_button);
                Button restoreBackupButton = findViewById(R.id.restore_backup_button);
                firstStartButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LauncherActivity.this, TutorialActivity.class);
                        startActivityForResult(intent, REQUEST_FIRST_START);
                    }

                });
                restoreBackupButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LauncherActivity.this, BackupListActivity.class);
                        intent.putExtra(BackupListActivity.BACKUP_MODE, BackupListActivity.RESTORE_ONLY);
                        startActivityForResult(intent, REQUEST_FIRST_START);
                    }

                });
            } else {
                startMainActivity();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SS_UPGRADE_ERROR, mUpgradeLegacyEditionError);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showUpgradeLegacyEditionErrorMessage() {
        if (mProgressWheel != null) {
            mProgressWheel.setVisibility(View.INVISIBLE);
        }
        ThemedDialog.buildMaterialDialog(LauncherActivity.this)
                .title(R.string.title_failed)
                .content(R.string.message_error_legacy_upgrade_failed, mUpgradeLegacyEditionError)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onAny(new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startMainActivity();
                    }

                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FIRST_START) {
            if (resultCode == RESULT_OK) {
                PreferenceManager.setIsFirstStartDone(true);
                startMainActivity();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction() != null) {
                switch (intent.getAction()) {
                    case LocalAction.ACTION_LEGACY_EDITION_UPGRADE_STARTED:
                        if (mProgressWheel != null) {
                            mProgressWheel.setVisibility(View.VISIBLE);
                        }
                        break;
                    case LocalAction.ACTION_LEGACY_EDITION_UPGRADE_FINISHED:
                        startMainActivity();
                        break;
                    case LocalAction.ACTION_LEGACY_EDITION_UPGRADE_FAILED:
                        mUpgradeLegacyEditionError = intent.getStringExtra(UpgradeLegacyEditionIntentService.ERROR_MESSAGE);
                        showUpgradeLegacyEditionErrorMessage();
                        break;
                }
            }
        }

    };
}