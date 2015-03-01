package com.crejaud.jrejaud.munchkincompanion;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by creja_000 on 7/27/2014.
 */
public class FightMenu extends FragmentActivity implements winnerAlertFragment.Communicator{

    public int baseLevel=1, bonusLevel=0, oneShotLevel=0, totalLevel=1, baseMonsterLevel = 1, modifierLevel = 0, totalMonsterLevel = 1;
    public String myName = "";
    public int pos;
    private TextView baseLvlText, bonusLvlText, totalLvlText, oneShotLvlText, baseMonsterLvlText, modifierLvlText, totalMonsterLvlText, nameText, vsText;
    private AdView mAdView;
    private static final String TAG = playerFragment.class.getSimpleName();
    MediaPlayer player;
    private final int DICE_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Get rid of top title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_fight_player_new);

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdListener(new ToastAdListener(this));
        mAdView.loadAd(new AdRequest.Builder().build());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            baseLevel = extras.getInt("baseLevel");
            bonusLevel = extras.getInt("bonusLevel");
            oneShotLevel = extras.getInt("oneShotLevel");
            totalLevel = extras.getInt("totalLevel");
            myName = extras.getString("name");
            pos = extras.getInt("pos");
        }

        nameText = (TextView) findViewById(R.id.textPlayerName);
        nameText.setText(myName);

        baseLvlText = (TextView) findViewById(R.id.textBaseLvl);
        baseLvlText.setText(baseLevel + "");

        bonusLvlText = (TextView) findViewById(R.id.textBonusLvl);
        bonusLvlText.setText(bonusLevel + "");

        totalLvlText = (TextView) findViewById(R.id.textTotalLvl);
        totalLvlText.setText(totalLevel + "");

        oneShotLvlText = (TextView) findViewById(R.id.textOneShotLvl);
        oneShotLvlText.setText(oneShotLevel + "");

        baseMonsterLvlText = (TextView) findViewById(R.id.textMonsterLvl);
        baseMonsterLvlText.setText(baseMonsterLevel + "");

        modifierLvlText = (TextView) findViewById(R.id.textMonsterModLvl);
        modifierLvlText.setText(modifierLevel + "");

        totalMonsterLvlText = (TextView) findViewById(R.id.textMonsterTotalLvl);
        totalMonsterLvlText.setText(totalMonsterLevel + "");

        vsText = (TextView) findViewById(R.id.vs_text);

        final Button upBtnBase = (Button) findViewById(R.id.imageBtnUpLevel);
        final Button downBtnBase = (Button) findViewById(R.id.imageBtnDownLevel);
        final Button upBtnGear = (Button) findViewById(R.id.imageBtnUpGear);
        final Button downBtnGear = (Button) findViewById(R.id.imageBtnDownGear);
        final Button downBtnMod = (Button) findViewById(R.id.imageBtnDownMod);
        final Button upBtnMod = (Button) findViewById(R.id.imageBtnUpMod);
        final Button upBtnMonster = (Button) findViewById(R.id.imageBtnUpMonsterLevel);
        final Button downBtnMonster = (Button) findViewById(R.id.imageBtnDownMonsterLevel);
        final Button upBtnMonsterMod = (Button) findViewById(R.id.imageBtnUpMonsterMod);
        final Button downBtnMonsterMod = (Button) findViewById(R.id.imageBtnDownMonsterMod);
        final Button fightBtn = (Button) findViewById(R.id.btnFight);
        final Button runBtn = (Button) findViewById(R.id.btnRunAway);

        //Jordan's Code, custom fonts
        //Declare typefaces
        Typeface header1 = Typeface.createFromAsset(this.getResources().getAssets(),"windlass.ttf");
        Typeface header2 = Typeface.createFromAsset(this.getResources().getAssets(),"pala.ttf");

        TextView levelText = (TextView) findViewById(R.id.textLevel);
        TextView gearText = (TextView) findViewById(R.id.textGear);
        TextView modifierText = (TextView) findViewById(R.id.textModifier);
        TextView monsterLevelText = (TextView) findViewById(R.id.textMonsterLevel);
        TextView monsterModText = (TextView) findViewById(R.id.textMonsterMod);
        TextView monsterText = (TextView) findViewById(R.id.textMonster);

        nameText.setTypeface(header1);
        vsText.setTypeface(header1);
        levelText.setTypeface(header2);
        gearText.setTypeface(header2);
        modifierText.setTypeface(header2);
        monsterLevelText.setTypeface(header2);
        monsterModText.setTypeface(header2);
        monsterText.setTypeface(header1);
        fightBtn.setTypeface(header2);
        runBtn.setTypeface(header2);
        totalLvlText.setTypeface(header1);
        totalMonsterLvlText.setTypeface(header1);
        baseLvlText.setTypeface(header2);
        bonusLvlText.setTypeface(header2);
        oneShotLvlText.setTypeface(header2);
        baseMonsterLvlText.setTypeface(header2);
        modifierLvlText.setTypeface(header2);

        //Check if base level is not one
        checkLevel(downBtnBase, baseLevel);
        checkLevel(downBtnMonster, baseMonsterLevel);

        baseMonsterLvlText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                checkLevel(downBtnMonster, baseMonsterLevel);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        baseLvlText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                checkLevel(downBtnBase, baseLevel);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        // Setup for upArrow button for base level (All arrow buttons adjust total level as needed)
        upBtnBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (baseLevel != 10) {
                    baseLevel++;
                    baseLvlText.setText(baseLevel + "");

                    totalLevel++;
                    totalLvlText.setText(totalLevel + "");
                }
                if (baseLevel == 10) {
                    showWinner(pos);
                }
            }
        });
        // Setup for downArrow button for base level
        downBtnBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (baseLevel != 1) {
                    baseLevel--;
                    baseLvlText.setText(baseLevel + "");

                    totalLevel--;
                    totalLvlText.setText(totalLevel + "");
                }
            }
        });
        // Setup for upArrow button for gear level
        upBtnGear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bonusLevel++;
                bonusLvlText.setText(bonusLevel + "");

                totalLevel++;
                totalLvlText.setText(totalLevel + "");
            }
        });
        // Setup for downArrow button for gear level
        downBtnGear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bonusLevel--;
                bonusLvlText.setText(bonusLevel + "");

                totalLevel--;
                totalLvlText.setText(totalLevel + "");
            }
        });
        // Setup for downArrow button for one-shot level
        downBtnMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneShotLevel--;
                oneShotLvlText.setText(oneShotLevel + "");

                totalLevel--;
                totalLvlText.setText(totalLevel + "");
            }
        });
        // Setup for upArrow button for one-shot level
        upBtnMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneShotLevel++;
                oneShotLvlText.setText(oneShotLevel + "");

                totalLevel++;
                totalLvlText.setText(totalLevel + "");
            }
        });

        // The following are the for monster stats
        // Setup for upArrow button for base level (All arrow buttons adjust total level as needed)
        upBtnMonster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseMonsterLevel++;
                baseMonsterLvlText.setText(baseMonsterLevel + "");

                totalMonsterLevel++;
                totalMonsterLvlText.setText(totalMonsterLevel + "");
            }
        });
        // Setup for downArrow button for base level
        downBtnMonster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLevel(downBtnMonster, baseMonsterLevel);
                if (baseMonsterLevel != 1) {
                    baseMonsterLevel--;
                    baseMonsterLvlText.setText(baseMonsterLevel + "");

                    totalMonsterLevel--;
                    totalMonsterLvlText.setText(totalMonsterLevel + "");
                }
            }
        });
        // Setup for upArrow button for gear level
        upBtnMonsterMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifierLevel++;
                modifierLvlText.setText(modifierLevel + "");

                totalMonsterLevel++;
                totalMonsterLvlText.setText(totalMonsterLevel + "");
            }
        });
        // Setup for downArrow button for gear level
        downBtnMonsterMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifierLevel--;
                modifierLvlText.setText(modifierLevel + "");

                totalMonsterLevel--;
                totalMonsterLvlText.setText(totalMonsterLevel + "");
            }
        });
        // Setup for Run Away button for rolling dice & delays for 3 seconds
        runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LevelMenu.showDice(FightMenu.this);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        killFightMenu();
                    }

                }, DICE_DISPLAY_LENGTH);

            }
        });
        // Setup for Fight button for displaying if the player won the fight
        fightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = myName + " has LOST to the Monster";
                if (totalLevel > totalMonsterLevel)
                    message = myName + " has WON against the Monster";
                if (totalLevel == totalMonsterLevel)
                    message = myName + " has TIED against the Monster";

                showFightOutcome(message);
                //Toast.makeText(FightMenu.this, message, Toast.LENGTH_SHORT).show();
            }
        });


    }

    // Shows Winner by calling Dialog Box fragment
    public void showWinner(int pos) {
        FragmentManager manager=getSupportFragmentManager();
        winnerAlertFragment myDialog = winnerAlertFragment.newInstance(MainMenu.Players.get(pos).getName());
        player=MediaPlayer.create(this,R.raw.win_song);
        player.start();
        myDialog.show(manager, "MyDialog");
    }

    public void showFightOutcome(String message) {
        FragmentManager manager=getSupportFragmentManager();
        fightAlertFragment myDialog = fightAlertFragment.newInstance(message);
        myDialog.show(manager, "MyDialog");
    }

    @Override
    public void onDialogMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Called to restart game once someone has won (Brings user back to MainMenu
    @Override
    public void restartGame() {
        MainMenu.erasePlayers();
        setResult(2);
        finish();
    }

    public void killFightMenu() {
        MainMenu.Players.get(pos).setBase(baseLevel);
        MainMenu.Players.get(pos).setBonus(bonusLevel);
        int totalLevel = baseLevel + bonusLevel;
        MainMenu.Players.get(pos).setTotal(totalLevel);

        finish();
    }

    public void checkLevel(Button button, int level) {
        if (level == 1) {button.setEnabled(false);}
        else {button.setEnabled(true);}
    }


    // When Advert is paused
    @Override
    protected void onPause() {
        mAdView.pause();
        super.onPause();
    }
    // When Advert resumes
    @Override
    protected void onResume() {
        super.onResume();
        mAdView.resume();
    }
    // When Advert is destroyed
    @Override
    protected void onDestroy() {
        mAdView.destroy();
        super.onDestroy();
    }

}
