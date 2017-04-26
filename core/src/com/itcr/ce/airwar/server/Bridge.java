package com.itcr.ce.airwar.server;

/**
 * Created by Sebas Mora on 28/03/2017.
 */

import com.itcr.ce.airwar.MyGdxGame;

import java.awt.*;
import java.awt.event.KeyEvent;


public class Bridge {
    private int delay_time = 20;
    public static TCPServer Server;
    private static Robot robot;

    public Bridge() {
        try{
            robot = new Robot();
        }catch (AWTException e){
        e.printStackTrace();}

        Server = new TCPServer(new TCPServer.OnMessageReceived() {
            @Override
            public void messageReceived(int message) {
                    switch (message){
                        case 1:
                            robot.keyPress(KeyEvent.VK_UP);
                            robot.delay(delay_time);
                            robot.keyRelease(KeyEvent.VK_UP);
                            break;
                        case 2:
                            robot.keyPress(KeyEvent.VK_RIGHT);
                            robot.delay(delay_time);
                            robot.keyRelease(KeyEvent.VK_RIGHT);
                            break;
                        case 3:
                            robot.keyPress(KeyEvent.VK_DOWN);
                            robot.delay(delay_time);
                            robot.keyRelease(KeyEvent.VK_DOWN);
                            break;
                        case 4:
                            robot.keyPress(KeyEvent.VK_LEFT);
                            robot.delay(delay_time);
                            robot.keyRelease(KeyEvent.VK_LEFT);
                            break;
                        case 5:
                            robot.keyPress(KeyEvent.VK_SPACE);
                            robot.delay(delay_time);
                            robot.keyRelease(KeyEvent.VK_SPACE);
                            break;
                        case 6:
                            robot.keyPress(KeyEvent.VK_ENTER);
                            robot.delay(delay_time);
                            robot.keyRelease(KeyEvent.VK_ENTER);
                            break;
                    }
            }
        });
        Server.start();

    }
    public static void UpdateServer(){
        Server.sendMessage("Score:"+Integer.toString(MyGdxGame.player.getScore())+"\n"+"Lifes:"+Integer.toString(MyGdxGame.player.getLifes()));
    }
}
