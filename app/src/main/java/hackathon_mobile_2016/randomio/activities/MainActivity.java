package hackathon_mobile_2016.randomio.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hackathon_mobile_2016.randomio.R;

public class MainActivity extends AppCompatActivity {
    String TAG="ductri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FirebaseAuth auth = FirebaseAuth.getInstance();
        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button buttonLoadMatch = (Button) findViewById(R.id.buttonLoadMatch);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Mainform.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WaitingRoom.class);
                intent.putExtra("roomId", "-KVFHhZrr2qZ1d-rNnDt");
                startActivity(intent);
            }
        });

        buttonLoadMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MatchActivity.class);
                intent.putExtra("isHost", "true");
                intent.putExtra("roomId", "-KVFHhZrr2qZ1d-rNnDt");
                intent.putExtra("team", "1");
                intent.putExtra("mode", "1");
                intent.putExtra("maxNumber", "10");
                intent.putExtra("playerId","1");
                intent.putExtra("playerName", "ductri");
                startActivity(intent);
            }
        });

        //login();
    }
//
//    private void login() {
//
//
//        //AppEventsLogger.activateApp(this);
//        mCallbackManager = CallbackManager.Factory.create();
//        LoginButton loginButton = (LoginButton) findViewById(R.id.button_facebook_login);
//        loginButton.setReadPermissions("email", "public_profile");
//
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
//                // ...
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//
//        });
//        //Get Firebase auth instance
//
//
//        if (auth.getCurrentUser() != null) {
//            //startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            //finish();
//            Log.i("ductri", "Login successful");
//        }
//
//
//
//
//        //authenticate user
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//            }
//        };
//    }

//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        auth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            auth.removeAuthStateListener(mAuthListener);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookAccessToken:" + token);
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        auth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "signInWithCredential", task.getException());
//                            //Toast.makeText(LoginActivity.this, "Authentication failed.",
//                            //        Toast.LENGTH_SHORT).show();
//                        }
//
//                        // ...
//                    }
//                });
//    }
}
