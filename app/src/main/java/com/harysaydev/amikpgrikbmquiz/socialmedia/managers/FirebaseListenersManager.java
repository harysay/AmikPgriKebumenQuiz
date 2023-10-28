/*
 * Copyright 2017 Rozdoum
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.harysaydev.amikpgrikbmquiz.socialmedia.managers;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.harysaydev.amikpgrikbmquiz.socialmedia.ApplicationHelper;
import com.harysaydev.amikpgrikbmquiz.socialmedia.utils.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.harysaydev.amikpgrikbmquiz.socialmedia.adapters.holders.PostViewHolder.TAG;

/**
 * Created by alexey on 19.12.16.
 */

public class FirebaseListenersManager {
    Map<Context, List<ValueEventListener>> activeListeners = new HashMap<>();
    DatabaseReference reference;
    DatabaseHelper databaseHelper = ApplicationHelper.getDatabaseHelper();

    void addListenerToMap(Context context, ValueEventListener valueEventListener) {
        if (activeListeners.containsKey(context)) {
            activeListeners.get(context).add(valueEventListener);
        } else {
            List<ValueEventListener> valueEventListeners = new ArrayList<>();
            valueEventListeners.add(valueEventListener);
            activeListeners.put(context, valueEventListeners);
        }
    }

    public void closeListeners(Context context) {

        if (activeListeners.containsKey(context)) {
            for (ValueEventListener listener : activeListeners.get(context)) {
                //databaseHelper.closeListener(listener);
                if (activeListeners.containsKey(listener)) {
                    activeListeners.get(listener);
                    reference.removeEventListener(listener);
                    activeListeners.remove(listener);
                    LogUtil.logDebug(TAG, "closeListener(), listener was removed: " + listener);
                } else {
                    LogUtil.logDebug(TAG, "closeListener(), listener not found :" + listener);
                }
            }
            activeListeners.remove(context);
        }
    }
}
