package movieticket.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import movieticket.entities.Movie;
import movieticket.entities.Schedule;
import movieticket.entities.Seat;
import movieticket.entities.Ticket;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.ResourceNotFoundException;

public class TicketRepository {

	private String file = "tickets.csv";
	
	private SeatRepository seatRepository = new SeatRepository();
	private ScheduleRepository scheduleRepository = new ScheduleRepository();
	private MovieRepository movieRepository = new MovieRepository();
	
	public List<Ticket> findAll() {
		return load();
	}
	
	public Optional<Ticket> findById(Long id) {
	    List<Ticket> list = load();
	    return Optional.ofNullable(list.stream()
	            .filter(ticket -> ticket.getId().equals(id))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("Ticket with Id " + id + " not found.")));
	}
	
	public void insert(Ticket ticket) {
	    List<Ticket> list = load(); // carrega a lista de objetos já cadastrados no csv
	    Long newId = ticket.getId(); // id do objeto a ser inserido
	    boolean idExists = list.stream().anyMatch(existingTicket -> existingTicket.getId().equals(newId)); // percorre a lista para ver se o id já está cadastrado
	    if (idExists) {
	        throw new DuplicateResourceException("Ticket with ID " + newId + " already exists.");
	    }
	    list.add(ticket); // adiciona o objeto a lista
	    save(list); // salva a lista novamente
	}

    public void update(Ticket updatedTicket) {
        List<Ticket> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isUpdated = false; // inicia como falso para encontrar o objeto

        for (Ticket ticket : list) { // percorre a lista
            if (ticket.getId().equals(updatedTicket.getId())) { // quando achou o objeto procurado, atualiza seus dados
                ticket.setClientName(updatedTicket.getClientName());
                ticket.setDate(updatedTicket.getDate());
                ticket.setHalfPrice(updatedTicket.getHalfPrice());
                ticket.setMovie(updatedTicket.getMovie());
                ticket.setPhoneNumber(updatedTicket.getPhoneNumber());
                ticket.setPrice(updatedTicket.getPrice());
                ticket.setSchedule(updatedTicket.getSchedule());
                ticket.setSeat(updatedTicket.getSeat());
                isUpdated = true; // muda para verdadeiro
                break;
            }
        }

        if (!isUpdated) {
            throw new ResourceNotFoundException("Ticket with Id " + updatedTicket.getId() + " not found.");
        }
        save(list); // salva a lista novamente
    }

    public void delete(Long id) {
        List<Ticket> list = load(); // carrega a lista de objetos já cadastrados no csv
        boolean isDeleted = list.removeIf(ticket -> ticket.getId().equals(id)); // verifica a existência do id e o deleta

        if (!isDeleted) {
            throw new ResourceNotFoundException("Ticket with Id " + id + " not found.");
        }
        save(list);
    }

    public void save(List<Ticket> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Ticket ticket : list) {
                writer.write(ticket.getId() + "," 
                			+ ticket.getClientName() + "," 
                			+ ticket.getDate() + ","
                			+ ticket.getPhoneNumber() + ","
                			+ ticket.getPrice() + ","
                			+ ticket.getHalfPrice() + ","
                			+ ticket.getSeat().getId() + ","
                			+ ticket.getSchedule().getId() + ","
                			+ ticket.getMovie().getId()
                			);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Ticket> load() {
        List<Ticket> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Formato da data no arquivo CSV
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // atributos separados por vírgula
                Long id = Long.parseLong(data[0]);
                String clientName = data[1];
                Date date;
                try {
                    // Converte a string de data para um objeto Date usando o formato especificado
                    date = dateFormat.parse(data[2]);
                } catch (ParseException e) {
                    e.printStackTrace();
                    date = null; // Define como null se a conversão falhar
                }
                String phoneNumber = data[3];
                Double price = Double.parseDouble(data[4]);
                Boolean halfPrice = Boolean.parseBoolean(data[5]);
                Seat seat = seatRepository.findById(Long.parseLong(data[6])).get();
                Schedule schedule = scheduleRepository.findById(Long.parseLong(data[7])).get();
                Movie movie = movieRepository.findById(Long.parseLong(data[8])).get();
                
                Ticket ticket = new Ticket(id, clientName, date, phoneNumber, price, halfPrice, seat, schedule, movie);
                list.add(ticket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
