package com.example.momandbaby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.spec.ECField;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextRegisterFullName,editTextRegisterPassword,editTextRegisterConfirmPassword,
            editTextRegisterEmail,editTextRegisterPhone,editTextRegisterAddress;
    private ProgressBar progressBar;
    private static final String TAG ="SigUpActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("Register");
        Toast.makeText(SignUpActivity.this,"Your can register now",Toast.LENGTH_LONG).show();
        editTextRegisterFullName = findViewById(R.id.edt_register_name);
        editTextRegisterPassword = findViewById(R.id.edt_register_pass);
        editTextRegisterConfirmPassword = findViewById(R.id.edt_register_confirmpass);
        editTextRegisterEmail = findViewById(R.id.edt_register_email);
        editTextRegisterPhone = findViewById(R.id.edt_register_phone);
        editTextRegisterAddress = findViewById(R.id.edt_register_address);
        progressBar = findViewById(R.id.progressBar);


        Button buttonRegister = findViewById(R.id.btn_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = editTextRegisterFullName.getText().toString();
                String textPass = editTextRegisterPassword.getText().toString();
                String textConfirmPass = editTextRegisterConfirmPassword.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textPhone = editTextRegisterPhone.getText().toString();
                String textAddress = editTextRegisterAddress.getText().toString();

                if (TextUtils.isEmpty(textName)){
                    Toast.makeText(SignUpActivity.this,"Vui lòng nhập tên",Toast.LENGTH_LONG).show();
                    editTextRegisterFullName.setError("Tên không được để trống");
                    editTextRegisterFullName.requestFocus();
                }else if (TextUtils.isEmpty(textPass)){
                    Toast.makeText(SignUpActivity.this,"Vui lòng nhập mật khẩu",Toast.LENGTH_LONG).show();
                    editTextRegisterPassword.setError("Mật khẩu không được để trống");
                    editTextRegisterPassword.requestFocus();
                } else if (textPass.length()<8) {
                    Toast.makeText(SignUpActivity.this,"Mật khẩu phải dài hơn 8 kí tự",Toast.LENGTH_LONG).show();
                    editTextRegisterPassword.setError("Mật khẩu chưa đủ mạnh!");
                    editTextRegisterPassword.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPass)) {
                    Toast.makeText(SignUpActivity.this,"Vui lòng nhập lại mật khẩu!",Toast.LENGTH_LONG).show();
                    editTextRegisterConfirmPassword.setError("Mật khẩu không được để trống!");
                    editTextRegisterConfirmPassword.requestFocus();
                } else if (!textPass.equals(textConfirmPass)) {
                    Toast.makeText(SignUpActivity.this,"Vui lòng nhập trùng mật khẩu!",Toast.LENGTH_LONG).show();
                    editTextRegisterConfirmPassword.setError("Xác nhận mật khẩu không được bỏ trống!");
                    editTextRegisterConfirmPassword.requestFocus();
                    editTextRegisterPassword.clearComposingText();
                    editTextRegisterConfirmPassword.clearComposingText();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(SignUpActivity.this,"Vui lòng nhập Email!",Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Email không được để trống!");
                    editTextRegisterEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(SignUpActivity.this,"Vui lòng nhập email!",Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Email không đúng định dạng!");
                    editTextRegisterEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPhone)) {
                    Toast.makeText(SignUpActivity.this,"Vui lòng nhập số điện thoại!",Toast.LENGTH_LONG).show();
                    editTextRegisterPhone.setError("Số điện thoại không được để trống!");
                    editTextRegisterPhone.requestFocus();
                } else if (textPhone.length()!=10) {
                    Toast.makeText(SignUpActivity.this,"Vui lòng nhập số điện thoại!",Toast.LENGTH_LONG).show();
                    editTextRegisterPhone.setError("Số điện thoại phải có 10 số!");
                    editTextRegisterPhone.requestFocus();
                } else if (TextUtils.isEmpty(textAddress)) {
                    Toast.makeText(SignUpActivity.this,"Vui lòng nhập địa chỉ!",Toast.LENGTH_LONG).show();
                    editTextRegisterAddress.setError("Địa chỉ không được để trống!");
                    editTextRegisterAddress.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textName,textPass,textConfirmPass,textEmail,textPhone,textAddress);
                }
            }
        });


    }
    //đăng ký người dùng bằng thông tin đăng nhập được cung cấp
    private void registerUser(String textName, String textPass, String textConfirmPass, String textEmail, String textPhone, String textAddress) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail,textPass).addOnCompleteListener(SignUpActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this,"Đăng kí tài khoản thành công",Toast.LENGTH_LONG).show();
                            FirebaseUser firebaseUser = auth.getCurrentUser();

//                           //luu du lieu
                            ReadWriteUserDetail writeUserDetail = new ReadWriteUserDetail(textName,textEmail,textPhone,textAddress);
                            //lay du dieu tu csdl
                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                            referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // gửi mã về email
                                    if (task.isSuccessful()){
                                        firebaseUser.sendEmailVerification();
                                        Toast.makeText(SignUpActivity.this,"Đăng kí tài khoản thành công.Vui lòng xác minh email.",
                                                Toast.LENGTH_LONG).show();

                                        //mở user profile sau khi dk thành công

                                        /* Intent intent = new Intent(SignUpActivity.this,UserProfileActivity.class);
                                         //Ngăn người dùng đăng kí
                                          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                 | Intent.FLAG_ACTIVITY_NEW_TASK);

                                             startActivity(intent);
                                             finish();//đóng đăng kí*/
                                    }else {
                                        Toast.makeText(SignUpActivity.this,"Đăng ký không thành công.Vui lòng đăng ký lại.",
                                                Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                    }

                                }
                            });
//
                        }else {
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                editTextRegisterPassword.setError("Mật khẩu quá yếu, vui lòng nhập mật khẩu mạnh hơn!");
                                editTextRegisterPassword.requestFocus();
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                editTextRegisterPassword.setError("Email không hợp lệ!");
                                editTextRegisterPassword.requestFocus();
                            }catch (FirebaseAuthUserCollisionException e){
                                editTextRegisterPassword.setError("Tài khoản này đã tồn tại!");
                                editTextRegisterPassword.requestFocus();
                            }catch (Exception e){
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(SignUpActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }
}