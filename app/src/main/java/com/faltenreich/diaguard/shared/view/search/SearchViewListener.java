package com.faltenreich.diaguard.shared.view.search;

public interface SearchViewListener {
    void onQueryChanged(String query);
    void onQueryClosed();
}
