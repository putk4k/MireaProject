package ru.mirea.vikulov.mireaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.vikulov.mireaproject.databinding.ActivityFirebaseBinding;

public class firebase extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityFirebaseBinding binding;
    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirebaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
// [START initialize_auth] Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        binding.inB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(binding.editEmail.getText());
                String password = String.valueOf(binding.editPas.getText());
                signIn(email, password, view);
            }
        });
        binding.createB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(binding.editEmail.getText());
                String password = String.valueOf(binding.editPas.getText());
                createAccount(email, password, view);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {

        } else {
            binding.textEmail.setText(R.string.signed_out);
            binding.textUI.setText(null);
            binding.editEmail.setVisibility(View.VISIBLE);
            binding.editPas.setVisibility(View.VISIBLE);
            binding.inB.setVisibility(View.VISIBLE);
            binding.createB.setVisibility(View.VISIBLE);
        }
    }

    private void createAccount(String email, String password, View view) {
        Log.d(TAG, "createAccount:" + email);
//        if (!validateForm()) {
//            return;
//        }
// [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
// Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            loadMain(view);
                        } else {
// If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure",
                                    task.getException());
                            Toast.makeText(firebase.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
// [END create_user_with_email]
    }


    private void signIn(String email, String password, View view) {
        Log.d(TAG, "signIn:" + email);
// [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
// Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            loadMain(view);
                        } else {
// If sign in fails, display a message to the user.

                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            Toast.makeText(firebase.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
// [START_EXCLUDE]

                        if (!task.isSuccessful()) {

                            binding.textEmail.setText(R.string.auth_failed);
                        }

// [END_EXCLUDE]

                    }
                });
// [END sign_in_with_email]
    }

    public void loadMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}