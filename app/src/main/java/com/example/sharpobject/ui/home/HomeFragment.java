package com.example.sharpobject.ui.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharpobject.R;
import com.example.sharpobject.databinding.FragmentHomeBinding;

import java.sql.Struct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private View rootView;
    private static Button speakButton;
    private EditText dateField;
    private EditText modelName;
    private EditText highValue;
    private EditText lowValue;
    private EditText pulse;
    private EditText activity;
    private EditText mVoiceInputTv;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mVoiceInputTv = (EditText) rootView.findViewById(R.id.editTextTextPersonName4);
        dateField = rootView.findViewById(R.id.dateField);
        modelName = (EditText) rootView.findViewById(R.id.modelName);
        highValue = (EditText) rootView.findViewById(R.id.highValue);
        lowValue = (EditText) rootView.findViewById(R.id.lowValue);
        pulse = (EditText) rootView.findViewById(R.id.pulse);
        activity = (EditText) rootView.findViewById(R.id.activity);
        speakButton = (Button) rootView.findViewById(R.id.speakButton);
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });

        return rootView;
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            System.out.println(a);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    parseValue(result.get(0));
                    mVoiceInputTv.setText(result.get(0));
                    Date datedate = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm dd-MM-yyyy");
                    String date = dateFormat.format(datedate);
                    dateField.setText(date);
                    modelName.setText("Omron M2 Classic");
                }
                break;
            }

        }
    }

    private void parseValue(String dataString) {
        int i = 0;
        dataString = dataString.toLowerCase();
        if (dataString.contains("давление")) {
            int count = 0;
            String high = new String("");
            String low = new String("");
            for (i = dataString.indexOf("давление"); i < dataString.length(); i++) {
                if (count != 2) {
                    boolean flag = false;
                    String str = new String("");
                    for (char c = dataString.charAt(i);(c >= '0') && (c <= '9'); c = dataString.charAt(i)) {
                        flag = true;
                        str += c;
                        i++;
                        if (i == dataString.length())
                            break;
                    }
                    if (flag) {
                        if (count == 0) {
                            high = str;
                        }
                        else {
                            low = str;
                        }
                        count++;
                    }
                }
                else {
                    highValue.setText(high);
                    lowValue.setText(low);
                    break;
                }
            }
        }
        if (dataString.contains("пульс")) {
            boolean flag = false;
            for (i = dataString.indexOf("пульс"); i < dataString.length(); i++) {
                if (!flag) {
                    String str = new String("");
                    for (char c = dataString.charAt(i);(c >= '0') && (c <= '9'); c = dataString.charAt(i)) {
                        flag = true;
                        str += c;
                        i++;
                        if (i == dataString.length())
                            break;
                    }
                    if (flag)
                        pulse.setText(str);
                }
                else {
                    break;
                }
            }
        }
        if (i < dataString.length()) {
            activity.setText(dataString.substring(i));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}