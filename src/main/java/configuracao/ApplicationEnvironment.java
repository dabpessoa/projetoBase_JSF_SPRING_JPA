package configuracao;

import configuracao.spring.SpringConfiguration;

public class ApplicationEnvironment {
	
	public static final String PROFILE_DESENVOLVIMENTO = "desenvolvimento";
    public static final String PROFILE_PRODUCAO = "producao";
    public static final String PROFILE_TESTE = "teste";
    
    public static final String WELCOME_PAGE = "/index.jsf";
    public static final String SPRING_DISPATCHER_SERVLET_URI = "/api/*";
    
    public static final String PROPERTY_SOURCE_EXTENSION = ".yml";
    public static final String PROPERTY_SOURCE_PREFFIX = "application";
    public static final String PROPERTY_SOURCE_DEFAULT_NAME = PROPERTY_SOURCE_PREFFIX+PROPERTY_SOURCE_EXTENSION;
    public static final String PROPERTY_SOURCE_DESENVOLVIMENTO_NAME = PROPERTY_SOURCE_PREFFIX+"_"+PROFILE_DESENVOLVIMENTO+PROPERTY_SOURCE_EXTENSION;
    public static final String PROPERTY_SOURCE_PRODUCAO_NAME = PROPERTY_SOURCE_PREFFIX+"_"+PROFILE_PRODUCAO+PROPERTY_SOURCE_EXTENSION;
    public static final String PROPERTY_SOURCE_TESTE_NAME = PROPERTY_SOURCE_PREFFIX+"_"+PROFILE_TESTE+PROPERTY_SOURCE_EXTENSION;
    
    public static final String[] modelPackages;
    static {
    	modelPackages = new String[]{
                "br.com.digitoglobal.projeto.bean.model"};
    }
    
    public static final Class<?>[] springConfigurationClasses;
    static {
    	springConfigurationClasses = new Class<?>[]{SpringConfiguration.class};
    }
	
}
