package com.example.metapigeon;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create files where the main information of the application will be saved
        //-------------------------------------------------------------------------------------------SPELLBOOK DATABASE-------------------------------------------------------------------------------------------------------------------
        createSpell("Shield","1st-level abjuration","PHB, page 275","1 reaction, which you take when you are hit by an attack or targeted by the magic missile spell",
                "Self","V, S","1 round","Sorcerer, Wizard","An invisible barrier of magical force appears and protects you. Until the start of your next turn, you have a +5 bonus to AC, including against the triggering attack, and you take no damage from magic missile.");
        createSpell("Dimension Door","4th-level conjuration","PHB, page 233","1 action","500 feet","V","Instantaneous","Bard, Sorcerer, Warlock, Wizard",
                "You teleport yourself from your current location to any other spot within range. You arrive at exactly the spot desired. It can be a place you can see, one you can visualize, or one you can describe by stating distance and direction, such " +
                        "as \"200 feet straight downward\" or \"upward to the northwest at a 45-degree angle, 300 feet.\" You can bring along objects as long as their weight doesn't exceed what you can carry. You can also bring one willing creature of your size or smaller " +
                        "who is carrying gear up to its carrying capacity. The creature must be within 5 feet of you when you cast this spell. If you would arrive in a place already occupied by an object or a creature, you and any creature traveling with you each take 4d6 force damage, " +
                        "and the spell fails to teleport you.");
        createSpell("Silvery Barbs","1st-level enchantment","SCC, page 38","1 reaction, which you take when a creature you can see within 60 feet of yourself succeeds on an attack roll, an ability check, or a saving throw",
                "60 feet","V","Instantaneous","Bard, Sorcerer, Wizard","You magically distract the triggering creature and turn its momentary uncertainty into encouragement for another creature. The triggering creature must reroll the d20 " +
                        "and use the lower roll. You can then choose a different creature you can see within range (you can choose yourself). The chosen creature has advantage on the next attack roll, ability check, or saving throw it makes within 1 minute. A creature can be empowered " +
                        "by only one use of this spell at a time.");
        createSpell("Fireball","3rd-level evocation","PHB, page 241","1 action","150 feet","V, S, M (a tiny ball of bat guano and sulfur)","Instantaneous","Sorcerer, Wizard",
                "A bright streak flashes from your pointing finger to a point you choose within range and then blossoms with a low roar into an explosion of flame. Each creature in a 20-foot-radius sphere centered on that point must make a Dexterity saving throw. " +
                        "A target takes 8d6 fire damage on a failed save, or half as much damage on a successful one. The fire spreads around corners. It ignites flammable objects in the area that aren't being worn or carried. " +
                        "At Higher Levels. When you cast this spell using a spell slot of 4th level or higher, the damage increases by 1d6 for each slot level above 3rd.");
        createSpell("Spiritual Weapon","2nd-level evocation","PHB, page 278","1 bonus action","60 feet","V, S","1 minute","Cleric",
                "You create a floating, spectral weapon within range that lasts for the duration or until you cast this spell again. When you cast the spell, you can make a melee spell attack against a creature within 5 feet of the weapon. On a hit, the target takes force damage " +
                        "equal to 1d8 + your spellcasting ability modifier. As a bonus action on your turn, you can move the weapon up to 20 feet and repeat the attack against a creature within 5 feet of it. The weapon can take whatever form you choose. Clerics of deities who are associated " +
                        "with a particular weapon (as St. Cuthbert is known for his mace and Thor for his hammer) make this spell's effect resemble that weapon. " +
                        "At Higher Levels. When you cast this spell using a spell slot of 3rd level or higher, the damage increases by 1d8 for every two slot levels above 2nd.");
        createSpell("Booming Blade","Cantrip evocation","TCE, page 106","1 action","Self (5-foot radius)","S, M (a melee weapon worth at least 1 sp)"," 1 round"," Artificer, Sorcerer, Warlock, Wizard",
                "You brandish the weapon used in the spell's casting and make a melee attack with it against one creature within 5 feet of you. On a hit, the target suffers the weapon attack's normal effects and then becomes sheathed in booming energy until the start of your next turn. " +
                        "If the target willingly moves 5 feet or more before then, the target takes 1d8 thunder damage, and the spell ends. This spell's damage increases when you reach certain levels. At 5th level, the melee attack deals an extra 1d8 thunder damage to the target on a hit, and the " +
                        "damage the target takes for moving increases to 2d8. Both damage rolls increase by 1d8 at 11th level (2d8 and 3d8) and again at 17th level (3d8 and 4d8).");
        createSpell("Mage Hand","Cantrip conjuration","PHB, page 256","1 action","30 feet","V, S","1 minute","Artificer, Bard, Sorcerer, Warlock, Wizard",
                "A spectral, floating hand appears at a point you choose within range. The hand lasts for the duration or until you dismiss it as an action. The hand vanishes if it is ever more than 30 feet away from you or if you cast this spell again. You can use your action to control " +
                        "the hand. You can use the hand to manipulate an object, open an unlocked door or container, stow or retrieve an item from an open container, or pour the contents out of a vial. You can move the hand up to 30 feet each time you use it. The hand can't attack, activate magic " +
                        "items, or carry more than 10 pounds.");
        createSpell("Toll the Dead","Cantrip necromancy","XGE, page 169","1 action","60 feet","V, S","Instantaneous","Cleric, Warlock, Wizard",
                "You point at one creature you can see within range, and the sound of a dolorous bell fills the air around it for a moment. The target must succeed on a Wisdom saving throw or take 1d8 necrotic damage. If the target is missing any of its hit points, it instead takes 1d12 " +
                        "necrotic damage. The spell's damage increases by one die when you reach 5th level (2d8 or 2d12), 11th level (3d8 or 3d12), and 17th level (4d8 or 4d12).");
        createSpell("Mage Armor","1st-level abjuration","PHB, page 256","1 action","Touch","V, S, M (a piece of cured leather)","8 hours","Sorcerer, Wizard",
                "You touch a willing creature who isn't wearing armor, and a protective magical force surrounds it until the spell ends. The target's base AC becomes 13 + its Dexterity modifier. The spell ends if the target dons armor or if you dismiss the spell as an action.");
        createSpell("Danse Macabre","5th-level necromancy","XGE, page 153","1 action","60 feet","V, S","Concentration, up to 1 hour","Warlock, Wizard",
                "Threads of dark power leap from your fingers to pierce up to five Small or Medium corpses you can see within range. Each corpse immediately stands up and becomes undead. You decide whether it is a zombie or a skeleton (the statistics for zombies and skeletons are in the " +
                        "Monster Manual), and it gains a bonus to its attack and damage rolls equal to your spellcasting ability modifier. You can use a bonus action to mentally command the creatures you make with this spell, issuing the same command to all of them. To receive the command, a " +
                        "creature must be within 60 feet of you. You decide what action the creatures will take and where they will move during their next turn, or you can issue a general command, such as to guard a chamber or passageway against your foes. If you issue no commands, the creatures " +
                        "do nothing except defend themselves against hostile creatures. Once given an order, the creatures continue to follow it until their task is complete. The creatures are under your control until the spell ends, after which they become inanimate once more. " +
                        "At Higher Levels. When you cast this spell using a spell slot of 6th level or higher, you animate up to two additional corpses for each slot level above 5th.");
        createSpell("Scorching Ray","2nd-level evocation","PHB, page 273","1 action","120 feet","V, S","Instantaneous","Sorcerer, Wizard",
                "You create three rays of fire and hurl them at targets within range. You can hurl them at one target or several. Make a ranged spell attack for each ray. On a hit, the target takes 2d6 fire damage. " +
                        "At Higher Levels. When you cast this spell using a spell slot of 3rd level or higher, you create one additional ray for each slot level above 2nd.");
        createSpell("Blur","2nd-level illusion","PHB, page 219","1 action","Self","V", "Concentration, up to 1 minute","Artificer, Sorcerer, Wizard",
                "Your body becomes blurred, shifting and wavering to all who can see you. For the duration, any creature has disadvantage on attack rolls against you. An attacker is immune to this effect if it doesn't rely on sight, as with blindsight, or can see through " +
                        "illusions, as with truesight.");
        createSpell("Mirror Image","2nd-level illusion","PHB, page 260","1 action","Self","V, S","1 minute","Sorcerer, Warlock, Wizard",
                "Three illusory duplicates of yourself appear in your space. Until the spell ends, the duplicates move with you and mimic your actions, shifting position so it's impossible to track which image is real. You can use your action to dismiss the illusory duplicates. " +
                        "Each time a creature targets you with an attack during the spell's duration, roll a d20 to determine whether the attack instead targets one of your duplicates. If you have three duplicates, you must roll a 6 or higher to change the attack's target to a duplicate. " +
                        "With two duplicates, you must roll an 8 or higher. With one duplicate, you must roll an 11 or higher. A duplicate's AC equals 10 + your Dexterity modifier. If an attack hits a duplicate, the duplicate is destroyed. A duplicate can be destroyed only by an attack " +
                        "that hits it. It ignores all other damage and effects. The spell ends when all three duplicates are destroyed. A creature is unaffected by this spell if it can't see, if it relies on senses other than sight, such as blindsight, or if it can perceive illusions as false, " +
                        "as with truesight.");

        //-------------------------------------------------------------------------------------------MONSTERBOOK DATABASE-------------------------------------------------------------------------------------------------------------------
        createMonster("Goblin" ,"Small Humanoid (Goblinoid), Neutral Evil", "15", "7", "30 ft.", "8", "14", "10","10","8","8");
        createMonster("Wolf" ,"Medium Beast, Unaligned", "13", "11", "40 ft.", "12", "15", "12","3","12","6");
        createMonster("Adult Blue Dragon" ,"Huge Dragon, Lawful Evil", "19", "225", "40 ft., burrow 30 ft., fly 80 ft.", "25", "10", "23","16","15","19");
        createMonster("Beholder" ,"Large Aberration, Lawful Evil", "18", "180", "0 ft., fly 20 ft. (hover)", "10", "14", "18","17","15","17");
        createMonster("Kobolds" ,"Small Humanoid (Kobold), Lawful Evil", "12", "5", "30 ft.", "7", "15", "9","8","7","8");

        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                Intent intent;

                if(loginData()){
                    //If you decided to save your login information
                    intent = new Intent(MainActivity.this, MenuActivity.class);
                } else {
                    //If you are not registered or simply did not want to save your login information
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                }
                //Run the corresponding Activity and delete the current Activity
                startActivity(intent);
                finish();
            }//run
        };//task

        //Object to set the timeout to 2.5 seconds
        Timer time = new Timer();
        time.schedule(task, 2500);

    }//onCreate

    //Check if there is userdata and password stored in the mobile
    //SharePreferences allows you to store configuration values
    private boolean loginData(){
        //Define the object where the information will be stored
        SharedPreferences metadata = getSharedPreferences( "user.dat",MODE_PRIVATE);
        //Returns false if there is no previously stored information
        return metadata.getBoolean("register", false);
    }//loginData

    private void createSpell(String name, String school, String source, String time, String range, String component, String duration, String classes , String description) {

        String spellFile = name.trim() + ".txt";
        String[] fileList = fileList();

        //Check if the file already exists, if it already exists nothing is done
        if( !checkFiles(fileList, spellFile)){
            try{
                OutputStreamWriter internalFile = new OutputStreamWriter(openFileOutput(spellFile, Activity.MODE_PRIVATE));
                internalFile.write(name + "\n" + school + "\n" + source + "\n" + time + "\n" + range + "\n" + component + "\n" + duration + "\n" + classes + "\n" + description);
                internalFile.flush();
                internalFile.close();
                Toast.makeText(getApplicationContext(), "Database Updated", Toast.LENGTH_LONG).show();
            } catch(IOException e) {
                Toast.makeText(getApplicationContext(), "ERROR creating spell", Toast.LENGTH_LONG).show();
            }
        }
    }//createSpell

    private void createMonster(String name, String type, String ac, String hp, String speed, String str, String dex, String con, String intel, String wis, String cha) {

        String monsterFile = name + ".txt";
        String[] fileList = fileList();

        //Check if the file already exists, if it already exists nothing is done
        if( !checkFiles(fileList, monsterFile)){
            try{
                OutputStreamWriter internalFile = new OutputStreamWriter(openFileOutput(monsterFile, Activity.MODE_PRIVATE));
                internalFile.write(name + "\n" + type + "\n" + ac + "\n" + hp + "\n" + speed + "\n" + str + "\n" + dex + "\n" + con + "\n" + intel + "\n" + wis +"\n" + cha);
                internalFile.flush();
                internalFile.close();
            } catch(IOException e) {
                Toast.makeText(getApplicationContext(), "ERROR creating monster", Toast.LENGTH_LONG).show();
            }
        }
    }//createMonster

    //Validate files within the device
    private boolean checkFiles (String[] filesList, String fileSearch){
        //Loop through the list of files to validate that they exist
        for (String file : filesList) {
            if (fileSearch.equals(file)) {
                return true;
            }
        }
        return false;
    }//checkFiles

}//class