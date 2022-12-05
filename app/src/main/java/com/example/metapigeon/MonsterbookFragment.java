package com.example.metapigeon;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import java.util.Arrays;

public class MonsterbookFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View vista;
    private EditText search;
    private ImageView settings;
    ListView listMonsters;
    String[] monsters;
    Intent intent;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MonsterbookFragment() {
        // Required empty public constructor
    }

    public static MonsterbookFragment newInstance(String param1, String param2) {
        MonsterbookFragment fragment = new MonsterbookFragment();
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

        vista = inflater.inflate(R.layout.fragment_monsterbook, container, false);

        listMonsters = vista.findViewById(R.id.lvwMonsters);
        monsters = new String[]{"Goblin", "Wolf", "Adult Blue Dragon", "Beholder", "Kobolds"};
        Arrays.sort(monsters);
        ArrayAdapter<String> monstersAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, monsters);
        listMonsters.setAdapter(monstersAdapter);
        listMonsters.setOnItemClickListener(this);

        search = vista.findViewById(R.id.txtSearchMonster);
        search.setFocusableInTouchMode(true);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                monstersAdapter.getFilter().filter(charSequence);
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
        String monster = listMonsters.getItemAtPosition(i).toString();
        //Toast.makeText(getActivity(), "Spell: " + spell, Toast.LENGTH_LONG).show();
        intent = new Intent(getActivity(), MonsterActivity.class);
        intent.putExtra("MonsterSelected",monster.trim());
        startActivity(intent);
    }

}