package com.example.pizzala;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PastaFragment extends ListFragment {
    private static final List<String> pastas = Arrays.asList("Cormoran", "Tatala", "Allemia");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pasta, container, false);
        ArrayAdapter<String> pastasListAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, pastas);
        setListAdapter(pastasListAdapter);
        return view;
    }
}