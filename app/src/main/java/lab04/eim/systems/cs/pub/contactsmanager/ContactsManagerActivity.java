package lab04.eim.systems.cs.pub.contactsmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    Button showHide, Save, Cancel;
    LinearLayout hiddenFields;
    EditText Name, Phone, Email, Address, Company, JobTitle, Website, Im;

    final int CONTACTS_MANAGER_REQUEST_CODE = 2020;

    class MyListener implements View

            .OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonSave:
                    String name = Name.getText().toString();
                    String phone = Phone.getText().toString();
                    String email = Email.getText().toString();
                    String address = Address.getText().toString();
                    String jobTitle = JobTitle.getText().toString();
                    String company = Company.getText().toString();
                    String website = Website.getText().toString();
                    String im = Im.getText().toString();
                    Log.d("mdebug", name);
                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    if (name != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    }
                    if (phone != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                    }
                    if (email != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                    }
                    if (address != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                    }
                    if (jobTitle != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                    }
                    if (company != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                    }
                    ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                    if (website != null) {
                        ContentValues websiteRow = new ContentValues();
                        websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                        websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                        contactData.add(websiteRow);
                    }
                    if (im != null) {
                        ContentValues imRow = new ContentValues();
                        imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                        imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                        contactData.add(imRow);
                    }
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                    startActivityForResult(intent, CONTACTS_MANAGER_REQUEST_CODE);
                    break;
                case R.id.buttonCancel:
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                    break;
                case R.id.buttonShowHide:
                    if (showHide.getText().equals("SHOW ADDITIONAL FIELDS")) {
                        showHide.setText("HIDE ADDITIONAL FIELDS");
                        hiddenFields.setVisibility(View.VISIBLE);
                    } else {
                        showHide.setText("SHOW ADDITIONAL FIELDS");
                        hiddenFields.setVisibility(View.GONE);
                    }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);
        showHide = (Button)findViewById(R.id.buttonShowHide);
        Save = (Button)findViewById(R.id.buttonSave);
        Cancel = (Button)findViewById(R.id.buttonCancel);
        hiddenFields = (LinearLayout) findViewById(R.id.layout);

        Name = findViewById(R.id.editName);
        Phone = findViewById(R.id.editNumber);
        Email = findViewById(R.id.editEmail);
        Address = findViewById(R.id.editAddress);
        Company = findViewById(R.id.editCompany);
        JobTitle = findViewById(R.id.editJobTitle);
        Website = findViewById(R.id.editWebsite);
        Im = findViewById(R.id.editIM);

        Intent intentFromParent = getIntent();
        if (intentFromParent != null) {
            System.out.println("In here");
            Bundle b = intentFromParent.getExtras();
            String phoneNumber = (String)b.get("lab04.eim.systems.cs.pub.ro.contactsmanager.PHONE_NUMBER_KEY");
            // Log.d("mdebug", phoneNumber);

            if (phoneNumber != null) {
                Phone.getText().insert(0, phoneNumber);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }



            /* Intent intentToParent = new Intent();
            intentToParent.putExtra("ro.pub.cs.systems.eim.lab04.anotherKey", anotherValue);
            setResult(RESULT_OK, intentToParent);
            finish();*/
        }

        MyListener lst = new MyListener();
        showHide.setOnClickListener(lst);
        Save.setOnClickListener(lst);
        Cancel.setOnClickListener(lst);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode) {
            case CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
}
