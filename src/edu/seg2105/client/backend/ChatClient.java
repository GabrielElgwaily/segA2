// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package edu.seg2105.client.backend;

import ocsf.client.*;

import java.io.*;

import edu.seg2105.client.common.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
    
    
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
      sendToServer(message);
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  public void connectionClosed() {
	  System.out.println("the server has shut down");
	  System.exit(0);
  }
  public void connectionException(Exception exception) {
	  System.out.println("lost connection to the server");
	  System.exit(0);
  }
  public void  handleCommand(String message) {
	  String[] postPartedCommand = message.split(" ");
	  String command = postPartedCommand[0];
	    if (command.equals("#quit")) {
	        quit();
	    } else if (command.equals("#logoff")) {
	        try {
	            closeConnection();
	            clientUI.display("logged off the server");
	        } catch (IOException e) {
	            clientUI.display("there was an error logging off");
	        }
	    } else if (command.equals("#sethost")) {
	        if (!isConnected()) {
	            if (postPartedCommand.length > 1) {
	                setHost(postPartedCommand[1]);
	                clientUI.display("host is set to " + postPartedCommand[1]);
	            } else {
	                clientUI.display("to set the host: #sethost <host>");
	            }
	        } else {
	            clientUI.display("you cant set the host while connected");
	        }
	    } else if (command.equals("#setport")) {
	        if (!isConnected()) {
	            if (postPartedCommand.length > 1) {
	                try {
	                    int port = Integer.parseInt(postPartedCommand[1]);
	                    setPort(port);
	                    clientUI.display("the port is set to " + port);
	                    
	                } catch (NumberFormatException e) {
	                    clientUI.display("the port number is not valid");
	                }
	            } else {
	                clientUI.display("to set the port: #setport <port>");
	                
	            }
	        } else {
	            clientUI.display("the port cannot be set while connected");
	            
	        }
	    } else if (command.equals("#login")) {
	        if (!isConnected()) {
	            try {
	                openConnection();
	                clientUI.display("logged in");
	            } catch (IOException e) {
	                clientUI.display("can't login to the server");
	            }
	        } else {
	            clientUI.display("connected ");
	            
	        }
	    } else if (command.equals("#gethost")) {
	        clientUI.display("current host: " + getHost());
	    } else if (command.equals("#getport")) {
	        clientUI.display("current port: " + getPort());
	    } else {
	        clientUI.display("command not known");
	    }
	}
  
}
//End of ChatClient class
