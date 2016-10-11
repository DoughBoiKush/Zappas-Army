package raider;
import org.osbot.rs07.api.Magic;
import org.osbot.rs07.api.NPCS;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.MagicSpell;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;


import java.awt.*;


@ScriptManifest(name = "Zappas Army 3 ", author = "ZappaScripts", version = 1.0, info = "f u pkers i got pked once now i gunna pk u all aww yh", logo = "") 
public class main extends Script {




    private void mvCam(){
    	
    	int mod = random(3,10);
    	int dice = random(1,6);
    	int a = 0;
    		if (dice > 3)
    			a = random(random(15,70)-mod,random(70,100));
    		else
    			a = random(random(15,70),random(70,100)+mod);
    	
    	mod = random(3,15);
    	dice = random(10,20);
    	int b = 0;
    		if (dice > 15)
    			b = random(random(15,70)-mod,random(70,100));
    		else
    			b = random(random(15,70),random(70,100)+mod);
    	
    	camera.movePitch(a);
    	camera.moveYaw(b);
    }

     private String[][] cmds = {
    		 {"1","Attack","Atk"},
    		 {"2","Follow","Flw"},
    		 {"3","Walk here","Walkhere", "Walk", "wlk"},
    		 {"4", "Trade with", "trd"}
    		 };
     
