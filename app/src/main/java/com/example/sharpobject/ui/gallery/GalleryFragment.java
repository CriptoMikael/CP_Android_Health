package com.example.sharpobject.ui.gallery;

import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_gallery, container,
                false);

        TableLayout ll = (TableLayout) rootView
                .findViewById(R.id.tableLayoutBloodPressure);

        String[] allDo = new String[]{"Выпить синюю таблетку после обеда",
                "В 12:00 замерить давление", "16:00 Поесть и замерить пульс"};

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
        }

        return rootView;

        // return inflater.inflate(R.layout.fragment_gallery, container, false);

        /*galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView listTodo = binding.listTodo;

        final String[] catNames = new String[] {
                "Рыжик", "Барсик", "Мурка"
        };
        final List<String> listElementsArrayList = new ArrayList<>(Arrays.asList(catNames));

        final ArrayAdapter<String> adapter = new ArrayAdapter(GalleryFragment.this, android.R.layout.simple_list_item_1, listElementsArrayList);
        listTodo.setAdapter(adapter);

        return root;*/
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