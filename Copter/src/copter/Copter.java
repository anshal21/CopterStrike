    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copter;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import static javax.swing.Spring.max;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import jdk.nashorn.internal.ir.Flags;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

/**
 *
 * @author Anshal Dwivedi(Blue Scorpion) B.Tech II-year Information Technology 
 * Completed On 29-10-2015
 * Game-Sky_Hawk
 */
public class Copter extends JComponent implements ActionListener,KeyListener{
    
    /**
     * @param args the command line arguments
     */
    public static AudioPlayer MGP=AudioPlayer.player;
     public static AudioPlayer MGP_2=AudioPlayer.player;
     public  static ContinuousAudioDataStream loop=null;
     public static ContinuousAudioDataStream loop_2=null;
     public static int enmySpeed=7,gravity=5,upThrust=7,bgSpeed=5,gravity_counter=0;
    public static int indi=0,indi1=0,Score=0,Deaths=0,Life=3,Cur_Level=1,copter_age=0,ammo,tempY=1;
    public static boolean game_start=false;
    public  static boolean pause_indicator=false,motion=false;
    private static final String sp="kevin16";
    JFrame jf=null;
     URL bg_url;
   ArrayList<Integer> bulletX=new ArrayList<Integer>();
   ArrayList<Integer> bulletY=new ArrayList<Integer>();
   ArrayList<Integer> bulletLifetime=new ArrayList<Integer>();
   public Copter(){
         game_start=true;
                tmpX=-1;tmpY=-1;
                    copterX=10;copterY=450;
                    img1X=0;img2X=900;
                    informer=0;
                     flag=0;
                     ammo=400;
                     game_over=false;
                     Life=3;
                     Deaths=0;
                     bgSpeed=10;
                    bg_url=getClass().getResource("forest.png");
                     
                    
    bulletX.removeAll(bulletX);
    bulletY.removeAll(bulletY);
    bulletLifetime.removeAll(bulletLifetime);
              for(int i=0;i<70;i++)
        {
            enmyX[i]=enmyY[i]=enmyHealth[i]=-1;
        }
   }
    int i;
    int bulletSpeed=13;
    public static int bullet_counter;
    
