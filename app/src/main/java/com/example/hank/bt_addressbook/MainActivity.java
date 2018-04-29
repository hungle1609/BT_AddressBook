package com.example.hank.bt_addressbook;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText edtName,edtNumber;
    Button btnAdd,btnCall,btnSend;
    ListView lvContact;
    ContactAdapter contactAdapter;
    RadioButton radioButtonMale,radioButtonFemale;
    ArrayList<Contact> contactArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtName =  findViewById(R.id.edt_name);
        edtNumber =  findViewById(R.id.edt_number);
        btnAdd = findViewById(R.id.btn_add_contact);
        lvContact =  findViewById(R.id.lv_contact);
        radioButtonFemale=findViewById(R.id.rbtn_female);
        radioButtonMale=findViewById(R.id.rbtn_male);

        contactArrayList = new ArrayList<>();
        contactAdapter = new ContactAdapter(MainActivity.this,R.layout.item_contact_listview,contactArrayList);
        lvContact.setAdapter(contactAdapter);
        checkAndRequestPermision();
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogConfirm(position);
            }
        });

    }
    public  void addContact(View view) {
        if (view.getId() == R.id.btn_add_contact) {
            String name = edtName.getText().toString().trim();
            String number = edtNumber.getText().toString().trim();
            boolean isMale = true;
            if (radioButtonMale.isChecked()) {
                isMale = true;
            } else {
                isMale = false;
            }
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)) {
                Toast.makeText(this, "Please input name or phone", Toast.LENGTH_SHORT).show();
            } else {
                Contact contact = new Contact(isMale, name, number);
                contactArrayList.add(contact);
            }
            contactAdapter.notifyDataSetChanged();
        }
    }
        public void showDialogConfirm(final int position)
        {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.custom_dialog_layout);
            btnCall =dialog.findViewById(R.id.btn_call);
            btnSend=dialog.findViewById(R.id.btn_send);

            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentCall(position);
                }

                private void intentCall(int position)
                {
                    Intent intent =  new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+contactArrayList.get(position).getmNumber()));
                    startActivity(intent);
                }
            });
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentSendMessage(position);
                }
            });
            dialog.show();
        }

    private void intentSendMessage(int position)
    {
        Intent intent =  new Intent(Intent.ACTION_VIEW,Uri.parse("sms:"+contactArrayList.get(position).getmNumber()));
        startActivity(intent);

    }


    public void checkAndRequestPermision()
        {
            String[] permissions = new String[]{
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.SEND_SMS
            };
            List<String> listPermissionNeeded = new ArrayList<>();
            for(String permission :permissions)
            {
                if(ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED)
                {
                    listPermissionNeeded.add(permission);
                }
                if(!listPermissionNeeded.isEmpty())
                {
                    ActivityCompat.requestPermissions(this,listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),1);
                }
            }
        }
    }
