package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mDisplayName, mEmail, mPassword, mConfirm;
    private Button mCreateBtn;
    private Toolbar mToolBar;
    private RadioGroup mRadioGroup;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mToolBar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegProgress = new ProgressDialog(this);



        mDisplayName = findViewById(R.id.reg_display_name);
        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
        mConfirm = findViewById(R.id.login_confirm_password);
        mCreateBtn = findViewById(R.id.login_btn);
        mRadioGroup =  findViewById(R.id.reg_radio_group);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selected = mRadioGroup.getCheckedRadioButtonId();
                RadioButton mRadioButton = findViewById(selected);

                String display_name = mDisplayName.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();
                String confirm = mConfirm.getEditText().getText().toString();
                String sex = mRadioButton.getText().toString();

                if(!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(confirm) || sex != null)
                {
                    if(password.equals(confirm)) {
                        mRegProgress.setTitle("Registering User");
                        mRegProgress.setMessage("Please wait while we create your account");
                        mRegProgress.setCanceledOnTouchOutside(false);
                        mRegProgress.show();

                        registerUser(display_name, email, password, sex);
                    }else
                        Toast.makeText(RegisterActivity.this, "Password must be the same in confirm form.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void registerUser(final String display_name, final String email, final String password, final String sex) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = currentUser.getUid();


                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(sex).child(uid);

                    User user = new User(display_name, "Default description", "default", "default", uid);

                    mDatabase.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                mRegProgress.dismiss();

                                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }
                            else
                            {
                                mRegProgress.hide();
                                Toast.makeText(RegisterActivity.this, "Cannot send your data to our database. Please try again later.", Toast.LENGTH_LONG).show();
                            }

                        }
                    });


                }else {
                    mRegProgress.hide();
                    Toast.makeText(RegisterActivity.this, "Cannot sign in. Please check your form and try again.", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
