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
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.ResourceNotFoundException;

public class CinemaRepository {

	private String file = "cinemas.csv";
	
	public List<Cinema> findAll() {
		return load();
	}
	
	public Optional<Cinema> findById(Long id) {
	    List<Cinema> list = load();
	    return Optional.ofNullable(list.stream()
	            .filter(cinema -> cinema.getId().equals(id))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("Cinema with Id " + id + " not found.")));
	}
	
	public void insert(Cinema cinema) {
	    List<Cinema> list = load(); // carrega a lista de objetos já cadastrados no csv
	    Long newId = cinema.getId(); // id do objeto a ser inserido
	    boolean idExists = list.stream().anyMatch(existingCinema -> existingCinema.getId().equals(newId)); // percorre a lista para ver se o id já está cadastrado
	    if (idExists) {
	        throw new DuplicateResourceException("Cinema with ID " + newId + " already exists.");
	    }
	    list.add(cinema); // adiciona o objeto a lista
	    save(list); // salva a lista novamente
	}

    public void update(Cinema updatedCinema) {
        List<Cinema> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isUpdated = false; // inicia como falso para encontrar o objeto

        for (Cinema cinema : list) { // percorre a lista
            if (cinema.getId().equals(updatedCinema.getId())) { // quando achou o objeto procurado, atualiza seus dados
                cinema.setName(updatedCinema.getName());
            	cinema.setAddress(updatedCinema.getAddress());
                isUpdated = true; // muda para verdadeiro
                break;
            }
        }

        if (!isUpdated) {
            throw new ResourceNotFoundException("Cinema with Id " + updatedCinema.getId() + " not found.");
        }
        save(list); // salva a lista novamente
    }

    public void delete(Long id) {
        List<Cinema> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isDeleted = list.removeIf(cinema -> cinema.getId().equals(id)); // verifica a existência do id e o deleta

        if (!isDeleted) {
            throw new ResourceNotFoundException("Cinema with Id " + id + " not found.");
        }
        save(list);
    }

    public void save(List<Cinema> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Cinema cinema : list) {
                writer.write(cinema.getId() + "," 
                			+ cinema.getName() + "," 
            				+ cinema.getAddress()
            	);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Cinema> load() {
        List<Cinema> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // atributos separados por vírgula
                Long id = Long.parseLong(data[0]);
                String name = data[1];
                String address = data[2];
                Cinema cinema = new Cinema(id, name, address);
                list.add(cinema);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
