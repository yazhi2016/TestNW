package com.xmzlb.registermodel;

import com.google.gson.annotations.Expose;

public class Data {

    @Expose
    private Session session;
    @Expose
    private User user;

    /**
     * 
     * @return
     *     The session
     */
    public Session getSession() {
        return session;
    }

    /**
     * 
     * @param session
     *     The session
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * 
     * @return
     *     The user
     */
    public User getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(User user) {
        this.user = user;
    }

}
