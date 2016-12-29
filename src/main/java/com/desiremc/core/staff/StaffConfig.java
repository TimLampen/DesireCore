package com.desiremc.core.staff;

import com.desiremc.core.DesireCore;
import com.desiremc.core.configs.def.YMLConfig;
import com.desiremc.core.staff.discipline.Discipline;
import com.desiremc.core.staff.discipline.DisciplineType;
import com.desiremc.core.util.logger.Logger;
import org.bukkit.Material;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Timothy Lampen on 12/4/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class StaffConfig extends YMLConfig {

    private ArrayList<DisciplineType> disciplineTypes = new ArrayList<DisciplineType>();

    public StaffConfig(){
        super(new File(DesireCore.getInstance().getDataFolder(), "staff.yml"), true);

        for(String s : getConfig().getConfigurationSection("disciplineTypes").getKeys(false)){
            Material m = Material.getMaterial(getConfig().getString("disciplineTypes." + s + ".displayItem"));
            if(m==null){
                Logger.error("Unable to load the material for discipline type: " + s + " in config");
                m = Material.RECORD_4;
            }
            disciplineTypes.add(new DisciplineType(m, s));
        }
    }

    public DisciplineType getDisciplineType(String discipline){
        for(DisciplineType type : disciplineTypes){
            if(type.getDiscipline().equalsIgnoreCase(discipline)){
                return type;
            }
        }
        return getDisciplineType("other");
    }

    public int getTotalDisciplines(){
        return disciplineTypes.size();
    }

    public ArrayList<DisciplineType> getDisciplineTypes(){
        return disciplineTypes;
    }
}
