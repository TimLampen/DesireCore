
package com.desiremc.core.extras;

import com.desiremc.core.extras.listeners.ItemShowListener;
import com.desiremc.core.module.Module;

/**
 * Created by Shaun on 2016-12-19.
 */
public class ExtraModule extends Module{
    @Override
    public void onLoad(){

    }

    @Override
    public void onEnable(){
        registerListeners(new ItemShowListener());
    }

    @Override
    public void onDisable(){

    }
}
