package com.example.metapigeon;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.metapigeon.ui.main.DBController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;

public class BooksFragment extends Fragment {

    private View vista;
    private FloatingActionButton FAB;
    private GridView grid;
    private Boolean bandera = false;
    DBController adm;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public BooksFragment() {
        // Required empty public constructor
    }

    public static BooksFragment newInstance(String param1, String param2) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_books, container, false);
        FAB = vista.findViewById(R.id.FAB);
        grid = vista.findViewById(R.id.gvBooks);

        grid.setAdapter(new GalleryAdapter(this.getContext()));

        adm = new DBController(getContext(), "metapigeonDB", null, 1);

        if(bandera==false) {
            regbook(0, "12345", "Player's Handbook", "El Manual del jugador es el libro de referencia esencial para todos los jugadores de Dungeons & Dragons.");
            regbook(1, "135", "Dungeon Master's Guide", "The Dungeon Master’s Guide provides the inspiration and the guidance you need to spark your imagination and create worlds of adventure for your players to explore and enjoy.");
            regbook(2, "A", "Monster Manual", "The Monster Manual presents a horde of classic Dungeons & Dragons creatures, including dragons, giants, mind flayers, and beholders—a monstrous feast for Dungeon Masters ready to challenge their players and populate their adventures.");
            regbook(3, "B", "Dungeons & Dragons Starter Set", "The Dungeons & Dragons Starter Set: Dragons of Stormwreck Isle is your gateway to action-packed adventures in the cooperative storytelling game Dungeons & Dragons, where heroes battle monsters, find treasure, and undertake epic quests.");
            regbook(4, "C", "Xanathar's Guide to Everything", "The beholder Xanathar—Waterdeep’s most infamous crime lord—is known to hoard information on friend and foe alike. The beholder catalogs lore about adventurers and ponders methods to thwart them. Its twisted mind imagines that it can eventually record everything!");
            regbook(5, "D", "Waterdeep Dragon Heist", "Famed explorer Volothamp Geddarm needs you to complete a simple quest. Thus begins a mad romp through the wards of Waterdeep as you uncover a villainous plot involving some of the city’s most influential figures.");
            regbook(6, "E", "Dungeon of the Mad Mage", "In the city of Waterdeep rests a tavern called the Yawning Portal, named after the gaping pit in its common room. At the bottom of this crumbling shaft is a labyrinthine dungeon shunned by all but the most daring adventurers. Known as Undermountain, this dungeon is the domain of the mad wizard Halaster Blackcloak. Long has the Mad Mage dwelt in these forlorn depths, seeding his lair with monsters, traps, and mysteries—to what end is a constant source of speculation and concern.");
            regbook(7, "F", "Guildmasters' Guide to Ravnica", "A perpetual haze of dreary rain hangs over the spires of Ravnica. Bundled against the weather, the cosmopolitan citizens in all their fantastic diversity go about their daily business in bustling markets and shadowy back alleys. Through it all, ten guilds—crime syndicates, scientific institutions, church hierarchies, military forces, judicial courts, buzzing swarms, and rampaging gangs—vie for power, wealth, and influence.");
            regbook(8, "G", "Mordenkainen's Tome of Foes", "This tome is built on the writings of the renowned wizard from the world of Greyhawk, gathered over a lifetime of research and scholarship. In his travels to other realms and other planes of existence, he has made many friends, and has risked his life an equal number of times, to amass the knowledge contained herein. In addition to Mordenkainen’s musings on the endless wars of the multiverse, the book contains game statistics for dozens of monsters: new demons and devils, several varieties of elves and duergar, and a vast array of other creatures from throughout the planes of existence.");
            regbook(9, "H", "Volo's Guide to Monsters", "The esteemed loremaster Volothamp Geddarm is back and he’s written a fantastical dissertation, covering some of the most iconic monsters in the Forgotten Realms. Unfortunately, the Sage of Shadowdale himself, Elminster, doesn’t believe Volo gets some of the important details quite right. Don’t miss out as Volo and Elminster square off (academically speaking of course) to illuminate the uninitiated on creatures both common and obscure. Uncover the machinations of the mysterious Kraken Society, what is the origin of the bizarre froghemoth, or how to avoid participating in the ghastly reproductive cycle of the grotesque vargouille.");
            bandera = true;
        }

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CompActivity.class);
                intent.putExtra("mybooks", position);
                startActivity(intent);
            }
        });

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setPrompt("Lector QR");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();
            }
        });

        return vista;

    }//onCreateView

    private void regbook(int id, String reg, String title, String descrip) {
        SQLiteDatabase bd = adm.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("id", id);
        registro.put("reg", reg);
        registro.put("title", title);
        registro.put("descrip", descrip);

        if (bd != null) {
            try {
                bd.insert("book", null, registro);
            } catch (SQLException e) {
                Log.e("ERROR", "Error:" + String.valueOf(e.getMessage()));
            }
            bd.close();
        }
    }

}