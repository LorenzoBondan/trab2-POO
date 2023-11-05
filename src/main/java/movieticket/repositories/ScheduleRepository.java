package movieticket.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import movieticket.entities.Movie;
import movieticket.entities.Room;
import movieticket.entities.Schedule;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.ResourceNotFoundException;

public class ScheduleRepository {

	private String file = "schedules.csv";
	
	private RoomRepository roomRepository = new RoomRepository();
	private MovieRepository movieRepository = new MovieRepository();
	
	public List<Schedule> findAll() {
		return load();
	}
	
	public List<Schedule> findAllByRoomId(Long roomId){
		List<Schedule> list = load();
		return list.stream()
	            .filter(schedule -> schedule.getRoom().getId().equals(roomId)) // filtra os horários com o roomId fornecido
	            .collect(Collectors.toList());
	}
	
	public List<Schedule> findAllByMovieId(Long movieId){
		List<Schedule> list = load();
		return list.stream()
	            .filter(schedule -> schedule.getMovie().getId().equals(movieId)) // filtra os horários com o movieId fornecido
	            .collect(Collectors.toList());
	}
	
	public Optional<Schedule> findById(Long id) {
	    List<Schedule> list = load();
	    return Optional.ofNullable(list.stream()
	            .filter(schedule -> schedule.getId().equals(id))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("Schedule with Id " + id + " not found.")));
	}
	
	public void insert(Schedule schedule) {
	    List<Schedule> list = load(); // carrega a lista de objetos já cadastrados no csv
	    Long newId = schedule.getId(); // id do objeto a ser inserido
	    boolean idExists = list.stream().anyMatch(existingSchedule -> existingSchedule.getId().equals(newId)); // percorre a lista para ver se o id já está cadastrado
	    if (idExists) {
	        throw new DuplicateResourceException("Schedule with ID " + newId + " already exists.");
	    }
	    list.add(schedule); // adiciona o objeto a lista
	    save(list); // salva a lista novamente
	}

    public void update(Schedule updatedSchedule) {
        List<Schedule> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isUpdated = false; // inicia como falso para encontrar o objeto

        for (Schedule schedule : list) { // percorre a lista
            if (schedule.getId().equals(updatedSchedule.getId())) { // quando achou o objeto procurado, atualiza seus dados
                schedule.setDate(updatedSchedule.getDate());
                schedule.setTime(updatedSchedule.getTime());
                schedule.setRoom(updatedSchedule.getRoom());
                schedule.setMovie(updatedSchedule.getMovie());
                isUpdated = true; // muda para verdadeiro
                break;
            }
        }

        if (!isUpdated) {
            throw new ResourceNotFoundException("Schedule with Id " + updatedSchedule.getId() + " not found.");
        }
        save(list); // salva a lista novamente
    }

    public void delete(Long id) {
        List<Schedule> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isDeleted = list.removeIf(schedule -> schedule.getId().equals(id)); // verifica a existência do id e o deleta

        if (!isDeleted) {
            throw new ResourceNotFoundException("Schedule with Id " + id + " not found.");
        }
        save(list);
    }

    public void save(List<Schedule> list) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // formato da data no arquivo CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Schedule schedule : list) {
                String formattedDate = dateFormat.format(schedule.getDate()); // formata a data para o formato desejado
                writer.write(schedule.getId() + "," 
                            + formattedDate + "," // grava a data formatada
                            + schedule.getTime()  + ","
                            + schedule.getRoom().getId() + ","
                            + schedule.getMovie().getId()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Schedule> load() {
        List<Schedule> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Formato da data no arquivo CSV
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // atributos separados por vírgula
                Long id = Long.parseLong(data[0]);
                Date date;
                try {
                    date = dateFormat.parse(data[1]); // converte a string de data para um objeto Date usando o formato especificado
                } catch (ParseException e) {
                    e.printStackTrace();
                    date = null; // define como null se a conversão falhar
                }
                LocalTime time = LocalTime.parse(data[2]);
                Long roomId = Long.parseLong(data[3]);
                Room room = roomRepository.findById(roomId).get();
                Long movieId = Long.parseLong(data[4]);
                Movie movie = movieRepository.findById(movieId).get();
                Schedule schedule = new Schedule(id, date, time, room, movie);
                list.add(schedule);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
