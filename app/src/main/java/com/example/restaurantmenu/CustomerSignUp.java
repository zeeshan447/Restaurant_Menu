package com.example.restaurantmenu;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.restaurantmenu.ui.main.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static android.text.TextUtils.isEmpty;

public class CustomerSignUp extends Fragment implements View.OnClickListener {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    EditText cMail, cPassword, cName, cNumber;
    Button Signup;
    RadioButton cMale, cFemale, Gender;
    RadioGroup cGender;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.customerfragment_xml, container, false);
        dateButton = view.findViewById(R.id.customerdate);
        initDatePicker();
        dateButton.setText(getTodaysDate());
        dateButton.setOnClickListener(this);
        cMail = view.findViewById(R.id.customerEmail);
        cPassword = view.findViewById(R.id.customerPassword);
        mAuth = FirebaseAuth.getInstance();
        cName = view.findViewById(R.id.customerName);
        cNumber = view.findViewById(R.id.customerPhone);
        Signup = view.findViewById(R.id.customerSubmit);
        cMale = view.findViewById(R.id.genderMale);
        cGender = view.findViewById(R.id.customerGender);
//        cFemale = view.findViewById(R.id.genderFemale);
//        cGender = view.findViewById(R.id.customerGender);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(cMail.getText().toString()) || isEmpty(cPassword.getText().toString()) || isEmpty(cName.getText().toString()) || isEmpty(cNumber.getText().toString())){
                    Toast.makeText(getContext(), "Please fill out all fields properly", Toast.LENGTH_SHORT).show();
                }
                else{
                    Gender = cGender.findViewById(cGender.getCheckedRadioButtonId());
                    mAuth.createUserWithEmailAndPassword(cMail.getText().toString(), cPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                UserModel user = new UserModel(cMail.getText().toString(), cName.getText().toString(),cNumber.getText().toString(), Gender.getText().toString());
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                                            Toast.makeText(getContext(), "Registration Successful. Verification email has been sent to your email address.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else {
                                Toast.makeText(getContext(), "ajlsjdklsask" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        return  view;
    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }
    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }



    @Override
    public void onClick(View view) {
        datePickerDialog.show();

    }
}
