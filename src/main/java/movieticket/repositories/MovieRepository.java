package movieticket.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import movieticket.entities.Cinema;
import movieticket.entities.Gender;
import movieticket.entities.Movie;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.ResourceNotFoundException;

public class MovieRepository {

private String file = "movies.csv";

	private GenderRepository genderRepository = new GenderRepository();
	private CinemaRepository cinemaRepository = new CinemaRepository();
	
	public List<Movie> findAll() {
		return load();
	}
	
	public Optional<Movie> findById(Long id) {
	    List<Movie> list = load();
	    return Optional.ofNullable(list.stream()
	            .filter(movie -> movie.getId().equals(id))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("Movie with Id " + id + " not found.")));
	}
	
	public void insert(Movie movie) {
	    List<Movie> list = load(); // carrega a lista de objetos já cadastrados no csv
	    Long newId = movie.getId(); // id do objeto a ser inserido
	    boolean idExists = list.stream().anyMatch(existingMovie -> existingMovie.getId().equals(newId)); // percorre a lista para ver se o id já está cadastrado
	    if (idExists) {
	        throw new DuplicateResourceException("Movie with ID " + newId + " already exists.");
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
                isUpdated = true; // muda para verdadeiro
                break;
            }
        }

        if (!isUpdated) {
            throw new ResourceNotFoundException("Movie with Id " + updatedMovie.getId() + " not found.");
        }
        save(list); // salva a lista novamente
    }

    public void delete(Long id) {
        List<Movie> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isDeleted = list.removeIf(movie -> movie.getId().equals(id)); // verifica a existência do id e o deleta

        if (!isDeleted) {
            throw new ResourceNotFoundException("Movie with Id " + id + " not found.");
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return list;
    }
}
