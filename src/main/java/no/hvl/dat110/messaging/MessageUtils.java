package no.hvl.dat110.messaging;

import java.util.Arrays;

import no.hvl.dat110.TODO;

/**
 * @author Johannes Nikolai
 */
public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";
    /** 
    * @param message byte message
     */
	public static byte[] encapsulate(Message message) {
		
		byte[] segment = null;
		byte[] data;
		
        data = message.getData();
        
        segment = new byte[SEGMENTSIZE];
        segment[0] = (byte) data.length;
        
        System.arraycopy(data, 0, segment, 1, data.length);
        
		return segment;
		
	}

    /**
     * @param segment encoded byte message
     * @return the message decoded
     */
	public static Message decapsulate(byte[] segment) {

		Message message = null;
		
        int length = segment[0] & 0xFF;
     
        // Should there be a method to check if the message has the correct length?
        byte[] newMessage = new byte[length];
        
        System.arraycopy(segment, 1, newMessage, 0, length);
        
        message = new Message(newMessage); 
	
		return message;
		
	}
	
}
