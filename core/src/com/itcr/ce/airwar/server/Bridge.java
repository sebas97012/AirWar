package com.itcr.ce.airwar.server;

/**
 * Created by Sebas Mora on 28/03/2017.
 */

import com.itcr.ce.airwar.MyGdxGame;

import java.awt.*;
import java.awt.event.KeyEvent;


public class Bridge {
    private int delay_time = 80;
    public static TCPServer Server;

    public Bridge() {

        Server = new TCPServer(new TCPServer.OnMessageReceived() {
            @Override
            public void messageReceived(String message) {
                System.out.print(message);
                try{
                    Robot robot = new Robot();
                    if(message.equals("u")){
                        robot.keyPress(KeyEvent.VK_UP);
                        robot.delay(delay_time);
                        robot.keyRelease(KeyEvent.VK_UP);
                    }
                    else if (message.equals("d")){
                        robot.keyPress(KeyEvent.VK_DOWN);
                        robot.delay(delay_time);
                        robot.keyRelease(KeyEvent.VK_DOWN);
                    }
                    else if(message.equals("l")){
                        robot.keyPress(KeyEvent.VK_LEFT);
                        robot.delay(delay_time);
                        robot.keyRelease(KeyEvent.VK_LEFT);
                    }
                    else if(message.equals("s")){
                        robot.keyPress(KeyEvent.VK_SPACE);
                        robot.delay(delay_time);
                        robot.keyRelease(KeyEvent.VK_SPACE);
                    }
                    else if(message.equals("p")){
                        robot.keyPress(KeyEvent.VK_ENTER);
                        robot.delay(delay_time);
                        robot.keyRelease(KeyEvent.VK_ENTER);
                    }
                    else{
                        robot.keyPress(KeyEvent.VK_RIGHT);
                        robot.delay(delay_time);
                        robot.keyRelease(KeyEvent.VK_RIGHT);
                    }
                }catch (AWTException e){
                    e.printStackTrace();}
            }
        });
        Server.start();

    }
    public static void UpdateServer(){
        Server.sendMessage("Score:"+Integer.toString(MyGdxGame.player.getScore())+"\n"+"Lifes:"+Integer.toString(MyGdxGame.player.getLifes()));
    }
}
