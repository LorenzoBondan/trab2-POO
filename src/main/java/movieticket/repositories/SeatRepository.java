package movieticket.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import movieticket.entities.Room;
import movieticket.entities.Seat;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.ResourceNotFoundException;

public class SeatRepository {

	private String file = "seats.csv";
	
	private RoomRepository roomRepository = new RoomRepository();
	
	public List<Seat> findAll() {
		return load();
	}
	
	public List<Seat> findAllByRoomId(Long roomId){
		List<Seat> list = load();
		order(list);
		return list.stream()
	            .filter(seat -> seat.getRoom().getId().equals(roomId)) // filtra os assentos com o roomId fornecido
	            .collect(Collectors.toList());
	}
	
	public Optional<Seat> findById(Long id) {
	    List<Seat> list = load();
	    return Optional.ofNullable(list.stream()
	            .filter(seat -> seat.getId().equals(id))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("Seat with Id " + id + " not found.")));
	}
	
	public void insert(Seat seat) {
	    List<Seat> list = load(); // carrega a lista de objetos já cadastrados no csv
	    Long newId = seat.getId(); // id do objeto a ser inserido
	    boolean idExists = list.stream().anyMatch(existingSeat -> existingSeat.getId().equals(newId)); // percorre a lista para ver se o id já está cadastrado
	    if (idExists) {
	        throw new DuplicateResourceException("Assento com ID " + newId + " já existe.");
	    }
	    list.add(seat); // adiciona o objeto a lista
	    save(list); // salva a lista novamente
	}

    public void update(Seat updatedSeat) {
        List<Seat> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isUpdated = false; // inicia como falso para encontrar o objeto

        for (Seat seat : list) { // percorre a lista
            if (seat.getId().equals(updatedSeat.getId())) { // quando achou o objeto procurado, atualiza seus dados
                seat.setNumber(updatedSeat.getNumber());
                seat.setLine(updatedSeat.getLine());
                seat.setRoom(updatedSeat.getRoom());
                isUpdated = true; // muda para verdadeiro
                break;
            }
        }

        if (!isUpdated) {
            throw new ResourceNotFoundException("Seat with Id " + updatedSeat.getId() + " not found.");
        }
        save(list); // salva a lista novamente
    }

    public void delete(Long id) {
        List<Seat> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isDeleted = list.removeIf(seat -> seat.getId().equals(id)); // verifica a existência do id e o deleta

        if (!isDeleted) {
            throw new ResourceNotFoundException("Seat with Id " + id + " not found.");
        }
        save(list);
    }

    public void save(List<Seat> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Seat seat : list) {
                writer.write(seat.getId() + "," 
                			+ seat.getNumber() + ","
                			+ seat.getLine()  + ","
                			+ seat.getRoom().getId()
                		);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void order(List<Seat> seats) {

        Collections.sort(seats, new Comparator<Seat>() {

            public int compare(Seat o1, Seat o2) {

                Integer x1 = ((Seat) o1).getLine();
                Integer x2 = ((Seat) o2).getLine();
                int sComp = x1.compareTo(x2);

                if (sComp != 0) {
                   return sComp;
                } 

                Integer x12 = ((Seat) o1).getNumber();
                Integer x22 = ((Seat) o2).getNumber();
                return x12.compareTo(x22);
        }});
    }

    public List<Seat> load() {
        List<Seat> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // atributos separados por vírgula
                Long id = Long.parseLong(data[0]);
                Integer number = Integer.parseInt(data[1]);
                Integer seatLine = Integer.parseInt(data[2]);
                Long roomId = Long.parseLong(data[3]);
                Room room = roomRepository.findById(roomId).get();
                Seat seat = new Seat(id, number, seatLine, room);
                list.add(seat);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
