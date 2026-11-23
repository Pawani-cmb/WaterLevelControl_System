import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

class WaterLevelObserver extends JFrame{
	public void update(int waterLevel){
		
	}
}
class Alarm extends WaterLevelObserver{
	JLabel lbl;
	Alarm(){
		setSize(300,400);
		setTitle("Alarm");
		setLocation(320,100);
		lbl = new JLabel("Alarm OFF");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().setBackground(new Color(16, 29, 58));
        lbl.setFont(new Font("Arial", Font.BOLD, 24));
        lbl.setForeground(new Color(248, 113, 113));
		add(lbl);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	public void update(int waterLevel){
		lbl.setText(waterLevel>50 ? "-->Alarm ON":"-->Alarm OFF");
	}
}
class Splitter extends WaterLevelObserver{
  JLabel lbl;
  Splitter(){
	  setSize(300,400);
	  setTitle("Splitter");
	  setLocation(620,100);
	  lbl = new JLabel("Splitter OFF");
	  lbl.setHorizontalAlignment(SwingConstants.CENTER);
	  getContentPane().setBackground(new Color(16, 29, 58));
      lbl.setFont(new Font("Arial", Font.BOLD, 24));
      lbl.setForeground(new Color(251, 191, 36));
	  add(lbl);
	  setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	  setVisible(true);
	  
	  }
	public void update(int waterLevel){
		lbl.setText(waterLevel>75 ? "-->Splitter ON":"-->Splitter OFF");
	}
}
class Display extends WaterLevelObserver{
	JLabel lbl;
	Display(){
	    setSize(300,400);
		setTitle("Display");
		setLocation(920,100);
	    lbl = new JLabel("Water Level");
	    lbl.setHorizontalAlignment(SwingConstants.CENTER);
	    getContentPane().setBackground(new Color(16, 29, 58));
        lbl.setFont(new Font("Arial", Font.BOLD, 24));
        lbl.setForeground(new Color(56, 189, 248));
	    add(lbl);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	public void update(int waterLevel){
		lbl.setText("-->Water Level : "+waterLevel);
	}
}
class SMSSender extends WaterLevelObserver{
	JLabel lbl;
	SMSSender(){
	    setSize(300,400);
		setTitle("Sms");
		setLocation(1220,100);
		lbl = new JLabel("Sending Sms");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().setBackground(new Color(16, 29, 58));
        lbl.setFont(new Font("Arial", Font.BOLD, 24));
        lbl.setForeground(new Color(52, 211, 153));
	    add(lbl);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void update(int waterLevel){
		lbl.setText("->Sending SMS..."+waterLevel);
	}
}
class ControlRoom{
	private static ControlRoom controlRoom;
	private WaterLevelObserver[] waterLevelObserverArray=new WaterLevelObserver[0];
	private int waterLevel;
	
	private ControlRoom(){}
	
	public static ControlRoom getInstance(){
		if(controlRoom==null){
			controlRoom=new ControlRoom();
		} 
		return controlRoom;
	}
	
	public void addWaterLevelObserver(WaterLevelObserver waterLevelObserver){
		extendArray();
		waterLevelObserverArray[waterLevelObserverArray.length-1]=waterLevelObserver;
	}

	public void setWaterLevel(int waterLevel){
		if(this.waterLevel!=waterLevel){
			this.waterLevel=waterLevel;
			notifyDevices();
		}
	}
	private void notifyDevices(){
		for(WaterLevelObserver ob : waterLevelObserverArray){
			ob.update(waterLevel);
		}
	}
	private void extendArray(){
		WaterLevelObserver[] tempWaterLevelObserverArray=new WaterLevelObserver[waterLevelObserverArray.length+1];
		for (int i = 0; i < waterLevelObserverArray.length; i++){
			tempWaterLevelObserverArray[i]=waterLevelObserverArray[i];
		}
		waterLevelObserverArray=tempWaterLevelObserverArray;	
	}
}
class WaterTankMonitor extends JFrame{
	private ControlRoom controlRoom;
	
	WaterTankMonitor(){
		setSize(300,400);
		JSlider slider=new JSlider(0,100,30);
		setTitle("Slider");
		setLocation(20,100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(slider);
		
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(5);
		slider.setMajorTickSpacing(10);
		slider.setPaintLabels(true);
		
		getContentPane().setBackground(new Color(16, 29, 58));
		slider.setBackground(new Color(16, 29, 58));
        slider.setForeground(Color.WHITE);
        slider.setFont(new Font("Arial", Font.BOLD, 16));
		
				
		controlRoom=ControlRoom.getInstance();
		controlRoom.addWaterLevelObserver(new Alarm());
		controlRoom.addWaterLevelObserver(new Display());
		controlRoom.addWaterLevelObserver(new SMSSender());
		controlRoom.addWaterLevelObserver(new Splitter());
		
		
		slider.addChangeListener(e -> {
        controlRoom.setWaterLevel(slider.getValue());
		});
	}
	public static void main(String[] args) {
		new WaterTankMonitor().setVisible(true);
	}
}


