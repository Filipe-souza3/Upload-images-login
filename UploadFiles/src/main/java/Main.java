
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class Main extends ResourceConfig {
	
	public Main() {
		packages("routes");
		//para usar upload de arquivos
		register(MultiPartFeature.class);
//		new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
	}
	
}


//public class Main extends Application {
//@Override
//public Set<Class<?>> getClasses() {
//  final Set<Class<?>> returnValue = new HashSet<Class<?>>();
//  returnValue.add(Route.class);
//  return returnValue;
//}
