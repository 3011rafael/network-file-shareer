/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Host;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Rafael Macedo
 */
public class Host {
    
    private String ip;
    private int porta;
    
    public Host(){
        
    }

    public Host(String ip, int porta) {
        this.ip = ip;
        this.porta = porta;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }
    
    
    
    
}
