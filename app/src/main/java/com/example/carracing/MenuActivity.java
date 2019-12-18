package com.example.carracing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity
{

    private Button menu_slow_Btn;
    private Button menu_fast_Btn;
    private Button menu_ss_Btn;
    private Button menu_start_topscore;

    public static String mode = "";
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        menu_slow_Btn = findViewById(R.id.menu_slow_Btn);
        menu_fast_Btn = findViewById(R.id.menu_fast_Btn);
        menu_ss_Btn= findViewById(R.id.menu_ss_Btn);
        menu_start_topscore = findViewById(R.id.menu_start_topscore);

        menu_slow_Btn.setOnClickListener(goNext);
        menu_fast_Btn.setOnClickListener(goNext);
        menu_ss_Btn.setOnClickListener(goNext);
        menu_start_topscore.setOnClickListener(goTopScore);

        checkAndRequestPermissions();

    }

    private  boolean checkAndRequestPermissions()
    {
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    View.OnClickListener goNext = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            String choosenMode = v.getResources().getResourceEntryName(v.getId());
            switch (choosenMode)
            {
                case "menu_slow_Btn":
                    mode = "Slow";
                    goToGameActivity();
                    break;

                case "menu_fast_Btn":
                    mode = "Fast";
                    goToGameActivity();
                    break;

                case "menu_ss_Btn":
                    mode = "ss";
                    goToGameActivity();
                    break;
            }
        }
    };

    View.OnClickListener goTopScore = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(MenuActivity.this, TopScoreActivity.class);
            //intent.putExtra("score", getIntent().getExtras().getString("score"));
            startActivity(intent);
            finish();
        }
    };

    private  void goToGameActivity()
    {
        Intent intent = new Intent(MenuActivity.this, GameActivity.class);
        intent.putExtra("mode" ,mode);
        startActivity(intent);
        finish();
    }
}
