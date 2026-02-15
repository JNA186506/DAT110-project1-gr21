package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessageUtils;

public class RPCUtils {
	
	public static byte[] encapsulate(byte rpcid, byte[] payload) {
		
		byte[] rpcmsg = new byte[1 + payload.length];

		rpcmsg[0] = rpcid;

		System.arraycopy(payload, 0, rpcmsg, 1, payload.length);

		return rpcmsg;
	}
	
	public static byte[] decapsulate(byte[] rpcmsg) {
		
		byte[] payload =  new byte[rpcmsg.length - 1];

		byte rpcid = rpcmsg[0];

		System.arraycopy(rpcmsg, 1, payload, 0, payload.length);

		return payload;
		
	}

	// convert String to byte array
	public static byte[] marshallString(String str) {
		
		byte[] encoded = new byte[str.length()];
		
		char[] stringDelt = str.toCharArray();

		int length = stringDelt.length;
		for (int i = 0; i < length; i++) {
			encoded[i] = (byte) stringDelt[i];
		}

		return encoded;
	}

	// convert byte array to a String
	public static String unmarshallString(byte[] data) {
		
		char[] chars = new char[data.length];

		for (int i = 0; i < data.length; i++) {
			 chars[i] = (char) data[i];
		}

		String decoded = new String(chars);

		return decoded;
	}
	
	public static byte[] marshallVoid() {
		return new byte[0];
	}
	
	public static void unmarshallVoid(byte[] data) {
		if (data.length != 0) {
			throw new IllegalArgumentException("Expected no data...");
		}
	}

	// convert boolean to a byte array representation
	public static byte[] marshallBoolean(boolean b) {
		
		byte[] encoded = new byte[1];
				
		if (b) {
			encoded[0] = 1;
		} else
		{
			encoded[0] = 0;
		}
		
		return encoded;
	}

	// convert byte array to a boolean representation
	public static boolean unmarshallBoolean(byte[] data) {
		
		return (data[0] > 0);
		
	}

	// integer to byte array representation
	public static byte[] marshallInteger(int x) {
		
		byte[] encoded = new byte[4];
		ByteBuffer byteBuffer = ByteBuffer.allocate(4);

		byteBuffer.putInt(x);
		encoded = byteBuffer.array();

		return encoded;
	}
	
	// byte array representation to integer
	public static int unmarshallInteger(byte[] data) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(data);

        if (byteBuffer.remaining() < 4) {
            return 0;
        }

		int decoded = byteBuffer.getInt();

		return decoded;
		
	}
}
