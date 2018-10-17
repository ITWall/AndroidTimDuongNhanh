package com.example.hungnv.directionmap.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hungnv.directionmap.R;
import com.example.hungnv.directionmap.controller.PersonService;
import com.example.hungnv.directionmap.model.ResponseRegister;
import com.example.hungnv.directionmap.model.person.Account;
import com.example.hungnv.directionmap.model.person.Fullname;
import com.example.hungnv.directionmap.model.person.Person;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener{

    private EditText mEdtFirstname;
    private EditText mEdtLastname;
    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private Button mBtnRegister;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        setup(v);
        return v;
    }

    private void mapping(View v){
        mEdtFirstname = v.findViewById(R.id.edtFirstname);
        mEdtLastname = v.findViewById(R.id.edtLastname);
        mEdtEmail = v.findViewById(R.id.edtEmail);
        mEdtPassword = v.findViewById(R.id.edtPassword);
        mBtnRegister = v.findViewById(R.id.btnRegister);
    }

    private void setup(View v){
        mapping(v);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegister:
                if(mEdtFirstname.getText().toString().trim().length() == 0){
                    Toast.makeText(getContext(), "First name is required", Toast.LENGTH_SHORT).show();
                }else if(mEdtLastname.getText().toString().trim().length() == 0){
                    Toast.makeText(getContext(), "Last name is required", Toast.LENGTH_SHORT).show();
                }else if(mEdtEmail.getText().toString().trim().length() == 0){
                    Toast.makeText(getContext(), "Email is required", Toast.LENGTH_SHORT).show();
                }else if(mEdtPassword.getText().toString().trim().length() == 0){
                    Toast.makeText(getContext(), "Password is required", Toast.LENGTH_SHORT).show();
                }else{
                    Account account = new Account();
                    account.setEmail(mEdtEmail.getText().toString());
                    account.setPassword(mEdtPassword.getText().toString());
                    Fullname fullname = new Fullname();
                    fullname.setFirstname(mEdtFirstname.getText().toString());
                    fullname.setLastname(mEdtLastname.getText().toString());
                    Person person = new Person();
                    person.setAccount(account);
                    person.setFullname(fullname);
                    Retrofit retrofit = new Retrofit.Builder().
                            baseUrl(getResources().getString(R.string.api_register)).
                            addConverterFactory(GsonConverterFactory.create()).
                            build();
                    PersonService service = retrofit.create(PersonService.class);
                    service.register(person).enqueue(new Callback<ResponseRegister>() {
                        @Override
                        public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                            Toast.makeText(getContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseRegister> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                }

        }
    }
}
