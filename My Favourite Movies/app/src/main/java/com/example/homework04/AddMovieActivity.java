package com.example.homework04;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class AddMovieActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    Spinner spinner;
    TextView ratingvalue;
    ArrayList<String> items;
    SeekBar seekbar;
    int progressval = 0;
    int title = 100;

    EditText name,description,year,imdb;

    Button addmovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        items = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.genre)));

        spinner = findViewById(R.id.spinner);
        ratingvalue = findViewById(R.id.ratingvalue);
        seekbar = findViewById(R.id.seekBar);
        name = findViewById(R.id.name);
        description = findViewById(R.id.descriptiontext);
        year = findViewById(R.id.year);
        addmovie = findViewById(R.id.addmovie);
        imdb = findViewById(R.id.imdb);
        //year = ((TextView)findViewById(R.id.year)).getText();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items.toArray(new String[items.size()])){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;

                    if(position == 0){
                        // Set the hint text color gray
                        tv.setTextColor(Color.GRAY);
                    }
                    else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
            }

            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        if(getIntent() != null && getIntent().getExtras() != null) {
            title = getIntent().getIntExtra(MainActivity.REQUEST_CODE, 100);

            setTitle(title == 100 ? "Add Movie" : "Edit Movie");
            addmovie.setText(title == 100 ? "Add Movie": "Save Changes");

            Movie editmovieobj = getIntent().getParcelableExtra(MainActivity.MOVIE_OBJ);

            if(editmovieobj != null){
                name.setText(editmovieobj.getName());
                description.setText(editmovieobj.getDescription());
                spinner.setSelection(items.indexOf(editmovieobj.getGenre()) != -1 ? items.indexOf(editmovieobj.getGenre()) : 0);
                ratingvalue.setText(String.valueOf(editmovieobj.getRating()));
                seekbar.setProgress(editmovieobj.getRating());
                year.setText(String.valueOf(editmovieobj.getYear()));
                imdb.setText(editmovieobj.getImdb());
            }
        }

        ((SeekBar)findViewById(R.id.seekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressval = progress;
                ratingvalue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        addmovie.setOnClickListener(this);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onClick(View v) {

        if(name.getText().toString().equals("")){
            name.setError("Set a Valid Name");
        }
        else if(description.getText().toString().equals("")) {
            Toast.makeText(this, "Select a Valid Description", Toast.LENGTH_SHORT).show();
        }
        else if(spinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select a Genre", Toast.LENGTH_SHORT).show();
        }
        else if(Integer.parseInt(year.getText().toString()) < 1800 || Integer.parseInt(year.getText().toString()) > 2019){
            year.setError("Set a Valid Year");
        }
        else if(imdb.getText().toString().equals("")){
            Toast.makeText(this, "Set the IMDB URL", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent();
            intent.putExtra(MainActivity.MOVIE_OBJ, new Movie(name.getText().toString(),
                    description.getText().toString(), spinner.getSelectedItem().toString(), imdb.getText().toString(), seekbar.getProgress(), Integer.parseInt(this.year.getText().toString())));
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
