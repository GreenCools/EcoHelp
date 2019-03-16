package com.example.ecohelp;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;
        import android.app.ProgressDialog;


        import com.google.firebase.FirebaseApp;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity implements


        View.OnClickListener {
    private static final String TAG = "EmailPassword";
    //Инициализация всего
    private EditText mEmailField;
    private EditText mPasswordField;
    public ProgressDialog pd;




    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Поля
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);

        // Кнопки
        findViewById(R.id.emailSignInButton).setOnClickListener(this);
        findViewById(R.id.emailCreateAccountButton).setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();
    }

    //Проверка входа
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "Создание аккаунта" + email);
        if (validateForm()) {
            return;
        }
        pd = new ProgressDialog(this);

        pd.show();
        pd.setMessage("Регистрация");

        //Регистрация через емайл
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                pd.hide();
                Log.d(TAG, "Аккаунт успешно создан");
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            } else {
                pd.hide();
                Log.w(TAG, "Ошибка создания аккаунта", task.getException());
                Toast.makeText(MainActivity.this, "Ошибка создания аккаунта",
                        Toast.LENGTH_SHORT).show();
                updateUI(null);
            }

        });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "Вход" + email);
        if (validateForm()) {
            return;
        }
        pd = new ProgressDialog(this);
        pd.show();
        pd.setMessage("Вход");

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        pd.hide();
                        Log.d(TAG, "Вход через почту успешен");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        pd.hide();
                        Log.w(TAG, "Вход не вошелся", task.getException());
                        Toast.makeText(MainActivity.this, "Ошибка входа",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }


                });
    }



    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Пусто");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Пусто");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return !valid;
    }

    private void updateUI(FirebaseUser user) {
        pd = new ProgressDialog(this);
        pd.hide();
        if (user != null) {
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.emailCreateAccountButton) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.emailSignInButton) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }

    }
}
