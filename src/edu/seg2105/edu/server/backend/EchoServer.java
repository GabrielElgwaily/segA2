package edu.seg2105.edu.server.backend;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import ocsf.server.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  public void acceptCommands() {
	    BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
	    try {
	      String command;
	      while ((command = consoleInput.readLine()) != null) {
	        if (command.equals("#quit")) {
	          close();
	          System.out.println("turning off server");
	          System.exit(0);
	          
	        } else if (command.equals("#stop")) {
	          stopListening();
	          System.out.println("seerver is not listening for new connections");
	          
	        } else if (command.equals("#close")) {
	          sendToAllClients("the server is closing");
	          close();
	          System.out.println("the server closed all of the connections");
	        } else if (command.startsWith("#setport")) {
	          if (!isListening()) {
	            int port = Integer.parseInt(command.split(" ")[1]);
	            setPort(port);
	            System.out.println("the port is set to " + port);
	            
	          } else {
	            System.out.println("port can't currently be changed");
	          }
	        } else if (command.equals("#start")) {
	          listen();
	        } else if (command.equals("#getport")) {
	          System.out.println("port: " + getPort());
	          
	        } else {
	          System.out.println("command not known");
	        }
	      }
	    } catch (IOException e) {
	      System.out.println("console reading error");
	    } catch (NumberFormatException e) {
	      System.out.println("the port is invalid");
	    }
	  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = DEFAULT_PORT; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Exception e)
    {
    	System.out.println("using default port since it was not specified"); //Set port to 5555. changed to print
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
      System.out.println("started server");
    } 
    catch (IOException e) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
    sv.acceptCommands();
  }
  public void clientConnected(ConnectionToClient client) {
      System.out.println("the client " + client.getInfo("loginId") + " is connected");
  }
  public void clientDisconnected(ConnectionToClient client) {
      System.out.println("the client " + client.getInfo("loginId") + " is disconnected");
  }
  public void clientException(ConnectionToClient client, Throwable exception) {
      System.out.println("the client " + client.getInfo("loginId") + "  encountered an error and disconnected");
  }

}
//End of EchoServer class
