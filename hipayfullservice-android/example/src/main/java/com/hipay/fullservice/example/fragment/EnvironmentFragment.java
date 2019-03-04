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
                final String username =  usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                final String signaturePassword = signatureInput.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.environment_alert_title);
                    builder.setMessage(R.string.environment_alert_message_empty);
                    builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;
                }

                if (localSignatureSwitch.isChecked() && signaturePassword.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.environment_alert_title);
                    builder.setMessage(R.string.environment_alert_message_passphrase_empty);
                    builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.environment_alert_message_save);
                builder.setCancelable(true);
                builder.setNeutralButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton(R.string.button_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ClientConfig.Environment environment = ClientConfig.Environment.Stage;

                        if (radioGroup.getCheckedRadioButtonId() == R.id.stage_radio_button) {
                            Preferences.setEnvironment(getContext(), Preferences.STAGE);
                            environment = ClientConfig.Environment.Stage;
                        }
                        else if (radioGroup.getCheckedRadioButtonId() == R.id.production_radio_button) {
                            Preferences.setEnvironment(getContext(), Preferences.PRODUCTION);
                            environment = ClientConfig.Environment.Production;
                        }
                        else if (radioGroup.getCheckedRadioButtonId() == R.id.custom_radio_button) {
                            Preferences.setEnvironment(getContext(), Preferences.CUSTOM);

                            boolean isProduction = urlInput.getText().toString().equals(ClientConfig.GatewayClientBaseURLNewProduction);

                            Preferences.setCustomUsername(getContext(), username);
                            Preferences.setCustomPassword(getContext(), password);
                            Preferences.setIsProductionUrl(getContext(), isProduction);
                            environment = isProduction ? ClientConfig.Environment.Production : ClientConfig.Environment.Stage;
                        }

                        Preferences.setIsLocalSignature(getContext(), localSignatureSwitch.isChecked());
                        Preferences.setLocalSignaturePassword(getContext(), signaturePassword);

                        ClientConfig.getInstance().setConfig(
                                environment,
                                username,
                                password
                        );

                        getFragmentManager().popBackStack();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
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
                    setOptions(Preferences.STAGE);
                }
                else if (checkedId == R.id.production_radio_button) {
                    setOptions(Preferences.PRODUCTION);
                }
                else if (checkedId == R.id.custom_radio_button) {
                    setOptions(Preferences.CUSTOM);
                }
            }
        });


        usernameInput = view.findViewById(R.id.username_input);
        passwordInput = view.findViewById(R.id.password_input);

        urlInput = view.findViewById(R.id.url_input);
        urlInput.setText(Preferences.isProductionUrl(getContext()) ? ClientConfig.GatewayClientBaseURLNewProduction : ClientConfig.GatewayClientBaseURLNewStage);
        urlInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                String[] choices = {"Stage", "Production"};
                builder.setItems(choices, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                urlInput.setText(ClientConfig.GatewayClientBaseURLNewStage);
                                break;
                            case 1:
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


        int env = Preferences.getEnvironment(getContext());
        setOptions(env);
        setRadioChecked(env);

        signatureInput.setText(Preferences.getLocalSignaturePassword(getContext()));
    }

    private void setOptions(int environment) {
        switch (environment) {
            case Preferences.STAGE:
                usernameInput.setText(R.string.username_stage);
                passwordInput.setText(R.string.password_stage);
                urlInput.setText(ClientConfig.GatewayClientBaseURLNewStage);
                environmentSectionEnabled(false);
                break;
            case Preferences.PRODUCTION:
                usernameInput.setText(R.string.username_production);
                passwordInput.setText(R.string.password_production);
                urlInput.setText(ClientConfig.GatewayClientBaseURLNewProduction);
                environmentSectionEnabled(false);
                break;
            case Preferences.CUSTOM:
                usernameInput.setText(Preferences.getCustomUsername(getContext()));
                passwordInput.setText(Preferences.getCustomPassword(getContext()));
                urlInput.setText(Preferences.isProductionUrl(getContext()) ? ClientConfig.GatewayClientBaseURLNewProduction : ClientConfig.GatewayClientBaseURLNewStage);
                environmentSectionEnabled(true);
                break;
        }
    }

    private void setRadioChecked(int environment) {
        int ressource = 0;
        switch (environment) {
            case Preferences.STAGE:
                ressource = R.id.stage_radio_button;
                break;
            case Preferences.PRODUCTION:
                ressource = R.id.production_radio_button;
                break;
            case Preferences.CUSTOM:
                ressource = R.id.custom_radio_button;
                break;
        }

        radioGroup.check(ressource);
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
