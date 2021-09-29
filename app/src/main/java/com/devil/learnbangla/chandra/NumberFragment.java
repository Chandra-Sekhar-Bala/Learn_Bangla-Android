package com.devil.learnbangla.chandra;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NumberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumberFragment extends Fragment {

    // Music Player:
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NumberFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NumberFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NumberFragment newInstance(String param1, String param2) {
        NumberFragment fragment = new NumberFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_number, container, false);
        //Get system services for AudioManager:
        audioManager = (AudioManager) requireActivity().getSystemService(Context.AUDIO_SERVICE);

//        String[] words = {"one","two","three","four","five","six","seven","eight","nine","ten"};
//        ArrayList<String> words = new ArrayList<String>();
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one","এক",R.drawable.number_one,R.raw.number_1));
        words.add(new Word("Two","দুই",R.drawable.number_two,R.raw.number_2));
        words.add(new Word("Three","তিন",R.drawable.number_three,R.raw.number_3));
        words.add(new Word("Four","চার",R.drawable.number_four,R.raw.number_4));
        words.add(new Word("Five","পাঁচ",R.drawable.number_five,R.raw.number_5));
        words.add(new Word("Six","ছয়",R.drawable.number_six,R.raw.number_6));
        words.add(new Word("Seven","সাত",R.drawable.number_seven,R.raw.number_7));
        words.add(new Word("Eight","আট",R.drawable.number_eight,R.raw.number_8));
        words.add(new Word("Nine","নয়",R.drawable.number_nine,R.raw.number_9));
        words.add(new Word("Ten","দশ",R.drawable.number_ten,R.raw.number_10));

        WordAdapter itemsAdapter = new WordAdapter (requireContext(),words,R.color.orange);
//        GridView GridView = (GridView) findViewById(R.id.grid);
//        GridView.setAdapter(itemsAdapter);

//        ImageView imageView = (ImageView)fragmentView.findViewById(R.id.my_image);
        ListView listView = (ListView) rootView.findViewById(R.id.number_list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word audio = words.get(position);
                int res = audioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                releaseMediaPlayer();
                if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaplayer = MediaPlayer.create(getContext(), audio.getAudio());
                    mediaplayer.start();
                    mediaplayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        //
        // Inflate the layout for this fragment
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