     private String cmdHandle(String cmd){
    	 
    	
    	 for (int i =0; i <= 3; i++ ){
    		 for (int a=0; a < cmds[i].length; a++){
    			 if (cmds[i][a].contains(cmd)){
    				 log("cmd: "+cmds[i][1]);
    				 return cmds[i][1];
    			 }
    		 }
    	 }
    	 
    	 
    	 return null;
     }
     boolean stop = false;
    private String[] oldData = {"null","null"};
    private boolean plyInter(String sid, String Action) throws InterruptedException{
    	//log(Action);
    	
    		//log(Action);
    		Player v = getPlayers().closest(player -> player.getName().replaceAll("\\u00a0", " ").replaceAll("\\s", " ").replaceAll("\\h", " ").equalsIgnoreCase(sid));
    		
    		
    		log(sid);
    		if (v != null && cmdHandle(Action) != null){
    			v.interact(cmdHandle(Action));
    			oldData[1] = sid;
    			log("here");
    			oldData[0] = Action;
    			sleep(random(600,900));  
    			stop = false;
    			return true;
    		}
    	
    	return false;
    }
    
   
    String command;
    RS2Widget input = null;
    String[] parser = null;
    String msg = null;
    RS2Widget ccop = null;
    @Override
    public int onLoop() throws InterruptedException {
    
    	mvCam();
    	ccop = widgets.get(162, 19);
    	if (ccop != null){
    		if (ccop.getSpriteIndex1() != 1022){
    			ccop.interact("Switch tab");
    		}
    	}
    	
    	if(equipment.isWieldingWeaponThatContains("Dragon")){
    		if (combat.getSpecialPercentage() > 49 && !combat.isSpecialActivated()){
    			combat.toggleSpecialAttack(true);
    		}
    	}
    	
    	
    	 input = getWidgets().get(162, 43,1);
    	if (input != null){
    		parser = input.getMessage().split(">");
    		if (parser != null && parser[0].equals("<col=7f0000") ){
    			msg = parser[1].replaceAll("</col", "");
    			parser = msg.split(" ");
    			
    			
    			if (parser != null && parser[0].substring(0,1).equals("!") && parser.length > 0){
    				parser[0] = parser[0].substring(1, parser[0].length());
    				
    				if (parser.length > 2){
    					parser[1] = String.join(" ", parser[1], parser[2]);
    				}
    					msg = msg.replaceAll(parser[0]+" ", "");
    					if(msg.contains(".")){
    						msg=msg.replaceAll("\\.", "");
    					}
    							msg = msg.substring(1,msg.length());
    							
    							
    							command = parser[0];
    							/*
    							String suMsg[] = msg.split(" ");
    							if(suMsg.length > 0){
    								command = msg.replaceFirst(suMsg[1], "");
    							}else{
    								 command = msg.replaceFirst(suMsg[0], "");
    							}
								*/
								log(command); 
								log(msg);
								//log("command:" + command+ ":end");
								//log("parser:" + parser[0] + ":end");
								//log("msg:"+msg+":end");
    						if(!plyInter(msg,parser[0]) && !stop ){
    							
								
    							switch (parser[0]){
    							
    							case "Hop":
    								worlds.hop(Integer.parseInt(msg));
    								sleep(random(10000,11000));
    								break;
    								
    							case "Say":
    								getKeyboard().typeString(msg, true);
    								
    								sleep(random(300,400));
    								break;
    								
    							case "Npc":
    								//log(Integer.parseInt(suMsg[1].replaceAll("\\s","")));
    								NPC uNpc = npcs.closest(msg);
    								//log("here: "+Integer.parseInt(msg));
    								if (uNpc != null){
    									
    								
    									uNpc.interact("Attack");
    									sleep(random(200,400));
    									
    								}
    								break;
    								
    							case "Obj":
    					
    								
    								RS2Object obj = objects.closest(msg);
    								if (obj != null){
    									
    									obj.interact(command);
    									sleep(random(200,400));
    									
    								}
    								
    								break;
    								
    							
    							case "Getgear":
    							{
    								RS2Object table = objects.closest("Tournament supplies");
    								log("here1");
    								if(table !=null){
    									log("here 2");
    									RS2Widget tbank = widgets.get(100, 1);
    									if (tbank == null && !stop){
    									table.interact("take");		
    									sleep(random(1000,1100));
    									}
    									while(tbank != null && !stop ){
    										
    										log("here1");
    										if (!inventory.contains("Serpentine helm")){
    											widgets.get(100,3,111).interact("take");
    											sleep(random(700,800));
    										}
    										
    										if (!inventory.contains("Bandos tassets")){
    											widgets.get(100,3,42).interact("take");
    											sleep(random(700,800));
    										}
    										
    										if(!inventory.contains("Bandos chestplate")){
    											widgets.get(100,3,41).interact("take");
    											sleep(random(700,800));
    										}
    										mouse.move(482,185);
    										mouse.click(false);
    										
    										if(!inventory.contains("Primordial boots")){
    											widgets.get(100,3,147).interact("take");
    											sleep(random(700,800));	
    										}
    										if(!inventory.contains("Amulet of torture")){
    											widgets.get(100, 3,155).interact("take");
    											sleep(random(700,800));
    										}
    										
    										if(!inventory.contains("Fire cape")){
    											widgets.get(100, 3,161).interact("take");
    											sleep(random(700,800));
    										}
    										
    										if(!inventory.contains("Berserker ring (i)")){
    											widgets.get(100,3,169).interact("take");
    											sleep(random(700,800));	
    										}
    										
    										if(!inventory.contains("Barrows gloves")){
    											widgets.get(100,3,150).interact("take");
    											sleep(random(700,800));
    										}
    										mouse.move(482,250);
    										mouse.click(false);
    										
    										
    										if(!inventory.contains("Dragon claws")){
    											widgets.get(100, 3,241).interact("take");
    											sleep(random(700,800));
    											stop = true;
    										}
    									}
    									
    									if(stop == true){
    										getKeyboard().typeString("/Gear collected", true);
    									}
    								}
    							}
    							break;
    							
    							case "Equipgear":{
    								inventory.interact("Wear","Serpentine helm","Dragon claws","Barrows gloves","Fire cape","Amulet of torture","Primordial boots","Bandos chestplate","Bandos tassets","Berserker ring (i)");
    								
    							}
    							
    						break;
    							
    							case "Autooff":
    							{
    								if(combat.isAutoRetaliateOn())
    									combat.toggleAutoRetaliate(false);
    							}
    							break;
    							case "Autoon":
    							{
    								if(!combat.isAutoRetaliateOn()){
    									combat.toggleAutoRetaliate(true);
    								}
    							}
    						
    							case "Getpots":{
    								RS2Object table = objects.closest("Tournament supplies");
    								log("here1");
    								if(table !=null){
    									log("here 2");
    									RS2Widget tbank = widgets.get(100, 1);
    									if (tbank == null && !stop){
    									table.interact("take");		
    									sleep(random(1000,1100));
    									}
    									mouse.move(482,250);
										mouse.click(false);
    									while(tbank != null && !stop ){
    										if (!inventory.contains("Super combat potion(4)")){
    											widgets.get(100, 1,264).interact("take");
    											stop = true;
    											sleep(random(700,800));
    										}
    									}
    									if(stop ){
    										getKeyboard().typeString("/Pot collected", true);
    									}
    									}
    								
    							}
    							break;
    							
    							case"Enterffa":{
    								RS2Object portal = objects.closest("Free-for-all portal");
    								if(portal != null){
    									portal.interact("Enter");
    								}
    							}
    							
    							case "Potup":{
    								inventory.interact("Drink", "Super combat potion(4)");
    								
    							}
    							break;
    						
    							case "Cc":
    							{
    								getTabs().open(Tab.CLANCHAT);
    								sleep(random(200,300));
    								RS2Widget cc = widgets.get(589, 0);
    								if (cc != null){
    									if(!cc.getMessage().contains("Talking in: Not in chat")){
    										widgets.get(589, 8).interact("Leave Chat");
    										sleep(random(700,800));
    									}
    									if (cc.getMessage().contains("Talking in: Not in chat")){
    										widgets.get(589, 8).interact("Join Chat");
    										sleep(random(700,800));
    	    								getKeyboard().typeString(msg, true);
    	    								sleep(random(700,800));
    									}
    								}
    								
    							}
    							break;
    							case "Run":
    							{
    								if(!settings.isRunning() )
    					    			settings.setRunning(true);
    								sleep(random(200,400));
    					    	
    							}
    							break;
    							
    							case "Spawn":
    							{
    								magic.open();
    								sleep(random(700,900));
    								 magic.castSpell(Spells.NormalSpells.HOME_TELEPORT);
    								 sleep(random(12000,13000));
    								   								
    							}
    							break;
    							
    							case "Item":
    								Entity item = getGroundItems().closest(Integer.parseInt(msg));
    								if (item != null)
    									item.interact(command);
    									item.getPosition().distance(myPlayer());
    									
    								sleep(random(200,400));
    								
    								break;
    								
    							
    							case "Inv":
    								if(inventory.getItem(Integer.parseInt(msg))!=null)
    									inventory.getItem(Integer.parseInt(msg)).interact(command);
    								
    								sleep(random(200,300));
    								break;
    								
    							case "Redo":
    								for (int i =0; i<Integer.parseInt(parser[1]); i++){
    									Player v = getPlayers().closest(oldData[1]);
    									if (v != null && cmdHandle(oldData[0]) != null){
    									v.interact(cmdHandle(oldData[0]));
    									sleep(random(500,1000));
    									stop = true;
    									}
    									
    								}
    								
    								break;
    							}
    							
    							
    							
    						}
    					
    				}
    		}
    			
    	}
    	
    	
    	
        return 3000; //The amount of time in milliseconds before the loop starts over
    }


    @Override
    public void onPaint(Graphics2D g) {
        //This is where you will put your code for paint(s)




    }


}