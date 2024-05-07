package de.cuzim1tigaaa.brainbow.files;

import com.google.gson.Gson;
import de.cuzim1tigaaa.brainbow.Brainbow;
import de.cuzim1tigaaa.brainbow.team.Team;
import lombok.Getter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class TeamFile {

    private final Brainbow plugin;
    @Getter private final Set<Team> dataSet;

    private final File dataFile;

    public TeamFile(Brainbow plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "playerdata.json");

        this.dataSet = new HashSet<>();
        this.loadJson();
    }

    private void loadJson() {
        if(!dataFile.exists())
            return;

        try(FileReader fileReader = new FileReader(dataFile)) {
            Team[] data = new Gson().fromJson(fileReader, Team[].class);
            this.dataSet.addAll(Set.of(data));
        }catch(IOException exception) {
            plugin.getLogger().log(Level.WARNING, "An error occurred while loading data!", exception);
        }
    }

    public void saveJson() {
        if(this.dataSet == null || this.dataSet.isEmpty())
            return;

        Gson gson = new Gson();
        if(!dataFile.exists()) {
            try {
                dataFile.getParentFile().mkdir();
                dataFile.createNewFile();
            }catch(IOException exception) {
                plugin.getLogger().log(Level.WARNING, "An error occurred while creating data file!", exception);
            }
        }
        try(FileWriter fileWriter = new FileWriter(dataFile, false)) {
            gson.toJson(dataSet, fileWriter);
            fileWriter.flush();
        }catch(IOException exception) {
            plugin.getLogger().log(Level.WARNING, "An error occurred while saving data!", exception);
        }
    }
}
