package ru.mirea.vikulov.mireaproject.ui.profile;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import ru.mirea.vikulov.mireaproject.R;
import ru.mirea.vikulov.mireaproject.databinding.FragmentProfileBinding;


public class Profile extends Fragment {

    private FragmentProfileBinding binding;
    private Context mContext;
    private SharedPreferences sharedPref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        mContext = inflater.getContext();
        sharedPref = mContext.getSharedPreferences("settings-vikulov",	Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        binding.saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("EMAIL", String.valueOf(binding.editMail.getText()));
                editor.putString("PASSWORD", String.valueOf(binding.editPas.getText()));
                editor.putString("NAME", String.valueOf(binding.editNam.getText()));
                editor.putString("SIRNAME", String.valueOf(binding.editSir.getText()));
                editor.putString("GROUP", String.valueOf(binding.editGroup.getText()));
                editor.apply();
                Toast.makeText(mContext, "Произошло сохранение", Toast.LENGTH_SHORT).show();

            }
        });
        return binding.getRoot();
    }


}