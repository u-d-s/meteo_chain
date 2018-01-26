package com.birdmanbros.blockchain.meteo_chain;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedList;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Main class.
 *
 */

public class Main {
	public static void main(String[] args) throws IOException {

		if (args == null || args.length == 0) {
			System.out.println(">> missing URL of the node.");
		} else {
			String url = args[0];
			Node node = new Node(url);
			EndPoints.setNode(node);

			if (args.length > 1) {
				for (int i = 1; i < args.length; i++) {
					node.addPeer(args[i]);
				}
			}
			
			runHttpServer(url);
			
			
			
			
//			ObjectMapper mapper = new ObjectMapper();
//			
////			Message req = null;
////			String message_str = "{\"type\":\"TEST\", \"data\":\"transaction\"}";
//			Chain c = new Chain().addNewBlock("happy new year");
//			Chain c;
//			String str = null;
////			System.out.format("str>> %s%n", message_str);
//			
//			
//			
//				String message_str = "[" +
//						  "{" +
//						    "\"index\": 0," +
//						    "\"previousHash\": \"-1\"," +
//						    "\"timestamp\": \"20171218\"," +
//						    "\"data\": \"ok?\"," +
//						    "\"hash\": \"28\"" +
//						  "}" +
//						  "]";
//				
//				try {
//					c = mapper.readValue(message_str, Chain.class);
//					str = mapper.writeValueAsString(c);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//	//			
//				System.out.format("main>> %s%n", str);

		}
	}
	
	private static void runHttpServer(String url) {
		// final String BASE_URI = "http://localhost:58080/meteo_chain/";
		final String BASE_URI = url + "/meteochain";
		final ResourceConfig rc = new ResourceConfig().packages("com.birdmanbros.blockchain.meteo_chain");
		final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
		System.out.println(String.format(
				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
				BASE_URI));
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.stop();
		
	}
}


//		String uri = "http://localhost:58080/meteo_chain";

		
//		final String BASE_URI = args[0];
//		final String BASE_URI = "http://localhost:58080/meteo_chain/";
//		final ResourceConfig rc = new ResourceConfig().packages("com.birdmanbros.blockchain.meteo_chain");
//		final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
//		System.out.println(String.format(
//				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
//				BASE_URI));
//		try {
//			System.in.read();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		server.stop();



//public class Main {
//    // Base URI the Grizzly HTTP server will listen on
//    public static final String BASE_URI = "http://localhost:8080/meteo_chain/";
//
//    /**
//     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
//     * @return Grizzly HTTP server.
//     */
//    public static HttpServer startServer() {
//        // create a resource config that scans for JAX-RS resources and providers
//        // in com.birdmanbros.blockchain.meteo_chain package
//        final ResourceConfig rc = new ResourceConfig().packages("com.birdmanbros.blockchain.meteo_chain");
//
//        // create and start a new instance of grizzly http server
//        // exposing the Jersey application at BASE_URI
//        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
//    }
//
//    /**
//     * Main method.
//     * @param args
//     * @throws IOException
//     */
//    public static void main(String[] args) throws IOException {
//        final HttpServer server = startServer();
//        System.out.println(String.format("Jersey app started with WADL available at "
//                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
//        System.in.read();
//        server.stop();
//    }
//}


