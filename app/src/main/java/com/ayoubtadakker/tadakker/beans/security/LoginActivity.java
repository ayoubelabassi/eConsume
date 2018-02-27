package com.ayoubtadakker.tadakker.beans.security;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ayoubtadakker.tadakker.R;
import com.ayoubtadakker.tadakker.beans.main.MainActivity;
import com.ayoubtadakker.tadakker.checker.Cryptor;
import com.ayoubtadakker.tadakker.checker.compagne.User;
import com.ayoubtadakker.tadakker.database.localDB.DBHandler;

import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    // UI references.
    private TextView txtUserName;
    private EditText txtPassword;
    private ProgressBar mProgressBar;
    DBHandler db=new DBHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        txtUserName = (TextView) findViewById(R.id.login_username);
        txtPassword = (EditText) findViewById(R.id.login_password);
        mProgressBar=(ProgressBar)findViewById(R.id.login_progress);



        List<User> list=db.getUsers();
        if(list==null){
            createUser();
        }else{
            if(list.size()==0){
                createUser();
            }
        }
    }

    public void createUser(){
        User user= null;
        try {
            user = new User(0,"admin", Cryptor.encrypt("admin"),"EL ABASSI","Ayoub",null);
            db.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginAction(View view) throws Exception {
        try{
            mProgressBar.setVisibility(View.VISIBLE);
            attemptLogin();
            mProgressBar.setVisibility(View.INVISIBLE);
        }catch (Exception e){
            Toast.makeText(this,e.getMessage() ,Toast.LENGTH_LONG).show();
        }
    }


    private void attemptLogin() {
        txtUserName.setError(null);
        txtPassword.setError(null);

        // Store values at the time of the login attempt.
        String username = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();

        boolean cancel = false;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            txtPassword.setError(getString(R.string.error_invalid_password));
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            txtUserName.setError(getString(R.string.error_field_required));
            cancel = true;
        } else if (!isEmailValid(username)) {
            txtUserName.setError(getString(R.string.error_invalid_email));
        }

        //check the existance of user in database
        User user=db.getUser(username,Cryptor.encrypt(password));
        List<User> list=db.getUsers();
        //Toast.makeText(this,"password : "+Cryptor.encrypt(password) ,Toast.LENGTH_LONG).show();
        if(user!=null){
            Bundle b=new Bundle();
            b.putInt("USER_ID",user.getId());
            Intent intent=new Intent(this,MainActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        }else{
            txtUserName.setError(getString(R.string.error_field_required));
            txtUserName.setError(getString(R.string.error_invalid_email));
        }
    }

    private boolean isEmailValid(String username) {
        //TODO: Replace this with your own logic
        return username.length()>0?true:false;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}

