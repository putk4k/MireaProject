package ru.mirea.vikulov.mireaproject.ui.file;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.vikulov.mireaproject.R;
import ru.mirea.vikulov.mireaproject.databinding.FragmentFileBinding;


public class file extends Fragment {
    private SharedPreferences sharedPref;
    private FragmentFileBinding binding;
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFileBinding.inflate(inflater, container, false);
        mContext = inflater.getContext();

        sharedPref = mContext.getSharedPreferences("settings-vikulov", Context.MODE_PRIVATE);
        binding.textFile.setText(sharedPref.getString("PASSWORD", "null password"));
        binding.fileB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String line = String.valueOf(binding.textFile.getText());
                Intent intent = new Intent(mContext, EncrActivity.class);
                intent.putExtra("line", line);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
}