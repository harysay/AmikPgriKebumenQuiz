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

package com.harysaydev.amikpgrikbmquiz.socialmedia.main.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.harysaydev.amikpgrikbmquiz.R;
import com.harysaydev.amikpgrikbmquiz.socialmedia.main.base.BaseActivity;
import com.harysaydev.amikpgrikbmquiz.socialmedia.main.editProfile.createProfile.CreateProfileActivity;
import com.harysaydev.amikpgrikbmquiz.socialmedia.utils.GoogleApiHelper;
import com.harysaydev.amikpgrikbmquiz.socialmedia.utils.LogUtil;
import com.harysaydev.amikpgrikbmquiz.socialmedia.utils.LogoutHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int SIGN_IN_GOOGLE = 9001;
    public static final int LOGIN_REQUEST_CODE = 10001;

    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;

    private CallbackManager mCallbackManager;
    private String profilePhotoUrlLarge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.harysaydev.amikpgrikbmquiz",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        FacebookSdk.sdkInitialize(getApplicationContext()); //Logging App Activations
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initGoogleSignIn();
        initFirebaseAuth();
        initFacebookSignIn();
    }

    private void initGoogleSignIn() {
        mGoogleApiClient = GoogleApiHelper.createGoogleApiClient(this);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.googleSignInButton).setOnClickListener(view -> presenter.onGoogleSignInClick());
    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            LogoutHelper.signOut(mGoogleApiClient, this);
        }

        mAuthListener = firebaseAuth -> {
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // Profile is signed in
                sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);//reference to shared preference file
                LogUtil.logDebug(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                presenter.checkIsProfileExist(sharedPreferences.getString("phone",""));
                //presenter.checkIsProfileExist(user.getUid());
                setResult(RESULT_OK);
            } else {
                // Profile is signed out
                LogUtil.logDebug(TAG, "onAuthStateChanged:signed_out");
            }
        };
    }

    private void initFacebookSignIn() {
        mCallbackManager = CallbackManager.Factory.create(); //Now create a callbackManager to handle login responses by calling CallbackManager.Factory.create.
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LogUtil.logDebug(TAG, "facebook:onSuccess:" + loginResult);
                presenter.handleFacebookSignInResult(loginResult);
            }

            @Override
            public void onCancel() {
                LogUtil.logDebug(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                LogUtil.logError(TAG, "facebook:onError", error);
                showSnackBar(error.getMessage());
            }
        });

        findViewById(R.id.facebookSignInButton).setOnClickListener(v -> presenter.onFacebookSignInClick());
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        if (presenter == null) {
            return new LoginPresenter(this);
        }
        return presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        // ResultPerTest returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == SIGN_IN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            presenter.handleGoogleSignInResult(result);
        }
    }

    @Override
    public void startCreateProfileActivity() {
        Intent intent = new Intent(LoginActivity.this, CreateProfileActivity.class);
        intent.putExtra(CreateProfileActivity.LARGE_IMAGE_URL_EXTRA_KEY, profilePhotoUrlLarge);
        startActivity(intent);
    }

    @Override
    public void firebaseAuthWithCredentials(AuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    LogUtil.logDebug(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        presenter.handleAuthError(task);
                    }
                });
    }

    @Override
    public void setProfilePhotoUrl(String url) {
        profilePhotoUrlLarge = url;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        LogUtil.logDebug(TAG, "onConnectionFailed:" + connectionResult);
        showSnackBar(R.string.error_google_play_services);
        hideProgress();
    }

    @Override
    public void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, SIGN_IN_GOOGLE);
    }

    @Override
    public void signInWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
    }
}

