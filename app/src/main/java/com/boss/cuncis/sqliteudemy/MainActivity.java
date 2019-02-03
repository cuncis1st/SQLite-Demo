package com.boss.cuncis.sqliteudemy;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseAdapter databaseAdapter;
    EditText etName, etEmail, nameInput;
    Button btnAddUser, btnShowData, btnGetUser, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseAdapter = new DatabaseAdapter(this);

        initView();
        initListener();

    }

    private void initListener() {
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();

                long id = databaseAdapter.insertData(name, email);

                if (id < 0) {
                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "One record successfully inserted.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = databaseAdapter.getAllData();
                Toast.makeText(MainActivity.this, "" + data, Toast.LENGTH_SHORT).show();
            }
        });
        btnGetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                String result = databaseAdapter.getData(name);
                Toast.makeText(MainActivity.this, "" + result, Toast.LENGTH_SHORT).show();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseAdapter.updateEmail("cuncis1st", "cuncisss@gmail.com");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseAdapter.deleteData("cuncis2nd");
            }
        });
    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        nameInput = findViewById(R.id.et_choosen_email);
        btnAddUser = findViewById(R.id.btn_add_user);
        btnShowData = findViewById(R.id.btn_show_data);
        btnGetUser = findViewById(R.id.btn_get_user);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
    }
}
