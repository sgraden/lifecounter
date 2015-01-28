package edu.washington.sraden.lifecounter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class LifeCounter extends ActionBarActivity {
    private static final String TAG = "LifeCounterMain";
    private ArrayList<Integer> playerInfo = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_counter);

        if (savedInstanceState == null) {
            final int DEFAULT = 2;
            for (int i = 0; i < DEFAULT; i++) {
                playerInfo.add(20);

                String playerID = "player" + (i + 1);
                int playerTableID = getResources().getIdentifier(playerID, "id", getPackageName());
                TableLayout playerTable = (TableLayout) findViewById(playerTableID);

                TextView playerTitle = (TextView) playerTable.findViewById(R.id.player_name);

                String playerTitleText =  getResources().getString(R.string.player_name);
                playerTitle.setText(String.format(playerTitleText, i + 1)); // Sets name of player

                TextView healthView = (TextView) playerTable.getChildAt(2).findViewById(R.id.player_health);
                healthView.setText("" + playerInfo.get(i)); //Set the health to value from arraylist
            }
        }
    }

    public void clickFunction(View v) {
        TableLayout player = (TableLayout) v.getParent().getParent(); //Player view
        String playerID = v.getResources().getResourceName(player.getId()); //String of player id

        TextView playerName = (TextView) player.findViewById(R.id.player_name);

        int playerNum = Integer.parseInt(playerID.substring(playerID.length() -1)) - 1; //Integer value for player in ArrayList
        int currHealth = playerInfo.get(playerNum); //Current health based on arraylist

        TextView health = (TextView) player.getChildAt(2).findViewById(R.id.player_health); //Textview of the health

        switch (v.getTag().toString()) {
            case "Pos1": currHealth += 1;
                break;
            case "Pos5": currHealth += 5;
                break;
            case "Min1": currHealth -= 1;
                break;
            case "Min5": currHealth -= 5;
                break;
        }

        //Sets name of player. If health <= 0 then gets "Loses" tag
        if (currHealth <= 0) {
            playerName.setText(String.format(getResources().getText(R.string.player_name).toString(), playerNum + 1) + " Loses!");
            currHealth = 0;
        } else {
            playerName.setText(String.format(getResources().getText(R.string.player_name).toString(), playerNum + 1));
        }
        health.setText("" + currHealth);
        playerInfo.set(playerNum, currHealth);
    }

    public void addPlayer(View v) {
        if (playerInfo.size() < 8) {
            playerInfo.add(20);

            LinearLayout parent = (LinearLayout) v.getParent();
            String viewStubInflate = "stub" + (playerInfo.size());
            int viewStubIntID = parent.getResources().getIdentifier(viewStubInflate, "id", getPackageName());
            ViewStub stub = (ViewStub) parent.findViewById(viewStubIntID); //find viewstub to inflate
            TableLayout newPlayer = (TableLayout) stub.inflate();

            //Set new player name
            TextView playerName = (TextView) newPlayer.findViewById(R.id.player_name);
            String playerNameText = getResources().getString(R.string.player_name);
            playerName.setText(String.format(playerNameText, playerInfo.size()));

            //Set new player health
            TextView healthView = (TextView) newPlayer.getChildAt(2).findViewById(R.id.player_health);
            healthView.setText("" + playerInfo.get(playerInfo.size() - 1));
        }
    }

    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putInt("numPlayers", playerInfo.size());
        for (int i = 0; i < playerInfo.size(); i++) {
            int playerScore = playerInfo.get(i);
            String playerID = "player" + (i + 1);
            saveInstanceState.putInt(playerID, playerScore);
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Goes through the players and updates their values
        for (int i = 0; i < savedInstanceState.getInt("numPlayers"); i++) {
            if (i > 1) { //Re-inflates the used viewstubs
                View parent = (View) findViewById(R.id.AddPlayer).getParent();
                String viewStubInflate = "stub" + (i + 1);
                //Log.i(TAG, "viewStubInflate: " + viewStubInflate);
                int viewStubIntID = parent.getResources().getIdentifier(viewStubInflate, "id", getPackageName());
                ViewStub stub = (ViewStub) parent.findViewById(viewStubIntID); //find viewstub to inflate
                stub.inflate();
            }

            String playerID = "player" + (i + 1); //String ID of player table

            Log.i(TAG, playerID + ": " + savedInstanceState.getInt(playerID));

            playerInfo.add(i, savedInstanceState.getInt(playerID)); //Add health to the array
            int playerTableID = getResources().getIdentifier(playerID, "id", getPackageName()); //Int ID of player table
            TableLayout playerTable = (TableLayout) findViewById(playerTableID); //Root table of player

            //Set name of player
            TextView playerTitle = (TextView) playerTable.findViewById(R.id.player_name); //Title name of player
            String playerTitleText = getResources().getString(R.string.player_name); //String format of title name

            //Sets name of the player. If <= 0 then they get the "Loses" tag
            if (playerInfo.get(i) <= 0) {
                playerTitle.setText(String.format(playerTitleText, i + 1) + " Loses!");
            } else {
                playerTitle.setText(String.format(playerTitleText, i + 1));
            }

            //Set health value of player
            TextView healthView = (TextView) playerTable.getChildAt(2).findViewById(R.id.player_health);
            healthView.setText("" + playerInfo.get(i)); //Set the health to value from playerInfo array
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_life_counter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
