package com.hipay.fullservice.example.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.hipay.fullservice.core.client.config.ClientConfig;
import com.hipay.fullservice.example.R;

public class EnvironmentFragment extends Fragment {

    TextInputEditText usernameInput;
    TextInputEditText passwordInput;
    TextInputEditText urlInput;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RadioGroup radioGroup = view.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.stage_radio_button:
                        Log.d("", "stage_radio_button: ");
                        environmentSectionEnabled(false);
                        break;
                    case R.id.production_radio_butto:
                        Log.d("", "production_radio_butto: ");
                        environmentSectionEnabled(false);
                        break;
                    case R.id.custom_radio_button:
                        Log.d("", "custom_radio_button: ");
                        environmentSectionEnabled(true);
                        break;
                }
            }
        });


        usernameInput = view.findViewById(R.id.username_input);
        usernameInput.setText("monUsername");

        passwordInput = view.findViewById(R.id.password_input);
        passwordInput.setText("password");

        urlInput = view.findViewById(R.id.url_input);
        urlInput.setText("http://www.qwant.com");
        urlInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose URL");

                String[] choices = {"Stage", "Production"};
                builder.setItems(choices, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Log.d("", "Stage");

                                urlInput.setText(ClientConfig.GatewayClientBaseURLNewStage);
                                break;
                            case 1:
                                Log.d("", "Prod");

                                urlInput.setText(ClientConfig.GatewayClientBaseURLNewProduction);
                                break;
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void environmentSectionEnabled(boolean enabled) {
        usernameInput.setEnabled(enabled);
        passwordInput.setEnabled(enabled);
        urlInput.setEnabled(enabled);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_environment, container, false);
        return view;
    }
}
