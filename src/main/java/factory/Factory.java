package factory;

import repository.Repository;
import service.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Factory {

    private static Service service;
    private static Repository repository;

    static {
        Properties properties = new Properties();
        try (InputStream in = Factory.class.getClassLoader().getResourceAsStream("resources/app.properties")){
            properties.load(in);
            String serviceClass = properties.getProperty("service");
            String repositoryClass = properties.getProperty("repository");
            repository = (Repository) Class.forName(repositoryClass).getConstructor().newInstance();
            service = (Service) Class.forName(serviceClass)
                    .getConstructor(Repository.class).newInstance(repository);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Service getService() {
        return service;
    }

    public static Repository getRepository() {
        return repository;
    }
}
