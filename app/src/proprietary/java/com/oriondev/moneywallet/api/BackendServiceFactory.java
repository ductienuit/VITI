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

package com.oriondev.Viti.api;

import android.content.Context;

import com.oriondev.Viti.R;
import com.oriondev.Viti.api.disk.DiskBackendService;
import com.oriondev.Viti.api.disk.DiskBackendServiceAPI;
import com.oriondev.Viti.api.dropbox.DropboxBackendService;
import com.oriondev.Viti.api.dropbox.DropboxBackendServiceAPI;
import com.oriondev.Viti.api.google.GoogleDriveBackendService;
import com.oriondev.Viti.api.google.GoogleDriveBackendServiceAPI;
import com.oriondev.Viti.model.BackupService;
import com.oriondev.Viti.model.DropBoxFile;
import com.oriondev.Viti.model.GoogleDriveFile;
import com.oriondev.Viti.model.IFile;
import com.oriondev.Viti.model.LocalFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DucTien on 21/10/19.
 */
public class BackendServiceFactory {

    public static final String SERVICE_ID_DROPBOX = "dropbox";
    public static final String SERVICE_ID_GOOGLE_DRIVE = "google_drive";
    public static final String SERVICE_ID_EXTERNAL_MEMORY = "external_memory";

    public static AbstractBackendServiceDelegate getServiceById(String backendId, AbstractBackendServiceDelegate.BackendServiceStatusListener listener) {
        switch (backendId) {
            case SERVICE_ID_DROPBOX:
                return new DropboxBackendService(listener);
            case SERVICE_ID_GOOGLE_DRIVE:
                return new GoogleDriveBackendService(listener);
            case SERVICE_ID_EXTERNAL_MEMORY:
                return new DiskBackendService(listener);
        }
        return null;
    }

    public static IBackendServiceAPI getServiceAPIById(Context context, String backendId) throws BackendException {
        switch (backendId) {
            case SERVICE_ID_DROPBOX:
                return new DropboxBackendServiceAPI(context);
            case SERVICE_ID_GOOGLE_DRIVE:
                return new GoogleDriveBackendServiceAPI(context);
            case SERVICE_ID_EXTERNAL_MEMORY:
                return new DiskBackendServiceAPI();
            default:
                throw new BackendException("Invalid backend");
        }
    }

    public static List<BackupService> getBackupServices() {
        List<BackupService> services = new ArrayList<>();
        services.add(new BackupService(SERVICE_ID_DROPBOX, R.drawable.ic_dropbox_24dp, R.string.service_backup_drop_box));
        services.add(new BackupService(SERVICE_ID_GOOGLE_DRIVE, R.drawable.ic_google_drive_24dp, R.string.service_backup_google_drive));
        services.add(new BackupService(SERVICE_ID_EXTERNAL_MEMORY, R.drawable.ic_sd_24dp, R.string.service_backup_external_memory));
        return services;
    }

    public static IFile getFile(String backendId, String encoded) {
        if (encoded != null) {
            switch (backendId) {
                case SERVICE_ID_DROPBOX:
                    return new DropBoxFile(encoded);
                case SERVICE_ID_GOOGLE_DRIVE:
                    return new GoogleDriveFile(encoded);
                case SERVICE_ID_EXTERNAL_MEMORY:
                    return new LocalFile(encoded);
            }
        }
        return null;
    }
}