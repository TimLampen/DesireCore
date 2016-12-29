package com.desiremc.core.staff.discipline;

import com.desiremc.core.util.StringUtil;
import org.bukkit.Material;

/**
 * Created by Timothy Lampen on 11/26/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class DisciplineType {

    private Material item;
    private String discipline;
    private String name;

    public DisciplineType(Material item, String discipline){
        this.item = item;
        this.discipline = discipline;
        StringBuilder sb = new StringBuilder();
        boolean capitalize = false;
        for(char c : discipline.toCharArray()){
            if(c == '_'){
                capitalize = true;
                sb.append(" ");
            }
            else{
                if(capitalize){
                    sb.append(Character.toUpperCase(c));
                    capitalize = false;
                }
                else{
                    sb.append(c);
                }
            }
        }
        this.name = StringUtil.capitalizeFirst(sb.toString());
    }

    public Material getItem() {
        return item;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getName() {
        return name;
    }
}
