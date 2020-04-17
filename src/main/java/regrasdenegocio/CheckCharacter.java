package regrasdenegocio;

import DAO.AbstractDAO;
import DAO.FormerNameDAO;
import DAO.PersonagemDAO;
import com.mysql.jdbc.CommunicationsException;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.AccountCharacters;
import model.AchievementPoints;
import model.Death;
import model.FormerName;
import model.FormerWorld;
import model.Guild;
import model.House;
import model.LevelAdvance;
import model.Personagem;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.spi.ServiceException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckCharacter {

    /* afterTitle = o conteúdo após o título do elemento */
    private final int afterTitle = 1;
    /* Tamanho da lista quando não é o conteúdo que procuro */
    private final int characterDoesNotExists = 6;
    /* Eliminar conteúdo lixo da lista de elementos */
    private final int trashEliminator = 2;

    public Personagem discoverCharacter(String name) {
        Personagem personagem = null;

        try {

            List<String> elementsList;

            String url = "https://www.tibia.com/community/?subtopic=characters&name=" + name;

            /* Conecta com o domínio */
            Document document = Jsoup.connect(url).get();

            /* Coloca os itens numa lista */
            Elements chosenElements = document.getElementsByClass("Content");
            elementsList = chosenElements.get(0).getElementsByTag("td").eachText();

            /* Se não existe o char, retorna null */
            if (elementsList.get(0).toLowerCase().equals("could not find character:")) {
                return null;

            } else {
                
                /* Se o char existir, inicia as chamadas do banco */
                personagem = new Personagem();
                Personagem p = new Personagem();
                

                System.out.println(elementsList.toString());
                for (int i = 1; i < elementsList.size() - trashEliminator; i++) {

                    /* Trabalhando com a lista de elementos do Tibia.com*/
                    switch (elementsList.get(i).toLowerCase()) {
                        
                        case "name:":
                            personagem.setName(elementsList.get(i + afterTitle));
                            /* Primeira persistência para salvar as tabelas fracas em relacionamento bilateral*/
                            new AbstractDAO<>(Personagem.class).insert(personagem);
                            
                            /* Retorna id para outras persistências */
                            int idCharacter = new PersonagemDAO().returnID(elementsList.get(i + afterTitle));
                            p.setIdCharacter(idCharacter);
                            
                            break;
//                            
                        case "former names:":
                            /* Pega todos os former names */
                            String[] splitNames = elementsList.get(i + afterTitle).trim().split(",");
                            
                            for (String rname : splitNames) {
                                /* Regra de persistência bilateral*/
                                FormerName fn = new FormerName(rname, Calendar.getInstance(), Calendar.getInstance());
                                fn.setPersonagem(p);
                                
                                new AbstractDAO<>(FormerName.class).insert(fn);
                                
                            }   
                            
                            break;
                            
                        case "title:":
                            personagem.setTitle(elementsList.get(i + afterTitle));
                            break;
                            
                        case "sex:":
                            personagem.setSex(elementsList.get(i + afterTitle));
                            break;
                            
                        case "vocation:":
                            personagem.setVocation(elementsList.get(i + afterTitle));
                            break;
                            
                        case "level:":
                            LevelAdvance la = new LevelAdvance(p, Integer.valueOf(elementsList.get(i + afterTitle)),
                                    Calendar.getInstance());
                            new AbstractDAO<>(LevelAdvance.class).insert(la);
                            break;
                            
                        case "achievement points:":
                            AchievementPoints ap = new AchievementPoints(p, Integer.valueOf(elementsList.get(i + afterTitle)),
                                    Calendar.getInstance());
                            new AbstractDAO<>(AchievementPoints.class).insert(ap);
                            break;
                            
                        case "world:":
                            personagem.setWorld(elementsList.get(i + afterTitle));
                            break;
                            
                        case "former world:":
                            FormerWorld fw = new FormerWorld(p, elementsList.get(i + afterTitle), Calendar.getInstance(), Calendar.getInstance());
                            new AbstractDAO<>(FormerWorld.class).insert(fw);
                            break;
                            
                        case "residence:":
                            personagem.setResidence(elementsList.get(i + afterTitle));
                            break;
                            
                        case "house:":
                            String[] nameHouse = elementsList.get(i + afterTitle).split(" is ");
                            House h = new House(p, nameHouse[0], Calendar.getInstance(), Calendar.getInstance());
                            new AbstractDAO<>(House.class).insert(h);
                            break;
                            
                        case "guild membership:":
                            String[] guildInfo = elementsList.get(i + afterTitle).split("of the");
                            Guild g = new Guild(p, guildInfo[1], guildInfo[0], Calendar.getInstance(), Calendar.getInstance());
                            new AbstractDAO<>(Guild.class).insert(g);
                            break;
                            
                        case "last login:":
                            personagem.setLastLogin(elementsList.get(i + afterTitle));
                            break;
                            
                        case "account status:":
                            personagem.setAccountStatus(elementsList.get(i + afterTitle));
                            break;
                            
                        case "character deaths":
                            int j = 1;
                            while (!elementsList.get(i + j).toLowerCase().equals("search character")
                                    && !elementsList.get(i + j).toLowerCase().equals("account information")) {
                                
                                /* Regras de negócio */
                                Death d = new Death(p, elementsList.get(i + j + 1), elementsList.get(i + j));
                                new AbstractDAO<>(Death.class).insert(d);
                                
                                j = j + 2;
                            }   i += j;
                            break;
                            
                        case "account information":
                            personagem.setTitleAccountInformation(elementsList.get(i + 2));
                            personagem.setDateCreate(Calendar.getInstance());
                            break;
                            
                        default:
                            break;
                    }

                } // fim FOR

                /* Persiste Personagem */
                personagem.setRegisterDate(Calendar.getInstance());
                new AbstractDAO<>(Personagem.class).update(personagem);

            } // fim ELSE

        } catch (IOException e) {
            System.out.println("Erro na conexão Jsoup: " + e);

        } catch (java.lang.IllegalArgumentException e) {
            System.out.println("Url inválida: " + e);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Quebra de array: " + e);

        } catch (ServiceException | JDBCConnectionException e) {
            /* Implementar */
            System.out.println("Erro de conexão com o Banco: " + e);
        }

        return personagem;
    }

    public Personagem updateCharacter(String name) {
        Personagem personagem = null;

        try {
            
            List<String> elementsList;
    
            String url = "https://www.tibia.com/community/?subtopic=characters&name=" + name;

            /* Conecta com o domínio */
            Document document = Jsoup.connect(url).get();

            /* Coloca os itens numa lista */
            Elements chosenElements = document.getElementsByClass("Content");
            elementsList = chosenElements.get(0).getElementsByTag("td").eachText();

            /* Se não existe o char, char foi deletado */
            if (elementsList.get(0).toLowerCase().equals("could not find character:")) {
                return null;
                // regra para chars deletados

            } else {
                
                /* Se o char existir, inicia as chamadas do banco */
                personagem = new AbstractDAO<>(Personagem.class).searchByString(name);
                int idCharacter = new PersonagemDAO().returnID(name);
                Personagem p = new Personagem();
                p.setIdCharacter(idCharacter);

                System.out.println(elementsList.toString());
                for (int i = 1; i < elementsList.size() - trashEliminator; i++) {

                    /* Trabalhando com a lista de elementos do Tibia.com*/
                    switch (elementsList.get(i).toLowerCase()) {
                        
                        case "name:":
                            
                            /* Se o nome for diferente do bd, é que mudou de nick */
                            if (!personagem.getName().equals(elementsList.get(i + afterTitle))) {
                                /* Atualiza novo nick */
                                personagem.setName(elementsList.get(i + afterTitle));
                                
                                /* Coloca o antigo nick como formerName */
                                
                                
                                FormerName fn = new FormerName(name, Calendar.getInstance(), Calendar.getInstance());
                                /* Buscar a data de começo do último fn*/
                                
                                new AbstractDAO<>(FormerName.class).insert(fn);
                            }
                            /* Retorna id para outras persistências */
                            
                            break;
                            
                        case "former names:":
                            /* Pega todos os former names */
                            String[] splitNames = elementsList.get(i + afterTitle).trim().split(",");
                            for (String rname : splitNames) {
                                /* Regra de persistência bilateral*/
                                FormerName fn = new FormerName(rname, Calendar.getInstance(), Calendar.getInstance());
                                fn.setPersonagem(p);
                                
                                new AbstractDAO<>(FormerName.class).insert(fn);
                                
                            }   
                            break;
                            
                        case "title:":
                            personagem.setTitle(elementsList.get(i + afterTitle));
                            break;
                            
                        case "sex:":
                            personagem.setSex(elementsList.get(i + afterTitle));
                            break;
                            
                        case "vocation:":
                            personagem.setVocation(elementsList.get(i + afterTitle));
                            break;
                            
                        case "level:":
                            LevelAdvance la = new LevelAdvance(p, Integer.valueOf(elementsList.get(i + afterTitle)),
                                    Calendar.getInstance());
                            new AbstractDAO<>(LevelAdvance.class).insert(la);
                            break;
                            
                        case "achievement points:":
                            AchievementPoints ap = new AchievementPoints(p, Integer.valueOf(elementsList.get(i + afterTitle)),
                                    Calendar.getInstance());
                            new AbstractDAO<>(AchievementPoints.class).insert(ap);
                            break;
                            
                        case "world:":
                            personagem.setWorld(elementsList.get(i + afterTitle));
                            break;
                            
                        case "former world:":
                            FormerWorld fw = new FormerWorld(p, elementsList.get(i + afterTitle), Calendar.getInstance());
                            new AbstractDAO<>(FormerWorld.class).insert(fw);
                            break;
                            
                        case "residence:":
                            personagem.setResidence(elementsList.get(i + afterTitle));
                            break;
                            
                        case "house:":
                            String[] nameHouse = elementsList.get(i + afterTitle).split(" is ");
                            House h = new House(p, nameHouse[0], Calendar.getInstance());
                            new AbstractDAO<>(House.class).insert(h);
                            break;
                            
                        case "guild membership:":
                            String[] guildInfo = elementsList.get(i + afterTitle).split("of the");
                            Guild g = new Guild(p, guildInfo[0], guildInfo[1], Calendar.getInstance());
                            new AbstractDAO<>(Guild.class).insert(g);
                            break;
                            
                        case "last login:":
                            personagem.setLastLogin(elementsList.get(i + afterTitle));
                            break;
                            
                        case "account status:":
                            personagem.setAccountStatus(elementsList.get(i + afterTitle));
                            break;
                            
                        case "character deaths":
                            int j = 1;
                            while (!elementsList.get(i + j).toLowerCase().equals("search character")
                                    && !elementsList.get(i + j).toLowerCase().equals("account information")) {
                                
                                /* Regras de negócio */
                                Death d = new Death(p, elementsList.get(i + j + 1), elementsList.get(i + j));
                                new AbstractDAO<>(Death.class).insert(d);
                                
                                j = j + 2;
                            }   i += j;
                            break;
                            
                        case "account information":
                            personagem.setTitleAccountInformation(elementsList.get(i + 2));
                            personagem.setDateCreate(Calendar.getInstance());
                            break;
                            
                        default:
                            break;
                    }

                  
                } // fim FOR

                /* Persiste Personagem */
                
                new AbstractDAO<>(Personagem.class).update(personagem);

            } // fim ELSE

        } catch (IOException e) {
            System.out.println("Erro na conexão Jsoup: " + e);

        } catch (java.lang.IllegalArgumentException e) {
            System.out.println("Url inválida: " + e);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Quebra de array: " + e);

        }

       
        return personagem;
    }

}
