package com.tuisitang.common.cache.memcached;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.spy.memcached.MemcachedClient;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.common.test.SpringTransactionalContextTests;

public class MemcachedClientTest extends SpringTransactionalContextTests {
	@Autowired
	private SpyMemcachedClient client;

	@Test
	public void get() {
//		String json = client.get(MemcachedObjectType.KeyValue.getPrefix());
//		logger.info("{}", json);
		MemcachedClient memcachedClient = client.getMemcachedClient();
		
		Map<SocketAddress, Map<String, String>> map = memcachedClient.getStats();
		for(Map.Entry<SocketAddress, Map<String, String>>entry : map.entrySet()){
			logger.info("{} \n {}", entry.getKey().getClass(), entry.getValue());
			InetSocketAddress isa = (InetSocketAddress)entry.getKey();
			String[] keys = allkeys(isa.getHostName(), isa.getPort()).split("\n");
	        Arrays.sort(keys);
	        for(String s : keys){
	            System.out.println(s);
	            memcachedClient.delete(s);
	        }
		}
	}
	
	public static void main(String args[]) {
        String[] keys = allkeys("localhost", 11211).split("\n");
        Arrays.sort(keys);
        for(String s : keys){
            System.out.println(s);
        }
        System.out.println(telnet("localhost", 11211, "stats"));
    }

	public static String allkeys(String host, int port) {
		StringBuffer r = new StringBuffer();
		try {
			Socket socket = new Socket(host, port);
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			BufferedReader is = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			os.println("stats items");
			os.flush();
			String l;
			while (!(l = is.readLine()).equals("END")) {
				r.append(l).append("\n");
			}
			String rr = r.toString();
			Set<String> ids = new HashSet<String>();
			if (rr.length() > 0) {
				r = new StringBuffer();// items
				rr.replace("STAT items", "");
				for (String s : rr.split("\n")) {
					ids.add(s.split(":")[1]);
				}
				if (ids.size() > 0) {
					r = new StringBuffer();//
					for (String s : ids) {
						os.println("stats cachedump " + s + " 0");
						os.flush();
						while (!(l = is.readLine()).equals("END")) {
							r.append(l.split(" ")[1]).append("\n");
						}
					}
				}
			}
			os.close();
			is.close();
			socket.close();
		} catch (Exception e) {
			System.out.println("Error" + e);
		}
		return r.toString();
	}
	
	public static String telnet(String host, int port, String cmd) {
		StringBuffer r = new StringBuffer();
		try {
			Socket socket = new Socket(host, port);
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			BufferedReader is = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			os.println(cmd);
			os.flush();
			String l;
			while (!(l = is.readLine()).equals("END")) {
				r.append(l).append("\n");
			}
			os.close();
			is.close();
			socket.close();
		} catch (Exception e) {
			System.out.println("Error" + e);
		}
		return r.toString();
	}
}
