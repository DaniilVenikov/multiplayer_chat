package configuration;

import java.io.FileNotFoundException;
import java.net.URI;

public interface ServerConfigurator{
    ServerConfiguration loadConfiguration(URI pathToConfiguration);
}


