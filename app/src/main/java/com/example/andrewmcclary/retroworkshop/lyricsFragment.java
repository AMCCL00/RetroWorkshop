package com.example.andrewmcclary.retroworkshop;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.andrewmcclary.retroworkshop.MainActivity.ARTIST_NAME;
import static com.example.andrewmcclary.retroworkshop.MainActivity.SONG_TITLE;

public class lyricsFragment extends Fragment {

    private String baseUrl = "https://api.lyrics.ovh/v1/";
    private Retrofit retrofit;
    private RetrofitMusicApiCalls retrofitMusicApiCalls;

    @BindView(R.id.lyrics_text)
    protected TextView lyricsText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_lyrics, container,false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        String artistName = getArguments().getString(ARTIST_NAME);
        String songTitle = getArguments().getString(SONG_TITLE);
        buildRetrofit();
        makeApiCall(artistName, songTitle);
    }

    private void buildRetrofit(){

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitMusicApiCalls = retrofit.create(RetrofitMusicApiCalls.class);

    }

    public static lyricsFragment newInstance() {

        Bundle args = new Bundle();

        lyricsFragment fragment = new lyricsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private void makeApiCall(String artistName, String songTitle){
        retrofitMusicApiCalls.getSongLyrics(artistName,songTitle).enqueue(new Callback<RetrofitMusicApiCalls.SongLyrics>() {
            @Override
            public void onResponse(Call<RetrofitMusicApiCalls.SongLyrics> call, Response<RetrofitMusicApiCalls.SongLyrics> response) {

                if(response.isSuccessful()){
                    lyricsText.setText(response.body().getLyrics());
                }else{
                    Toast.makeText(getActivity(), "Error, try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RetrofitMusicApiCalls.SongLyrics> call, Throwable t) {

            }
        });
    }
}