    public  static  String bg_music_1="bgms.wav";
    public  static  String bg_music_2="bgm_cheap.wav";
    public static class Sound_thread extends Thread{
        public void run(){
           /*  Clip clip;
            File file=new File("D:\\RecAudio.wav");
            try {
                AudioInputStream ais=AudioSystem.getAudioInputStream(file);
                clip=AudioSystem.getClip();
                clip.open(ais);
                clip.start();
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(TheGame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TheGame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(TheGame.class.getName()).log(Level.SEVERE, null, ex);
            }
           */
            
            AudioStream as = null;
            AudioData ad;
            
           
            AudioStream as_2;
            AudioData ad_2;
            
            try {
                 File fil=null;
                URL ut=getClass().getResource(bg_music_1);
                try {
                    fil=new File(ut.toURI());
                } catch (URISyntaxException ex) {
                    Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
                }
                as=new AudioStream(new FileInputStream(fil));
                ad=as.getData();
                loop=new ContinuousAudioDataStream(ad);
            } catch (FileNotFoundException ex) {
            //    Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
              //  Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                File fil=null;
                URL ut=getClass().getResource(bg_music_2);
          
                fil=new File(ut.toURI());
                as_2=new AudioStream(new FileInputStream(fil));
                ad_2=as_2.getData();
                loop_2=new ContinuousAudioDataStream(ad_2);
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                //Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
            }
            while(true)
            {
                if(game_start==false&&(indi1==0||indi1==3))
                {
                    if(indi1==3)
                    {
                        MGP_2.stop(loop_2);
                    }
                        
                   // MGP.start(loop);
                    
                    indi1++;
                    
                }
                else if(game_start==true&&(indi==0||indi==3))
                {
                  MGP.stop(loop);
                    MGP_2.start(loop_2);
                    indi++;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                  //  Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
    
   
    public void copter_collide(){
        //Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
        
        Clip clip;
        try {
            AudioInputStream ais=AudioSystem.getAudioInputStream(this.getClass().getResource("blast_effect.wav"));
            clip=AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException ex) {
            //Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            //Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    public void bullet_fire(){
    Clip clip;
       
            try {
                AudioInputStream ais=AudioSystem.getAudioInputStream(this.getClass().getResource("fire.wav"));
                clip=AudioSystem.getClip();
                clip.open(ais);
                clip.start();
            } catch (UnsupportedAudioFileException ex) {
                //Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                //Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                //Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
            }
}
  //  java.net.URL url=Copter.class.getResource("src\\res\\copter2.gif");
    URL copter2=getClass().getResource("copter2.gif");
     Image img=Toolkit.getDefaultToolkit().getImage(copter2);
    int tmpX=-1,tmpY=-1;
    int copterX=10,copterY=450;
    int img1X=0,img2X=900;
    int informer=0;
    int flag=0;
    boolean game_over=false;
    public static int enmy_lev_1=7,enmy_lev_2=11,enmy_lev_3=15;
    public static int[] enmyX=new int[70];
    public static  int[] enmyY=new int[70];
    public static int[] enmyHealth=new int[70];
    public void closeFrame()
    {
        jf.dispose();
    }
       
    public  void execute() {
        // TODO code application logic here
        Sound_thread st=new Sound_thread();
        st.start();
        
          for(int i=0;i<70;i++)
        {
            enmyX[i]=enmyY[i]=-1;
        }
        
         jf=new JFrame();
        jf.setTitle("Copter Strike");
        Copter cop=new Copter();
        jf.add(cop);
         //URL coptter2=getClass().getResource("copter2.gif");
       //  Image immg=Toolkit.getDefaultToolkit().getImage(copter2);
       // jf.setIconImage(immg);
       // jf.setIconImage(img);
        System.out.println(jf);
        jf.setVisible(true);
        jf.pack();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        Game_over_listner gol=new  Game_over_listner();
        gol.start();
        Timer tm=new Timer(100, cop);
        tm.start();
        
        jf.addKeyListener(cop);
    }
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(900,600);
    }
    @Override
    public void paintComponent(Graphics g){
         Image imgbg;
         URL menu2=getClass().getResource("menu2.png");
          Image img_option=Toolkit.getDefaultToolkit().getImage(menu2);
        if(game_start==true)
        {
            
         
         imgbg=Toolkit.getDefaultToolkit().getImage(bg_url);
         g.drawImage(imgbg,img1X,0,900,600, this);
        g.drawImage(imgbg,img2X,0,900,600, this);
        }
        else
        {
            
            URL inbg_url=getClass().getResource("inbg.jpg");
           imgbg=Toolkit.getDefaultToolkit().getImage(inbg_url);
           g.drawImage(imgbg,0,0,900,600, this);
           g.drawImage(img_option, 225, 100, 450, 400, this);
           g.setColor(Color.WHITE);
           g.drawString("A Game By Blue Scorpion",700,570);
        }
        
       if(game_start==true)
       {
         g.drawImage(img, copterX, copterY,150,70, this);
         URL img_en=getClass().getResource("enmy3.gif");
            Image img_enmy=Toolkit.getDefaultToolkit().getImage(img_en);
            URL exp=getClass().getResource("expl.gif");
            Image explode=Toolkit.getDefaultToolkit().getImage(exp);
            URL smok=getClass().getResource("smoke.gif");
            Image smoke=Toolkit.getDefaultToolkit().getImage(smok);
            g.setColor(new Color(255,255,0));
           // Iterator itr=bulletX.iterator();
            //Iterator itr_2=bulletY.iterator();
            int count=0;
            while((count<bulletX.size())&&(!bulletX.isEmpty())&&(!bulletY.isEmpty()))
            {
                int t=bulletX.get(count);
                if(t<=900)
                {
                g.fillRect(t, bulletY.get(count),12,3);
              //   itr.next();
                //itr_2.next();
                }
                else
                {
                    bulletX.remove(count);
                    bulletY.remove(count);
                }
               
                
                count++;
            }
            int up=0;
            if(Cur_Level==1)
                up=enmy_lev_1;
            else if(Cur_Level==2)
                up=enmy_lev_2;
            else
                up=enmy_lev_3;
         for(int i=0;i<up;i++)
         {
             
            // g.fillRect(enmyX[i],enmyY[i],100 ,30);
             if(enmyX[i]!=-1&&enmyY[i]!=-1)
             g.drawImage(img_enmy,enmyX[i],enmyY[i],100,30, this);
             if(enmyHealth[i]<1)
             {
                g.drawImage(smoke,enmyX[i]+10,enmyY[i]-30,20,60, this); 
             }
             if(enmyX[i]<-80||enmyY[i]>590)
             {
                 enmyX[i]=-1;
                 enmyY[i]=-1;
                 Score+=50;
             }
             if(((((enmyX[i]>=copterX&&enmyX[i]<=copterX+150)||(enmyX[i]==copterX)||(enmyX[i]<copterX&&enmyX[i]+100>copterX))&&(enmyY[i]==copterY||(enmyY[i]<copterY&&enmyY[i]+30>=copterY)||(enmyY[i]>copterY&&enmyY[i]-30<=copterY))&&enmyHealth[i]>0)||(copterY<=0)||(copterY+70>=600)||((copterY+70>=600)&&(motion==true)))&&copter_age>20)
             {
                 copter_age=0;
                 motion=false;
                 g.drawImage(explode, copterX+100,copterY+10,100,90, this);
                 
                copter_collide();
                 if(Deaths<3)
                 Deaths++;
                 try {
                     Thread.sleep(1000);
                 } catch (InterruptedException ex) {
                     Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 if(Deaths==3)
                 {
                MGP_2.stop(loop_2);
               //
                //System.out.println("hhh");
                 
                 indi1=3;
                 
                  Image imggo=Toolkit.getDefaultToolkit().getImage(getClass().getResource("gmo.gif"));
          g.drawImage(imggo, 300, 200,300,200, this);
                 game_over=true;
   
             
                 flag=1;
                 
                 try {
                     Thread.sleep(1000);
                 } catch (InterruptedException ex) {
                     Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 }
                 else{
                     copterX=10;
                     copterY=450;
                 }
               
             }
               g.setColor(Color.BLACK);
                 g.drawString("Score : "+Score,10,20);
                 g.drawString("Level : "+Cur_Level,10,30);
                 g.drawString("Life : "+(Life-Deaths),10,40);
                 g.drawString("Ammo : "+(ammo),10,50);
            
         }
         if(game_over==true)
         {
             bgSpeed=0;
             g.drawImage(explode, copterX+100,copterY+10,100,90, this);
             Image imggo=Toolkit.getDefaultToolkit().getImage(getClass().getResource("gmo.gif"));
          g.drawImage(imggo, 300, 200,300,200, this);
         }
       }
        if(pause_indicator==true&&game_over==false)
             {
                    Image img_paused=Toolkit.getDefaultToolkit().getImage(getClass().getResource("paused.png"));
          g.drawImage(img_paused, 350, 200,200,200, this);
             }
      //  g.setColor(Color.yellow);
        //g.fillRect(copterX,copterY,200,100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       // System.out.println(bulletY);
       int up = 0;
        copter_age++;
        if(Score>1500&&Cur_Level==1)
        {
            System.out.println("Level Increased");
            ammo+=400;
            upThrust=10;
            gravity=7;
              bg_url=getClass().getResource("desert.png");
            Cur_Level++;
        }
        if(Score>3000&&Cur_Level==2)
        {
            System.out.println("Level Increased Again");
            ammo+=400;
                upThrust=13;
             bg_url=getClass().getResource("snow.png");
            Cur_Level++;
        }
        if(game_start==true)
        {
        if(img1X<=-900)
            img1X=900-bgSpeed;
        else
            img1X=img1X-bgSpeed;
         if(img2X<=-900)
            img2X=900-bgSpeed;
        else
            img2X=img2X-bgSpeed;
       /* for(int i=0;i<7;i++)
        {
            System.out.println(enmyY[i]);
        }*/
        Random r = new Random();
        int max=1250,min=830;
        int nt;
        if(game_over==false){
            if(Cur_Level==1)
            {
                up=enmy_lev_1;
            }
            else if(Cur_Level==2)
            {
                up=enmy_lev_2;
            }
            else
                up=enmy_lev_3;
        for(int i=0;i<up;i++)
        {
            if(enmyX[i]==-1)
            {
                nt = r.nextInt(max - min + 1) + min;
                enmyX[i]=nt;
                enmyHealth[i]=100;
            }
            else{
                enmyX[i]=enmyX[i]-enmySpeed;
            }
            
        }
        max=550;
        min=0;
        for(int i=0;i<up;i++)
        {
            if(enmyY[i]==-1)
            {
                if(i==1)
                    nt = r.nextInt(max - (min+400) + 1) + min+400;
                else if(i==2||i==5)
                    nt = r.nextInt(max-250 - (min) + 1) + min;
                else
                    nt = r.nextInt(max - min + 1) + min;
                enmyY[i]=nt;
            }
        }
        Iterator itr=bulletX.iterator();
        int count=0;
            while(itr.hasNext())
            {
               int y=bulletLifetime.get(count);
               
                int g=(y*y*tempY)/2;
              bulletX.set(count,bulletX.get(count)+bulletSpeed);
             
               bulletY.set(count,bulletY.get(count));
               if(gravity_counter==30)
               {
               bulletLifetime.set(count,y+1);
               gravity_counter=0;
               }
               gravity_counter++;
                //System.out.println("bullet at"+bulletX.get(count));
                itr.next();
                count++;
            }
        /*    System.out.println("/////////////////////////////////");
            System.out.println(enmyHealth);
            System.out.println("/////////////////////////////////");*/
            if(motion==false||copter_age<20)
            {
                 if(copterY<500)
                 {
                    copterY=copterY+gravity;
                 }
            }
        else
        {
            copterY=copterY+gravity;
        }
        }
        }
        int j;
        for(i=0;i<up;i++)
        {
            for(j=0;j<bulletX.size();j++)
            {
                int t=bulletY.get(j);
                if((bulletX.get(j)>=enmyX[i]+10)&&(t+30>enmyY[i]+30&&t-30<enmyY[i])||bulletY.get(j)>600)
                {
                    bulletX.remove(j);
                    bulletY.remove(j);
               
                    bulletLifetime.remove(j);
                    enmyHealth[i]-=20;
                    if(enmyHealth[i]==0)
                        Score+=100;
                }
            }
        }
        for(i=0;i<up;i++)
        {
            if(enmyHealth[i]<1)
            {
                enmyY[i]+=10;
                enmyX[i]+=enmySpeed-enmySpeed;
            }
        }
        repaint();
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        informer=1;
        if(e.getKeyCode()==32&&game_start==true)
        {
            motion=true;
            copterY=copterY-upThrust;
        }
        else if(e.getKeyCode()==10)
        {
            game_start=true;
                tmpX=-1;tmpY=-1;
                    copterX=10;copterY=450;
                    img1X=0;img2X=900;
                    informer=0;
                     flag=0;
                     ammo=400;
                     game_over=false;
                     Life=3;
                     Deaths=0;
                     bgSpeed=10;
                     Score=0;
                     bg_url=getClass().getResource("forest.png");
    bulletX.removeAll(bulletX);
    bulletY.removeAll(bulletY);
    bulletLifetime.removeAll(bulletLifetime);
              for(int i=0;i<70;i++)
        {
            enmyX[i]=enmyY[i]=-1;
        }
           // bg_music="C:\\Users\\Anshal Dwivedi\\Documents\\NetBeansProjects\\Copter\\src\\copter\\bgm_cheap.wav";
            
        }
        else if(e.getKeyCode()==27)
        {
             game_start=false;
            game_over=true;
            System.out.println(game_over);
            MenuFrame mf=new  MenuFrame();
            mf.setLocationRelativeTo(null);
            mf.setVisible(true);
           // closeFrame();
            System.out.println(jf);
           
           
            indi=3;
           
            MGP_2.stop(loop_2);
            MGP.start(loop);
            
        }
        else if(e.getKeyCode()==81&&game_start==false){
           System.exit(0);
        }
        else if(e.getKeyCode()==80&&game_start==true) {
            enmySpeed=0;
            gravity=0;
            upThrust=0;
            tempY=0;
            bgSpeed=0;
            bulletSpeed=0;
           MGP_2.stop(loop_2);
           pause_indicator=true;
            
        }
         else if(e.getKeyCode()==82&&game_start==true) {
            enmySpeed=7;
            gravity=4;
            upThrust=7;
            tempY=1;
            bulletSpeed=13;
            bgSpeed=5;
             MGP_2.start(loop_2);
              pause_indicator=false;
            
        }
         else if(e.getKeyCode()==17&&game_over==false&&game_start==true&&ammo>0){
            try {
                bullet_fire();
                ammo--;
                bulletX.add(copterX+150);
                bulletY.add(copterY+60);
                bulletLifetime.add(1);
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Copter.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
        repaint();
        informer=0;
         }
    

    @Override
    public void keyReleased(KeyEvent e) {
        // System.out.println("this "+e.getKeyCode());
    }
    public class Game_over_listner extends Thread{
        public void run()
        {
            while(true)
            {
             //   System.out.println(game_start);
                if(game_start==false)
                {
                 //   System.out.println("this time- "+jf);
                    jf.dispose();
                    
                }
                else
                {
                 //   System.out.println("this time-"+jf+"\n");
                    
                }
            }
        }
    }
}
