package com.crejaud.jrejaud.munchkincompanion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LevelMenu extends FragmentActivity implements winnerAlertFragment.Communicator{

    ViewPager viewPager=null;
    PagerTitleStrip viewPagerTitleStrip;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get rid of top title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_level_menu);

        List<Player> Players = MainMenu.getPlayers();

        Typeface header1 = Typeface.createFromAsset(this.getResources().getAssets(),"windlass.ttf");
        Typeface header2 = Typeface.createFromAsset(this.getResources().getAssets(),"pala.ttf");

        viewPagerTitleStrip = (PagerTitleStrip) findViewById(R.id.title);

        for (int index = 0; index <= Players.size(); index++) {
            if (viewPagerTitleStrip.getChildAt(index) instanceof TextView)
                ((TextView) viewPagerTitleStrip.getChildAt(index)).setTypeface(header1);
        }

        // Setup for adapter (Allowing sliding screens)
        viewPager= (ViewPager) findViewById(R.id.pager);
        FragmentManager fragmentManager=getSupportFragmentManager();
        viewPager.setAdapter(new MyAdapter(fragmentManager));


    }
    // Shows Winner by calling Dialog Box fragment
    public void showWinner(int pos) {
        FragmentManager manager=getSupportFragmentManager();
        winnerAlertFragment myDialog = winnerAlertFragment.newInstance(MainMenu.Players.get(pos).getName());
        player=MediaPlayer.create(this,R.raw.win_song);
        player.start();
        myDialog.show(manager, "MyDialog");
    }
    // Called to restart game once someone has won (Brings user back to MainMenu
    @Override
    public void restartGame() {
        MainMenu.erasePlayers();
        setResult(2);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==2){
            setResult(2);
            finish();
        }
    }

    public static void showDice(Context v) {
        int diceNum = 1 + (int)(Math.random()*6); //Get random number from 1-6

        ImageView diceImageView = new ImageView(v);

        if (diceNum==1) { diceImageView.setImageResource(R.drawable.dice_roll_1); }
        if (diceNum==2) { diceImageView.setImageResource(R.drawable.dice_roll_2); }
        if (diceNum==3) { diceImageView.setImageResource(R.drawable.dice_roll_3); }
        if (diceNum==4) { diceImageView.setImageResource(R.drawable.dice_roll_4); }
        if (diceNum==5) { diceImageView.setImageResource(R.drawable.dice_roll_5); }
        if (diceNum==6) { diceImageView.setImageResource(R.drawable.dice_roll_6); }

        Toast toast = new Toast(v);
        toast.setView(diceImageView);
        toast.show();
        //toast.show();
    }

    public void showFightMenu(int baseLevel, int bonusLevel, int oneShotLevel, int totalLevel, int pos) {
        Intent i = new Intent(LevelMenu.this, FightMenu.class);
        i.putExtra("baseLevel",baseLevel);
        i.putExtra("bonusLevel",bonusLevel);
        i.putExtra("oneShotLevel",oneShotLevel);
        i.putExtra("totalLevel",totalLevel);
        i.putExtra("name", MainMenu.Players.get(pos).getName());
        i.putExtra("pos", pos);

        startActivityForResult(i, 1);
    }

    @Override
    public void onDialogMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public interface AdjustPlayerFragment {
        public void adjustValues();
    }

}

// Adapter that actually creates the fragments using playFragment.java, gives titles, and sets positions for each fragment
class MyAdapter extends FragmentPagerAdapter
{

    List<Fragment> playerFrags = new ArrayList<Fragment>();

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;

        for(int j = 0; j < MainMenu.getPlayers().size(); j++) {
            playerFrags.add(j, playerFragment.newInstance(j, MainMenu.Players.get(i).getBase(), MainMenu.Players.get(i).getBonus()));
        }
        if (i >= 0 && i < MainMenu.getPlayers().size()) {
            fragment = playerFrags.get(i);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return MainMenu.getPlayers().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position >= 0 && position < MainMenu.getPlayers().size())
            return MainMenu.getPlayers().get(position).getName();

        return null;
    }

}
