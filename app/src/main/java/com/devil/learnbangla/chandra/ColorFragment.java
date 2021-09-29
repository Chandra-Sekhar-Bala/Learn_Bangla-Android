package com.devil.learnbangla.chandra;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ColorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ColorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ColorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ColorFragment newInstance(String param1, String param2) {
        ColorFragment fragment = new ColorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private MediaPlayer mediaplayer;
    private final MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the mediaplayer file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };
    // For AudioFocus:-
    private AudioManager audioManager;
    private final AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Do not have resource pause it now.
                mediaplayer.pause();
                mediaplayer.seekTo(0);
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // resume MediaPlayer
                mediaplayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // No access to AudioFocus
                releaseMediaPlayer();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_family, container, false);
        audioManager = (AudioManager) requireActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> color = new ArrayList<Word>();
        color.add(new Word("Red","লাল",R.drawable.color_red,R.raw.color_red));
        color.add(new Word("Green","সবুজ",R.drawable.color_green,R.raw.color_green));
        color.add(new Word("Black","কালো",R.drawable.color_black,R.raw.color_black));
        color.add(new Word("Brown","বাদামী",R.drawable.color_brown,R.raw.color_brown));
        color.add(new Word("Yellow","হলুদ",R.drawable.color_mustard_yellow,R.raw.color_yellow));
        color.add(new Word("Fade Yellow","বিবর্ণ হলুদ",R.drawable.color_dusty_yellow,R.raw.color_fade_yellow));
        color.add(new Word("Gray","ধূসর",R.drawable.color_gray,R.raw.color_gray));
        color.add(new Word("White","সাদা",R.drawable.color_white,R.raw.color_white));

        WordAdapter itemsAdapter = new WordAdapter (requireContext(),color,R.color.green);
        ListView listView = (ListView) rootView.findViewById(R.id.family_list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word audio = color.get(position);
                int res = audioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                releaseMediaPlayer();
                if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaplayer = MediaPlayer.create(getContext(), audio.getAudio());
                    mediaplayer.start();
                    mediaplayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more mediaplayers.
        releaseMediaPlayer();
    }

    public  void releaseMediaPlayer(){
        if(mediaplayer!=null)// then it has some valid resource:
            mediaplayer.release();

        mediaplayer = null;
        audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }
}