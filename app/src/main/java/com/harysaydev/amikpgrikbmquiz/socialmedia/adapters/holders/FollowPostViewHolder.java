package com.harysaydev.amikpgrikbmquiz.socialmedia.adapters.holders;

import android.view.View;

import com.harysaydev.amikpgrikbmquiz.socialmedia.main.base.BaseActivity;
import com.harysaydev.amikpgrikbmquiz.socialmedia.managers.listeners.OnPostChangedListener;
import com.harysaydev.amikpgrikbmquiz.socialmedia.model.FollowingPost;
import com.harysaydev.amikpgrikbmquiz.socialmedia.model.Post;
import com.harysaydev.amikpgrikbmquiz.socialmedia.utils.LogUtil;

/**
 * Created by Alexey on 22.05.18.
 */
public class FollowPostViewHolder extends PostViewHolder {


    public FollowPostViewHolder(View view, OnClickListener onClickListener, BaseActivity activity) {
        super(view, onClickListener, activity);
    }

    public FollowPostViewHolder(View view, OnClickListener onClickListener, BaseActivity activity, boolean isAuthorNeeded) {
        super(view, onClickListener, activity, isAuthorNeeded);
    }

    public void bindData(FollowingPost followingPost) {
        postManager.getSinglePostValue(followingPost.getPostId(), new OnPostChangedListener() {
            @Override
            public void onObjectChanged(Post obj) {
                bindData(obj);
            }

            @Override
            public void onError(String errorText) {
                LogUtil.logError(TAG, "bindData", new RuntimeException(errorText));
            }
        });
    }

}
