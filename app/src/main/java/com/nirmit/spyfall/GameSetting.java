package com.nirmit.spyfall;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;


public class GameSetting extends Activity {

    // variables to use in different activities (same copy)
    private static String location = "";          // gets the location
    private static int players = 0;               // number of players
    private static Collection<String> roles;      // holds roles associated with the location
    private static List<String> keysList;         // holds keys/ locations
    private static Multimap<String, String>
            gameMap = ArrayListMultimap.create(); // multimap to store key and values

    // Variables to get references to the widgets
    EditText numPlayers;
    Button showListBtn, hostBtn, noHostBtn;
    RelativeLayout playerInfo;

    Random rand = new Random();   // Instantiating class to get a random integer

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        GameSetting.location = location;
    }

    public static Collection<String> getRoles() {
        return roles;
    }

    public static void setRoles(Collection<String> roles) {
        GameSetting.roles = roles;
    }

    public static int getPlayers() {
        return players;
    }

    public static Multimap<String, String> getGameMap() {
        return gameMap;
    }

    // ---- Getter methods ----

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_game_setting);
        roles = new ArrayList<>();

        // getting references to the widgets
        numPlayers = findViewById(R.id.playerNumber);
        playerInfo = findViewById(R.id.playerInfo);
        hostBtn = findViewById(R.id.hostBtn);
        noHostBtn = findViewById(R.id.noHostBtn);
        showListBtn = findViewById(R.id.showListBtn);

        addClickEffect(hostBtn);
        addClickEffect(noHostBtn);
        addClickEffect(showListBtn);

        // Set locations + Roles
        setGameRoles(gameMap);
        keysList = new ArrayList<>(gameMap.keySet());

        hostGame();    // 1 narrator
        noHostGame();  // no host, all plays.
        showList();    // Alert dialog with default items is shown to the user

    }

    // host game selected (1 narrator).
    public void hostGame() {
        hostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get number of players and set up next intent
                if (playersEntered()) {

                    players = Integer.parseInt(numPlayers.getText().toString()); // getting players

                    Intent intent = new Intent(GameSetting.this, EnterRoles.class);
                    startActivity(intent);

                } else {
                    // error is displayed if wrong input
                    numPlayers.setError("Enter valid player number (> 2)");
                }
            }
        });

    }

    // no-host game selected (Every player plays)
    public void noHostGame() {
        noHostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playersEntered()) {

                    players = Integer.parseInt(numPlayers.getText().toString()); // getting players

                    // randomly location from the array is selected.
                    location = keysList.get(rand.nextInt(keysList.size()));
                    roles = gameMap.get(location);

                    // goes to the next activity (Display Roles)
                    Intent intent = new Intent(GameSetting.this, DisplayRoles.class);
                    startActivity(intent);

                } else {
                    // error is displayed if wrong input
                    numPlayers.setError("Enter valid player number (> 2)");
                }
            }
        });
    }

    public void showList() {
        showListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String totalMessage = "";

                // Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(GameSetting.this);
                builder.setCancelable(true);

                //button to allow the user to go back.
                builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                // setting the string based on list array.
                for (int i = 0; i < keysList.size(); i++) {
                    totalMessage += keysList.get(i) + "\n";
                    System.out.println("i = " + i + ", location = " + keysList.get(i));
                    Collection<String> roles = gameMap.get(keysList.get(i));
                    for (int j = 0; j < roles.size(); j++) {
                        totalMessage += "\t\t• " + roles.toArray()[j] + "\n";
                    }
                }

                // Chain together various setter methods to set the dialog characteristics
                builder.setMessage(totalMessage);
                builder.setTitle("Locations");

                // dialog is displayed on the screen.
                builder.show();

            }
        });
    }

    // checks to see if correct number of players is entered or not.
    public boolean playersEntered() {
        boolean result = true;
        if (numPlayers.getText().toString().equals("") || Integer.parseInt(numPlayers.getText().toString()) < 3) {
            result = false;
        }
        return result;
    }


    // ---- Setter methods ----

    // Adds click effect on the round buttons
    void addClickEffect(View view) {
        Drawable drawableNormal = view.getBackground();

        Drawable drawablePressed = view.getBackground().getConstantState().newDrawable();
        drawablePressed.mutate();
        drawablePressed.setColorFilter(Color.argb(100, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);

        StateListDrawable listDrawable = new StateListDrawable();
        listDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);
        listDrawable.addState(new int[]{}, drawableNormal);
        view.setBackground(listDrawable);
    }

    public List<String> getKeysList() {
        return keysList;
    }


    //--- Data structure which holds Location + Roles ---

    private void setGameRoles(Multimap<String, String> gameMap) {

        gameMap.put("Airport", "Flight Attendant");
        gameMap.put("Airport", "Pilot");
        gameMap.put("Airport", "Service Agent");
        gameMap.put("Airport", "Traffic Controller");
        gameMap.put("Airport", "Flight Dispatcher");
        gameMap.put("Airport", "Captain");
        gameMap.put("Airport", "Passanger");
        gameMap.put("Airport", "Co-pilot");

        gameMap.put("Amusement Park", "Ride Controller");
        gameMap.put("Amusement Park", "Clean-up Crew Member");
        gameMap.put("Amusement Park", "Ride Mechanic");
        gameMap.put("Amusement Park", "Ticket Seller");
        gameMap.put("Amusement Park", "Security Guard");

        gameMap.put("Bank", "Trader");
        gameMap.put("Bank", "Fund Manager");
        gameMap.put("Bank", "Client Adviser");
        gameMap.put("Bank", "Security & Fraud Specialist");
        gameMap.put("Bank", "Teller");
        gameMap.put("Bank", "Loan Officer");
        gameMap.put("Bank", "Robber");

        gameMap.put("Baseball Stadium", "Player");
        gameMap.put("Baseball Stadium", "Captain");
        gameMap.put("Baseball Stadium", "Security Guard");
        gameMap.put("Baseball Stadium", "Referee");
        gameMap.put("Baseball Stadium", "Coach");
        gameMap.put("Baseball Stadium", "Fielder");
        gameMap.put("Baseball Stadium", "Cheerleader");

        gameMap.put("Beach", "Lifeguard");
        gameMap.put("Beach", "Garbage Collector");
        gameMap.put("Beach", "Visitor");
        gameMap.put("Beach", "Boat Driver ");
        gameMap.put("Beach", "Swimmer");
        gameMap.put("Beach", "Photographer");
        gameMap.put("Beach", "Surfer");
        gameMap.put("Beach", "Diver");

        gameMap.put("Broadway Theater", "Stage Manager");
        gameMap.put("Broadway Theater", "Actor");
        gameMap.put("Broadway Theater", "Show Viewer");
        gameMap.put("Broadway Theater", "Makeup Artist");
        gameMap.put("Broadway Theater", "Scenic Carpenter");

        gameMap.put("Camping Resort", "Site Manager");
        gameMap.put("Camping Resort", "Tourist Guider");
        gameMap.put("Camping Resort", "Parking Enforcement Officer");
        gameMap.put("Camping Resort", "Tent Seller");

        gameMap.put("Car Shop", "Salesman");
        gameMap.put("Car Shop", "Mechanic");
        gameMap.put("Car Shop", "Manager");
        gameMap.put("Car Shop", "Administrator");
        gameMap.put("Car Shop", "Delivery Person");
        gameMap.put("Car Shop", "Shopper");

        gameMap.put("Casino", "Cage Cashier");
        gameMap.put("Casino", "Dealer");
        gameMap.put("Casino", "Surveillance Manager");
        gameMap.put("Casino", "Security Guard");
        gameMap.put("Casino", "Accountant");
        gameMap.put("Casino", "Gambler");

        gameMap.put("Carnival", "Announcer");
        gameMap.put("Carnival", "Salesman");
        gameMap.put("Carnival", "Police Officer");
        gameMap.put("Carnival", "Game Organizer");
        gameMap.put("Carnival", "Ride Operator");

        gameMap.put("Church", "Priest");
        gameMap.put("Church", "Choir Singer");
        gameMap.put("Church", "Sinner");
        gameMap.put("Church", "Tourist");
        gameMap.put("Church", "Visitor");
        gameMap.put("Church", "Sponsor");

        gameMap.put("Circus", "Entertainer");
        gameMap.put("Circus", "Clown");
        gameMap.put("Circus", "Stage Manager");
        gameMap.put("Circus", "Makeup Artist");
        gameMap.put("Circus", "Show Planner");

        gameMap.put("Cruise", "Cook");
        gameMap.put("Cruise", "Captain");
        gameMap.put("Cruise", "Mechanic");
        gameMap.put("Cruise", "Lifeguard");
        gameMap.put("Cruise", "Casino Dealer");
        gameMap.put("Cruise", "Waiter");
        gameMap.put("Cruise", "Stand-up Comedy Artist");

        gameMap.put("Embassy", "Political Officer");
        gameMap.put("Embassy", "Consular Officer");
        gameMap.put("Embassy", "Ambassador");
        gameMap.put("Embassy", "Mailroom Clerk");
        gameMap.put("Embassy", "Administrative Coordinator");
        gameMap.put("Embassy", "Refugee");
        gameMap.put("Embassy", "Government Official");
        gameMap.put("Embassy", "Secretary");

        gameMap.put("Hospital", "Doctor");
        gameMap.put("Hospital", "Nurse");
        gameMap.put("Hospital", "Pharmacists");
        gameMap.put("Hospital", "Medical Lab Technologists");
        gameMap.put("Hospital", "Therapist");
        gameMap.put("Hospital", "Surgeon");
        gameMap.put("Hospital", "Patient");

        gameMap.put("Hotel", "Manger");
        gameMap.put("Hotel", "Chef");
        gameMap.put("Hotel", "Housekeeper");
        gameMap.put("Hotel", "Waiter");
        gameMap.put("Hotel", "Concierge");
        gameMap.put("Hotel", "Security Guard");
        gameMap.put("Hotel", "Bartender");

        gameMap.put("Jail", "Inmate");
        gameMap.put("Jail", "Police Officer");
        gameMap.put("Jail", "Surveillance Officer");
        gameMap.put("Jail", "Health Inspector");
        gameMap.put("Jail", "Chef");

        gameMap.put("Laboratory", "Assistant Technician");
        gameMap.put("Laboratory", "Laboratory Manager");
        gameMap.put("Laboratory", "Report Analyser");
        gameMap.put("Laboratory", "Sterile Processor");

        gameMap.put("Military Base", "Tank Rider");
        gameMap.put("Military Base", "Medic");
        gameMap.put("Military Base", "Militant");
        gameMap.put("Military Base", "Captain");
        gameMap.put("Military Base", "Commander");
        gameMap.put("Military Base", "Sniper");
        gameMap.put("Military Base", "Colonel");
        gameMap.put("Military Base", "Nurse");

        gameMap.put("Movie Studio", "Production Manager");
        gameMap.put("Movie Studio", "Actor");
        gameMap.put("Movie Studio", "Makeup Artist");
        gameMap.put("Movie Studio", "Stunt Artist");
        gameMap.put("Movie Studio", "Director");
        gameMap.put("Movie Studio", "Light Crew Member");
        gameMap.put("Movie Studio", "Cameraman");

        gameMap.put("Movie Theater", "Ticket Seller");
        gameMap.put("Movie Theater", "Ticker Checker");
        gameMap.put("Movie Theater", "Store Clerk");
        gameMap.put("Movie Theater", "Manager");
        gameMap.put("Movie Theater", "Projector Handler");

        gameMap.put("Museum", "Director");
        gameMap.put("Museum", "Curator");
        gameMap.put("Museum", "Registrar");
        gameMap.put("Museum", "Educator");
        gameMap.put("Museum", "Docent");
        gameMap.put("Museum", "Exhibit Designer");
        gameMap.put("Museum", "Shop Manager");

        gameMap.put("Ocean liner", "Deck Crew Member");
        gameMap.put("Ocean liner", "Cruise Director");
        gameMap.put("Ocean liner", "Casino Staff");
        gameMap.put("Ocean liner", "Entertainer");
        gameMap.put("Ocean liner", "Lifeguard");
        gameMap.put("Ocean liner", "Message Tehrapists");

        gameMap.put("Office", "Engineer");
        gameMap.put("Office", "Tester");
        gameMap.put("Office", "Manager");
        gameMap.put("Office", "Executive Vise President");
        gameMap.put("Office", "CEO");

        gameMap.put("Pirate Ship", "The Captain");
        gameMap.put("Pirate Ship", "Quartermaster");
        gameMap.put("Pirate Ship", "Boatswain");
        gameMap.put("Pirate Ship", "Gunner");
        gameMap.put("Pirate Ship", "Slave");
        gameMap.put("Pirate Ship", "Sailor");
        gameMap.put("Pirate Ship", "Prisoner");
        gameMap.put("Pirate Ship", "Carpenter");

        gameMap.put("Police Station", "Officer");
        gameMap.put("Police Station", "Chief");
        gameMap.put("Police Station", "Detective");
        gameMap.put("Police Station", "Crime Scene Investigator");
        gameMap.put("Police Station", "Victim Advocate");

        gameMap.put("Parliament", "Senator");
        gameMap.put("Parliament", "PM");
        gameMap.put("Parliament", "Judge");
        gameMap.put("Parliament", "Foreign Minister");
        gameMap.put("Parliament", "Trade Minister");
        gameMap.put("Parliament", "Financial Advisor");

        gameMap.put("Polar Station", "Biologist");
        gameMap.put("Polar Station", "Geologist");
        gameMap.put("Polar Station", "Hydrologist");
        gameMap.put("Polar Station", "Medic");
        gameMap.put("Polar Station", "Radioman");
        gameMap.put("Polar Station", "Control Room Employee");

        gameMap.put("Restaurant", "Chef");
        gameMap.put("Restaurant", "Waiter");
        gameMap.put("Restaurant", "Bartender");
        gameMap.put("Restaurant", "Catering Manager");
        gameMap.put("Restaurant", "Musician");
        gameMap.put("Polar Station", "Owner");
        gameMap.put("Polar Station", "Customer");

        gameMap.put("Spa", "Therapists");
        gameMap.put("Spa", "Pedicurist");
        gameMap.put("Spa", "Manicurist");
        gameMap.put("Spa", "Facial Massage Person");
        gameMap.put("Spa", "Waxing Person");
        gameMap.put("Spa", "Stylist");
        gameMap.put("Spa", "Makeup Artist");
        gameMap.put("Spa", "Customer");

        gameMap.put("Service Station", "Gas Filler");
        gameMap.put("Service Station", "Lotty Ticket Seller");
        gameMap.put("Service Station", "Trash Collector");
        gameMap.put("Service Station", "Store Clerk");
        gameMap.put("Service Station", "Tire Pressure checker");

        gameMap.put("Space Station", "Spaceflight Participant");
        gameMap.put("Space Station", "Astronaut");
        gameMap.put("Space Station", "Researcher");
        gameMap.put("Space Station", "Engineer");
        gameMap.put("Space Station", "Flight Controller");
        gameMap.put("Space Station", "Alien");

        gameMap.put("Submarine", "Fire Control Technician");
        gameMap.put("Submarine", "Solar Technician");
        gameMap.put("Submarine", "Controller");
        gameMap.put("Submarine", "Mechinist Mate");
        gameMap.put("Submarine", "Handler");
        gameMap.put("Submarine", "Navigator");

        gameMap.put("Supermarket", "Manger");
        gameMap.put("Supermarket", "Cashier");
        gameMap.put("Supermarket", "Stock Clerk");
        gameMap.put("Supermarket", "Florist Assistant");
        gameMap.put("Supermarket", "Customer Service Representative");

        gameMap.put("School", "Principle");
        gameMap.put("School", "Guidance Counselor");
        gameMap.put("School", "Library Technician");
        gameMap.put("School", "Teacher");
        gameMap.put("School", "Invigilator");
        gameMap.put("School", "Administrator");

        gameMap.put("Strip Club", "Security Guard");
        gameMap.put("Strip Club", "Entertainer");
        gameMap.put("Strip Club", "Dancer");
        gameMap.put("Strip Club", "Manger");
        gameMap.put("Strip Club", "Announcer");

        gameMap.put("Subway", "Transit Operator");
        gameMap.put("Subway", "Schedule Maker");
        gameMap.put("Subway", "Manager");
        gameMap.put("Subway", "Mechanic");
        gameMap.put("Subway", "Garbage Collector");

        gameMap.put("Train Station", "Conductor");
        gameMap.put("Train Station", "Locomotive Engineer");
        gameMap.put("Train Station", "Constable");
        gameMap.put("Train Station", "Ticket Seller");
        gameMap.put("Train Station", "Cleaner");

        gameMap.put("University", "Professor");
        gameMap.put("University", "Administrator");
        gameMap.put("University", "Financial Counselor");
        gameMap.put("University", "Academic Adviser");
        gameMap.put("University", "Lab Technician");
        gameMap.put("University", "Researcher");
        gameMap.put("University", "Student");
        gameMap.put("University", "Teacher's Assistant");

        gameMap.put("Work Party", "HR");
        gameMap.put("Work Party", "Secretary");
        gameMap.put("Work Party", "Manager");
        gameMap.put("Work Party", "CEO");
        gameMap.put("Work Party", "Delivery Boy");
        gameMap.put("Work Party", "Party Planner");
        gameMap.put("Work Party", "Dancer");

        gameMap.put("Zoo", "Veterinary Technician");
        gameMap.put("Zoo", "General Curator");
        gameMap.put("Zoo", "Director");
        gameMap.put("Zoo", "Animal Curator");
        gameMap.put("Zoo", "Zoologist");
        gameMap.put("Zoo", "Educator");

    }
}