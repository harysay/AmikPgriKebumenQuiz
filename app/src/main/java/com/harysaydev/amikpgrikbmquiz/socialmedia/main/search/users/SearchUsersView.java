package com.harysaydev.amikpgrikbmquiz.socialmedia.main.search.users;

import com.harysaydev.amikpgrikbmquiz.socialmedia.main.base.BaseFragmentView;
import com.harysaydev.amikpgrikbmquiz.socialmedia.model.Profile;

import java.util.List;

/**
 * Created by Alexey on 08.06.18.
 */
public interface SearchUsersView extends BaseFragmentView {
    void onSearchResultsReady(List<Profile> profiles);

    void showLocalProgress();

    void hideLocalProgress();

    void showEmptyListLayout();

    void updateSelectedItem();
}
