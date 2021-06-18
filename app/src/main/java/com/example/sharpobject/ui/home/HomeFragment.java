package com.example.sharpobject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharpobject.R;
import com.example.sharpobject.databinding.FragmentHomeBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        EditText textDate = rootView.findViewById(R.id.editTextDate2);
        Date datedate = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm dd-MM-yyyy");
        String date = dateFormat.format(datedate);
        textDate.setText(date);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}