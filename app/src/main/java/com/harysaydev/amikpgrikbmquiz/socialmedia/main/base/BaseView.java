/*
 * Copyright 2018 Rozdoum
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

package com.harysaydev.amikpgrikbmquiz.socialmedia.main.base;

import android.content.DialogInterface;
import androidx.annotation.StringRes;
import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Alexey on 03.05.18.
 */

public interface BaseView extends MvpView {

    void showProgress();

    void showProgress(int message);

    void hideProgress();

    void showSnackBar(String message);

    void showSnackBar(int message);

    void showSnackBar(View view, int messageId);

    void showToast(@StringRes int messageId);

    void showToast(String message);

    void showWarningDialog(int messageId);

    void showWarningDialog(String message);

    void showNotCancelableWarningDialog(String message);

    void showWarningDialog(@StringRes int messageId, DialogInterface.OnClickListener listener);

    void showWarningDialog(String message, DialogInterface.OnClickListener listener);

    void startLoginActivity();

    void hideKeyboard();

    void finish();
}
