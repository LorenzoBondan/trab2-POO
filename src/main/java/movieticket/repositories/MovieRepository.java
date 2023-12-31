package movieticket.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import movieticket.entities.Cinema;
import movieticket.entities.Gender;
import movieticket.entities.Movie;
import movieticket.entities.Schedule;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.ResourceNotFoundException;

public class MovieRepository {

	private String file = "movies.csv";
	private String file_actors_movies = "actors_movies.csv";
	private String file_directors_movies = "directors_movies.csv";

	private GenderRepository genderRepository = new GenderRepository();
	private CinemaRepository cinemaRepository = new CinemaRepository();

	
	public List<Movie> findAll() {
		return load();
	}

    public List<Movie> findAllByName(String name) {
        List<Movie> movies = load();
        return movies.stream()
                .filter(movie -> movie.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
	
	public List<Movie> findAllByGenderId(Long genderId){
		List<Movie> list = load();
		return list.stream()
	            .filter(movie -> movie.getGender().getId().equals(genderId)) // filtra os filmes com o genderId fornecido
	            .collect(Collectors.toList());
	}
	
	public List<Movie> findAllByCinemaId(Long cinemaId){
		List<Movie> list = load();
		return list.stream()
	            .filter(movie -> movie.getCinema().getId().equals(cinemaId)) // filtra os filmes com o cinemaId fornecido
	            .collect(Collectors.toList());
	}
	
	public List<Movie> findAllByActorId(Long actorId){
		return loadMoviesByActorId(actorId);
	}
	
	public List<Movie> findAllByDirectorId(Long directorId){
		return loadMoviesByDirectorId(directorId);
	}

	public Optional<Movie> findById(Long id) {
	    List<Movie> list = load();
	    return Optional.ofNullable(list.stream()
	            .filter(movie -> movie.getId().equals(id))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("Filme com Id " + id + " não encontrado.")));
	}
	
	public void insert(Movie movie) {
	    List<Movie> list = load(); // carrega a lista de objetos já cadastrados no csv
	    Long newId = movie.getId(); // id do objeto a ser inserido
	    boolean idExists = list.stream().anyMatch(existingMovie -> existingMovie.getId().equals(newId)); // percorre a lista para ver se o id já está cadastrado
	    if (idExists) {
	        throw new DuplicateResourceException("Filme com Id " + newId + " já existe.");
	    }
	    list.add(movie); // adiciona o objeto a lista
	    save(list); // salva a lista novamente
	}

    public void update(Movie updatedMovie) {
        List<Movie> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isUpdated = false; // inicia como falso para encontrar o objeto

        for (Movie movie : list) { // percorre a lista
            if (movie.getId().equals(updatedMovie.getId())) { // quando achou o objeto procurado, atualiza seus dados
                movie.setName(updatedMovie.getName());
                movie.setDescription(updatedMovie.getDescription());
                movie.setDuration(updatedMovie.getDuration());
                movie.setYear(updatedMovie.getYear());
                movie.setActors(updatedMovie.getActors());
                movie.setCinema(updatedMovie.getCinema());
                movie.setDirectors(updatedMovie.getDirectors());
                movie.setSchedules(updatedMovie.getSchedules());
                //movie.setTickets(updatedMovie.getTickets());
                isUpdated = true; // muda para verdadeiro
                break;
            }
        }

        if (!isUpdated) {
            throw new ResourceNotFoundException("Filme com Id " + updatedMovie.getId() + " não encontrado.");
        }
        save(list); // salva a lista novamente
    }

    public void delete(Long id) {
        List<Movie> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isDeleted = list.removeIf(movie -> movie.getId().equals(id)); // verifica a existência do id e o deleta

        if (!isDeleted) {
            throw new ResourceNotFoundException("Filme com Id " + id + " não encontrado.");
        }
        save(list);
    }

    public void save(List<Movie> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Movie movie : list) {
                writer.write(movie.getId() + "," 
                			+ movie.getName() + "," 
                			+ movie.getYear() + "," 
                			+ movie.getDescription() + ","
                			+ movie.getDuration() + ","
                			+ movie.getGender().getId() + ","
                			+ movie.getCinema().getId()
                		);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Falha ao abrir o arquivo: " + file);
        }
    }

    public List<Movie> load() {
        List<Movie> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // atributos separados por vírgula
                Long id = Long.parseLong(data[0]);
                String name = data[1];
                Integer year = Integer.parseInt(data[2]);
                String description = data[3];
                Integer duration = Integer.parseInt(data[4]);
                Long genderId = Long.parseLong(data[5]);
                Gender gender = genderRepository.findById(genderId).get();
                Cinema cinema = cinemaRepository.findById(Long.parseLong(data[6])).get();
                
                Movie movie = new Movie(id, name, year, description, duration, gender, cinema);
                list.add(movie);
            }
        } catch (IOException e) {
            System.out.println("Falha ao abrir o arquivo: " + file);
        }
        return list;
    }
    
    public List<Movie> loadMoviesByActorId(Long actorId){
        List<Movie> list = new ArrayList<>();
        File fileObject = new File(file_actors_movies);

        if (fileObject.exists() && fileObject.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileObject))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(","); // atributos separados por vírgula
                    Long actorId1 = Long.parseLong(data[0]);
                    Long movieId = Long.parseLong(data[1]);
                    if (actorId == actorId1) {
                        Movie movie = findById(movieId).orElse(null);
                        list.add(movie);
                    }
                }
            } catch (IOException e) {
                System.out.println("Falha ao abrir o arquivo: " + file);
            }
        } else {
            System.out.println("Arquivo vazio ou não encontrado.");
        }
        return list;
    }
    
    public List<Movie> loadMoviesByDirectorId(Long directorId){
        List<Movie> list = new ArrayList<>();
        File fileObject = new File(file_directors_movies);

        if (fileObject.exists() && fileObject.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileObject))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(","); // atributos separados por vírgula
                    Long directoId1 = Long.parseLong(data[0]);
                    Long movieId = Long.parseLong(data[1]);
                    if (directorId == directoId1) {
                        Movie movie = findById(movieId).orElse(null);
                        list.add(movie);
                    }
                }
            } catch (IOException e) {
                System.out.println("Falha ao abrir o arquivo: " + file);
            }
        } else {
            System.out.println("Arquivo vazio ou não encontrado.");
        }
        return list;
    }
}
