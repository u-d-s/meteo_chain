package com.birdmanbros.blockchain.meteo_chain;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class.
 *
 */

public class Main {
	public static void main(String[] args) {

		if(args==null || args.length == 0) {
			System.out.println(">> missing URI of the node.");
		}else {
			String uri = args[0];
			Node node = new Node(uri);
			EndPoints.setNode(node);
			
			if(args.length > 1) {
				for(int i=1; i<args.length;i++) {
					node.addPeer(args[i]);
				}
			}
			
			node.startHttpServer();
			
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


