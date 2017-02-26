package com.schanz.jaxsciencefestival.presenter;

import com.schanz.jaxsciencefestival.ai.ChatCompanion;
import com.schanz.jaxsciencefestival.model.DataBlob;

public interface MainView {

    void showItems(DataBlob model);

    // TODO: How do I implement this for a generic type?
    void showDetails(ChatCompanion model);

    void showLoading();

    void showEmpty();
}
