package edu.nhti.ist218.soundscapeproject;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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



public class MainActivity extends AppCompatActivity {



    private SeekBar primaryVolume;
    private SeekBar secondaryVolume;
    private SeekBar tertiaryVolume;

    private static MediaPlayer[] audio;

    private EditText userTitle;

    private static MediaPlayer primaryPlaying = null;
    private static MediaPlayer secondaryPlaying = null;
    private static MediaPlayer tertiaryPlaying = null;


    private final static int maxVolume = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


        //initialize mp3 files
        MediaPlayer rain_sound = MediaPlayer.create(this, R.raw.rain_sound);
        MediaPlayer ocean_sound = MediaPlayer.create(this, R.raw.ocean_sound);
        MediaPlayer fire_sound = MediaPlayer.create(this, R.raw.fire_sound);

        MediaPlayer thunder_sound = MediaPlayer.create(this, R.raw.thunder_sound);
        MediaPlayer seagulls_sound = MediaPlayer.create(this, R.raw.seagulls_sound);
        MediaPlayer crickets_sound = MediaPlayer.create(this, R.raw.crickets_sound);

        MediaPlayer birds_sound = MediaPlayer.create(this, R.raw.birds_chirping_sound);
        MediaPlayer harbor_sound = MediaPlayer.create(this, R.raw.harbor_bells_sound);
        MediaPlayer leaves_sound = MediaPlayer.create(this, R.raw.rustling_leaves_sound);

        //add mp3 to audio array
        audio = new MediaPlayer[] {rain_sound, ocean_sound, fire_sound, thunder_sound,
                            seagulls_sound, crickets_sound, birds_sound, harbor_sound, leaves_sound};



        //initializes string array from strings xml
        String[] primarySounds = getResources().getStringArray(R.array.primary_sounds);
        String[] secondarySounds = getResources().getStringArray(R.array.secondary_sounds);
        String[] tertiarySounds = getResources().getStringArray(R.array.tertiary_sounds);
        String[] gifSpinnerArray = getResources().getStringArray(R.array.gif_spinner);

        //initializes spinners and adds them to an array
        Spinner primarySpinner = findViewById(R.id.primarySpinner);
        Spinner secondarySpinner = findViewById(R.id.secondarySpinner);
        Spinner tertiarySpinner = findViewById(R.id.tertiarySpinner);
        Spinner gifSpinner = findViewById(R.id.gifSpinner);
        Spinner[] spinners = new Spinner[] {primarySpinner, secondarySpinner, tertiarySpinner, gifSpinner};

        //populates spinners with string array
        populateSpinner(spinners[0], primarySounds, 0);
        populateSpinner(spinners[1], secondarySounds, 3);
        populateSpinner(spinners[2], tertiarySounds, 6);
        populateGifSpinner(spinners[3], gifSpinnerArray);

        configureStartButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_1) {
            Toast.makeText(MainActivity.this, "Item 1 selected", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.item_2) {
            Toast.makeText(MainActivity.this, "Item 2 selected", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configureStartButton() {

        Button startButton = findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userTitle = findViewById(R.id.editScapeName);
                Intent intent = new Intent(MainActivity.this, PlayBackActivity.class);
                intent.putExtra("userTitle", userTitle.getText().toString());

                startActivity(intent);
            }
        });
    }

    private void populateSpinner(final Spinner spinner, String[] soundsArray, final int soundNum) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, soundsArray){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint

                switch (position) {
                    case 1:
                        setVolumeSlider(soundNum);

                        Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        setVolumeSlider(soundNum + 1);

                        Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        setVolumeSlider(soundNum + 2);

                        Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void populateGifSpinner(final Spinner spinner, String[] gifArray) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, gifArray){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint

                switch (position) {
                    case 1:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.tornado = true;
                        break;
                    case 2:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.tornado2 = true;
                        break;
                    case 3:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.storm = true;
                        break;
                    case 4:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.storm2 = true;
                        break;
                    case 5:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.street = true;
                        break;
                    case 6:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.rain_ocean = true;
                        break;
                    case 7:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.rain = true;
                        break;
                    case 8:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.lightning = true;
                        break;
                    default:
                        break;
                }

            }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
        });
    }

    private void setVolumeSlider(int i){

        primaryVolume = findViewById(R.id.primarySeekBar);
        secondaryVolume = findViewById(R.id.secondarySeekBar);
        tertiaryVolume = findViewById(R.id.tertiarySeekBar);


        if(i < 3) {

            if (primaryPlaying != null) {
                primaryPlaying.pause();
            }
            primaryPlaying = audio[i];
            primaryVolume.setMax(maxVolume);
            primaryVolume.setProgress(maxVolume);
            primaryPlaying.start();
            primaryPlaying.setLooping(true);

            primaryVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    float volume = (float) (1 - (Math.log(maxVolume - progress) / Math.log(maxVolume)));
                    primaryPlaying.setVolume(volume, volume);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
        if (i >=3 && i < 6) {

            if (secondaryPlaying != null) {
                secondaryPlaying.pause();
            }
            secondaryPlaying = audio[i];
            secondaryVolume.setMax(maxVolume);
            secondaryVolume.setProgress(maxVolume);
            secondaryPlaying.start();
            secondaryPlaying.setLooping(true);

            secondaryVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    float volume = (float) (1 - (Math.log(maxVolume - progress) / Math.log(maxVolume)));
                    secondaryPlaying.setVolume(volume, volume);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
        if (i >= 6 && i < 9) {

            if (tertiaryPlaying != null) {
                tertiaryPlaying.pause();
            }
            tertiaryPlaying = audio[i];
            tertiaryVolume.setMax(maxVolume);
            tertiaryVolume.setProgress(maxVolume);
            tertiaryPlaying.start();
            tertiaryPlaying.setLooping(true);

            tertiaryVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    float volume = (float) (1 - (Math.log(maxVolume - progress) / Math.log(maxVolume)));
                    tertiaryPlaying.setVolume(volume, volume);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

    }
}
