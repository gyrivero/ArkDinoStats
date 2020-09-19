package com.cloudfoxgames.jerboapp.ui

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cloudfoxgames.jerboapp.common.MyApp
import com.cloudfoxgames.jerboapp.model.Dino
import com.cloudfoxgames.jerboapp.R
import com.cloudfoxgames.jerboapp.common.Utils
import com.cloudfoxgames.jerboapp.model.JsonDino
import com.cloudfoxgames.jerboapp.ui.DinoRecyclerViewAdapter
import java.lang.NullPointerException

class DinoFragment : Fragment()  {

    private var columnCount = 3
    private lateinit var dinoAdapter : DinoRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        setHasOptionsMenu(true)

        val dinoList = createDinos(Utils.jsonParse("values.json"))


        dinoAdapter =
            DinoRecyclerViewAdapter(dinoList as MutableList<Dino>,context)

        val displayMetrics = context!!.resources.displayMetrics
        val dpWidth : Float = displayMetrics.widthPixels / displayMetrics.density
        columnCount = (dpWidth/200).toInt()
        if (columnCount == 1) {
            columnCount = 2
        }

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = dinoAdapter
            }
        }
        return view

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.open_db) {
            val intent = Intent(activity,SavedDinoActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem : MenuItem = menu.findItem(R.id.dino_search)
        val searchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                dinoAdapter.filter.filter(newText)
                return false
            }

        })
        super.onPrepareOptionsMenu(menu)
    }

    fun createDinos(jsonDinos: List<JsonDino>): List<Dino> {
        var repetead = 0
        val list: MutableList<Dino> = mutableListOf()
        for (JsonDino in jsonDinos) {
            doLog(JsonDino)
            if (checkUntameable(JsonDino)) {
                continue
            }
            var jsonName: String
            jsonName = checkVariants(JsonDino)
            var aberrantTekCheck = jsonName
            var dinoImage = putImage(JsonDino.name)
            if (jsonName.contains("Aberrant") || jsonName.contains("Tek") ||
                jsonName.contains("Alpha") || jsonName.contains("Brute") ||
                jsonName.contains("Bunny") || jsonName.contains("Corrupted") ||
                jsonName.contains("Malfunctioned") || jsonName.contains("Party") ||
                jsonName.contains("X-")) {
                aberrantTekCheck = "z$jsonName"
            }
            if (JsonDino.fullStatsRaw[3] == null || JsonDino.fullStatsRaw[4] == null) {
                list.add(
                    Dino(jsonName, dinoImage, JsonDino.fullStatsRaw[0][0], JsonDino.fullStatsRaw[1][0],
                        0F, JsonDino.fullStatsRaw[4][0],
                        JsonDino.fullStatsRaw[7][0], JsonDino.fullStatsRaw[8][0] * 100,
                        JsonDino.fullStatsRaw[9][0] * 100, JsonDino.fullStatsRaw[2][0],
                        JsonDino.fullStatsRaw[0][1],JsonDino.fullStatsRaw[1][1],0F,
                        JsonDino.fullStatsRaw[4][1],JsonDino.fullStatsRaw[7][1],JsonDino.fullStatsRaw[8][1],
                        JsonDino.fullStatsRaw[9][1],JsonDino.fullStatsRaw[2][1],JsonDino.fullStatsRaw[0][3],
                        JsonDino.fullStatsRaw[1][3],0F,JsonDino.fullStatsRaw[4][3],
                        JsonDino.fullStatsRaw[7][3],JsonDino.fullStatsRaw[8][3],JsonDino.fullStatsRaw[9][3],
                        JsonDino.fullStatsRaw[2][3],JsonDino.fullStatsRaw[0][4],JsonDino.fullStatsRaw[1][4],
                        0F,JsonDino.fullStatsRaw[4][4],JsonDino.fullStatsRaw[7][4],
                        JsonDino.fullStatsRaw[8][4],JsonDino.fullStatsRaw[9][4],JsonDino.fullStatsRaw[2][4],
                        JsonDino.tamedBaseHealthMultiplier,aberrantTekCheck
                    )
                )
            } else {
                if (!isRepeated(jsonName, list)) {
                    list.add(
                        Dino(jsonName, dinoImage, JsonDino.fullStatsRaw[0][0], JsonDino.fullStatsRaw[1][0],
                            JsonDino.fullStatsRaw[3][0], JsonDino.fullStatsRaw[4][0],
                            JsonDino.fullStatsRaw[7][0], JsonDino.fullStatsRaw[8][0] * 100,
                            JsonDino.fullStatsRaw[9][0] * 100, JsonDino.fullStatsRaw[2][0],
                            JsonDino.fullStatsRaw[0][1],JsonDino.fullStatsRaw[1][1],JsonDino.fullStatsRaw[3][1],
                            JsonDino.fullStatsRaw[4][1],JsonDino.fullStatsRaw[7][1],JsonDino.fullStatsRaw[8][1],
                            JsonDino.fullStatsRaw[9][1],JsonDino.fullStatsRaw[2][1],JsonDino.fullStatsRaw[0][3],
                            JsonDino.fullStatsRaw[1][3],JsonDino.fullStatsRaw[3][3],JsonDino.fullStatsRaw[4][3],
                            JsonDino.fullStatsRaw[7][3],JsonDino.fullStatsRaw[8][3],JsonDino.fullStatsRaw[9][3],
                            JsonDino.fullStatsRaw[2][3],JsonDino.fullStatsRaw[0][4],JsonDino.fullStatsRaw[1][4],
                            JsonDino.fullStatsRaw[3][4],JsonDino.fullStatsRaw[4][4],JsonDino.fullStatsRaw[7][4],
                            JsonDino.fullStatsRaw[8][4],JsonDino.fullStatsRaw[9][4],JsonDino.fullStatsRaw[2][4],
                            JsonDino.tamedBaseHealthMultiplier,aberrantTekCheck
                        )
                    )
                } else {
                    repetead += 1
                }
            }
        }
        list.sortBy { dino -> dino.aberrantTek }
        return list
    }

    private fun checkUntameable(jsonDino: JsonDino): Boolean {
        try {
            Log.i("Util", "${jsonDino.breeding.incubationTime}")
        } catch (ex: NullPointerException) {
            if (jsonDino.taming.nonViolent == false && jsonDino.taming.violent == false) {
                return true
            }
        }
        if (jsonDino.name.contains("Overseer") || jsonDino.name.contains("Attack Drone") || jsonDino.name.contains("Dinotar")) {
            return true
        }
        return false
    }

    private fun checkVariants(jsonDino: JsonDino): String {
        try {
            if (!jsonDino.name.contains(jsonDino.variants[0])) {
                return jsonDino.name + " " + jsonDino.variants[0]
            } else {
                return jsonDino.name
            }
        } catch (ex: NullPointerException) {
            return jsonDino.name
        }
    }

    private fun isRepeated(
        jsonName: String,
        list: MutableList<Dino>
    ): Boolean {
        for (Dino in list) {
            if (Dino.name.equals(jsonName)) {
                return true
            }
        }
        return false
    }

    private fun doLog(jsonDino: JsonDino) {
        try {
            Log.i("Util", "${jsonDino.displayedStats}")
        } catch (ex: NullPointerException) {
            if (jsonDino.taming.nonViolent == false && jsonDino.taming.violent == false) {
            }
            Log.i("Check", jsonDino.name)
        }
    }

    private fun putImage(name: String): Int {
        if (name.contains("Achatina")) {
            return R.drawable.ic_achatina
        } else if (name.contains("Ankylosaurus")) {
            return R.drawable.ic_ankylosaurus
        } else if (name.contains("Allosaurus")) {
            return R.drawable.ic_allosaurus
        } else if (name.contains("Anglerfish")) {
            return R.drawable.ic_anglerfish
        } else if (name.contains("Archaeopteryx")) {
            return R.drawable.ic_archaeopteryx
        } else if (name.contains("Argentavis")) {
            return R.drawable.ic_argentavis
        } else if (name.contains("Arthropluera")) {
            return R.drawable.ic_arthropluera
        } else if (name.contains("Astrocetus")) {
            return R.drawable.ic_astrocetus
        } else if (name.contains("Baryonyx")) {
            return R.drawable.ic_baryonyx
        } else if (name.contains("Basilisk")) {
            return R.drawable.ic_basilisk
        } else if (name.contains("Basilosaurus")) {
            return R.drawable.ic_basilosaurus
        } else if (name.contains("Beelzebufo")) {
            return R.drawable.ic_beelzebufo
        } else if (name.contains("Bloodstalker")) {
            return R.drawable.ic_bloodstalker
        } else if (name.contains("Brontosaurus")) {
            return R.drawable.ic_brontosaurus
        } else if (name.contains("Bulbdog")) {
            return R.drawable.ic_bulbdog
        } else if (name.contains("Carbonemys")) {
            return R.drawable.ic_carbonemys
        } else if (name.contains("Carnotaurus")) {
            return R.drawable.ic_carnotaurus
        } else if (name.contains("Chalicotherium")) {
            return R.drawable.ic_chalicotherium
        } else if (name.contains("Compy")) {
            return R.drawable.ic_compy
        } else if (name.contains("Daeodon")) {
            return R.drawable.ic_daeodon
        } else if (name.contains("Deinonychus")) {
            return R.drawable.ic_deinonychus
        } else if (name.contains("Dilophosaur")) {
            return R.drawable.ic_dilophosaur
        } else if (name.contains("Dimetrodon")) {
            return R.drawable.ic_dimetrodon
        } else if (name.contains("Diplodocus")) {
            return R.drawable.ic_diplodocus
        } else if (name.contains("Diplocaulus")) {
            return R.drawable.ic_diplocaulus
        } else if (name.contains("Dimorphodon")) {
            return R.drawable.ic_dimorphodon
        } else if (name.contains("Bear")) {
            return R.drawable.ic_dire_bear
        } else if (name.contains("Direwolf")) {
            return R.drawable.ic_direwolf
        } else if (name.contains("Dodo")) {
            return R.drawable.ic_dodo
        } else if (name.contains("Doedicurus")) {
            return R.drawable.ic_doedicurus
        } else if (name.contains("Dung Beetle")) {
            return R.drawable.ic_dung_beetle
        } else if (name.contains("Dunkleosteus")) {
            return R.drawable.ic_dunkleosteus
        } else if (name.contains("Electrophorus")) {
            return R.drawable.ic_electrophorus
        } else if (name.contains("Equus")) {
            return R.drawable.ic_equus
        } else if (name.contains("Featherlight")) {
            return R.drawable.ic_featherlight
        } else if (name.contains("Ferox")) {
            return R.drawable.ic_ferox
        } else if (name.contains("Gacha")) {
            return R.drawable.ic_gacha
        } else if (name.contains("Gallimimus")) {
            return R.drawable.ic_gallimimus
        } else if (name.contains("Gasbags")) {
            return R.drawable.ic_gasbags
        } else if (name.contains("Castoroides")) {
            return R.drawable.ic_giant_beaver
        } else if (name.contains("Giant")) {
            return R.drawable.ic_giant_bee
        } else if (name.contains("Giganotosaurus")) {
            return R.drawable.ic_giganotosaurus
        } else if (name.contains("Gigantopithecus") || name.contains("Yeti")) {
            return R.drawable.ic_gigantopithecus
        } else if (name.contains("Glowtail")) {
            return R.drawable.ic_glowtail
        } else if (name.contains("Griffin")) {
            return R.drawable.ic_griffin
        } else if (name.contains("Hesperornis")) {
            return R.drawable.ic_hesperornis
        } else if (name.contains("Hyaenodon")) {
            return R.drawable.ic_hyaenodon
        } else if (name.contains("Ichthyornis")) {
            return R.drawable.ic_ichthyornis
        } else if (name.contains("Ichthyosaurus")) {
            return R.drawable.ic_ichthyosaurus
        } else if (name.contains("Iguanodon")) {
            return R.drawable.ic_iguanodon
        } else if (name.contains("Jerboa")) {
            return R.drawable.ic_jerboa
        } else if (name.contains("Kairuku")) {
            return R.drawable.ic_kairuku
        } else if (name.contains("Kaprosuchus")) {
            return R.drawable.ic_kaprosuchus
        } else if (name.contains("Karkinos")) {
            return R.drawable.ic_karkinos
        } else if (name.contains("Kentrosaurus")) {
            return R.drawable.ic_kentrosaurus
        } else if (name.contains("Liopleurodon")) {
            return R.drawable.ic_liopleurodon
        } else if (name.contains("Lymantria")) {
            return R.drawable.ic_lymantria
        } else if (name.contains("Lystrosaurus")) {
            return R.drawable.ic_lystrosaurus
        } else if (name.contains("Magmasaur")) {
            return R.drawable.ic_magmasaur
        } else if (name.contains("Mammoth")) {
            return R.drawable.ic_mammoth
        } else if (name.contains("Managarmr")) {
            return R.drawable.ic_managarmr
        } else if (name.contains("Manta")) {
            return R.drawable.ic_manta
        } else if (name.contains("Mantis")) {
            return R.drawable.ic_mantis
        } else if (name.contains("Megachelon")) {
            return R.drawable.ic_megachelon
        } else if (name.contains("Megalodon")) {
            return R.drawable.ic_megalodon
        } else if (name.contains("Megalania")) {
            return R.drawable.ic_megalania
        } else if (name.contains("Megaloceros")) {
            return R.drawable.ic_megaloceros
        } else if (name.contains("Megalosaurus")) {
            return R.drawable.ic_megalosaurus
        } else if (name.contains("Megatherium")) {
            return R.drawable.ic_megatherium
        } else if (name.contains("Mesopithecus")) {
            return R.drawable.ic_mesopithecus
        } else if (name.contains("Microraptor")) {
            return R.drawable.ic_microraptor
        } else if (name.contains("Morellatops")) {
            return R.drawable.ic_morellatops
        } else if (name.contains("Mosasaur")) {
            return R.drawable.ic_mosasaurus
        } else if (name.contains("Moschops")) {
            return R.drawable.ic_moschops
        } else if (name.contains("Nameless")) {
            return R.drawable.ic_nameless
        } else if (name.contains("Onyc")) {
            return R.drawable.ic_onyc
        } else if (name.contains("Otter")) {
            return R.drawable.ic_otter
        } else if (name.contains("Oviraptor")) {
            return R.drawable.ic_oviraptor
        } else if (name.contains("Ovis")) {
            return R.drawable.ic_ovis
        } else if (name.contains("Pachy")) {
            return R.drawable.ic_pachy
        } else if (name.contains("Pachyrhinosaurus")) {
            return R.drawable.ic_pachyrhinosaurus
        } else if (name.contains("Paraceratherium")) {
            return R.drawable.ic_paraceratherium
        } else if (name.contains("Parasaur")) {
            return R.drawable.ic_parasaur
        } else if (name.contains("Pegomastax")) {
            return R.drawable.ic_pegomastax
        } else if (name.contains("Pelagornis")) {
            return R.drawable.ic_pelagornis
        } else if (name.contains("Phiomia")) {
            return R.drawable.ic_phiomia
        } else if (name.contains("Phoenix")) {
            return R.drawable.ic_phoenix
        } else if (name.contains("Plesiosaur")) {
            return R.drawable.ic_plesiosaur
        } else if (name.contains("Procoptodon")) {
            return R.drawable.ic_procoptodon
        } else if (name.contains("Pteranodon")) {
            return R.drawable.ic_pteranodon
        } else if (name.contains("Purlovia")) {
            return R.drawable.ic_purlovia
        } else if (name.contains("Quetzal")) {
            return R.drawable.ic_quetzal
        } else if (name.contains("Raptor")) {
            return R.drawable.ic_raptor
        } else if (name.contains("Ravager")) {
            return R.drawable.ic_ravager
        } else if (name.contains("Reaper")) {
            return R.drawable.ic_reaper
        } else if (name.contains("Rex")) {
            return R.drawable.ic_rex
        } else if (name.contains("Rock Drake")) {
            return R.drawable.ic_rock_drake
        } else if (name.contains("Rock Elemental") || name.contains("Golem")) {
            return R.drawable.ic_rock_elemental
        } else if (name.contains("Roll Rat")) {
            return R.drawable.ic_roll_rat
        } else if (name.contains("Sabertooth")) {
            return R.drawable.ic_sabertooth
        } else if (name.contains("Shinehorn")) {
            return R.drawable.ic_shinehorn
        } else if (name.contains("Snow Owl")) {
            return R.drawable.ic_snow_owl
        } else if (name.contains("Araneo")) {
            return R.drawable.ic_spider
        } else if (name.contains("Sarco")) {
            return R.drawable.ic_sarco
        } else if (name.contains("Pulmonoscorpius")) {
            return R.drawable.ic_scorpion
        } else if (name.contains("Spino")) {
            return R.drawable.ic_spino
        } else if (name.contains("Stegosaurus")) {
            return R.drawable.ic_stegosaurus
        } else if (name.contains("Tapejara")) {
            return R.drawable.ic_tapejara
        } else if (name.contains("Terror Bird")) {
            return R.drawable.ic_terror_bird
        } else if (name.contains("Therizinosaur")) {
            return R.drawable.ic_therizinosaur
        } else if (name.contains("Thorny Dragon")) {
            return R.drawable.ic_thorny_dragon
        } else if (name.contains("Thylacoleo")) {
            return R.drawable.ic_thylacoleo
        } else if (name.contains("Titanoboa")) {
            return R.drawable.ic_titanoboa
        } else if (name.contains("Titanosaur")) {
            return R.drawable.ic_titanosaur
        } else if (name.contains("Triceratops")) {
            return R.drawable.ic_trike
        } else if (name.contains("Troodon")) {
            return R.drawable.ic_troodon
        } else if (name.contains("Tropeognathus")) {
            return R.drawable.ic_tropeognathus
        } else if (name.contains("Tusoteuthis")) {
            return R.drawable.ic_tusoteuthis
        } else if (name.contains("Velonasaur")) {
            return R.drawable.ic_velonasaur
        } else if (name.contains("Vulture")) {
            return R.drawable.ic_vulture
        } else if (name.contains("Woolly Rhino")) {
            return R.drawable.ic_woolly_rhinoceros
        } else if (name.contains("Wyvern")) {
            return R.drawable.ic_wyvern
        } else if (name.contains("Yutyrannus")) {
            return R.drawable.ic_yutyrannus
        } else if (name.contains("Unicorn")) {
            return R.drawable.ic_unicorn
        } else if (name.contains("Desert Titan") || name.contains("Ice Titan")) {
            return R.drawable.ic_desert_titan
        } else {
            return R.drawable.ic_launcher_foreground
        }
    }



    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            DinoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}