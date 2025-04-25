package com.faltenreich.diaguard.shared.data.database.dao;

import android.util.Log;

import com.faltenreich.diaguard.shared.data.database.entity.Entry;
import com.faltenreich.diaguard.shared.data.database.entity.EntryTag;
import com.faltenreich.diaguard.shared.data.database.entity.Tag;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntryTagDao extends BaseDao<EntryTag> {

    private static final String TAG = EntryTagDao.class.getSimpleName();

    private static EntryTagDao instance;

    public static EntryTagDao getInstance() {
        if (instance == null) {
            instance = new EntryTagDao();
        }
        return instance;
    }

    private EntryTagDao() {
        super(EntryTag.class);
    }

    public List<EntryTag> getAll(Entry entry) {
        try {
            return getQueryBuilder()
                .orderBy(EntryTag.Column.UPDATED_AT, false)
                .where().eq(EntryTag.Column.ENTRY, entry)
                .query();
        } catch (SQLException exception) {
            Log.e(TAG, exception.toString());
            return new ArrayList<>();
        }
    }

    public int deleteAll(Entry entry) {
        try {
            DeleteBuilder<EntryTag, Long> builder = getDeleteBuilder();
            builder.where().eq(EntryTag.Column.ENTRY, entry);
            return builder.delete();
        } catch (SQLException exception) {
            Log.e(TAG, exception.toString());
            return 0;
        }
    }

    public List<EntryTag> getAll(Tag tag) {
        try {
            return getQueryBuilder()
                .orderBy(EntryTag.Column.UPDATED_AT, false)
                .where().eq(EntryTag.Column.TAG, tag)
                .query();
        } catch (SQLException exception) {
            Log.e(TAG, exception.toString());
            return new ArrayList<>();
        }
    }

    public long count(Tag tag) {
        try {
            return getQueryBuilder()
                .orderBy(EntryTag.Column.UPDATED_AT, false)
                .where().eq(EntryTag.Column.TAG, tag)
                .countOf();
        } catch (SQLException exception) {
            Log.e(TAG, exception.toString());
            return 0;
        }
    }
}
