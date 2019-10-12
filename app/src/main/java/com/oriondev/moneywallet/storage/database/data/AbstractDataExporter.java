package com.oriondev.Viti.storage.database.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.util.LongSparseArray;

import com.oriondev.Viti.model.Wallet;
import com.oriondev.Viti.storage.database.Contract;
import com.oriondev.Viti.utils.DateUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by DucTien on 13/10/2019.
 */

public abstract class AbstractDataExporter {

    public static final String COLUMN_EVENT = "column_event";
    public static final String COLUMN_PEOPLE = "column_people";
    public static final String COLUMN_PLACE = "column_place";
    public static final String COLUMN_NOTE = "column_note";

    private final Context mContext;
    private final LongSparseArray<String> mPeopleCache;

    public AbstractDataExporter(Context context, File folder) throws IOException {
        mContext = context;
        mPeopleCache = new LongSparseArray<>();
    }

    protected String getDefaultFileName(String extension) {
        String dateTimeString = DateUtils.getSQLDateTimeString(new Date());
        return "Viti_export_" + dateTimeString + extension;
    }

    public abstract boolean isMultiWalletSupported();

    public abstract String[] getColumns(boolean uniqueWallet, String[] optionalColumns);

    public abstract boolean shouldLoadPeople();

    public void cachePeople(Cursor cursor) {
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(Contract.Person.ID));
            String name = cursor.getString(cursor.getColumnIndex(Contract.Person.NAME));
            mPeopleCache.put(id, name);
        }
    }

    protected String getPersonName(long id) {
        return mPeopleCache.get(id);
    }

    protected Context getContext() {
        return mContext;
    }

    public abstract void exportData(Cursor cursor, String[] columns, Wallet... wallets) throws IOException;

    public abstract void close() throws IOException;

    public abstract File getOutputFile();

    public abstract String getResultType();
}