package com.example.sharpobject.ui.gallery;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.sharpobject.R;
import com.example.sharpobject.databinding.FragmentGalleryBinding;
import com.example.sharpobject.ui.home.HomeFragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    private View rootView;
    private TableLayout ll;
    public String[] allDo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_gallery, container,
                false);

        ll = (TableLayout) rootView.findViewById(R.id.tableLayoutBloodPressure);

        String address = "http://ec2-3-15-18-180.us-east-2.compute.amazonaws.com:5000/recommendation";


        GetUrlContentTask asnyc = new GetUrlContentTask();
        asnyc.execute();

        return rootView;
    }

    class GetUrlContentTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            for (int i = 0; i < allDo.length; i++) {
                TableRow tbrow = new TableRow(getActivity().getApplicationContext());
                tbrow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                TextView tv1 = new TextView(getActivity().getApplicationContext());
                tv1.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv1.setId(i);

                tv1.setText(allDo[i]);
                tbrow.addView(tv1);

                ll.addView(tbrow, new TableLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String urlString = "http://ec2-3-15-18-180.us-east-2.compute.amazonaws.com:5000/recommendation";
                String response = performGetCall(urlString, new HashMap<String, String>() {
                    private static final long serialVersionUID = 1L;
                    {
                        put("Accept", "application/json");
                        put("Content-Type", "application/json");
                    }
                });
                allDo = response.split(",");
            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public String performGetCall(String requestURL,
                                  HashMap<String, String> getDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String test = conn.getResponseMessage();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
                /*String[] allDo = response.split(",");
                TableLayout ll = (TableLayout) rootView.findViewById(R.id.tableLayoutBloodPressure);
                for (int i = 0; i < 3; i++) {
                    TableRow tbrow = new TableRow(getActivity().getApplicationContext());
                    tbrow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

                    TextView tv1 = new TextView(getActivity().getApplicationContext());
                    tv1.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv1.setId(i);

                    tv1.setText(allDo[i]);
                    tbrow.addView(tv1);

                    ll.addView(tbrow, new TableLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }*/
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}