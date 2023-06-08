package ru.mirea.vikulov.mireaproject.ui.file;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ru.mirea.vikulov.mireaproject.databinding.ActivityEncrBinding;
import ru.mirea.vikulov.mireaproject.R;

public class EncrActivity extends AppCompatActivity {

    private ActivityEncrBinding binding;
    private String fileName = "mirea.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEncrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String line = (String) getIntent().getSerializableExtra("line");
        SecretKey key = generateKey();
        byte[] shiper = encryptMsg(line,key);
        String str = new String(shiper, StandardCharsets.UTF_8);
        binding.editFN.setText(fileName);
        binding.textT.setText(str);

        binding.saveBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExternalStorageWritable()) {
                    writeFileToExternalStorage();
                }
            }
        });
        binding.loadB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExternalStorageReadable()) readFileFromExternalStorage();
            }
        });
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    /* Проверяем внешнее хранилище на доступность чтения */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void writeFileToExternalStorage()	{
        File path =	Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file =	new	File(path,	binding.editFN.getText() + ".txt");
        try	{
            FileOutputStream fileOutputStream =	new	FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter output =	new	OutputStreamWriter(fileOutputStream);
            //	Запись строки в файл
            output.write(String.valueOf(binding.textT.getText()));
            //	Закрытие потока записи
            output.close();

        }	catch	(IOException e)	{
            Log.w("ExternalStorage",	"Error	writing	"	+	file,	e);
        }
    }

    public void	readFileFromExternalStorage() {
        File path =	Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file =	new	File(path,binding.editFN.getText() + ".txt");
        try	{
            FileInputStream fileInputStream	= new	FileInputStream(file.getAbsoluteFile());
            InputStreamReader inputStreamReader	= new	InputStreamReader(fileInputStream,	StandardCharsets.UTF_8);
            BufferedReader reader =	new BufferedReader(inputStreamReader);
            String line	= reader.readLine();

            Log.w("ExternalStorage", String.format("Read from file %s successful",	line));
            binding.textT.setText(line);
        }	catch (Exception	e) {
            Log.w("ExternalStorage", String.format("Read from file %s failed",	e.getMessage()));
        }
    }

    public	static SecretKey generateKey(){
        try	{
            SecureRandom sr	= SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any	data	used	as	random	seed".getBytes());
            KeyGenerator kg	= KeyGenerator.getInstance("AES");
            kg.init(256,	sr);
            return	new SecretKeySpec((kg.generateKey()).getEncoded(),	"AES");
        }	catch	(NoSuchAlgorithmException e)	{
            throw	new	RuntimeException(e);
        }
    }

    public	static	byte[]	encryptMsg(String	message,	SecretKey	secret)	{
        Cipher cipher	=	null;
        try	{
            cipher	=	Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,	secret);
            return	cipher.doFinal(message.getBytes());
        }	catch	(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                       BadPaddingException | IllegalBlockSizeException e)	{
            throw	new	RuntimeException(e);
        }
    }

}