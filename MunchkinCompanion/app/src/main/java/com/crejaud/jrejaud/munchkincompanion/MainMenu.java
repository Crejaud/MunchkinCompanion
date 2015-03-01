package com.crejaud.jrejaud.munchkincompanion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;


public class MainMenu extends Activity {

    EditText playerText;
    public static List<Player> Players = new ArrayList<Player>();
    ListView playerListView=null;
    ArrayAdapter<Player> adapter=null;
    int startCounter = 0;
    private AdView mAdView;
    private static final String TAG = playerFragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get rid of top title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_main_menu_new);

        //Jordan's Code, custom fonts
        //Declare typefaces
        Typeface header1 = Typeface.createFromAsset(this.getResources().getAssets(),"windlass.ttf");
        Typeface header2 = Typeface.createFromAsset(this.getResources().getAssets(),"pala.ttf");



        // Initialize Advert
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdListener(new ToastAdListener(this));
        mAdView.loadAd(new AdRequest.Builder().build());

        //Removed as moved from two pages to one
//        mAdView2 = (AdView) findViewById(R.id.adView2);
//        mAdView2.setAdListener(new ToastAdListener(this));
//        mAdView2.loadAd(new AdRequest.Builder().build());

        playerListView = null;
        adapter = null;
        Players = new ArrayList<Player>();

        playerText = (EditText) findViewById(R.id.textPlayer);

        playerListView = (ListView) findViewById(R.id.listView);

//        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
//
//        tabHost.setup();
//
//        TabHost.TabSpec tabSpec = tabHost.newTabSpec("player creation");
//        tabSpec.setContent(R.id.tabCreator);
//        tabSpec.setIndicator("Player Creation");
//        tabHost.addTab(tabSpec);
//
//        tabSpec = tabHost.newTabSpec("player list");
//        tabSpec.setContent(R.id.tabPlayerList);
//        tabSpec.setIndicator("Player List");
//        tabHost.addTab(tabSpec);

        //Declare Elements
        final Button startBtn = (Button) findViewById(R.id.btnStart);
        final Button addBtn = (Button) findViewById(R.id.btnAdd);

        final TextView creationText = (TextView) findViewById(R.id.textCreation);
        //final TextView listText = (TextView) findViewById(R.id.listTitle);

        //Assign custom typefaces
        creationText.setTypeface(header1);
        //listText.setTypeface(header1);
        startBtn.setTypeface(header2);
        addBtn.setTypeface(header2);
        playerText.setTypeface(header2);



        // Setup for the 'Add Player' Button
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlayer(playerText.getText().toString());
                populateList();
                startCounter++;
                if (Players.size() >= 1) {
                    startBtn.setEnabled(true);
                }
                //Toast.makeText(getApplicationContext(), playerText.getText().toString() + " has been added!", Toast.LENGTH_SHORT).show();
                playerText.setText("");
            }
        });

        // Setup for the 'Start' Button
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this, LevelMenu.class);
                startActivityForResult(i, 1);
            }
        });

        // Enables 'Add Player' Button whenever there is text present in the 'Player Name' editText
        playerText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                addBtn.setEnabled(!playerText.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                removeFromList(position);
                adapter.notifyDataSetChanged();

                if(Players.isEmpty()) {
                    startBtn.setEnabled(false);
                }
            }});


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==2){
            setResult(2);
            // This code restarts the game
//            Intent intent = new Intent(getApplicationContext(), Splash.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
            finish();
        }
    }

    // Sends players through adapter in order to create sliding screens
    private void populateList() {
        adapter = new playerListAdapter();
        playerListView.setAdapter(adapter);


    }

    private void removeFromList(int i) {
        adapter.remove(Players.remove(i));
        //Log.d(TAG, "Removed " + Players.get(i).getName() + " from adapter");
        //Log.d(TAG, "Removed " + Players.get(i).getName() + " from Players");
    }

    // An adapter to create the following sliding screens in LevelMenu from the players list
    private class playerListAdapter extends ArrayAdapter<Player> {
        public playerListAdapter() {
            super(MainMenu.this, R.layout.listview_item, Players);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Player currentPlayer = Players.get(position);

            Typeface header2 = Typeface.createFromAsset(view.getResources().getAssets(),"pala.ttf");

            TextView name = (TextView) view.findViewById(R.id.playerName);
            name.setText(currentPlayer.getName());
            name.setTypeface(header2);

            return view;
        }

    }
    // Add player object to players list
    private void addPlayer(String name) {
        Players.add(new Player(name));

    }
    // Accessor: used to get players list from other classes
    public static List<Player> getPlayers() {
        return Players;
    }
    //Mutator: used to erase players list from other classes when restarting the game
    public static void erasePlayers() { Players = new ArrayList<Player>(); }

    // When Advert is paused
    @Override
    protected void onPause() {
        mAdView.pause();
        //mAdView2.pause();
        super.onPause();
    }
    // When Advert resumes
    @Override
    protected void onResume() {
        super.onResume();
        mAdView.resume();
        //mAdView2.resume();
    }
    // When Advert is destroyed
    @Override
    protected void onDestroy() {
        mAdView.destroy();
        //mAdView2.destroy();
        super.onDestroy();
    }

}

// Toast class to signify presence of Advert
class ToastAdListener extends AdListener {
    private Context mContext;

    public ToastAdListener(Context context) {
        this.mContext = context;
    }

    // Used to check that the Advert was being displayed
    /*
    @Override
    public void onAdLoaded() {
        Toast.makeText(mContext, "onAdLoaded()", Toast.LENGTH_SHORT).show();
    }
    */

    @Override
    public void onAdFailedToLoad(int errorCode) {
        String errorReason = "";
        switch(errorCode) {
            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                errorReason = "Internal error";
                break;
            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                errorReason = "Invalid request";
                break;
            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                errorReason = "Network Error";
                break;
            case AdRequest.ERROR_CODE_NO_FILL:
                errorReason = "No fill";
                break;
        }
        Toast.makeText(mContext, String.format("onAdFailedToLoad(%s)", errorReason),
                Toast.LENGTH_SHORT).show();
    }

    // These methods were used to check that the Advert was correctly working
    /*
    @Override
    public void onAdOpened() {
        Toast.makeText(mContext, "onAdOpened()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdClosed() {
        Toast.makeText(mContext, "onAdClosed()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdLeftApplication() {
        Toast.makeText(mContext, "onAdLeftApplication()", Toast.LENGTH_SHORT).show();
    }
    */
}

