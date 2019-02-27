package com.hipay.fullservice.example.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.hipay.fullservice.core.client.config.ClientConfig;
import com.hipay.fullservice.example.Preferences;
import com.hipay.fullservice.example.R;

public class EnvironmentFragment extends Fragment {

    RadioGroup radioGroup;

    TextInputEditText usernameInput;
    TextInputEditText passwordInput;
    TextInputEditText urlInput;

    Switch localSignatureSwitch;
    TextInputEditText signatureInput;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.environment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                Preferences.saveEnvironmentString(getContext(), Preferences.PREF_USERNAME_KEY, usernameInput.getText().toString());
                Preferences.saveEnvironmentString(getContext(), Preferences.PREF_USERNAME_KEY, usernameInput.getText().toString());

                Preferences.saveEnvironmentBoolean(getContext(), Preferences.PREF_LOCAL_SIGNATURE_ACTIVATION_KEY, localSignatureSwitch.isChecked());
                Preferences.saveEnvironmentString(getContext(), Preferences.PREF_LOCAL_SIGNATURE_PASSWORD_KEY, signatureInput.getText().toString());

                break;
        }
        return true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radioGroup = view.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.stage_radio_button) {
                    setOptions(0);
                }
                else if (checkedId == R.id.production_radio_button) {
                    setOptions(1);
                }
                else if (checkedId == R.id.custom_radio_button) {
                    setOptions(2);
                }
            }
        });


        usernameInput = view.findViewById(R.id.username_input);
        passwordInput = view.findViewById(R.id.password_input);

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

        signatureInput = view.findViewById(R.id.signature_input);

        localSignatureSwitch = view.findViewById(R.id.local_signature_switch);
        localSignatureSwitch.setChecked(Preferences.isLocalSignature(getContext()));
        setLocalSignatureEnabled(Preferences.isLocalSignature(getContext()));
        localSignatureSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setLocalSignatureEnabled(isChecked);
            }
        });


        setOptions(Preferences.getEnvironmentInt(getContext(), Preferences.PREF_ENVIRONMENT_KEY));
    }

    private void setOptions(int environment) {
        int ressource = 0;
        switch (environment) {
            case 0:
                ressource = R.id.stage_radio_button;
                usernameInput.setText(R.string.username_stage);
                passwordInput.setText(R.string.password_stage);
                urlInput.setText(ClientConfig.GatewayClientBaseURLNewStage);
                signatureInput.setText(R.string.signature_stage);
                environmentSectionEnabled(false);
                break;
            case 1:
                ressource = R.id.production_radio_button;
                usernameInput.setText(R.string.username_production);
                passwordInput.setText(R.string.password_production);
                urlInput.setText(ClientConfig.GatewayClientBaseURLNewProduction);
                signatureInput.setText(R.string.signature_production);
                environmentSectionEnabled(false);
                break;
            case 2:
                ressource = R.id.custom_radio_button;
                usernameInput.setText(Preferences.getCustomUsername(getContext()));
                passwordInput.setText(Preferences.getCustomPassword(getContext()));
                urlInput.setText(Preferences.isProductionUrl(getContext()) ? ClientConfig.GatewayClientBaseURLNewProduction : ClientConfig.GatewayClientBaseURLNewStage);
                signatureInput.setText(Preferences.getLocalSignaturePassword(getContext()));
                environmentSectionEnabled(true);
                break;
        }

        if (radioGroup.getCheckedRadioButtonId() != environment) {
            radioGroup.check(ressource);
        }
    }

    private void environmentSectionEnabled(boolean enabled) {
        usernameInput.setEnabled(enabled);
        passwordInput.setEnabled(enabled);
        urlInput.setEnabled(enabled);
    }

    private void setLocalSignatureEnabled(boolean enabled) {
        signatureInput.setEnabled(enabled);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_environment, container, false);
        return view;
    }

}
