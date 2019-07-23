package configuracao;

import configuracao.spring.SpringConfiguration;

public class ApplicationEnvironment {
	
	public static final String PROFILE_DESENVOLVIMENTO = "desenvolvimento";
    public static final String PROFILE_PRODUCAO = "producao";
    public static final String PROFILE_TESTE = "teste";
    
    public static final String WELCOME_PAGE = "/index.jsf";
    
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
