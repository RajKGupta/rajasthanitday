package govirtual360.numberverify.mainactitvities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import govirtual360.numberverify.R;
import govirtual360.numberverify.service.model.PersonalDetails;
import govirtual360.numberverify.service.model.SharedPreference;

import static govirtual360.numberverify.Farmer.DBREF;
import static govirtual360.numberverify.Farmer.Punjab;
import static govirtual360.numberverify.Farmer.Rajasthan;
import static govirtual360.numberverify.Farmer.users;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String name, area, state, district;
    private EditText ename, earea;
    private FloatingActionButton submit_profile;
    public SharedPreference session;
    private Spinner spinnerState, spinnerDistrict;
    ArrayAdapter<CharSequence> stateAdapter, districtAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        session = new SharedPreference(this);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        ename = (EditText) findViewById(R.id.ename);
        earea = (EditText) findViewById(R.id.earea);
        spinnerDistrict = (Spinner) findViewById(R.id.district);
        spinnerState = (Spinner) findViewById(R.id.state);
        submit_profile = (FloatingActionButton) findViewById(R.id.submit_profile);

        //       stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<String>(
//                Arrays.asList(Punjab, Rajasthan)));
        //ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stateList);

        //spinnerDistrict.setAdapter(districtAdapter);

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = parent.getItemAtPosition(position).toString();
                if (state.equals("Rajasthan"))
                    districtAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.rajasthan, android.R.layout.simple_spinner_dropdown_item);
                else
                    districtAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.punjab, android.R.layout.simple_spinner_dropdown_item);

                spinnerDistrict.setAdapter(districtAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                district = parent.getItemAtPosition(position).toString();
                Toast.makeText(ProfileActivity.this, district, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        districtAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.punjab, android.R.layout.simple_spinner_dropdown_item);
        stateAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.state, android.R.layout.simple_spinner_dropdown_item);

        spinnerState.setAdapter(stateAdapter);
        spinnerDistrict.setAdapter(districtAdapter);
        submit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = ename.getText().toString().trim();
                area = earea.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(area)) {
                    Toast.makeText(ProfileActivity.this, "Fill all the details", Toast.LENGTH_SHORT).show();
                } else {
                    session.setSharedPreference(name, session.getPhone(), session.getUID(), area, district, state);
                    DBREF.child(users).child(session.getUID()).setValue(new PersonalDetails(name, session.getPhone(), district, state, session.getUID(), area, session.getFCMavail()));
                    goToNextActivity();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        ProfileActivity.super.onBackPressed();
                    }


                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void goToNextActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        Toast.makeText(adapterView.getContext(), "Selected: " + state, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}