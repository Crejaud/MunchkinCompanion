package com.crejaud.jrejaud.munchkincompanion;

/**
 * Created by creja_000 on 7/23/2014.
 */
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class playerFragment extends Fragment implements LevelMenu.AdjustPlayerFragment {


    private TextView baseLvlText, bonusLvlText, totalLvlText, oneShotLvlText;
    private int baseLevel, bonusLevel, totalLevel, oneShotLevel;
    private int pos;
    private AdView mAdView;
    private static final String TAG = playerFragment.class.getSimpleName();

    static playerFragment newInstance(int pos, int base, int bonus) {
        playerFragment f = new playerFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pos = getArguments() != null ? getArguments().getInt("pos") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_player_new, container, false);

        mAdView = (AdView) rootView.findViewById(R.id.adView);
        mAdView.setAdListener(new ToastAdListener(getActivity()));
        mAdView.loadAd(new AdRequest.Builder().build());

        baseLvlText = (TextView) rootView.findViewById(R.id.textBaseLvl);
        baseLevel = MainMenu.Players.get(pos).getBase();
        baseLvlText.setText(baseLevel + "");

        bonusLvlText = (TextView) rootView.findViewById(R.id.textBonusLvl);
        bonusLevel = MainMenu.Players.get(pos).getBonus();
        bonusLvlText.setText(bonusLevel + "");

        totalLvlText = (TextView) rootView.findViewById(R.id.textTotalLvl);
        totalLevel = MainMenu.Players.get(pos).getTotal();
        totalLvlText.setText(totalLevel + "");

        //oneShotLvlText = (TextView) rootView.findViewById(R.id.textOneShotLvl);
        //oneShotLvlText.setText(oneShotLevel + "");

        final Button upBtn1 = (Button) rootView.findViewById(R.id.imageBtnUp1);
        final Button downBtn1 = (Button) rootView.findViewById(R.id.imageBtnDown1);
        final Button upBtn2 = (Button) rootView.findViewById(R.id.imageBtnUp2);
        final Button downBtn2 = (Button) rootView.findViewById(R.id.imageBtnDown2);
        //final Button leftBtn = (Button) rootView.findViewById(R.id.imageBtnLeft);
        //final Button rightBtn = (Button) rootView.findViewById(R.id.imageBtnRight);
        final Button diceBtn = (Button) rootView.findViewById(R.id.btnDice);
        final Button fightBtn = (Button) rootView.findViewById(R.id.btnFight);

        //Jordan's Code, custom fonts
        //Declare typefaces
        Typeface header1 = Typeface.createFromAsset(this.getResources().getAssets(),"windlass.ttf");
        Typeface header2 = Typeface.createFromAsset(this.getResources().getAssets(),"pala.ttf");

        final TextView levelText = (TextView) rootView.findViewById(R.id.textLevel);
        final TextView gearText = (TextView) rootView.findViewById(R.id.textGear);
        final TextView strengthText = (TextView) rootView.findViewById(R.id.textStrength);

        levelText.setTypeface(header2);
        gearText.setTypeface(header2);
        strengthText.setTypeface(header2);
        baseLvlText.setTypeface(header2);
        bonusLvlText.setTypeface(header2);
        totalLvlText.setTypeface(header1);
        fightBtn.setTypeface(header2);
        diceBtn.setTypeface(header2);
        checkLevel(downBtn1,baseLevel);

        baseLvlText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                checkLevel(downBtn1,baseLevel);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Setup for upArrow button for base level (All arrow buttons adjust total level as needed)
        upBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (baseLevel != 10) {
                    baseLevel++;
                    MainMenu.Players.get(pos).setBase(baseLevel);
                    baseLvlText.setText(baseLevel + "");

                    totalLevel++;
                    MainMenu.Players.get(pos).setTotal(totalLevel);
                    totalLvlText.setText(totalLevel + "");
                }
                if (baseLevel == 10) {
                    ((LevelMenu)getActivity()).showWinner(pos);
                }
            }
        });
        // Setup for downArrow button for base level
        downBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (baseLevel != 1) {
                    baseLevel--;
                    MainMenu.Players.get(pos).setBase(baseLevel);
                    baseLvlText.setText(baseLevel + "");

                    totalLevel--;
                    MainMenu.Players.get(pos).setTotal(totalLevel);
                    totalLvlText.setText(totalLevel + "");
                }
            }
        });
        // Setup for upArrow button for gear level
        upBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bonusLevel++;
                MainMenu.Players.get(pos).setBonus(bonusLevel);
                bonusLvlText.setText(bonusLevel + "");

                totalLevel++;
                MainMenu.Players.get(pos).setTotal(totalLevel);
                totalLvlText.setText(totalLevel + "");
            }
        });
        // Setup for downArrow button for gear level
        downBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    bonusLevel--;
                    MainMenu.Players.get(pos).setBonus(bonusLevel);
                    bonusLvlText.setText(bonusLevel + "");

                    totalLevel--;
                    MainMenu.Players.get(pos).setTotal(totalLevel);
                    totalLvlText.setText(totalLevel + "");
            }
        });
        // Setup for leftArrow button for one-shot level
//        leftBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (oneShotLevel != 0) {
//                    oneShotLevel--;
//                    oneShotLvlText.setText(oneShotLevel + "");
//
//                    totalLevel--;
//                    totalLvlText.setText(totalLevel + "");
//                }
//            }
//        });
//        // Setup for rightArrow button for one-shot level
//        rightBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                oneShotLevel++;
//                oneShotLvlText.setText(oneShotLevel + "");
//
//                totalLevel++;
//                totalLvlText.setText(totalLevel + "");
//            }
//        });
        // Setup for dice button for running away
        diceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LevelMenu)getActivity()).showDice(getActivity());
            }
        });

        // Setup for fight button for comparing monster and player stats
        fightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LevelMenu)getActivity()).showFightMenu(baseLevel, bonusLevel, oneShotLevel, totalLevel, pos);
            }
        });


        return rootView;
    }

    public void checkLevel(Button button, int level) {
        if (level == 1) {button.setEnabled(false);}
        else {button.setEnabled(true);}
    }

    // Need to override onStart to update baseLevel, bonusLevel, & totalLevel when FightMenu was killed
    @Override
    public void onStart() {
        super.onStart();

        adjustValues();
    }

//    public static void adjustLevel() {
//        baseLevel = newBaseLevel;
//        bonusLevel = newBonusLevel;
//        totalLevel = baseLevel + bonusLevel;
//
//        baseLvlText.setText(baseLevel + "");
//        bonusLvlText.setText(bonusLevel + "");
//        totalLvlText.setText(totalLevel + "");
//    }


    // When Advert is paused
    @Override
    public void onPause() {
        mAdView.pause();
        super.onPause();
    }
    // When Advert resumes
    @Override
    public void onResume() {
        super.onResume();
        mAdView.resume();
    }
    // When Advert is destroyed
    @Override
    public void onDestroy() {
        mAdView.destroy();
        super.onDestroy();
    }

    @Override
    public void adjustValues() {
        baseLevel = MainMenu.Players.get(pos).getBase();
        bonusLevel = MainMenu.Players.get(pos).getBonus();
        totalLevel = MainMenu.Players.get(pos).getTotal();

        baseLvlText.setText(baseLevel + "");
        bonusLvlText.setText(bonusLevel + "");
        totalLvlText.setText(totalLevel + "");
    }
}