package config;


import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources("classpath:config.properties")
public interface ServerConfig extends Config {

    @Key("otusUrl")
    String otusUrl();

    @Key("googleUrl")
    String googleUrl();

}