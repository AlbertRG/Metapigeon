package com.example.metapigeon;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.Arrays;

public class SpellbookFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View vista;
    private EditText search;
    private ImageView settings;
    ListView listSpells;
    String[] spells;
    Intent intent;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SpellbookFragment() {
        // Required empty public constructor
    }

    public static SpellbookFragment newInstance(String param1, String param2) {
        SpellbookFragment fragment = new SpellbookFragment();
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

        vista = inflater.inflate(R.layout.fragment_spellbook, container, false);

        listSpells = vista.findViewById(R.id.lvwSpells);
        spells = new String[]{"Fireball", "Toll the Dead", "Shield", "Dimension Door", "Silvery Barbs", "Spiritual Weapon", "Booming Blade", "Mage Hand", "Mage Armor", "Danse Macabre", "Scorching Ray", "Mirror Image", "Blur"};
        Arrays.sort(spells);
        ArrayAdapter<String> spellsAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, spells);
        listSpells.setAdapter(spellsAdapter);
        listSpells.setOnItemClickListener(this);

        search = vista.findViewById(R.id.txtSearchSpell);
        search.setFocusableInTouchMode(true);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                spellsAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        settings = vista.findViewById(R.id.btnSettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        return vista;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String spell = listSpells.getItemAtPosition(i).toString();
        //Toast.makeText(getActivity(), "Spell: " + spell, Toast.LENGTH_LONG).show();
        intent = new Intent(getActivity(), SpellActivity.class);
        intent.putExtra("SpellSelected",spell.trim() + ".txt");
        startActivity(intent);
    }

}