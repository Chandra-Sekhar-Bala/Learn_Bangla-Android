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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhraseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhraseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PhraseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhraseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhraseFragment newInstance(String param1, String param2) {
        PhraseFragment fragment = new PhraseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_phrase, container, false);
        audioManager = (AudioManager) requireActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> phrase = new ArrayList<Word>();

        phrase.add(new Word("Hello","ওহে ভাই",R.raw.phrase_hello));
        phrase.add(new Word("How are you?","আপনি কেমন আছেন?",R.raw.phrase_how));
        phrase.add(new Word("Thanks a lot","অনেক ধন্যবাদ",R.raw.phrase_thanks));
        phrase.add(new Word("That's not a big deal","সেটা বড় কথা নয়",R.raw.phrase_big_deal));
        phrase.add(new Word("I used to come here","আমি এখানে আসতাম",R.raw.phrase_used_to));
        phrase.add(new Word("I have nothing to say","আমার কিছু বলার নেই",R.raw.phrase_say));
        phrase.add(new Word("Love your parents","আপনার বাবা -মাকে ভালবাসুন",R.raw.phrase_parents));
        phrase.add(new Word("He's very clever"," খুব চালাক",R.raw.phrase_clever));

        //** Custom WordAdapter:
        WordAdapter items = new WordAdapter(requireContext(),phrase,R.color.phrase);

        //** ListView in activity_Phrase.xml
        ListView listView = (ListView) rootView.findViewById(R.id.phrase_list);
        listView.setAdapter(items);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word audio = phrase.get(position);
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