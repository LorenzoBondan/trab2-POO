package movieticket.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import movieticket.entities.Actor;
import movieticket.entities.Director;
import movieticket.entities.Person;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.ResourceNotFoundException;

public class PersonRepository {

	private String file = "people.csv";
	private String file_actors_movies = "actors_movies.csv";
	private String file_directors_movies = "directors_movies.csv";
	
	public List<Person> findAll() {
		return load();
	}
	
	public List<Actor> findAllActors(){
		return loadAllActors();
	}
	
	public List<Director> findAllDirectors(){
		return loadAllDirectors();
	}
	
	public List<Actor> findAllActorsByMovieId(Long movieId){
		return loadActorsByMovieId(movieId);
	}
	
	public List<Director> findAllDirectorsByMovieId(Long movieId){
		return loadDirectorsByMovieId(movieId);
	}
	
	public Optional<Person> findById(Long id) {
	    List<Person> list = load();
	    return Optional.ofNullable(list.stream()
	            .filter(person -> person.getId().equals(id))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("Person with Id " + id + " not found.")));
	}
	
	public Optional<Actor> findActorById(Long id) {
	    List<Actor> list = loadAllActors();
	    return Optional.ofNullable(list.stream()
	            .filter(actor -> actor.getId().equals(id))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("Actor with Id " + id + " not found.")));
	}
	
	public Optional<Director> findDirectorById(Long id) {
	    List<Director> list = loadAllDirectors();
	    return Optional.ofNullable(list.stream()
	            .filter(director -> director.getId().equals(id))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("Director with Id " + id + " not found.")));
	}
	
	public void insert(Person person) {
	    List<Person> list = load(); // carrega a lista de objetos já cadastrados no csv
	    Long newId = person.getId(); // id do objeto a ser inserido
	    boolean idExists = list.stream().anyMatch(existingPerson -> existingPerson.getId().equals(newId)); // percorre a lista para ver se o id já está cadastrado
	    if (idExists) {
	        throw new DuplicateResourceException("Pessoa com ID " + newId + " já existe.");
	    }
	    list.add(person); // adiciona o objeto a lista
	    save(list); // salva a lista novamente
	}

    public void update(Person updatedPerson) {
        List<Person> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isUpdated = false; // inicia como falso para encontrar o objeto

        for (Person person : list) { // percorre a lista
            if (person.getId().equals(updatedPerson.getId())) { // quando achou o objeto procurado, atualiza seus dados
                person.setName(updatedPerson.getName());
                person.setRole(updatedPerson.getRole());
                person.setMarried(updatedPerson.getMarried());
                isUpdated = true; // muda para verdadeiro
                break;
            }
        }

        if (!isUpdated) {
            throw new ResourceNotFoundException("Person with Id " + updatedPerson.getId() + " not found.");
        }
        save(list); // salva a lista novamente
    }

    public void delete(Long id) {
        List<Person> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isDeleted = list.removeIf(person -> person.getId().equals(id)); // verifica a existência do id e o deleta

        if (!isDeleted) {
            throw new ResourceNotFoundException("Person with Id " + id + " not found.");
        }
        save(list);
    }

    public void save(List<Person> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Person person : list) {
                writer.write(person.getId() + "," 
                			+ person.getName() + "," 
                			+ person.getRole() + ","
                			+ person.getMarried().getId()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Person> load() {
        List<Person> list = new ArrayList<>();
        List<Long> marriedIds = new ArrayList<>(); // lista para armazenar ids das pessoas casadas

        File fileObject = new File(file);

        if (fileObject.exists() && fileObject.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileObject))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(","); // atributos separados por vírgula
                    Long id = Long.parseLong(data[0]);
                    String name = data[1];
                    String role = data[2];
                    Long marriedPersonId = Long.parseLong(data[3]);

                    if (marriedPersonId != 0) {
                        // se o id da pessoa casada não for 0, adiciona-o a lista de ids das pessoas casadas
                        marriedIds.add(marriedPersonId);
                    }

                    // cria a pessoa sem associar a pessoa casada por enquanto
                    Person person = new Person(id, name, role, null);
                    list.add(person);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // associa as pessoas casadas na segunda passagem
            for (Person person : list) {
                Long marriedPersonId = marriedIds.get((int) ((Long) person.getId() - 1)); // -1 porque os ids começam de 1
                if (marriedPersonId != 0) {
                    Person marriedPerson = list.get((int) (marriedPersonId - 1)); // -1 porque os ids começam de 1
                    person.setMarried(marriedPerson);
                }
            }
        } else {
            System.out.println("Empty or non existing file.");
        }

        return list;
    }
    
    public List<Actor> loadAllActors() {
        List<Person> people = load();
        List<Actor> actors = people.stream()
                .filter(person -> "Actor".equals(person.getRole()))
                .map(person -> new Actor(person.getId(), person.getName(), person.getRole(), person.getMarried()))
                .collect(Collectors.toList());
        return actors;
    }
    
    public List<Director> loadAllDirectors() {
        List<Person> people = load();
        List<Director> directors = people.stream()
                .filter(person -> "Director".equals(person.getRole()))
                .map(person -> new Director(person.getId(), person.getName(), person.getRole(), person.getMarried()))
                .collect(Collectors.toList());
        return directors;
    }
    
    public List<Actor> loadActorsByMovieId(Long movieId){
        List<Actor> list = new ArrayList<>();
        File fileObject = new File(file_actors_movies);

        if (fileObject.exists() && fileObject.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileObject))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(","); // atributos separados por vírgula
                    Long actorId = Long.parseLong(data[0]);
                    Long movieId1 = Long.parseLong(data[1]);
                    if (movieId == movieId1) {
                        Person actorPerson = findById(actorId).orElse(null);
                        list.add(new Actor(actorPerson.getId(), actorPerson.getName(), actorPerson.getRole(), actorPerson.getMarried()));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Empty or non existing file.");
        }
        return list;
    }
    
    public List<Director> loadDirectorsByMovieId(Long movieId){
        List<Director> list = new ArrayList<>();
        File fileObject = new File(file_directors_movies);

        if (fileObject.exists() && fileObject.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileObject))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(","); // atributos separados por vírgula
                    Long directorId = Long.parseLong(data[0]);
                    Long movieId1 = Long.parseLong(data[1]);
                    if (movieId == movieId1) {
                        Person directorPerson = findById(directorId).orElse(null);
                        list.add(new Director(directorPerson.getId(), directorPerson.getName(), directorPerson.getRole(), directorPerson.getMarried()));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Empty or non existing file.");
        }
        return list;
    }
    
    ////// RELAÇÕES MUITOS PARA MUITOS
    
    public void addActorToMovie(Long actorId, Long movieId) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_actors_movies, true))) {
            writer.write(actorId + "," + movieId);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addDirectorToMovie(Long directorId, Long movieId) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_directors_movies, true))) {
            writer.write(directorId + "," + movieId);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean existsByActorIdAndMovieId(Long actorId, Long movieId){
        try (BufferedReader reader = new BufferedReader(new FileReader(file_actors_movies))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Long storedActorId = Long.parseLong(data[0]);
                Long storedMovieId = Long.parseLong(data[1]);
                if (actorId.equals(storedActorId) && movieId.equals(storedMovieId)) {
                    return true; // a relação ator-filme foi encontrada no arquivo.
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao verificar a existência da relação ator-filme.");
        }
        return false; // a relação ator-filme não foi encontrada no arquivo.
    }

    public Boolean existsByDirectorIdAndMovieId(Long directorId, Long movieId){
        try (BufferedReader reader = new BufferedReader(new FileReader(file_directors_movies))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Long storedDirectorId = Long.parseLong(data[0]);
                Long storedMovieId = Long.parseLong(data[1]);
                if (directorId.equals(storedDirectorId) && movieId.equals(storedMovieId)) {
                    return true; // a relação diretor-filme foi encontrada no arquivo.
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao verificar a existência da relação diretor-filme.");
        }
        return false; // a relação diretor-filme não foi encontrada no arquivo.
    }
    
    public void removeActorsFromMovie(List<Long> actorsToRemove, Long movieId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file_actors_movies));
             BufferedWriter writer = new BufferedWriter(new FileWriter("temp_actors_movies.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Long actorId = Long.parseLong(data[0]);
                Long movieIdInFile = Long.parseLong(data[1]);
                if (!movieId.equals(movieIdInFile) || movieId.equals(movieIdInFile) && !actorsToRemove.contains(actorId)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao remover atores do filme.");
            return;
        }

        // substitui o arquivo original
        File originalFile = new File(file_actors_movies);
        originalFile.delete();
        
        File tempFile = new File("temp_actors_movies.csv");
        tempFile.renameTo(originalFile);
    }
    
    public void removeDirectorsFromMovie(List<Long> directorsToRemove, Long movieId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file_directors_movies));
                BufferedWriter writer = new BufferedWriter(new FileWriter("temp_directors_movies.csv"))) {
               String line;
               while ((line = reader.readLine()) != null) {
                   String[] data = line.split(",");
                   Long directorId = Long.parseLong(data[0]);
                   Long movieIdInFile = Long.parseLong(data[1]);
                   if (!movieId.equals(movieIdInFile) || movieId.equals(movieIdInFile) && !directorsToRemove.contains(directorId)) {
                       writer.write(line);
                       writer.newLine();
                   }
               }
           } catch (IOException e) {
               e.printStackTrace();
               System.out.println("Erro ao remover diretores do filme.");
               return;
           }

        // substitui o arquivo original
        File originalFile = new File(file_directors_movies);
        originalFile.delete();
        
        File tempFile = new File("temp_directors_movies.csv");
        tempFile.renameTo(originalFile);
    }

}
