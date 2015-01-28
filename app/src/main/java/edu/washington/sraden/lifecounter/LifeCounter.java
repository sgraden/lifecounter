package edu.washington.sraden.lifecounter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
            final int DEFAULT = 4;
            for (int i = 0; i < DEFAULT; i++) {
                playerInfo.add(i, 20);

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
        int playerNum = Integer.parseInt(playerID.substring(playerID.length() -1)) - 1; //Integer value for player space

        TextView health = (TextView) player.getChildAt(2).findViewById(R.id.player_health); //Textview of the health
        int currHealth = playerInfo.get(playerNum); //Current health based on arraylist

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
        if (currHealth < 0) {
            currHealth = 0;
        }
        health.setText("" + currHealth);
        playerInfo.set(playerNum, currHealth);
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

        for (int i = 0; i < savedInstanceState.getInt("numPlayers"); i++) {
            String playerID = "player" + (i + 1); //String ID of player table
            playerInfo.add(i, savedInstanceState.getInt(playerID)); //Add health to the array
            int playerTableID = getResources().getIdentifier(playerID, "id", getPackageName()); //Int ID of player table

            TableLayout playerTable = (TableLayout) findViewById(playerTableID); //Root table of player

            //Set name of player
            TextView playerTitle = (TextView) playerTable.findViewById(R.id.player_name); //Title name of player
            String playerTitleText = getResources().getString(R.string.player_name); //String format of title name
            playerTitle.setText(String.format(playerTitleText, i + 1)); // Sets name of player

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
