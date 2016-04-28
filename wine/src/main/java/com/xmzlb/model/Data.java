package com.xmzlb.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Data {

    @Expose
    private List<Player> player = new ArrayList<Player>();

    /**
     * 
     * @return
     *     The player
     */
    public List<Player> getPlayer() {
        return player;
    }

    /**
     * 
     * @param player
     *     The player
     */
    public void setPlayer(List<Player> player) {
        this.player = player;
    }

}
