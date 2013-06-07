/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java_net_client;






import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author dima
 */
public class MyGUI {
    
    
    
    
    
    
//Writer output = null	;
//Socket s= null	;
    
int mmm = 0 ;
//#############################################
PrintStream out = null;
BufferedReader in = null;
//#############################################
Thread Tr2 = null;
int ClientStatus = 0;
ServerSocket Server_Socket;
//#############################################
public JTextArea IPv4Address_TextArea = new JTextArea();
public JPanel groupBoxEncryption_IPv4Address_TextArea = new JPanel();
public JScrollPane scroll_IPv4Address_TextArea = new JScrollPane(IPv4Address_TextArea);
//#############################################
public JTextArea ServerPrint_TextArea = new JTextArea();
public JPanel groupBoxEncryption_ServerPrint_TextArea = new JPanel();
public JScrollPane scroll_ServerPrint_TextArea = new JScrollPane(ServerPrint_TextArea);
//#############################################
public JTextField TextField1 = new JTextField();
public JTextField TextField_IP = new JTextField();
public JTextField TextField_PORT = new JTextField();
//#############################################
//public JTextField ServerCurentStatus_TextField = new JTextField();
//#############################################
//public JLabel ServerStatus_label = new JLabel();
//public JLabel ServerCurentStatus_label = new JLabel();

public JLabel label_RemoteIP = new JLabel();
public JLabel label_RemotePORT = new JLabel();
public JLabel label_ClientSTATUS = new JLabel();
public JLabel label_ClientSTATUS_Text = new JLabel();
//#############################################
JButton Button_Connect = new JButton("Connect");
JButton Button_DataSend = new JButton("DataSend");
//#############################################
//#############################################



 int  Cou1=0,Cou2=0,Cou3=0,Temp1,Temp2,T1,T2;


//String myLine;

public MyGUI() {
    JFrame jf = new JFrame("IP Client v0.01");
    jf.setLayout(null);
    jf.setBounds(100, 100, 535, 500);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //#############################################
    
    int JButton_Width = 110;
    int JButton_Height = 30;

    //#############################################
    
    int MyX1 = 10;
    int MyY1 = 10;
    
    label_ClientSTATUS.setBounds(MyX1, MyY1, 150, 25);
    label_ClientSTATUS.setText("Client Status :");
    jf.add(label_ClientSTATUS);
    
    label_ClientSTATUS_Text.setBounds(MyX1+105, MyY1, 400, 25);
    label_ClientSTATUS_Text.setText("No Status Information");
    label_ClientSTATUS_Text.setForeground(Color.RED);
    jf.add(label_ClientSTATUS_Text);
  
    //#############################################
    
    int MyX = 10;
    int MyY = 40;
    
    label_RemoteIP.setBounds(MyX, MyY, 100, 25);
    label_RemoteIP.setText("IP");
    jf.add(label_RemoteIP);
    
    TextField_IP.setBounds(MyX, MyY+20, 100, 25);
    TextField_IP.setText("127.0.0.1");
    jf.add(TextField_IP);
    
    label_RemotePORT.setBounds(MyX+110, MyY, 100, 25);
    label_RemotePORT.setText("PORT");
    jf.add(label_RemotePORT);  
    
    TextField_PORT.setBounds(MyX+110, MyY+20, 100, 25);
    TextField_PORT.setText("56789");
    jf.add(TextField_PORT);  
    
    
    Button_Connect.setBounds(MyX, MyY+50, JButton_Width+100, JButton_Height);
    Button_Connect.addActionListener(new Button_Connect_Handler());
    jf.add(Button_Connect);  
 
    //#############################################

    
    
    
    TextField1.setBounds(10, 195, 500, 25);
    TextField1.setBackground(Color.red);
    jf.add(TextField1);
   
    //#############################################

    groupBoxEncryption_ServerPrint_TextArea.add(scroll_ServerPrint_TextArea);
    scroll_ServerPrint_TextArea.setBounds(10, 220, 500, 220);
    scroll_ServerPrint_TextArea.setVisible(true);
    jf.add(scroll_ServerPrint_TextArea);
    
    
    ServerPrint_TextArea.setBackground(Color.gray);

    //#############################################


 
    
    Button_DataSend.setBounds(10, 150, JButton_Width, JButton_Height);
    Button_DataSend.addActionListener(new Button_DataSend_Handler());
    Button_DataSend.setEnabled(false);
    jf.add(Button_DataSend);


    //#############################################

    jf.setVisible(true);
}


class Button_DataSend_Handler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        out.print(" xxxxxxxxxxxxxxxx \n");
        out.flush();
    }
}



class Button_Connect_Handler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	if (ClientStatus==0){
    	
    		Tr2 = new Thread(new MyTread2());
    		Tr2.start();
    		
    	}else{
			out.close();
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Button_Connect.setText("Connect");
			Button_DataSend.setEnabled(false);
			ClientStatus=0;
    	}
        
        
        
    }
}



public class MyTread2 implements Runnable {

    @Override
    public void run() {

            try {
            	
            	int Remote_PORT = Integer.parseInt(TextField_PORT.getText());
            	String Remote_IP = TextField_IP.getText();
            	
            	
    			Socket client=new Socket(Remote_IP, Remote_PORT);
                     //int P=   client.getReceiveBufferSize();
                     //client.setReceiveBufferSize(3000);
    			
        		ClientStatus = 1 ;			
    			label_ClientSTATUS_Text.setText("Connected with "+client.getRemoteSocketAddress().toString()+"/");
    			Button_Connect.setText("Disconnect");
    			Button_DataSend.setEnabled(true);
    			
    			out=new PrintStream(client.getOutputStream());
    			//in= new BufferedReader(new InputStreamReader(client.getInputStream()));
    			
                        int K;
                        
                       
                      Cou3++;
                        
                      while((K=client.getInputStream().read()) !=-1){
                          //TextField1.setText(Integer.toString(K));
                         // ServerPrint_TextArea.append(Integer.toString(K)+"\n");
                          //System.out.print(K);
                          
                          //k1 k2 T1 T2 T3 T4 Ch1B1 Ch1B2 Ch1B3 Ch2B1...
                          //906 bytes   130 120
                          
                          
                          Temp2=Temp1;
                          Temp1=K;
                          
                          
                          if (Temp2==130 & Temp1==120){
                              
                              TextField1.setText(Integer.toString(Cou3)+"   "+Integer.toString(Cou2)+"   "+Integer.toString(Cou1)+"   "+Integer.toString(Temp1)+"   "+Integer.toString(Temp2));
                              Cou2++; 
                              Cou1=0;
                          
                          }
                          
                                                                                                                                            
                          Cou1++;
                          
                      }
                          
                        
                        
                       
                        
                   
                
           
                
                String myLine;
                int a = 0;
                
              //  while ((myLine = in.readLine()) != null) { 
              //      System.out.print(myLine);
                    
                    
                    //ServerPrint_TextArea.append(myLine + "\n");
                    //TextField1.setText(myLine);
               //     out.print(a + " xxxxxxxxxxxxxxxx \n"); a++;
              //      out.flush();
                    
                    
                    
                    
            //    }	
                
                
    			
            	
            } catch (IOException ex) {
            	label_ClientSTATUS_Text.setText(ex.getMessage());
                System.out.println(ex);
                //ClientStatus = 0;
            }
    }
}
    
    
    
    
    
    
    
    
}
