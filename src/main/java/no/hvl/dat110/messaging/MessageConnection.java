package no.hvl.dat110.messaging;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MessageConnection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection
	
	public MessageConnection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream(socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) {

		byte[] data;

        data = MessageUtils.encapsulate(message);

		try {
			outStream.write(data);
		} catch (IOException e) {
			throw new RuntimeException("Failed to send message " + e.getMessage());
		}
        
	}

    public Message receive() {
        byte[] segment = new byte[MessageUtils.SEGMENTSIZE];
        
        int read = 0;
		int r;
        while (read < segment.length) {
			try {
				r = inStream.read(segment, read, segment.length - read);
			} catch (IOException e) {
				throw new RuntimeException("Failed to recieve message " + e.getMessage());
			}
            if (r == -1) {
				throw new RuntimeException("Connection closed too early");
            }
            read += r;
        }
        return MessageUtils.decapsulate(segment);
    }

	// close the connection by closing streams and the underlying socket	
	public void close() {

		try {
			
			outStream.close();
			inStream.close();

			socket.close();
			
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}