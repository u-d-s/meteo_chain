package com.birdmanbros.blockchain.meteo_chain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Main class.
 *
 */

public class Main {
	public static void main(String[] args) {
		System.out.format(">> main(String[]) in Main%n  args[0] %s%n", args[0]);
		
		Node node = new Node(args[0]);
		node.startHttpServer(args[0]);
		
		System.out.format(">> node %s%n", node.toJson());
////		ObjectMapper	 mapper = new ObjectMapper();
//		System.out.format("args[0] %s%n", args[0]);
//		
//		Node node1 = new Node("http://www.birdmanbros.co.jp/meteo_chain/node1");
//		Node node2 = new Node("http://www.birdmanbros.co.jp/meteo_chain/node2");
//		
//		node1.addCounterparty(node2);
//		node2.addCounterparty(node1);
//		
//		try {
//			node1.writeTo(node2, new Message("QUERY_ALL", "alo!"));
//		}catch(Exception x) {
//			x.printStackTrace();
//		}
//		
////		Block block = new Block(10L, "xyz", "2015-12-15T23:30:59.999", "iv got hungry.");	
//		
//
////		System.out.format(">> %s%n", mapper.writeValueAsString(node1));
////		System.out.format(">> %s%n", mapper.writeValueAsString(node2));

	}
}


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


