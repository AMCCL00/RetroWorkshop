package com.example.andrewmcclary.retroworkshop;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String ARTIST_NAME = "artist_name";
    public static final String SONG_TITLE = "song_title";

    @BindView(R.id.text_artist)
    protected TextInputEditText artistName;
    @BindView(R.id.text_song)
    protected TextInputEditText songTitle;

    private com.example.andrewmcclary.retroworkshop.lyricsFragment lyricsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed(){

        if(lyricsFragment.isVisible()){
            getSupportFragmentManager().beginTransaction().remove(lyricsFragment).commit();
        }else{
            super.onBackPressed();
        }
    }

    @OnClick(R.id.button_submit)
    protected void submitClicked(){

        if(artistName.getText().toString().isEmpty() || songTitle.getText().toString().isEmpty()){
            Toast.makeText(this, "All fields are required, try again.", Toast.LENGTH_LONG).show();
        }else {
            lyricsFragment = lyricsFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString(ARTIST_NAME, artistName.getText().toString());
            bundle.putString(SONG_TITLE, songTitle.getText().toString());
            lyricsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, lyricsFragment).commit();
        }
    }
}
