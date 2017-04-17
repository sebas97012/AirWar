package com.itcr.ce.airwar.server;

/**
 * Created by Sebas Mora on 28/03/2017.
 */

import com.itcr.ce.airwar.MyGdxGame;

import java.awt.*;
import java.awt.event.KeyEvent;


public class Bridge {

    public static TCPServer Server;

    public Bridge() {

        Server = new TCPServer(new TCPServer.OnMessageReceived() {
            @Override
            public void messageReceived(String message) {
                System.out.print(message);
                try{
                    Robot robot = new Robot();
                    if(message.equals("UP")){
                        robot.keyPress(KeyEvent.VK_UP);
                        robot.delay(100);
                        robot.keyRelease(KeyEvent.VK_UP);
                    }
                    else if (message.equals("DOWN")){
                        robot.keyPress(KeyEvent.VK_DOWN);
                        robot.delay(100);
                        robot.keyRelease(KeyEvent.VK_DOWN);
                    }
                    else if(message.equals("LEFT")){
                        robot.keyPress(KeyEvent.VK_LEFT);
                        robot.delay(100);
                        robot.keyRelease(KeyEvent.VK_LEFT);
                    }
                    else if(message.equals("SPACE")){
                        robot.keyPress(KeyEvent.VK_SPACE);
                        robot.delay(100);
                        robot.keyRelease(KeyEvent.VK_SPACE);
                    }
                    else{
                        robot.keyPress(KeyEvent.VK_RIGHT);
                        robot.delay(100);
                        robot.keyRelease(KeyEvent.VK_RIGHT);
                    }
                }catch (AWTException e){
                    e.printStackTrace();}
            }
        });
        Server.start();

    }
    public static void UpdateServer(){
        Server.sendMessage("L:"+Integer.toString(MyGdxGame.player.getLifes()));
        Server.sendMessage("S:"+Integer.toString(MyGdxGame.player.getScore()));
    }
}