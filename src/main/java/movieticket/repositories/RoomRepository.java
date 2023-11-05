package movieticket.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import movieticket.entities.Cinema;
import movieticket.entities.Room;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.ResourceNotFoundException;

public class RoomRepository {

	private String file = "rooms.csv";
	
	private CinemaRepository cinemaRepository = new CinemaRepository();
	
	public List<Room> findAll() {
		return load();
	}
	
	public List<Room> findAllByCinemaId(Long cinemaId){
		List<Room> list = load();
		return list.stream()
	            .filter(room -> room.getCinema().getId().equals(cinemaId)) // filtra as salas com o cinemaId fornecido
	            .collect(Collectors.toList());
	}
	
	public Optional<Room> findById(Long id) {
	    List<Room> list = load();
	    return Optional.ofNullable(list.stream()
	            .filter(room -> room.getId().equals(id))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("Room with Id " + id + " not found.")));
	}
	
	public void insert(Room room) {
	    List<Room> list = load(); // carrega a lista de objetos já cadastrados no csv
	    Long newId = room.getId(); // id do objeto a ser inserido
	    boolean idExists = list.stream().anyMatch(existingRoom -> existingRoom.getId().equals(newId)); // percorre a lista para ver se o id já está cadastrado
	    if (idExists) {
	        throw new DuplicateResourceException("Room with ID " + newId + " already exists.");
	    }
	    list.add(room); // adiciona o objeto a lista
	    save(list); // salva a lista novamente
	}

    public void update(Room updatedRoom) {
        List<Room> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isUpdated = false; // inicia como falso para encontrar o objeto

        for (Room room : list) { // percorre a lista
            if (room.getId().equals(updatedRoom.getId())) { // quando achou o objeto procurado, atualiza seus dados
                room.setNumber(updatedRoom.getNumber());
                room.setCinema(updatedRoom.getCinema());
                isUpdated = true; // muda para verdadeiro
                break;
            }
        }

        if (!isUpdated) {
            throw new ResourceNotFoundException("Room with Id " + updatedRoom.getId() + " not found.");
        }
        save(list); // salva a lista novamente
    }

    public void delete(Long id) {
        List<Room> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isDeleted = list.removeIf(room -> room.getId().equals(id)); // verifica a existência do id e o deleta

        if (!isDeleted) {
            throw new ResourceNotFoundException("Room with Id " + id + " not found.");
        }
        save(list);
    }

    public void save(List<Room> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Room room : list) {
                writer.write(room.getId() + "," + room.getNumber() + "," + room.getCinema().getId());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Room> load() {
        List<Room> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // atributos separados por vírgula
                Long id = Long.parseLong(data[0]);
                Integer number = Integer.parseInt(data[1]);
                Cinema cinema = cinemaRepository.findById(Long.parseLong(data[2])).get();
                Room room = new Room(id, number, cinema);
                list.add(room);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
