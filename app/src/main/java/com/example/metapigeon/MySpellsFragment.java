package com.example.metapigeon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MySpellsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View vista;
    private EditText search;
    private ImageView settings;
    ListView mySpellList;
    List<String> mySpells = new ArrayList<>();
    Set<String> set = new LinkedHashSet<String>();
    Intent intent;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MySpellsFragment() {
        // Required empty public constructor
    }

    public static MySpellsFragment newInstance(String param1, String param2) {
        MySpellsFragment fragment = new MySpellsFragment();
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
    public void onResume() {
        super.onResume();

        mySpells.clear();
        set.clear();
        getMySpells();
        set.addAll(mySpells);
        mySpells.clear();
        mySpells.addAll(set);
        ArrayAdapter<String> spellsAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, mySpells);
        mySpellList.setAdapter(spellsAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_myspells, container, false);

        mySpellList = vista.findViewById(R.id.lvwMySpells);

        mySpells.clear();
        set.clear();
        getMySpells();
        set.addAll(mySpells);
        mySpells.clear();
        mySpells.addAll(set);
        ArrayAdapter<String> mySpellsAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, mySpells);
        mySpellList.setAdapter(mySpellsAdapter);
        mySpellList.setOnItemClickListener(this);

        search = vista.findViewById(R.id.txtSearchMySpells);
        search.setFocusableInTouchMode(true);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mySpellsAdapter.getFilter().filter(charSequence);
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
        String spell = mySpellList.getItemAtPosition(i).toString();
        //Toast.makeText(getActivity(), "Spell: " + spell, Toast.LENGTH_LONG).show();
        intent = new Intent(getActivity(), SpellAlterActivity.class);
        intent.putExtra("SpellSelected",spell.trim() + ".txt");
        startActivity(intent);
    }

    //Validate files within the device
    private boolean checkFiles (String[] files, String fileSearch){
        //Loop through the list of files to validate that they exist
        for (String file : files) {
            if (fileSearch.equals(file)) {
                return true;
            }
        }
        return false;
    }//checkFiles

    private void getMySpells() {
        //Get list of internal files
        String account;
        SharedPreferences metadata = requireActivity().getSharedPreferences("user.dat", Context.MODE_PRIVATE);
        account = metadata.getString("email",null);
        String spellFile = account + ".spells";
        String[] fileList = this.requireActivity().fileList();
        //Validate file
        if(checkFiles(fileList, spellFile)){
            try{
                InputStreamReader internalFile = new InputStreamReader(this.requireActivity().openFileInput(spellFile));
                BufferedReader spellFileReader = new BufferedReader(internalFile);

                String line = spellFileReader.readLine();
                while (line != null){
                    mySpells.add(line);
                    line = spellFileReader.readLine();
                }
                spellFileReader.close();
                internalFile.close();
            } catch (IOException e) {
                Toast.makeText(getActivity(), "ERROR getting spells",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "Please add a spell",Toast.LENGTH_LONG).show();
        }
    }//getMySpells

}