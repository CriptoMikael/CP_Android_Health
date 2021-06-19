package com.example.sharpobject.ui.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharpobject.R;
import com.example.sharpobject.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private View rootView;
    private static Button speakButton;
    private static Button sendButton;
    private EditText dateField;
    private EditText modelName;
    private EditText highValue;
    private EditText lowValue;
    private EditText pulse;
    private EditText activity;
    private EditText mVoiceInputTv;

    private Long timeStamp;
    private String modelString;
    private int highString;
    private int lowString;
    private int pulseString;
    private String activityString;

    JSONObject json;

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

        mVoiceInputTv.setVisibility(View.INVISIBLE);
        dateField.setVisibility(View.INVISIBLE);
        modelName.setVisibility(View.INVISIBLE);
        highValue.setVisibility(View.INVISIBLE);
        lowValue.setVisibility(View.INVISIBLE);
        pulse.setVisibility(View.INVISIBLE);
        activity.setVisibility(View.INVISIBLE);

        timeStamp = 0L;
        modelString = new String("");
        highString = 0;
        lowString = 0;
        pulseString = 0;
        activityString = new String("");

        highValue.setText("0");
        lowValue.setText("0");
        pulse.setText("0");
        activity.setText("");

        speakButton = (Button) rootView.findViewById(R.id.speakButton);
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });

        sendButton = (Button) rootView.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                sendAllData();
                Toast toast = Toast.makeText(getActivity(), "Показания переданы врачу", Toast.LENGTH_LONG);
                toast.show();
                clearFields();
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
                    dateField.setVisibility(View.VISIBLE);
                    modelName.setVisibility(View.VISIBLE);
                    highValue.setVisibility(View.VISIBLE);
                    lowValue.setVisibility(View.VISIBLE);
                    pulse.setVisibility(View.VISIBLE);
                    activity.setVisibility(View.VISIBLE);

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    parseValue(result.get(0));
                    mVoiceInputTv.setText(result.get(0));
                    Date datedate = Calendar.getInstance().getTime();
                    timeStamp = datedate.getTime();
                    DateFormat dateFormat = new SimpleDateFormat("kk:mm dd-MM-yyyy");
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
                    break;
                }
            }
            highValue.setText(high);
            lowValue.setText(low);
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

    private void clearFields() {
        highValue.setText("0");
        lowValue.setText("0");
        pulse.setText("0");
        activity.setText("");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void sendAllData() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm dd-MM-yyyy");
            Date parsedDate = dateFormat.parse(dateField.getText().toString());
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            timeStamp = timestamp.getTime();
        } catch (Exception e) {
            System.out.println(e);
        }

        modelString = modelName.getText().toString();
        highString = Integer.parseInt(highValue.getText().toString());
        lowString = Integer.parseInt(lowValue.getText().toString());
        pulseString = Integer.parseInt(pulse.getText().toString());
        activityString = activity.getText().toString();

        json = new JSONObject();
        try {
            //json.put("date", timeStamp*1000);
            json.put("date", 1624099204);
            json.put("modelName", modelString);
            json.put("highValue", highString);
            json.put("lowValue", lowString);
            json.put("pulse", pulseString);
            json.put("activity", activityString);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            HttpTask asnyc = new HttpTask();
            asnyc.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    class HttpTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                    String urlString = "http://ec2-3-15-18-180.us-east-2.compute.amazonaws.com:5000/write_data";
                    String response = performPostCall(urlString, new HashMap<String, String>() {
                        private static final long serialVersionUID = 1L;
                        {
                            put("Accept", "application/json");
                            put("Content-Type", "application/json");
                        }
                    });
            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public String performPostCall(String requestURL,
                                  HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");

            String str = json.toString();
            byte[] outputBytes = str.getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write(outputBytes);

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}